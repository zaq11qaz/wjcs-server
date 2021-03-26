package com.huihe.eg.user.service.impl;

import com.google.common.collect.Lists;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.cloud.feign.MessageApiService;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.PushHistoryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushHistoryServiceImpl extends BaseFrameworkServiceImpl<PushHistoryEntity, Long> implements PushHistoryService {

    @Resource
    private PushHistoryMapper mapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;
    @Resource
    private UserOrderMapper userOrderMapper;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserIdentityMapper userIdentityMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private OverseasIdentityMapper overseasIdentityMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private MasterNoticeMapper masterNoticeMapper;
    @Resource
    private UserRecommenderIncomeLogMapper userRecommenderIncomeLogMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;
    @Resource
    private UserStudyCardMapper userStudyCardMapper;
    @Resource
    private SignInMapper signInMapper;
    @Resource
    private MechanismUserMapper mechanismUserMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    //修改看过
    @Override
    public List<PushHistoryEntity> queryListPage(PushHistoryEntity pushHistoryEntity, HttpServletRequest request, HttpServletResponse response) {
        List<PushHistoryEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryListPage(pushHistoryEntity);
            //更新状态已读
            List<PushHistoryEntity> list1 = list;
            for (PushHistoryEntity historyEntity : list1) {
                historyEntity.setUser_status(2);
                super.update(historyEntity, request, response);
            }
            //查询相关信息
            for (PushHistoryEntity historyEntity : list) {
                HashMap<String, Object> map = new HashMap<>();
                /**
                 * 课堂
                 */
                if ("auth".equals(historyEntity.getType())) {
                    if ("apply_appointment_Cancel".equals(historyEntity.getLarge_type()) || "appointment_Cancel_request".equals(historyEntity.getLarge_type())
                            || "live_class_statr_notice".equals(historyEntity.getLarge_type())
                            || "curriculumCancelRespond".equals(historyEntity.getLarge_type()) || "offLine_cancel_Course".equals(historyEntity.getLarge_type())
                            || "student_class_notice".equals(historyEntity.getLarge_type()) || "student_single_class_notice".equals(historyEntity.getLarge_type())
                            || "teacher_class_notice".equals(historyEntity.getLarge_type()) || "teacher_single_class_notice".equals(historyEntity.getLarge_type())
                            || "curriculum_Update_Respond_agree".equals(historyEntity.getLarge_type()) || "curriculum_insert_Respond_agree".equals(historyEntity.getLarge_type())
                            || "curriculum_Cancel_Respond_agree".equals(historyEntity.getLarge_type()) || "curriculum_Cancel_Respond_disagree".equals(historyEntity.getLarge_type())
                            || "curriculum_Cancel_Respond".equals(historyEntity.getLarge_type()) || "curriculum_update_request".equals(historyEntity.getLarge_type())
                            || "curriculum_appointment".equals(historyEntity.getLarge_type()) || "curriculum_appointment_full".equals(historyEntity.getLarge_type())

                            || "applyCurriculumCancel".equals(historyEntity.getLarge_type()) || "class_statr_notice".equals(historyEntity.getLarge_type())
                            || "cancaleCourse".equals(historyEntity.getLarge_type()) || "curriculumAppointment".equals(historyEntity.getLarge_type())
                            || "classnotice".equals(historyEntity.getLarge_type()) || "appointment_scheduling ".equals(historyEntity.getLarge_type())
                            || "curriculumUpdateRespond".equals(historyEntity.getLarge_type())|| "offline_course_reminders".equals(historyEntity.getLarge_type())
                            || "sign_in".equals(historyEntity.getLarge_type())

                    ) {
                        UserAppointmentEntity userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                        if (userAppointmentEntity != null) {
                            map.put("userAppointmentEntity", userAppointmentEntity);
//                            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(userAppointmentEntity.getUser_id() + "userinfo")));
                        } else {
                            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            if (masterAppointmentEntity != null) {

                                map.put("masterAppointmentEntity", masterAppointmentEntity);
//                           map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(masterAppointmentEntity.getUser_id() + "userinfo")));
                            } else {
                                MasterNoticeEntity masterNoticeEntity = new MasterNoticeEntity();
                                masterNoticeEntity.setAppointment_id(historyEntity.getType_id().longValue());
                                map.put("appointmentCancel", masterNoticeMapper.queryListPage(masterNoticeEntity));
                                historyEntity.setMap(map);
                                continue;
                            }
                        }
                        //学生信息
                        historyEntity.setMap(map);
                        continue;
                    }
                    if ("live_class_statr_notice".equals(historyEntity.getOpera_type()) || "single_class_close".equals(historyEntity.getOpera_type())
                            || "course_evaluation".equals(historyEntity.getOpera_type()) || "single_course_evaluation_comment".equals(historyEntity.getOpera_type())
                            || "groupClassNotice".equals(historyEntity.getOpera_type())
                    ) {
                        //助学师课程
                        MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                        if (masterAppointmentEntity != null) {
                            map.put("masterAppointmentEntity", masterAppointmentEntity);
                        } else {
                            UserAppointmentEntity userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            if (userAppointmentEntity != null) {
                                map.put("userAppointmentEntity", userAppointmentEntity);
                            } else {
                                map.put("classEnd", "课程已结束");
                            }
                        }
                        historyEntity.setMap(map);
                        continue;
                    }
                }
                /**
                 * 事件消息
                 */
                if ("event".equals(historyEntity.getType())) {
                    if ("login".equals(historyEntity.getLarge_type()) || "register".equals(historyEntity.getLarge_type()) ||
                            "chat".equals(historyEntity.getLarge_type()) || "task".equals(historyEntity.getLarge_type()) ||
                            "sign".equals(historyEntity.getLarge_type()) || "recharge".equals(historyEntity.getLarge_type()) ||
                            "payment".equals(historyEntity.getLarge_type()) || "gift".equals(historyEntity.getLarge_type()) ||
                            "redenvelopes".equals(historyEntity.getLarge_type()) || "viewpoint".equals(historyEntity.getLarge_type()) ||
                            "live_broadcast".equals(historyEntity.getLarge_type()) || "live_connection".equals(historyEntity.getLarge_type()) ||
                            "video_connection".equals(historyEntity.getLarge_type()) || "video_broadcast".equals(historyEntity.getLarge_type()) ||
                            "class".equals(historyEntity.getLarge_type()) || "watch_recording".equals(historyEntity.getLarge_type())||
                            "add_friends".equals(historyEntity.getLarge_type())

                    ) {
                        UserOrderEntity userOrderEntity = userOrderMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                        map.put("userOrderEntity", userOrderEntity);
                        historyEntity.setMap(map);
                        continue;
                    }
                }
                /**
                 * 系统
                 */
                if ("system".equals(historyEntity.getType())) {
                    if ("updateAuth".equals(historyEntity.getLarge_type()) || "frozenStatus".equals(historyEntity.getLarge_type())) {

                        //助学师审核
                        if ("masterAuthentication".equals(historyEntity.getOpera_type()) || "mechanisMasterAuth".equals(historyEntity.getOpera_type()) ||
                                "masterInfo".equals(historyEntity.getOpera_type())

                        ) {
                            MasterInfoEntity masterInfoEntity = masterInfoMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("masterInfoEntity", masterInfoEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        //机构认证
                        if ("mechanismAuthentication".equals(historyEntity.getOpera_type())) {
                            MasterMechanismEntity masterMechanismEntity = masterMechanismMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("masterMechanismEntity", masterMechanismEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("sign".equals(historyEntity.getOpera_type())) {
                            SignInEntity signInEntity = signInMapper.selectByPrimaryKey((long)historyEntity.getType_id());
                            map.put("SignInEntity", signInEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        //助学师课程认证
                        if ("masterAppointmentAuthentication".equals(historyEntity.getOpera_type())) {
                            MasterAppointmentEntity masterAppointmentEntity = masterAppointmentMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("masterAppointmentEntity", masterAppointmentEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        //实名认证
                        if ("realNameAuthentication".equals(historyEntity.getOpera_type())) {
                            UserIdentityEntity userIdentityEntity = userIdentityMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("userIdentityEntity", userIdentityEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        //海外身份认证
                        if ("abroadAuthentication".equals(historyEntity.getOpera_type())) {
                            OverseasIdentityEntity overseasIdentityEntity = overseasIdentityMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("overseasIdentityEntity", overseasIdentityEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("recommenderPass".equals(historyEntity.getOpera_type()) || "recommenderRefuse".equals(historyEntity.getOpera_type())) {
                            UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(Long.valueOf(historyEntity.getType_id()));
                            map.put("userRecommenderEntity", userRecommenderEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("mechanism_user_need_check".equals(historyEntity.getOpera_type()) ) {
                            MechanismUserEntity mechanismUserEntity = mechanismUserMapper.selectByPrimaryKey(historyEntity.getType_id());
                            map.put("mechanismUserEntity", mechanismUserEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("mechanism_offline".equals(historyEntity.getOpera_type()) || "sale_course".equals(historyEntity.getOpera_type())||
                        "master_offline".equals(historyEntity.getOpera_type())

                        ) {
                            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = userRecommenderIncomeLogMapper.selectByPrimaryKey(Long.valueOf(historyEntity.getType_id()));
                            map.put("userRecommenderIncomeLogEntity",userRecommenderIncomeLogEntity );
                            historyEntity.setMap(map);
                            continue;
                        }
                    }

                    /*
                    if ("pay".equals(historyEntity.getLarge_type())) {
                        UserOrderEntity userOrderEntity = userOrderMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                        map.put("userOrderEntity", userOrderEntity);
                        if (historyEntity.getSend_id() != 0) {
                            map.put("sendUserInfo", JSONUtils.obj2Json(redisService.getStr(historyEntity.getSend_id() + "userinfo")));
                        } else {
                            map.put("sendUserInfo", null);
                        }
                        historyEntity.setMap(map);
                        continue;

                    }

                     */
                    if ("cash".equals(historyEntity.getLarge_type())||"pay".equals(historyEntity.getLarge_type())) {
                        if ("cash".equals(historyEntity.getOpera_type())) {
                            UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity = userRecommenderIncomeLogMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("userRecommenderIncomeLogEntity", userRecommenderIncomeLogEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("saleCash".equals(historyEntity.getOpera_type())||"pay".equals(historyEntity.getOpera_type())
                                ||"buy_coupon".equals(historyEntity.getOpera_type())

                        ) {
                            RechargeRecordEntity rechargeRecordEntity = rechargeRecordMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                            map.put("rechargeRecordEntity", rechargeRecordEntity);
                            historyEntity.setMap(map);
                            continue;
                        }
                        if ("buy_course".equals(historyEntity.getOpera_type())

                        ) {
                            UserStudyCardEntity userStudyCardEntity = userStudyCardMapper.selectByPrimaryKey((long) pushHistoryEntity.getType_id());
                            map.put("userStudyCardEntity",userStudyCardEntity );
                            historyEntity.setMap(map);
                            continue;
                        }
                    }
                }
                /**
                 * 动态消息
                 */
                if ("dynamic".equals(historyEntity.getType())) {
                    //接受礼物动态
                    if ("gift".equals(historyEntity.getLarge_type())) {
                        //如果发送人id不为空
                        UserOrderEntity userOrderEntity = userOrderMapper.selectByPrimaryKey((long) historyEntity.getType_id());
                        map.put("userOrderEntity", userOrderEntity);
                        if (historyEntity.getSend_id() != 0) {
                            map.put("sendUserInfo", JSONUtils.obj2Json(redisService.getStr(historyEntity.getSend_id() + "userinfo")));
                        }
                        historyEntity.setMap(map);
                        continue;
                    }

                    //笔记
                    if ("note".equals(historyEntity.getLarge_type())) {
                        if ("note".equals(historyEntity.getOpera_type()) || "addViewpoint".equals(historyEntity.getOpera_type()) ||
                                "like".equals(historyEntity.getOpera_type()) || "viewpoint".equals(historyEntity.getOpera_type()) ||
                                "addNoteComment".equals(historyEntity.getOpera_type())

                        ) {
//                        String str = newsApiService.queryHistoryById((long)historyEntity.getType_id());
//                        map.put("noteInfo", redisService.getStr(historyEntity.getTarget_id()+"notenews"));
//                        Integer integer = messageApiService.queryHistoryId((long) historyEntity.getType_id());//获取发起userid
                            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(historyEntity.getType_id() + "userinfo")));
                            historyEntity.setMap(map);
                            continue;
                        }
                    }
//                    观点
                    if ("curiosity".equals(historyEntity.getLarge_type())) {
//                    点赞
                        if ("curiosity".equals(historyEntity.getOpera_type()) || "addViewpoint".equals(historyEntity.getOpera_type()) ||
                                "like".equals(historyEntity.getOpera_type()) || "viewpoint".equals(historyEntity.getOpera_type())
                        ) {
//                            Integer integer = newsApiService.queryHistoryById((long) historyEntity.getType_id());
//                            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(integer + "userinfo")));
                            map.put("userInfo", JSONUtils.obj2Json(redisService.getStr(historyEntity.getType_id() + "userinfo")));
                            historyEntity.setMap(map);
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryIsRead(PushHistoryEntity pushHistoryEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            pushHistoryEntity.setUser_status(1);
            pushHistoryEntity.setType("auth");
            Integer authIsRead = mapper.queryIfRead(pushHistoryEntity);
            pushHistoryEntity.setType("event");
            Integer eventIsRead = mapper.queryIfRead(pushHistoryEntity);
            pushHistoryEntity.setType("dynamic");
            Integer dynamicIsRead = mapper.queryIfRead(pushHistoryEntity);
            pushHistoryEntity.setType("system");
            Integer systemIsRead = mapper.queryIfRead(pushHistoryEntity);
            //'auth 课程消息 event 事件消息 dynamic 动态消息 system 系统消息 大类型',
            map.put("authIsRead", authIsRead != null);
            map.put("eventIsRead", eventIsRead != null);
            map.put("dynamicIsRead", dynamicIsRead != null);
            map.put("systemIsRead", systemIsRead != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}