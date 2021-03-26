package com.huihe.eg.news.service.impl;
import com.alibaba.fastjson.JSON;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.news.model.*;
import com.huihe.eg.news.mybatis.dao.*;
import com.huihe.eg.news.service.dao.CuriosityCommentService;
import com.huihe.eg.news.service.dao.CuriosityViewpointService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CuriosityViewpointServiceImpl extends BaseFrameworkServiceImpl<CuriosityViewpointEntity, Long> implements CuriosityViewpointService {

    @Resource
    private CuriosityViewpointMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private HistoryMapper historyMapper;
    @Resource
    private CuriosityClassfiyMapper classfiyMapper;
    @Resource
    private CuriosityCommentService curiosityCommentService;
    @Resource
    private CuriosityMapper curiosityMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public void init() {
        setMapper(mapper);
    }




    /**
     * 分页查询
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年1月8日
     *
     * */
    @Override
    public List<CuriosityViewpointEntity> queryListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityViewpointEntity>  viewpointEntities=null;
        try {
            viewpointEntities=mapper.queryListPage(param);
            for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities
                    ) {
                if(curiosityViewpointEntity.getPicts()!=null){
                    List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getPicts(),String.class);
                    if (list.size() != 0) {
                        curiosityViewpointEntity.setPicStrings(list);
                        curiosityViewpointEntity.setPicts(null);
                    }
                }
                if(curiosityViewpointEntity.getCover()!=null){
                    List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getCover(),String.class);
                    if (list.size() != 0) {
                        curiosityViewpointEntity.setCoverPics(list);
                        curiosityViewpointEntity.setCover(null);
                    }
                }
                Map<String,Object> map = new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityViewpointEntity.getUser_id()+"userinfo")));
                if(param.getOper_id()!=null&&param.getOper_id()!=0){
                    //是否点赞
                    HistoryEntity historyEntity=new HistoryEntity();
                    historyEntity.setHistory_id(curiosityViewpointEntity.getId());
                    historyEntity.setOperation_type("like");
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setHistory_type("curiosityviewpoint");
                    List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                    if(list!=null&&list.size()>0){
                        map.put("is_like",true);
                    }else{
                        map.put("is_like",false);
                    }
                    //是否收藏
                    HistoryEntity historyCollect=new HistoryEntity();
                    historyCollect.setHistory_id(curiosityViewpointEntity.getId());
                    historyCollect.setOperation_type("collect");
                    historyCollect.setUser_id(param.getOper_id());
                    historyCollect.setHistory_type("curiosityviewpointcollect");
                    List<HistoryEntity> listCollect =historyMapper.queryListPage(historyCollect);
                    if(listCollect!=null&&listCollect.size()>0){
                        map.put("is_collect",true);
                    }else{
                        map.put("is_collect",false);
                    }
                }
                //查询标签
                CuriosityClassfiyEntity classfiyEntity=classfiyMapper.selectByPrimaryKey(curiosityViewpointEntity.getClassfiy());
                map.put("classfiyInfo",classfiyEntity);
                curiosityViewpointEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("CuriosityViewpoint  queryListPage  debug");
        }
        return  viewpointEntities;
    }
    /**
     * 详情查询
     * @Description: 详情查询
     * @author zwx
     * @Date : 2019年1月7日
     */
    @Override
    public List<CuriosityViewpointEntity> query(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<CuriosityViewpointEntity>  viewpointEntities=null;
        try {
            viewpointEntities=mapper.query(param);
            if(viewpointEntities!=null&&viewpointEntities.size()>0){
                CuriosityViewpointEntity viewpointEntity=viewpointEntities.get(0);
                if(viewpointEntity.getPicts()!=null){
                    List<String> list =JSONUtils.json2List(viewpointEntity.getPicts(),String.class);
                    if (list != null&&list.size()>0) {
                        viewpointEntity.setPicStrings(list);
                        viewpointEntity.setPicts(null);
                    }
                }
                if(viewpointEntity.getCover()!=null){
                    List<String> list =JSONUtils.json2List(viewpointEntity.getCover(),String.class);
                    if (list != null&&list.size()>0) {
                        viewpointEntity.setCoverPics(list);
                        viewpointEntity.setCover(null);
                    }
                }
                CuriosityCommentEntity entity=new CuriosityCommentEntity();
                entity.setViewpoint_id(viewpointEntity.getId());
                entity.setOper_id(param.getOper_id());
                entity.setParent_id(0L);
                entity.setPageSize(10);
                entity.setStatus(1);
                List<CuriosityCommentEntity> commentEntities=curiosityCommentService.queryListPage(entity,request,response);
                Map<String,Object> map = new HashMap<>();
                map.put("commentList",commentEntities);
                entity.setPageSize(2);
                List<CuriosityCommentEntity> entity1=curiosityCommentService.queryHotCommentListPage(entity,request,response);
                map.put("hotCommentList",entity1);
                //是否点赞0
                if(param.getOper_id()!=null&&param.getOper_id()!=0){
                    HistoryEntity historyEntity=new HistoryEntity();
                    historyEntity.setHistory_id(viewpointEntity.getId());
                    historyEntity.setOperation_type("like");
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setHistory_type("curiosityviewpoint");
                    List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                    if(list!=null&&list.size()>0){
                        map.put("is_like",true);
                    }else{
                        map.put("is_like",false);
                    }
                    //是否收藏
                    HistoryEntity historyCollect=new HistoryEntity();
                    historyCollect.setHistory_id(viewpointEntity.getId());
                    historyCollect.setOperation_type("collect");
                    historyCollect.setUser_id(param.getOper_id());
                    historyCollect.setHistory_type("curiosityviewpointcollect");
                    List<HistoryEntity> listCollect =historyMapper.queryListPage(historyCollect);
                    if(listCollect!=null&&listCollect.size()>0){
                        map.put("is_collect",true);
                    }else{
                        map.put("is_collect",false);
                    }
                }
                //查询标签
                CuriosityClassfiyEntity classfiyEntity=classfiyMapper.selectByPrimaryKey(viewpointEntity.getClassfiy());
                map.put("classfiyInfo",classfiyEntity);
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(viewpointEntity.getUser_id()+"userinfo")));
                CuriosityEntity curiosityEntity=new CuriosityEntity();
                curiosityEntity.setId(viewpointEntity.getCuriosity_id());
                List<CuriosityEntity> curiosityEntity1=curiosityMapper.query(curiosityEntity);
                if(curiosityEntity1!=null&&curiosityEntity1.size()>0){
                    map.put("usercuriosityinfo",JSONUtils.obj2Json(redisService.getStr(curiosityEntity1.get(0).getUser_id()+"userinfo")));
                }
                viewpointEntity.setCuriosityEntity(curiosityEntity1.get(0));
                viewpointEntity.setMap(map);
                mapper.updateBrowseCount(viewpointEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("CuriosityViewpoint query debug");
        }
        return  viewpointEntities;
    }
    /**
     * 查看收藏的回应
     * zwx
     * 2019年1月15日
     */
    @Override
    public List<CuriosityViewpointEntity> queryCollectCuriosityListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityViewpointEntity>  viewpointEntities=null;
        try {

            viewpointEntities=mapper.queryCollectCuriosityListPage(param);
            if(viewpointEntities!=null&&viewpointEntities.size()>0){
                for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities
                        ) {
                    if(curiosityViewpointEntity.getPicts()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getPicts(),String.class);
                        if (list != null&&list.size()>0) {
                            curiosityViewpointEntity.setPicStrings(list);
                            curiosityViewpointEntity.setPicts(null);
                        }
                    }
                    if(curiosityViewpointEntity.getCover()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getCover(),String.class);
                        if (list != null&&list.size()>0) {
                            curiosityViewpointEntity.setCoverPics(list);
                            curiosityViewpointEntity.setCover(null);
                        }
                    }
                    Map<String,Object> map = new HashMap<>();
                    map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityViewpointEntity.getUser_id()+"userinfo")));
                    if(param.getOper_id()!=null&&param.getOper_id()!=0){
                        //是否点赞
                        HistoryEntity historyEntity=new HistoryEntity();
                        historyEntity.setHistory_id(curiosityViewpointEntity.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setHistory_type("curiosityviewpoint");
                        List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                        if(list!=null&&list.size()>0){
                            map.put("is_like",true);
                        }else{
                            map.put("is_like",false);
                        }
                        //是否收藏
                        HistoryEntity historyCollect=new HistoryEntity();
                        historyCollect.setHistory_id(curiosityViewpointEntity.getId());
                        historyCollect.setOperation_type("collect");
                        historyCollect.setUser_id(param.getOper_id());
                        historyCollect.setHistory_type("curiosityviewpointcollect");
                        List<HistoryEntity> listCollect =historyMapper.queryListPage(historyCollect);
                        if(listCollect!=null&&listCollect.size()>0){
                            map.put("is_collect",true);
                        }else{
                            map.put("is_collect",false);
                        }
                    }
                    curiosityViewpointEntity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("CuriosityViewpoint  queryListPage  debug");
        }
        return  viewpointEntities;
    }
    /**
     * 精彩回应
     * @Description:
     * @author zwx
     * @Date : 2019年1月8日
     */
    @Override
    public List<CuriosityViewpointEntity> queryHotListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<CuriosityViewpointEntity> viewpointEntities = null;
        try {
            viewpointEntities = mapper.queryHotListPage(param);
            if(viewpointEntities!=null&&viewpointEntities.size()>0){
                for (CuriosityViewpointEntity curiosityViewpointEntity : viewpointEntities
                        ) {
                    if(curiosityViewpointEntity.getPicts()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getPicts(),String.class);
                        if (list != null&&list.size()>0) {
                            curiosityViewpointEntity.setPicStrings(list);
                            curiosityViewpointEntity.setPicts(null);
                        }
                    }
                    if(curiosityViewpointEntity.getCover()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getCover(),String.class);
                        if (list != null&&list.size()>0) {
                            curiosityViewpointEntity.setCoverPics(list);
                            curiosityViewpointEntity.setCover(null);
                        }
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityViewpointEntity.getUser_id()+"userinfo")));
                    if(param.getOper_id()!=null&&param.getOper_id()!=0){
                        //是否点赞
                        HistoryEntity historyEntity=new HistoryEntity();
                        historyEntity.setHistory_id(curiosityViewpointEntity.getId());
                        historyEntity.setOperation_type("like");
                        historyEntity.setUser_id(param.getOper_id());
                        historyEntity.setHistory_type("curiosityviewpoint");
                        List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                        if(list!=null&&list.size()>0){
                            map.put("is_like",true);
                        }else{
                            map.put("is_like",false);
                        }
                        //是否收藏
                        HistoryEntity historyCollect=new HistoryEntity();
                        historyCollect.setHistory_id(curiosityViewpointEntity.getId());
                        historyCollect.setOperation_type("collect");
                        historyCollect.setUser_id(param.getOper_id());
                        historyCollect.setHistory_type("curiosityviewpointcollect");
                        List<HistoryEntity> listCollect =historyMapper.queryListPage(historyCollect);
                        if(listCollect!=null&&listCollect.size()>0){
                            map.put("is_collect",true);
                        }else{
                            map.put("is_collect",false);
                        }
                    }
                    curiosityViewpointEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("CuriosityViewpoint  queryListPage  debug");
        }
        return viewpointEntities;
    }
    @Override
    public ResultParam update(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            if(StringUtil.isEmpty(param.getId().toString())){
                return ResultUtil.error(NewsEum. news_80000.getCode(),NewsEum. news_80000.getDesc());
            }
            if(param.getPicts()!=null&&param.getPicts()!=""){
                String[] arr = param.getPicts().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                String string =JSONUtils.list2Json(list);
                param.setPicts(string);
            }
            if(param.getCover()!=null&&param.getCover()!=""){
                String[] arr = param.getCover().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                String string =JSONUtils.list2Json(list);
                param.setCover(string);
            }
            if (param.getPicStrings() != null && param.getPicStrings().size() >0) {
                String pic = JSONUtils.list2Json(param.getPicStrings()).toString();
                param.setPicts(pic);
            }
            if (param.getContent() != null && param.getContent().length() > 0) {
                String content = JSONUtils.obj2Json(param.getContent()).toString();
                param.setContent(content);
            }
            if (param.getCoverPics() != null && param.getCoverPics().size() > 0) {
                String coverPic = JSONUtils.list2Json(param.getCoverPics()).toString();
                param.setCover(coverPic);
            }
            resultParam= super.update(param, request, response);
            logger.info("CuriosityViewpointService  insert result "+ JSON.toJSONString(resultParam));
        }catch (Exception e){
            logger.info("CuriosityViewpointService   insert warn");
            e.printStackTrace();
        }
        return resultParam;
    }
    @Override
    public ResultParam insert(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try {
            if(StringUtil.isEmpty(param.getUser_id().toString())){
                return ResultUtil.error(NewsEum. news_80001.getCode(),NewsEum. news_80001.getDesc());
            }
            if(StringUtil.isEmpty(param.getCuriosity_id().toString())){
                return ResultUtil.error(NewsEum. news_80003.getCode(),NewsEum. news_80003.getDesc());
            }
            if(param.getPicts()!=null&& !param.getPicts().equals("")){
                String[] arr = param.getPicts().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                String string =JSONUtils.list2Json(list);
                param.setPicts(string);
            }
            if(param.getCover()!=null&& !param.getCover().equals("")){
                String[] arr = param.getCover().split(","); // 用,分割
                List<String> list = Arrays.asList(arr);
                String string =JSONUtils.list2Json(list);
                param.setCover(string);
            }
            if (param.getPicStrings() != null && param.getPicStrings().size() >0) {
                String pic = JSONUtils.list2Json(param.getPicStrings()).toString();
                param.setPicts(pic);
            }
            if (param.getContent() != null && param.getContent().length() > 0) {
                String content = JSONUtils.obj2Json(param.getContent()).toString();
                param.setContent(content);
            }
            if (param.getCoverPics() != null && param.getCoverPics().size() > 0) {
                String coverPic = JSONUtils.list2Json(param.getCoverPics()).toString();
                param.setCover(coverPic);
            }
            resultParam= super.insert(param, request, response);
            logger.info("CuriosityViewpointService  insert result "+ JSON.toJSONString(resultParam));
            PushMessageParam pushParam=new PushMessageParam();
            if (resultParam.getCode() == 0){
                CuriosityEntity curiosityEntity=new CuriosityEntity();
                curiosityEntity.setId(param.getCuriosity_id());
                List<CuriosityEntity> entities=curiosityMapper.query(curiosityEntity);
                if(entities!=null&&entities.size()>0){
                    pushParam.setTarget_id(entities.get(0).getUser_id());
                    pushParam.setPush_time(new Date());
                    pushParam.setType("curiosity");
                    pushParam.setSend_id(param.getUser_id());
                    pushParam.setType_id(entities.get(0).getId());
                    pushParam.setOpera_type("addViewpoint");
                    pushParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("addNewsMessage", pushParam);
                }
            }
        }catch (Exception e){
            logger.info("CuriosityViewpointService   insert warn");
            e.printStackTrace();
        }
        return resultParam;
    }
    /**
     * 相关推荐
     * zwx
     * 2019年3月27日16:30:42
     */
    @Override
    public List<CuriosityViewpointEntity> queryRecommendationsListPage(CuriosityViewpointEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityViewpointEntity> viewpointEntities=null;
        try {
            CuriosityViewpointEntity curiosityViewpoint=super.findById(param.getId(),request,response);
            curiosityViewpoint.setPageSize(2);
            curiosityViewpoint.setSortName("like_count");
            viewpointEntities=mapper.queryRecommendationsListPage(curiosityViewpoint);
            for (CuriosityViewpointEntity curiosityViewpointEntity : viewpointEntities
                    ) {
                if(curiosityViewpointEntity.getPicts()!=null){
                    List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getPicts(),String.class);
                    if (list != null&&list.size()>0) {
                        curiosityViewpointEntity.setPicStrings(list);
                        curiosityViewpointEntity.setPicts(null);
                    }
                }
                if(curiosityViewpointEntity.getCover()!=null){
                    List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getCover(),String.class);
                    if (list != null&&list.size()>0) {
                        curiosityViewpointEntity.setCoverPics(list);
                        curiosityViewpointEntity.setCover(null);
                    }
                }
            }
        }catch (Exception e){
            logger.warn("CuriosityViewpointService   queryRecommendationsListPage   info");
            e.printStackTrace();
        }
        return viewpointEntities;
    }

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    @Override
    public Integer queryAddViewpointCount(Long user_id) {
        CuriosityViewpointEntity viewpointEntity=new CuriosityViewpointEntity();
        viewpointEntity.setUser_id(user_id);
        Map<String,Object> map = new HashMap<>();
        Integer viewpointCount=mapper.queryAddViewpointCount(viewpointEntity);
        return viewpointCount;
    }

    @Override
    public String queryViewpointById(Long id) {
        return mapper.selectByPrimaryKey(id).toString();
    }
}