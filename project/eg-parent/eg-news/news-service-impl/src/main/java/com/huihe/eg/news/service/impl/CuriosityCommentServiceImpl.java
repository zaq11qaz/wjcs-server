package com.huihe.eg.news.service.impl;

import com.alibaba.fastjson.JSON;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.news.model.CuriosityCommentEntity;
import com.huihe.eg.news.model.CuriosityViewpointEntity;
import com.huihe.eg.news.model.HistoryEntity;
import com.huihe.eg.news.mybatis.dao.CuriosityCommentMapper;
import com.huihe.eg.news.mybatis.dao.CuriosityViewpointMapper;
import com.huihe.eg.news.mybatis.dao.HistoryMapper;
import com.huihe.eg.news.service.dao.CuriosityCommentService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CuriosityCommentServiceImpl extends BaseFrameworkServiceImpl<CuriosityCommentEntity, Long> implements CuriosityCommentService {

    @Resource
    private CuriosityCommentMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private HistoryMapper historyMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private CuriosityViewpointMapper viewpointMapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 分页查询
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年1月8日
     */
    @Override
    public List<CuriosityCommentEntity> queryListPage(CuriosityCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityCommentEntity> commentEntities=null;
        try {
            commentEntities=mapper.queryListPage(param);
                for (CuriosityCommentEntity commentEntity: commentEntities
                        ) {
                    CuriosityCommentEntity entity=new CuriosityCommentEntity();
                    entity.setParent_id(commentEntity.getId());
                    entity.setStatus(1);
                    List<CuriosityCommentEntity> entities=mapper.queryListPage(entity);
                    Map<String,Object> map=new HashMap<>();
                    map.put("subcount",mapper.queryListPageCount(entity));
                    for (CuriosityCommentEntity entity1:entities){
                        Map<String,Object> map1=new HashMap<>();
                        Long subReply_id= 0L;
                        if(entity1.getIs_reply()!=null&&entity1.getIs_reply()){
                            subReply_id =entity1.getReply_id();
                        }
                        map1.put("replyinfo",JSONUtils.obj2Json(redisService.getStr(subReply_id+"userinfo")));
                        map1.put("userinfo",JSONUtils.obj2Json(redisService.getStr(entity1.getUser_id()+"userinfo")));
                        if(param.getOper_id()!=null&&param.getOper_id()>0){
                            //是否点赞
                            HistoryEntity historyEntity=new HistoryEntity();
                            historyEntity.setHistory_id(entity1.getId());
                            historyEntity.setOperation_type("like");
                            historyEntity.setUser_id(param.getOper_id());
                            historyEntity.setStatus(1);
                            historyEntity.setHistory_type("curiosityviewpointcomment");
                            List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                            if(list!=null&&list.size()>0){
                                map1.put("is_like",true);
                            }else {
                                map1.put("is_like",false);
                            }
                        }else{
                            map1.put("is_like",false);
                        }
                        entity1.setMap(map1);
                    }
                    if(param.getOper_id()!=null&&param.getOper_id()>0){
                        //是否点赞
                        HistoryEntity historyEntity=new HistoryEntity();
                        historyEntity.setHistory_id(commentEntity.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setStatus(1);
                        historyEntity.setHistory_type("curiosityviewpointcomment");
                        List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                        if(list!=null&&list.size()>0){
                            map.put("is_like",true);
                        }else{
                            map.put("is_like",false);
                        }
                    }else{
                        map.put("is_like",false);
                    }
                    map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(commentEntity.getUser_id()+"userinfo")));
                    map.put("subComment",entities);
                    commentEntity.setMap(map);
                }

        }catch (Exception e){
            e.printStackTrace();
            logger.debug("CuriosityCommentServiceImpl  queryListPage  debug");
        }
        return  commentEntities;
    }

    /**
     * 查询最热评论
     * 2019年1月15日
     * zwx
     */
    @Override
    public List<CuriosityCommentEntity> queryHotCommentListPage(CuriosityCommentEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityCommentEntity> commentEntities=null;
        try {
            commentEntities=mapper.queryHotCommentListPage(param);
            for (CuriosityCommentEntity commentEntity: commentEntities
                    ) {
                CuriosityCommentEntity entity=new CuriosityCommentEntity();
                entity.setParent_id(commentEntity.getId());
                entity.setStatus(1);
                List<CuriosityCommentEntity> entities=mapper.queryListPage(entity);
                Map<String,Object> map=new HashMap<>();
                map.put("subcount",mapper.queryListPageCount(entity));
                for (CuriosityCommentEntity entity1:entities){
                    Map<String,Object> map1=new HashMap<>();
                    Long subReply_id= 0L;
                    if(entity1.getIs_reply()!=null&&entity1.getIs_reply()){
                        subReply_id =entity1.getReply_id();
                    }
                    map1.put("replyinfo",JSONUtils.obj2Json(redisService.getStr(subReply_id+"userinfo")));
                    map1.put("userinfo",JSONUtils.obj2Json(redisService.getStr(entity1.getUser_id()+"userinfo")));
                    if(param.getOper_id()!=null&&param.getOper_id()>0){
                        //是否点赞
                        HistoryEntity historyEntity=new HistoryEntity();
                        historyEntity.setHistory_id(entity1.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setStatus(1);
                        historyEntity.setHistory_type("curiosityviewpointcomment");
                        List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                        if(list!=null&&list.size()>0){
                            map1.put("is_like",true);
                        }else{
                            map1.put("is_like",false);
                        }
                    }
                    entity1.setMap(map1);
                }
                if(param.getOper_id()!=null&&param.getOper_id()>0) {
                    //是否点赞
                    HistoryEntity historyEntity=new HistoryEntity();
                    historyEntity.setHistory_id(commentEntity.getId());
                    historyEntity.setOperation_type("like");
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setStatus(1);
                    historyEntity.setHistory_type("curiosityviewpointcomment");
                    List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                    if(list!=null&&list.size()>0){
                        map.put("is_like",true);
                    }else{
                        map.put("is_like",false);
                    }
                }
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(commentEntity.getUser_id()+"userinfo")));
                map.put("subComment",entities);
                commentEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return commentEntities;
    }


    @Override
    public ResultParam insert(CuriosityCommentEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            if(null==param.getViewpoint_id()||StringUtil.isEmpty(param.getViewpoint_id().toString())){
                return ResultUtil.error(NewsEum.news_80006.getCode(),NewsEum.news_80006.getDesc());
            }
            if(null==param.getUser_id()||StringUtil.isEmpty(param.getUser_id().toString())){
                return ResultUtil.error(NewsEum.news_80001.getCode(),NewsEum.news_80001.getDesc());
            }
            resultParam= super.insert(param, request, response);
            if (resultParam.getCode() == 0){
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setId(param.getViewpoint_id());
                curiosityViewpointEntity.setPageSize(1);
                List<CuriosityViewpointEntity> entities=viewpointMapper.query(curiosityViewpointEntity);
                if(entities!=null&&entities.size()>0){
                    PushMessageParam pushMessageParam=new PushMessageParam();
                    if(param.getParent_id()==null||param.getParent_id()==0){
                        pushMessageParam.setTarget_id(entities.get(0).getUser_id());
                        pushMessageParam.setType_id(param.getViewpoint_id());
                        pushMessageParam.setOpera_type("addViewpointComment");
                    }else{
                        CuriosityCommentEntity entity=new CuriosityCommentEntity();
                        entity.setId(param.getParent_id());
                        List<CuriosityCommentEntity> entityList=mapper.query(entity);
                        pushMessageParam.setTarget_id(entityList.get(0).getReply_id());
                        pushMessageParam.setType_id(param.getViewpoint_id());
                        pushMessageParam.setOpera_type("addViewpointSubComment");
                    }
                    if(entities.get(0).getStyle()!=null||entities.get(0).getStyle()!=0){
                        pushMessageParam.setView_type("pic");
                    }else {
                        pushMessageParam.setView_type("video");
                    }
                    pushMessageParam.setPush_time(new Date());
                    pushMessageParam.setType("curiosity");
                    pushMessageParam.setSend_id(param.getUser_id());
                    pushMessageParam.setPush_type("notice");
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    logger.info("push info "+JSON.toJSONString(pushMessageParam));
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("video", pushMessageParam);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }
}