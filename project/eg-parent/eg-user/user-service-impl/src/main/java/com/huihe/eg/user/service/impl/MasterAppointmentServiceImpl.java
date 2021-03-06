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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.comm.FinalConfigParam;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;
import com.huihe.eg.user.service.dao.pay.PayService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MasterAppointmentServiceImpl extends BaseFrameworkServiceImpl<MasterAppointmentEntity, Long> implements MasterAppointmentService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MasterAppointmentMapper mapper;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private MasterInfoService masterInfoService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private TimeZoneMapper timeZoneMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterCommentService masterCommentService;
    @Resource
    private ClassRecordMapper classRecordMapper;
    @Resource
    private ClassRecordService classRecordService;
    @Resource
    private MasterCommentMapper masterCommentMapper;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private MasterMechanismService masterMechanismService;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private SystemRechargeMapper systemRechargeMapper;
    @Resource
    private SpecialLecturerMapper specialLecturerMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserEarnRoleMapper userEarnRoleMapper;
    @Resource
    private UserRecommenderIncomeLogService userRecommenderIncomeLogService;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;
    @Resource
    private MasterFollowMapper masterFollowMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private MechanismClassesMapper mechanismClassesMapper;
    @Resource
    private PayService payService;

    @Override
    public void init() {
        setMapper(mapper);
    }


    @Override
    public synchronized ResultParam delete(Long aLong, HttpServletRequest request, HttpServletResponse response) {
        MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(aLong);
        if (masterAppointmentEntity.getStatus() == 2 || masterAppointmentEntity.getStatus() == 3) {
            return ResultUtil.error(UserEum.user_10056.getCode(), UserEum.user_10056.getDesc());
        }

        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
        userAppointmentEntity.setAppointment_id(aLong);
        userAppointmentEntity.setStatus(2);
        Integer integer = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
        if (integer > 0) {
            return ResultUtil.error(UserEum.user_10057.getCode(), UserEum.user_10057.getDesc());
        }
        return super.delete(aLong, request, response);
    }

    @SuppressWarnings("AlibabaAvoidNewDateGetTime")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResultParam insert(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /**
             * ???????????????????????? ??????????????????
             */
            if (param.getEntities() != null && StringUtil.isNotEmpty(param.getEntities())) {//????????????
                List<MasterAppointmentEntity> masterAppointmentEntiti = JSONUtils.json2List(param.getEntities(), MasterAppointmentEntity.class);//json??? List??????????????????
                if (masterAppointmentEntiti != null && masterAppointmentEntiti.size() > 0) {//???list??????
                    for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntiti) {//????????????
                        if (masterAppointmentEntity.getOffset() != null && masterAppointmentEntity.getOffset().intValue() != 0) {//??????????????????
                            //????????????
                            if (masterAppointmentEntity.getTitle() != null) {
                                masterAppointmentEntity.setTitle(EmojiParser.parseToHtmlDecimal(masterAppointmentEntity.getTitle()));
                            }
                            if (masterAppointmentEntity.getIntroduction_content() != null) {
                                masterAppointmentEntity.setIntroduction_content(EmojiParser.parseToHtmlDecimal(masterAppointmentEntity.getIntroduction_content()));
                            }
                            MasterAppointmentEntity entity = new MasterAppointmentEntity();
                            entity.setMaster_id(masterAppointmentEntity.getMaster_id());//????????????id
                            entity.setStart_time(masterAppointmentEntity.getStart_time());//??????????????????
                            entity.setOffset(masterAppointmentEntity.getOffset());//????????????
                            List<MasterAppointmentEntity> entityList = mapper.queryAppointmentListPage(entity);
                            if (entityList != null && entityList.size() > 0) {//?????????
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                            } else {
                                String roomId = getRoomId();
                                MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                                masterAppointmentEntity1.setRoom_id(roomId);
                                Integer integer = mapper.queryListPageCount(masterAppointmentEntity1);
                                while (integer > 0) {
                                    roomId = getRoomId();
                                    masterAppointmentEntity1.setRoom_id(roomId);
                                    integer = mapper.queryListPageCount(masterAppointmentEntity1);
                                }
                                masterAppointmentEntity.setRoom_id(roomId);
                                super.insert(masterAppointmentEntity, request, response);//???????????????
                            }

                        } else {
                            return ResultUtil.error(MasterEum.master_40005.getCode(), MasterEum.master_40005.getDesc());
                        }
                    }
                    //???list???
                } else {
                    if (param.getType() != null && ("mechanism_offline".equalsIgnoreCase(param.getType()) || "jointly_class".equalsIgnoreCase(param.getType()))) {//??????????????????????????? ??? ???????????????
                        return super.insert(param, request, response);
                    }
                    MasterAppointmentEntity appointmentEntity = new MasterAppointmentEntity();//?????????????????????
                    appointmentEntity.setMaster_id(param.getMaster_id());//?????????id
                    appointmentEntity.setStart_time(param.getStart_time());//????????????
                    appointmentEntity.setType(param.getType());//????????????
                    List<MasterAppointmentEntity> masterAppointmentEntities = mapper.queryDaySchedule(appointmentEntity);//???????????????????????????
                    for (MasterAppointmentEntity entity : masterAppointmentEntities) {//??????
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//????????????????????????
                        userAppointmentEntity.setAppointment_id(entity.getId());//?????????????????????id
                        userAppointmentEntity.setStatus(2);//?????????
                        List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.query(userAppointmentEntity);//?????????????????????????????????
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {//?????????

                        } else {
                            if ("single_class".equalsIgnoreCase(entity.getType()))//?????????????????????
                            {
                                mapper.deleteByPrimaryKey(entity.getId());//?????????????????????
                            }
                        }
                    }
                }
                /**
                 * ??????????????????
                 */
            } else {//?????????
                if ("open_class".equals(param.getType())) {
                    if (param.getIs_special() != null && param.getIs_special()) {//??????????????????
                        ResultParam resultParam = checkIsSpecial(param.getMaster_id(), request, response);
                        if (resultParam.getCode() != 0) {
                            return resultParam;
                        }
                    }
                }

                if (param.getType() != null && ("mechanism_offline".equalsIgnoreCase(param.getType()) || "jointly_class".equalsIgnoreCase(param.getType()))) {//??????????????????????????? ??? ???????????????
                    //????????????
                    if (param.getTitle() != null) {
                        param.setTitle(EmojiParser.parseToHtmlDecimal(param.getTitle()));
                    }
                    if (param.getIntroduction_content() != null) {
                        param.setIntroduction_content(EmojiParser.parseToHtmlDecimal(param.getIntroduction_content()));
                    }
                    return super.insert(param, request, response);

                    /*
                    param.setPageSize(1);
                    Long id = mapper.queryListPage(param).get(0).getId();
                    param.setId(id);
                    MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                    masterSetPriceEntity.setUser_id(param.getMaster_id());
                    masterSetPriceEntity.setStatus(2);
                    masterSetPriceEntity.setMaster_appointment_id(id);
                    masterSetPriceEntity.setType("mechanism_offline");
                    masterSetPriceEntity.setService_type("mechanism_offline".equals(param.getType()) ? 1 : 2);
                    masterSetPriceEntity.setTitle(param.getTitle());
                    masterSetPriceEntity.setFace_url(param.getCover());
                    masterSetPriceEntity.setIntroduction_content(param.getTeach_program());
                    masterSetPriceEntity.setIntroduction_url(param.getIntroduction_cover());
                    masterSetPriceEntity.setIntroduction_text(param.getIntroduction_content());
                    masterSetPriceEntity.setAmout(param.getAmout());
                    masterSetPriceEntity.setDiscount_amout(param.getDiscount_amout());
                    masterSetPriceEntity.setMechanism_id(param.getMechanism_id());
                    masterSetPriceMapper.insertSelective(masterSetPriceEntity);
                    masterSetPriceEntity.setPageSize(1);
                    Long id1 = masterSetPriceMapper.queryListPage(masterSetPriceEntity).get(0).getId();
                    param.setMaster_set_price_id(id1);
                    mapper.updateByPrimaryKeySelective(param);

                     */
                }

                TimeZoneEntity timeZoneEntity = new TimeZoneEntity();
                timeZoneEntity.setOffset(param.getOffset());
                timeZoneEntity.setPageSize(1);
                List<TimeZoneEntity> timeZoneEntities = timeZoneMapper.queryListPage(timeZoneEntity);
                param.setTimezone_id(timeZoneEntities.get(0).getId());//????????????id
                MasterAppointmentEntity entity = new MasterAppointmentEntity();
                entity.setMaster_id(param.getMaster_id());//????????????id
                entity.setStart_time(param.getStart_time());//??????????????????
                entity.setOffset(param.getOffset());//????????????
                List<MasterAppointmentEntity> entityList = mapper.queryAppointmentListPage(entity);
                if (entityList != null && entityList.size() > 0) {//?????????
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                } else {
                    String roomId = getRoomId();
                    MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                    masterAppointmentEntity1.setRoom_id(roomId);
                    Integer integer = mapper.queryListPageCount(masterAppointmentEntity1);
                    while (integer > 0) {
                        roomId = getRoomId();
                        masterAppointmentEntity1.setRoom_id(roomId);
                        integer = mapper.queryListPageCount(masterAppointmentEntity1);
                    }
                    param.setRoom_id(roomId);

//                    param.setRoom_id(param.getMaster_id().toString() + System.currentTimeMillis() / 1000);
                    //????????????
                    if (param.getTitle() != null) {
                        param.setTitle(EmojiParser.parseToHtmlDecimal(param.getTitle()));
                    }
                    if (param.getIntroduction_content() != null) {
                        param.setIntroduction_content(EmojiParser.parseToHtmlDecimal(param.getIntroduction_content()));
                    }
                    return super.insert(param, request, response);//???????????????????????????
                }
//                param.setRoom_id(param.getMaster_id().toString() + System.currentTimeMillis() / 1000);
//                return super.insert(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterAppointmentServiceImpl    insert");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.success();
    }

    public static String getRoomId() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * ???????????????????????????
     *
     * @param master_id
     * @return
     */
    @Override
    public ResultParam checkIsSpecial(Long master_id, HttpServletRequest request, HttpServletResponse response) {
        //???????????????????????????
        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
        masterInfoEntity.setUser_id(master_id);
        masterInfoEntity.setType("live_lecturer");
        masterInfoEntity.setPageSize(1);
        List<MasterInfoEntity> query1 = masterInfoMapper.query(masterInfoEntity);
        if (query1 != null && query1.size() > 0) {
            masterInfoEntity = query1.get(0);
        } else {
            return ResultUtil.error(MasterEum.master_40017.getCode(), MasterEum.master_40017.getDesc());
        }
        if (!masterInfoEntity.getIs_special()) {//????????????????????????
            return ResultUtil.error(MasterEum.master_40017.getCode(), MasterEum.master_40017.getDesc());
        }

        SpecialLecturerEntity specialLecturerEntity = new SpecialLecturerEntity();
        specialLecturerEntity.setUser_id(master_id);
        specialLecturerEntity.setPageSize(1);
        List<SpecialLecturerEntity> query = specialLecturerMapper.query(specialLecturerEntity);
        if (query != null && query.size() > 0) {
            specialLecturerEntity = query.get(0);
            MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
            masterAppointmentEntity1.setMaster_id(master_id);
            masterAppointmentEntity1.setIs_special(true);
            masterAppointmentEntity1.setType("open_class");
            Integer integer = mapper.queryDayCount(masterAppointmentEntity1);//???????????????
            if (specialLecturerEntity.getStart_frequency() <= integer) {
                return ResultUtil.error(MasterEum.master_40016.getCode(), MasterEum.master_40016.getDesc());
            }
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam updateCourseMaster(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {

        MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(param.getId());
        MasterAppointmentEntity masterAppointment1 = new MasterAppointmentEntity();
        masterAppointment1.setMaster_id(param.getMaster_id());
        masterAppointment1.setStart_time(masterAppointmentEntity.getStart_time());
        masterAppointment1.setEnd_time(masterAppointmentEntity.getEnd_time());
        masterAppointment1.setOffset(masterAppointmentEntity.getOffset());
        masterAppointment1.setPageSize(1);

        List<MasterAppointmentEntity> masterAppointmentEntities = mapper.querySchedule(masterAppointment1);
        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {//????????????
            masterAppointment1 = masterAppointmentEntities.get(0);
            if (masterAppointment1.getConnect_peoplenum() > 0) {
                if (masterAppointment1.getTitle().equals(param.getTitle())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(masterAppointment1.getId());
                    Integer integer = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                    if (integer >= masterAppointment1.getConnect_peoplenum()) {
                        return ResultUtil.error(MasterEum.master_40013.getCode(), MasterEum.master_40013.getDesc());
                    }
                } else {
                    return ResultUtil.error(MasterEum.master_40013.getCode(), MasterEum.master_40013.getDesc());
                }
            } else {
                return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
            }
        }
        //??????????????????????????????
        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
        userAppointmentEntity.setStart_time(masterAppointmentEntity.getStart_time());
        userAppointmentEntity.setEnd_time(masterAppointmentEntity.getEnd_time());
        userAppointmentEntity.setOffset(masterAppointmentEntity.getOffset());
        userAppointmentEntity.setTimezone_id(masterAppointmentEntity.getTimezone_id());
        userAppointmentEntity.setUser_id(param.getMaster_id());

        List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryIsAppointmentClass(userAppointmentEntity);
        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
            return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
        }

        UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
        userAppointmentEntity1.setAppointment_id(param.getId());
        List<UserAppointmentEntity> userAppointmentEntities1 = userAppointmentMapper.queryListPage(userAppointmentEntity1);
        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities1) {
            appointmentEntity.setMaster_id(param.getMaster_id());
            userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);
        }
        return super.update(param, request, response);
    }

    @Override
    public Map<String, Object> queryLiveCash(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setPageSize(10000);
            BigDecimal totalCash = new BigDecimal("0");
            List<MasterAppointmentEntity> masterAppointmentEntitiesTotal = this.queryProfitListPage(param, request, response);
            if (masterAppointmentEntitiesTotal != null && masterAppointmentEntitiesTotal.size() > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntitiesTotal) {
                    if (masterAppointmentEntity.getMap() != null && masterAppointmentEntity.getMap().containsKey("finalEarn")) {
                        totalCash = totalCash.add((BigDecimal) masterAppointmentEntity.getMap().get("finalEarn"));
                    }
                }
            }
            map.put("totalCash", totalCash);

            Calendar cMonth = Calendar.getInstance();
            cMonth.setTime(new Date());
            //??????Calendar ?????? Date??????+1???
            cMonth.add(Calendar.MONTH, -1);
            param.setCreate_time(new Date());
            param.setUpdate_time(cMonth.getTime());
            BigDecimal monthCash = new BigDecimal("0");
            List<MasterAppointmentEntity> masterAppointmentEntitiesMonth = this.queryProfitListPage(param, request, response);
            if (masterAppointmentEntitiesMonth != null && masterAppointmentEntitiesMonth.size() > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntitiesMonth) {
                    if (masterAppointmentEntity.getMap() != null && masterAppointmentEntity.getMap().containsKey("finalEarn")) {
                        monthCash = monthCash.add((BigDecimal) masterAppointmentEntity.getMap().get("finalEarn"));
                    }
                }
            }
            map.put("monthCash", monthCash);

            Calendar cDay = Calendar.getInstance();
            cDay.setTime(new Date());
            //??????Calendar ?????? Date??????+1???
            cDay.add(Calendar.DAY_OF_MONTH, -1);
            param.setCreate_time(new Date());
            param.setUpdate_time(cDay.getTime());
            BigDecimal dayCash = new BigDecimal("0");
            List<MasterAppointmentEntity> masterAppointmentEntitiesDay = this.queryProfitListPage(param, request, response);
            if (masterAppointmentEntitiesDay != null && masterAppointmentEntitiesDay.size() > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntitiesDay) {
                    if (masterAppointmentEntity.getMap() != null && masterAppointmentEntity.getMap().containsKey("finalEarn")) {
                        dayCash = dayCash.add((BigDecimal) masterAppointmentEntity.getMap().get("finalEarn"));
                    }
                }
            }
            map.put("dayCash", dayCash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam updateSettlement(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = userRecommenderIncomeLogMapper.selectByPrimaryKey(param.getLog_id());
            if (userRecommenderIncomeLogEntity != null && !userRecommenderIncomeLogEntity.getIs_settlement()) {
                //????????????
                userRecommenderIncomeLogEntity.setIs_settlement(true);
                int i1 = userRecommenderIncomeLogMapper.updateByPrimaryKeySelective(userRecommenderIncomeLogEntity);
                if (i1 < 0) {
                    return ResultUtil.error(UserEum.user_10043.getCode(), UserEum.user_10043.getDesc());
                }
                //??????
                if (userRecommenderIncomeLogEntity.getMechanism_id() != null && userRecommenderIncomeLogEntity.getMechanism_id() != 0) {

                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMechanism_id());

                    if (StringUtil.isNotEmpty(mechanismEntity.getRecommender_id())) {

                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(mechanismEntity.getRecommender_id());
                        UserRecommenderIncomeLogEntity userRecommendedIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                        userRecommendedIncomeLogEntity1.setRecommender_type(userRecommenderEntity.getType());
                        userRecommendedIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                        userRecommendedIncomeLogEntity1.setPageSize(1);
                        userRecommendedIncomeLogEntity1 = userRecommenderIncomeLogMapper.queryListPage(userRecommendedIncomeLogEntity1).get(0);
                        userRecommendedIncomeLogEntity1.setIs_settlement(true);
                        userRecommenderIncomeLogMapper.updateByPrimaryKeySelective(userRecommendedIncomeLogEntity1);

                        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommendedIncomeLogEntity1.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userRecommendedIncomeLogEntity1.getCash().add(userInfoEntity.getCash()));
                        synchronized (userInfoEntity) {
                            userInfoMapper.updateCash(userInfoEntity);
                        }
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                        pushMessageParam.setPush_type("cash");
                        pushMessageParam.setOpera_type("cash");
                        pushMessageParam.setContent("????????????????????????" + param.getMasterRecommenderCash() + "???");
                        pushMessageParam.setType(userRecommenderIncomeLogEntity.getType());
                        pushMessageParam.setSource("pc");
                        pushMessageParam.setTitle("????????????????????????");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    }

                    //????????????
                    mechanismEntity.setCash(mechanismEntity.getCash().add(param.getFinalEarn()));
                    masterMechanismMapper.updateByPrimaryKeySelective(mechanismEntity);

                    //???????????????
                    UserInfoEntity mechanismOwner = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                    mechanismOwner.setCash(mechanismOwner.getCash().add(param.getFinalEarn()));
                    userInfoMapper.updateCash(mechanismOwner);

                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setType_id(userRecommenderIncomeLogEntity.getId());
                    pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
                    pushMessageParam.setPush_type("cash");
                    pushMessageParam.setOpera_type("begin_appointment");
                    pushMessageParam.setContent("??????????????????" + param.getFinalEarn() + "???");
                    pushMessageParam.setType("cash");
                    pushMessageParam.setSource("pc");
                    pushMessageParam.setTitle("??????????????????");
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

                } else {
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setType("live_lecturer");
                    masterInfoEntity.setUser_id(userRecommenderIncomeLogEntity.getMaster_id());
                    masterInfoEntity.setPageSize(1);
                    masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                    if (StringUtil.isNotEmpty(masterInfoEntity.getInvitees_id())) {
                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterInfoEntity.getInvitees_id());
                        UserRecommenderIncomeLogEntity userRecommendedIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                        userRecommendedIncomeLogEntity1.setRecommender_type(userRecommenderEntity.getType());
                        userRecommendedIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                        userRecommendedIncomeLogEntity1.setPageSize(1);
                        userRecommendedIncomeLogEntity1 = userRecommenderIncomeLogMapper.queryListPage(userRecommendedIncomeLogEntity1).get(0);
                        userRecommendedIncomeLogEntity1.setIs_settlement(true);
                        userRecommenderIncomeLogMapper.updateByPrimaryKeySelective(userRecommendedIncomeLogEntity1);

                        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userRecommenderMapper.selectByPrimaryKey(userRecommendedIncomeLogEntity1.getRecommender_id()).getUser_id());
                        userInfoEntity.setCash(userRecommendedIncomeLogEntity1.getCash().add(userInfoEntity.getCash()));
                        synchronized (userInfoEntity) {
                            userInfoMapper.updateCash(userInfoEntity);
                        }

                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(userInfoEntity.getUser_id());
                        pushMessageParam.setPush_type("cash");
                        pushMessageParam.setOpera_type("cash");
                        pushMessageParam.setContent("????????????????????????" + param.getMasterRecommenderCash() + "???");
                        pushMessageParam.setType(userRecommendedIncomeLogEntity1.getType());
                        pushMessageParam.setSource("pc");
                        pushMessageParam.setTitle("????????????????????????");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        //????????????
                        userRecommenderEntity.setEarnings_this_month(userRecommenderEntity.getEarnings_this_month().add(userRecommendedIncomeLogEntity1.getCash()));
                        userRecommenderMapper.updateCash(userRecommenderEntity);
                    }

                    //???????????????
                    UserInfoEntity userOwn = userInfoMapper.selectByPrimaryKey(userRecommenderIncomeLogEntity.getMaster_id());
                    userOwn.setCash(userOwn.getCash().add(userRecommenderIncomeLogEntity.getCash()));
                    userInfoMapper.updateCash(userOwn);

                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setType_id(param.getId());
                    pushMessageParam.setTarget_id(userOwn.getUser_id());
                    pushMessageParam.setPush_type("cash");
                    pushMessageParam.setOpera_type("cash");
                    pushMessageParam.setContent("??????????????????" + param.getMasterRecommenderCash() + "???");
                    pushMessageParam.setType(userRecommenderIncomeLogEntity.getType());
                    pushMessageParam.setSource("pc");
                    pushMessageParam.setTitle("??????????????????");
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);

                }
            }
            return ResultUtil.error(UserEum.user_10043.getCode(), UserEum.user_10043.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /**
     * ????????????
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String, Object> querySettlementList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = mapper.querySettlementList(param);
            Integer total = mapper.querySettlementListCount(param);
            if (list != null && list.size() > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : list) {
                    HashMap<String, Object> map = new HashMap<>();
                    if (masterAppointmentEntity.getMechanism_id() != 0) {
                        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterAppointmentEntity.getMechanism_id());
                        map.put("mechanismEntity", mechanismEntity);
                    }
                    if (masterAppointmentEntity.getMaster_id() != 0) {
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(masterAppointmentEntity.getMaster_id());
                        masterInfoEntity.setType("live_lecturer");
                        List<MasterInfoEntity> list1 = masterInfoMapper.queryListPage(masterInfoEntity);
                        if (list1 != null && list1.size() > 0) {
                            masterInfoEntity = list1.get(0);
                        }
                        map.put("masterInfoEntity", masterInfoEntity);
                    }
                    masterAppointmentEntity.setMap(map);
                }
                map1.put("rows", list);
                map1.put("total", total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map1;
    }

    @Override
    public Map<String, Object> querySoonList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = mapper.querySoonClass(param);
            Integer total = mapper.querySoonClassCount(param);
            if (list != null && list.size() > 0) {
                setListInfo(list, request, response);
            }
            map.put("rows", list);
            map.put("total", total);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryBeginClass(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = mapper.queryBeginList(param);
            Integer total = mapper.queryBeginListPageCount(param);
            if (list != null && list.size() > 0) {
                setListInfo(list, request, response);
            }
            map.put("rows", list);
            map.put("total", total);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryHistoryList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = mapper.queryHistoryListPage(param);
            Integer total = mapper.queryHistoryListPageCount(param);
            if (list != null && list.size() > 0) {
                setListInfo(list, request, response);
            }
            map.put("rows", list);
            map.put("total", total);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMasterAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = mapper.queryListPage(param);
            for (MasterAppointmentEntity entity : list) {
                //????????????
                entity.setTitle(EmojiParser.parseToUnicode(entity.getTitle()));
                entity.setIntroduction_content(EmojiParser.parseToUnicode(entity.getIntroduction_content()));

                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(entity.getIdentity_type());
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> list1 = masterInfoMapper.queryMasterListPage(masterInfoEntity);
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("masterInfo", list1);
                map1.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                entity.setMap(map1);
            }
            Integer total = mapper.queryListPageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMechanismAppCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer totalCount = mapper.queryListPageCount(param);
            map.put("totalCount", totalCount);

            param.setLanguage("English");
            Integer englishCount = mapper.queryListPageCount(param);
            map.put("englishCount", englishCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryMechanismAppointment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = this.queryMechanismAppointmentListPage(param, request, response);
            Integer total = mapper.queryMechanismAppointmentListPageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void updateLikeCount(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateLikeCount(id);
    }

    @Override
    public void updateLikeCountCancel(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateLikeCountCancel(id);
    }

    @Override
    public void updateShareCount(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateShareCount(id);
    }

    @Override
    public void updateCommentCount(Long id, HttpServletRequest request, HttpServletResponse response) {
        mapper.updateCommentCount(id);
    }

    @Override
    @Transactional
    public void updateCash(final RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {

            MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getAppointment_id());
            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);


            UserRecommenderIncomeLogEntity recommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
            recommenderIncomeLogEntity.setIs_settlement(true);
            BigDecimal addCash = rechargeRecordEntity.getAmount().multiply(new BigDecimal(userEarnRoleEntity.getFirst_course_earln().toString()));
            recommenderIncomeLogEntity.setCash(addCash);
            recommenderIncomeLogEntity.setType("system_card");
            recommenderIncomeLogEntity.setCash_describe("??????????????????????????????");
            recommenderIncomeLogEntity.setMastersetprice_id(rechargeRecordEntity.getStudycard_id());
            recommenderIncomeLogEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
            recommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
            recommenderIncomeLogEntity.setAppointment_id(masterAppointmentEntity.getId());
            ResultParam insert = userRecommenderIncomeLogService.insert(recommenderIncomeLogEntity, request, response);

            if (insert.getCode() == 0) {
                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(addCash));
                synchronized (userInfoEntity) {
                    userInfoMapper.updateCash(userInfoEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public Map<String, Object> queryBeginListPageCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = this.queryBeginListPage(param, request, response);
            Integer total = mapper.queryBeginListPageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam queryCourseSettlement(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(param.getId());

            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(param.getStudy_card_id());

            long differenceTime = masterAppointmentEntity.getEnd_time().getTime() - masterAppointmentEntity.getStart_time().getTime();
            int largerThan;

            if (differenceTime > 1200000) {
                largerThan = 25;
            } else {
                largerThan = 15;
            }
            ClassRecordEntity classRecordEntity = new ClassRecordEntity();
            classRecordEntity.setUser_id(masterAppointmentEntity.getMaster_id());
            classRecordEntity.setCurriculum_id(masterAppointmentEntity.getId());
            classRecordEntity.setPageSize(1);
            classRecordEntity = classRecordMapper.queryListPage(classRecordEntity).get(0);
            if (classRecordEntity.getFree_minute() > largerThan) {
                masterAppointmentEntity.setIs_qualified(true);
                if (userStudyCardEntity.getIs_one_time_payment()) {
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setAppointment_id(masterAppointmentEntity.getId());
                    rechargeRecordEntity.setIs_one_time_payment(false);
                    rechargeRecordEntity.setCourse_num(1);
                    rechargeRecordEntity.setPageSize(1);
                    rechargeRecordEntity = rechargeRecordMapper.queryListPage(rechargeRecordEntity).get(0);
                    rechargeRecordEntity.setStatus(2);
                    rechargeRecordEntity.setFinished(true);
                    rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);
                }
                mapper.updateByPrimaryKeySelective(masterAppointmentEntity);
            }
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("courseMinute", classRecordEntity.getFree_minute());
            map.put("is_one_time_payment", userStudyCardEntity.getIs_one_time_payment());
            return ResultUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam updateSingleCourseCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*
            MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getAppointment_id());
            masterAppointmentEntity.setStatus(9);
            mapper.updateByPrimaryKeySelective(masterAppointmentEntity);


            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setUser_id(rechargeRecordEntity.getUser_id());
            userStudyCardEntity.setType(rechargeRecordEntity.getStudy_type());
            userStudyCardEntity.setMaster_id(rechargeRecordEntity.getMaster_id());
            userStudyCardEntity.setMechanism_id(rechargeRecordEntity.getMechanism_id());
            userStudyCardEntity.setStudycard_id(rechargeRecordEntity.getStudycard_id());
            userStudyCardEntity.setPageSize(1);
            List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);
            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                userStudyCardEntity = userStudyCardEntities.get(0);
                UserInfoEntity userInfoEntity;
                if (userStudyCardEntity.getBinding_mechanism_id() == 0) {
                    userInfoEntity = userInfoMapper.selectByPrimaryKey(rechargeRecordEntity.getMaster_id());
                    userInfoEntity.setCash(userInfoEntity.getCash().add(rechargeRecordEntity.getAmount()
                            .multiply(new BigDecimal("0.85"))));
                } else {
                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userStudyCardEntity.getBinding_mechanism_id());
                    userInfoEntity = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                    userInfoEntity.setCash(userInfoEntity.getCash().add(rechargeRecordEntity.getAmount()
                            .multiply(new BigDecimal("0.95"))));
                }
                synchronized (this) {
                    userInfoMapper.updateCash(userInfoEntity);
                }
            }
            todo
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam insertMechanismOfflineCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            //todo
            param.setIs_qualified(false);
            if (param.getStudy_card_id() != null && param.getStudy_card_id() != 0) {
                UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(param.getStudy_card_id());
                if (userStudyCardEntity.getCourse_num() > 0) {
                    if (userStudyCardEntity.getIs_one_time_payment()) {
                        int i = userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                        if (i > 0) {
                            param.setStatus(6);
                            int res = mapper.insertSelective(param);
                            if (res > 0) {
                                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                                rechargeRecordEntity.setAmount(userStudyCardEntity.getEach_lesson_price());
                                rechargeRecordEntity.setUser_id(param.getUser_id());
                                rechargeRecordEntity.setCourse_num(1);
                                rechargeRecordEntity.setStudy_type(userStudyCardEntity.getType());
                                rechargeRecordEntity.setRcharge_type("study_card");
                                rechargeRecordEntity.setStudy_type("mechanism_offline");
                                if (userStudyCardEntity.getIs_Interoperability()) {
                                    rechargeRecordEntity.setMechanism_id(param.getMechanism_id());
                                } else {
                                    rechargeRecordEntity.setMechanism_id(userStudyCardEntity.getMechanism_id());
                                }
                                rechargeRecordEntity.setIs_one_time_payment(false);
                                rechargeRecordEntity.setFlowing_no(CommonUtils.generateFlowingCode());
                                rechargeRecordEntity.setRcharge_abstract("???????????????");
                                rechargeRecordEntity.setAppointment_id(param.getId());
                                rechargeRecordEntity.setFinished(true);
                                rechargeRecordEntity.setNumber_of_lessons(param.getNumber_of_lessons() != null ? param.getNumber_of_lessons().intValue() : 0);
                                rechargeRecordEntity.setIs_teach_paypal(true);
                                int res1 = rechargeRecordMapper.insertSelective(rechargeRecordEntity);
                                if (res1 > 0) {
                                    return ResultUtil.success();
                                }
                            }
                        }
                    } else {
//                    param.setStatus(2);
//                    param.setProfit(userStudyCardEntity.getEach_lesson_price());
//                    int i = mapper.insertSelective(param);
//                    if (i > 0) {
                        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
//                            rechargeRecordEntity.setAmount(param.getAmout());
                        rechargeRecordEntity.setAmount(userStudyCardEntity.getEach_lesson_price());
                        rechargeRecordEntity.setUser_id(param.getUser_id());
                        rechargeRecordEntity.setCourse_num(1);
                        rechargeRecordEntity.setRcharge_type("prepayment");
                        rechargeRecordEntity.setStudy_type(param.getType());
                        rechargeRecordEntity.setMechanism_id(param.getMechanism_id());
                        rechargeRecordEntity.setIs_one_time_payment(false);
                        rechargeRecordEntity.setRcharge_abstract("???????????????");
                        rechargeRecordEntity.setAppointment_id(param.getId());
                        rechargeRecordEntity.setSource(param.getSource());
                        return payService.aliPrepayment(rechargeRecordEntity, request, response);
//                    }
                    }
                } else {
                    return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public List<MasterAppointmentEntity> queryOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryScheduleListPage(param);
            for (MasterAppointmentEntity entity : list) {
                Map<String, Object> map = new HashMap<>();

                if (entity.getStudent_id() != null && entity.getStudent_id() != 0) {
                    map.put("studentInfo", JSONUtils.obj2Json(redisService.getStr(entity.getStudent_id() + "userinfo")));
                } else {
                    map.put("studentInfo", null);
                }

                MasterMechanismEntity mechanismEntity = null;
                if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                    mechanismEntity = masterMechanismMapper.selectByPrimaryKey(entity.getMechanism_id());
                }
                map.put("mechanismEntity", mechanismEntity);

                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> list1 = masterInfoMapper.queryListPage(masterInfoEntity);
                map.put("masterInfoEntity", list1);

                MasterSetPriceEntity masterSetPriceEntity = null;
                if (entity.getMechanism_id() != 0) {
                    masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(entity.getMaster_set_price_id());
                }
                map.put("masterSetPriceEntity", masterSetPriceEntity);
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(rechargeRecordEntity.getAppointment_id());

        if (masterAppointmentEntity.getStatus() == 2) {
            masterAppointmentEntity.setStatus(6);
            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(masterAppointmentEntity.getStudy_card_id());
            int i = userStudyCardMapper.updateCourseNum(userStudyCardEntity);
            if (i > 0) {
                mapper.updateByPrimaryKeySelective(masterAppointmentEntity);
            }
        }
        if (masterAppointmentEntity.getStatus() == 8) {
            ResultParam resultParam = this.updateSingleCourseCash(rechargeRecordEntity, request, response);
        }
    }

    @Override
    public List<MasterAppointmentEntity> queryMechanismOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> list = Lists.newArrayList();
        try {
            if (param.getStatus() != null && param.getStatus() == 1) {
                list = mapper.queryMechanismSchedule(param);
            } else {
                list = mapper.queryEndCourse(param);
            }
            if (list.size() > 0) {
                this.setQueryMechanismOfflineScheduleInfo(list, param);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setQueryMechanismOfflineScheduleInfo(List<MasterAppointmentEntity> list, MasterAppointmentEntity param) {
        for (MasterAppointmentEntity masterAppointmentEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
//            if (param.getStatus() != null && param.getStatus() == 6) {
            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());

            userAppointmentEntity.setStatus(1);
            Integer reservations = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("reservations", reservations);

            userAppointmentEntity.setStatus(3);
            Integer check_in = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("check_in", check_in);

            map.put("totalNum", check_in + reservations);

            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id()));
//            } else if (param.getStatus() != null && param.getStatus() == 9) {
            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());

            userAppointmentEntity.setStatus(8);
            Integer to_be_paid = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("to_be_paid", to_be_paid);

            userAppointmentEntity.setStatus(9);
            Integer paid = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
            map.put("paid", paid);

            map.put("totalNum", paid + to_be_paid);

            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id());
            map.put("masterSetPriceEntity", masterSetPriceEntity);

//            } else {
//            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id()));

            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
            List<UserAppointmentEntity> list1 = userAppointmentMapper.query2and3(userAppointmentEntity);
            map.put("studentNum", list1 == null ? 0 : list1.size());

//        }
            MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
            masterCommentEntity.setMastersetprice_id(masterSetPriceEntity.getId());
            Integer commentCount = masterCommentMapper.queryListPageCount(masterCommentEntity);
            map.put("commentCount", commentCount);

            if (userAppointmentEntity.getMaster_id() != 0) {
                map.put("masterInfo", userInfoMapper.selectByPrimaryKey(userAppointmentEntity.getMaster_id()));
            } else {
                map.put("masterInfo", null);
            }

            masterAppointmentEntity.setMap(map);
        }

    }

    @Override
    public Map<String, Object> queryPayPalEarn(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setStatus(9);
            param.setIdentity_type("teach_paypal_course");
            BigDecimal earnTotal = mapper.queryEarnTotal(param);
            map.put("earnTotal", earnTotal);

            BigDecimal earnToday = mapper.querydayEarn(param);
            map.put("earnToday", earnToday);

            Integer settledNum = mapper.queryListPageCount(param);
            map.put("settledNum", settledNum);

            param.setStatus(8);
            Integer notSettledNum = mapper.queryListPageCount(param);
            map.put("notSettledNum", notSettledNum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterAppointmentEntity> queryPayPalEarnList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryEarnList(param);
            if (list != null && list.size() > 0) {
                this.setinfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResultParam insertMechanismCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {

            Integer integer = mapper.queryListPageCount(param);
            if (integer > 0) {
                return ResultUtil.error(MasterEum.master_40026.getCode(), MasterEum.master_40026.getDesc());
            }
            if (param.getStudy_card_ids() != null) {
                param.setCreate_type("scheduling");
                param.setIs_appointment(true);
                ResultParam insert = super.insert(param, request, response);
                if (insert.getCode() == 0) {
                    String[] ids = param.getStudy_card_ids().split(",");

                    int count = 0;
                    for (String id : ids) {
                        UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(Long.valueOf(id));
                        userStudyCardMapper.updateCourseNum(userStudyCardEntity);

                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setAppointment_id(param.getId());
                        userAppointmentEntity.setStart_time(param.getStart_time());
                        userAppointmentEntity.setEnd_time(param.getEnd_time());
                        userAppointmentEntity.setOffset(param.getOffset());
                        userAppointmentEntity.setMaster_id(param.getMaster_id());
                        userAppointmentEntity.setMaster_type(param.getType());
                        userAppointmentEntity.setMaster_set_price_id(param.getMaster_set_price_id());
                        userAppointmentEntity.setMechanism_id(param.getMechanism_id());
                        userAppointmentEntity.setIs_pay(!userStudyCardEntity.getIs_one_time_payment());
                        userAppointmentEntity.setStudy_card_id(userStudyCardEntity.getId());
                        userAppointmentEntity.setTitle(param.getTitle());
                        userAppointmentEntity.setUser_id(userStudyCardEntity.getUser_id());
                        userAppointmentEntity.setEarnings(userStudyCardEntity.getEach_lesson_price());
                        userAppointmentEntity.setNumber_of_lessons(param.getNumber_of_lessons());
                        if (param.getStatus() != null) {
                            userAppointmentEntity.setStatus(param.getStatus());
                        }
                        int i = userAppointmentMapper.insertSelective(userAppointmentEntity);
                        if (i == 0) {
                            throw new Exception("????????????");
                        } else {
                            if (count == param.getCount()){
                                break;
                            }
                            if (param.getStatus() != null && param.getStatus() != 0) {

                                PushMessageParam pushMessageParamUser = new PushMessageParam();
                                pushMessageParamUser.setView_type("user");
                                pushMessageParamUser.setType_id(userAppointmentEntity.getId());
                                pushMessageParamUser.setTarget_id(userAppointmentEntity.getUser_id());
                                pushMessageParamUser.setPush_type("appointment_scheduling");
                                pushMessageParamUser.setOpera_type("appointment_scheduling");
                                pushMessageParamUser.setContent("????????????????????????");
                                pushMessageParamUser.setType("appointment_scheduling");
                                pushMessageParamUser.setTitle("????????????????????????");
                                pushMessageParamUser.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamUser);

                                PushMessageParam pushMessageParamMaster = new PushMessageParam();
                                pushMessageParamMaster.setView_type("master");
                                pushMessageParamMaster.setType_id(userAppointmentEntity.getId());
                                pushMessageParamMaster.setTarget_id(userAppointmentEntity.getMaster_id());
                                pushMessageParamMaster.setPush_type("appointment_scheduling");
                                pushMessageParamMaster.setOpera_type("appointment_scheduling");
                                pushMessageParamMaster.setContent("????????????????????????");
                                pushMessageParamMaster.setType("appointment_scheduling");
                                pushMessageParamMaster.setTitle("????????????????????????");
                                pushMessageParamUser.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMaster);

                                PushMessageParam pushMessageParamMechanism = new PushMessageParam();
                                pushMessageParamMechanism.setView_type("mechanism");
                                pushMessageParamMechanism.setType_id(userAppointmentEntity.getId());
                                pushMessageParamMechanism.setTarget_id(userAppointmentEntity.getUser_id());
                                pushMessageParamMechanism.setPush_type("appointment_scheduling");
                                pushMessageParamMechanism.setOpera_type("appointment_scheduling");
                                pushMessageParamMechanism.setContent("??????????????????");
                                pushMessageParamMechanism.setType("appointment_scheduling");
                                pushMessageParamMechanism.setTitle("????????????????????????");
                                pushMessageParamUser.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMechanism);
                            }
                        }
                    }
                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id());
                    if (param.getStatus() != null && param.getStatus() != 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(mechanismEntity.getId());
                        pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
                        pushMessageParam.setPush_type("appointment_scheduling");
                        pushMessageParam.setOpera_type("appointment_scheduling");
                        pushMessageParam.setContent("???????????????????????????");
                        pushMessageParam.setType("appointment_scheduling");
                        pushMessageParam.setTitle("???????????????????????????");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    }

                    return ResultUtil.success(param.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam insertOfflineCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        param.setCreate_type("appointment");
        return super.insert(param, request, response);
    }

    @Override
    public ResultParam queryMechanismCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> list = Lists.newArrayList();
            Integer total;
            if (param.getId() == null) {
                list = mapper.queryMechanismScheduleListPage(param);
//            this.setMechanismInfo(list);
                total = mapper.queryMechanismScheduleListPageCount(param);
                if (total > 0) {
                    this.setMechanismInfo(list);
                }
            } else {
                list.add(mapper.selectByPrimaryKey(param.getId()));
                total = list.size();
                if (total > 0) {
                    this.setMechanismAllInfo(list);
                }
            }

            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public void querySoonMechanismCourse() {
        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
        masterAppointmentEntity.setIdentity_type("teach_paypal_course");
        masterAppointmentEntity.setType("mechanism_offline");
        List<MasterAppointmentEntity> list = mapper.querySoonMechanismCourse(masterAppointmentEntity);
        if (list != null && list.size() > 0) {
            for (MasterAppointmentEntity entity : list) {
                MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(entity.getMechanism_id());
                PushMessageParam pushMessageParamMaster = new PushMessageParam();
                pushMessageParamMaster.setTarget_id(entity.getMaster_id());
                pushMessageParamMaster.setPush_type("offline_course_reminders");
                pushMessageParamMaster.setOpera_type("offline_course_reminders");
                pushMessageParamMaster.setContent(("??????????????????????????????").trim());
                pushMessageParamMaster.setType("offline_course_reminders");
                pushMessageParamMaster.setView_type("master");
                pushMessageParamMaster.setType_id(entity.getId());
                pushMessageParamMaster.setIs_teach_paypal(entity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMaster);

                PushMessageParam pushMessageParamMechanism = new PushMessageParam();
                pushMessageParamMechanism.setTarget_id(mechanismEntity.getUser_id());
                pushMessageParamMechanism.setPush_type("offline_course_reminders");
                pushMessageParamMechanism.setOpera_type("offline_course_reminders");
                pushMessageParamMechanism.setContent(("????????????????????????????????????").trim());
                pushMessageParamMechanism.setType("offline_course_reminders");
                pushMessageParamMechanism.setView_type("mechanism");
                pushMessageParamMechanism.setType_id(entity.getId());
                pushMessageParamMaster.setIs_teach_paypal(entity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMechanism);
            }
        }


    }

    @Override
    public ResultParam queryStatusById(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(param.getId());
        return ResultUtil.success(masterAppointmentEntity == null ? 2 : masterAppointmentEntity.getStatus());
    }

    @Override
    public List<MasterAppointmentEntity> queryMechanismOfflineScheduleListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryMechanismScheduleListPage(param);
            if (list.size() > 0) {
                this.setQueryMechanismOfflineScheduleInfo(list, param);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional
    public synchronized ResultParam insertCopyCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterAppointmentEntity masterAppointmentEntity = mapper.selectByPrimaryKey(param.getId());
            masterAppointmentEntity.setStudent_id(null);
            masterAppointmentEntity.setStudy_card_id(null);
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id());
//            List<String> titleList = Arrays.asList(masterSetPriceEntity.getTitile_url().split("#$*"));
            List<String> titleList = this.getTitleList(masterSetPriceEntity.getTitile_url());
            List<String> list = Arrays.asList(param.getDates().split(","));
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            MasterAppointmentEntity masterAppointmentEntity2 = new MasterAppointmentEntity();
            masterAppointmentEntity2.setMaster_set_price_id(masterAppointmentEntity.getMaster_set_price_id());
            Integer integer = mapper.queryListPageCount(masterAppointmentEntity2);

            if ("date".equalsIgnoreCase(param.getType())) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String dateStr = sdf.format(param.getStart_time());
                String dateEnd = sdf.format(param.getEnd_time());


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat hms = new SimpleDateFormat("yyyy-MM-dd");
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String sb = year + "-" + month + "-" + day;
                Date parse = hms.parse(sb);
                for (String s : list) {
                    if (hms.parse(s).compareTo(parse) < 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResultUtil.error(OrderEum.order_70020.getCode(), OrderEum.order_70020.getDesc());
                    } else {
                        masterAppointmentEntity.setStart_time(dateformat.parse(s + " " + dateStr));
                        masterAppointmentEntity.setEnd_time(dateformat.parse(s + " " + dateEnd));
                        masterAppointmentEntity.setId(null);
                        int res = mapper.insertSelective(masterAppointmentEntity);
                    }
                }

                MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                masterAppointmentEntity1.setMaster_set_price_id(masterAppointmentEntity.getMaster_set_price_id());
                List<MasterAppointmentEntity> list1 = mapper.queryAsc(masterAppointmentEntity1);
                for (int i = 0; i < list1.size(); i++) {
                    if (i == titleList.size() - 1) {
                        return ResultUtil.success();
                    }
                    masterAppointmentEntity1 = list1.get(i);
                    masterAppointmentEntity1.setNumber_of_lessons((long) (i + 1));
                    masterAppointmentEntity1.setTitle(titleList.get(i));

                    int i1 = mapper.updateByPrimaryKeySelective(masterAppointmentEntity1);
                }
            } else if ("time".equalsIgnoreCase(param.getType())) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String dateStr = sdf.format(param.getStart_time());
                String dateEnd = sdf.format(param.getEnd_time());

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(param.getStart_time());
                int count = 0;
                while (calendar.getTime().compareTo(param.getEnd_time()) <= 0 && integer + count < titleList.size()) {
                    String ymd = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                    if (list.contains(ymd)) {
                        ymd = ymd + " ";
                        masterAppointmentEntity.setId(null);
                        masterAppointmentEntity.setStart_time(dateformat.parse(ymd + dateStr));
                        masterAppointmentEntity.setEnd_time(dateformat.parse(ymd + dateEnd));
                        int i = mapper.insertSelective(masterAppointmentEntity);
                        count++;
                    }
                    calendar.add(Calendar.DATE, 1);
                }

                MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                masterAppointmentEntity1.setMaster_set_price_id(masterAppointmentEntity.getMaster_set_price_id());
                List<MasterAppointmentEntity> list1 = mapper.queryAsc(masterAppointmentEntity1);
                for (int i = 0; i < list1.size(); i++) {
                    if (i == titleList.size() - 1) {
                        return ResultUtil.success();
                    }
                    masterAppointmentEntity1 = list1.get(i);
                    masterAppointmentEntity1.setNumber_of_lessons((long) (i + 1));
                    masterAppointmentEntity1.setTitle(titleList.get(i));

                    int i1 = mapper.updateByPrimaryKeySelective(masterAppointmentEntity1);
                }
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private List<String> getTitleList(String title) {
        List<String> arr = Lists.newArrayList();
        StringTokenizer st = new StringTokenizer(title, "#$*");

        while (st.hasMoreTokens()) {
            arr.add(st.nextToken());
        }
        return arr;
    }

    private void setMechanismInfo(List<MasterAppointmentEntity> list) {
        for (MasterAppointmentEntity masterAppointmentEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            /*
            if (masterAppointmentEntity.getStudent_id() !=0){
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getStudent_id() + "userinfo")));
            }else {
                map.put("userinfo",null);
            }

             */
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
            map.put("studentCount", userAppointmentMapper.queryListPageCount(userAppointmentEntity));


            map.put("masterInfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getMaster_id() + "userinfo")));
//            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id()));
//            map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterAppointmentEntity.getMechanism_id()));
            if (StringUtil.isNotEmpty(masterAppointmentEntity.getClassroom())) {
                MechanismClassesEntity mechanismClassesEntity = new MechanismClassesEntity();
                mechanismClassesEntity.setName(masterAppointmentEntity.getClassroom());
                mechanismClassesEntity.setPageSize(1);
                List<MechanismClassesEntity> list1 = mechanismClassesMapper.queryListPage(mechanismClassesEntity);
                map.put("mechanismClassesEntity", list1);
            } else {
                map.put("mechanismClassesEntity", null);
            }
            masterAppointmentEntity.setMap(map);
        }
    }

    private void setMechanismAllInfo(List<MasterAppointmentEntity> list) {
        for (MasterAppointmentEntity masterAppointmentEntity : list) {

            HashMap<String, Object> map = Maps.newHashMap();
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());

            List<UserAppointmentEntity> query = userAppointmentMapper.query(userAppointmentEntity);
            if (query != null && query.size() > 0) {
                ArrayList<UserInfoEntity> objects = Lists.newArrayList();
                for (UserAppointmentEntity appointmentEntity : query) {
                    objects.add(userInfoMapper.selectByPrimaryKey(appointmentEntity.getUser_id()));
                }
                map.put("userInfoList", objects);
                map.put("studentCount", objects.size());
            } else {
                map.put("studentCount", 0);
                map.put("userInfoList", null);
            }

            map.put("masterInfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getMaster_id() + "userinfo")));
            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id()));
            map.put("masterMechanismEntity", masterMechanismMapper.selectByPrimaryKey(masterAppointmentEntity.getMechanism_id()));
            if (StringUtil.isNotEmpty(masterAppointmentEntity.getClassroom())) {
                MechanismClassesEntity mechanismClassesEntity = new MechanismClassesEntity();
                mechanismClassesEntity.setName(masterAppointmentEntity.getClassroom());
                mechanismClassesEntity.setPageSize(1);
                List<MechanismClassesEntity> list1 = mechanismClassesMapper.queryListPage(mechanismClassesEntity);
                map.put("mechanismClassesEntity", list1);
            } else {
                map.put("mechanismClassesEntity", null);
            }
            masterAppointmentEntity.setMap(map);
        }
    }

    private void setinfo(List<MasterAppointmentEntity> list) {
        for (MasterAppointmentEntity masterAppointmentEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getStudent_id() + "userinfo")));
            map.put("masterInfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getMaster_id() + "userinfo")));
            map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_set_price_id()));
            masterAppointmentEntity.setMap(map);
        }

    }

    private void setListInfo(List<MasterAppointmentEntity> list, HttpServletRequest request, HttpServletResponse response) {
        for (MasterAppointmentEntity entity : list) {

            Map<String, Object> map = new HashMap<>();
            //???????????????
            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setUser_id(entity.getMaster_id());
            List<MasterInfoEntity> list1 = masterInfoMapper.queryListPage(masterInfoEntity);
            if (list1 != null && list1.size() > 0) {
                masterInfoEntity = list1.get(0);
            }
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
            map.put("masterInfo", masterInfoEntity);
            map.put("appointmentCount", mapper.queryListPageCount(entity));
            if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                map.put("groupinfo", JSONObject.parseObject(group_info));
                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setAppointment_id(entity.getId());
                masterCommentEntity.setParent_id(0L);
                List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                map.put("masterCommentEntities", masterCommentEntities);//???????????????
            } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setAppointment_id(entity.getId());
                List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                    for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                        Map<String, Object> objectMap = new HashMap<>();
                        objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                        appointmentEntity.setMap(objectMap);
                    }
                }
                if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                    //????????????
                    map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                    entity.setIs_appointment(true);
                }
                map.put("appointmentinfo", userAppointmentEntities);
            }
            entity.setMap(map);
        }
    }

    @Override
    public List<MasterAppointmentEntity> querySchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.querySchedule(param);
            for (MasterAppointmentEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                    if (entity.getGroup_id() != null && entity.getGroup_id() != 0) {
                        String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                        map.put("groupinfo", JSONObject.parseObject(group_info));
                    }
                }
                entity.setIs_appointment(false);//???????????????
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setAppointment_id(entity.getId());
                List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);//status!=1&&!=7
                if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                    for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                        Map<String, Object> objectMap = new HashMap<>();
                        objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                        appointmentEntity.setMap(objectMap);
                    }
                }
                /*
                if (entity.getStudent_id()!=null&&entity.getStudent_id()!=0){
                    map.put("studentInfo", JSONUtils.obj2Json(redisService.getStr(entity.getStudent_id() + "userinfo")));
                }else {
                    map.put("studentInfo", null);
                }

                 */
                if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                    entity.setIs_appointment(true);//?????????
                }
                map.put("userAppointmentinfo", userAppointmentEntities);

                MasterMechanismEntity mechanismEntity = null;
                if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                    mechanismEntity = masterMechanismMapper.selectByPrimaryKey(entity.getMechanism_id());
                }
                map.put("mechanismEntity", mechanismEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> list = masterInfoMapper.queryListPage(masterInfoEntity);
                map.put("masterInfoEntity", list);

                TimeZoneEntity timeZoneEntity = new TimeZoneEntity();
                timeZoneEntity.setId(entity.getTimezone_id());
                timeZoneEntity.setPageSize(1);
                List<TimeZoneEntity> timeZoneEntities = timeZoneMapper.queryListPage(timeZoneEntity);
                if (timeZoneEntities != null && timeZoneEntities.size() > 0) {
                    map.put("timezone", timeZoneEntities.get(0));//????????????
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     querySchedule");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryListPage(param);
            if (entityList == null || entityList.size() == 0) {
                param.setRecommend_type(0);
                entityList = mapper.queryListPage(param);
            }
            for (MasterAppointmentEntity entity : entityList) {
                //????????????
                entity.setTitle(EmojiParser.parseToUnicode(entity.getTitle()));
                entity.setIntroduction_content(EmojiParser.parseToUnicode(entity.getIntroduction_content()));

                Map<String, Object> map = new HashMap<>();
                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                entity.setIs_appointment(false);
                if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                    MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                    masterCommentEntity.setAppointment_id(entity.getId());
                    masterCommentEntity.setParent_id(0L);
                    List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                    map.put("masterCommentEntities", masterCommentEntities);
                } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                    }
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        entity.setIs_appointment(true);
                    }
                    map.put("appointmentinfo", userAppointmentEntities);
                } else {
                    MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                    if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                        masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                    }
                    map.put("masterMechanismEntity", masterMechanismEntity);
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setStatus(2);
                    masterInfoEntity.setPageSize(1);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    map.put("masterinfo", masterInfoEntities);
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryListPage");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryHomeListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = Lists.newArrayList();
        try {
            if (param.getIs_teenagers() != null && param.getIs_teenagers()) {
                String group_idStr = messageApiService.queryGroupIds(param.getUser_id());
                List<Long> group_ids = JSONUtils.json2List(group_idStr, Long.class);
                param.setGroup_ids(group_ids);
            }
            //???????????????
            List<MasterAppointmentEntity> tempAllList = mapper.queryHomeListPage(param);
            List<MasterAppointmentEntity> tempNearList = Lists.newArrayList();
            List<MasterAppointmentEntity> tempEndList = Lists.newArrayList();
            for (MasterAppointmentEntity masterAppointmentEntity : tempAllList) {
                switch (masterAppointmentEntity.getStatus()) {
                    case 3:
                        entityList.add(masterAppointmentEntity);
                        break;
                    case 2:
                        tempEndList.add(masterAppointmentEntity);
                        break;
                    case 1:
                        tempNearList.add(masterAppointmentEntity);
                }
            }
            Collections.shuffle(tempNearList);
            Collections.shuffle(tempEndList);
            Collections.shuffle(entityList);
            entityList.addAll(tempEndList);
            entityList.addAll(tempNearList);
            Integer beginCount = mapper.queryHomeListPageBeginCount(param);
            Integer endCount = mapper.queryHomeListPageEndCount(param);
            Integer nearCount = mapper.queryHomeListPageNearCount(param);
            for (MasterAppointmentEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                map.put("beginCount", beginCount);
                map.put("endCount", endCount);
                map.put("nearCount", nearCount);

                if (entity.getIs_recording()) {
                    map.put("recordingUrl", messageApiService.queryRecordingUrl(entity.getId()));
                }

                //????????????????????????
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                masterAppointmentEntity.setMaster_id(entity.getMaster_id());
                List<MasterAppointmentEntity> list = mapper.queryBeginList(masterAppointmentEntity);
                if (list != null && list.size() > 0) {
                    map.put("teacherIsLiving", list.get(0).getId());
                } else {
                    map.put("teacherIsLiving", 0);
                }

                //??????????????????
                if (param.getUser_id() != null) {
                    Boolean isLike = messageApiService.queryIsLike(param.getUser_id(), entity.getId());
                    map.put("isLike", isLike);

                    MasterFollowEntity masterFollowEntity = new MasterFollowEntity();
                    masterFollowEntity.setUser_id(param.getUser_id());
                    masterFollowEntity.setMaster_id(entity.getMaster_id());
                    masterFollowEntity.setPageSize(1);
                    List<MasterFollowEntity> masterFollowEntities = masterFollowMapper.queryListPage(masterFollowEntity);
                    map.put("isFollow", masterFollowEntities != null && masterFollowEntities.size() > 0);
                }

                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                entity.setIs_appointment(false);
                if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                    String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                    map.put("groupinfo", JSONObject.parseObject(group_info));
                } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                    }
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        entity.setIs_appointment(true);
                    }
                    map.put("appointmentinfo", userAppointmentEntities);
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryListPage");
        }
        return entityList;
    }


    /**
     * ??????????????????????????????
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            if (param.getOffset() != null) {
                if (param.getMaster_id() != null) {
                    entityList = mapper.queryAppointmentUpdate(param);
                    for (MasterAppointmentEntity entity : entityList) {
                        UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                        userAppointmentEntity1.setMaster_id(entity.getMaster_id());
                        userAppointmentEntity1.setStatus(2);
                        Map<String, Object> map = new HashMap<>();
                        Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity1);
                        map.put("studentCount", studentCount);//????????????
                        userAppointmentEntity1.setStatus(3);
                        Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity1);
                        map.put("classCount", classCount);//????????????
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(entity.getMaster_id());
                        masterInfoEntity.setStatus(2);
                        List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                        map.put("masterinfo", masterInfoEntities);
                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                        entity.setIs_appointment(false);
                        //????????????
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setAppointment_id(entity.getId());
                        List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                            for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                                Map<String, Object> objectMap = new HashMap<>();
                                objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                                appointmentEntity.setMap(objectMap);
                            }
                        }
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                            //????????????
                            map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                            entity.setIs_appointment(true);
                        }
                        map.put("appointmentinfo", userAppointmentEntities);
                        entity.setMap(map);
                    }
                } else {
                    entityList = mapper.queryAppointmentListPage(param);
                    for (MasterAppointmentEntity entity : entityList) {
                        if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                            UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                            userAppointmentEntity1.setMaster_id(entity.getMaster_id());
                            userAppointmentEntity1.setStatus(2);
                            Map<String, Object> map = new HashMap<>();
                            Integer studentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity1);
                            map.put("studentCount", studentCount);//????????????
                            userAppointmentEntity1.setStatus(3);
                            Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity1);
                            map.put("classCount", classCount);//????????????
                            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                            masterInfoEntity.setUser_id(entity.getMaster_id());
                            masterInfoEntity.setStatus(2);
                            List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                            map.put("masterinfo", masterInfoEntities);
                            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                            entity.setIs_appointment(false);
                            //????????????
                            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                            userAppointmentEntity.setAppointment_id(entity.getId());
                            List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                            if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                                for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                                    Map<String, Object> objectMap = new HashMap<>();
                                    objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                                    appointmentEntity.setMap(objectMap);
                                }
                            }
                            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                                //????????????
                                map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                                entity.setIs_appointment(true);
                            }
                            map.put("appointmentinfo", userAppointmentEntities);
                            entity.setMap(map);
                        } else if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                            Map<String, Object> map = new HashMap<>();
                            String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                            map.put("groupinfo", JSONObject.parseObject(group_info));
                            entity.setMap(map);
                        }
                    }
                }
            } else {
                return ResultUtil.error(MasterEum.master_40005.getCode(), MasterEum.master_40005.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryAppointmentListPage");
        }
        return ResultUtil.success(entityList);
    }

    @Override
    public List<MasterAppointmentEntity> queryClassListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryClassListPage(param);
            for (MasterAppointmentEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryNewAppointment(MasterAppointmentEntity param) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryListPage(param);
            for (MasterAppointmentEntity entity : entityList) {
                //????????????
                entity.setTitle(EmojiParser.parseToUnicode(entity.getTitle()));
                entity.setIntroduction_content(EmojiParser.parseToUnicode(entity.getIntroduction_content()));

                Map<String, Object> map = new HashMap<>();
                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryIsClassListPage(MasterAppointmentEntity param) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryClassListPage(param);
            if (entityList != null && entityList.size() > 0) {
                param = entityList.get(0);
                if (param.getStatus() == 1) {
                    param.setStatus(3);
//                } else if (param.getStatus() == 3) {
//                    param.setStatus(2);
                } else if (param.getStatus() == 2) {
                    param.setStatus(3);
                }
            }
            mapper.updateByPrimaryKey(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryIsClassListPageClose(MasterAppointmentEntity param) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryClassListPage(param);
            if (entityList != null && entityList.size() > 0) {
                param = entityList.get(0);
                if (param.getStatus() == 3) {
                    param.setStatus(2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    //??????????????????????????????
    @Override
    public void getQueryExpireClass() {
        try {
            List<MasterAppointmentEntity> masterAppointmentEntities = mapper.queryExpireClass();
            for (MasterAppointmentEntity entity : masterAppointmentEntities) {
                if ("open_class".equalsIgnoreCase(entity.getType())) {
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setId(entity.getId());
                    masterAppointmentEntity.setStatus(2);
                    mapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(entity.getMaster_id());
                    pushMessageParam.setType_id(entity.getGroup_id());
                    pushMessageParam.setPush_type("live_class_end");
                    pushMessageParam.setOpera_type("live_class_end");
                    pushMessageParam.setContent("??????????????????????????????");
                    pushMessageParam.setType("groupClassNotice");
                    pushMessageParam.setIs_teach_paypal(entity.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    messageApiService.endCourseNotice(entity.getId());
                } else if ("single_class".equalsIgnoreCase(entity.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    userAppointmentEntity.setPageSize(1);
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryListPage(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setTarget_id(userAppointmentEntities.get(0).getUser_id());
                        pushMessageParam.setType_id(entity.getId());
                        pushMessageParam.setPush_type("single_class_close");
                        pushMessageParam.setOpera_type("single_class_close");
                        pushMessageParam.setContent("??????????????????????????????????????????");
                        pushMessageParam.setType("class");
                        pushMessageParam.setIs_teach_paypal(entity.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    }
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(entity.getMaster_id());
                    pushMessageParam.setType_id(entity.getId());
                    pushMessageParam.setPush_type("single_class_close");
                    pushMessageParam.setOpera_type("single_class_close");
                    pushMessageParam.setContent("?????????????????????????????????????????????");
                    pushMessageParam.setType("class");
                    pushMessageParam.setIs_teach_paypal(entity.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                }

            }
            List<MasterAppointmentEntity> appointmentEntities = mapper.queryStayComment();
            for (MasterAppointmentEntity masterAppointmentEntity : appointmentEntities) {
                ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                classRecordEntity.setCurriculum_id(masterAppointmentEntity.getId());
                List<ClassRecordEntity> classRecordEntities = classRecordMapper.queryStayListPage(classRecordEntity);
                if (classRecordEntities != null && classRecordEntities.size() > 0) {
                    for (ClassRecordEntity recordEntity : classRecordEntities) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setTarget_id(recordEntity.getUser_id());
                        pushMessageParam.setType_id(recordEntity.getGroup_id());
                        pushMessageParam.setPush_type("single_course_evaluation_comment");
                        pushMessageParam.setOpera_type("single_course_evaluation_comment");
                        pushMessageParam.setContent("????????????????????????????????????????????????");
                        pushMessageParam.setType("single_course_evaluation_comment");
                        pushMessageParam.setIs_teach_paypal(recordEntity.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //??????????????????????????????
    @Override
    public void queryClassStartPush() {
        try {
            List<MasterAppointmentEntity> masterAppointmentEntities = mapper.queryClassStartPush();

            for (MasterAppointmentEntity entity : masterAppointmentEntities) {
                //List<MasterFollowEntity> masterFollowEntities
                if (entity.getGroup_id() != null) {
                    String user_ids = messageApiService.queryGroupUserInfos(entity.getGroup_id());//??????????????????
                    if (StringUtil.isNotEmpty(user_ids)) {
                        List<Long> longList = JSONUtils.json2List(user_ids, Long.class);
                        for (Long aLong : longList) {
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setTarget_id(aLong);
                            pushMessageParam.setType_id(entity.getGroup_id());
                            pushMessageParam.setPush_type("live_class_statr_notice");
                            pushMessageParam.setOpera_type("live_class_statr_notice");
                            pushMessageParam.setContent("????????????????????????" + new SimpleDateFormat(
                                    FinalConfigParam.TIME_FORMAT_SYSTLE).format(entity.getStart_time()) + "??????");
                            pushMessageParam.setType("groupClassNotice");
                            pushMessageParam.setIs_teach_paypal(false);
                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultParam deleteAppointment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        try {
            MasterAppointmentEntity masterAppointmentEntity = super.findById(param.getId(), request, response);
            if (masterAppointmentEntity != null) {
                Long aLong = System.currentTimeMillis() / 1000 - 8 * 60 * 60;
                Long appointmentStart = masterAppointmentEntity.getStart_time().getTime() / 1000 - Double.doubleToLongBits(masterAppointmentEntity.getOffset().doubleValue() * 60 * 60);
                long aLongStart = appointmentStart - aLong;
                Long appointmentend = masterAppointmentEntity.getEnd_time().getTime() / 1000 - Double.doubleToLongBits(masterAppointmentEntity.getOffset().doubleValue() * 60 * 60);
                long aLongEnd = appointmentend - aLong;
                if ((aLongStart < 43200 && aLongStart > 0) || (aLongStart < 0 && aLongEnd > 0)) {//?????????10???????????????????????????????????????
                    return ResultUtil.error(MasterEum.master_40011.getCode(), MasterEum.master_40011.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("MasterAppointmentServiceImpl  deleteAppointment");
        }
        return super.delete(param.getId(), request, response);
    }

    @Override
    public List<MasterAppointmentEntity> queryBeginListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryBeginListPage(param);
            if (entityList != null && entityList.size() > 0) {
                for (MasterAppointmentEntity entity : entityList) {
                    Map<String, Object> map = new HashMap<>();
                    //???????????????
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    List<MasterInfoEntity> list = masterInfoMapper.queryListPage(masterInfoEntity);
                    if (list != null && list.size() > 0) {
                        masterInfoEntity = list.get(0);
                    }
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    map.put("masterInfo", masterInfoEntity);
                    map.put("appointmentCount", mapper.queryListPageCount(param));
                    if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                        String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                        map.put("groupinfo", JSONObject.parseObject(group_info));
                        MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                        masterCommentEntity.setAppointment_id(entity.getId());
                        masterCommentEntity.setParent_id(0L);
                        List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                        map.put("masterCommentEntities", masterCommentEntities);//???????????????
                    } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setAppointment_id(entity.getId());
                        List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                            for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                                Map<String, Object> objectMap = new HashMap<>();
                                objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                                appointmentEntity.setMap(objectMap);
                            }
                        }
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                            //????????????
                            map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                            entity.setIs_appointment(true);
                        }
                        map.put("appointmentinfo", userAppointmentEntities);
                    }
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    @Override
    public List<MasterAppointmentEntity> queryHistoryListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryHistoryListPage(param);//??????????????????
            for (MasterAppointmentEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setType("live_lecturer");
                masterInfoEntity.setUser_id(entity.getMaster_id());
                List<MasterInfoEntity> list = masterInfoMapper.queryListPage(masterInfoEntity);
                if (list != null && list.size() > 0) {
                    masterInfoEntity = list.get(0);
                }
                //???????????????
                map.put("masterInfo", masterInfoEntity);
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));//??????userinfo
                map.put("appointmentCount", mapper.queryListPageCount(param));//???????????????
                if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {//open_class??????????????????
                    String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());//?????????????????????
                    map.put("groupinfo", JSONObject.parseObject(group_info));//objjson//??????????????????
                    MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                    masterCommentEntity.setAppointment_id(entity.getId());//??????????????????id
                    masterCommentEntity.setParent_id(0L);
                    int commentCoumt = masterCommentService.queryListPageCount(masterCommentEntity, request, response);//????????????????????????
                    map.put("commentCoumt", commentCoumt);//??????
                    //????????????
                    List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                    map.put("masterCommentEntities", masterCommentEntities);//??????map
                    ClassRecordEntity classRecordEntity = new ClassRecordEntity();//?????????????????????????????????
                    classRecordEntity.setCurriculum_id(entity.getId());//????????????id
                    Integer peopleNum = classRecordMapper.queryListPageCount(classRecordEntity);//?????????????????????????????????
                    map.put("peopleNum", peopleNum);//????????????
                    map.put("profit", 0);
                } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                    }
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        entity.setIs_appointment(true);
                    }
                    map.put("appointmentinfo", userAppointmentEntities);
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    public static void main(String[] args) {
        /*
        Map<String, Object> stringObjectMap = new HashMap<>();
        Integer bigDecimal = -7;
        stringObjectMap.put("offset", bigDecimal);

        BigDecimal offset = BigDecimal.valueOf(Long.parseLong(stringObjectMap.get("offset").toString()));
        System.out.println(offset);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleFormat.format(System.currentTimeMillis()));
         */

    }

    //????????????
    @Override
    public List<MasterAppointmentEntity> queryDetailsListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            entityList = mapper.queryListPage(param);
            for (MasterAppointmentEntity entity : entityList) {
                //????????????
                entity.setTitle(EmojiParser.parseToUnicode(entity.getTitle()));
                entity.setIntroduction_content(EmojiParser.parseToUnicode(entity.getIntroduction_content()));

                Map<String, Object> map = new HashMap<>();
                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                map.put("appointmentCount", mapper.queryListPageCount(param));
                if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {
                    String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                    map.put("groupinfo", JSONObject.parseObject(group_info));
                    MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                    masterCommentEntity.setAppointment_id(entity.getId());
                    masterCommentEntity.setParent_id(0L);
                    int commentCoumt = masterCommentService.queryListPageCount(masterCommentEntity, request, response);
                    map.put("commentCoumt", commentCoumt);//????????????
                   /* List<MasterCommentEntity> masterCommentEntities=masterCommentService.queryListPage(masterCommentEntity,request,response);
                    map.put("masterCommentEntities",masterCommentEntities);////????????????*/
                    ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                    classRecordEntity.setCurriculum_id(entity.getId());
                    Integer peopleNum = classRecordMapper.queryPayCount(classRecordEntity);
                    map.put("peopleNum", peopleNum);//????????????
                    //List<ClassRecordEntity> classRecordEntities=classRecordService.queryHistoryListPage(classRecordEntity,request,response);
                    //map.put("classRecordEntities",classRecordEntities);//????????????
                    map.put("profit", 0);//??????
                    String recording_info = messageApiService.queryRecordingByAppointmentId(entity.getId());
                    JSONObject jsonObject = new JSONObject();
                    if (StringUtil.isNotEmpty(recording_info)) {
                        jsonObject = JSONObject.parseObject(recording_info);
                    }
                    map.put("recording_info", jsonObject);//??????????????????
                } else if (entity.getType() != null && "single_class".equalsIgnoreCase(entity.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                    }
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        entity.setIs_appointment(true);
                    }
                    map.put("appointmentinfo", userAppointmentEntities);
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
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
    public ResultParam masterAppointmentAudit(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(param);
            if (ret > 0) {
                MasterAppointmentEntity entity = super.findById(param.getId(), request, response);
                if (entity != null) {
                    MasterMechanismEntity masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                    map = JSONUtils.generatorMap("????????????", true);
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(masterMechanismEntity.getUser_id());
                    pushMessageParam.setType("authentication");
                    pushMessageParam.setOpera_type("masterAppointmentAuthentication");
                    pushMessageParam.setTitle("????????????????????????");
                    pushMessageParam.setType_id(param.getId());
                    if (param.getStatus() == 1) {
                        pushMessageParam.setContent("???????????????????????????");
                    } else {
                        pushMessageParam.setContent("???????????????????????????");
                    }
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

    @Override
    public List<MasterAppointmentEntity> queryMechanismAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = null;
        try {
            //????????????????????????
            entityList = mapper.queryMechanismAppointmentListPage(param);
            for (MasterAppointmentEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                //???????????????
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                //???????????????
                entity.setIs_appointment(false);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setPageSize(1);
                MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                    //????????????id??????
                    masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                }
                //?????????????????????
                map.put("masterMechanismEntity", masterMechanismEntity);
                List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                //????????????id??????user??????
                map.put("masterinfo", masterInfoEntities);
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryListPage");
        }
        return entityList;
    }

    @Override
    public Map<String, Object> queryMechanismSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            //???????????????
            param.setType("single_class");
            List<MasterAppointmentEntity> single_class_info = mapper.querySchedule(param);
            if (single_class_info != null && single_class_info.size() > 0) {
                for (MasterAppointmentEntity entity : single_class_info) {
                    Map<String, Object> map = new HashMap<>();
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    userAppointmentEntity.setPageSize(1);
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryListPage(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        map.put("appointmentinfo", userAppointmentEntities);
                        entity.setIs_appointment(true);
                    } else {
                        entity.setIs_appointment(false);
                    }
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setStatus(2);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                    if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                        masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                        map.put("masterMechanismEntity", masterMechanismEntity);
                    } else {
                        map.put("masterMechanismEntity", null);
                    }
                    if (entity.getMaster_id() == null || entity.getMaster_id() == 0) {
                        map.put("masterInfoEntities", null);
                        map.put("masterinfo", null);
                    } else {
                        map.put("masterInfoEntities", masterInfoEntities);
                        map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    }
                    entity.setMap(map);
                }
            }
            objectMap.put("single_class_info", single_class_info);
            //????????????
            param.setType("open_class");
            List<MasterAppointmentEntity> open_class_info = mapper.querySchedule(param);
            if (open_class_info != null && open_class_info.size() > 0) {
                for (MasterAppointmentEntity entity : open_class_info) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setStatus(2);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    map.put("masterInfoEntities", masterInfoEntities);
                    MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                    if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                        masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                    }
                    map.put("masterMechanismEntity", masterMechanismEntity);
                    entity.setMap(map);
                }
                objectMap.put("open_class_info", open_class_info);
            }
            //?????????????????????
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setMaster_type("private_education");
            userAppointmentEntity.setMechanism_id(param.getMechanism_id());
            userAppointmentEntity.setStart_time(param.getStart_time());
            userAppointmentEntity.setEnd_time(param.getEnd_time());
            userAppointmentEntity.setOffset(param.getOffset());
            userAppointmentEntity.setMaster_id(param.getMaster_id());
            List<UserAppointmentEntity> private_education_info = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
            if (private_education_info != null && private_education_info.size() > 0) {
                for (UserAppointmentEntity entity : private_education_info) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                        masterAppointmentEntity = super.findById(entity.getAppointment_id(), request, response);
                    }
                    map.put("masterAppointmentEntity", masterAppointmentEntity);
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setType(userAppointmentEntity.getMaster_type());
                    masterInfoEntity.setPageSize(1);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    map.put("masterInfoEntities", masterInfoEntities);
                    MasterMechanismEntity masterMechanismEntity = new MasterMechanismEntity();
                    if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                        masterMechanismEntity = masterMechanismService.findById(entity.getMechanism_id(), request, response);
                    }
                    map.put("masterMechanismEntity", masterMechanismEntity);
                    entity.setMap(map);
                }
            }
            objectMap.put("private_education_info", private_education_info);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    @Override
    public Map<String, Object> mechanismCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;

        try {
            map = new HashMap<>();
            Integer countTotal = mapper.queryListPageCount(param);
            //??????
            map.put("countTotal", countTotal);

            param.setLanguage("English");
            Integer englishCount = mapper.queryListPageCount(param);
            //????????????
            map.put("englishCount", englishCount);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MasterAppointmentEntity> queryProfitListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterAppointmentEntity> entityList = Lists.newArrayList();
        try {
            if (param.getId() != null && param.getId() != 0) {
                entityList.add(mapper.selectByPrimaryKey(param.getId()));
            } else {
                if (param.getStatus() == null) {
                    param.setStatus(2);
                }
                entityList = mapper.querySchedule(param);
            }
            if (entityList != null && entityList.size() > 0) {
                for (MasterAppointmentEntity entity : entityList) {
                    Map<String, Object> map = new HashMap<>();
                    //???????????????
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));//????????????
                    if (entity.getType() != null && "open_class".equalsIgnoreCase(entity.getType())) {//?????????
                        SpecialLecturerEntity specialLecturerEntity = new SpecialLecturerEntity();
                        if (entity.getIs_special()) {
                            specialLecturerEntity.setPageSize(1);
                            specialLecturerEntity.setUser_id(param.getMaster_id());
                            specialLecturerEntity = specialLecturerMapper.queryListPage(specialLecturerEntity).get(0);
                        } else {
                            specialLecturerEntity = specialLecturerMapper.selectByPrimaryKey(0L);
                        }
                        ClassRecordEntity classRecordEntity = new ClassRecordEntity();//?????????????????????
                        classRecordEntity.setCurriculum_id(entity.getId());
                        classRecordEntity.setStatus(1);
                        //????????????
                        Integer peopleNum = classRecordMapper.queryListPageCount(classRecordEntity);
                        classRecordEntity.setStatus(3);
                        //????????????
                        Integer freeNum = classRecordMapper.queryListPageCount(classRecordEntity);
                        //????????????
                        Integer userNum = peopleNum + freeNum;
                        map.put("peopleNum", peopleNum);//????????????
                        map.put("freeNum", freeNum);//????????????
                        map.put("userNum", userNum);//????????????

                        //??????????????????
                        BigDecimal finalEarn;
                        BigDecimal earn = new BigDecimal((userNum / specialLecturerEntity.getEach_student() * specialLecturerEntity.getEach_money().intValue()) + "");
                        map.put("beforeEarn", earn);

                        UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getMaster_id());

                        if (userInfoEntity.getCountry() == null) {
                            userInfoEntity.setCountry("");
                        }
                        if (entity.getIs_special()) {
                            BigDecimal least_earn = this.getEarn(userInfoEntity.getCountry(), specialLecturerEntity);
                            if (least_earn.compareTo(earn) > 0) {//??????
                                finalEarn = least_earn;
                            } else {
                                finalEarn = earn;
                            }
                        } else {
                            finalEarn = earn;
                        }
                        finalEarn = finalEarn.multiply(specialLecturerEntity.getIncome_coefficient());
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(entity.getMaster_id());
                        masterInfoEntity.setType("live_lecturer");
                        masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);

                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                        if (StringUtil.isNotEmpty(masterInfoEntity.getInvitees_id()) && !"0".equals(masterInfoEntity.getInvitees_id())) {
                            UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterInfoEntity.getInvitees_id());
                            map.put("userRecommenderEntity", userRecommenderEntity);
                            UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                            BigDecimal masterRecommenderCash = finalEarn.multiply(userEarnRoleEntity.getMaster_bonus());
                            map.put("masterRecommenderCash", masterRecommenderCash);
                            //?????????
                            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                            userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                            userRecommenderIncomeLogEntity.setRecommender_type("master_recommender");
                            userRecommenderIncomeLogEntity.setIs_settlement(false);
                            userRecommenderIncomeLogEntity.setCash(masterRecommenderCash);
                            userRecommenderIncomeLogEntity.setType("master_live");
                            userRecommenderIncomeLogEntity.setCash_describe("??????????????????");
                            userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getId());
                            userRecommenderIncomeLogEntity.setAppointment_id(entity.getId());
                            userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                            userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                            map.put("userRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);
                            finalEarn = finalEarn.subtract(masterRecommenderCash);
                            userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                            userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getUser_id());
                        }

                        if (entity.getMechanism_id() != 0) {
                            MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(entity.getMechanism_id());
                            if (StringUtil.isNotEmpty(mechanismEntity.getRecommender_id())) {
                                UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(mechanismEntity.getRecommender_id());
                                map.put("userRecommenderEntity", userRecommenderEntity);
                                UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                                BigDecimal masterRecommenderCash = finalEarn.multiply(userEarnRoleEntity.getMaster_bonus());
                                map.put("masterRecommenderCash", masterRecommenderCash);
                                //?????????
                                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                                userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                                userRecommenderIncomeLogEntity.setIs_settlement(false);
                                userRecommenderIncomeLogEntity.setCash(masterRecommenderCash);
                                userRecommenderIncomeLogEntity.setType("mechanism_live");
                                userRecommenderIncomeLogEntity.setMechanism_id(entity.getMechanism_id());
                                userRecommenderIncomeLogEntity.setCash_describe("??????????????????");
                                userRecommenderIncomeLogEntity.setAppointment_id(entity.getId());
                                userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getUser_id());
                                userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                                userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                                map.put("userRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);
                                finalEarn = finalEarn.subtract(masterRecommenderCash);
                                userRecommenderIncomeLogEntity1.setMechanism_id(entity.getMechanism_id());
                                userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getUser_id());
                                userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                            }
                        }
                        //??????????????????
                        map.put("finalEarn", finalEarn);
                        entity.setProfit(finalEarn);
                        mapper.updateProfit(entity);

                        if (userRecommenderIncomeLogEntity1.getRecommender_id() == null) {
                            userRecommenderIncomeLogEntity1.setRecommender_id(0L);
                        }
                        userRecommenderIncomeLogEntity1.setIs_settlement(false);
                        userRecommenderIncomeLogEntity1.setCash(finalEarn);
                        userRecommenderIncomeLogEntity1.setType("live");
                        userRecommenderIncomeLogEntity1.setCash_describe("??????????????????");
                        userRecommenderIncomeLogEntity1.setAppointment_id(entity.getId());
                        userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getId());
                        userRecommenderIncomeLogEntity1.setUser_id(entity.getMaster_id());
                        userRecommenderIncomeLogEntity1.setRole_id(userInfoEntity.getRole_id());
                        userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity1, request, response);
                        map.put("masterRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity1);

                        String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                        map.put("groupinfo", JSONObject.parseObject(group_info));

                        MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                        masterCommentEntity.setAppointment_id(entity.getId());
                        masterCommentEntity.setParent_id(0L);
                        Integer commentCoumt = masterCommentService.queryListPageCount(masterCommentEntity, request, response);
                        map.put("commentCoumt", commentCoumt);//????????????
                        List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                        map.put("masterCommentEntities", masterCommentEntities);//??????????????????
                        entity.setProfit(finalEarn);
                        mapper.updateByPrimaryKeySelective(entity);

                        Map<String, Object> saleCourseMap = userRecommenderIncomeLogService.queryCountByTime(entity, request, response);

                        UserOrderEntity userOrderEntity = new UserOrderEntity();
                        userOrderEntity.setStart_time(entity.getStart_time());
                        userOrderEntity.setEnd_time(entity.getEnd_time());
                        userOrderEntity.setPayment_id(entity.getMaster_id());
                        map.put("giftTotal", userOrderMapper.queryListPageSum(userOrderEntity));

                        BigDecimal cardCash = rechargeRecordMapper.querySaleCardCash(entity.getId());
                        BigDecimal courseCash = rechargeRecordMapper.querySaleCourseCash(entity.getId());

                        map.put("cardCash", cardCash);
                        map.put("courseCash", courseCash);


                        finalEarn = finalEarn.add(new BigDecimal(saleCourseMap.get("courseCash").toString()));
                        finalEarn = finalEarn.add(new BigDecimal(saleCourseMap.get("cardCash").toString()));
                        map.put("earnTotal", finalEarn);

                        entity.setMap(map);
                    } else if ("single_class".equals(entity.getType())) {
                        if (!entity.getIs_special()) {
                            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                            userAppointmentEntity.setAppointment_id(entity.getId());
                            List<UserAppointmentEntity> list = userAppointmentMapper.queryListPage(userAppointmentEntity);
                            if (list != null && list.size() > 0) {
                                userAppointmentEntity = list.get(0);
                                BigDecimal cash = this.queryMasterType(userAppointmentEntity);
                                if (cash.compareTo(new BigDecimal("0")) == 0) {
                                    continue;
                                }
                                BigDecimal finalEarn = cash.multiply(new BigDecimal(list.size()));
                                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                                masterInfoEntity.setUser_id(entity.getMaster_id());
                                masterInfoEntity.setType("live_lecturer");
                                masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);

                                UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1 = new UserRecommenderIncomeLogEntity();
                                if (StringUtil.isNotEmpty(masterInfoEntity.getInvitees_id()) && !"0".equals(masterInfoEntity.getInvitees_id())) {
                                    UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(masterInfoEntity.getInvitees_id());
                                    map.put("userRecommenderEntity", userRecommenderEntity);
                                    UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                                    BigDecimal masterRecommenderCash = finalEarn.multiply(userEarnRoleEntity.getMaster_bonus());
                                    map.put("masterRecommenderCash", masterRecommenderCash);
                                    //?????????
                                    UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                                    userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                                    userRecommenderIncomeLogEntity.setRecommender_type("master_recommender");
                                    userRecommenderIncomeLogEntity.setIs_settlement(false);
                                    userRecommenderIncomeLogEntity.setCash(masterRecommenderCash);
                                    userRecommenderIncomeLogEntity.setType("master_class");
                                    userRecommenderIncomeLogEntity.setCash_describe("????????????????????????");
                                    userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getUser_id());
                                    userRecommenderIncomeLogEntity.setAppointment_id(entity.getId());
                                    userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                                    userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                                    map.put("userRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);
                                    finalEarn = finalEarn.subtract(masterRecommenderCash);
                                    userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                                    userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getUser_id());
                                }
                                if (entity.getMechanism_id() != 0) {
                                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(entity.getMechanism_id());
                                    if (StringUtil.isNotEmpty(mechanismEntity.getRecommender_id())) {
                                        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.queryInvateCode(mechanismEntity.getRecommender_id());
                                        map.put("userRecommenderEntity", userRecommenderEntity);
                                        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(userRecommenderEntity.getRule_id());
                                        BigDecimal masterRecommenderCash = finalEarn.multiply(userEarnRoleEntity.getMaster_bonus());
                                        map.put("masterRecommenderCash", masterRecommenderCash);
                                        //?????????
                                        UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = new UserRecommenderIncomeLogEntity();
                                        userRecommenderIncomeLogEntity.setRecommender_id(userRecommenderEntity.getId());
                                        userRecommenderIncomeLogEntity.setIs_settlement(false);
                                        userRecommenderIncomeLogEntity.setCash(masterRecommenderCash);
                                        userRecommenderIncomeLogEntity.setType("mechanism_master_class");
                                        userRecommenderIncomeLogEntity.setMechanism_id(entity.getMechanism_id());
                                        userRecommenderIncomeLogEntity.setCash_describe("??????????????????????????????");
                                        userRecommenderIncomeLogEntity.setAppointment_id(entity.getId());
                                        userRecommenderIncomeLogEntity.setMaster_id(masterInfoEntity.getUser_id());
                                        userRecommenderIncomeLogEntity.setRole_id(userEarnRoleEntity.getId());
                                        userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity, request, response);
                                        map.put("userRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);
                                        finalEarn = finalEarn.subtract(masterRecommenderCash);
                                        userRecommenderIncomeLogEntity1.setMechanism_id(entity.getMechanism_id());
                                        userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getUser_id());
                                        userRecommenderIncomeLogEntity1.setRecommender_id(userRecommenderEntity.getId());
                                    }
                                }
                                map.put("finalEarn", finalEarn);
                                entity.setProfit(finalEarn);
                                mapper.updateProfit(entity);

                                UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(entity.getMaster_id());

                                if (userRecommenderIncomeLogEntity1.getRecommender_id() == null) {
                                    userRecommenderIncomeLogEntity1.setRecommender_id(0L);
                                }
                                userRecommenderIncomeLogEntity1.setIs_settlement(false);
                                userRecommenderIncomeLogEntity1.setCash(finalEarn);
                                userRecommenderIncomeLogEntity1.setType("class");
                                userRecommenderIncomeLogEntity1.setCash_describe("??????????????????");
                                userRecommenderIncomeLogEntity1.setAppointment_id(entity.getId());
                                userRecommenderIncomeLogEntity1.setMaster_id(masterInfoEntity.getId());
                                userRecommenderIncomeLogEntity1.setUser_id(entity.getMaster_id());
                                userRecommenderIncomeLogEntity1.setRole_id(userInfoEntity.getRole_id());
                                userRecommenderIncomeLogService.insert(userRecommenderIncomeLogEntity1, request, response);
                                map.put("masterRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity1);

                                Map<String, Object> saleCourseMap = userRecommenderIncomeLogService.queryCountByTime(entity, request, response);
                                map.put("saleCourseMap", saleCourseMap);

                                UserOrderEntity userOrderEntity = new UserOrderEntity();
                                userOrderEntity.setStart_time(entity.getStart_time());
                                userOrderEntity.setEnd_time(entity.getEnd_time());
                                userOrderEntity.setPayment_id(entity.getMaster_id());
                                map.put("giftTotal", userOrderMapper.queryListPageSum(userOrderEntity));

                                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                                rechargeRecordEntity.setStart_time(entity.getStart_time());
                                rechargeRecordEntity.setUpdate_time(entity.getUpdate_time());
                                BigDecimal userCash = rechargeRecordMapper.queryPayTotal(rechargeRecordEntity);
                                map.put("userCash", userCash);

                                map.put("earnTotal", finalEarn.add(new BigDecimal(saleCourseMap.get("cash").toString())));
                            }
                        }
                    }
                    entity.setMap(map);
                }
                    /*
                        Double profit_total = 0.0;
                        ClassRecordEntity classRecordCurriculum = new ClassRecordEntity();//?????????????????????
                        classRecordCurriculum.setCurriculum_id(entity.getId());//??????????????????id
                        classRecordCurriculum.setType("curriculum");//?????? ??????
                        classRecordCurriculum.setStatus(1);
                        Integer curriculum_count = classRecordMapper.queryListPageCount(classRecordCurriculum);//???????????????????????????
                        profit_total = (double) curriculum_count * 10;//10?????????1???
                        ClassRecordEntity classRecord = new ClassRecordEntity();
                        classRecord.setStatus(1);
                        classRecord.setCurriculum_id(entity.getId());
                        classRecord.setType("minute");
                        List<ClassRecordEntity> classRecordMinutes = classRecordMapper.query(classRecord);//???????????????
                        for (ClassRecordEntity classRecordEntity : classRecordMinutes) {
                            int classRecordMinute = classRecordEntity.getFree_minute() - 5;
                            profit_total = profit_total + 0.8 * classRecordMinute;//0.8????????????
                        }
                        BigDecimal bigDecimal = new BigDecimal("0");
                        UserOrderEntity userOrderEntit = new UserOrderEntity();
                        userOrderEntit.setSource("user");
                        userOrderEntit.setPay_id(entity.getId());//?????????id
                        userOrderEntit.setStart_time(param.getStart_time());
                        userOrderEntit.setEnd_time(param.getEnd_time());
                        List<UserOrderEntity> userOrderEntities = userOrderMapper.queryUserRankingListPage(userOrderEntit);//?????????????????????
                        if (userOrderEntities != null && userOrderEntities.size() > 0) {//????????????????????????
//                        userOrderEntit = userOrderEntities.get(0);
                            for (UserOrderEntity userOrderEntity : userOrderEntities) {
                                bigDecimal.add(new BigDecimal(String.valueOf(userOrderEntity.getPay_count())));
                            }
                        }
                        map.put("gift_total", bigDecimal);//????????????
                        map.put("profit_total", profit_total);//???????????????
                        String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                        map.put("groupinfo", JSONObject.parseObject(group_info));
                        MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                        masterCommentEntity.setAppointment_id(entity.getId());
                        masterCommentEntity.setParent_id(0L);
                        int commentCoumt = masterCommentService.queryListPageCount(masterCommentEntity, request, response);
                        map.put("commentCoumt", commentCoumt);//????????????
                        List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                        map.put("masterCommentEntities", masterCommentEntities);//??????????????????
                        ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                        classRecordEntity.setCurriculum_id(entity.getId());
                        classRecordEntity.setStatus(1);
                        Integer peopleNum = classRecordMapper.queryListPageCount(classRecordEntity);
                        map.put("peopleNum", peopleNum);//????????????
                        map.put("profit", 0);

                    map.put("masterCommentEntities", masterCommentEntities);??????
                } else if (entity.getType() != null && entity.getType().equalsIgnoreCase("single_class")) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(entity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryMechanismAppointment(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 1) {
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                    }
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        //????????????
                        map.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                        entity.setIs_appointment(true);
                    }
                    map.put("appointmentinfo", userAppointmentEntities);
                    */

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }

    private BigDecimal queryMasterType(UserAppointmentEntity userAppointmentEntity) {
        BigDecimal cash = new BigDecimal("0");
        UserEarnRoleEntity userEarnRoleEntity = userEarnRoleMapper.selectByPrimaryKey(0L);
        switch (userAppointmentEntity.getMaster_type()) {
            case "major":
                return userEarnRoleEntity.getEach_master_usa();
            case "cross_border":
                return userEarnRoleEntity.getEach_master_britain();
            case "mother_tongue":
                return userEarnRoleEntity.getEach_master_newzealand_canada_australia();
            default:
                return cash;
        }
    }

    private BigDecimal getEarn(String country, SpecialLecturerEntity specialLecturerEntity) {
        BigDecimal least_earn = new BigDecimal("0");
        switch (country) {
            case "??????":
                least_earn = specialLecturerEntity.getUsa_least_earn();
                break;
            case "?????????":
            case "?????????":
            case "????????????":
                least_earn = specialLecturerEntity.getNewzealand_canada_australia_least_earn();
                break;
            case "??????":
                least_earn = specialLecturerEntity.getBritain_least_earn();
                break;
            case "??????":
            case "??????":
                least_earn = specialLecturerEntity.getSouthafrica_europe_least_earn();
                break;
            default:
                least_earn = specialLecturerEntity.getElse_least_earn();
        }
        return least_earn;
    }

    @Override
    public Map<String, Object> queryOfflineMaster(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<MasterAppointmentEntity> masterAppointmentEntities = mapper.queryListPage(param);
            if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {

                for (MasterAppointmentEntity masterAppointmentEntity : masterAppointmentEntities) {
                    //????????????
                    masterAppointmentEntity.setTitle(EmojiParser.parseToUnicode(masterAppointmentEntity.getTitle()));
                    masterAppointmentEntity.setIntroduction_content(EmojiParser.parseToUnicode(masterAppointmentEntity.getIntroduction_content()));

                    MasterMechanismEntity masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterAppointmentEntity.getMechanism_id());
                    map.put("masterMechanismEntity", masterMechanismEntity);//????????????
                    MasterInfoEntity masterInfoEntity = masterInfoMapper.selectByPrimaryKey(masterAppointmentEntity.getMaster_id());
                    map.put("masterInfoEntity", masterInfoEntity);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
                    Integer currentStudentCount = userAppointmentMapper.queryStudentCount(userAppointmentEntity);
                    map.put("currentStudentCount", currentStudentCount);//???????????????
                    Integer classCount = userAppointmentMapper.queryListPageCount(userAppointmentEntity);
                    map.put("classCount", classCount);//????????????
                    masterAppointmentEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> countAppoinment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount", dayCount);//?????????

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount", weekCount);//?????????

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount", monthCount);//?????????

            Integer CountTotal = mapper.queryCountTotal(param);
            map.put("CountTotal", CountTotal);//??????

            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setRecommend_type(1);
            Integer recommendTotal = mapper.queryListPageCount(masterAppointmentEntity);
            map.put("recommendTotal", recommendTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryEachType(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer CountTotal = mapper.queryMasterAppointmentCount(param);
            map.put("CountTotal", CountTotal);//??????

            param.setLanguage("English");
            Integer englishMasterCount = mapper.queryMasterAppointmentCount(param);
            map.put(param.getLanguage() + "AppointmentCount", englishMasterCount);//???????????????

            param.setLanguage("French");
            Integer FrenchMasterCount = mapper.queryMasterAppointmentCount(param);
            map.put(param.getLanguage() + "AppointmentCount", FrenchMasterCount);//???????????????

            param.setLanguage("Japanese");
            Integer JapaneseMasterCount = mapper.queryMasterAppointmentCount(param);
            map.put(param.getLanguage() + "AppointmentCount", JapaneseMasterCount);//???????????????

            map.put("OtherAppointmentCount", CountTotal - englishMasterCount - FrenchMasterCount - JapaneseMasterCount);//???????????????

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;

        try {
            map = Maps.newHashMap();

            map.put("everyTimeCount", queryTimeCount(param));

            map.put("everyCourseCount", queryEveryCourseCount(param));

            map.put("queryEachTypeClass", queryEachTypeClass(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    private Object queryEachTypeClass(MasterAppointmentEntity param) {
        HashMap<String, Object> map = new HashMap<>();
        param.setType("online_video");
        Integer videoCount = mapper.queryAppointmentCountByTime(param);
        map.put("videoCount", videoCount);//?????????

        param.setType("online_voice");
        Integer voiceCount = mapper.queryAppointmentCountByTime(param);
        map.put("voiceCount", voiceCount);//?????????

        param.setType("multi_media");
        Integer multiMediaCount = mapper.queryAppointmentCountByTime(param);
        map.put("multiMediaCount", multiMediaCount);//????????????

        param.setLanguage("English");
        param.setType("online_video");
        Integer EnglishvideoCount = mapper.queryAppointmentCountByTime(param);
        map.put("EnglishvideoCount", EnglishvideoCount);//?????????

        param.setType("online_voice");
        Integer EnglishvoiceCount = mapper.queryAppointmentCountByTime(param);
        map.put("EnglishvoiceCount", EnglishvoiceCount);//?????????

        param.setType("multi_media");
        Integer EnglishMultiMediaCount = mapper.queryAppointmentCountByTime(param);
        map.put("EnglishMultiMediaCount", EnglishMultiMediaCount);//????????????

        return map;
    }

    private Object queryEveryCourseCount(MasterAppointmentEntity param) {
        HashMap<String, Object> map = new HashMap<>();

        Integer soonBeginCountTotal = mapper.querySoonClassCount(param);
        map.put("soonBeginCountTotal", soonBeginCountTotal);//??????????????????

        param.setLanguage("English");
        Integer soonClassCount = mapper.querySoonClassCount(param);
        map.put(param.getLanguage() + "SoonAppointmentCount", soonClassCount);//????????????????????????

        param.setLanguage(null);
        Integer BeginCountTotal = mapper.queryBeginListCount(param);
        map.put("BeginCountTotal", BeginCountTotal);//????????????

        Integer totalClassCount = mapper.queryAppointmentCountByTime(param);
        map.put("totalClassAppointmentCount", totalClassCount);//?????????

        param.setLanguage("English");
        Integer englishClassCount = mapper.queryAppointmentCountByTime(param);
        map.put(param.getLanguage() + "AppointmentCount", englishClassCount);//???????????????

        param.setLanguage("French");//????????????
        Integer FrenchAppointmentCount = mapper.queryAppointmentCountByTime(param);
        map.put(param.getLanguage() + "ClassCount", FrenchAppointmentCount);//????????????

        param.setLanguage("Japanese");//????????????
        Integer JapaneseAppointmentCount = mapper.queryAppointmentCountByTime(param);
        map.put(param.getLanguage() + "ClassCount", JapaneseAppointmentCount);//????????????


        param.setLanguage("English");
        Integer endClassCount = mapper.queryEndClassCount(param);
        map.put(param.getLanguage() + "EndAppointmentCount", endClassCount);//????????????
        map.put("otherClassCount", totalClassCount - FrenchAppointmentCount - JapaneseAppointmentCount - englishClassCount);//????????????

        param.setStatus(null);
        param.setLanguage(null);
        Integer HistoryCountTotal = mapper.queryEndClassCount(param);
        map.put("HistoryCountTotal", HistoryCountTotal);//????????????
        return map;
    }

    private Object queryTimeCount(MasterAppointmentEntity param) {
        HashMap<String, Object> map = new HashMap<>();

        Integer dayCount = mapper.queryDayCount(param);
        map.put("dayCount", dayCount);//?????????

        Integer weekCount = mapper.queryWeekCount(param);
        map.put("weekCount", weekCount);//?????????

        Integer monthCount = mapper.queryMonthCount(param);
        map.put("monthCount", monthCount);//?????????

        Integer CountTotal = mapper.queryCountTotal(param);
        map.put("CountTotal", CountTotal);//??????

        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
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
            List<MasterAppointmentEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                for (MasterAppointmentEntity masterAppointmentEntity : list) {
                    HashMap<String, Object> map1 = new HashMap<>();
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryListPage(userAppointmentEntity);//????????????????????????
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {//foreach????????????0?????????
                        for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                            Map<String, Object> objectMap = new HashMap<>();
                            objectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(appointmentEntity.getUser_id() + "userinfo")));
                            appointmentEntity.setMap(objectMap);
                        }
                        if (userAppointmentEntities.size() > 0) {//foreach????????????0?????????
                            //????????????
                            map1.put("appointmentuserinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntities.get(0).getUser_id() + "userinfo")));
                            masterAppointmentEntity.setIs_appointment(true);
                            map1.put("appointmentinfo", userAppointmentEntities);
                        }
                    }
                    if (masterAppointmentEntity.getMechanism_id() != null && masterAppointmentEntity.getMechanism_id() != 0) {
                        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(masterAppointmentEntity.getMechanism_id());
                        map1.put("mechanismInfo", mechanismEntity);
                    } else {
                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setType("live_lecturer");
                        masterInfoEntity.setUser_id(masterAppointmentEntity.getMaster_id());
                        List<MasterInfoEntity> list1 = masterInfoMapper.queryListPage(masterInfoEntity);
                        if (list1 != null && list1.size() > 0) {
                            masterInfoEntity = list1.get(0);
                        }
                        //???????????????
                        map1.put("masterInfo", masterInfoEntity);
                    }
                    masterAppointmentEntity.setMap(map1);
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
    public Map<String, Object> queryCountNum(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            //???????????????  ?????????
            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
            userClassCardEntity.setType("minute");
            userClassCardEntity.setStatus(2);
            userClassCardEntity.setUser_id(param.getUser_id());
            Integer classMinuteTotal = userClassCardMapper.queryPayTotal(userClassCardEntity);
            map.put("classCardMinuteTotal", classMinuteTotal);

            //?????????
            userClassCardEntity.setType("curriculum");
            Integer classCardCurriculumTotal = userClassCardMapper.queryPayTotal(userClassCardEntity);
            map.put("classCardCurriculumTotal", classCardCurriculumTotal);

            //??????????????????   ?????????
            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
            userStudyCardEntity.setStatus(2);
            userStudyCardEntity.setUser_id(param.getUser_id());
            userStudyCardEntity.setMechanism_id(0L);
            userStudyCardEntity.setMaster_id(0L);
            Integer studyCardCurriculum = userStudyCardMapper.queryPayCount(userStudyCardEntity);
            map.put("studyCardCurriculum", studyCardCurriculum);

            //?????? ????????????
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUser_id(param.getUser_id());
            userInfoEntity.setPageSize(1);
            List<UserInfoEntity> list = userInfoMapper.queryListPage(userInfoEntity);
            BigDecimal cat_coin = new BigDecimal("0");
            if (list != null && list.size() > 0) {
                cat_coin = list.get(0).getCat_coin();
            }
            map.put("catCoinTotal", cat_coin);

            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setUser_id(param.getUser_id());

            //?????????
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setMaster_id(param.getUser_id());

            //?????????
            Integer masterSoonCount = mapper.querySoonCount(masterAppointmentEntity);
            map.put("masterSoonCount", masterSoonCount);

            //??????????????????
            userAppointmentEntity.setStatus(2);
            Integer studentSoonCount = userAppointmentMapper.queryDayCount(userAppointmentEntity);
            map.put("studentSoonCount", studentSoonCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam updateShowList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getEntities() != null) {
                List<String> strings = JSONUtils.json2List(param.getEntities(), String.class);
                for (String string : strings) {
                    long id = Long.parseLong(string);
                    Integer ret = mapper.updateIsFirstpageShow(id, param.getIs_firstpage_show());
                    if (ret == 0) {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                }
            } else {
                return ResultUtil.error(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getDesc());
            }
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    public ResultParam update(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return super.update(param, request, response);
    }
}