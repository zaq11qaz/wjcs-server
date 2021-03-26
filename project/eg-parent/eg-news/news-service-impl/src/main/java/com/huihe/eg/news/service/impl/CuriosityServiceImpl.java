package com.huihe.eg.news.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.news.model.*;
import com.huihe.eg.news.mybatis.dao.*;
import com.huihe.eg.news.service.dao.CuriosityService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.news.service.dao.CuriosityViewpointService;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CuriosityServiceImpl extends BaseFrameworkServiceImpl<CuriosityEntity, Long> implements CuriosityService {

    @Resource
    private CuriosityMapper mapper;
    @Resource
    private CuriosityViewpointService curiosityViewpointService;
    @Resource
    private CuriosityViewpointMapper curiosityViewpointMapper;
    @Resource
    private HistoryMapper historyMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private BrowesHistoryMapper browesHistoryMapper;
    @Resource
    private CuriosityClassfiyMapper classfiyMapper;
    @Resource
    private UserApiService userApiService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Override
    public void init() {
        setMapper(mapper);
    }

    /**
     * 分页查询列表
     * @Description: 分页查询列表
     * @author zwx
     * @Date :2019年2月21日16:09:51
     */
    @Override
    public List<CuriosityEntity> queryListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {
            curiosityEntities=mapper.queryListPage(param);
            for (CuriosityEntity curiosityEntity:curiosityEntities
                    ) {
                Map<String,Object> map = new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityEntity.getUser_id()+"userinfo")));
                CuriosityClassfiyEntity classfiyEntity=classfiyMapper.selectByPrimaryKey(curiosityEntity.getClassfiy());
                map.put("classfiyInfo",classfiyEntity);
                CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpoint.setOper_id(param.getOper_id());
                curiosityViewpoint.setStatus(1);
                curiosityViewpoint.setPageSize(2);
                curiosityViewpoint.setSortName("like_count");
                List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                }
                List<JSONObject> userinfos=new ArrayList<>();
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setPageSize(5);
                curiosityViewpointEntity.setStatus(1);
                List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                for (Integer user_id:integers){
                    String userinfo=redisService.getStr(user_id+"userinfo");
                    if(userinfo!=null&&userinfo!=""){
                        userinfos.add(JSONUtils.obj2Json(userinfo));
                    }
                }
                curiosityEntity.setMap(map);
                curiosityEntity.setViewPointUserInfos(userinfos);
                curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }

    /**
     * 好奇首页
     * @Description: 好奇首页
     * @author zwx
     * @Date : 2019年1月8日
     */
    @Override
    public List<CuriosityEntity> queryHomeListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {

            /*if(param.getCurrentPage()>0&param.getCurrentPage()<6){
                for (int i=1;i<6;i++){
                    CuriosityEntity curiosityEntity=new CuriosityEntity();
                    curiosityEntity.setClassfiy((long)i);
                    curiosityEntity.setStatus(3);
                    List<CuriosityEntity> curiosityServices=mapper.queryListPage(curiosityEntity);
                    curiosityServices.add(0, curiosityServices.remove(param.getPageSize()));
                }
            }else{
                param.setStatus(1);
                param.setSortName("viewpoint_count");
                curiosityEntities=mapper.queryListPage(param);
            }*/
            param.setStatus(1);
           // param.setSortName("viewpoint_count");
            curiosityEntities=mapper.queryListPage(param);
            for (CuriosityEntity curiosityEntity:curiosityEntities
                    ) {
                CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpoint.setOper_id(param.getOper_id());
                curiosityViewpoint.setStatus(1);
                curiosityViewpoint.setPageSize(2);
                curiosityViewpoint.setSortName("like_count");
                List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
                    if(curiosityViewpointEntity.getPicts()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getPicts(),String.class);
                        if (list!=null&&list.size() > 0) {
                            curiosityViewpointEntity.setPicStrings(list);
                            curiosityViewpointEntity.setPicts(null);
                        }
                    }
                    if(curiosityViewpointEntity.getCover()!=null){
                        List<String> list =JSONUtils.json2List(curiosityViewpointEntity.getCover(),String.class);
                        if (list!=null&&list.size() > 0) {
                            curiosityViewpointEntity.setCoverPics(list);
                            curiosityViewpointEntity.setCover(null);
                        }
                    }
                }
                List<JSONObject> userinfos=new ArrayList<>();
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setPageSize(5);
                curiosityViewpointEntity.setStatus(1);
                List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                for (Integer user_id:integers){
                    String userinfo=redisService.getStr(user_id+"userinfo");
                    if(userinfo!=null&&userinfo!=""){
                        userinfos.add(JSONUtils.obj2Json(userinfo));
                    }
                }
                curiosityEntity.setViewPointUserInfos(userinfos);
                curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }

    /**
     * 详情查询
     * @Description: 详情查询
     * @author zwx
     * @Date : 2019年1月7日
     */
    @Override
    public List<CuriosityEntity> query(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<CuriosityEntity> curiosityEntities= null;
        Map<String,Object> map = new HashMap<>();
        try {
            curiosityEntities=mapper.query(param);
            if(curiosityEntities!=null&&curiosityEntities.size()>0){
                CuriosityEntity curiosityEntity=    curiosityEntities.get(0);
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityEntity.getUser_id()+"userinfo")));
                if(param.getOper_id()!=null&&param.getOper_id()!=0){
                    //是否好奇
                    HistoryEntity historyEntityF=new HistoryEntity();
                    historyEntityF.setHistory_id(curiosityEntity.getId());
                    historyEntityF.setOperation_type("curiosity");
                    historyEntityF.setUser_id(param.getOper_id());
                    historyEntityF.setHistory_type("curiosity");
                    List<HistoryEntity> listF = historyMapper.queryListPage(historyEntityF);
                    if (listF != null && listF.size() > 0) {
                        map.put("is_curiosity", true);
                    } else {
                        map.put("is_curiosity", false);
                    }
                }
                //查询标签
                CuriosityClassfiyEntity classfiyEntity=classfiyMapper.selectByPrimaryKey(curiosityEntity.getClassfiy());
                map.put("classfiyInfo",classfiyEntity);
                curiosityEntity.setMap(map);
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setOper_id(param.getOper_id());
                curiosityViewpointEntity.setSortName("like_count");
                curiosityViewpointEntity.setType(param.getType());
                curiosityViewpointEntity.setStatus(1);
                List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointService.queryListPage(curiosityViewpointEntity,request,response);
                map.put("viewpointEntities",viewpointEntities);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity query debug");
        }
        return curiosityEntities;
    }

    /**
     * 查询好奇(我也好奇)内容
     * @param param oper_id
     * @return List<CuriosityEntity>
     *     2019年1月15日
     *     zwx
     */
    @Override
    public List<CuriosityEntity>  queryCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {
            curiosityEntities=mapper.queryCuriosityListPage(param);
            Map<String,Object> map = new HashMap<>();
            if(curiosityEntities!=null&&curiosityEntities.size()>0){
                for (CuriosityEntity curiosityEntity:curiosityEntities
                        ) {
                    //回应
                    CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                    curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpoint.setOper_id(param.getOper_id());
                    curiosityViewpoint.setPageSize(2);
                    curiosityViewpoint.setStatus(1);
                    curiosityViewpoint.setSortName("like_count");
                    List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                    for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                    }
                    //好奇用户数据
                    List<JSONObject> userinfos=new ArrayList<>();
                    CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                    curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpointEntity.setPageSize(5);
                    curiosityViewpointEntity.setStatus(1);
                    List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                    for (Integer user_id:integers){
                        String userinfo=redisService.getStr(user_id+"userinfo");
                        if(userinfo!=null&&userinfo!=""){
                            userinfos.add(JSONUtils.obj2Json(userinfo));
                        }
                    }
                    curiosityEntity.setViewPointUserInfos(userinfos);
                    curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
                    curiosityEntity.setMap(map);
                }
            }else{
                curiosityEntities=queryGuessListPage(param,request,response);

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }
    /**
     * 查询推荐内容
     * @param param oper_id
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity> queryRecommendCuriosityListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= new ArrayList<>();
        try {
            /*BrowesHistoryEntity browesHistoryEntity=new BrowesHistoryEntity();
            browesHistoryEntity.setUser_id(param.getUser_id());
            browesHistoryEntity.setSortName("duration_time");
            browesHistoryEntity.setOrderBy("desc");
            List<BrowesHistoryEntity> browes=browesHistoryMapper.queryListPage(browesHistoryEntity);
            if(browes!=null&&browes.size()>0){
                for (BrowesHistoryEntity browe:browes){
                    Integer nums=browesHistoryMapper.getAllDurationTime(browesHistoryEntity);
                    int proportion=(int)((new BigDecimal((float) browe.getDuration_time() / nums).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
                    int count= param.getPageSize()*proportion/100+1;
                    CuriosityEntity entity=new CuriosityEntity();
                    entity.setClassfiy(browe.getClassfiy());
                    entity.setSortName("curiosity_count");
                    entity.setPageSize(count);
                    entity.setOrderBy("DESC");
                    entity.setCurrentPage(param.getCurrentPage());
                    List<CuriosityEntity> cur=mapper.queryListPage(entity);
                    if(cur!=null&&cur.size()>0){
                        curiosityEntities.addAll(cur);
                    }
                }
            }*/
            curiosityEntities=mapper.queryRecommendCuriosityListPage(param);
            Map<String,Object> map = new HashMap<>();
            if(curiosityEntities!=null&&curiosityEntities.size()>0){
                if(curiosityEntities.size()>10){
                    List<CuriosityEntity> replaceList=curiosityEntities.subList(0,10);
                    curiosityEntities=replaceList;
                }
                for (CuriosityEntity curiosityEntity:curiosityEntities
                        ) {
                    //回应
                    CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                    curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpoint.setOper_id(param.getOper_id());
                    curiosityViewpoint.setPageSize(2);
                    curiosityViewpoint.setStatus(1);
                    curiosityViewpoint.setSortName("like_count");
                    List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                    for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                    }
                    List<JSONObject> userinfos=new ArrayList<>();
                    CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                    curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpointEntity.setPageSize(5);
                    curiosityViewpointEntity.setStatus(1);
                    List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                    for (Integer user_id:integers){
                        String userinfo=redisService.getStr(user_id+"userinfo");
                        if(userinfo!=null&&userinfo!=""){
                            userinfos.add(JSONUtils.obj2Json(userinfo));
                        }
                    }
                    curiosityEntity.setViewPointUserInfos(userinfos);
                    curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
                    curiosityEntity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }

    /**
     * 热门/总排行
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity> queryHotRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {
            curiosityEntities=mapper.queryHotRankingListPage(param);
            Map<String,Object> map = new HashMap<>();
            if(curiosityEntities!=null&&curiosityEntities.size()>0){
                for (CuriosityEntity curiosityEntity:curiosityEntities
                        ) {
                    //回应
                    CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                    curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpoint.setOper_id(param.getOper_id());
                    curiosityViewpoint.setPageSize(2);
                    curiosityViewpoint.setSortName("like_count");
                    curiosityViewpoint.setStatus(1);
                    List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                    for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                    }
                    List<JSONObject> userinfos=new ArrayList<>();
                    CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                    curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                    curiosityViewpointEntity.setPageSize(5);
                    curiosityViewpointEntity.setStatus(1);
                    List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                    for (Integer user_id:integers){
                        String userinfo=redisService.getStr(user_id+"userinfo");
                        if(userinfo!=null&&userinfo!=""){
                            userinfos.add(JSONUtils.obj2Json(userinfo));
                        }
                    }
                    curiosityEntity.setViewPointUserInfos(userinfos);
                    curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
                    curiosityEntity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }
    /**
     * 月排行
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity> queryMonthRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {
            curiosityEntities=mapper.queryMonthRankingListPage(param);
            Map<String,Object> map = new HashMap<>();
            for (CuriosityEntity curiosityEntity:curiosityEntities
                    ) {
                //回应
                CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpoint.setOper_id(param.getOper_id());
                curiosityViewpoint.setPageSize(2);
                curiosityViewpoint.setSortName("like_count");
                curiosityViewpoint.setStatus(1);
                List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                }
                List<JSONObject> userinfos=new ArrayList<>();
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setPageSize(5);
                curiosityViewpointEntity.setStatus(1);
                List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                for (Integer user_id:integers){
                    String userinfo=redisService.getStr(user_id+"userinfo");
                    if(userinfo!=null&&userinfo!=""){
                        userinfos.add(JSONUtils.obj2Json(userinfo));
                    }
                }
                curiosityEntity.setViewPointUserInfos(userinfos);
                curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
                curiosityEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }
    /**
     * 周排行
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity> queryWeekRankingListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities= null;
        try {
            curiosityEntities=mapper.queryWeekRankingListPage(param);
            Map<String,Object> map = new HashMap<>();
            for (CuriosityEntity curiosityEntity:curiosityEntities
                    ) {
                //回应
                CuriosityViewpointEntity curiosityViewpoint=new CuriosityViewpointEntity();
                curiosityViewpoint.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpoint.setOper_id(param.getOper_id());
                curiosityViewpoint.setPageSize(2);
                curiosityViewpoint.setSortName("like_count");
                curiosityViewpoint.setStatus(1);
                List<CuriosityViewpointEntity> viewpointEntities=curiosityViewpointMapper.queryListPage(curiosityViewpoint);
                for (CuriosityViewpointEntity curiosityViewpointEntity:viewpointEntities){
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
                }
                List<JSONObject> userinfos=new ArrayList<>();
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setPageSize(5);
                curiosityViewpointEntity.setStatus(1);
                List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                for (Integer user_id:integers){
                    String userinfo=redisService.getStr(user_id+"userinfo");
                    if(userinfo!=null&&userinfo!=""){
                        userinfos.add(JSONUtils.obj2Json(userinfo));
                    }
                }
                curiosityEntity.setViewPointUserInfos(userinfos);
                curiosityEntity.setCuriosityViewpointEntities(viewpointEntities);
                curiosityEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("Curiosity queryListPage debug");
        }
        return curiosityEntities;
    }

    /**
     * 猜你好奇
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity> queryGuessListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities=null;
        Map<String,Object> map=new HashMap<>();
        try {
            param.setSortName("like_count");
            param.setOrderBy("DESC");
            curiosityEntities=mapper.queryListPage(param);
            if(curiosityEntities==null||curiosityEntities.size()<1){
                param.setCurrentPage(1);
                curiosityEntities=mapper.queryListPage(param);

                map.put("is_null",true);
            }else{
                map.put("is_null",false);
            }
            for (CuriosityEntity curiosityEntity:curiosityEntities){
                if(param.getOper_id()!=null&&param.getOper_id()!=0){
                    //查询是否好奇
                    HistoryEntity historyEntity=new HistoryEntity();
                    historyEntity.setUser_id(param.getOper_id());
                    historyEntity.setHistory_id(curiosityEntity.getId());
                    historyEntity.setOperation_type("curiosity");
                    historyEntity.setHistory_type("curiosity");
                    historyEntity.setStatus(1);
                    List<HistoryEntity> list =historyMapper.queryListPage(historyEntity);
                    if (list != null && list.size() > 0) {
                        map.put("is_curiosity", true);
                    } else {
                        map.put("is_curiosity", false);
                    }
                    curiosityEntity.setMap(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return curiosityEntities;
    }
    /**
     * 内容审核
     * @param param
     * @return List<CuriosityEntity>
     *     2019年1月16日
     *     zwx
     */
    @Override
    public List<CuriosityEntity>  queryAuditListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response){
        List<CuriosityEntity> curiosityEntities=null;
        try {
            curiosityEntities=mapper.queryListPage(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return curiosityEntities;
    }

    /**
     * 分类下好奇
     * @Description: 分类下好奇
     * @author zwx
     * @Date : 2019年2月21日15:02:42
     */
    @Override
    public List<CuriosityEntity> queryCuriostyByClassfiyListPage(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<CuriosityEntity> curiosityEntities=null;
        try {
            param.setSortName("curiosity_count");
            param.setOrderBy("DESC");
            curiosityEntities=mapper.queryListPage(param);
            for (CuriosityEntity curiosityEntity:curiosityEntities ){
                Map<String,Object> map=new HashMap<>();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(curiosityEntity.getUser_id()+"userinfo")));
                List<JSONObject> userinfos=new ArrayList<>();
                CuriosityViewpointEntity curiosityViewpointEntity=new CuriosityViewpointEntity();
                curiosityViewpointEntity.setCuriosity_id(curiosityEntity.getId());
                curiosityViewpointEntity.setPageSize(5);
                curiosityViewpointEntity.setStatus(1);
                List<Integer> integers=curiosityViewpointMapper.queryViewpointUserListPage(curiosityViewpointEntity);
                curiosityEntity.setViewPointUserInfos(userinfos);
                //标签
                CuriosityClassfiyEntity classfiyInfo=classfiyMapper.selectByPrimaryKey (curiosityEntity.getClassfiy());
                map.put("classfiyInfo",classfiyInfo);
                curiosityEntity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return curiosityEntities;
    }
    @Override
    public ResultParam insert(CuriosityEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam=null;
        try{
            resultParam=super.insert(param,request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }

    /**
     * 查询发布数量
     * @param user_id
     * @return
     */
    @Override
    public Integer queryAddCount(Long user_id) {
        CuriosityEntity curiosityEntity=new CuriosityEntity();
        curiosityEntity.setUser_id(user_id);
        Integer curiosCount=mapper.queryAddCount(curiosityEntity);
        return curiosCount;
    }
}
