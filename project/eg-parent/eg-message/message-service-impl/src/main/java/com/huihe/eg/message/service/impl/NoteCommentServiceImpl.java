package com.huihe.eg.message.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.comm.sensitive.SensitiveFilterService;
import com.huihe.eg.message.model.HistoryEntity;
import com.huihe.eg.message.model.MessageBlacklistEntity;
import com.huihe.eg.message.model.NoteCommentEntity;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.mybatis.dao.HistoryMapper;
import com.huihe.eg.message.mybatis.dao.MessageBlacklistMapper;
import com.huihe.eg.message.mybatis.dao.NoteCommentMapper;
import com.huihe.eg.message.mybatis.dao.NoteUserMapper;
import com.huihe.eg.message.service.dao.NoteCommentService;
import com.huihe.eg.message.service.dao.NoteUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class NoteCommentServiceImpl extends BaseFrameworkServiceImpl<NoteCommentEntity, Long> implements NoteCommentService {

    @Resource
    private NoteCommentMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private HistoryMapper historyMapper;
    @Resource
    private NoteUserMapper noteUserMapper;
    @Resource
    private NoteUserService noteUserService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MessageBlacklistMapper messageBlacklistMapper;
    @Override
    public void init() {
        setMapper(mapper);
    }


    @Override
    public ResultParam update(NoteCommentEntity noteCommentEntity, HttpServletRequest request, HttpServletResponse response) {
        if (noteCommentEntity.getStatus() != null && noteCommentEntity.getStatus() == 4) {
            MessageBlacklistEntity messageBlacklistEntity = new MessageBlacklistEntity();
            messageBlacklistEntity.setUser_id(noteCommentEntity.getUser_id());
            messageBlacklistEntity.setBlacklist_status(3);
            int i = messageBlacklistMapper.insertSelective(messageBlacklistEntity);
        }
        return super.update(noteCommentEntity, request, response);
    }

    /**
     * ????????????
     * @Description: ????????????
     * @author zwx
     * @Date : 2019???1???7???
     */
    @Override
    public List<NoteCommentEntity> queryListPage(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        List<NoteCommentEntity> noteCommentEntities=null;
        try {
            if (param.getStatus()==null){
                param.setStatus(1);
            }
            if(StringUtil.isEmpty(param.getNote_id().toString())){
                throw new DataException(NewsEum.news_80007.getCode(),NewsEum.news_80007.getDesc());
            }

            noteCommentEntities=mapper.queryListPage(param);
            for (NoteCommentEntity noteCommentEntity :noteCommentEntities) {
                // String userInfo=redisService.getStr(noteCommentEntity.getUser_id()+"userInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(noteCommentEntity.getUser_id()+"userinfo")));
                Long subReply_id= 0L;
                if(noteCommentEntity.getIs_reply()!=null&&noteCommentEntity.getIs_reply()){
                    subReply_id =noteCommentEntity.getReply_id();
                }
                map.put("replyinfo",JSONUtils.obj2Json(redisService.getStr(subReply_id+"userinfo")));
                noteCommentEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(" NoteCommentServiceImpl       queryListPage" );
        }
        return noteCommentEntities;
    }
    /**
     * ??????????????????????????????
     * @Description: ????????????
     * @author zwx
     * @Date : 2019???1???7???
     */

    @Override
    public List<NoteCommentEntity> queryDetailsListPage(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        List<NoteCommentEntity> noteCommentEntities=null;
        try {
            if(StringUtil.isEmpty(param.getNote_id().toString())){
                throw new DataException(NewsEum.news_80007.getCode(),NewsEum.news_80007.getDesc());
            }
            noteCommentEntities=mapper.queryListPage(param);
            for (NoteCommentEntity noteCommentEntity :noteCommentEntities) {
                // String userInfo=redisService.getStr(noteCommentEntity.getUser_id()+"userInfo");
                Map<String,Object> map = new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(noteCommentEntity.getUser_id()+"userinfo")));
                NoteCommentEntity commentEntity=new NoteCommentEntity();
                commentEntity.setParent_id(noteCommentEntity.getId());
                commentEntity.setStatus(1);
                List<NoteCommentEntity> noteComments=mapper.queryListPage(commentEntity);
                map.put("subcount",mapper.queryListPageCount(commentEntity));
                for (NoteCommentEntity commentEntity1 :noteComments) {
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("userinfo",JSONUtils.obj2Json(redisService.getStr(commentEntity1.getUser_id()+"userinfo")));
                    Long subReply_id= 0L;
                    if(commentEntity1.getIs_reply()!=null&&commentEntity1.getIs_reply()){
                        subReply_id =commentEntity1.getReply_id();
                    }
                    map1.put("replyinfo",JSONUtils.obj2Json(redisService.getStr(subReply_id+"userinfo")));
                    if(param.getOper_id()!=null&&param.getOper_id()>0){
                        //????????????
                        HistoryEntity historyEntity=new HistoryEntity();
                        historyEntity.setHistory_id(commentEntity1.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setHistory_type("notecomment");
                        historyEntity.setStatus(1);
                        List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                        map1.put("is_like", list != null && list.size() > 0);
                    }else{
                        map1.put("is_like",false);
                    }
                    commentEntity1.setMap(map1);
                }
                map.put("subComment",noteComments);
                if(param.getOper_id()!=null&&param.getOper_id()>0){
                    //????????????
                    HistoryEntity historyEntity=new HistoryEntity();
                    historyEntity.setHistory_id(noteCommentEntity.getId());
                    historyEntity.setOperation_type("like");
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setHistory_type("notecomment");
                    historyEntity.setStatus(1);
                    List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                    map.put("is_like", list != null && list.size() > 0);
                }else{
                    map.put("is_like",false);
                }
                noteCommentEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(" NoteCommentServiceImpl       queryListPage" );
        }
        return noteCommentEntities;
    }

    @Override
    public Map<String, Object> queryManagerListPageSystem(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            map.put("rows", mapper.queryListPage(param));
            map.put("total", mapper.queryListPageCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam insert(NoteCommentEntity param, HttpServletRequest request, HttpServletResponse response) {
        MessageBlacklistEntity messageBlacklistEntity = new MessageBlacklistEntity();
        messageBlacklistEntity.setUser_id(param.getUser_id());
        messageBlacklistEntity.setBlacklist_status(3);
        messageBlacklistEntity.setPageSize(1);
        List<MessageBlacklistEntity> messageBlacklistEntities = messageBlacklistMapper.queryListPage(messageBlacklistEntity);
        if (messageBlacklistEntities != null && messageBlacklistEntities.size() > 0) {
            return ResultUtil.error(NewsEum.news_80010.getCode(), NewsEum.news_80010.getDesc());
        }
        param.setStatus(3);

        Map<String,Object> map = new HashMap<>();
        map.put("type","notecomment");
        map.put("new_time",new Date());
        map.put("notecomment",param);
        ResultParam resultParam=null;
        try {
            if(StringUtil.isEmpty(param.getNote_id().toString())){
                return ResultUtil.error(NewsEum.news_80007.getCode(),NewsEum.news_80007.getDesc());
            }
            if(StringUtil.isEmpty(param.getUser_id().toString())){
                return ResultUtil.error(NewsEum.news_80001.getCode(),NewsEum.news_80001.getDesc());
            }
            /*if(param.getContent()!=null&&param.getContent()!=""){
                SensitiveFilterService filter = SensitiveFilterService.getInstance();
                String hou = filter.replaceSensitiveWord(param.getContent(), 1, "*");
                param.setContent(hou);
            }*/

            if ("h5".equals(param.getPlatform())){
                return ResultUtil.error(NewsEum.news_80009.getCode(),NewsEum.news_80009.getDesc());
            }

            resultParam= super.insert(param, request, response);
            if (resultParam.getCode() == 0){//????????????
                NoteUserEntity entity=new NoteUserEntity();
                entity.setId(param.getNote_id());
                entity.setPageSize(1);
                List<NoteUserEntity> entities=noteUserService.queryListPage(entity,request,response);//???????????????
                PushMessageParam pushMessageParam=new PushMessageParam();
                if(entities!=null&&entities.size()>0){
                    if(param.getParent_id()!=null&&param.getParent_id()!=0){
                        //???????????????
                        if(param.getReply_id()!=null&&param.getReply_id()!=0){
                            map.put("noteinfo",entities.get(0));//????????????
                            map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(param.getUser_id()+"userinfo")));
                            String str=redisService.getStr(param.getReply_id()+"notenews");
                            if(str!=null&& !str.equals("")){
                                List<String> list1=JSONUtils.json2List(str,null);
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(param.getReply_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }else{
                                List<String> list1=new ArrayList<>();
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(param.getReply_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }
                        }
                        if(param.getUser_id()!=null&&param.getUser_id()!=0){
                            String str1=redisService.getStr(entities.get(0).getUser_id()+"notenews");
                            if(str1!=null&& !str1.equals("")){
                                List<String> list1=JSONUtils.json2List(str1,null);
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(entities.get(0).getUser_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }else{
                                List<String> list1=new ArrayList<>();
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(entities.get(0).getUser_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }
                        }
                        //??????
                        pushMessageParam.setTarget_id(param.getReply_id());
                        pushMessageParam.setType_id(param.getNote_id());
                        pushMessageParam.setOpera_type("addNoteCommentSub");
                    }else{
                        //???????????????
                        if(param.getUser_id()!=null&&param.getUser_id()!=0){
                            map.put("noteinfo",entities.get(0));
                            map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(param.getUser_id()+"userinfo")));
                            String str=redisService.getStr(entities.get(0).getUser_id()+"notenews");
                            if(str!=null&& !str.equals("")){
                                List<String> list1=JSONUtils.json2List(str,null);
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(entities.get(0).getUser_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }else{
                                List<String> list1=new ArrayList<>();
                                list1.add(JSONUtils.obj2Json(map).toString());
                                redisService.set(entities.get(0).getUser_id()+"notenews",JSONUtils.list2Json(list1).toString());
                            }
                        }
                        //??????
                        pushMessageParam.setTarget_id(entities.get(0).getUser_id());
                        pushMessageParam.setType_id(param.getNote_id());
                        pushMessageParam.setOpera_type("addNoteComment");
                    }
                    pushMessageParam.setType("note");
                    pushMessageParam.setPush_type("notecomment");
                    pushMessageParam.setPush_time(new Date());
                    pushMessageParam.setSend_id(param.getUser_id());
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("NoteCommentServiceImpl  insert ");
        }
        return resultParam;
    }

    /**
     * ??????????????????
     * @param user_id
     * @return
     */
    @Override
    public Integer queryCommentAddCount(Long user_id) {
        NoteCommentEntity commentEntity=new NoteCommentEntity();
        commentEntity.setUser_id(user_id);
        return mapper.queryCommentAddCount(commentEntity);
    }
}