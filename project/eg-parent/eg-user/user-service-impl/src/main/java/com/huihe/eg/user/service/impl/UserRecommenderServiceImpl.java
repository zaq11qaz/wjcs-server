package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.UserRecommenderEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.dao.UserRecommenderService;
import com.huihe.eg.user.service.dao.UserService;
import com.huihe.eg.user.service.impl.mail.MailService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserRecommenderServiceImpl extends BaseFrameworkServiceImpl<UserRecommenderEntity, Long> implements UserRecommenderService {

    @Resource
    private UserRecommenderMapper mapper;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private ClassRecordMapper classRecordMapper;
    @Resource
    private SmsService smsService;
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserRecommenderEntity userRecommenderEntity, HttpServletRequest request, HttpServletResponse response) {
        UserRecommenderEntity userRecommenderEntity1 = new UserRecommenderEntity();
        userRecommenderEntity1.setType(userRecommenderEntity.getType());
        userRecommenderEntity1.setUser_id(userRecommenderEntity.getUser_id());
        userRecommenderEntity1.setPageSize(1);
        List<UserRecommenderEntity> query = mapper.queryListPage(userRecommenderEntity1);
        if (query != null && query.size() > 0) {
            userRecommenderEntity.setStatus(1);
            userRecommenderEntity.setId(query.get(0).getId());
            userRecommenderEntity.setRefuse_contect("");
            return super.update(userRecommenderEntity, request, response);
        }
        StringBuilder stringBuilder = new StringBuilder(userRecommenderEntity.getUser_id().toString());
        Random random = new Random();
        while (stringBuilder.length() < 8) {
            stringBuilder.append(random.nextInt(10));
        }
        userRecommenderEntity.setInvate_code(stringBuilder.toString());
        return super.insert(userRecommenderEntity, request, response);
    }

    @Override
    public List<UserRecommenderEntity> queryListPage(UserRecommenderEntity userRecommenderEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderEntity> userRecommenderEntities = mapper.queryListPage(userRecommenderEntity);
        for (UserRecommenderEntity recommenderEntity : userRecommenderEntities) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(recommenderEntity.getUser_id() + "userinfo")));
            recommenderEntity.setMap(map);
        }
        return userRecommenderEntities;
    }

    @Override
    public ResultParam recommenderAudit(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(param);
            if (ret > 0) {
                param = mapper.selectByPrimaryKey(param.getId());
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(param.getUser_id());//????????????id

                pushMessageParam.setType_id(param.getId());
                pushMessageParam.setTitle("?????????????????????");   //????????????
                if ("mechanism_recommender".equals(param.getType())) {
                    if (param.getStatus() == 2) {//??????
                        pushMessageParam.setType("recommenderPass");//????????????
                        pushMessageParam.setOpera_type("recommenderPass");//??????????????????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMechanism_recommender_id(param.getId());
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("??????????????????????????????");
                        smsService.sendPassSms(param.getPhone() == 0 ? param.getEmail() : param.getPhone().toString(), "??????????????????????????????");
                    } else {
                        pushMessageParam.setType("recommenderRefuse");//????????????
                        pushMessageParam.setOpera_type("recommenderRefuse");//??????????????????
                        pushMessageParam.setContent("??????????????????????????????");
                        smsService.sendPassSms(param.getPhone() == 0 ? param.getEmail() : param.getPhone().toString(), "??????????????????????????????");
                    }
                }
                if ("master_recommender".equals(param.getType())) {
                    if (param.getStatus() == 2) {//??????
                        pushMessageParam.setType("recommenderPass");//????????????
                        pushMessageParam.setOpera_type("recommenderPass");//??????????????????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMaster_recommender_id(param.getId());
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("??????????????????????????????");
                        smsService.sendPassSms(param.getPhone().toString(), "??????????????????????????????");
                    } else {
                        pushMessageParam.setType("recommenderRefuse");//????????????
                        pushMessageParam.setOpera_type("recommenderRefuse");//??????????????????
                        pushMessageParam.setContent("??????????????????????????????");
                        smsService.sendPassSms(param.getPhone().toString(), "??????????????????????????????");
                    }
                }
                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("updateAuthMessage", pushMessageParam);
                return ResultUtil.success(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam queryEarningsThisMonth(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setStatus(2);
            param.setPageSize(1);
            List<UserRecommenderEntity> queryUserRecommender = mapper.queryListPage(param);
            if (queryUserRecommender != null && queryUserRecommender.size() > 0) {
                for (UserRecommenderEntity userRecommenderEntity : queryUserRecommender) {
                    BigDecimal earnTotal = new BigDecimal("0");
                    UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                    if ("mechanism_recommender".equals(param.getType())) {
                        MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                        masterMechanismEntity.setRecommender_id(param.getInvate_code());
                        Integer newAdded = masterMechanismMapper.queryMonthNewlyAdded(masterMechanismEntity);
                        BigDecimal mechanismInviteSum = userEarnRoleEntity.getEvery_mechanism_earn().multiply(new BigDecimal(newAdded.toString()));
                        map.put("mechanismInviteSum", mechanismInviteSum);//??????????????????

                        earnTotal = earnTotal.add(mechanismInviteSum);//?????????????????????

                        BigDecimal masterEarnTotal = new BigDecimal("0");//????????????????????????
                        BigDecimal appointmentEarnTotal = new BigDecimal("0");//????????????????????????
                        Integer peopleSum = 0;//?????????????????????????????????
                        BigDecimal mechanismRechargeEarlTotal = new BigDecimal("0");//??????????????????
                        List<MasterMechanismEntity> queryMasterMechanism = masterMechanismMapper.queryMonthNewlyAddedList(masterMechanismEntity);
                        if (queryMasterMechanism != null && queryMasterMechanism.size() > 0) {
                            for (MasterMechanismEntity mechanismEntity : queryMasterMechanism) {
                                UserInfoEntity userInfoEntity = new UserInfoEntity();
                                userInfoEntity.setAdmin_id(mechanismEntity.getId());
                                Integer masterCount = userInfoMapper.queryMasterCountTotal(userInfoEntity);
                                BigDecimal masterEarn = userEarnRoleEntity.getEvery_master_earn().multiply(new BigDecimal(masterCount.toString()));
                                if (masterEarn.compareTo(userEarnRoleEntity.getEvery_master_earn_max()) < 0) {//????????? ??????????????????
                                    masterEarn = userEarnRoleEntity.getEvery_master_earn_max();
                                }

                                masterEarnTotal = masterEarnTotal.add(masterEarn);

                                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                                masterAppointmentEntity.setMechanism_id(masterMechanismEntity.getId());
                                Integer mechanismAppointmentCount = masterAppointmentMapper.queryMechanismAppointmentCount(masterAppointmentEntity);
                                BigDecimal appointmentEarn = userEarnRoleEntity.getEvery_commodity_earn().multiply(new BigDecimal(mechanismAppointmentCount.toString()));
                                if (appointmentEarn.compareTo(userEarnRoleEntity.getEvery_commodity_earn_max()) < 0) {
                                    appointmentEarn = userEarnRoleEntity.getEvery_commodity_earn_max();
                                }
                                appointmentEarnTotal = appointmentEarnTotal.add(appointmentEarn);

                                peopleSum = queryAppointmentStudentSum(mechanismEntity);

                                mechanismRechargeEarlTotal = mechanismRechargeEarlTotal.add(rechargeRecordMapper.queryMechanismMounthTotal(mechanismEntity.getId()));
                            }

                        }
                        int peopleEachSum = peopleSum / userEarnRoleEntity.getEach_student();//????????????
                        BigDecimal studentEarnTotal = userEarnRoleEntity.getEach_earl().multiply(new BigDecimal(Integer.toString(peopleEachSum)));//???????????????????????????
                        mechanismRechargeEarlTotal = mechanismRechargeEarlTotal.multiply(new BigDecimal(userEarnRoleEntity.getMechanism_bonus().toString()));//??????????????????????????????
                        map.put("studentEarnTotal", studentEarnTotal);//??????????????????
                        map.put("masterEarnTotal", masterEarnTotal);//????????????????????????
                        map.put("appointmentEarnTotal", appointmentEarnTotal);//????????????????????????
                        map.put("mechanismRechargeEarlTotal", mechanismRechargeEarlTotal);//????????????????????????
                        earnTotal = earnTotal.add(studentEarnTotal);
                        earnTotal = earnTotal.add(masterEarnTotal);
                        earnTotal = earnTotal.add(appointmentEarnTotal);
                        earnTotal = earnTotal.add(mechanismRechargeEarlTotal);
                        map.put("earnTotal", earnTotal);

                    } else {
                        //???????????????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setInvitees_id(param.getUser_id());
                        BigDecimal eachTotalEarn;//????????????
                        List<UserInfoEntity> query = userInfoMapper.queryThisMonth(userInfoEntity);
                        if (query != null && query.size() > 0) {
                            Integer eachCouontNum;
                            Integer studentTotal;

                            userInfoEntity.setCountry("??????");
                            List<MasterInfoEntity> list1 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(list1.size() + ""));
                            map.put("usaInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);
                            List<UserInfoEntity> userInfoList1 = masterInfoList2UserInfoList(list1);
                            Integer usaCountNum = querFirstAppointmentCount(userInfoList1);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * usaCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("?????????");
                            List<MasterInfoEntity> list2 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_newzealand_canada_australia().multiply(new BigDecimal(list2.size() + ""));
                            map.put("newzealandInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);
                            List<UserInfoEntity> userInfoList2 = masterInfoList2UserInfoList(list2);
                            Integer newzealandCountNum = querFirstAppointmentCount(userInfoList2);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * newzealandCountNum + ""));
                            map.put("newzealandFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("?????????");
                            List<MasterInfoEntity> list3 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_newzealand_canada_australia().multiply(new BigDecimal(list3.size() + ""));
                            map.put("canadaInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            List<UserInfoEntity> userInfoList3 = masterInfoList2UserInfoList(list1);
                            Integer canadaCountNum = querFirstAppointmentCount(userInfoList3);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * canadaCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("????????????");
                            List<MasterInfoEntity> list4 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_newzealand_canada_australia().multiply(new BigDecimal(list4.size() + ""));
                            map.put("australiaInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            List<UserInfoEntity> userInfoList4 = masterInfoList2UserInfoList(list4);
                            Integer australiaCountNum = querFirstAppointmentCount(userInfoList4);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * australiaCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("??????");
                            List<MasterInfoEntity> list5 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_britain().multiply(new BigDecimal(list5.size() + ""));
                            map.put("britainInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            List<UserInfoEntity> userInfoList5 = masterInfoList2UserInfoList(list5);
                            Integer britainCountNum = querFirstAppointmentCount(userInfoList5);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * britainCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("??????");
                            List<MasterInfoEntity> list6 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_southafrica_europe().multiply(new BigDecimal(list6.size() + ""));
                            map.put("southafricaInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            List<UserInfoEntity> userInfoList6 = masterInfoList2UserInfoList(list6);
                            Integer southafricaCountNum = querFirstAppointmentCount(userInfoList6);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * southafricaCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            userInfoEntity.setCountry("??????");
                            List<MasterInfoEntity> list7 = queryEachLanguageList(userInfoEntity);
                            eachTotalEarn = userEarnRoleEntity.getEach_master_southafrica_europe().multiply(new BigDecimal(list7.size() + ""));
                            map.put("europeInviteEarn", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            List<UserInfoEntity> userInfoList7 = masterInfoList2UserInfoList(list7);
                            Integer europeCountNum = querFirstAppointmentCount(userInfoList7);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * europeCountNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            //??????
                            Integer elseNum = query.size() - list1.size() - list2.size() - list3.size() - list4.size() - list5.size() - list6.size() - list7.size();
                            eachTotalEarn = userEarnRoleEntity.getEach_master_else().multiply(new BigDecimal(elseNum + ""));
                            map.put("elseInviteEarn", eachTotalEarn);

                            eachCouontNum = querFirstAppointmentCount(query);//?????????
                            map.put("firstCourseTotal", eachCouontNum);

                            eachCouontNum = eachCouontNum - usaCountNum - newzealandCountNum - canadaCountNum - australiaCountNum - britainCountNum - southafricaCountNum - europeCountNum;
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * eachCouontNum + ""));
                            map.put("eachFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);

                            eachCouontNum = querFirstAppointmentCount(query);//?????????
                            eachTotalEarn = userEarnRoleEntity.getEach_master_usa().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln() * eachCouontNum + ""));
                            map.put("usaFirstCourse", eachTotalEarn);
                            earnTotal = earnTotal.add(eachTotalEarn);


                            studentTotal = querStudentTotal(query);//?????????
                            BigDecimal studentEarnTotal = userEarnRoleEntity.getEach_earl().multiply(new BigDecimal(studentTotal / userEarnRoleEntity.getEach_student() + ""));
                            map.put("studentEarnTotal", studentEarnTotal);
                            earnTotal = earnTotal.add(studentEarnTotal);

                            map.put("earnTotal", earnTotal);
                        }
                    }
                    userRecommenderEntity.setEarnings_this_month(earnTotal);
                    mapper.updateByPrimaryKeySelective(userRecommenderEntity);
                }

            } else {
                return ResultUtil.error(UserRecommenderEum.UserRecommenderEum_11111.getCode(), UserRecommenderEum.UserRecommenderEum_11111.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam insertMechismRecommender(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {

            ResultParam resultParam = this.checkLoginName(param);
            if (resultParam.getCode() != 0) {
                return resultParam;
            }
            param.setPageSize(1);
            List<UserRecommenderEntity> query = mapper.queryListPage(param);
            if (query != null && query.size() > 0) {//?????????
                return ResultUtil.error(UserEum.user_10042.getCode(), UserEum.user_10042.getDesc());
            }
            //?????? ??????userEntity
            UserEntity userEntity = new UserEntity();
            userEntity.setNick_name(param.getName());
            userEntity.setLogin_name(param.getPhone() == null ? param.getEmail() : param.getPhone().toString());
            userEntity.setRegister_platform("recommend");
            ResultParam insert1 = userService.insert(userEntity, request, response);
            if (insert1.getCode() == 0) {//??????
                userEntity.setPageSize(1);
                param.setUser_id(userMapper.queryListPage(userEntity).get(0).getUser_id());//??????user_id
                ResultParam insert = this.insert(param, request, response);//??????
                if (insert.getCode() == 0) {
                    return ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private ResultParam checkLoginName(UserRecommenderEntity param) {
        if (StringUtil.isEmpty(param.getVerification_code())) {//???????????????
            return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
        }

        switch (param.getRegister_type()) {
            case 1:
                return checkPhone(param);
            case 2:
                return checkEmail(param);
            default:
                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
        }
    }

    private ResultParam checkEmail(UserRecommenderEntity param) {
        ResultParam resultParam = mailService.validate(param.getEmail(), param.getVerification_code());//??????redis????????????
        if (resultParam.getCode() != 0) {//??????
            return resultParam;
        }
        return ResultUtil.success();
    }

    private ResultParam checkPhone(UserRecommenderEntity param) {
        ResultParam resultParam = smsService.validate(param.getPhone().toString(), param.getVerification_code());//??????redis????????????
        if (resultParam.getCode() != 0) {//??????
            return resultParam;
        }
        return ResultUtil.success();
    }

    @Override
    public List<UserRecommenderEntity> queryMyMechanismRecommenderInfo(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderEntity> list = Lists.newArrayList();
        try {
            UserRecommenderEntity userRecommenderEntity = mapper.selectByPrimaryKey(param.getId());
            HashMap<String, Object> map = new HashMap<>();
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
            userRecommenderIncomeLogEntity = userRecommenderIncomeLogMapper.queryCashList(userRecommenderIncomeLogEntity);
            map.put("latelyUserRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);

            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
            userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
            userRecommenderIncomeLogEntity1.setType("mechanism");
            List<UserRecommenderIncomeLogEntity> query = userRecommenderIncomeLogMapper.query(userRecommenderIncomeLogEntity1);
            List<MasterMechanismEntity> list1 = Lists.newArrayList();
            if (query != null && query.size() > 0) {
                for (UserRecommenderIncomeLogEntity recommenderIncomeLogEntity : query) {
                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(recommenderIncomeLogEntity.getMechanism_id());
                    list1.add(mechanismEntity);
                }
            }
            map.put("inviteMechanismList", list1);

            userRecommenderIncomeLogEntity1.setType("master");
            List<UserRecommenderIncomeLogEntity> query2 = userRecommenderIncomeLogMapper.query(userRecommenderIncomeLogEntity1);
            List<MasterInfoEntity> list2 = Lists.newArrayList();
            if (query2 != null && query2.size() > 0) {
                for (UserRecommenderIncomeLogEntity recommenderIncomeLogEntity : query2) {
                    MasterInfoEntity masterInfoEntity = masterInfoMapper.selectByPrimaryKey(recommenderIncomeLogEntity.getMaster_id());
                    list2.add(masterInfoEntity);
                }
            }
            map.put("inviteMasterList", list2);
            userRecommenderEntity.setMap(map);
            list.add(userRecommenderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryRecommenderCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setStatus(1);
            Integer waitCount = mapper.queryListPageCount(param);
            map.put("waitCount", waitCount);

            param.setStatus(2);
            Integer passCount = mapper.queryListPageCount(param);
            map.put("passCount", passCount);

            param.setStatus(3);
            Integer refuseCount = mapper.queryListPageCount(param);
            map.put("refuseCount", refuseCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
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
            List<UserRecommenderEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                for (UserRecommenderEntity userRecommenderEntity : list) {
                    Map<String, Object> objectMap = new HashMap<>();
                    objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(userRecommenderEntity.getUser_id() + "userinfo")));
                    userRecommenderEntity.setMap(objectMap);
                }
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryRecommenderListCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map = Maps.newHashMap();

        try {
            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount",dayCount);

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount",weekCount);

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount",monthCount);

            Integer totalCount = mapper.queryTotalCount(param);
            map.put("totalCount",totalCount);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public synchronized Object updateShareCount(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {

            param.setType("share_recommender");
            List<UserRecommenderEntity> list = mapper.queryListPage(param);
            if (list!=null&&list.size()>0){
                param = list.get(0);
            }else {
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
                param.setName(userInfoEntity.getNick_name());
                param.setStatus(2);
                param.setInvate_code(userInfoEntity.getInvite_code());
                param.setRegister_type(3);
                param.setPhone(Long.valueOf(userInfoEntity.getMobile()));
                int i = mapper.insertSelective(param);
            }
            int i = mapper.updateShareCount(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaseMap queryGroupListByUserId(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        BaseMap baseMap = new BaseMap();
        try {
            List<UserRecommenderEntity> list = mapper.queryGroupListByUserId(param.getUser_id());
            Integer total = mapper.queryGroupListByUserIdCount(param.getUser_id());
            if (total>0){
            }
            baseMap.setRows(list);
            baseMap.setTotal(total);
        }catch (Exception e){
            e.printStackTrace();
        }
        return baseMap;
    }

    @Override
    public List<UserRecommenderEntity> queryGroupUserDetail(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserRecommenderEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryGroupUserDetail(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //masterinfo ??? userinfo
    private List<UserInfoEntity> masterInfoList2UserInfoList(List<MasterInfoEntity> list1) {
        List<UserInfoEntity> list = Lists.newArrayList();
        for (MasterInfoEntity masterInfoEntity : list1) {
            list.add(userInfoMapper.selectByPrimaryKey(masterInfoEntity.getUser_id()));
        }
        return list;
    }

    //????????????
    private Integer querStudentTotal(List<UserInfoEntity> list) {
        Integer studentNum = 0;
        if (list != null && list.size() > 0) {

            for (UserInfoEntity userInfoEntity : list) {
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                masterAppointmentEntity.setMaster_id(userInfoEntity.getUser_id());
                masterAppointmentEntity.setType("open_class");
                List<MasterAppointmentEntity> query = masterAppointmentMapper.query(masterAppointmentEntity);
                if (query != null && query.size() > 0) {
                    for (MasterAppointmentEntity appointmentEntity : query) {
                        ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                        classRecordEntity.setCurriculum_id(appointmentEntity.getId());
                        classRecordEntity.setStatus(1);
                        studentNum += classRecordMapper.queryThisMonthCount(classRecordEntity);
                    }
                }
            }
        }
        return studentNum;
    }

    //??????????????????
    private Integer querFirstAppointmentCount(List<UserInfoEntity> list) {
        Integer i = 0;
        if (list != null && list.size() > 0) {
            for (UserInfoEntity UserInfoEntity : list) {
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                masterAppointmentEntity.setMaster_id(UserInfoEntity.getUser_id());
                masterAppointmentEntity.setStatus(2);
                int count = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity);
                if (count != 0) {
                    i++;
                }
            }
        }
        return i;
    }

    private List<MasterInfoEntity> queryEachLanguageList(UserInfoEntity userInfoEntity) {
        List<MasterInfoEntity> list = new ArrayList<>();
        List<UserInfoEntity> query = userInfoMapper.query(userInfoEntity);
        if (query != null && query.size() > 0) {
            for (UserInfoEntity infoEntity : query) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(infoEntity.getUser_id());
                masterInfoEntity.setStatus(2);
                List<MasterInfoEntity> list1 = masterInfoMapper.queryMasterTotal(masterInfoEntity);
                if (list1 != null && list1.size() > 0) {
                    list.addAll(list1);
                }
            }
        }
        return list;
    }

    /**
     * ????????????????????????????????????
     *
     * @param mechanismEntity
     * @return
     * @throws ParseException
     */
    private Integer queryAppointmentStudentSum(MasterMechanismEntity mechanismEntity) throws ParseException {
        Integer peopleSum = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        Date lastDay = cale.getTime();

        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        Date firstDay = cale.getTime();

        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
        masterAppointmentEntity.setStart_time(lastDay);
        masterAppointmentEntity.setEnd_time(firstDay);
        masterAppointmentEntity.setOffset(new BigDecimal("8"));
        masterAppointmentEntity.setMechanism_id(mechanismEntity.getId());
        List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointmentEntity);

        for (MasterAppointmentEntity appointmentEntity : masterAppointmentEntities) {
            ClassRecordEntity classRecordEntity = new ClassRecordEntity();//?????????????????????
            classRecordEntity.setCurriculum_id(appointmentEntity.getId());
            classRecordEntity.setStatus(1);
            peopleSum += classRecordMapper.queryListPageCount(classRecordEntity);
        }
        return peopleSum;
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
    public ResultParam update(UserRecommenderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(param);
            if (ret > 0) {
                param = mapper.query(param).get(0);
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(param.getUser_id());//????????????id
                pushMessageParam.setType("recommender");//????????????
                pushMessageParam.setOpera_type("recommender");//??????????????????
                pushMessageParam.setType_id(param.getId());
                pushMessageParam.setTitle("?????????????????????");   //????????????
                if ("mechanism_recommender".equals(param.getType())) {
                    if (param.getStatus() != 2) {//??????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMechanism_recommender_id(param.getId());
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("????????????????????????,???????????????");
                    } else {
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMechanism_recommender_id(0L);
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("????????????????????????,???????????????");
                    }
                }
                if ("master_recommender".equals(param.getType())) {
                    if (param.getStatus() != 2) {//??????
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMaster_recommender_id(param.getId());
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("??????????????????????????????,???????????????");
                    } else {
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setMaster_recommender_id(0L);
                        userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                        pushMessageParam.setContent("??????????????????????????????,???????????????");
                    }
                }
                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("frozenStatusMessage", pushMessageParam);
                return ResultUtil.success(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}