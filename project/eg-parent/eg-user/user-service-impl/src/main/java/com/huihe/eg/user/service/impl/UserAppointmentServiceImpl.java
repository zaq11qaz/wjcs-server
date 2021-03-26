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
import com.huihe.eg.user.model.count.AppointmentParam;
import com.huihe.eg.user.model.count.ChartParam;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;
import com.huihe.eg.user.service.dao.pay.PayService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huihe.eg.user.service.impl.MasterAppointmentServiceImpl.getRoomId;

@Service
public class UserAppointmentServiceImpl extends BaseFrameworkServiceImpl<UserAppointmentEntity, Long> implements UserAppointmentService {

    @Resource
    private UserAppointmentMapper mapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private MasterSummaryMapper masterSummaryMapper;
    @Resource
    private MasterInfoService masterInfoService;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserIdentityService userIdentityService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private MasterCommentService masterCommentService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TimeZoneMapper timeZoneMapper;
    @Resource
    private MasterTypeMapper masterTypeMapper;
    @Resource
    private MasterNoticeMapper noticeMapper;
    @Resource
    private MasterNoticeService masterNoticeService;
    @Resource
    private UserOrderService orderService;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private MasterMechanismService masterMechanismService;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private PayService payService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MechanismUserService mechanismUserService;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;
    @Resource
    private UserPointsMapper userPointsMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResultParam insert(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*
            UserAppointmentEntity userAppointmentEntity2 = new UserAppointmentEntity();
            userAppointmentEntity2.setUser_id(param.getUser_id());
            userAppointmentEntity2.setStatus(8);
            userAppointmentEntity2.setPageSize(1);
            List<UserAppointmentEntity> list = mapper.queryListPage(userAppointmentEntity2);
            if (list!=null&&list.size()>0) {

             */
            MasterAppointmentEntity masterAppointmentEntityStatic = null;
            if (param.getAppointment_id() != null && param.getAppointment_id() != 0) {
                masterAppointmentEntityStatic = masterAppointmentMapper.selectByPrimaryKey(param.getAppointment_id());
                masterAppointmentEntityStatic.setIs_appointment(true);
                masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntityStatic);
            }

            if (StringUtil.isNotEmpty(param.getUserAppointmentEntities())) {//若集合非空
                List<UserAppointmentEntity> userAppointmentEntities = JSONUtils.json2List(param.getUserAppointmentEntities(), UserAppointmentEntity.class);//json2List
                int num = 0;
                //循环对每一个操作
                for (UserAppointmentEntity userAppointmentEntity : userAppointmentEntities) {
                    //查询这节课信息
                    MasterAppointmentEntity masterAppointment = new MasterAppointmentEntity();
                    masterAppointment.setId(userAppointmentEntity.getAppointment_id());
                    masterAppointment.setStatus(1);
                    masterAppointment.setPageSize(1);
                    //查询id为这一条 正常的
                    List<MasterAppointmentEntity> masterAppointmentEntities1 = masterAppointmentMapper.queryListPage(masterAppointment);
                    //非空
                    if (masterAppointmentEntities1 != null && masterAppointmentEntities1.size() > 0) {
                        //重新赋值为查到的那一条
                        masterAppointment = masterAppointmentEntities1.get(0);
                        MasterAppointmentEntity masterAppointment1 = new MasterAppointmentEntity();
                        masterAppointment1.setMaster_id(param.getUser_id());
                        masterAppointment1.setStart_time(masterAppointment.getStart_time());
                        masterAppointment1.setEnd_time(masterAppointment.getEnd_time());
                        masterAppointment1.setOffset(masterAppointment.getOffset());
                        //查询时间段内作为助学师是否有课程
                        List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointment1);
                        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                            num++;
                            continue;
                        }
                        UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                        userAppointmentEntity1.setAppointment_id(masterAppointment.getId());
                        userAppointmentEntity1.setStatus(2);
                        //查询待上课的一条
                        List<UserAppointmentEntity> subscribe_Appointment = mapper.queryListPage(userAppointmentEntity1);
                        if (subscribe_Appointment != null && subscribe_Appointment.size() >= masterAppointment.getConnect_peoplenum()) {
                            num++;
                            continue;
                        }
                        userAppointmentEntity.setStart_time(masterAppointment.getStart_time());
                        userAppointmentEntity.setEnd_time(masterAppointment.getEnd_time());
                        userAppointmentEntity.setOffset(masterAppointment.getOffset());
                        userAppointmentEntity.setTimezone_id(masterAppointment.getTimezone_id());
                    } else {
                        return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                    }
                    //查询时间是否冲突
                    List<UserAppointmentEntity> userAppointments = mapper.queryIsAppointmentClass(userAppointmentEntity);
                    if (userAppointments != null && userAppointments.size() > 0) {
                        num++;
                        continue;
                    }
                    if (param.getService_type() != null && "offline".equalsIgnoreCase(param.getService_type())) {
                        //流水号
                        param.setPipeline_num(CommonUtils.generatePayCode());
                        masterAppointment.setLanguage(param.getLanguage());
                        masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointment);
                        int integer = mapper.insertSelective(param);
                        if (integer > 0) {
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                            pushMessageParam.setTarget_id(param.getMaster_id());
                            pushMessageParam.setTitle("预约课程提醒");
                            pushMessageParam.setView_type("user");
                            pushMessageParam.setPush_type("curriculum_appointment");
                            pushMessageParam.setOpera_type("curriculum_appointment");
                            pushMessageParam.setContent((infoEntity.getNick_name() + "预约了你的线下课程，点击前往处理").trim());
                            pushMessageParam.setType("curriculum_appointment");
                            pushMessageParam.setTitle("预约课程提醒");
                            //获取课堂id
                            pushMessageParam.setType_id(param.getId());
                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                        }
                    } else {
                        if (userAppointmentEntity.getMaster_type() != null && "experience".equalsIgnoreCase(userAppointmentEntity.getMaster_type())) {
                            UserCouponEntity userCouponEntity = new UserCouponEntity();
                            userCouponEntity.setUser_id(userAppointmentEntity.getUser_id());
                            userCouponEntity.setStatus(1);
                            userCouponEntity.setPageSize(1);
                            List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                            if (userCouponEntities != null && userCouponEntities.size() > 0) {
                                userCouponEntity = userCouponEntities.get(0);
                                Integer count = userCouponEntity.getCourse_num();
                                if (count > 0) {
                                    count = count - 1;
                                    if (count > 0) {
                                        userCouponEntity.setCourse_num(count);
                                    } else {
                                        userCouponEntity.setStatus(3);
                                        userCouponEntity.setCourse_num(count);
                                    }
                                    userCouponMapper.updateByPrimaryKeySelective(userCouponEntity);
                                    userAppointmentEntity.setPipeline_num(CommonUtils.generatePayCode());
                                    masterAppointment.setLanguage(param.getLanguage());
                                    masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointment);
                                    int integer = mapper.insertSelective(userAppointmentEntity);
                                    if (integer > 0) {
                                        //消息中心
                                        MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                                        masterNoticeEntity.setAppointment_id(userAppointmentEntity.getId());
                                        masterNoticeEntity.setType("curriculumappointment");
                                        masterNoticeEntity.setMechanism_id(userAppointmentEntity.getMechanism_id());
                                        masterNoticeEntity.setUser_id(userAppointmentEntity.getUser_id());
                                        masterNoticeEntity.setMaster_id(userAppointmentEntity.getMaster_id());
                                        masterNoticeEntity.setStatus(2);
                                        masterNoticeEntity.setIdentity("user");
                                        masterNoticeEntity.setRead_type("masterread");
                                        Integer result = noticeMapper.insertSelective(masterNoticeEntity);
                                        if (result > 0) {
                                            //推送
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                                            pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_appointment");
                                            if (param.getMaster_id() != null && param.getMaster_id() != 0) {
                                                pushMessageParam.setOpera_type("curriculum_appointment");
                                                pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的课程，点击查看");
                                            } else {
                                                pushMessageParam.setOpera_type("curriculum_appointment_mechanism");
                                                pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的机构课程，点击查看");
                                            }
                                            pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的课程，点击查看");
                                            pushMessageParam.setType_id(param.getId());//获取课堂id
                                            pushMessageParam.setType("curriculum_appointment");
                                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }
                                    }
                                }
                            } else {
                                return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                            }
                        } else {
                            //查询学习卡
                            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                            if (masterAppointment.getConnect_peoplenum() != null && masterAppointment.getConnect_peoplenum() > 1) {
                                userStudyCardEntity.setType(userAppointmentEntity.getMaster_type() + "_special");
                            } else {
                                userStudyCardEntity.setType(userAppointmentEntity.getMaster_type());
                            }
                            userStudyCardEntity.setUser_id(userAppointmentEntity.getUser_id());
                            userStudyCardEntity.setPageSize(1);
                            userStudyCardEntity.setStatus(2);
                            List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);
                            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                                //用户购买过 判断剩余课时
                                if (userStudyCardEntities.get(0).getCourse_num() != null && userStudyCardEntities.get(0).getCourse_num() > 0) {
                                    int ret = 0;
                                    int integer = 0;
                                    UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                                    userAppointmentEntity1.setAppointment_id(masterAppointment.getId());
                                    userAppointmentEntity1.setStatus(6);
                                    List<UserAppointmentEntity> subscribe_Appointment = mapper.queryListPage(userAppointmentEntity1);
                                    if (subscribe_Appointment != null && subscribe_Appointment.size() >= masterAppointment.getConnect_peoplenum()) {
                                        num++;
                                        continue;
                                    } else {
                                        //获取对应类型的收益
                                        MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                                        masterTypeEntity.setType(param.getMaster_type());
                                        masterTypeEntity.setPageSize(1);
                                        List<MasterTypeEntity> masterTypeEntities = masterTypeMapper.queryListPage(masterTypeEntity);
                                        if (masterTypeEntities != null && masterTypeEntities.size() > 0) {
                                            userAppointmentEntity.setEarnings(masterTypeEntities.get(0).getEarnings());
                                        } else {
                                            return ResultUtil.error(MasterEum.master_40008.getCode(), MasterEum.master_40008.getDesc());
                                        }
                                        userAppointmentEntity.setPipeline_num(CommonUtils.generatePayCode());
                                        masterAppointment.setLanguage(param.getLanguage());
                                        masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointment);
                                        integer = mapper.insertSelective(userAppointmentEntity);
                                    }
                                    if (integer > 0) {
                                        //消息中心
                                        MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                                        masterNoticeEntity.setAppointment_id(param.getId());
                                        masterNoticeEntity.setType("curriculumappointment");
                                        masterNoticeEntity.setUser_id(userAppointmentEntity.getUser_id());
                                        masterNoticeEntity.setMaster_id(userAppointmentEntity.getMaster_id());
                                        masterNoticeEntity.setStatus(2);
                                        masterNoticeEntity.setMechanism_id(userAppointmentEntity.getMechanism_id());
                                        masterNoticeEntity.setIdentity("user");
                                        masterNoticeEntity.setRead_type("masterread");
                                        Integer result = noticeMapper.insertSelective(masterNoticeEntity);
                                       /* if (result > 0) {
                                            //推送
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            UserInfoEntity infoEntity = userInfoService.findById(userAppointmentEntity.getUser_id(), request, response);
                                            pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_Appointment");
                                            pushMessageParam.setOpera_type("curriculum_Appointment");
                                            pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的课程，点击查看");
                                            pushMessageParam.setType_id(param.getId());//获取课堂id
                                            pushMessageParam.setType("curriculum_Appointment");
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }*/
                                        if (subscribe_Appointment != null && subscribe_Appointment.size() == masterAppointment.getConnect_peoplenum()) {
                                            //助学师课程状态
                                            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                                            masterAppointmentEntity.setId(param.getAppointment_id());
                                            masterAppointmentEntity.setStatus(2);
                                            masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                                            for (UserAppointmentEntity appointmentEntity : subscribe_Appointment) {
                                                //查询学习卡
                                                UserStudyCardEntity studyCardEntity = new UserStudyCardEntity();
                                                if (masterAppointment.getConnect_peoplenum() != null && masterAppointment.getConnect_peoplenum() > 1) {
                                                    studyCardEntity.setType(appointmentEntity.getMaster_type() + "_special");
                                                } else {
                                                    studyCardEntity.setType(appointmentEntity.getMaster_type());
                                                }
                                                studyCardEntity.setUser_id(appointmentEntity.getUser_id());
                                                studyCardEntity.setPageSize(1);
                                                studyCardEntity.setStatus(2);
                                                synchronized (userStudyCardEntity) {
                                                    ret = userStudyCardMapper.updateCourseNum(userStudyCardEntity);//减去学习卡剩余课时
                                                }
                                                if (ret > 0) {
                                                    appointmentEntity.setStatus(2);
                                                    mapper.updateByPrimaryKeySelective(appointmentEntity);
                                                } else {
                                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                                }
                                            }
                                            //推送
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            UserInfoEntity infoEntity = userInfoService.findById(userAppointmentEntity.getUser_id(), request, response);
                                            pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_appointment_full");
                                            pushMessageParam.setOpera_type("curriculum_appointment_full");
                                            pushMessageParam.setContent("您的课程人数已满，可准时开课");
                                            pushMessageParam.setType_id(masterAppointment.getId());//获取课堂id
                                            pushMessageParam.setType("curriculum_appointment_full");
                                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }
                                    }
                                } else {
                                    return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                }
                            } else {
                                return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                            }
                        }
                    }
                }
                if (num > 0) {
                    return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                } else {
                    return ResultUtil.success();
                }
            } else {//集合空 只有一条
                if (param.getMaster_type() != null && "private_education".equalsIgnoreCase(param.getMaster_type())) {//私教
                    MasterAppointmentEntity masterAppointment1 = new MasterAppointmentEntity();
                    masterAppointment1.setMaster_id(param.getUser_id());
                    masterAppointment1.setStart_time(param.getStart_time());
                    masterAppointment1.setEnd_time(param.getEnd_time());
                    masterAppointment1.setOffset(param.getOffset());
                    //查询时间段内作为助学师是否有课程
                    List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointment1);
                    if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                        return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                    }
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setUser_id(param.getUser_id());
                    userAppointmentEntity.setStart_time(param.getStart_time());
                    userAppointmentEntity.setEnd_time(param.getEnd_time());
                    userAppointmentEntity.setOffset(param.getOffset());
                    userAppointmentEntity.setPageSize(1);
                    //查询时间是否冲突
                    List<UserAppointmentEntity> userAppointments = mapper.queryIsAppointmentClass(userAppointmentEntity);
                    if (userAppointments != null && userAppointments.size() > 0) {//如果冲突
                        return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                    } else {
                        param.setPipeline_num(CommonUtils.generatePayCode());//流水号
                        int integer = mapper.insertSelective(param);
                        if (integer > 0) {//插入预约信息
                            //消息中心
                            MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                            masterNoticeEntity.setAppointment_id(param.getId());
                            masterNoticeEntity.setType("curriculumappointment");
                            masterNoticeEntity.setUser_id(param.getUser_id());
                            masterNoticeEntity.setMaster_id(param.getMaster_id());
                            masterNoticeEntity.setStatus(2);
                            masterNoticeEntity.setMechanism_id(param.getMechanism_id());
                            masterNoticeEntity.setIdentity("user");
                            masterNoticeEntity.setRead_type("masterread");
                            Integer result = noticeMapper.insertSelective(masterNoticeEntity);
                            if (result > 0) {
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                                pushMessageParam.setTarget_id(param.getMaster_id());
                                pushMessageParam.setPush_type("curriculum_appointment");
                                pushMessageParam.setOpera_type("curriculum_appointment");
                                pushMessageParam.setContent((infoEntity.getNick_name() + "预约了您的私教课程，点击前往处理").trim());
                                pushMessageParam.setType("curriculum_appointment");
                                pushMessageParam.setType_id(param.getId());//获取课节id
                                pushMessageParam.setTitle("预约课程提醒");
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                            }
                        }
                    }
                } else if (masterAppointmentEntityStatic != null && "mechanism_offline".equals(masterAppointmentEntityStatic.getType())) {

                    UserAppointmentEntity userAppointmentEntityIsApped = new UserAppointmentEntity();
                    userAppointmentEntityIsApped.setUser_id(param.getUser_id());
                    userAppointmentEntityIsApped.setAppointment_id(param.getAppointment_id());
                    userAppointmentEntityIsApped.setStatus(2);
                    Integer integer = mapper.queryListPageCount(userAppointmentEntityIsApped);
                    if (integer > 0) {
                        return ResultUtil.error(MasterEum.master_40022.getCode(), MasterEum.master_40022.getDesc());
                    }

                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setAppointment_id(masterAppointmentEntityStatic.getId());
                    int studentCount = mapper.query2and3(userAppointmentEntity).size();
                    if (studentCount >= masterAppointmentEntityStatic.getConnect_peoplenum()) {
                        return ResultUtil.error(MasterEum.master_40013.getCode(), MasterEum.master_40013.getDesc());
                    }
                    param.setStart_time(masterAppointmentEntityStatic.getStart_time());
                    param.setEnd_time(masterAppointmentEntityStatic.getEnd_time());
                    param.setOffset(masterAppointmentEntityStatic.getOffset());
                    param.setMaster_id(masterAppointmentEntityStatic.getMaster_id());
                    param.setMaster_type(masterAppointmentEntityStatic.getType());
                    param.setMaster_set_price_id(masterAppointmentEntityStatic.getMaster_set_price_id());
                    param.setMechanism_id(masterAppointmentEntityStatic.getMechanism_id());
                    masterAppointmentEntityStatic.setIs_qualified(false);
                    RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                    rechargeRecordEntity.setSource(param.getSource());
                    rechargeRecordEntity.setTitle(param.getTitle());
                    if (param.getStudy_card_id() != null && param.getStudy_card_id() != 0) {
                        UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(param.getStudy_card_id());
                        if (userStudyCardEntity.getCourse_num() > 0) {
                            ResultParam resultParam = this.insertMechanismUser(param, request, response);
                            if (resultParam.getData() != null && "true".equals(resultParam.getData().toString())) {
                                rechargeRecordEntity.setIs_free(true);
                            }
                            BigDecimal price = userStudyCardEntity.getEach_lesson_price();
                            if (userStudyCardEntity.getIs_one_time_payment()) {
                                int i = userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                if (i > 0) {
                                    param.setStatus(2);
                                    rechargeRecordEntity.setAmount(price);
                                    rechargeRecordEntity.setUser_id(param.getUser_id());
                                    rechargeRecordEntity.setCourse_num(1);
                                    rechargeRecordEntity.setStudy_type(userStudyCardEntity.getType());
                                    rechargeRecordEntity.setRcharge_type("study_card");
                                    if (userStudyCardEntity.getIs_Interoperability()) {
                                        rechargeRecordEntity.setMechanism_id(param.getMechanism_id());
                                    } else {
                                        rechargeRecordEntity.setMechanism_id(userStudyCardEntity.getMechanism_id());
                                    }
                                    rechargeRecordEntity.setIs_one_time_payment(true);
                                    rechargeRecordEntity.setFlowing_no(CommonUtils.generateFlowingCode());
                                    rechargeRecordEntity.setRcharge_abstract("购买单节课");
                                    rechargeRecordEntity.setAppointment_id(param.getAppointment_id());
                                    rechargeRecordEntity.setFinished(false);
                                    rechargeRecordEntity.setStudycard_id(userStudyCardEntity.getStudycard_id());
                                    rechargeRecordEntity.setSource(param.getSource());
                                    rechargeRecordEntity.setUser_appointment_id(param.getId());
                                    rechargeRecordEntity.setTitle(param.getTitle());
                                    rechargeRecordEntity.setType("single_session");
                                    param.setStatus(2);
                                    param.setEarnings(price);
                                    rechargeRecordEntity.setNumber_of_lessons(param.getNumber_of_lessons() != null ? param.getNumber_of_lessons().intValue() : 0);
                                    super.insert(param, request, response);
                                    rechargeRecordEntity.setIs_teach_paypal(true);
                                    int res1 = rechargeRecordMapper.insertSelective(rechargeRecordEntity);
                                    if (res1 == 0) {
                                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                                    } else {
                                        PushMessageParam pushMessageParamMaster = new PushMessageParam();
                                        UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                                        pushMessageParamMaster.setTarget_id(param.getMaster_id());
                                        pushMessageParamMaster.setView_type("master");
                                        pushMessageParamMaster.setPush_type("curriculum_appointment");
                                        pushMessageParamMaster.setOpera_type("curriculum_appointment");
                                        pushMessageParamMaster.setContent((infoEntity.getNick_name() + "预约了您的线下课程，点击前往处理").trim());
                                        pushMessageParamMaster.setType("curriculum_appointment");
                                        pushMessageParamMaster.setType_id(param.getId());
                                        pushMessageParamMaster.setTitle("预约课程提醒");
                                        pushMessageParamMaster.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMaster);

                                        PushMessageParam pushMessageParamUser = new PushMessageParam();
                                        pushMessageParamUser.setTarget_id(param.getUser_id());
                                        pushMessageParamUser.setView_type("user");
                                        pushMessageParamUser.setPush_type("curriculum_appointment");
                                        pushMessageParamUser.setOpera_type("curriculum_appointment");
                                        pushMessageParamUser.setContent(("您的课程预约成功,请按时上课").trim());
                                        pushMessageParamUser.setType("curriculum_appointment");
                                        pushMessageParamUser.setType_id(param.getAppointment_id());
                                        pushMessageParamUser.setTitle("预约课程提醒");
                                        pushMessageParamUser.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamUser);

                                        PushMessageParam pushMessageParamMechanism = new PushMessageParam();
                                        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id());
                                        pushMessageParamMechanism.setTarget_id(mechanismEntity.getUser_id());
                                        pushMessageParamMechanism.setView_type("mechanism");
                                        pushMessageParamMechanism.setPush_type("curriculum_appointment");
                                        pushMessageParamMechanism.setOpera_type("curriculum_appointment");
                                        pushMessageParamMechanism.setContent((infoEntity.getNick_name() + "预约了您的机构线下课程，点击前往处理").trim());
                                        pushMessageParamMechanism.setType("curriculum_appointment");
                                        pushMessageParamMechanism.setType_id(param.getId());
                                        pushMessageParamMechanism.setTitle("预约课程提醒");
                                        pushMessageParamMechanism.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMechanism);
                                        return ResultUtil.success();
                                    }
                                }
                            } else {
                                param.setStatus(1);
                                rechargeRecordEntity.setAmount(price);
                                rechargeRecordEntity.setUser_id(param.getUser_id());
                                rechargeRecordEntity.setCourse_num(1);
                                rechargeRecordEntity.setRcharge_type("prepayment");
                                rechargeRecordEntity.setStudy_type(masterAppointmentEntityStatic.getType());
                                rechargeRecordEntity.setMechanism_id(masterAppointmentEntityStatic.getMechanism_id());
                                rechargeRecordEntity.setIs_one_time_payment(true);
                                rechargeRecordEntity.setRcharge_abstract("购买单节课");
                                rechargeRecordEntity.setAppointment_id(param.getAppointment_id());
                                rechargeRecordEntity.setSource(param.getSource());
                                rechargeRecordEntity.setCoupon_id(param.getCoupon_id());
                                rechargeRecordEntity.setPoints(param.getPoint());
                                rechargeRecordEntity.setStudycard_id(userStudyCardEntity.getStudycard_id());
                                rechargeRecordEntity.setFinished(false);
                                rechargeRecordEntity.setPoints(param.getPoint());
                                rechargeRecordEntity.setTitle(param.getTitle());
                                rechargeRecordEntity.setType("single_session");
                                param.setStatus(1);
                                param.setIs_pay(true);
                                param.setEarnings(price);
                                if (param.getWhether() == null) {
                                    super.insert(param, request, response);
                                    rechargeRecordEntity.setUser_appointment_id(param.getId());
//                                    userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                    rechargeRecordEntity.setNumber_of_lessons(param.getNumber_of_lessons() != null ? param.getNumber_of_lessons().intValue() : 0);

                                    rechargeRecordEntity.setIs_teach_paypal(true);
                                    rechargeRecordMapper.insertSelective(rechargeRecordEntity);
                                    return payService.aliPrepayment(rechargeRecordEntity, request, response);
                                } else {
                                    param.setStatus(2);
                                    super.insert(param, request, response);
//                                    rechargeRecordEntity.setUser_appointment_id(param.getId());
//                                    rechargeRecordMapper.insertSelective(rechargeRecordEntity);
//                                    userStudyCardMapper.updateCourseNum(userStudyCardEntity);
                                    return ResultUtil.success();
                                }
                            }
                        } else {
                            return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                        }
                    }
                } else {//不是私教课
                    //查询这节课信息
                    MasterAppointmentEntity masterAppointment = new MasterAppointmentEntity();
                    masterAppointment.setId(param.getAppointment_id());
                    masterAppointment.setStatus(1);
                    masterAppointment.setPageSize(1);
                    List<MasterAppointmentEntity> masterAppointmentEntities1 = masterAppointmentMapper.queryListPage(masterAppointment);//查询是否有这节课
                    if (masterAppointmentEntities1 != null && masterAppointmentEntities1.size() > 0) {
                        masterAppointment = masterAppointmentEntities1.get(0);
                        MasterAppointmentEntity masterAppointment1 = new MasterAppointmentEntity();
                        masterAppointment1.setMaster_id(param.getUser_id());
                        masterAppointment1.setStart_time(masterAppointment.getStart_time());
                        masterAppointment1.setEnd_time(masterAppointment.getEnd_time());
                        masterAppointment1.setOffset(masterAppointment.getOffset());
                        masterAppointment1.setPageSize(1);
                        //查询时间段内作为助学师是否有课程
                        List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointment1);
                        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                            return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                        }
                        param.setStart_time(masterAppointment.getStart_time());
                        param.setEnd_time(masterAppointment.getEnd_time());
                        param.setOffset(masterAppointment.getOffset());
                        param.setTimezone_id(masterAppointment.getTimezone_id());
                    } else {
                        return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                    }
                    param.setPageSize(1);
                    //查询时间是否冲突
                    List<UserAppointmentEntity> userAppointments = mapper.queryIsAppointmentClass(param);
                    if (userAppointments != null && userAppointments.size() > 0) {
                        return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                    } else {
                        if (param.getMaster_type() != null && "experience".equalsIgnoreCase(param.getMaster_type())) {//用券
                            UserCouponEntity userCouponEntity = new UserCouponEntity();
                            userCouponEntity.setUser_id(param.getUser_id());
                            userCouponEntity.setStatus(1);
                            userCouponEntity.setPageSize(1);
                            List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                            if (userCouponEntities != null && userCouponEntities.size() > 0) {
                                userCouponEntity = userCouponEntities.get(0);
                                Integer count = userCouponEntity.getCourse_num();
                                if (count > 0) {
                                    count = count - 1;
                                    if (count > 0) {
                                        userCouponEntity.setCourse_num(count);
                                    } else {
                                        userCouponEntity.setStatus(3);
                                        userCouponEntity.setCourse_num(count);
                                    }
                                    userCouponMapper.updateByPrimaryKeySelective(userCouponEntity);
                                    param.setPipeline_num(CommonUtils.generatePayCode());//流水号
                                    int integer = mapper.insertSelective(param);
                                    if (integer > 0) {
                                        //消息中心
                                        MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                                        masterNoticeEntity.setAppointment_id(param.getId());
                                        masterNoticeEntity.setType("curriculumappointment");
                                        masterNoticeEntity.setUser_id(param.getUser_id());
                                        masterNoticeEntity.setMaster_id(param.getMaster_id());
                                        masterNoticeEntity.setStatus(2);
                                        masterNoticeEntity.setMechanism_id(param.getMechanism_id());
                                        masterNoticeEntity.setIdentity("user");
                                        masterNoticeEntity.setRead_type("masterread");
                                        Integer result = noticeMapper.insertSelective(masterNoticeEntity);
                                        if (result > 0) {
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                                            pushMessageParam.setTarget_id(param.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_appointment");
                                            if (param.getMaster_id() != null && param.getMaster_id() != 0) {
                                                pushMessageParam.setOpera_type("curriculum_appointment");
                                                pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的课程，点击查看");
                                            } else {
                                                pushMessageParam.setOpera_type("curriculum_appointment_mechanism");
                                                pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的机构课程，点击查看");
                                            }
                                            pushMessageParam.setType("curriculum_appointment");
                                            pushMessageParam.setType_id(param.getId());//获取课堂id
                                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }
                                    } else {
                                        return ResultUtil.error(MasterEum.master_40008.getCode(), MasterEum.master_40008.getDesc());
                                    }
                                } else {
                                    return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                                }
                            }
                        } else {
                            //查询学习卡
                            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                            if (masterAppointment.getConnect_peoplenum() != null && masterAppointment.getConnect_peoplenum() > 1) {
                                userStudyCardEntity.setType(param.getMaster_type() + "_special");
                            } else {
                                userStudyCardEntity.setType(param.getMaster_type());
                            }
                            userStudyCardEntity.setUser_id(param.getUser_id());
                            userStudyCardEntity.setPageSize(1);
                            userStudyCardEntity.setStatus(2);
                            List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);
                            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                                //用户购买过时 判断剩余课时
                                if (userStudyCardEntities.get(0).getCourse_num() != null && userStudyCardEntities.get(0).getCourse_num() > 0) {
                                    int ret = 0;
                                    int integer = 0;
                                    UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
                                    userAppointmentEntity1.setAppointment_id(masterAppointment.getId());
                                    userAppointmentEntity1.setStatus(2);//之前是6 修改为2 待上课人数
                                    List<UserAppointmentEntity> subscribe_count = mapper.queryListPage(userAppointmentEntity1);
                                    if (subscribe_count != null && subscribe_count.size() >= masterAppointment.getConnect_peoplenum()) {
                                        return ResultUtil.error(MasterEum.master_40013.getCode(), MasterEum.master_40013.getDesc());
                                        //预约人数是否到达上限
                                    } else {
                                        //获取对应类型的收益
                                        MasterTypeEntity masterTypeEntity = new MasterTypeEntity();
                                        masterTypeEntity.setType(param.getMaster_type());
                                        masterTypeEntity.setPageSize(1);
                                        List<MasterTypeEntity> masterTypeEntities = masterTypeMapper.queryListPage(masterTypeEntity);
                                        if (masterTypeEntities != null && masterTypeEntities.size() > 0) {
                                            param.setEarnings(masterTypeEntities.get(0).getEarnings());
                                        } else {
                                            return ResultUtil.error(MasterEum.master_40008.getCode(), MasterEum.master_40008.getDesc());
                                        }
                                        param.setPipeline_num(CommonUtils.generatePayCode());
                                        param.setMaster_id(masterAppointment.getMaster_id());
                                        masterAppointment.setLanguage(param.getLanguage());
                                        masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointment);
                                        integer = mapper.insertSelective(param);
                                    }
                                    if (integer > 0) {
                                        //消息中心
                                        MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                                        masterNoticeEntity.setAppointment_id(param.getId());
                                        masterNoticeEntity.setType("curriculumappointment");
                                        masterNoticeEntity.setUser_id(param.getUser_id());
                                        masterNoticeEntity.setMaster_id(param.getMaster_id());
                                        masterNoticeEntity.setStatus(2);
                                        masterNoticeEntity.setMechanism_id(param.getMechanism_id());
                                        masterNoticeEntity.setIdentity("user");
                                        masterNoticeEntity.setRead_type("masterread");
                                        Integer result = noticeMapper.insertSelective(masterNoticeEntity);
                                        /*if (result > 0) {
                                            //推送
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                                            pushMessageParam.setTarget_id(param.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_appointment");
                                            pushMessageParam.setOpera_type("curriculum_appointment");
                                            pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的课程，点击查看");
                                            pushMessageParam.setType_id(param.getId());//获取课堂id
                                            pushMessageParam.setType("curriculum_appointment");
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }*/
                                        subscribe_count = mapper.queryListPage(userAppointmentEntity1);//插入之后重新查这个数据
                                        if (subscribe_count != null && subscribe_count.size() == masterAppointment.getConnect_peoplenum()) {//人满开始 扣费
                                            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                                            masterAppointmentEntity.setId(param.getAppointment_id());
                                            masterAppointmentEntity.setStatus(2);
                                            masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                                            for (UserAppointmentEntity appointmentEntity : subscribe_count) {
                                                //查询学习卡
                                                UserStudyCardEntity studyCardEntity = new UserStudyCardEntity();
                                                if (masterAppointment.getConnect_peoplenum() != null && masterAppointment.getConnect_peoplenum() > 1) {
                                                    studyCardEntity.setType(appointmentEntity.getMaster_type() + "_special");
                                                } else {
                                                    studyCardEntity.setType(appointmentEntity.getMaster_type());
                                                }
                                                studyCardEntity.setUser_id(appointmentEntity.getUser_id());
                                                studyCardEntity.setPageSize(1);
                                                studyCardEntity.setStatus(2);
                                                synchronized (userStudyCardEntity) {
                                                    ret = userStudyCardMapper.updateCourseNum(userStudyCardEntity);//减去学习卡剩余课时
                                                }
                                                if (ret > 0) {
                                                    appointmentEntity.setStatus(2);
                                                    mapper.updateByPrimaryKeySelective(appointmentEntity);
                                                } else {
                                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                                }
                                            }
                                            //推送
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            pushMessageParam.setTarget_id(param.getMaster_id());
                                            pushMessageParam.setPush_type("curriculum_appointment_full");
                                            pushMessageParam.setOpera_type("curriculum_appointment_full");
                                            pushMessageParam.setContent("您的课程人数已满，可准时开课");
                                            pushMessageParam.setType_id(masterAppointment.getId());//获取课堂id
                                            pushMessageParam.setType("curriculum_appointment_full");
                                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                                        }
                                    }
                                } else {
                                    return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                }
                            } else {
                                return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                            }
                        }
                    }
                }
            }
            return ResultUtil.success();
                /*
            }else {
                return ResultUtil.error(MasterEum.master_40021.getCode(), MasterEum.master_40021.getDesc());
            }

                 */
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.info("MasterAppointmentServiceImpl    insert");
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    public ResultParam insertMechanismUser(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        MechanismUserEntity mechanismUserEntity = new MechanismUserEntity();
        mechanismUserEntity.setUser_id(param.getUser_id());
        mechanismUserEntity.setMechanism_id(param.getMechanism_id());
        mechanismUserEntity.setType("course");
        return mechanismUserService.insert(mechanismUserEntity, request, response);
    }

    @Override
    public synchronized ResultParam updateUserConfirm(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(param.getId());
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setStatus(0);
            userAppointmentEntity.setAppointment_id(masterAppointmentEntity.getId());
            userAppointmentEntity.setPageSize(1);
            List<UserAppointmentEntity> list = mapper.queryListPage(userAppointmentEntity);
            if (list != null && list.size() > 0) {
                userAppointmentEntity = list.get(0);
            } else {
                return ResultUtil.error(OrderEum.order_70019.getCode(), OrderEum.order_70019.getDesc());
            }
            PushMessageParam pushMessageParam = new PushMessageParam();
            if (param.getWhether()) {
                userAppointmentEntity.setStatus(2);
                int i = mapper.updateByPrimaryKeySelective(userAppointmentEntity);
                masterAppointmentEntity.setStatus(1);
                int i1 = masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                pushMessageParam.setView_type("master");
                pushMessageParam.setPush_type("appointmeng_confirm_agree");
                pushMessageParam.setOpera_type("appointmeng_confirm_agree");
                pushMessageParam.setContent("您的课程邀请，学生已同意");
                pushMessageParam.setType("appointmeng_confirm_agree");
                pushMessageParam.setType_id(param.getId());//获取课堂id
                pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
            } else {
                int i1 = mapper.deleteByPrimaryKey(userAppointmentEntity.getId());
                int i = masterAppointmentMapper.deleteByPrimaryKey(userAppointmentEntity.getId());
                pushMessageParam.setView_type("master");
                pushMessageParam.setPush_type("appointmeng_confirm_disagree");
                pushMessageParam.setOpera_type("appointmeng_confirm_disagree");
                pushMessageParam.setContent("您的课程邀请，学生未同意");
                pushMessageParam.setType("appointmeng_confirm_disagree");
                pushMessageParam.setType_id(param.getId());//获取课堂id
                pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
            }
            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("addNewsMessages", pushMessageParam);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /*
    private BigDecimal queryPrice(UserAppointmentEntity param) {
        if (param.getActivity_id() != 0) {
            return businessActivityMapper.selectByPrimaryKey(param.getActivity_id()).getPrice();
        } else {
            return masterSetPriceMapper.selectByPrimaryKey(param.getMaster_set_price_id()).getOriginal_price();
        }
    }

     */

    /**
     * 修改/取消的回复
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam updateConfirm(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserAppointmentEntity userAppointmentEntity = mapper.selectByPrimaryKey(param.getId());
            if (param.getWhether() != null && param.getWhether()) {//if true 同意
                if (param.getUpdate_type() != null && "curriculumcancel".equalsIgnoreCase(param.getUpdate_type())) {
                    /**
                     * 取消后设置课程为可用
                     */
                    MasterAppointmentEntity masterAppointmentEntity111 = masterAppointmentMapper.selectByPrimaryKey(userAppointmentEntity.getAppointment_id());
                    masterAppointmentEntity111.setStatus(1);
                    masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity111);

                    param.setStatus(1);//同意设1
                    int ret = mapper.updateByPrimaryKeySelective(param);//更新状态
                    if (ret > 0) {
                        if (param.getMasterNotice_id() != null && param.getMasterNotice_id().intValue() != 0) {//记录id非空
                            MasterNoticeEntity masterNoticeEntity = masterNoticeService.findById(param.getMasterNotice_id(), request, response);//根据id查询记录
                            if (masterNoticeEntity != null) {
                                masterNoticeEntity.setStatus(2);//已读
                                masterNoticeEntity.setHandle(1);//同意
                                if ("user".equalsIgnoreCase(param.getUpdate_identity())) {//若user 设置需要拉取类型助学师
                                    masterNoticeEntity.setRead_type("masterread");
                                } else {
                                    masterNoticeEntity.setRead_type("userread");
                                }
                                masterNoticeService.update(masterNoticeEntity, request, response);//更新历史记录状态
                            }
                        }
                        /**
                         * 如果是小班课
                         */
                        Integer connect_peoplenum = masterAppointmentMapper.selectByPrimaryKey(userAppointmentEntity.getAppointment_id()).getConnect_peoplenum();
                        if (connect_peoplenum == 1) {
                            //非小班课取消,学习卡课时+1
                            UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                            userStudyCardEntity.setUser_id(param.getUser_id());//设置用户id
                            userStudyCardEntity.setType(param.getMaster_type());//设置助学师类型
                            userStudyCardEntity.setStatus(2);//可用
                            userStudyCardEntity.setPageSize(1);
                            List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.queryListPage(userStudyCardEntity);//查询是否存在
                            if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                                userStudyCardEntity = userStudyCardEntities.get(0);//获得学习卡对象
                                Integer count = userStudyCardEntity.getCourse_num();//获得学习卡课程数
                                userStudyCardEntity.setCourse_num(count + 1);//课程数+1
                                userStudyCardMapper.updateByPrimaryKeySelective(userStudyCardEntity);//更新
                            }
                        } else {
                            //小班课 user
                            if ("user".equals(param.getUpdate_identity())) {
                                userAppointmentEntity.setStatus(1);
                                mapper.updateByPrimaryKeySelective(userAppointmentEntity);//取消该节课
                            }
                        }
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        if ("user".equalsIgnoreCase(param.getUpdate_identity())) {//若user 设置助学师id
                            pushMessageParam.setTarget_id(param.getMaster_id());
                        } else {
                            pushMessageParam.setTarget_id(param.getUser_id());
                        }
                        pushMessageParam.setPush_type("curriculum_Cancel_Respond_agree");
                        pushMessageParam.setOpera_type("curriculum_Cancel_Respond_agree");
                        pushMessageParam.setContent("你的课程取消,对方已同意");
                        pushMessageParam.setType("curriculum_Cancel_Respond_agree");
                        pushMessageParam.setView_type(param.getUpdate_identity());//身份
                        pushMessageParam.setType_id(param.getId());//获取课堂id
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                } else if (param.getUpdate_type() != null && "curriculumupdate".equalsIgnoreCase(param.getUpdate_type())) {
                    /**
                     * 如果不是小班课
                     */
                    Integer connect_peoplenum = masterAppointmentMapper.selectByPrimaryKey(userAppointmentEntity.getAppointment_id()).getConnect_peoplenum();
                    if (connect_peoplenum == 1) {
                        //修改成功
                        MasterAppointmentEntity masterAppointment = masterAppointmentService.findById(userAppointmentEntity.getAppointment_id(), request, response);
                        if (masterAppointment != null) {
                            masterAppointment.setStatus(2);
                            masterAppointmentService.update(masterAppointment, request, response);
                        } else {
                            return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                        }
                        MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(param.getUpdate_appointment_id(), request, response);
                        if (masterAppointmentEntity != null) {
                            param.setStart_time(masterAppointmentEntity.getStart_time());
                            param.setEnd_time(masterAppointmentEntity.getEnd_time());
                            param.setOffset(masterAppointmentEntity.getOffset());
                            param.setTimezone_id(masterAppointmentEntity.getTimezone_id());
                            param.setAppointment_id(masterAppointmentEntity.getId());
                            param.setTitle(userAppointmentEntity.getUpdate_title());
                            param.setStatus(2);
                            int ret = mapper.updateByPrimaryKeySelective(param);
                            if (ret > 0) {
                                if (param.getMasterNotice_id() != null && param.getMasterNotice_id().intValue() != 0) {
                                    MasterNoticeEntity masterNoticeEntity = masterNoticeService.findById(param.getMasterNotice_id(), request, response);
                                    if (masterNoticeEntity != null) {
                                        masterNoticeEntity.setStatus(2);
                                        masterNoticeEntity.setHandle(1);
                                        if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                            masterNoticeEntity.setRead_type("masterread");
                                        } else {
                                            masterNoticeEntity.setRead_type("userread");
                                        }
                                        masterNoticeService.update(masterNoticeEntity, request, response);
                                    } else {
                                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                                    }
                                } else {
                                    return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                                }
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                    pushMessageParam.setTarget_id(param.getMaster_id());
                                } else {
                                    pushMessageParam.setTarget_id(param.getUser_id());
                                }
                                pushMessageParam.setPush_type("curriculum_Update_Respond_agree");
                                pushMessageParam.setOpera_type("curriculum_Update_Respond_agree");
                                pushMessageParam.setContent("你的课程修改,对方已同意");
                                pushMessageParam.setType("curriculum_Update_Respond_agree");
                                pushMessageParam.setView_type(param.getUpdate_identity());
                                pushMessageParam.setType_id(param.getId());//获取课堂id
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                            } else {
                                return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                            }
                        } else {
                            return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                        }
                    } else {
                        return ResultUtil.error(MasterEum.master_40015.getCode(), MasterEum.master_40015.getDesc());
                    }
                } else if (param.getUpdate_type() != null && "curriculumappointment".equalsIgnoreCase(param.getUpdate_type())) {
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                            pushMessageParam.setTarget_id(userAppointmentEntity.getMaster_id());
                        } else {
                            pushMessageParam.setTarget_id(userAppointmentEntity.getUser_id());
                        }
                        pushMessageParam.setPush_type("curriculum_insert_Respond_agree");
                        pushMessageParam.setOpera_type("curriculum_insert_Respond_agree");
                        pushMessageParam.setContent("你的课程预约,对方已同意，请点击查看");
                        pushMessageParam.setType("curriculum_insert_Respond_agree");
                        pushMessageParam.setView_type(userAppointmentEntity.getUpdate_identity());
                        pushMessageParam.setType_id(userAppointmentEntity.getId());//获取课堂id
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                }
            } else if (param.getWhether() != null && !param.getWhether()) { //不同意
                if (param.getUpdate_type() != null && ("curriculumappointment".equalsIgnoreCase(param.getUpdate_type())
                        || "curriculumcancel".equals(param.getUpdate_type()))) {
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        if (param.getMasterNotice_id() != null && param.getMasterNotice_id().intValue() != 0) { //从参数中获得记录id
                            MasterNoticeEntity masterNoticeEntity = masterNoticeService.findById(param.getMasterNotice_id(), request, response);//根据id查询记录
                            if (masterNoticeEntity != null) {//如果有
                                masterNoticeEntity.setStatus(2);//已处理
                                masterNoticeEntity.setHandle(2);//拒绝
                                if ("user".equalsIgnoreCase(param.getUpdate_identity())) {//如果身份是用户
                                    pushMessageParam.setTarget_id(param.getMaster_id());
                                    masterNoticeEntity.setRead_type("masterread");
                                } else {//身份是助学师
                                    pushMessageParam.setTarget_id(param.getUser_id());//如果身份是外教 设置用户id
                                    masterNoticeEntity.setRead_type("userread");//设置已读类型 需要用户拉取
                                }
                                masterNoticeService.update(masterNoticeEntity, request, response);//更新记录表信息
                            }
                        }
                        userAppointmentEntity.setStatus(2);
                        mapper.updateByPrimaryKeySelective(userAppointmentEntity);
                        pushMessageParam.setPush_type("curriculum_Cancel_Respond_disagree");
                        pushMessageParam.setOpera_type("curriculum_Cancel_Respond_disagree");
                        pushMessageParam.setContent("你的课程取消,对方已拒绝");
                        pushMessageParam.setType("curriculum_Cancel_Respond_disagree");
                        pushMessageParam.setView_type(param.getUpdate_identity());
                        pushMessageParam.setType_id(param.getId());//获取课堂id
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                } else if (param.getUpdate_type() != null && "curriculumupdate".equalsIgnoreCase(param.getUpdate_type())) {
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        if (param.getMasterNotice_id() != null && param.getMasterNotice_id().intValue() != 0) {
                            MasterNoticeEntity masterNoticeEntity = masterNoticeService.findById(param.getMasterNotice_id(), request, response);
                            if (masterNoticeEntity != null) {
                                masterNoticeEntity.setStatus(2);
                                masterNoticeEntity.setHandle(2);
                                if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                    masterNoticeEntity.setRead_type("masterread");
                                    pushMessageParam.setTarget_id(param.getMaster_id());
                                } else {
                                    masterNoticeEntity.setRead_type("userread");
                                    pushMessageParam.setTarget_id(param.getUser_id());
                                }
                                masterNoticeService.update(masterNoticeEntity, request, response);
                            }
                        }
                        userAppointmentEntity.setStatus(2);
                        mapper.updateByPrimaryKeySelective(userAppointmentEntity);
                        pushMessageParam.setPush_type("curriculum_Update_Respond_disagree");
                        pushMessageParam.setOpera_type("curriculum_Update_Respond_disagree");
                        pushMessageParam.setContent("你的课程修改,对方已拒绝");
                        pushMessageParam.setType("curriculum_Update_Respond_disagree");
                        pushMessageParam.setType_id(param.getId());//获取课堂id
                        pushMessageParam.setView_type(param.getUpdate_identity());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                    /*
                } else if (param.getUpdate_type() != null && "curriculum_appointment".equalsIgnoreCase(param.getUpdate_type())) {
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setTarget_id(param.getUser_id());
                        pushMessageParam.setPush_type("curriculumCancelRespond");
                        pushMessageParam.setOpera_type("curriculumCancelRespond");
                        pushMessageParam.setContent("你的课程预约,对方已处理，请点击查看");
                        pushMessageParam.setType("curriculumCancelRespond");
                        pushMessageParam.setView_type(param.getUpdate_identity());
                        pushMessageParam.setType_id(param.getId());//获取课堂id
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }

                     */
                }
            } else {
                return ResultUtil.error(ResultEnum.DATA_ERROR.getCode(), ResultEnum.DATA_ERROR.getDesc());
            }
        } catch (AmqpException e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl      updateConfirm");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam update(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getId() != null) {
                UserAppointmentEntity userAppointment = super.findById(param.getId(), request, response);//根据id查 用户预约记录
                if (userAppointment == null) {
                    return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                }
                if (param.getUpdate_type() != null && "cancel".equalsIgnoreCase(param.getUpdate_type())) {//取消
                    int ret = mapper.updateByPrimaryKeySelective(param);//更新取消
                    if (ret > 0) {
                        MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();//记录
                        masterNoticeEntity.setAppointment_id(param.getId());
                        masterNoticeEntity.setType("curriculumcancel");//curriculumcancel课程取消   curriculumupdate课程修改  curriculum_appointment课程预约
                        masterNoticeEntity.setUser_id(userAppointment.getUser_id());
                        masterNoticeEntity.setMaster_id(userAppointment.getMaster_id());
                        masterNoticeEntity.setMechanism_id(userAppointment.getMechanism_id());
                        masterNoticeEntity.setStatus(1);//待处理
                        masterNoticeEntity.setIdentity(param.getUpdate_identity());//设置身份
                        if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                            masterNoticeEntity.setRead_type("masterread");
                        } else {
                            masterNoticeEntity.setRead_type("userread");//需要user拉取
                        }
                        int result = noticeMapper.insertSelective(masterNoticeEntity);//新增一条记录
                        if (result > 0) {
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                pushMessageParam.setTarget_id(userAppointment.getMaster_id());//如果是user发起 设置外教id
                            } else {
                                pushMessageParam.setTarget_id(userAppointment.getUser_id());
                            }
                            pushMessageParam.setPush_type("appointment_Cancel_request");
                            pushMessageParam.setOpera_type("appointment_Cancel_request");
                            pushMessageParam.setContent("你的课程安排被申请取消,请查看");
                            pushMessageParam.setType("appointment_Cancel_request");
                            pushMessageParam.setView_type(param.getUpdate_identity());//发起人身份
                            pushMessageParam.setType_id(param.getId());//获取课堂id
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                        }
                    } else {
                        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                    }
                } else if (param.getUpdate_type() != null && "update".equalsIgnoreCase(param.getUpdate_type())) {
                    MasterAppointmentEntity masterAppointmentEntity = masterAppointmentService.findById(param.getUpdate_appointment_id(), request, response);
                    if (masterAppointmentEntity != null) {
                        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                        userAppointmentEntity.setUser_id(userAppointment.getUser_id());
                        userAppointmentEntity.setStart_time(masterAppointmentEntity.getStart_time());
                        userAppointmentEntity.setEnd_time(masterAppointmentEntity.getEnd_time());
                        userAppointmentEntity.setOffset(masterAppointmentEntity.getOffset());
                        List<UserAppointmentEntity> userAppointmentEntities = mapper.queryIsAppointmentClass(userAppointmentEntity);//查询某个时间段是否需要上课
                        if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                            return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                        }
                        param.setOffset(null);
                        int ret = mapper.updateByPrimaryKeySelective(param);//更新user预约记录
                        if (ret > 0) {
                            MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                            masterNoticeEntity.setAppointment_id(param.getId());
                            masterNoticeEntity.setType("curriculumupdate");//curriculumcancel课程取消   curriculumupdate课程修改  curriculum_appointment课程预约
                            masterNoticeEntity.setUser_id(userAppointment.getUser_id());
                            masterNoticeEntity.setMaster_id(userAppointment.getMaster_id());
                            masterNoticeEntity.setMechanism_id(userAppointment.getMechanism_id());
                            masterNoticeEntity.setStatus(1);
                            masterNoticeEntity.setIdentity(param.getUpdate_identity());
                            if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                masterNoticeEntity.setRead_type("masterread");
                            } else {
                                masterNoticeEntity.setRead_type("userread");
                            }
                            int result = noticeMapper.insertSelective(masterNoticeEntity);//插入一条记录
                            if (result > 0) {
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                if ("user".equalsIgnoreCase(param.getUpdate_identity())) {
                                    pushMessageParam.setTarget_id(userAppointment.getMaster_id());
                                } else {
                                    pushMessageParam.setTarget_id(userAppointment.getUser_id());
                                }
                                pushMessageParam.setPush_type("curriculum_update_request");
                                pushMessageParam.setOpera_type("curriculum_update_request");
                                pushMessageParam.setContent("您的课程安排被申请修改,请查看");
                                pushMessageParam.setType_id(param.getId());//获取课堂id
                                pushMessageParam.setType("curriculum_update_request");
                                pushMessageParam.setView_type(param.getUpdate_identity());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                            }
                        } else {
                            return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                        }
                    } else {
                        return ResultUtil.error(MasterEum.master_40007.getCode(), MasterEum.master_40007.getDesc());
                    }
                } else {
                    if (param.getStatus() != null && param.getStatus() == 3) {
                        PushMessageParam pushMessage = new PushMessageParam();
                        pushMessage.setType_id(param.getId());
                        pushMessage.setTarget_id(userAppointment.getUser_id());
                        pushMessage.setPush_type("sign_in");
                        pushMessage.setOpera_type("sign_in");
                        pushMessage.setContent("你已确认到达签到地点");
                        pushMessage.setType("sign_in");
                        pushMessage.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessage);
                    }
                    return super.update(param, request, response);
                }
            } else {
                return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterAppointmentServiceImpl    update");
        }
        return ResultUtil.success();
    }

    @Override
    public List<UserAppointmentEntity> querySummaryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity entity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
                    masterSummaryEntity.setAppointment_id(entity.getId());
                    masterSummaryEntity.setPageSize(1);
                    List<MasterSummaryEntity> masterSummaryEntities = masterSummaryMapper.queryListPage(masterSummaryEntity);
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map.put("summaryinfo", masterSummaryEntities);
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl     querySummaryListPage");
        }
        return userAppointmentEntities;
    }

    @Override
    public List<UserAppointmentEntity> queryTeacherListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryTeacherListPage(param);
            for (UserAppointmentEntity entity : userAppointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                if ("private_education".equalsIgnoreCase(entity.getMaster_type())) {
                    UserStudyCardEntity userStudyCardEntity = new UserStudyCardEntity();
                    userStudyCardEntity.setMaster_id(entity.getMaster_id());
                    userStudyCardEntity.setUser_id(param.getUser_id());
                    userStudyCardEntity.setPageSize(1);
                    List<UserStudyCardEntity> userStudyCardEntities = userStudyCardMapper.query(userStudyCardEntity);
                    if (userStudyCardEntities != null && userStudyCardEntities.size() > 0) {
                        /*map.put("userstudycount",userStudyCardEntities.get(0).getCourse_num());
                        UserAppointmentEntity userAppointmentEntity=new UserAppointmentEntity();
                        userAppointmentEntity.setUser_id(param.getUser_id());
                        userAppointmentEntity.setMaster_id(entity.getMaster_id());
                        Integer count=mapper.queryListPageCount(userAppointmentEntity);
                        map.put("monthcourse",count+userStudyCardEntities.get(0).getCourse_num());*/
                    }
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setUser_id(param.getUser_id());
                    userAppointmentEntity.setMaster_id(entity.getMaster_id());
                    userAppointmentEntity.setMaster_type(entity.getMaster_type());
                    Integer totalCourse = mapper.queryListPageCount(userAppointmentEntity);
                    map.put("totalCourse", totalCourse);
                    userAppointmentEntity.setStatus(2);
                    Integer notCount = mapper.queryListPageCount(userAppointmentEntity);
                    map.put("notCount", notCount);
                } else {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                    userAppointmentEntity.setUser_id(param.getUser_id());
                    userAppointmentEntity.setMaster_id(entity.getMaster_id());
                    userAppointmentEntity.setMaster_type(entity.getMaster_type());
                    userAppointmentEntity.setStatus(2);
                    userAppointmentEntity.setPageSize(1);
                    List<UserAppointmentEntity> userAppointmentEntities1 = mapper.queryLatelyCourse(userAppointmentEntity);
                    map.put("latelyclass", userAppointmentEntities1);
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl     queryTeacherListPage");
        }
        return userAppointmentEntities;
    }

    @Override
    public List<UserAppointmentEntity> queryStudentListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryStudentListPage(param);
            for (UserAppointmentEntity entity : userAppointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setUser_id(entity.getUser_id());
                userAppointmentEntity.setMaster_id(param.getMaster_id());
                Integer totalCourse = mapper.queryListPageCount(userAppointmentEntity);
                map.put("totalCourse", totalCourse);
                userAppointmentEntity.setStatus(2);
                Integer notCount = mapper.queryListPageCount(userAppointmentEntity);
                map.put("notCount", notCount);
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl     queryStudentListPage");
        }
        return userAppointmentEntities;
    }

    @Override
    public List<UserAppointmentEntity> queryUserSchedule(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryUserSchedule(param);//查询结束时间大于现在 && 状态!=7 && 状态!=1
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity entity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));//设置userInfo
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());//新建一个masterInfo对象设置masterId
                    //助学师类型  major 专业,cross_border 跨境,mother_tongue 母语,private_education  私教
                    masterInfoEntity.setType(param.getMaster_type());//服务类型参数给的类型
                    masterInfoEntity.setPageSize(1);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {//若学生预约课程id非空 课节id
                        masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                    }
                    map.put("masterAppointmentEntity", masterAppointmentEntity);//根据id查询课程
                    TimeZoneEntity timeZoneEntity = new TimeZoneEntity();
                    timeZoneEntity.setId(entity.getTimezone_id());
                    timeZoneEntity.setPageSize(1);
                    List<TimeZoneEntity> timeZoneEntities = timeZoneMapper.queryListPage(timeZoneEntity);
                    if (timeZoneEntities != null && timeZoneEntities.size() > 0) {
                        map.put("timezone", timeZoneEntities.get(0));//时区对象
                    }
                    if (entity.getMaster_id() != null && entity.getMaster_id() != 0) {
                        map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));//设置masterInfo
                        map.put("masterInfoEntities", masterInfoEntities);//设置助学师信息对象
                    } else {
                        map.put("masterinfo", null);
                        map.put("masterInfoEntities", null);
                    }
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl   queryUserSchedule");
        }
        return userAppointmentEntities;
    }

    @Override
    public List<UserAppointmentEntity> queryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity userAppointmentEntity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    UserIdentityEntity userIdentityEntity = new UserIdentityEntity();
                    userIdentityEntity.setUser_id(userAppointmentEntity.getMaster_id());
                    userIdentityEntity.setStatus(1);
                    userIdentityEntity.setPageSize(1);
                    List<UserIdentityEntity> identityEntityList = userIdentityService.queryListPage(userIdentityEntity, request, response);
                    if (identityEntityList != null && identityEntityList.size() > 0) {
                        map.put("identity_name", identityEntityList.get(0).getUser_name() + "老师");
                    } else {
                        map.put("identity_name", "老师");
                    }
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getUser_id() + "userinfo")));
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getMaster_id() + "userinfo")));
                    map.put("masterAppointMentEntity", masterAppointmentMapper.selectByPrimaryKey(userAppointmentEntity.getAppointment_id()));
                    userAppointmentEntity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl   queryListPage");
        }
        return userAppointmentEntities;
    }

    //上课前通知
    @Override
    public void querySoonClass() {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            UserAppointmentEntity appointmentEntity = new UserAppointmentEntity();
            userAppointmentEntities = mapper.querySoonClass(appointmentEntity);
            for (UserAppointmentEntity entity : userAppointmentEntities) {
                //学生推送
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setType_id(entity.getId());
                pushMessageParam.setTarget_id(entity.getUser_id());
                pushMessageParam.setPush_type("student_single_class_notice");
                pushMessageParam.setOpera_type("student_single_class_notice");
                pushMessageParam.setContent(new SimpleDateFormat(
                        FinalConfigParam.TIME_FORMAT_SYSTLE).format(entity.getStart_time()) + ":" + entity.getTitle());
                pushMessageParam.setType("student_single_class_notice");
                pushMessageParam.setView_type("user");
                pushMessageParam.setType_id(entity.getAppointment_id());//获取课堂id
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                //老师推送
                PushMessageParam pushMessage = new PushMessageParam();
                pushMessage.setType_id(entity.getId());
                pushMessage.setTarget_id(entity.getMaster_id());
                pushMessage.setPush_type("teacher_single_class_notice  ");
                pushMessage.setOpera_type("teacher_single_class_notice  ");
                pushMessage.setContent(entity.getTitle());
                pushMessage.setType("teacher_single_class_notice");
                pushMessage.setView_type("master");
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl     querySoonClass");
        }
    }

    /**
     * 我的收益
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        Map<String, Object> map1 = new HashMap<>();
        try {
            userAppointmentEntities = mapper.queryEarningsListPage(param);//查询课程表
            for (UserAppointmentEntity userAppointmentEntity : userAppointmentEntities) {//用户上课记录
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getMaster_id() + "userinfo")));
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(userAppointmentEntity.getMaster_id());//外教id
                masterInfoEntity.setType(param.getMaster_type());//外教类型
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                map.put("masterInfoEntities", masterInfoEntities);
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (userAppointmentEntity.getAppointment_id() != null && userAppointmentEntity.getAppointment_id() != 0) {//若学生预约课程id非空
                    masterAppointmentEntity = masterAppointmentService.findById(userAppointmentEntity.getAppointment_id(), request, response);
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);//根据id查询课程
                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();//评分对象
                masterCommentEntity.setUser_id(userAppointmentEntity.getUser_id());//设置学生id
                masterCommentEntity.setUser_appointment_id(userAppointmentEntity.getId());//设置课节id
                masterCommentEntity.setParent_id(0L);//父id
                List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);//查询评分表
                map.put("masterCommentEntities", masterCommentEntities);
                userAppointmentEntity.setMap(map);

            }
            map1.put("userAppointmentEntities", userAppointmentEntities);
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//外教课程对象
            userAppointmentEntity.setMaster_id(param.getMaster_id());//设置外教id
            userAppointmentEntity.setEarnings_status(2);//设置到账状态2
            Double cumulativeEarnings = mapper.queryCumulativeEarnings(userAppointmentEntity);//累计
            map1.put("cumulativeEarnings", cumulativeEarnings);
            userAppointmentEntity.setStart_time(param.getStart_time());
            userAppointmentEntity.setEnd_time(param.getEnd_time());
            Double cumulativeMontlyEarnings = mapper.queryCumulativeEarnings(userAppointmentEntity);//本月累计
            map1.put("cumulativeMontlyEarnings", cumulativeMontlyEarnings);
            userAppointmentEntity.setEarnings_status(1);
            Integer not_Account = mapper.queryNotEarnings(userAppointmentEntity);//未到账
            map1.put("not_Account", not_Account);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl   queryEarningsListPage");
        }
        return ResultUtil.success(map1);
    }

    @Override
    public Map<String, Object> queryEarningsStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            ChartParam chartParam = mapper.queryEarningsStatistics(param);
            map = new HashMap<>();
            map.put("statisticsParam", chartParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<AppointmentParam> queryAppointmentTimeStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<AppointmentParam> appointmentParamList = null;
        try {
            appointmentParamList = mapper.queryAppointmentTimeStatistics(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointmentParamList;
    }

    @Override
    public List<UserAppointmentEntity> queryHistoryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {

            userAppointmentEntities = mapper.queryHistoryListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity entity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                        masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                    }
                    MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                    masterCommentEntity.setUser_id(entity.getUser_id());
                    masterCommentEntity.setUser_appointment_id(entity.getId());
                    masterCommentEntity.setParent_id(0L);
                    List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                    map.put("masterCommentEntities", masterCommentEntities);
                    map.put("masterAppointmentEntity", masterAppointmentEntity);
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setType(param.getMaster_type());
                    masterInfoEntity.setPageSize(1);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    map.put("masterInfoEntities", masterInfoEntities);
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAppointmentEntities;
    }

    /**
     * 线下课程签到
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam offlineAppointmentSign(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setPageSize(1);
            List<UserAppointmentEntity> userAppointmentEntities = mapper.queryListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                param.setStatus(9);
                int ret = mapper.updateByPrimaryKeySelective(param);
                if (ret > 0) {
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(userAppointmentEntities.get(0).getUser_id());
                    pushMessageParam.setPush_type("offline_course_sign");
                    pushMessageParam.setOpera_type("offline_course_sign");
                    pushMessageParam.setContent("您的课程已开始，请点击查看");
                    pushMessageParam.setType("offline_course_sign");
                    pushMessageParam.setView_type(param.getUpdate_identity());
                    pushMessageParam.setType_id(param.getId());//获取课堂id
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                } else {
                    return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                }
            } else {
                return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    /**
     * 线下课程结束
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam offlineAppointmentEnd(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setPageSize(1);
            List<UserAppointmentEntity> userAppointmentEntities = mapper.queryListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                param.setStatus(3);
                param.setEarnings_status(2);
                int ret = mapper.updateByPrimaryKeySelective(param);
                if (ret > 0) {
                    PushMessageParam pushMessageParam = new PushMessageParam();
                    pushMessageParam.setTarget_id(userAppointmentEntities.get(0).getUser_id());
                    pushMessageParam.setPush_type("offLine_course_end");
                    pushMessageParam.setOpera_type("offLine_course_end");
                    pushMessageParam.setType_id(param.getId());//获取课堂id
                    if ("private_education".equalsIgnoreCase(userAppointmentEntities.get(0).getMaster_type())) {
                        pushMessageParam.setContent("您的课程已结束，请点击查看");
                    } else {
                        pushMessageParam.setOpera_type("offLine_cancel_Course");
                        pushMessageParam.setPush_type("offLine_cancel_Course");
                        pushMessageParam.setContent("您的课程核销已完成，请点击查看");
                    }
                    pushMessageParam.setType("offLine_cancel_Course");
                    pushMessageParam.setView_type(param.getUpdate_identity());
                    pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                    rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                } else {
                    return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
                }
            } else {
                return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public List<UserAppointmentEntity> queryMyOrderListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {
            userAppointmentEntities = mapper.queryMyOrderListPage(param);
            for (UserAppointmentEntity entity : userAppointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setUser_id(entity.getUser_id());
                masterCommentEntity.setUser_appointment_id(entity.getId());
                masterCommentEntity.setParent_id(0L);
                List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                map.put("masterCommentEntities", masterCommentEntities);
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAppointmentEntities;
    }

    @Override
    public Map<String, Object> queryTodayListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            //专业助学师课程
            param.setMaster_type("major");
            List<UserAppointmentEntity> major_info = mapper.queryUserSchedule(param);
            for (UserAppointmentEntity entity : major_info) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
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
            objectMap.put("major_info", major_info);
            //跨境助学师课程
            param.setMaster_type("cross_border");
            List<UserAppointmentEntity> cross_border_info = mapper.queryUserSchedule(param);
            for (UserAppointmentEntity entity : cross_border_info) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
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
            objectMap.put("cross_border_info", cross_border_info);
            //母语助学师课程
            param.setMaster_type("mother_tongue");
            List<UserAppointmentEntity> mother_tongue_info = mapper.queryUserSchedule(param);
            for (UserAppointmentEntity entity : mother_tongue_info) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
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
            objectMap.put("mother_tongue_info", mother_tongue_info);
            //私教助学师课程
            param.setMaster_type("private_education");
            List<UserAppointmentEntity> private_education_info = mapper.queryUserSchedule(param);
            for (UserAppointmentEntity entity : private_education_info) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
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
            objectMap.put("private_education_info", private_education_info);
            //直播讲师课程
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setType("open_class");
            masterAppointmentEntity.setStart_time(param.getStart_time());
            masterAppointmentEntity.setEnd_time(param.getEnd_time());
            masterAppointmentEntity.setOffset(param.getOffset());
            masterAppointmentEntity.setMaster_id(param.getMaster_id());
            masterAppointmentEntity.setPageSize(param.getPageSize());
            masterAppointmentEntity.setMechanism_id(param.getMechanism_id());
            List<MasterAppointmentEntity> appointmentEntities = masterAppointmentMapper.querySchedule(masterAppointmentEntity);
            for (MasterAppointmentEntity entity : appointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                String group_info = messageApiService.queryGroupInfo(entity.getGroup_id());
                map.put("groupinfo", JSONObject.parseObject(group_info));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType("live_lecturer");
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
            objectMap.put("live_appointment", appointmentEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    /**
     * 助学师收益
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryMasterEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        Map<String, Object> map1 = new HashMap<>();
        try {
            userAppointmentEntities = mapper.queryMasterEarningsListPage(param);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity userAppointmentEntity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getUser_id() + "userinfo")));
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getMaster_id() + "userinfo")));
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(userAppointmentEntity.getMaster_id());
                    masterInfoEntity.setType(param.getMaster_type());
                    masterInfoEntity.setPageSize(1);
                    List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                    map.put("masterInfoEntities", masterInfoEntities);
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    if (userAppointmentEntity.getAppointment_id() != null && userAppointmentEntity.getAppointment_id() != 0) {
                        masterAppointmentEntity = masterAppointmentService.findById(userAppointmentEntity.getAppointment_id(), request, response);
                    }
                    map.put("masterAppointmentEntity", masterAppointmentEntity);
                    userAppointmentEntity.setMap(map);
                }
            }
            map1.put("userAppointmentEntities", userAppointmentEntities);
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setMaster_id(param.getMaster_id());
            userAppointmentEntity.setEarnings_status(2);
            Double cumulativeEarnings = mapper.queryCumulativeEarnings(userAppointmentEntity);//累计
            map1.put("cumulativeEarnings", cumulativeEarnings);
            userAppointmentEntity.setStart_time(param.getStart_time());
            userAppointmentEntity.setEnd_time(param.getEnd_time());
            Double cumulativeMontlyEarnings = mapper.queryCumulativeEarnings(userAppointmentEntity);//本月累计
            map1.put("cumulativeMontlyEarnings", cumulativeMontlyEarnings);
            userAppointmentEntity.setEarnings_status(1);
            Integer not_Account = mapper.queryNotEarnings(userAppointmentEntity);//未到账
            map1.put("not_Account", not_Account);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl   queryEarningsListPage");
        }
        return ResultUtil.success(map1);
    }

    @Override
    public List<UserAppointmentEntity> queryMasterHistoryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> userAppointmentEntities = null;
        try {

            userAppointmentEntities = mapper.queryMasterHistoryListPage(param);
            for (UserAppointmentEntity entity : userAppointmentEntities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                if (entity.getAppointment_id() != null && entity.getAppointment_id() != 0) {
                    masterAppointmentEntity = masterAppointmentService.findById(entity.getAppointment_id(), request, response);
                }
                MasterCommentEntity masterCommentEntity = new MasterCommentEntity();
                masterCommentEntity.setUser_id(entity.getUser_id());
                masterCommentEntity.setUser_appointment_id(entity.getId());
                masterCommentEntity.setParent_id(0L);
                List<MasterCommentEntity> masterCommentEntities = masterCommentService.queryListPage(masterCommentEntity, request, response);
                map.put("masterCommentEntities", masterCommentEntities);
                map.put("masterAppointmentEntity", masterAppointmentEntity);
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setUser_id(entity.getMaster_id());
                masterInfoEntity.setType(param.getMaster_type());
                masterInfoEntity.setPageSize(1);
                List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                map.put("masterInfoEntities", masterInfoEntities);
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userAppointmentEntities;
    }


    @Override
    public Map<String, Object> queryByMessage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
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
            Integer total = mapper.queryByMessageCount(param);
            List<UserAppointmentEntity> list = mapper.queryByMessage(param);
            if (total > 0) {
                for (UserAppointmentEntity entity : list) {
                    HashMap<String, Object> map1 = new HashMap<>();
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setType(entity.getMaster_type());
                    masterInfoEntity.setPageSize(1);
                    masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                    map1.put("masterInfoEntity", masterInfoEntity);
                    map1.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map1.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    entity.setMap(map1);
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
    public ResultParam insertSpecialCourse(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            //查询商品信息
            MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(param.getAppointment_id());

            //查询时间段内作为助学师是否有课程
            MasterAppointmentEntity masterAppointment1 = new MasterAppointmentEntity();
            masterAppointment1.setMaster_id(param.getUser_id());
            masterAppointment1.setStart_time(param.getStart_time());
            masterAppointment1.setEnd_time(param.getEnd_time());
            masterAppointment1.setOffset(param.getOffset());
            masterAppointment1.setPageSize(1);

            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointment1);
            if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {//是否有课
                return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
            }

            //查询预约课程是否冲突
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setStart_time(param.getStart_time());
            userAppointmentEntity.setEnd_time(param.getEnd_time());
            userAppointmentEntity.setOffset(param.getOffset());
            userAppointmentEntity.setTimezone_id(param.getTimezone_id());
            userAppointmentEntity.setUser_id(param.getUser_id());
            List<UserAppointmentEntity> userAppointmentEntities = mapper.queryIsAppointmentClass(userAppointmentEntity);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
            }


            //查询是否是多人课
            masterAppointment1.setTitle(param.getTitle());
            masterAppointmentEntities = masterAppointmentMapper.querySchedule(masterAppointment1);
            if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                masterAppointmentEntity = masterAppointmentEntities.get(0);
                if ((masterAppointmentEntity.getConnect_peoplenum() == 1) || (!masterAppointmentEntity.getTitle().equals(param.getTitle()) && param.getMechanism_id() == 0)) {
                    return ResultUtil.error(MasterEum.master_40003.getCode(), MasterEum.master_40003.getDesc());
                }
            } else {
                masterAppointmentEntity.setMechanism_id(param.getMechanism_id());
                masterAppointmentEntity.setMaster_id(param.getMaster_id());
                masterAppointmentEntity.setStart_time(param.getStart_time());
                masterAppointmentEntity.setEnd_time(param.getEnd_time());
                masterAppointmentEntity.setOffset(param.getOffset());
                if (masterSetPriceEntity.getConnect_peoplenum() == 1) {
                    masterAppointmentEntity.setStatus(2);
                } else {
                    masterAppointmentEntity.setStatus(1);
                }
                masterAppointmentEntity.setType("single_class");
                masterAppointmentEntity.setConnect_peoplenum(masterSetPriceEntity.getConnect_peoplenum());
                masterAppointmentEntity.setTitle(param.getTitle());
                masterAppointmentEntity.setCover(masterSetPriceEntity.getFace_url());
                masterAppointmentEntity.setLanguage(param.getLanguage());
                masterAppointmentEntity.setMaster_set_price_id(param.getAppointment_id());
                masterAppointmentEntity.setIntroduction_content(masterSetPriceEntity.getIntroduction_content());
                masterAppointmentEntity.setIs_special(true);
                String s = System.currentTimeMillis() + "";
                String roomId = getRoomId();
                MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                masterAppointmentEntity1.setRoom_id(roomId);
                Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity1);
                while (integer > 0) {
                    roomId = getRoomId();
                    masterAppointmentEntity1.setRoom_id(roomId);
                    integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity1);
                }
                masterAppointmentEntity.setRoom_id(roomId);
                masterAppointmentMapper.insertSelective(masterAppointmentEntity);

            }
            userAppointmentEntity.setPageSize(1);

            //查询时间是否冲突
            List<UserAppointmentEntity> userAppointments = mapper.queryIsAppointmentClass(userAppointmentEntity);

            UserAppointmentEntity userAppointmentEntity1 = new UserAppointmentEntity();
            Integer userAppointmentCount = 0;
            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(param.getId());//查询学习卡
            if (userStudyCardEntity != null && userStudyCardEntity.getCourse_num() > 0) {
                userAppointmentEntity1.setMechanism_id(param.getMechanism_id());
                userAppointmentEntity1.setMaster_id(param.getMaster_id());
                userAppointmentEntity1.setMaster_type(userStudyCardEntity.getType());
                userAppointmentEntity1.setIs_exclusive(true);
            } else {
                return ResultUtil.error(MasterEum.master_40018.getCode(), MasterEum.master_40018.getDesc());
            }
            userAppointmentCount = mapper.queryBeginCount(userAppointmentEntity1);//查询预约人数
            userAppointmentEntity1.setUser_id(param.getUser_id());
            userAppointmentEntity1.setStart_time(param.getStart_time());
            userAppointmentEntity1.setEnd_time(param.getEnd_time());
            userAppointmentEntity1.setOffset(param.getOffset());

            userAppointmentCount += 1;

            //是否人数超过限制
            if (userAppointments != null && userAppointments.size() > 0 && userAppointmentCount > masterSetPriceEntity.getConnect_peoplenum()) {
                masterAppointmentEntity.setStatus(2);
                masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                if (param.getMechanism_id() == 0) {
                    return ResultUtil.error(MasterEum.master_40013.getCode(), MasterEum.master_40013.getDesc());
                } else {
                    MasterAppointmentEntity masterAppointmentEntity1 = new MasterAppointmentEntity();
                    masterAppointmentEntity1.setMechanism_id(param.getMechanism_id());
                    masterAppointmentEntity1.setMaster_id(param.getMaster_id());
                    masterAppointmentEntity1.setStatus(1);
                    if ("mechanism_offline".equals(param.getMaster_type())) {
                        masterAppointmentEntity1.setType("mechanism_offline");
                    } else {
                        masterAppointmentEntity1.setType("single_class");
                    }
                    masterAppointmentEntity1.setConnect_peoplenum(masterSetPriceEntity.getConnect_peoplenum());
                    masterAppointmentEntity1.setTitle(param.getTitle());
                    masterAppointmentEntity1.setCover(masterSetPriceEntity.getFace_url());
                    masterAppointmentEntity1.setLanguage(param.getLanguage());
                    masterAppointmentEntity1.setMaster_set_price_id(param.getAppointment_id());
                    masterAppointmentEntity1.setIs_special(true);

                    String roomId = getRoomId();
                    MasterAppointmentEntity masterAppointmentEntity2 = new MasterAppointmentEntity();
                    masterAppointmentEntity2.setRoom_id(roomId);
                    Integer integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity2);
                    while (integer > 0) {
                        roomId = getRoomId();
                        masterAppointmentEntity1.setRoom_id(roomId);
                        integer = masterAppointmentMapper.queryListPageCount(masterAppointmentEntity2);
                    }
                    masterAppointmentEntity1.setRoom_id(roomId);
                    masterAppointmentMapper.insertSelective(masterAppointmentEntity1);
                    masterAppointmentEntity = masterAppointmentEntity1;
                    masterAppointmentEntity.setIntroduction_content(masterSetPriceEntity.getIntroduction_content());
                }
            }
            userAppointmentEntity1.setStudy_card_id(param.getId());
            userAppointmentEntity1.setAppointment_id(masterAppointmentEntity.getId());
            userAppointmentEntity1.setTitle(param.getTitle());
            userAppointmentEntity1.setStatus(param.getStatus());
            //新增信息
            mapper.insertSelective(userAppointmentEntity1);

            //推送
            PushMessageParam pushMessageParam = new PushMessageParam();
            UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
            if (param.getMaster_id() != 0) {
                pushMessageParam.setTarget_id(param.getMaster_id());
            } else {
                pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id()).getUser_id());
            }
            pushMessageParam.setPush_type("curriculum_appointment");
            pushMessageParam.setOpera_type("curriculum_appointment");
            pushMessageParam.setContent(infoEntity.getNick_name() + "预约了你的专属课程，点击查看");
            pushMessageParam.setType_id(masterAppointmentEntity.getId());//获取课堂id
            pushMessageParam.setType("curriculum_appointment");
            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);

            //助学师课程状态
            int ret = 0;
            synchronized (userStudyCardEntity) {
                ret = userStudyCardMapper.updateCourseNum(userStudyCardEntity);//减去学习卡剩余课时
            }
            if (ret > 0) {
                userAppointmentEntity.setStatus(2);
                mapper.updateByPrimaryKeySelective(userAppointmentEntity);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            if (userAppointmentCount.equals(masterSetPriceEntity.getConnect_peoplenum())) {

                //推送
                PushMessageParam pushMessageParam1 = new PushMessageParam();
                if (param.getMaster_id() != 0) {
                    pushMessageParam.setTarget_id(param.getMaster_id());
                } else {
                    pushMessageParam.setTarget_id(masterMechanismMapper.selectByPrimaryKey(param.getMechanism_id()).getUser_id());
                }
                pushMessageParam1.setPush_type("curriculum_appointment");
                pushMessageParam1.setOpera_type("curriculum_appointment");
                pushMessageParam1.setContent("您的专属课程人数已满，可准时开课");
                pushMessageParam1.setType_id(masterAppointmentEntity.getId());//获取课堂id
                pushMessageParam1.setType("curriculum_appointment");
                pushMessageParam1.setIs_teach_paypal(param.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam1);
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public Map<String, Object> queryCourseCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount", dayCount);

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount", weekCount);

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount", monthCount);

            Integer total = mapper.queryListPageCount(param);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam deleteAppointment(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(param.getAppointment_id());

            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setAppointment_id(param.getAppointment_id());
            userAppointmentEntity.setPageSize(masterAppointmentEntity.getConnect_peoplenum());
            List<UserAppointmentEntity> list = mapper.queryListPage(userAppointmentEntity);
            if (list != null && list.size() == masterAppointmentEntity.getConnect_peoplenum()) {
                return ResultUtil.error(MasterEum.master_40019.getCode(), MasterEum.master_40019.getDesc());
            }
            mapper.deleteByPrimaryKey(param.getId());
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(MasterEum.master_40011.getCode(), MasterEum.master_40011.getDesc());
    }

    @Override
    public Map<String, Object> querySummaryListPagePC(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map1 = Maps.newHashMap();
        try {
            List<UserAppointmentEntity> userAppointmentEntities = mapper.queryListPage(param);
            Integer total = mapper.queryListPageCount(param);
            if (total > 0) {
                for (UserAppointmentEntity entity : userAppointmentEntities) {
                    Map<String, Object> map = new HashMap<>();
                    MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
                    masterSummaryEntity.setAppointment_id(entity.getId());
                    masterSummaryEntity.setPageSize(1);
                    List<MasterSummaryEntity> masterSummaryEntities = masterSummaryMapper.queryListPage(masterSummaryEntity);
                    map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    map.put("summaryinfo", masterSummaryEntities);
                    entity.setMap(map);
                }
            }
            map1.put("rows", userAppointmentEntities);
            map1.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserAppointmentServiceImpl     querySummaryListPage");
        }
        return map1;


    }

    @Override
    public Map<String, Object> queryUserScheduleListCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserAppointmentEntity> list = queryUserSchedule(param, request, response);
            Integer total = mapper.queryUserScheduleCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryHistoryListPageCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserAppointmentEntity> list = queryHistoryListPage(param, request, response);
            Integer total = mapper.queryHistoryListPageCount(param);
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void updateStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response) {
        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
        userAppointmentEntity.setUser_id(rechargeRecordEntity.getUser_id());
        userAppointmentEntity.setAppointment_id(rechargeRecordEntity.getAppointment_id());
        userAppointmentEntity.setPageSize(1);
        userAppointmentEntity = mapper.queryListPage(userAppointmentEntity).get(0);
        if (userAppointmentEntity.getStatus() == 1) {

            PushMessageParam pushMessageParamMaster = new PushMessageParam();
            UserInfoEntity infoEntity = userInfoService.findById(rechargeRecordEntity.getUser_id(), request, response);
            pushMessageParamMaster.setTarget_id(rechargeRecordEntity.getMaster_id());
            pushMessageParamMaster.setView_type("master");
            pushMessageParamMaster.setPush_type("curriculum_appointment");
            pushMessageParamMaster.setOpera_type("curriculum_appointment");
            pushMessageParamMaster.setContent((infoEntity.getNick_name() + "购买了您的线下课程，点击前往处理").trim());
            pushMessageParamMaster.setType("curriculum_appointment");
            pushMessageParamMaster.setType_id(userAppointmentEntity.getId());
            pushMessageParamMaster.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMaster);

            PushMessageParam pushMessageParamUser = new PushMessageParam();
            pushMessageParamUser.setTarget_id(rechargeRecordEntity.getUser_id());
            pushMessageParamUser.setView_type("user");
            pushMessageParamUser.setPush_type("curriculum_appointment");
            pushMessageParamUser.setOpera_type("curriculum_appointment");
            pushMessageParamUser.setContent(("您的课程预约成功,请按时上课").trim());
            pushMessageParamUser.setType("curriculum_appointment");
            pushMessageParamUser.setType_id(userAppointmentEntity.getAppointment_id());
            pushMessageParamUser.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamUser);

            PushMessageParam pushMessageParamMechanism = new PushMessageParam();
            MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
            pushMessageParamMechanism.setTarget_id(mechanismEntity.getUser_id());
            pushMessageParamMechanism.setView_type("mechanism");
            pushMessageParamMechanism.setPush_type("curriculum_appointment");
            pushMessageParamMechanism.setOpera_type("curriculum_appointment");
            pushMessageParamMechanism.setContent((infoEntity.getNick_name() + "预约了您的机构线下课程，点击前往处理").trim());
            pushMessageParamMechanism.setType("curriculum_appointment");
            pushMessageParamMechanism.setType_id(userAppointmentEntity.getId());
            pushMessageParamUser.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamMechanism);

            userAppointmentEntity.setStatus(2);

        } else if (userAppointmentEntity.getStatus() == 8) {

            PushMessageParam pushMessageParamUser = new PushMessageParam();
            pushMessageParamUser.setTarget_id(rechargeRecordEntity.getUser_id());
            pushMessageParamUser.setPush_type("pay");
            pushMessageParamUser.setOpera_type("pay");
            pushMessageParamUser.setContent(("支付成功").trim());
            pushMessageParamUser.setType("pay");
            pushMessageParamUser.setType_id(rechargeRecordEntity.getId());
            pushMessageParamUser.setIs_teach_paypal(rechargeRecordEntity.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamUser);

            /*

            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(rechargeRecordEntity.getStudycard_id());
            UserInfoEntity userInfoEntity;
            MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(rechargeRecordEntity.getMechanism_id());
            if (userStudyCardEntity.getBinding_mechanism_id() == 0) {
                userInfoEntity = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(rechargeRecordEntity.getAmount()
                        .multiply(new BigDecimal("0.85"))));
            } else {
                userInfoEntity = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                userInfoEntity.setCash(userInfoEntity.getCash().add(rechargeRecordEntity.getAmount()
                        .multiply(new BigDecimal("0.95"))));
            }

            synchronized (this) {
                userInfoMapper.updateCash(userInfoEntity);
            }
                         */

            userAppointmentEntity.setStatus(9);
        }
        mapper.updateByPrimaryKeySelective(userAppointmentEntity);
    }

    @Override
    public List<UserAppointmentEntity> queryOfflineSchedule(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserAppointmentEntity> list = Lists.newArrayList();
        try {
            if (param.getStatus() != null && param.getStatus() == 2) {
                list = mapper.queryOfflineSchedule(param);
            } else {
                list = mapper.queryEndCourse(param);
            }
            if (list != null && list.size() > 0) {
                setinfo(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam updateCancelCourse(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserAppointmentEntity userAppointmentEntity = mapper.selectByPrimaryKey(param.getId());
            if (userAppointmentEntity.getStatus() == 2) {
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setUser_id(userAppointmentEntity.getUser_id());
                rechargeRecordEntity.setAppointment_id(userAppointmentEntity.getMaster_id());
                ResultParam resultParam = payService.fundAuthOrderUnFreeze(rechargeRecordEntity, request, response);
                if (resultParam.getCode() == 0) {
                    return super.delete(userAppointmentEntity.getId(), request, response);
                }
            } else {
                return ResultUtil.error(MasterEum.master_40023.getCode(), MasterEum.master_40023.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public void querySoonMechanismCourse() {
        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
        List<UserAppointmentEntity> list = mapper.querySoonMechanismCourse(userAppointmentEntity);
        if (list != null && list.size() > 0) {
            for (UserAppointmentEntity appointmentEntity : list) {
                PushMessageParam pushMessageParamUser = new PushMessageParam();
                pushMessageParamUser.setTarget_id(appointmentEntity.getUser_id());
                pushMessageParamUser.setPush_type("offline_course_reminders");
                pushMessageParamUser.setOpera_type("offline_course_reminders");
                pushMessageParamUser.setContent(("您的线下课程即将开始").trim());
                pushMessageParamUser.setType("offline_course_reminders");
                pushMessageParamUser.setType_id(appointmentEntity.getId());
                rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParamUser);
            }
        }
    }

    @Override
    public synchronized void queryEndMechanismCourse() {
        UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
        List<UserAppointmentEntity> list = mapper.queryEndMechanismCourse(userAppointmentEntity);
        if (list != null && list.size() > 0) {
            for (UserAppointmentEntity appointmentEntity : list) {
                RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                rechargeRecordEntity.setUser_id(appointmentEntity.getUser_id());
                rechargeRecordEntity.setUser_appointment_id(appointmentEntity.getId());
                rechargeRecordEntity.setPageSize(1);
                List<RechargeRecordEntity> list1 = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
                if (list1 == null || list1.size() < 1) {
                    rechargeRecordEntity.setAppointment_id(appointmentEntity.getAppointment_id());
                    rechargeRecordEntity.setUser_appointment_id(null);
                    list1 = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
                    if (list1 != null && list1.size() > 0) {
                        rechargeRecordEntity = list1.get(0);
                    } else {
                        continue;
                    }
                    MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(appointmentEntity.getMechanism_id());
                    UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(appointmentEntity.getStudy_card_id());
                    UserInfoEntity userInfoEntity;
                    BigDecimal finalCash = new BigDecimal("0");
                    if (userStudyCardEntity != null && userStudyCardEntity.getBinding_mechanism_id() != null && userStudyCardEntity.getBinding_mechanism_id() != 0) {
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                        finalCash = rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.95"));
                        userInfoEntity.setCash(finalCash);
                    } else {
                        userInfoEntity = userInfoMapper.selectByPrimaryKey(mechanismEntity.getUser_id());
                        finalCash = rechargeRecordEntity.getAmount().multiply(new BigDecimal("0.85"));
                        userInfoEntity.setCash(finalCash);
                    }
                    rechargeRecordEntity.setStatus(3);
                    rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecordEntity);
                    Integer integer = userInfoMapper.updateCash(userInfoEntity);
                    if (integer > 0) {
                        appointmentEntity.setEarnings_status(2);
                        mapper.updateByPrimaryKeySelective(appointmentEntity);
                    }
                }
            }
        }
    }

    private void setinfo(List<UserAppointmentEntity> list) {
        for (UserAppointmentEntity userAppointmentEntity : list) {
            HashMap<String, Object> map = Maps.newHashMap();
            MasterSetPriceEntity masterSetPriceEntity = null;
            if (userAppointmentEntity.getMaster_set_price_id() != 0) {
                masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(userAppointmentEntity.getMaster_set_price_id());
            }
            map.put("masterSetPriceEntity", masterSetPriceEntity);
            map.put("masterinfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getMaster_id() + "userinfo")));

            MasterMechanismEntity mechanismEntity = null;
            if (userAppointmentEntity.getMechanism_id() != 0) {
                mechanismEntity = masterMechanismMapper.selectByPrimaryKey(userAppointmentEntity.getMechanism_id());
            }
            map.put("mechanismEntity", mechanismEntity);
            map.put("masterAppointment", masterAppointmentMapper.selectByPrimaryKey(userAppointmentEntity.getAppointment_id()));
            userAppointmentEntity.setMap(map);
        }
    }
}