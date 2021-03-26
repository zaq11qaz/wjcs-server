package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.MasterAppointmentService;
import com.huihe.eg.user.service.dao.MasterSummaryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserAppointmentService;
import com.huihe.eg.user.service.dao.pay.PayService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterSummaryServiceImpl extends BaseFrameworkServiceImpl<MasterSummaryEntity, Long> implements MasterSummaryService {

    @Resource
    private MasterSummaryMapper mapper;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private MasterAppointmentService masterAppointmentService;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private PayService payService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<MasterSummaryEntity> queryListPage(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSummaryEntity> masterSummaryEntities = null;
        try {
            masterSummaryEntities = mapper.queryListPage(param);
            if (masterSummaryEntities != null && masterSummaryEntities.size() > 0) {
                for (MasterSummaryEntity entity : masterSummaryEntities) {
                    Map<String, Object> map = new HashMap<>();
                    MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                    masterInfoEntity.setUser_id(entity.getMaster_id());
                    masterInfoEntity.setPageSize(1);
                    map.put("masterinfo", masterInfoMapper.queryListPage(masterInfoEntity));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                    map.put("userappointmentinfo", userAppointmentService.findById(entity.getAppointment_id(), request, response));
                    entity.setMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MasterSummaryServiceImpl     queryListPage");
        }
        return masterSummaryEntities;
    }

    @Override
    public synchronized ResultParam insertSummary(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
            masterSummaryEntity.setMaster_id(param.getMaster_id());
            masterSummaryEntity.setAppointment_id(param.getAppointment_id());
            masterSummaryEntity.setPageSize(1);
            List<MasterSummaryEntity> list = mapper.queryListPage(masterSummaryEntity);
            if (list != null && list.size() > 0) {
                return ResultUtil.error(MasterEum.master_40024.getCode(), MasterEum.master_40024.getDesc());
            }
            int i = mapper.insertSelective(param);
            if (i > 0) {
                MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(param.getAppointment_id());
                masterAppointmentEntity.setStatus(2);
                int res = masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);
                if (res > 0) {
                    return ResultUtil.success();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public ResultParam insertSummaryOffline(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
            masterSummaryEntity.setAppointment_id(param.getAppointment_id());
            masterSummaryEntity.setPageSize(1);
            List<MasterSummaryEntity> list = mapper.queryListPage(masterSummaryEntity);
            if (list != null && list.size() > 0) {
                return ResultUtil.error(MasterEum.master_40024.getCode(), MasterEum.master_40024.getDesc());
            }
            int i = mapper.insertSelective(param);
            if (i > 0) {
                MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey(param.getAppointment_id());
                masterAppointmentEntity.setStatus(2);
                masterAppointmentMapper.updateByPrimaryKeySelective(masterAppointmentEntity);

                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
                userAppointmentEntity.setAppointment_id(param.getAppointment_id());
                List<UserAppointmentEntity> query = userAppointmentMapper.query2and3(userAppointmentEntity);
                if (query != null && query.size() > 0) {
                    for (UserAppointmentEntity appointmentEntity : query) {
                        if ("scheduling".equals(masterAppointmentEntity.getCreate_type())) {

                            if (appointmentEntity.getIs_pay()) {
                                UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(userAppointmentEntity.getStudy_card_id());
                                appointmentEntity.setStatus(8);
                                int i1 = userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);
                                if (i1 == 0) {
                                    throw new Exception("参数错误");
                                }
                                Boolean flag = this.insertRechargeRecord(userStudyCardEntity, masterAppointmentEntity, param, userAppointmentEntity);
                                if (!flag) {
                                    throw new Exception();
                                }
                            } else {
                                appointmentEntity.setStatus(9);
                                userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);
                            }
                        } else {
                            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
                            rechargeRecordEntity.setAppointment_id(param.getAppointment_id());
                            rechargeRecordEntity.setUser_id(appointmentEntity.getUser_id());
                            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey(appointmentEntity.getStudy_card_id());
                            ResultParam resultParam = payService.aliPrepayment2Pay(rechargeRecordEntity, request, response);
                            if (resultParam.getCode() == 0) {
                                appointmentEntity.setStatus(9);
                            } else {
                                appointmentEntity.setStatus(8);
                                Boolean flag = this.insertRechargeRecord(userStudyCardEntity, masterAppointmentEntity, param, appointmentEntity);
                                if (!flag) {
                                    throw new Exception();
                                }
                            }
                            userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);
                        }
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(appointmentEntity.getId());
                        pushMessageParam.setTarget_id(appointmentEntity.getUser_id());
                        pushMessageParam.setPush_type("summary");
                        pushMessageParam.setOpera_type("summary");
                        pushMessageParam.setContent("您的课程已总结，请前往查看");
                        pushMessageParam.setType("summary");
                        pushMessageParam.setTitle("课程已总结");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("classNewsMessage", pushMessageParam);
                    }
                    return ResultUtil.success();
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultUtil.error(MasterEum.master_40025.getCode(), MasterEum.master_40025.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    private Boolean insertRechargeRecord(UserStudyCardEntity userStudyCardEntity, MasterAppointmentEntity masterAppointmentEntity, MasterSummaryEntity param, UserAppointmentEntity userAppointmentEntity) {
        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
        rechargeRecordEntity.setUser_appointment_id(userAppointmentEntity.getId());
        rechargeRecordEntity.setUser_id(userAppointmentEntity.getUser_id());
        rechargeRecordEntity.setPageSize(1);
        List<RechargeRecordEntity> list = rechargeRecordMapper.queryListPage(rechargeRecordEntity);
        if (list != null && list.size() > 0) {
            return true;
        }

        if (userStudyCardEntity.getCourse_num()<=3 && userStudyCardEntity.getIs_pay_deposit()){
            rechargeRecordEntity.setFinished(true);
            rechargeRecordEntity.setStatus(2);
        }
        rechargeRecordEntity.setSource(param.getSource());
        rechargeRecordEntity.setTitle(masterAppointmentEntity.getTitle());
        rechargeRecordEntity.setAmount(userStudyCardEntity.getEach_lesson_price());
        rechargeRecordEntity.setCourse_num(1);
        rechargeRecordEntity.setStudy_type("mechanism_offline");
        rechargeRecordEntity.setRcharge_type("study_card");
        rechargeRecordEntity.setType("single_session");
        rechargeRecordEntity.setNumber_of_lessons(masterAppointmentEntity.getNumber_of_lessons().intValue());
        rechargeRecordEntity.setUser_study_card_id(userStudyCardEntity.getId());
        rechargeRecordEntity.setAppointment_id(masterAppointmentEntity.getId());
        rechargeRecordEntity.setStudycard_id(masterAppointmentEntity.getMaster_set_price_id());
        rechargeRecordEntity.setMechanism_id(masterAppointmentEntity.getMechanism_id());
        rechargeRecordEntity.setMaster_id(masterAppointmentEntity.getMaster_id());
        rechargeRecordEntity.setIs_one_time_payment(true);
        rechargeRecordEntity.setTitle(masterAppointmentEntity.getTitle());
        rechargeRecordEntity.setIs_teach_paypal(true);
        return rechargeRecordMapper.insertSelective(rechargeRecordEntity) > 0;
    }

    @Override
    public List<MasterSummaryEntity> querySummaryList(MasterSummaryEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterSummaryEntity> list = Lists.newArrayList();
        if (param.getMechanism_id() != null || param.getAppointment_id() != null || param.getMaster_id() != null) {
            list = mapper.queryListPage(param);
        } else if (param.getUser_id() != null) {
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setUser_id(param.getUser_id());
            userAppointmentEntity.setPageSize(param.getPageSize());
            userAppointmentEntity.setCurrentPage(param.getCurrentPage());
            List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryListPage8or9(userAppointmentEntity);
            if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                for (UserAppointmentEntity appointmentEntity : userAppointmentEntities) {
                    MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
                    masterSummaryEntity.setAppointment_id(appointmentEntity.getAppointment_id());
                    masterSummaryEntity.setPageSize(1);
                    list.addAll(mapper.queryListPage(masterSummaryEntity));
                }
            }
        } else if (param.getId() != null) {
            list.add(mapper.selectByPrimaryKey(param.getId()));
        }
        if (list != null && list.size() > 0) {
            this.setInfo(list);
        }
        /*
        else if ()param.getMaster_id()!=null{
            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setStatus(2);
            masterAppointmentEntity.setMaster_id(param.getMaster_id());
            masterAppointmentEntity.setPageSize(param.getPageSize());
            masterAppointmentEntity.setCurrentPage(param.getCurrentPage());
            List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentMapper.queryListPage(masterAppointmentEntity);
            if (masterAppointmentEntities!=null&&masterAppointmentEntities.size()>0){
                for (MasterAppointmentEntity appointmentEntity : masterAppointmentEntities) {
                    MasterSummaryEntity masterSummaryEntity = new MasterSummaryEntity();
                    masterSummaryEntity.setAppointment_id(appointmentEntity.getId());
                    masterSummaryEntity.setPageSize(1);
                    list.addAll(mapper.queryListPage(masterSummaryEntity));
                }
            }
        }

         */

        return list;
    }

    private void setInfo(List<MasterSummaryEntity> list) {
        for (MasterSummaryEntity masterSummaryEntity : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("masterAppointmentEntity", masterAppointmentMapper.selectByPrimaryKey(masterSummaryEntity.getAppointment_id()));
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(masterSummaryEntity.getMaster_id() + "userinfo")));
            masterSummaryEntity.setMap(map);
        }
    }
}