package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.AuthorizationApiService;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.comm.util.GenSerial;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.model.count.StatisticsParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.impl.tim.TimConfig;
import com.tencentyun.TLSSigAPIv2;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserInfoServiceImpl extends BaseFrameworkServiceImpl<UserInfoEntity, Long> implements UserInfoService {

    @Resource
    private UserInfoMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserLikeMapper userLikeMapper;
    @Resource
    private UserSchoolMapper userSchoolMapper;
    @Resource
    private TimConfig timConfig;
    @Resource
    private UserOrderMapper orderMapper;
    @Resource
    private UserPreferenceMapper userPreferenceMapper;
    @Resource
    private MasterInfoService masterInfoService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private MasterTypeService masterTypeService;
    @Resource
    private UserService userService;
    @Resource
    private AuthorizationApiService authorizationApiService;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }


    @Override
    public ResultParam insert(UserInfoEntity userInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())) {
            userInfoEntity.setNick_name(EmojiParser.parseToHtmlDecimal(userInfoEntity.getNick_name()));
        }
        ResultParam insert = super.insert(userInfoEntity, request, response);
        redisService.set(userInfoEntity.getUser_id() + "userinfo", JSONObject.toJSONString(userInfoEntity));
        return insert;
    }



    @Override
    public Map<String, Object> userCount(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            StatisticsParam statisticsParam = mapper.userStatistics();
            ChartParam chartParam = mapper.queryChart(param);
            List<UserInfoEntity> userInfoEntities = mapper.queryUserOnline(param);
            map = new HashMap<>();
            map.put("statisticsParam", statisticsParam);
            map.put("userOnline", userInfoEntities);
            map.put("chartParam", chartParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void queueUpdateUser(UserInfoEntity userInfoEntity) {
        if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
            userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
            userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
        }
        mapper.queueUpdateUser(userInfoEntity);
    }

    @Override
    public UserInfoEntity findById(Long aLong, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userInfoEntity = super.findById(aLong, request, response);
        if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
            userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
            userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
        }
        return userInfoEntity;
    }

    @Override
    public List<UserInfoEntity> queryUserListPage(UserInfoEntity userInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> list = null;
        try {
            if (userInfoEntity.getSearchParam() != null) {
                if (StringUtil.isNotEmpty(userInfoEntity.getSearchParam().getSearch_type())) {
                    if ("company".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        list = mapper.queryColleagueListPage(userInfoEntity);
                    } else if ("school".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        list = mapper.querySchoolmateListPage(userInfoEntity);
                    } else if ("contacts".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        list = mapper.queryContactsListPage(userInfoEntity);
                    } else if ("nearby".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        list = mapper.queryNearbyListPage(userInfoEntity);
                    } else if ("recommend".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        /*if(userInfoEntity.getUserPreferenceEntity()!=null){
                            UserPreferenceEntity entity=userInfoEntity.getUserPreferenceEntity();
                            entity.setPageSize(1);
                            List<UserPreferenceEntity> entities=userPreferenceMapper.query(entity);
                            if(entities!=null&&entities.size()>0){
                                entity=entities.get(0);
                                if(entity.getType().contains("社交")){
                                    if(entity.getPreference().equalsIgnoreCase(""));
                                }
                            }
                        }*/
                        userInfoEntity.setIs_robot(true);
                        list = mapper.queryUserListPage(userInfoEntity);
                    } else if ("master".equals(userInfoEntity.getSearchParam().getSearch_type())) {
                        userInfoEntity.setIs_help(true);
                        list = mapper.queryMasterListPage(userInfoEntity);
                        for (UserInfoEntity entity : list) {
                            Map<String, Object> map = new HashMap<>();
                            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                            masterInfoEntity.setUser_id(entity.getUser_id());
                            masterInfoEntity.setType(entity.getTeacher_type());
                            masterInfoEntity.setPageSize(1);
                            List<MasterInfoEntity> masterInfoEntities = masterInfoService.queryListPage(masterInfoEntity, request, response);
                            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                                map.put("masterinfo", masterInfoEntities.get(0));
                                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                                userAppointmentEntity.setMaster_id(masterInfoEntities.get(0).getId());
                                userAppointmentEntity.setStatus(3);
                                Integer classCount = userAppointmentService.queryListPageCount(userAppointmentEntity, request, response);
                                map.put("classCount", classCount);
                                Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                                map.put("studentCount", studentCount);
                                MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                                masterInfoEntity.setPageSize(1);
                                masterInfoEntity.setType(masterInfoEntities.get(0).getType());
                                List<MasterTypeEntity> masterTypeEntities = masterTypeService.queryListPage(masterTypeEntity, request, response);
                                map.put("masterprice", masterTypeEntities.get(0).getPrice());
                            }
                            entity.setMap(map);
                        }
                    }
                } else {
                    list = mapper.queryUserListPage(userInfoEntity);
                }
            } else {
                list = mapper.queryUserListPage(userInfoEntity);
            }
            if (list != null && list.size() > 0) {
                for (UserInfoEntity userInfoEntity1 : list) {
                    setNick(userInfoEntity1);
                    userInfoEntity1.setSearchParam(userInfoEntity.getSearchParam());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setNick(UserInfoEntity userInfoEntity1) {
        EmojiUtil.resolveToEmojiFromByte(userInfoEntity1.getNick_name());
    }

    @Override
    public ResultParam update(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(param.getNick_name())) {
            param.setNick_name(EmojiParser.parseToHtmlDecimal(param.getNick_name()));
        }
        ResultParam resultParam = null;
        try {
            resultParam = super.update(param, request, response);
            if (resultParam.getCode() == 0) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                List<UserInfoEntity> userInfoEntities = mapper.queryListPage(userInfoEntity);
                if (userInfoEntities != null && userInfoEntities.size() > 0) {
                    userInfoEntity = userInfoEntities.get(0);
                    redisService.set(param.getUser_id() + "userinfo", JSONObject.toJSONString(userInfoEntity));
                } else {
                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                }
            } else {
                return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("userinfoserviceimpl  update ");
        }
        return ResultUtil.success();
    }

    /**
     * 用户详情
     *
     * @param param
     * @param request
     * @param response
     * @return zwx
     * 2019年4月3日19:47:08
     */
    @Override
    public ResultParam queryUserInfoById(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userInfoEntity = null;
        List<UserLikeEntity> userLikeEntities = null;
        List<UserSchoolEntity> userSchoolEntities = null;
        Map<String, Object> map = new HashMap<>();
        try {
            param.setPageSize(1);
            List<UserInfoEntity> list = mapper.queryUserListPage(param);
            if (list != null && list.size() > 0) {
                userInfoEntity = list.get(0);
                setNick(userInfoEntity);
                UserLikeEntity userLikeEntity = new UserLikeEntity();
                userLikeEntity.setUser_id(param.getOper_id());
                userLikeEntity.setOther_user_id(param.getUser_id());
                userLikeEntities = userLikeMapper.query(userLikeEntity);
                //查询同学
                UserSchoolEntity userSchoolEntity = new UserSchoolEntity();
                userSchoolEntity.setUser_id(param.getUser_id());
                userSchoolEntity.setOper_id(param.getOper_id());
                userSchoolEntities = userSchoolMapper.queryIsClassmate(userSchoolEntity);
            }
            if (userInfoEntity != null &&StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
                userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
            }
            map.put("userInfoEntity", userInfoEntity);
            if (userLikeEntities != null && userLikeEntities.size() > 0) {
                map.put("is_like", userLikeEntities.get(0).getIs_like());
            } else {
                map.put("is_like", false);
            }
            map.put("is_classmate", userSchoolEntities != null && userSchoolEntities.size() > 0);
            //礼物
            UserOrderEntity entity = new UserOrderEntity();
            entity.setType("gift");
            entity.setSource("user");
            entity.setPayment_id(param.getUser_id());
            Integer gift_count = orderMapper.queryListPageCount(entity);
            map.put("gift_count", gift_count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("userinfoserviceimpl  queryUserInfoById ");
        }
        return ResultUtil.success(map);
    }

    @Override
    public void queryUserinfoInsertRedis(UserInfoEntity param) {
        List<UserInfoEntity> userInfoEntities = mapper.query(param);
        try {
            for (UserInfoEntity userInfoEntity1 : userInfoEntities) {
                userInfoEntity1.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity1.getNick_name()));
                String userinfo = redisService.getStr(userInfoEntity1.getUser_id() + "userinfo");
                if (null == userinfo || "".equals(userinfo)) {
                    redisService.set(userInfoEntity1.getUser_id() + "userinfo", JSONObject.toJSONString(userInfoEntity1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl   queryUserinfoInsertRedis");
        }
    }


    @Override
    public List<UserInfoEntity> queryFriendInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> userInfoEntities = Lists.newArrayList();
        try {
            if (param.getUser_idList() != null && param.getUser_idList().size() > 0) {
                for (Long user_id : param.getUser_idList()) {
                    UserInfoEntity infoEntity = super.findById(user_id, request, response);
                    if (infoEntity != null) {
                        userInfoEntities.add(infoEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl  queryFriendInfo");
        }
        return userInfoEntities;
    }

    //TIM导入
    @Override
    public int TIMregister(UserInfoEntity base) {
        String str = "";
        String url = "";
        Map<String, Object> map = new HashMap<>();
        try {
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
            String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());
            UUID uuid = UUID.randomUUID();
            if (base.getUser_idList() != null && base.getUser_idList().size() > 0) {
                //批量注册
                List<String> list = new ArrayList<>();
                List<Long> longs = base.getUser_idList();
                for (Long user_id : longs) {
                    list.add(user_id.toString());
                }
                map.put("Accounts", list);

                url = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?usersig=" + adminSign + "&identifier=" + timConfig.getAdminName() + "&sdkappid=" + timConfig.getSdkAppId() + "&random=" + uuid + "&contenttype=" + timConfig.getContenttype();
            } else {
                //单个注册
                url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig=" + adminSign + "&identifier=" + timConfig.getAdminName() + "&sdkappid=" + timConfig.getSdkAppId() + "&random=" + uuid + "&contenttype=" + timConfig.getContenttype();
                String st = String.valueOf(base.getUser_id());
                // ipushAssistantTaskMessage(st);
                map.put("Identifier", st);
            }
            url = url.replaceAll("\r|\n", "");
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
            System.out.println(json);
            str = HttpsClientRequest.post(url, json.toString(), null, null);
            System.out.println(JSONUtils.obj2Json(str));
            Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
            // System.out.println(JSONUtils.obj2Json(resultMap));
            String actionStatus = resultMap.get("ActionStatus").toString();
            if ("OK".equalsIgnoreCase(actionStatus)) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 批量导入
     */
    @Override
    public void batchImportUserid() {
        /*System.out.println(1);//删除
        for (int i=1;i<=10000;i++){

            List<Map<String, Object>> inte=new ArrayList<>();
            Map<String, Object> Map=new HashMap<>();
            Map.put("UserID",i+"");
            inte.add(Map);

            Map<String, Object> resultMap=new HashMap<>();
            resultMap.put("DeleteItem",inte);
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
            String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());
            UUID uuid = UUID.randomUUID();
            String url = "https://console.tim.qq.com/v4/im_open_login_svc/account_delete?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                    timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(resultMap);
            url = url.replaceAll("\r|\n", "");
            String str = HttpsClientRequest.post(url, json.toString(), null, null);
            Map<String, Object> result = JSONUtils.obj2Map(str, null);
            System.out.println(result.toString());

        }*/
        /*for (int i = 1;;i++){
            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setPageSize(100);
            infoEntity.setCurrentPage(i);
            List<Long> integers = mapper.queryAllUserid(infoEntity);
            if(integers!=null&&integers.size()>0){
                infoEntity.setUser_idList(integers);
                System.out.println(integers.toString());
                Integer count = TIMregister(infoEntity);
                System.out.println(count);
            }else{
                System.out.println("结束了");
                break;
            }
        }*/
    }

    @Override
    public void updatetimUserInfo(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        List<UserInfoEntity> userInfoEntities = mapper.query(userInfoEntity);
        if (param.getUser_id() != null && param.getUser_id() != 0) {
            UserInfoEntity infoEntity = super.findById(param.getUser_id(), request, response);
            Map<String, Object> map = new HashMap<>();
            map.put("From_Account", param.getUser_id().toString());
            List<Map<String, Object>> maps = new ArrayList<>();
            Map<String, Object> mapAvatar = new HashMap<>();
            mapAvatar.put("Tag", "Tag_Profile_IM_Nick");//昵称
            mapAvatar.put("Value", infoEntity.getNick_name());
            maps.add(mapAvatar);
            Map<String, Object> mapSex = new HashMap<>();
            mapSex.put("Tag", "Tag_Profile_IM_Gender");
            if (infoEntity.getSex() == 1) {//性别
                mapSex.put("Value", "Gender_Type_Male");
            } else if (infoEntity.getSex() == 2) {
                mapSex.put("Value", "Gender_Type_Female");
            } else {
                mapSex.put("Value", "Gender_Type_Unknown");
            }
            maps.add(mapSex);
            Map<String, Object> mapBirth = new HashMap<>();
            mapBirth.put("Tag", "Tag_Profile_IM_Image");//头像
            mapBirth.put("Value", infoEntity.getAvatar());
            maps.add(mapBirth);
            map.put("ProfileItem", maps);
            String str = null;
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
            String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());
            UUID uuid = UUID.randomUUID();
            String url = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                    timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
            url = url.replaceAll("\r|\n", "");
            str = HttpsClientRequest.post(url, json.toString(), null, null);
            Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
            System.out.println("------resultMap:" + resultMap.toString());
            String actionStatus = resultMap.get("ActionStatus").toString();
            System.out.println("------actionStatus:" + actionStatus);
            logger.info(url);
        } else {
            for (UserInfoEntity entity : userInfoEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("From_Account", entity.getUser_id().toString());
                List<Map<String, Object>> maps = new ArrayList<>();
                Map<String, Object> mapAvatar = new HashMap<>();
                mapAvatar.put("Tag", "Tag_Profile_IM_Nick");//昵称
                mapAvatar.put("Value", entity.getNick_name());
                maps.add(mapAvatar);
                Map<String, Object> mapSex = new HashMap<>();
                mapSex.put("Tag", "Tag_Profile_IM_Gender");
                if (entity.getSex() == 1) {//性别
                    mapSex.put("Value", "Gender_Type_Male");
                } else if (entity.getSex() == 2) {
                    mapSex.put("Value", "Gender_Type_Female");
                } else {
                    mapSex.put("Value", "Gender_Type_Unknown");
                }
                maps.add(mapSex);
                Map<String, Object> mapBirth = new HashMap<>();
                mapBirth.put("Tag", "Tag_Profile_IM_Image");//头像
                mapBirth.put("Value", entity.getAvatar());
                maps.add(mapBirth);
                map.put("ProfileItem", maps);
                String str = null;
                TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());
                UUID uuid = UUID.randomUUID();
                String url = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                        timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
                System.out.println(json);
                url = url.replaceAll("\r|\n", "");
                str = HttpsClientRequest.post(url, json.toString(), null, null);
                Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
                String actionStatus = resultMap.get("ActionStatus").toString();
                System.out.println("------actionStatus:" + actionStatus);
                logger.info(url);
            }

        }
    }

    @Override
    public List<UserInfoEntity> query(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> userInfoEntities = null;
        try {
            userInfoEntities = mapper.query(param);
            if (userInfoEntities != null && userInfoEntities.size() > 0) {
                TLSSigAPIv2 tlsSigAPIv2 = null;
                for (UserInfoEntity entity : userInfoEntities) {
                    setNick(entity);
                    String sign = redisService.getStr("usersign" + entity.getUser_id());
                    if (StringUtil.isNotEmpty(sign)) {
                        entity.setSign(sign);
                    } else {
                        tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire());
                        redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
                        entity.setSign(sign);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl     query");
        }
        return userInfoEntities;
    }

    @Override
    public List<UserInfoEntity> queryListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> userInfoEntities = null;
        try {
            userInfoEntities = mapper.queryListPage(param);
            if (userInfoEntities != null && userInfoEntities.size() > 0) {
                TLSSigAPIv2 tlsSigAPIv2 = null;
                for (UserInfoEntity entity : userInfoEntities) {
                    setNick(entity);
                    Map<String, Object> map = new HashMap<>();
                    /*
                    if (entity.getAdmin_id() != null && entity.getAdmin_id() != 0) {//若admin_Id不为空
                        String managerInfo = authorizationApiService.queryManager(entity.getAdmin_id());//查询admin信息
                        map.put("managerInfo", JSONUtils.obj2Json(managerInfo));
                    }

                     */
                    //查询redis信息
                    String sign = redisService.getStr("usersign" + entity.getUser_id());
                    if (StringUtil.isNotEmpty(sign)) {
                        entity.setSign(sign);//如果有 设置sign
                    } else {
                        tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire());
                        redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
                        entity.setSign(userSign);
                    }
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl     queryListPage");
        }
        return userInfoEntities;
    }

    /**
     * 管理分配用户
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam updateUserBatch(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getUser_idList() != null && param.getUser_idList().size() > 0) {
                for (Long aLong : param.getUser_idList()) {
                    param.setUser_id(aLong);
                    mapper.updateByPrimaryKeySelective(param);
                }
            } else {
                return super.update(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl    updateUserBatch");
        }
        return ResultUtil.success();
    }

    @Override
    public List<UserInfoEntity> queryNewListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> userInfoEntities = null;
        try {
            userInfoEntities = mapper.queryListPage(param);
            if (userInfoEntities != null && userInfoEntities.size() > 0) {
                TLSSigAPIv2 tlsSigAPIv2 = null;
                for (UserInfoEntity entity : userInfoEntities) {
                    setNick(entity);
                    String sign = redisService.getStr("usersign" + entity.getUser_id());
                    if (StringUtil.isNotEmpty(sign)) {
                        entity.setSign(sign);
                    } else {
                        tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire());
                        redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
                        entity.setSign(sign);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl     queryNewListPage");
        }
        return userInfoEntities;
    }

    /**
     * 后台机器人用户
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserInfoEntity> queryBackstage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        String[] country = {"中国", "美国", "英国", "法国", "意大利", "日本", "韩国", "澳大利亚", "葡萄牙", "西班牙"};
        List<UserInfoEntity> userInfoEntities = Lists.newArrayList();
        try {
            TLSSigAPIv2 tlsSigAPIv2 = null;
            for (String string : country) {
                param.setCountry(string);
                param.setIs_robot(true);
                List<UserInfoEntity> userInfoEntities1 = mapper.queryUserListPage(param);
                if (userInfoEntities.size() > 0) {
                    for (UserInfoEntity entity : userInfoEntities1) {
                        String sign = redisService.getStr("usersign" + entity.getUser_id());
                        if (StringUtil.isNotEmpty(sign)) {
                            entity.setSign(sign);
                        } else {
                            tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                            String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire());
                            redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
                            entity.setSign(sign);
                        }
                    }
                }
                userInfoEntities.addAll(userInfoEntities1);
            }
            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setAdvertising_position(true);
            List<UserInfoEntity> userInfoEntities1 = mapper.queryUserListPage(infoEntity);
            for (UserInfoEntity entity : userInfoEntities1) {
                String sign = redisService.getStr("usersign" + entity.getUser_id());
                if (StringUtil.isNotEmpty(sign)) {
                    entity.setSign(sign);
                } else {
                    tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                    String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire());
                    redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
                    entity.setSign(sign);
                }
            }
            userInfoEntities.addAll(userInfoEntities1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl     queryBackstage");
        }
        return userInfoEntities;
    }

    /**
     * 在线用户统计
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserInfoEntity> queryUserOnline(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> userInfoEntities = null;
        try {
            userInfoEntities = mapper.queryUserOnline(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl  queryUserOnline");
        }
        return userInfoEntities;
    }

    /**
     * 会员管理注册信息
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String, Object> queryMemberListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<UserInfoEntity> userInfoEntities = mapper.queryMemberListPage(param);
            Integer integer = mapper.queryMemberListPageCount(param);
            map.put("userMemberInfo", userInfoEntities);
            map.put("userMemberInfoCount", integer);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserInfoServiceImpl  queryMemberListPage");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getFull_name() != null) {
                List<Long> list1 = masterInfoMapper.queryIdByFullName(param.getFull_name());
                if (list1 != null && list1.size() > 0) {
                    param.setIds(list1);
                }
            }
            if (param.getLogin_name() != null) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null && list1.size() > 0) {
                    param.setLoginIDs(list1);
                }
            }
            List<UserInfoEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询每个助学师
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserInfoEntity> queryEachMaster(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(param);
            for (UserInfoEntity userInfoEntity : list) {
                setNick(userInfoEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(userInfoEntity.getUser_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/11 11:09
     * @ Description：根据邀请码加钱
     * @since: JDk1.8
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddInviteCash(Long user_id, Long invite_id, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfoEntity inviteUser = mapper.selectByPrimaryKey(invite_id);
            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey((long) inviteUser.getRole_id());
            Integer registr_num = inviteUser.getRegistr_num();
            int num = (registr_num - 1) / 10;
            BigDecimal cash;
            switch (num) {
                case 0:
                    cash = userEarnRoleEntity.getEach_master_usa();
                    break;
                case 1:
                    cash = userEarnRoleEntity.getEach_master_newzealand_canada_australia();
                    break;
                case 2:
                case 3:
                case 4:
                    cash = userEarnRoleEntity.getEach_master_britain();
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    cash = userEarnRoleEntity.getEach_master_southafrica_europe();
                    break;
                default:
                    cash = userEarnRoleEntity.getEach_master_else();
            }
            inviteUser.setCash(inviteUser.getCash().add(cash));
            this.updateEarn(inviteUser, user_id, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    private void updateEarn(UserInfoEntity userInfoEntity, Long user_id, HttpServletRequest request, HttpServletResponse response) {
        //存日志
        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
        userRecommenderIncomeLogEntity.setCash(userInfoEntity.getCash());
        userRecommenderIncomeLogEntity.setType("user");
        userRecommenderIncomeLogEntity.setCash_describe("邀请用户");
        userRecommenderIncomeLogEntity.setUser_id(userInfoEntity.getUser_id());
        userRecommenderIncomeLogEntity.setInvitation_id(user_id);
        userRecommenderIncomeLogEntity.setRole_id(userInfoEntity.getRole_id());
        ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
        if (insert.getCode() == 0) {//不存在
            //更新账户余额
            mapper.updateCash(userInfoEntity);
            mapper.updateRegistrNum(userInfoEntity);
        }
    }

    @Override
    public Object updateInviteCode(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> query = mapper.query(param);
        for (UserInfoEntity userInfoEntity : query) {
            if (StringUtil.isEmpty(userInfoEntity.getInvite_code())) {
                String inviteCode = getCode(userInfoEntity.getUser_id());
                userInfoEntity.setInvite_code(inviteCode);
                mapper.updateInvateCode(userInfoEntity);
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean checkIsCanPay(Long user_id, BigDecimal bigDecimal) {
        try {
            UserInfoEntity userInfoEntity = mapper.selectByPrimaryKey(user_id);
            if (userInfoEntity.getCash().compareTo(bigDecimal) >= 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    @Override
    public Map<String, Object> queryHelperInfoListPage(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<UserInfoEntity> userInfoEntities = mapper.queryListPage(param);
            Integer integer = mapper.queryListPageCount(param);
            if (integer > 0) {
                for (UserInfoEntity userInfoEntity : userInfoEntities) {
                    setNick(userInfoEntity);
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(userInfoEntity.getUser_id());
                    List<MasterInfoEntity> query = masterInfoMapper.query(masterInfoEntity);
                    if (query != null && query.size() > 0) {
                        HashMap<String, Object> map1 = Maps.newHashMap();
                        map1.put("masterInfoList", query);
                        userInfoEntity.setMap(map1);
                    }
                }
            }
            map.put("rows", userInfoEntities);
            map.put("total", integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Object deleteUserSign(Long id, HttpServletRequest request, HttpServletResponse response) {
        String sign = redisService.getStr("usersign" + id);
        if (StringUtil.isNotEmpty(sign)) {
            redisService.del("usersign" + id);
        }
        return redisService.getStr("usersign" + id);
    }

    private String getCode(Long user_id) {
        return GenSerial.generateNewCode(8, user_id.intValue(), 5, 2);
    }


    @Override
    public List<UserInfoEntity> queryMechanismMasterList(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfoEntity> list = Lists.newArrayList();
        try {
            param.setIs_help(true);
            List<UserInfoEntity> list1 = mapper.queryInfoList(param);
            if (list1 != null && list1.size() > 0) {
                for (UserInfoEntity userInfoEntity : list1) {
                    Map<String, Object> map = Maps.newHashMap();
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMaster_id(userInfoEntity.getUser_id());
                    Integer studentCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                    map.put("studentCount", studentCount);

                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setMaster_id(userInfoEntity.getUser_id());
                    Integer courseCount = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                    map.put("courseCount", courseCount);

                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(userInfoEntity.getUser_id());
                    masterInfoEntity.setStatus(2);
                    masterInfoEntity.setMechanism_id(userInfoEntity.getAdmin_id());
                    masterInfoEntity.setPageSize(10);
                    List<MasterInfoEntity> masterList = masterInfoMapper.queryListPage(masterInfoEntity);
                    boolean flag = false;
                    if (masterList != null && masterList.size() > 0) {
                        map.put("masterInfoList", masterList);
                        map.put("score", masterList.get(0).getScore());
                        for (MasterInfoEntity infoEntity : masterList) {
                            if ("single".equals(param.getTeacher_type())) {
                                if ("major".equals(infoEntity.getType()) || "cross_border".equals(infoEntity.getType())
                                        || "mother_tongue".equals(infoEntity.getType())) {
                                    flag = true;
                                }
                            }
                            if ((infoEntity.getType()).equals(param.getTeacher_type())) {
                                flag = true;
                            }
                        }
                    }
                    userInfoEntity.setMap(map);
                    if (flag) {
                        list.add(userInfoEntity);
                    }else if (param.getTeacher_type()==null && (masterList != null && masterList.size() > 0)){
                        list.add(userInfoEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateUserPoint(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        if (rechargeRecordEntity.getPoints()!=0){
            UserInfoEntity userInfoEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getUser_id());
            userInfoEntity.setPoints(rechargeRecordEntity.getPoints());
            mapper.updateSubPoint(userInfoEntity);
        }
    }

    @Override
    public Map<String, Object> queryTeachPayUserStatistics(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();
        try {
            UserInfoEntity userInfoEntity = mapper.selectByPrimaryKey(param.getUser_id());
            map.put("points", userInfoEntity.getPoints());

            UserCouponEntity userCouponEntity = new UserCouponEntity();
            userCouponEntity.setUser_id(userInfoEntity.getUser_id());
            userCouponEntity.setStatus(1);
            Integer coupNum = userCouponMapper.queryListPageCount(userCouponEntity);
            map.put("coupNum", coupNum);

            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setUser_id(userInfoEntity.getUser_id());
            rechargeRecordEntity.setFinished(true);
            Integer rechargeCount = rechargeRecordMapper.queryListPageCount(rechargeRecordEntity);
            map.put("rechargeCount", rechargeCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void deleteRedisById(UserInfoEntity param) {
        redisService.del(param.getUser_id()+"userinfo");
        redisService.del("usersign"+param.getUser_id());
    }
}
