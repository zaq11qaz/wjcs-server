package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterInfoService;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.dao.UserRecommenderIncomeLogService;
import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.impl.mail.MailService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import com.huihe.eg.user.service.impl.tim.TimConfig;
import com.tencentyun.TLSSigAPIv2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MasterInfoServiceImpl extends BaseFrameworkServiceImpl<MasterInfoEntity, Long> implements MasterInfoService {

    @Resource
    private MasterInfoMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private TimConfig timConfig;
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private MasterTypeMapper masterTypeMapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private SmsService smsService;
    @Resource
    private MailService mailService;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Map<String, Object> queryManagerListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", super.queryListPageCount(masterInfoEntity, request, response));
        List<MasterInfoEntity> masterInfoEntities = super.queryListPage(masterInfoEntity, request, response);
        for (MasterInfoEntity infoEntity : masterInfoEntities) {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUser_id(infoEntity.getUser_id());
            userInfoEntity.setPageSize(1);
            List<UserInfoEntity> query = userInfoMapper.queryListPage(userInfoEntity);
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("userInfo", query);
            userInfoEntity = query.get(0);
            if (infoEntity.getMechanism_id() != 0) {
                MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(infoEntity.getMechanism_id());
                map1.put("mechanismEntity", mechanismEntity);
            }
            if (userInfoEntity.getAdmin_id() != 0) {
                MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userInfoEntity.getAdmin_id());
                map1.put("mechanismEntity", mechanismEntity);
            }
            infoEntity.setMap(map1);
        }
        map.put("rows", masterInfoEntities);
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (param.getLogin_name() != null) {
                List<Long> list1 = userMapper.queryIdByLoginName(param.getLogin_name());
                if (list1 != null && list1.size() > 0) {
                    param.setLoginIDs(list1);
                }
            }
            if (StringUtil.isNotEmpty(param.getMechanism_name())){
                MasterMechanismEntity mechanismEntity = new MasterMechanismEntity();
                mechanismEntity.setPageSize(1);
                mechanismEntity.setMechanism_name(param.getMechanism_name());
                List<MasterMechanismEntity> list = masterMechanismMapper.queryListPage(mechanismEntity);
                if (list!=null&&list.size()>0){
                    param.setMechanism_id(list.get(0).getId());
                }
            }
            List<MasterInfoEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                if (list != null && list.size() > 0) {
                    this.setUserInfo(list, request, response);
                }
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    private void setUserInfo(List<MasterInfoEntity> list, HttpServletRequest request, HttpServletResponse response) {
        for (MasterInfoEntity infoEntity : list) {
            Map<String, Object> map = new HashMap<>();
            UserInfoEntity userInfoEntity = userInfoService.findById(infoEntity.getUser_id(), request, response);
            map.put("userInfoEntity", userInfoEntity);
            map.put("userEntity", userMapper.selectByPrimaryKey(infoEntity.getUser_id()));
            infoEntity.setMap(map);
        }
    }

    @Override
    public ResultParam insert(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        MasterInfoEntity entity = new MasterInfoEntity();
        entity.setUser_id(param.getUser_id());
        entity.setType(param.getType());
        List<MasterInfoEntity> list = super.query(entity, request, response);
        if (list != null && list.size() > 0) {
            param.setId(list.get(0).getId());
            param.setStatus(1);
            param.setRefuse_contect("");
            return super.update(param, request, response);
        }
        param.setInvitees_id(param.getInvitees_id());
        return super.insert(param, request, response);
    }

    /**
     * ??????
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam masterAudit(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(param);
            if (ret > 0) {
                //???????????????
                MasterInfoEntity masterInfoEntity1 = mapper.selectByPrimaryKey(param.getId());
                /**
                 * ???????????????????????????
                 * true ??????????????????
                 */
                //?????????id??????0
                if (masterInfoEntity1.getMechanism_id() != null && masterInfoEntity1.getMechanism_id() != 0) {
                    //?????????
                    MasterMechanismEntity masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterInfoEntity1.getMechanism_id());
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    //????????????id
                    pushMessageParam.setTarget_id(masterMechanismEntity.getUser_id());
                    //????????????
                    pushMessageParam.setType("mechanisMasterAuth");
                    //??????????????????
                    pushMessageParam.setOpera_type("mechanisMasterAuth");
                    pushMessageParam.setType_id(param.getId());
                    //????????????
                    pushMessageParam.setTitle("?????????????????????");
                    //??????
                    if (param.getStatus() == 2) {
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setIs_help(true);
                        infoEntity.setUser_id(masterInfoEntity1.getUser_id());
                        param.setRefuse_contect("");
                        //????????????????????? true
                        userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                        pushMessageParam.setContent("??????????????????????????????");

                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(masterInfoEntity1.getUser_id());
                        masterInfoEntity.setType("live_lecturer");
                        masterInfoEntity.setFull_name(masterInfoEntity1.getFull_name());
                        masterInfoEntity.setTeach_language(masterInfoEntity1.getTeach_language());
                        masterInfoEntity.setTeach_language_url(masterInfoEntity1.getTeach_language_url());
                        masterInfoEntity.setLanguage_url(masterInfoEntity1.getLanguage_url());
                        masterInfoEntity.setPageSize(1);
                        List<MasterInfoEntity> infoEntities = mapper.queryList(masterInfoEntity);
                        if (infoEntities != null && infoEntities.size() > 0) {
                            masterInfoEntity.setStatus(2);
                            masterInfoEntity.setMechanism_id(masterInfoEntity1.getMechanism_id());
                            mapper.updateByPrimaryKeySelective(masterInfoEntity);
                        } else {
                            if (StringUtil.isNotEmpty(masterMechanismEntity.getRecommender_id())) {
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                                UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterMechanismEntity.getRecommender_id());
                                pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
                                pushMessageParam.setContent("??????????????????????????????????????????");
                                this.updateEarn(masterMechanismEntity, masterInfoEntity, userRecommenderEntity, request, response);
                            }
                            masterInfoEntity.setStatus(2);
                            masterInfoEntity.setMechanism_id(masterInfoEntity1.getMechanism_id());
                            mapper.insertSelective(masterInfoEntity);
                        }
                        mapper.updateMechanismMasterStatus(masterInfoEntity1);
                    } else {
                        mapper.updateMechanismMasterStatus3(masterInfoEntity1);
                        pushMessageParam.setContent("??????????????????????????????");
                    }
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                    return ResultUtil.success(map);
                } else {
                    // ?????????????????????
                    MasterInfoEntity entity = super.findById(param.getId(), request, response);
                    //??????????????????
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(entity.getUser_id());
                    map = JSONUtils.generatorMap("????????????", true);
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    //????????????id
                    pushMessageParam.setTarget_id(entity.getUser_id());
                    //????????????
                    pushMessageParam.setType("updateAuth");
                    //??????????????????
                    pushMessageParam.setOpera_type("masterAuthentication");
                    //????????????
                    pushMessageParam.setTitle("?????????????????????");
                    pushMessageParam.setType_id(param.getId());
                    //??????
                    if (param.getStatus() == 2) {
                        infoEntity.setIs_help(true);
                        //????????????????????? true
                        userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                        entity.setRefuse_contect("");
                        if ("private_education".equals(entity.getType())) {
                            //??????????????????
                            this.insertMasterSetPrice(entity);
                        }
                        //????????????
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(entity.getUser_id());
                        masterInfoEntity.setType("live_lecturer");
                        masterInfoEntity.setFull_name(entity.getFull_name());
                        masterInfoEntity.setPageSize(1);
                        List<MasterInfoEntity> infoEntities = mapper.queryList(masterInfoEntity);
                        if (infoEntities != null && infoEntities.size() > 0) {
                            masterInfoEntity.setStatus(2);
                            masterInfoEntity.setMechanism_id(entity.getMechanism_id());
                            mapper.updateByPrimaryKeySelective(masterInfoEntity);
                        } else {
                            if (StringUtil.isNotEmpty(entity.getInvitees_id())) {
                                masterInfoEntity.setInvitees_id(entity.getInvitees_id());
                            }
                            masterInfoEntity.setStatus(2);
                            if (entity.getMechanism_id() != null) {
                                masterInfoEntity.setMechanism_id(entity.getMechanism_id());
                            }
                            mapper.insertSelective(masterInfoEntity);
                        }
                        pushMessageParam.setContent("????????????????????????");
                        if (StringUtil.isNotEmpty(entity.getInvitees_id())) {
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                            UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(entity.getInvitees_id());
                            this.updateEarn(entity, userRecommenderEntity, request, response);
                            pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
                            pushMessageParam.setContent("??????????????????: " + entity.getFull_name() + "???????????????");
                            if (entity.getMobile() == null || "".equals(entity.getMobile())) {
                                mailService.sendPassMail(entity.getMail(), pushMessageParam.getContent());
                            } else {
                                smsService.sendPassSms(entity.getMobile(), pushMessageParam.getContent());
                            }
                        }
                    } else {
                        if (StringUtil.isNotEmpty(entity.getInvitees_id())) {
                            pushMessageParam.setContent("????????????????????????");
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                            pushMessageParam.setTarget_id(userRecommenderMapper.queryInvateCode(entity.getInvitees_id()).getUser_id());
                            pushMessageParam.setContent("??????????????????: " + entity.getFull_name() + "???????????????");
                            if (entity.getMobile() == null || "".equals(entity.getMobile())) {
                                mailService.sendPassMail(entity.getMail(), pushMessageParam.getContent());
                            } else {
                                smsService.sendPassSms(entity.getMobile(), pushMessageParam.getContent());
                            }
                        } else {
                            pushMessageParam.setContent("????????????????????????");
                        }
                    }
                    /**
                     * ???????????????
                     */
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                }
            } else {
                map = JSONUtils.generatorMap("????????????", false);
            }
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }


    private void updateEarn(MasterInfoEntity entity, UserRecommenderEntity userRecommenderEntity, HttpServletRequest request, HttpServletResponse response) {
        //?????????
        if ("master_recommender".equals(userRecommenderEntity.getType())) {
            //??????
            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
            BigDecimal earn = this.getEarnCountry(entity.getNationality(), userEarnRoleEntity);
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            //??????
            userRecommenderIncomeLogEntity.setCash(earn);
            //???????????????id
            userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
            //????????????id
            userRecommenderIncomeLogEntity.setMaster_id(entity.getId());
            userRecommenderIncomeLogEntity.setType("master");
            userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
            userRecommenderIncomeLogEntity.setRecommender_type("master_recommender");
            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
            userRecommenderIncomeLogEntity.setInvitation_id(entity.getUser_id());
            ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
            if (insert.getCode() == 0) {
                //??????????????????
                userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(earn));
                userRecommenderEntity.setInvate_num(userRecommenderEntity.getInvate_num() + 1);
                userRecommenderMapper.updateByPrimaryKeySelective(userRecommenderEntity);
                //??????????????????
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(earn));
                synchronized (userInfoEntity) {
                    userInfoMapper.updateCash(userInfoEntity);
                }
            }
        }
    }

    private BigDecimal getEarnCountry(String country, UserEarnRoleEntity userEarnRoleEntity) {
        BigDecimal least_earn;
        switch (country) {
            case "??????":
                least_earn = userEarnRoleEntity.getEach_master_usa();
                break;
            case "?????????":
            case "?????????":
            case "????????????":
                least_earn = userEarnRoleEntity.getEach_master_newzealand_canada_australia();
                break;
            case "??????":
                least_earn = userEarnRoleEntity.getEach_master_britain();
                break;
            case "??????":
            case "??????":
                least_earn = userEarnRoleEntity.getEach_master_southafrica_europe();
                break;
            default:
                least_earn = userEarnRoleEntity.getEach_master_else();
        }
        return least_earn;

    }

    /**
     * ??????????????????
     *
     * @param entity
     */
    private void insertMasterSetPrice(MasterInfoEntity entity) {
        MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
        masterSetPriceEntity.setTitle(entity.getType());
        masterSetPriceEntity.setType("exclusive_courses");
        masterSetPriceEntity.setUser_id(entity.getUser_id());
        masterSetPriceEntity.setAmout(entity.getCharging_standard().multiply(new BigDecimal("1.5")));
        masterSetPriceEntity.setService_type(1);
        masterSetPriceEntity.setDiscount(new BigDecimal("0.5"));
        masterSetPriceEntity.setDiscount_amout(entity.getCharging_standard());
        masterSetPriceEntity.setIntroduction_content(entity.getIntroduction_content());
        masterSetPriceEntity.setStatus(2);
        masterSetPriceEntity.setCourse_num(entity.getMinimum_pay());
        masterSetPriceEntity.setService_type(2);
        masterSetPriceEntity.setDiscount_amout(entity.getCharging_standard_offline());
        masterSetPriceEntity.setAmout(entity.getCharging_standard_offline().multiply(new BigDecimal("1.5")));
        masterSetPriceMapper.insertSelective(masterSetPriceEntity);
    }

    private void updateEarn(MasterMechanismEntity masterMechanismEntity, MasterInfoEntity masterInfoEntity1, UserRecommenderEntity userRecommenderEntity, HttpServletRequest request, HttpServletResponse response) {
        //?????????
        if ("mechanism_recommender".equals(userRecommenderEntity.getType())) {
            //??????
            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            //??????
            userRecommenderIncomeLogEntity.setCash(userEarnRoleEntity.getEvery_master_earn());
            //???????????????id
            userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
            //??????id
            userRecommenderIncomeLogEntity.setMechanism_id(masterMechanismEntity.getId());
            //????????????id
            userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity1.getId());
            //?????? ????????????
            userRecommenderIncomeLogEntity.setType("mechanism_master");
            //?????? true
            userRecommenderIncomeLogEntity.setIs_settlement(true);
            userRecommenderIncomeLogEntity.setCash_describe("???????????????????????????");
            userRecommenderIncomeLogEntity.setRecommender_type("mechanism_recommender");
            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
            ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
            if (insert.getCode() == 0) {
                //???????????????????????????
                userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(userEarnRoleEntity.getEvery_master_earn()));
                userRecommenderMapper.updateByPrimaryKeySelective(userRecommenderEntity);
                //??????????????????
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderEntity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(userEarnRoleEntity.getEvery_master_earn()));
                synchronized (userInfoEntity) {
                    userInfoMapper.updateCash(userInfoEntity);
                }
            }
        }
    }

    @Override
    public Map<String, Object> queryMasterAuthListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> listMap = new HashMap<>();
        List<MasterInfoEntity> list = mapper.queryMasterAuthListPage(masterInfoEntity);
        if (list != null && list.size() > 0) {
            for (MasterInfoEntity entity : list) {
                Map<String, Object> map = new HashMap<>();
                UserInfoEntity userInfoEntity = userInfoService.findById(entity.getUser_id(), request, response);
                map.put("userInfoEntity", userInfoEntity);
                map.put("userEntity", userMapper.selectByPrimaryKey(userInfoEntity.getUser_id()));
                entity.setMap(map);
            }
        }
        //?????????????????????
        listMap.put("masterinfo", list);
        return listMap;
    }

    /**
     * ????????????
     * zwx
     * 2019???6???24???20:18:12
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterInfoEntity> queryTeachingCenter(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryListPage(param);
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                for (MasterInfoEntity entity : masterInfoEntities) {
                    if ("live_lecturer".equalsIgnoreCase(entity.getType())) {
                        Map<String, Object> map = new HashMap<>();
                        MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                        masterTypeEntity.setType(entity.getType());
                        masterTypeEntity.setPageSize(1);
                        List<MasterTypeEntity> list = masterTypeMapper.queryListPage(masterTypeEntity);
                        if (list != null && list.size() > 0) {
                            masterTypeEntity = list.get(0);
                        }
                        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                        //????????????
                        map.put("currentStudentCount", 0);
                        //????????????
                        map.put("studentCount", 0);
                        //???????????????
                        map.put("mastertype", masterTypeEntity);
                        masterAppointmentEntity.setMaster_id(param.getUser_id());
                        masterAppointmentEntity.setType("open_class");
                        Integer courseCount = masterAppointmentMapper.queryCourseCount(masterAppointmentEntity);
                        //????????????
                        map.put("courseCount", courseCount);
                        Integer residueCount = masterAppointmentMapper.querySurplusCount(masterAppointmentEntity);
                        //????????????
                        map.put("residueCount", residueCount);
                        Integer classCount = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                        //????????????
                        map.put("classCount", classCount);
                        List<MasterAppointmentEntity> AppointmentEntities = masterAppointmentMapper.queryLatelyCourse(masterAppointmentEntity);
                        //????????????
                        map.put("latelyclass", AppointmentEntities);
                        //????????????
                        map.put("totalIncome", 0);
                        //?????????
                        map.put("monthlyEarnings", 0);
                        //?????????
                        map.put("groupClassCount", messageApiService.queryMyClassCount(entity.getUser_id()));
                        entity.setMap(map);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                        masterTypeEntity.setType(entity.getType());
                        masterTypeEntity.setPageSize(1);
                        List<MasterTypeEntity> list = masterTypeMapper.queryListPage(masterTypeEntity);
                        if (list != null && list.size() > 0) {
                            masterTypeEntity = list.get(0);
                        }
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setMaster_id(entity.getUser_id());
                        Integer courseCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                        userAppointmentEntity.setStatus(2);
                        map.put("courseCount", courseCount);//????????????
                        Integer residueCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                        map.put("residueCount", residueCount);//????????????
                        userAppointmentEntity.setStatus(3);
                        Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                        map.put("classCount", classCount);//????????????
                        userAppointmentEntity.setStatus(2);
                        map.put("mastertype", masterTypeEntity);//???????????????
                        Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                        map.put("studentCount", studentCount);//????????????
                        Integer currentStudentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                        map.put("currentStudentCount", currentStudentCount);//????????????
                        List<UserAppointmentEntity> userAppointmentEntities1 = userAppointmentMapper.queryLatelyCourse(userAppointmentEntity);
                        map.put("latelyclass", userAppointmentEntities1);//????????????
                        map.put("totalIncome", 0);//????????????
                        map.put("monthlyEarnings", 0);//?????????
                        entity.setMap(map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    /**
     * ????????????????????????
     * zwx
     * 2019???6???24???20:18:12
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryHomeListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = new ArrayList<>();
        try {
           /*
           param.setPageSize(3);
            if(param.getCurrentPage()==1){
            //???????????????
            MasterInfoEntity experienceMaster = new MasterInfoEntity();
            //experienceMaster.setPageSize(3);
            experienceMaster.setRecommend_type("experience");
            List<MasterInfoEntity> experienceMasters = queryHomeMaster(experienceMaster, request, response);
            masterInfoEntities.addAll(experienceMasters);
            //???????????????
            MasterInfoEntity motherTongueMaster = new MasterInfoEntity();
            //motherTongueMaster.setPageSize(3);
            motherTongueMaster.setRecommend_type("mother_tongue");
            List<MasterInfoEntity> motherTongueMasters = queryHomeMaster(motherTongueMaster, request, response);
            masterInfoEntities.addAll(motherTongueMasters);
            //map.put("motherTongueMasters",motherTongueMasters);
            //???????????????
            MasterInfoEntity privateEducation = new MasterInfoEntity();
            //privateEducation.setPageSize(3);
            privateEducation.setRecommend_type("private_education");
            List<MasterInfoEntity> privateEducationMasters = queryHomeMaster(privateEducation, request, response);
            masterInfoEntities.addAll(privateEducationMasters);
            //map.put("privateEducationMasters",privateEducationMasters);
            //???????????????
            MasterInfoEntity recommendMaster = new MasterInfoEntity();
            recommendMaster.setRecommend_type("recommend");
            //recommendMaster.setPageSize(4);
            List<MasterInfoEntity> recommendMasters = queryHomeMaster(recommendMaster, request, response);
            masterInfoEntities.addAll(recommendMasters);
            //map.put("recommendMasters",recommendMasters);
            //???????????????
            MasterInfoEntity majorMaster = new MasterInfoEntity();
            majorMaster.setRecommend_type("major");
            //majorMaster.setPageSize(3);
            List<MasterInfoEntity> majorMasters = queryHomeMaster(majorMaster, request, response);
            masterInfoEntities.addAll(majorMasters);
            //map.put("majorMaster",majorMasters);
            //???????????????
            MasterInfoEntity crossBorder = new MasterInfoEntity();
            crossBorder.setRecommend_type("cross_border");
            //crossBorder.setPageSize(3);
            List<MasterInfoEntity> crossBorderMasters = queryHomeMaster(crossBorder, request, response);
            masterInfoEntities.addAll(crossBorderMasters);
            //map.put("crossBorderMasters",crossBorderMasters);
            //?????????????????????
            List<MasterInfoEntity> hotMasters = queryHomeMaster(param, request, response);//queryHomeHotListPage
            for (MasterInfoEntity entity : hotMasters) {
                if (entity.getRecommend_video() == null || entity.getRecommend_video() == "") {
                    entity.setRecommend_type(entity.getTeach_language_url());
                }
                entity.setRecommend_type("hot");
                entity.setLayout(3);
                Map<String, Object> map1 = new HashMap<>();
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setMaster_id(entity.getUser_id());
                Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                map1.put("studentCount", studentCount);//????????????
                map1.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                entity.setMap(map1);
            }
            masterInfoEntities.addAll(hotMasters);
            //map.put("hotMaster",hotMasters);
            }else{*/


            //?????????????????????
            masterInfoEntities = queryHomeMaster(param, request, response);
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                for (MasterInfoEntity entity : masterInfoEntities) {
//                if (entity.getRecommend_video() == null || entity.getRecommend_video() == "") {
//                    entity.setRecommend_type(entity.getTeach_language_url());
//                }

                    entity.setRecommend_type("hot");
                    entity.setLayout(3);
                    Map<String, Object> map1 = new HashMap<>();
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMaster_id(entity.getUser_id());
                    map1.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                    map1.put("studentCount", studentCount);//????????????
                    entity.setMap(map1);
                }
            }
            /*}*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryHomeListPage");
        }
        return ResultUtil.success(masterInfoEntities);
    }

    @Override
    public List<MasterInfoEntity> queryHomeMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        List<MasterInfoEntity> masterInfoEntities1 = Lists.newArrayList();
        try {
            param.setStatus(2);
            param.setIs_recommend(true);
            masterInfoEntities = mapper.queryListPage(param);
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {

                for (MasterInfoEntity entity : masterInfoEntities) {
                    if ("private_education".equals(entity.getType())) {
                        continue;
                    }
                /*if (entity.getRecommend_video() == null || entity.getRecommend_video() == "") {
                    entity.setRecommend_video(entity.getTeach_language_url());
                    entity.setCover(entity.getTeach_language_url() + "?vframe/jpg/offset/0");
                }
                 */
                    if (entity.getRecommend_video() != null && !entity.getRecommend_video().equals("") && entity.getIs_recommend()) {
//                        entity.setCover(entity.getTeach_language_url() + "?vframe/jpg/offset/0");
                        entity.setCover(entity.getRecommend_video() + "?vframe/jpg/offset/0");
                        Map<String, Object> map = new HashMap<>();
                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setMaster_id(entity.getUser_id());
                        Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                        map.put("studentCount", studentCount);//????????????
                        entity.setMap(map);
                        masterInfoEntities1.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl   queryHomeMaster");
        }
        return masterInfoEntities1;
    }

    /**
     * update?????? ????????????
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam update(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /**
             * ??????
             */
            if (param.getStatus() != null && 5 == param.getStatus()) {//??????
                MasterInfoEntity masterInfoEntity = mapper.selectByPrimaryKey(param.getId());
                MasterInfoEntity masterInfoEntity1 = new MasterInfoEntity();
                masterInfoEntity1.setUser_id(masterInfoEntity.getUser_id());
                List<MasterInfoEntity> query = mapper.queryListPage(masterInfoEntity1);
                for (MasterInfoEntity infoEntity : query) {//??????
                    infoEntity.setStatus(param.getStatus());
                    infoEntity.setFrozen_contect(param.getFrozen_contect());
                    mapper.updateByPrimaryKeySelective(infoEntity);
                }
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setPush_type("masterInfo");
                pushMessageParam.setOpera_type("masterInfo");
                pushMessageParam.setTarget_id(masterInfoEntity.getUser_id());
                if ("private_education".equals(masterInfoEntity.getType())) {
                    pushMessageParam.setContent("????????????????????????,???????????????");
                } else if ("teach_paypal".equalsIgnoreCase(masterInfoEntity.getType())) {
                    pushMessageParam.setContent("????????????????????????,???????????????");
                    pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(masterInfoEntity.getMechanism_id()).getUser_id());
                } else {
                    pushMessageParam.setContent("???????????????????????????,???????????????");
                }
                pushMessageParam.setType_id(masterInfoEntity.getId());
                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("frozenStatusMessage", pushMessageParam);
                return ResultUtil.success();
            }

            /**
             * ??????
             */
            if (param.getStatus() != null && 2 == param.getStatus()) {
                MasterInfoEntity masterInfoEntity = mapper.selectByPrimaryKey(param.getId());
                MasterInfoEntity masterInfoEntity1 = new MasterInfoEntity();
                masterInfoEntity1.setUser_id(masterInfoEntity.getUser_id());
                List<MasterInfoEntity> query = mapper.queryListPage(masterInfoEntity1);
                for (MasterInfoEntity infoEntity : query) {//??????
                    infoEntity.setStatus(param.getStatus());
                    infoEntity.setFrozen_contect("");
                    mapper.updateByPrimaryKeySelective(infoEntity);
                }
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(masterInfoEntity.getUser_id());
                pushMessageParam.setPush_type("masterInfo");
                pushMessageParam.setOpera_type("masterInfo");
                if ("private_education".equals(masterInfoEntity.getType())) {
                    pushMessageParam.setContent("?????????????????????");
                } else if ("teach_paypal".equalsIgnoreCase(masterInfoEntity.getType())) {
                    pushMessageParam.setContent("?????????????????????");
                    pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(masterInfoEntity.getMechanism_id()).getUser_id());
                } else {
                    pushMessageParam.setContent("????????????????????????");
                }
                pushMessageParam.setType("frozenStatus");
                pushMessageParam.setType_id(masterInfoEntity.getId());
                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("frozenStatusMessage", pushMessageParam);
                return ResultUtil.success();
            }

            if (param.getIdList() != null && param.getIdList().size() > 0) {
                for (Long aLong : param.getIdList()) {
                    param.setId(aLong);
                    mapper.updateByPrimaryKeySelective(param);
                }
            } else {
                return super.update(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl   queryHomeMaster");
        }
        return ResultUtil.success();
    }

    /**
     * ???????????????
     * zwx
     * 2020???6???24???21:08:59
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterInfoEntity> queryMasterListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            if (param.getType() != null && "private_education".equalsIgnoreCase(param.getType())) {
                masterInfoEntities = mapper.queryPrivateListPage(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryPrivateListPage(param);
                }
            } else {
                masterInfoEntities = mapper.queryMasterListPage(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryMasterListPage(param);
                }
            }
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                this.setTeacherInfo(masterInfoEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryMasterListPage");
        }
        return masterInfoEntities;
    }

    private void setTeacherInfo(List<MasterInfoEntity> masterInfoEntities) {
        for (MasterInfoEntity entity : masterInfoEntities) {
            Map<String, Object> map = new HashMap<>();
            MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
            masterTypeEntity.setType(entity.getType());
            masterTypeEntity.setPageSize(1);
            List<MasterTypeEntity> list = masterTypeMapper.queryListPage(masterTypeEntity);
            if (list != null && list.size() > 0) {
                masterTypeEntity = list.get(0);
            }
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setMaster_id(entity.getUser_id());
            Integer courseCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
            userAppointmentEntity.setStatus(2);
            map.put("courseCount", courseCount);//????????????
            Integer residueCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
            map.put("residueCount", residueCount);//????????????
            userAppointmentEntity.setStatus(3);
            Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("classCount", classCount);//????????????
            userAppointmentEntity.setStatus(3);
            map.put("mastertype", masterTypeEntity);//???????????????
            Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
            map.put("studentCount", studentCount);//????????????
            Integer currentStudentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
            map.put("currentStudentCount", currentStudentCount);//???????????????
            List<UserAppointmentEntity> userAppointmentEntities1 = userAppointmentMapper.queryLatelyCourse(userAppointmentEntity);
            map.put("latelyclass", userAppointmentEntities1);//????????????
            map.put("totalIncome", 0);//????????????
            map.put("monthlyEarnings", 0);//?????????
            entity.setMap(map);
        }
    }

    @Override
    public List<MasterInfoEntity> queryMasterListPageNew(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            if (param.getType() != null && "private_education".equalsIgnoreCase(param.getType())) {
                masterInfoEntities = mapper.queryPrivateListPageWith3(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryPrivateListPageWith3(param);
                }
            } else {
                masterInfoEntities = mapper.queryMasterListPage(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryMasterListPage(param);
                }
            }
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                this.setTeacherInfo(masterInfoEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryMasterListPage");
        }
        return masterInfoEntities;
    }

    @Override
    public ResultParam updateMechanismMasterInfo(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getStatus()!=null) {
                MasterInfoEntity masterInfoEntity = mapper.selectByPrimaryKey(param.getId());
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(masterInfoEntity.getUser_id());
                userInfoEntity.setAdmin_id(0L);
                userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                UserEntity userEntity = new UserEntity();
                userEntity.setUser_id(masterInfoEntity.getUser_id());
                userEntity.setStatus(4);
                userMapper.updateByPrimaryKeySelective(userEntity);
                param.setIs_recommend(false);
            }else {
                MasterInfoEntity masterInfoEntity = mapper.selectByPrimaryKey(param.getId());
                String str = MD5Util.GetMD5Code(param.getLogin_name() + param.getLogin_pass());
                UserEntity userEntity = userMapper.selectByPrimaryKey(masterInfoEntity.getUser_id());
                userEntity.setLogin_pass(str);
                int i = userMapper.updateByPrimaryKeySelective(userEntity);
            }
            return super.update(param, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }


    @Override
    @Transactional
    public ResultParam masterMechanismInsert(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            //user????????????
            UserEntity userEntity = new UserEntity();
            userEntity.setPageSize(1);
            userEntity.setLogin_name(param.getLogin_name());
            List<UserEntity> userEntities = userMapper.queryListPage(userEntity);
            if (userEntities != null && userEntities.size() > 0) {
                return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
            }
            String str = MD5Util.GetMD5Code(param.getLogin_name() + param.getLogin_pass());
            userEntity.setLogin_pass(str);
            userEntity.setAdmin_id(param.getMechanism_id());
            userEntity.setAvatar(param.getPhoto());//??????
            userEntity.setNick_name(param.getFull_name());
            userEntity.setRegister_platform("mechanism_master");
            ResultParam resultParam = userService.insert(userEntity, request, response);
            if (resultParam.getCode() != null && resultParam.getCode() == 0) {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(userEntity.getUser_id());
                int ret = userInfoService.TIMregister(userInfoEntity);//TIM?????? ????????????
                if (ret > 0) {
                    TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                    String userSign = tlsSigAPIv2.genSig(userEntity.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                    redisService.set("usersign" + userEntity.getUser_id(), userSign, timConfig.getExpire());
                    UserInfoEntity infoEntity = userInfoService.findById(userEntity.getUser_id(), request, response);
                    infoEntity.setAdmin_id(param.getMechanism_id());
                    userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                    redisService.set(userEntity.getUser_id() + "userinfo", JSONObject.toJSONString(infoEntity));
                    if (StringUtil.isNotEmpty(param.getStringList())) {
                        String[] arr = param.getStringList().split(","); // ???,??????
                        List<String> stringList = Arrays.asList(arr);
                        ArrayList<String> typeArray = new ArrayList<>(Arrays.asList("major", "cross_border", "mother_tongue", "private_education", "live_lecturer"));

                        for (String s : stringList) {
                            param.setType(s);
                            param.setUser_id(userEntity.getUser_id());
                            typeArray.remove(s);
                            param.setIs_open(1);
                            param.setStatus(1);
                            ResultParam result = super.insert(param, request, response);
                            if (result.getCode() != null && result.getCode() != 0) {
                                return result;
                            }
                        }
                        for (int i = 0; i < 5 - stringList.size(); i++) {
                            param.setType(typeArray.get(i));
                            param.setUser_id(userEntity.getUser_id());
                            param.setIs_open(0);
                            param.setStatus(6);
                            ResultParam result = super.insert(param, request, response);
                            if (result.getCode() != null && result.getCode() != 0) {
                                return result;
                            }
                        }

                    } else {
                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                }
            } else {
                return resultParam;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.success();
    }

    /**
     * ?????????????????????
     * zwx
     * 2020???6???24???21:08:59
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterInfoEntity> queryMechanismMasters(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            //??????????????????
            if (param.getIs_all() != null && param.getIs_all()) {//true is_all = true ||!=null
                masterInfoEntities = mapper.queryMasterInfo(param);//???????????????id ???type
                //?????? major ??????,cross_border ??????,mother_tongue ??????,private_education  ??????  advertising_position ????????? live_lecturer????????????
            } else if (param.getType() != null && !Objects.equals(param.getType(), "")) {//type ??????
                masterInfoEntities = mapper.queryMasterInfo(param);//???type?????????
            } else {//????????????????????? ???type
                masterInfoEntities = mapper.queryMasterInfoListPage(param);
            }
            for (MasterInfoEntity masterInfoEntity : masterInfoEntities) {

                Map<String, Object> map = new HashMap<>();
                MasterInfoEntity masterInfoEntity1 = new MasterInfoEntity();//new ????????????
                masterInfoEntity1.setUser_id(masterInfoEntity.getUser_id());//??????id
                List<MasterInfoEntity> list = mapper.query(masterInfoEntity1);//??????????????????
                map.put("masterInfoEntities1", list);//??????map???
                map.put("score", list.get(0).getScore());

                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//????????????????????????
                userAppointmentEntity.setStatus(3);// ?????? 2 ????????? 3 ????????????  4 ????????? 5 ????????? 6?????????????????????  7??????????????????  8????????????????????? 9??????????????????
                userAppointmentEntity.setMaster_id(masterInfoEntity.getUser_id());//???????????????id
                Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);//???????????????????????? ????????????
                map.put("studentCount", studentCount);//?????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(masterInfoEntity.getUser_id() + "userinfo")));//?????????????????????
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();//??????????????????
                masterAppointmentEntity.setMaster_id(masterInfoEntity.getUser_id());//????????????id
                Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);//????????????????????????
                UserAppointmentEntity appointmentEntity = new UserAppointmentEntity();//????????????????????????
                appointmentEntity.setMaster_type("private_education");//???????????? ??????
                appointmentEntity.setMaster_id(masterInfoEntity.getUser_id());//????????????id
                appointmentEntity.setStatus(3);//????????????
                Integer privateCount = userAppointmentMapper.queryListPageCount(appointmentEntity);//????????????????????????
                UserAppointmentEntity appointmentEntity1 = new UserAppointmentEntity();//????????????????????????
                appointmentEntity1.setMaster_type("mechanism_offline");//??????????????????
                appointmentEntity1.setStatus(3);//?????????
                appointmentEntity1.setMaster_id(masterInfoEntity.getUser_id());//???????????????id
                Integer mechanismCount = userAppointmentMapper.queryListPageCount(appointmentEntity1);
                Integer classCount = integer + privateCount + mechanismCount;
                map.put("classCount", classCount);//????????????
                masterInfoEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    @Override
    @Transactional
    public ResultParam masterBindingSwitch(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        try {
            if (StringUtil.isEmpty(param.getLogin_name())) {
                return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
            }
            if (StringUtil.isEmpty(param.getVerification_code())) {
                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setPageSize(1);
            userEntity.setLogin_name(param.getLogin_name());
            List<UserEntity> userEntities = userMapper.queryListPage(userEntity);
            if (userEntities != null && userEntities.size() > 0) {
                return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
            }
            ResultParam resultParam1 = new ResultParam();
            if (param.getLogin_name().contains("@")) {
                resultParam1 = mailService.validate(param.getLogin_name(), param.getVerification_code());
                if (resultParam1.getCode() != 0) {
                    return resultParam1;
                }
            }
            if (CommonUtils.isPhone(param.getLogin_name())) {//???????????????
                resultParam1 = smsService.validate(param.getLogin_name(), param.getVerification_code());
                if (resultParam1.getCode() != 0) {
                    return resultParam1;
                }
            }
            String str = MD5Util.GetMD5Code(param.getLogin_name() + param.getLogin_pass());
            userEntity.setLogin_pass(str);
            userEntity.setRegister_platform("mechanism_master");
            userEntity.setIs_teach_paypal(true);
            int ret = userMapper.insertSelective(userEntity);
            if (ret > 0) {
                UserInfoEntity userInfoEntity = userInfoService.findById(param.getUser_id(), request, response);
                if (userInfoEntity != null) {
                    userInfoEntity.setBinding_id(userEntity.getUser_id());
                    int retb = userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                    if (retb > 0) {
                        userInfoEntity.setMechanism_id(0L);
                        userInfoEntity.setAdmin_id(0L);
                        userInfoEntity.setUser_id(userEntity.getUser_id());
                        userInfoEntity.setBinding_id(param.getUser_id());
                        userInfoEntity.setIs_teach_paypal(param.getIs_teach_paypal());
                        int ret1 = userInfoMapper.insertSelective(userInfoEntity);
                        userInfoEntity = userInfoService.findById(userEntity.getUser_id(), request, response);
                        stringObjectMap.put("userInfoEntity", userInfoEntity);
                        if (ret1 > 0) {
                            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                            String userSign = tlsSigAPIv2.genSig(userEntity.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                            redisService.set("usersign" + userEntity.getUser_id(), userSign, timConfig.getExpire());
                            stringObjectMap.put("usersign", userSign);
                            redisService.set(userEntity.getUser_id() + "userinfo", JSONObject.toJSONString(userInfoEntity));
                            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                            masterInfoEntity.setUser_id(param.getUser_id());
                            List<MasterInfoEntity> appointmentEntities = mapper.queryListPage(masterInfoEntity);
                            for (MasterInfoEntity infoEntity : appointmentEntities) {
                                infoEntity.setMechanism_id(0L);
                                infoEntity.setUser_id(userEntity.getUser_id());
                                infoEntity.setId(null);
                                mapper.insertSelective(infoEntity);
                            }
                        } else {
                            return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
                        }
                    } else {
                        return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
                    }

                } else {
                    return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
                }
            } else {
                return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.success(stringObjectMap);
    }

    @Override
    public Map<String, Object> countMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        try {

            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();

            masterInfoEntity.setIs_recommend(true);
            Integer recommendCount = mapper.queryMasterCount(masterInfoEntity);
            stringObjectMap.put("recommendCount", recommendCount);//??????
            masterInfoEntity.setIs_recommend(null);

            masterInfoEntity.setStatus(2);
            Integer masterCount = mapper.queryMasterCount(masterInfoEntity);
            stringObjectMap.put("masterCount", masterCount);//????????????

            masterInfoEntity.setType("major");
            Integer majorCount = mapper.queryMasterCount(masterInfoEntity);//??????
            stringObjectMap.put("majorCount", masterCount);//??????????????????

            masterInfoEntity.setType("cross_border");
            Integer crossBorderCount = mapper.queryMasterCount(masterInfoEntity);//??????
            stringObjectMap.put("crossBorderCount", crossBorderCount);//??????????????????

            masterInfoEntity.setType("mother_tongue");
            Integer motherTongue = mapper.queryMasterCount(masterInfoEntity);//??????
            stringObjectMap.put("motherTongue", motherTongue);//??????????????????

            stringObjectMap.put("helperCount", majorCount + crossBorderCount + motherTongue);//?????????

            masterInfoEntity.setType("private_education");
            masterInfoEntity.setService_type(1);
            Integer privateCount = mapper.queryMasterCount(masterInfoEntity);
            stringObjectMap.put("privateOnlineCount", privateCount);//????????????

            masterInfoEntity.setService_type(2);
            Integer privateOfflineCount = mapper.queryMasterCount(masterInfoEntity);
            stringObjectMap.put("privateOfflineCount", privateOfflineCount);//??????

            stringObjectMap.put("queryEachType", this.queryEachType(param, request, response));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringObjectMap;
    }

    @Override
    public Map<String, Object> queryEachType(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer masterCount = mapper.queryMasterCount(param);
            map.put("masterCount", masterCount);//??????

            param.setTeach_language("English");
            Integer englishMasterCount = mapper.queryEnglishCount(param);
            map.put(param.getTeach_language() + "MasterCount", englishMasterCount);//???????????????

            param.setTeach_language("French");
            Integer FrenchMasterCount = mapper.queryFrenchCount(param);
            map.put(param.getTeach_language() + "MasterCount", FrenchMasterCount);//???????????????

            param.setTeach_language("Japanese");
            Integer JapaneseMasterCount = mapper.queryJapaneseCount(param);
            map.put(param.getTeach_language() + "MasterCount", JapaneseMasterCount);//???????????????

            map.put("OtherMasterCount", masterCount - englishMasterCount - FrenchMasterCount - JapaneseMasterCount);//???????????????

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> masterAuditCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer total = mapper.queryListPageCount(param);
            map.put("total", total);

            param.setStatus(2);
            Integer agreeCount = mapper.queryListPageCount(param);
            map.put("agreeCount", agreeCount);

            param.setStatus(1);
            Integer waitCount = mapper.queryListPageCount(param);
            map.put("waitCount", waitCount);

            param.setStatus(3);
            Integer refuseCount = mapper.queryListPageCount(param);
            map.put("refuseCount", refuseCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> helperCount(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer applyTotal = mapper.qeuryHelperCount(param);
            map.put("applyTotal", applyTotal);

            param.setStatus(2);
            Integer trueTotal = mapper.qeuryHelperCount(param);
            map.put("trueTotal", trueTotal);

            param.setLanguage("English");
            Integer englishCount = mapper.qeuryHelperCount(param);
            map.put("englishCount", englishCount);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterInfoEntity> queryRecommendMasterInfo(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            param.setStatus(2);
            masterInfoEntities = mapper.queryRecommendMasterInfo(param);
            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                for (MasterInfoEntity entity : masterInfoEntities) {
                    if (entity.getRecommend_video() == null || entity.getRecommend_video().equals("")) {
                        entity.setRecommend_video(entity.getTeach_language_url());
                        entity.setCover(entity.getTeach_language_url() + "?vframe/jpg/offset/0");
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMaster_id(entity.getUser_id());
                    Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                    map.put("studentCount", studentCount);//????????????
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl   queryRecommendMasterInfo");
        }
        return masterInfoEntities;
    }

    /**
     * ??????????????????
     * zwx
     * 2020???7???15???14:50:18
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<MasterInfoEntity> queryMechanismPrivateListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> masterInfoEntities = null;
        try {
            masterInfoEntities = mapper.queryMechanismPrivateListPage(param);
            for (MasterInfoEntity entity : masterInfoEntities) {
                Map<String, Object> map = new HashMap<>();
                MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                masterTypeEntity.setType(entity.getType());
                masterTypeEntity.setPageSize(1);
                List<MasterTypeEntity> list = masterTypeMapper.queryListPage(masterTypeEntity);
                if (list != null && list.size() > 0) {
                    masterTypeEntity = list.get(0);
                }
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setMaster_id(entity.getUser_id());
                Integer courseCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                userAppointmentEntity.setStatus(2);
                map.put("courseCount", courseCount);//????????????
                Integer residueCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                map.put("residueCount", residueCount);//????????????
                userAppointmentEntity.setStatus(3);
                Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                map.put("classCount", classCount);//????????????
                userAppointmentEntity.setStatus(3);
                map.put("mastertype", masterTypeEntity);//???????????????
                Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                map.put("studentCount", studentCount);//????????????
                Integer currentStudentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                map.put("currentStudentCount", currentStudentCount);//???????????????
                List<UserAppointmentEntity> userAppointmentEntities1 = userAppointmentMapper.queryLatelyCourse(userAppointmentEntity);
                map.put("latelyclass", userAppointmentEntities1);//????????????
                map.put("totalIncome", 0);//????????????
                map.put("monthlyEarnings", 0);//?????????
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryListPage");
        }
        return masterInfoEntities;
    }

    @Override
    public Map<String, Object> queryMasterLoginInfo(MasterInfoEntity param, HttpServletResponse request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterInfoEntity> masterInfoEntitys = mapper.queryMasterLoginInfo(param);
            MasterInfoEntity masterInfoEntity = masterInfoEntitys.get(0);
            for (MasterInfoEntity infoEntity : masterInfoEntitys) {
                map.put(infoEntity.getType(), infoEntity.getIs_open());
            }
            map.put("login_name", masterInfoEntity.getLogin_name());
            map.put("login_pass", masterInfoEntity.getLogin_pass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public ResultParam upDateMasterStatus(Map<String, String> param, HttpServletResponse request, HttpServletResponse response) {
        Map<String, String> map = Maps.newHashMap();
        try {
            // major ???????????????,cross_border ???????????????,mother_tongue ???????????????,private_education  ??????  live_lecturer ????????????',
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setUser_id(Long.valueOf(param.get("user_id")));
            List<MasterInfoEntity> masterInfoEntities = mapper.queryList(masterInfoEntity);

            for (MasterInfoEntity infoEntity : masterInfoEntities) {
                if (param.containsKey(infoEntity.getType())) {
                    if ("1".equals(param.get(infoEntity.getType()))) {
                        infoEntity.setIs_open(1);
                    } else {
                        infoEntity.setIs_open(0);
                    }
                }
                if (infoEntity.getIs_open() == 0) {
                    infoEntity.setStatus(6);
                } else {
                    infoEntity.setStatus(2);
                }
                String login_name = param.get("login_name");
                String login_pass = param.get("login_pass");
                infoEntity.setLogin_name(login_name);
                infoEntity.setLogin_pass(login_pass);
                mapper.updateByPrimaryKeySelective(infoEntity);
            }
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setLogin_name(param.get("login_name"));
            List<UserEntity> query = userMapper.query(userEntity1);
            if (query != null && query.size() > 1) {
                return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
            }
            UserEntity userEntity = userMapper.selectByPrimaryKey(Long.valueOf(param.get("user_id").toString()));
            String str = MD5Util.GetMD5Code(param.get("login_name") + param.get("login_pass"));
            userEntity.setLogin_name(param.get("login_name"));
            userEntity.setLogin_pass(str);
            userMapper.updateByPrimaryKeySelective(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public Map<String, Object> queryMasterTeacherInfo(MasterInfoEntity masterInfoEntity, HttpServletResponse request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            //5???
            List<MasterInfoEntity> masterInfoEntities = mapper.queryList(masterInfoEntity);
            for (MasterInfoEntity infoEntity : masterInfoEntities) {
                map.put(infoEntity.getType(), infoEntity.getIs_open());
            }
            if (masterInfoEntities.get(0).getUser_id() != null) {
                map.put("login_name", masterInfoEntities.get(1).getLogin_name());
            }
            if (masterInfoEntities.get(0).getLogin_pass() != null) {
                map.put("login_pass", masterInfoEntities.get(1).getLogin_pass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> updateMasterInfo(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            MasterInfoEntity masterInfoEntity1 = new MasterInfoEntity();
            masterInfoEntity1.setUser_id(param.getUser_id());
            if (param.getMechanism_id() != null && param.getMechanism_id() == 0) {//??????????????????
                masterInfoEntity1.setType(param.getStringList());
                MasterInfoEntity masterInfoEntity = mapper.query(masterInfoEntity1).get(0);
                param.setId(masterInfoEntity.getId());
                param.setStatus(1);
                param.setIs_open(1);
                param.setRefuse_contect("");
                super.update(param, request, response);
                return map;
            }
            List<MasterInfoEntity> list = mapper.queryList(masterInfoEntity1);
            for (MasterInfoEntity masterInfoEntity : list) {
                if (StringUtil.isNotEmpty(param.getStringList())) {
                    String[] arr = param.getStringList().split(","); // ???,??????
                    List<String> stringList = Arrays.asList(arr);
                    //??????isopen
                    if (stringList.contains(masterInfoEntity.getType())) {
                        param.setIs_open(1);
                        masterInfoEntity.setIs_open(1);
                    } else {
                        param.setIs_open(0);
                        masterInfoEntity.setIs_open(0);
                    }
                    //isopen?????? ???status
                    if (masterInfoEntity.getIs_open() == 1) {
                        param.setStatus(1);
                    } else {
                        param.setStatus(6);
                    }
                }
                param.setId(masterInfoEntity.getId());
                super.update(param, request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterInfoEntity> queryPassMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryPassMasterListPage(masterInfoEntity);
            for (MasterInfoEntity infoEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(infoEntity.getUser_id() + "userinfo")));
                infoEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryMasterList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<MasterInfoEntity> list = mapper.queryMasterListPage(masterInfoEntity);
            Integer total = mapper.queryMasterListCount(masterInfoEntity);
            for (MasterInfoEntity infoEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(infoEntity.getUser_id() + "userinfo")));
                infoEntity.setMap(map);
            }
            map1.put("rows", list);
            map1.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @Override
    public Map<String, Object> querEachMasterCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer total = mapper.queryMasterListPageCount(masterInfoEntity);
            map.put("total", total);

            masterInfoEntity.setType("major");
            Integer major = mapper.queryMasterListPageCount(masterInfoEntity);
            map.put("major", major);

            Integer cross_border = mapper.queryMasterListPageCount(masterInfoEntity);
            map.put("cross_border", cross_border);

            Integer mother_tongue = mapper.queryMasterListPageCount(masterInfoEntity);
            map.put("mother_tongue", mother_tongue);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterInfoEntity> queryPrivateMaster(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = null;
        try {
            list = mapper.queryListPage(masterInfoEntity);
            for (MasterInfoEntity infoEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userInfo", userInfoMapper.selectByPrimaryKey(infoEntity.getUser_id()));
                infoEntity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ResultParam insertInviteMaster(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            ResultParam resultParam = null;
            if (StringUtil.isEmpty(param.getVerification_code())) {//???????????????
                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
            }
            MasterInfoEntity masterInfoEntity1 = new MasterInfoEntity();
            if (StringUtil.isNotEmpty(param.getMail())) {
                resultParam = mailService.validate(param.getMail(), param.getVerification_code());//??????redis????????????
                masterInfoEntity1.setMail(param.getMail());
            } else {
                resultParam = smsService.validate(param.getMobile(), param.getVerification_code());//??????redis????????????
                masterInfoEntity1.setMobile(param.getMobile());
            }
            if (resultParam.getCode() != 0) {//??????
                return resultParam;
            } else {
                if (StringUtil.isEmpty(param.getInvitees_id())) {
                    param.setInvitees_id("");
                }
                masterInfoEntity1.setInvitees_id(param.getInvitees_id());
                masterInfoEntity1.setPageSize(1);
                List<MasterInfoEntity> query = mapper.queryListPage(masterInfoEntity1);
                if (query != null && query.size() > 0) {//????????? return
                    return ResultUtil.error(UserEum.user_10042.getCode(), UserEum.user_10042.getDesc());
                } else {
                    //??????userEntity
                    UserEntity userEntity = new UserEntity();
                    userEntity.setLogin_name(param.getMobile() == null ? param.getMail() : param.getMobile());
                    userEntity.setNick_name(param.getFull_name());
                    userEntity.setInvitation_code(param.getInvitees_id());
                    userEntity.setRegister_platform("mechanism_master");
                    ResultParam insert1 = userService.insert(userEntity, request, response);
                    if (insert1.getCode() == 0 && StringUtil.isNotEmpty(insert1.getMessage())) {//??????
                        //??????????????????
                        userEntity.setPageSize(1);
                        userEntity = userMapper.queryListPage(userEntity).get(0);
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(userEntity.getUser_id());
                        masterInfoEntity.setPageSize(1);
                        List<MasterInfoEntity> list = mapper.queryListPage(masterInfoEntity);
                        if (list != null && list.size() > 0) {//true return
                            return ResultUtil.error(UserEum.user_10041.getCode(), UserEum.user_10041.getDesc());
                        }
                    }

                    param.setStatus(7);
                    param.setUser_id(userEntity.getUser_id());
                    return super.insert(param, request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    public ResultParam updateFaceVideo(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        int ret = mapper.updateFaceVideo(masterInfoEntity);
        if (ret > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public Map<String, Object> queryByFullName(String nick_name, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            List<MasterInfoEntity> userInfoEntities = mapper.queryByFullName(nick_name);
            ArrayList<Long> list = new ArrayList<>();
            for (MasterInfoEntity infoEntity : userInfoEntities) {
                list.add(infoEntity.getUser_id());
            }
            map.put("userIds", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> querySingleMasterListPage(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterInfoEntity> list = mapper.queryMasterListPage(param);
            Integer total = mapper.queryMasterCountNum(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setUser_id(rechargeRecordEntity.getUser_id());
            userStudyCardEntity.setType(rechargeRecordEntity.getStudy_type());
            userStudyCardEntity.setMaster_id(rechargeRecordEntity.getMaster_id());
            userStudyCardEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
            userStudyCardEntity.setStudycard_id(rechargeRecordEntity.getStudycard_id());
            userStudyCardEntity.setPageSize(1);
            userStudyCardEntity = userStudyCardMapper.queryListPage(userStudyCardEntity).get(0);
            if (userStudyCardEntity.getBinding_mechanism_id() != 0) {
                rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.95")));
            } else {
                rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.85")));
            }
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setUser_id(rechargeRecordEntity.getMaster_id());
            masterInfoEntity.setType("live_lecturer");
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            List<MasterInfoEntity> list = mapper.queryListPage(masterInfoEntity);
            if (list != null && list.size() > 0) {
                masterInfoEntity = list.get(0);
                if (!"".equals(masterInfoEntity.getInvitees_id())) {
                    UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterInfoEntity.getInvitees_id());
                    UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                    BigDecimal amount = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getMaster_bonus().toString()));
                    rechargeRecordEntity.setAmount(rechargeRecordEntity.getAmount().subtract(amount));
                    userRecommenderIncomeLogEntity.setInvitation_id(userRecommenderEntity.getId());
                    /**
                     * ??????????????????
                     */
                    UserRecommenderIncomeLogEntity recommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                    recommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                    recommenderIncomeLogEntity.setIs_settlement(false);
                    recommenderIncomeLogEntity.setCash(amount);
                    recommenderIncomeLogEntity.setDuration(userEarnRoleEntity.getDuration());
                    recommenderIncomeLogEntity.setType("master_offline");
                    recommenderIncomeLogEntity.setCash_describe("??????????????????????????????");
                    recommenderIncomeLogEntity.setRecommender_type("master_recommender");
                    recommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                    recommenderIncomeLogEntity.setRecharge_record_id(rechargeRecordEntity.getId());
                    recommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                    recommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                    ResultParam insert = userRecommenderIncomeLogService.insert(recommenderIncomeLogEntity, request, response);
                    if (insert.getCode() == 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setPush_type("master_offline");
                        pushMessageParam.setOpera_type("master_offline");
                        pushMessageParam.setType("master_offline");
                        pushMessageParam.setType_id(recommenderIncomeLogEntity.getId());
                        pushMessageParam.setSend_id(0L);
                        pushMessageParam.setTarget_id(userRecommenderEntity.getUser_id());
                        pushMessageParam.setContent("??????????????????????????????");
                        pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    }
                }

                userRecommenderIncomeLogEntity.setCash(rechargeRecordEntity.getAmount());
                userRecommenderIncomeLogEntity.setIs_settlement(false);
                userRecommenderIncomeLogEntity.setType("sale_course");
                userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
                userRecommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
                userRecommenderIncomeLogEntity.setRecharge_record_id(rechargeRecordEntity.getId());
                userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                if (userRecommenderIncomeLogEntity.getRecommender_id() == null) {
                    userRecommenderIncomeLogEntity.setRecommender_id(0L);
                }
                userRecommenderIncomeLogEntity.setRole_id(0L);
                ResultParam insert = userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                if (insert.getCode() == 0) {
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setPush_type("sale_course");
                    pushMessageParam.setOpera_type("sale_course");
                    pushMessageParam.setType("sale_course");
                    pushMessageParam.setType_id(userRecommenderIncomeLogEntity.getId());
                    pushMessageParam.setSend_id(0L);
                    pushMessageParam.setTarget_id(masterInfoEntity.getUser_id());
                    pushMessageParam.setContent("??????????????????????????????");
                    pushMessageParam.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public ResultParam updateMechanismID(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        MasterInfoEntity masterInfoEntity = mapper.selectByPrimaryKey(param.getId());
        masterInfoEntity.setMechanism_id(param.getMechanism_id());
        param.setUser_id(masterInfoEntity.getUser_id());
        Integer res = mapper.updateMechanismID(param);
        if (res <= 0) {
            return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
        }

        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(masterInfoEntity.getUser_id());
        userInfoEntity.setAdmin_id(param.getMechanism_id());
        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
        return ResultUtil.success();
    }

    @Override
    public Map<String, Object> queryMechanismCount(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            HashMap<Object, Object> helperMap = Maps.newHashMap();
            Integer helperCount = mapper.queryMasterCount(masterInfoEntity);
            helperMap.put("helperCount", helperCount);

            masterInfoEntity.setLanguage("English");
            Integer englishCount = mapper.queryMasterCount(masterInfoEntity);
            helperMap.put("englishCount", englishCount);

            masterInfoEntity.setLanguage("Japanese");
            Integer japaneseCount = mapper.queryMasterCount(masterInfoEntity);
            helperMap.put("japaneseCount", japaneseCount);

            masterInfoEntity.setLanguage("French");
            Integer frenchCount = mapper.queryMasterCount(masterInfoEntity);
            helperMap.put("frenchCount", frenchCount);

            int otherCount = helperCount - englishCount - japaneseCount - frenchCount;
            helperMap.put("otherCount", otherCount);

            map.put("helperMap", helperMap);

            HashMap<Object, Object> privateMap = Maps.newHashMap();
            masterInfoEntity.setType("private_education");
            Integer privateCount = mapper.queryMasterCount(masterInfoEntity);
            privateMap.put("privateCount", privateCount);

            masterInfoEntity.setLanguage("English");
            englishCount = mapper.queryMasterCount(masterInfoEntity);
            privateMap.put("englishCount", englishCount);

            masterInfoEntity.setLanguage("Japanese");
            japaneseCount = mapper.queryMasterCount(masterInfoEntity);
            privateMap.put("japaneseCount", japaneseCount);

            masterInfoEntity.setLanguage("French");
            frenchCount = mapper.queryMasterCount(masterInfoEntity);
            privateMap.put("frenchCount", frenchCount);

            otherCount = helperCount - englishCount - japaneseCount - frenchCount;
            privateMap.put("otherCount", otherCount);

            map.put("privateMap", privateMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMechanismHelperList(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterInfoEntity> list = mapper.queryMasterInfoListPage(masterInfoEntity);
            Integer total = mapper.queryMasterInfoListPageCount(masterInfoEntity);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryRecommendMasterInfoPC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            param.setStatus(2);
            List<MasterInfoEntity> masterInfoEntities = mapper.queryRecommendMasterInfo(param);
            Integer total = mapper.queryRecommendMasterInfoCount(param);
            if (total > 0) {
                for (MasterInfoEntity entity : masterInfoEntities) {
                    if (entity.getRecommend_video() == null || entity.getRecommend_video().equals("")) {
                        entity.setRecommend_video(entity.getTeach_language_url());
                        entity.setCover(entity.getTeach_language_url() + "?vframe/jpg/offset/0");
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMaster_id(entity.getUser_id());
                    Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                    map.put("studentCount", studentCount);//????????????
                    entity.setMap(map);
                }
            }
            map1.put("total", total);
            map1.put("rows", masterInfoEntities);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl   queryRecommendMasterInfoPC");
        }
        return map1;
    }

    @Override
    public Map<String, Object> queryMasterListPagePC(MasterInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<MasterInfoEntity> masterInfoEntities;
            Integer total = 0;
            if (param.getType() != null && "private_education".equalsIgnoreCase(param.getType())) {
                masterInfoEntities = mapper.queryPrivateListPage(param);
                total = mapper.queryPrivateListPageCount(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryPrivateListPage(param);
                    total = mapper.queryPrivateListPageCount(param);
                }
            } else {
                total = mapper.queryMasterListPageCountPC(param);
                masterInfoEntities = mapper.queryMasterListPage(param);
                if (masterInfoEntities == null || masterInfoEntities.size() == 0) {
                    param.setRecommend_type(null);
                    masterInfoEntities = mapper.queryMasterListPage(param);
                    total = mapper.queryMasterListPageCountPC(param);
                }
            }
            for (MasterInfoEntity entity : masterInfoEntities) {
                Map<String, Object> map = new HashMap<>();
                MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                masterTypeEntity.setType(entity.getType());
                masterTypeEntity.setPageSize(1);
                List<MasterTypeEntity> list = masterTypeMapper.queryListPage(masterTypeEntity);
                if (list != null && list.size() > 0) {
                    masterTypeEntity = list.get(0);
                }
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setMaster_id(entity.getUser_id());
                Integer courseCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                userAppointmentEntity.setStatus(2);
                map.put("courseCount", courseCount);//????????????
                Integer residueCount = userAppointmentMapper.queryCourseCount(userAppointmentEntity);
                map.put("residueCount", residueCount);//????????????
                userAppointmentEntity.setStatus(3);
                Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                map.put("classCount", classCount);//????????????
                userAppointmentEntity.setStatus(3);
                map.put("mastertype", masterTypeEntity);//???????????????
                Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                map.put("studentCount", studentCount);//????????????
                Integer currentStudentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                map.put("currentStudentCount", currentStudentCount);//???????????????
                List<UserAppointmentEntity> userAppointmentEntities1 = userAppointmentMapper.queryLatelyCourse(userAppointmentEntity);
                map.put("latelyclass", userAppointmentEntities1);//????????????
                map.put("totalIncome", 0);//????????????
                map.put("monthlyEarnings", 0);//?????????
                entity.setMap(map);
            }

            map1.put("total", total);
            map1.put("rows", masterInfoEntities);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterInfoServiceImpl        queryMasterListPage");
        }
        return map1;
    }

    @Override
    public List<MasterInfoEntity> queryByLanguage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = Lists.newArrayList();
        try {
            List<MasterInfoEntity> list1 = mapper.queryByMessage(masterInfoEntity);
            if (list1 != null && list1.size() > 0) {
                this.setMasterInfo(list1);
                List<MasterInfoEntity> objects = Lists.newArrayList();
                for (MasterInfoEntity infoEntity : list1) {
                    if (infoEntity.getIs_recommend()) {
                        list.add(infoEntity);
                    } else {
                        objects.add(infoEntity);
                    }
                }
                Collections.shuffle(objects);
                list.addAll(objects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<MasterInfoEntity> queryListPage(MasterInfoEntity masterInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MasterInfoEntity> list = mapper.queryList(masterInfoEntity);
        if (list != null && list.size() > 0) {
            this.setMasterInfo(list);
        }
        return list;
    }

    private void setMasterInfo(List<MasterInfoEntity> list) {
        for (MasterInfoEntity infoEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setMaster_id(infoEntity.getUser_id());
            userAppointmentEntity.setStatus(3);
            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(infoEntity.getUser_id());
            Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("classCount", classCount);//????????????
            Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
            map.put("studentCount", studentCount);//????????????
            if (infoEntity.getMechanism_id()!=0){
                map.put("mechanism_name", masterMechanismMapper.selectByPrimaryKey(infoEntity.getMechanism_id()).getMechanism_name());
            }
            else if (userInfoEntity.getAdmin_id()!=0){
                map.put("mechanism_name", masterMechanismMapper.selectByPrimaryKey(userInfoEntity.getAdmin_id()).getMechanism_name());
            }
            infoEntity.setMap(map);
        }
    }
}
