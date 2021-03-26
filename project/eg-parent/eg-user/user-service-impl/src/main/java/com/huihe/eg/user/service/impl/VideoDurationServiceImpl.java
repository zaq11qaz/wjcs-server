package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.CouponEum;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class VideoDurationServiceImpl extends BaseFrameworkServiceImpl<VideoDurationEntity, Long> implements VideoDurationService {

    @Resource
    private VideoDurationMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserAppointmentService userAppointmentService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserPriceService priceService;
    @Resource
    private UserOrderMapper orderMapper;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private SettledAnchorService settledAnchorService;
    @Resource
    private UserClassCardService userClassCardService;
    @Resource
    private UserClassCardMapper userClassCardMapper;
    @Resource
    private ClassRecordService classRecordService;
    @Resource
    private ClassRecordMapper classRecordMapper;
    @Resource
    private CurriculumRecordMapper curriculumRecordMapper;
    @Resource
    private CouponListMapper couponListMapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private MasterAppointmentService masterAppointmentService;

    @Override
    public void init() {
        setMapper(mapper);
    }


    //查询未挂断视频并挂断
    public void queryUnsuspendedVideo(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<VideoDurationEntity> videoDurationEntities = null;
        try {
            videoDurationEntities = mapper.queryUnsuspendedVideo(param);
            if (videoDurationEntities != null && videoDurationEntities.size() > 0) {
                for (VideoDurationEntity entity : videoDurationEntities) {
                    if ("class" .equalsIgnoreCase(entity.getType())) {
                        entity.setStatus(2);
                        mapper.updateByPrimaryKeySelective(entity);
                    } else {
                        UserInfoEntity infoEntity1 = userInfoService.findById(entity.getUser_id(), request, response);
                        Long duration = 0L;
                        if (entity.getEnd_time().getTime() <= System.currentTimeMillis()) {
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setUser_id(entity.getUser_id());
                            infoEntity.setCat_coin(BigDecimal.valueOf(0));
                            infoEntity.setDuration(0);
                            userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                            duration = (entity.getEnd_time().getTime() - entity.getCreate_time().getTime()) / 1000;
                            entity.setDuration(duration.intValue());
                            mapper.updateByPrimaryKeySelective(entity);
                        } else {
                            duration = (System.currentTimeMillis() - entity.getCreate_time().getTime()) / 1000;
                            entity.setDuration(duration.intValue());
                            int cat_coin = infoEntity1.getCat_coin().intValue();
                            if (infoEntity1.getDuration() >= duration) {
                                long aLong = infoEntity1.getDuration().longValue() - duration;
                                UserInfoEntity infoEntity = new UserInfoEntity();
                                infoEntity.setDuration((int) aLong);
                                infoEntity.setUser_id(entity.getUser_id());
                                int ret = userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                                if (ret > 0) {
                                    mapper.updateByPrimaryKeySelective(entity);
                                }
                            } else if (infoEntity1.getDuration() < duration) {
                                long aLong = duration - infoEntity1.getDuration().longValue();
                                long remaining_cat = aLong * 20;
                                cat_coin = cat_coin - (int) remaining_cat;
                                UserInfoEntity infoEntity = new UserInfoEntity();
                                infoEntity.setUser_id(entity.getUser_id());
                                if (cat_coin < 0) {
                                    infoEntity.setCat_coin(BigDecimal.valueOf(0));
                                } else {
                                    infoEntity.setCat_coin(BigDecimal.valueOf(cat_coin));
                                }
                                infoEntity.setDuration(0);
                                int ret = userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                                if (ret > 0) {
                                    mapper.updateByPrimaryKeySelective(entity);
                                }
                            } else {
                                logger.warn("VideoDurationServiceImpl insert  duration  is null");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl   param");
        }
    }

    @Override
    @Transactional
    public ResultParam insert(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (param.getType() != null && "class" .equalsIgnoreCase(param.getType())) {
                UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//查询此时有没有课
                userAppointmentEntity.setPageSize(1);
                userAppointmentEntity.setUser_id(param.getUser_id());
                userAppointmentEntity.setMaster_id(param.getOther_id());
                List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryClass(userAppointmentEntity);
                if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                    UserAppointmentEntity appointmentEntity = userAppointmentEntities.get(0);
                    param.setAppointment_id(appointmentEntity.getId());
                    if (userAppointmentEntities.get(0).getStatus() == 2) {
                        appointmentEntity.setStatus(3); //修改课程完成
                        userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);//课程完成
                        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                        masterAppointmentEntity.setId(appointmentEntity.getAppointment_id());

                        MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                        masterInfoEntity.setUser_id(userAppointmentEntities.get(0).getMaster_id());
                        masterInfoEntity.setType(userAppointmentEntities.get(0).getMaster_type());
                        masterInfoEntity.setPageSize(1);
                        List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                        if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                            masterInfoEntity.setId(masterInfoEntities.get(0).getId());
                            masterInfoEntity.setEarnings(userAppointmentEntities.get(0).getEarnings());
                            masterInfoMapper.updateAddEarnings(masterInfoEntity);//修改收益
                        }
                    }
                    param.setCreate_time(appointmentEntity.getStart_time());
                    param.setEnd_time(appointmentEntity.getEnd_time());
                    if (param.getId() == null) {
                        super.insert(param, request, response);
                    }

                    map.put("video_id", param.getId());
                    map.put("is_video", true); //是否可以发送视频
                    map.put("appointment_id", userAppointmentEntities.get(0).getId()); //课程id
                    Map<String, String> mapPush = new HashMap<>();
                    mapPush.put("video_id", param.getId().toString());//视频记录id
                    UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                    mapPush.put("user_id", infoEntity.getUser_id().toString());
                    mapPush.put("avatar", infoEntity.getAvatar());
                    mapPush.put("nick_name", infoEntity.getNick_name());
                    mapPush.put("appointment_id", userAppointmentEntities.get(0).getId().toString());
                    PushMessageParam pushMessageParam = new PushMessageParam();//推送
                    pushMessageParam.setMap(mapPush);
                    pushMessageParam.setPush_type("class");
                    pushMessageParam.setOpera_type("class");
                    pushMessageParam.setType("video"); //视频 个推
                    pushMessageParam.setSend_id(param.getUser_id());
                    pushMessageParam.setTarget_id(param.getOther_id());
                    pushMessageParam.setContent(JSONUtils.obj2Json(mapPush).toString());
                    rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                } else {
                    map.put("is_video", false);
                }
            } else {
                UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                if (infoEntity != null) {
                    int cat_coin = infoEntity.getCat_coin().intValue();
                    if (infoEntity.getDuration() > 0 || infoEntity.getDuration() + cat_coin / 20 > 0) {
                        map.put("is_video", true);
                        int duration = cat_coin / 20 * 60 + infoEntity.getDuration();
                        Date dt = new Date();
                        Calendar rightNow = Calendar.getInstance();
                        rightNow.setTime(dt);
                        rightNow.add(Calendar.SECOND, duration);
                        Date dt1 = rightNow.getTime();
                        param.setEnd_time(dt1);
                        UserInfoEntity otherInfo = userInfoService.findById(param.getOther_id(), request, response);
                        if (otherInfo != null) {
                            if (param.getId() == null) {
                                queryUnsuspendedVideo(param, request, response);
                                param.setStatus(3);
                                super.insert(param, request, response);
                            }
                            map.put("video_id", param.getId().toString());
                            map.put("end_time", dt1);
                            Map<String, String> mapPush = new HashMap<>();
                            mapPush.put("video_id", param.getId().toString());
                            mapPush.put("user_id", infoEntity.getUser_id().toString());
                            mapPush.put("avatar", infoEntity.getAvatar());
                            mapPush.put("nick_name", infoEntity.getNick_name());
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setMap(mapPush);
                            pushMessageParam.setPush_type("chat");
                            pushMessageParam.setOpera_type("chat");
                            pushMessageParam.setType("video");
                            pushMessageParam.setSend_id(param.getUser_id());
                            pushMessageParam.setTarget_id(param.getOther_id());
                            pushMessageParam.setContent(JSONUtils.obj2Json(mapPush).toString());
                            pushMessageParam.setType_id(param.getId());
                            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                        } else {
                            return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                        }
                    } else {
                        map.put("is_video", false);
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                }
            }

        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam videoDurationConnect(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            VideoDurationEntity videoDurationEntity = super.findById(param.getId(), request, response);
            if (videoDurationEntity != null) {
                if (videoDurationEntity.getType() != null && "video" .equalsIgnoreCase(videoDurationEntity.getType())) {
                    UserInfoEntity infoEntity = userInfoService.findById(videoDurationEntity.getUser_id(), request, response);
                    int cat_coin = infoEntity.getCat_coin().intValue();
                    if (infoEntity.getDuration() > 0 || infoEntity.getDuration() + cat_coin / 20 > 0) {
                        map.put("is_video", true);
                        UserInfoEntity otherInfo = userInfoService.findById(videoDurationEntity.getOther_id(), request, response);
                        if (otherInfo != null) {
                            Date dt = new Date();
                            Calendar rightNow = Calendar.getInstance();
                            rightNow.setTime(dt);
                            rightNow.add(Calendar.MINUTE, infoEntity.getDuration() + cat_coin / 20);
                            Date dt1 = rightNow.getTime();
                            videoDurationEntity.setEnd_time(dt1);
                            videoDurationEntity.setStatus(1);
                            super.update(videoDurationEntity, request, response);
                        } else {
                            return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                        }
                    } else {
                        map.put("is_video", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl  videoDurationConnect");
        }
        return ResultUtil.success(map);
    }

    /*public void timeTaskVideoChat() {
        List<VideoDurationEntity> videoDurationEntities = null;
        try {
            VideoDurationEntity videoDurationEntity = new VideoDurationEntity();
            videoDurationEntity.setStatus(1);
            videoDurationEntities = mapper.query(videoDurationEntity);
            if (videoDurationEntities != null && videoDurationEntities.size() > 0) {
                for (VideoDurationEntity entity : videoDurationEntities) {
                    if (System.currentTimeMillis() >= entity.getEnd_time().getTime()) {
                        if (entity.getType().equalsIgnoreCase("class")) {
                            UserAppointmentEntity userAppointmentEntity = userAppointmentMapper.selectByPrimaryKey(videoDurationEntity.getAppointment_id());
                            if (userAppointmentEntity != null) {
                                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                                masterInfoEntity.setUser_id(userAppointmentEntity.getMaster_id());
                                masterInfoEntity.setType(userAppointmentEntity.getMaster_type());
                                masterInfoEntity.setPageSize(1);
                                List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                                if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                                    masterInfoEntity.setId(masterInfoEntities.get(0).getId());
                                    masterInfoEntity.setEarnings(userAppointmentEntity.getEarnings());
                                    int ret = masterInfoMapper.updateAddEarnings(masterInfoEntity);
                                    if (ret > 0) {
                                        userAppointmentEntity.setEarnings_status(2);
                                        userAppointmentMapper.updateByPrimaryKeySelective(userAppointmentEntity);
                                    }
                                }
                            }
                            entity.setStatus(2);
                            mapper.updateByPrimaryKeySelective(entity);
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setTarget_id(entity.getUser_id());
                            pushMessageParam.setPush_type("break_video");
                            pushMessageParam.setOpera_type("break_video");
                            pushMessageParam.setType("break_video");
                            pushMessageParam.setContent("您的课程已结束,请点击查看");
                            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                        } else if (entity.getType().equalsIgnoreCase("video")) {
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setUser_id(entity.getUser_id());
                            infoEntity.setCat_coin(BigDecimal.valueOf(0));
                            infoEntity.setDuration(0);
                            userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setTarget_id(entity.getUser_id());
                            pushMessageParam.setPush_type("break_video");
                            pushMessageParam.setOpera_type("break_video");
                            pushMessageParam.setType("break_video");
                            pushMessageParam.setContent("您的视频时长已消耗完,请点击查看");
                            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                        }
                        entity.setStatus(2);
                        PushMessageParam pushMessage = new PushMessageParam();
                        pushMessage.setTarget_id(entity.getOther_id());
                        pushMessage.setPush_type("break_video");
                        pushMessage.setOpera_type("break_video");
                        pushMessage.setType("break_video");
                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessage);

                    } else if (System.currentTimeMillis() < entity.getEnd_time().getTime()) {
                        if (entity.getType().equalsIgnoreCase("video")) {
                            UserInfoEntity infoEntity1 = userInfoMapper.selectByPrimaryKey(entity.getUser_id());
                            Long aLong = System.currentTimeMillis() / 1000 - entity.getCreate_time().getTime() / 1000;
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            Long aLo = aLong / 60 / 5;
                            if (aLong > infoEntity1.getDuration()) {
                                pushMessageParam.setContent("您的视频通话正在消耗猫币");
                            } else if (aLo > 1) {
                                pushMessageParam.setContent("您的视频通话已使用" + aLo * 5 + "分钟");
                            }
                            pushMessageParam.setPush_type("notice_video");
                            pushMessageParam.setOpera_type("notice_video");
                            pushMessageParam.setType("notice_video");
                            pushMessageParam.setTarget_id(entity.getUser_id());
                            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl  timeTaskVideoChat");
        }
    }*/

    @Override
    public List<VideoDurationEntity> queryVideoReconnection(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<VideoDurationEntity> videoDurationEntities = new ArrayList<>();
        try {
            //videoDurationEntities = mapper.queryVideoReconnection(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl  queryVideoReconnection");
        }
        return videoDurationEntities;
    }

    /*@Override
    public List<VideoDurationEntity> queryAnswerVideo(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<VideoDurationEntity> videoDurationEntities=null;
        try {
            videoDurationEntities=mapper.queryListPage(param);
            for (VideoDurationEntity videoDurationEntity:videoDurationEntities){
                if(videoDurationEntity.getType().equalsIgnoreCase("class"))
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl  queryVideoReconnection");
        }
        return videoDurationEntities;
    }*/
    public static void main(String[] args) {
        //往后推时间

    }

    /**
     * param.getType().equalsIgnoreCase class live_connection live_broadcast
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional
    public ResultParam insertVideoOrder(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        boolean aBoolean = false;
        boolean is_audition = true;
        try {
            if (param.getId() != null) {
                if (param.getType() != null && "class" .equalsIgnoreCase(param.getType())) {
                    VideoDurationEntity entity = super.findById(param.getId(), request, response);
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//查询此时有没有课
                    userAppointmentEntity.setPageSize(1);
                    userAppointmentEntity.setUser_id(param.getUser_id());
                    userAppointmentEntity.setMaster_id(param.getOther_id());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryClass(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        aBoolean = true;
                        UserAppointmentEntity appointmentEntity = userAppointmentEntities.get(0);
                        param.setAppointment_id(appointmentEntity.getId());
                        if (userAppointmentEntities.get(0).getStatus() == 2) {
                            appointmentEntity.setStatus(3); //修改课程完成
                            userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);//课程完成
                            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                            masterInfoEntity.setUser_id(userAppointmentEntities.get(0).getMaster_id());
                            masterInfoEntity.setType(userAppointmentEntities.get(0).getMaster_type());
                            masterInfoEntity.setPageSize(1);
                            List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                            /*
                            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                                masterInfoEntity.setId(masterInfoEntities.get(0).getId());
                                masterInfoEntity.setEarnings(masterInfoEntity.getEarnings().add(userAppointmentEntities.get(0).getEarnings()));
                                synchronized (masterInfoEntity) {
                                    masterInfoMapper.updateAddEarnings(masterInfoEntity);//修改收益
                                }
                            }

                             */
                        }
                        Integer duration = entity.getDuration() + 60;
                        param.setDuration(duration);//时长
                        param.setCreate_time(appointmentEntity.getStart_time());
                        param.setEnd_time(appointmentEntity.getEnd_time());
                        mapper.updateByPrimaryKeySelective(param);
                        /*Map<String, String> mapPush = new HashMap<>();
                        mapPush.put("video_id", param.getId().toString());//视频记录id
                        UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                        mapPush.put("user_id", infoEntity.getUser_id().toString());
                        mapPush.put("avatar", infoEntity.getAvatar());
                        mapPush.put("nick_name", infoEntity.getNick_name());
                        mapPush.put("appointment_id", userAppointmentEntities.get(0).getId().toString());
                        PushMessageParam pushMessageParam = new PushMessageParam();//推送
                        pushMessageParam.setMap(mapPush);
                        pushMessageParam.setPush_type("class");
                        pushMessageParam.setOpera_type("class");
                        pushMessageParam.setType("video"); //视频 个推
                        pushMessageParam.setSend_id(param.getUser_id());
                        pushMessageParam.setTarget_id(param.getOther_id());
                        pushMessageParam.setContent(JSONUtils.obj2Json(mapPush).toString());
                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);*/
                    }
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                    map.put("appointment_id", userAppointmentEntities.get(0).getId()); //课程id
                } else if (param.getType() != null &&
                        ("live_connection" .equalsIgnoreCase(param.getType()) ||
                                "live_broadcast" .equalsIgnoreCase(param.getType()))) {
                    //查询课程安排
                    MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                    masterAppointmentEntity.setGroup_id(param.getOther_id());
                    //masterAppointmentEntity.setStatus(3);
                    masterAppointmentEntity.setPageSize(1);
//                    masterAppointmentEntity.setStart_time(new Date());
//                    masterAppointmentEntity.setOffset(BigDecimal.valueOf(8));
                    List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentService.queryIsClassListPage(masterAppointmentEntity);
                    if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                        VideoDurationEntity entity = super.findById(param.getId(), request, response);
                        ClassRecordEntity classRecordEntity = classRecordService.findById(entity.getOrder_id(), request, response);
                        if (classRecordEntity != null) {
                            if (classRecordEntity.getFree_minute() < 2) {//试听三分钟
                                //新增试听记录
                                classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                if (req > 0) {
                                    aBoolean = true;
                                    Integer duration = entity.getDuration() + 60;
                                    param.setDuration(duration);//时长
                                    mapper.updateByPrimaryKeySelective(param);
                                }
                            } else if (classRecordEntity.getFree_minute() == 2) {
                                is_audition = false;//试听
                                //新增试听记录
//                                classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                            } else {
                                classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                if (classRecordEntity.getType() != null && !"" .equalsIgnoreCase(classRecordEntity.getType())) {
                                    Integer duration = entity.getDuration() + 60;
                                    param.setDuration(duration);
                                    int result = mapper.updateByPrimaryKeySelective(param);//修改时长
                                    if (result > 0) {
                                        UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                        userClassCardEntity.setUser_id(param.getUser_id());
                                        userClassCardEntity.setType(classRecordEntity.getType());//查询对应学习卡
                                        userClassCardEntity.setStatus(2);
                                        userClassCardEntity.setPageSize(1);
                                        List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                        if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                            userClassCardEntity = userClassCardEntities.get(0);
                                            if (userClassCardEntity != null) {
                                                if ("minute" .equalsIgnoreCase(classRecordEntity.getType())) {
                                                    if (userClassCardEntity.getMinute_num() > 0) {
                                                        Integer minute_num = userClassCardEntity.getMinute_num() - 1;
                                                        userClassCardEntity.setMinute_num(minute_num);
                                                        int res = 0;
                                                        synchronized (userClassCardEntity) {
                                                            //扣除点数
                                                            res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                        }
                                                        if (res > 0) {
                                                            minute_num = classRecordEntity.getMinute_num() + 1;
                                                            classRecordEntity.setMinute_num(minute_num);
                                                            int ret = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                            if (ret > 0) {
                                                                aBoolean = true;
                                                            }
                                                        }
                                                    } else {
                                                        return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                    }
                                                } else if ("experience" .equalsIgnoreCase(classRecordEntity.getType())) {
                                                    aBoolean = true;
                                                } else {
                                                    aBoolean = true;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                    userClassCardEntity.setUser_id(param.getUser_id());
                                    userClassCardEntity.setStatus(2);
                                    userClassCardEntity.setPageSize(1);
                                    List<UserClassCardEntity> classCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                    if (classCardEntities == null || classCardEntities.size() == 0) {
                                        UserCouponEntity userCouponEntity = new UserCouponEntity();
                                        userCouponEntity.setUser_id(param.getUser_id());
                                        userCouponEntity.setAppointment_id(param.getAppointment_id());
                                        List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                                        if (userCouponEntities != null && userCouponEntities.size() > 0) {
                                            map.put("is_audition", is_audition);
                                            map.put("is_video", aBoolean);
                                            map.put("id", param.getId());
                                            return ResultUtil.success(map);
                                        }
                                        return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                                    }
                                    userClassCardEntity.setDefault_use(true);
                                    userClassCardEntity.setStatus(2);
                                    userClassCardEntity.setPageSize(1);
                                    List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);//查询所指定使用学习卡
                                    if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                        userClassCardEntity = userClassCardEntities.get(0);
                                        Integer duration = entity.getDuration() + 60;
                                        param.setDuration(duration);//时长
                                        int result = mapper.updateByPrimaryKeySelective(param);
                                        if (result > 0) {
                                            if ("curriculum" .equalsIgnoreCase(userClassCardEntity.getType())) {
                                                if (userClassCardEntity.getCurriculum_num() > 0) {//学习卡内点卡
                                                    Integer curriculum_num = userClassCardEntity.getCurriculum_num() - 1;
                                                    userClassCardEntity.setCurriculum_num(curriculum_num);
                                                    int res = 0;
                                                    synchronized (userClassCardEntity) {
                                                        //扣除点数
                                                        res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                    }
                                                    if (res > 0) {
                                                        classRecordEntity.setType(userClassCardEntity.getType());
                                                        classRecordEntity.setCurriculum_num(1);
                                                        int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                        if (req > 0) {
                                                            aBoolean = true;
                                                        }
                                                    }
                                                } else {
                                                    return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                }

                                            } else if ("minute" .equalsIgnoreCase(userClassCardEntity.getType())) {
                                                if (userClassCardEntity.getMinute_num() > 0) {//学习卡内点卡
                                                    Integer minute_num = userClassCardEntity.getMinute_num() - 1;
                                                    userClassCardEntity.setMinute_num(minute_num);
                                                    int res = 0;
                                                    synchronized (userClassCardEntity) {
                                                        //扣除点数
                                                        res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                    }
                                                    if (res > 0) {
                                                        //新增扣除记录
                                                        classRecordEntity.setType(userClassCardEntity.getType());
                                                        classRecordEntity.setMinute_num(classRecordEntity.getMinute_num() + 1);
                                                        int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                        if (req > 0) {
                                                            aBoolean = true;
                                                        }
                                                    }
                                                } else {
                                                    return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                }
                                            }
                                        }
                                    } else {
                                        return ResultUtil.error(MasterEum.master_40012.getCode(), MasterEum.master_40012.getDesc());
                                    }
                                }

                            }

                        }
                    } else {
                        return ResultUtil.error(MasterEum.master_40009.getCode(), MasterEum.master_40009.getDesc());
                    }
                    map.put("is_audition", is_audition);
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                } else {
                    UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                    SettledAnchorEntity settledAnchorEntity = new SettledAnchorEntity();
                    settledAnchorEntity.setUser_id(param.getUser_id());
                    settledAnchorEntity.setStatus(1);
                    settledAnchorEntity.setPageSize(1);
                    List<SettledAnchorEntity> settledAnchorEntities = settledAnchorService.queryListPage(settledAnchorEntity, request, response);
                    if (settledAnchorEntities == null || settledAnchorEntities.size() == 0) {
                        VideoDurationEntity entity = super.findById(param.getId(), request, response);
                        UserPriceEntity userPriceEntity = new UserPriceEntity();
                        userPriceEntity.setType(param.getType());
                        userPriceEntity.setStatus(1);
                        userPriceEntity.setPageSize(1);
                        List<UserPriceEntity> userPriceEntities = priceService.queryListPage(userPriceEntity, request, response);
                        if (infoEntity.getCat_coin().compareTo(userPriceEntities.get(0).getPrice()) >= 0) {
                            infoEntity.setCat_coin(userPriceEntities.get(0).getPrice());
                            int res = 0;
                            synchronized (infoEntity) {
                                //扣除猫币
                                res = userInfoMapper.updateReduceCatCoin(infoEntity);
                            }
                            if (res > 0) {
                                aBoolean = true;
                                BigDecimal bigDecimal = userPriceEntities.get(0).getPrice();
                                if (entity.getOrder_id() != null && entity.getOrder_id() != 0) {
                                    UserOrderEntity entity1 = userOrderService.findById(entity.getOrder_id(), request, response);
                                    bigDecimal = bigDecimal.add(entity1.getPay_count());
                                    entity1.setPay_count(bigDecimal);
                                    orderMapper.updateByPrimaryKeySelective(entity1);

                                } else {
                                    UserOrderEntity orderEntity = new UserOrderEntity();
                                    orderEntity.setPay_count(bigDecimal);
                                    orderEntity.setType("video");
                                    orderEntity.setSource(param.getType());
                                    orderEntity.setUser_id(param.getUser_id());
                                    orderEntity.setPay_id(param.getId());
                                    orderEntity.setType_id(param.getOther_id());
                                    orderEntity.setOrder_no(CommonUtils.generateOrderKey());
                                    orderMapper.insertSelective(orderEntity);
                                    param.setOrder_id(orderEntity.getId());
                                }
                                Integer duration = entity.getDuration() + 60;
                                param.setDuration(duration);//时长
                                mapper.updateByPrimaryKeySelective(param);
                            }
                        }
                    } else {
                        aBoolean = true;
                    }
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                }
            } else {
                if (param.getType() != null && "class" .equalsIgnoreCase(param.getType())) {
                    UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();//查询此时有没有课
                    userAppointmentEntity.setPageSize(1);
                    userAppointmentEntity.setUser_id(param.getUser_id());
                    userAppointmentEntity.setMaster_id(param.getOther_id());
                    List<UserAppointmentEntity> userAppointmentEntities = userAppointmentMapper.queryClass(userAppointmentEntity);
                    if (userAppointmentEntities != null && userAppointmentEntities.size() > 0) {
                        aBoolean = true;
                        map.put("appointment_id", userAppointmentEntities.get(0).getId()); //课程id
                        UserAppointmentEntity appointmentEntity = userAppointmentEntities.get(0);
                        param.setAppointment_id(appointmentEntity.getId());
                        if (userAppointmentEntities.get(0).getStatus() == 2) {
                            appointmentEntity.setStatus(3); //修改课程完成
                            userAppointmentMapper.updateByPrimaryKeySelective(appointmentEntity);//课程完成
                            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                            masterInfoEntity.setUser_id(userAppointmentEntities.get(0).getMaster_id());
                            masterInfoEntity.setType(userAppointmentEntities.get(0).getMaster_type());
                            masterInfoEntity.setPageSize(1);
                            List<MasterInfoEntity> masterInfoEntities = masterInfoMapper.queryListPage(masterInfoEntity);
                            if (masterInfoEntities != null && masterInfoEntities.size() > 0) {
                                masterInfoEntity.setId(masterInfoEntities.get(0).getId());
                                masterInfoEntity.setEarnings(userAppointmentEntities.get(0).getEarnings());
                                synchronized (masterInfoEntity) {
                                    masterInfoMapper.updateAddEarnings(masterInfoEntity);//修改收益
                                }

                            }
                        }
                        param.setDuration(60);//时长
                        param.setCreate_time(appointmentEntity.getStart_time());
                        param.setEnd_time(appointmentEntity.getEnd_time());
                        mapper.insertSelective(param);
                       /* Map<String, String> mapPush = new HashMap<>();
                        mapPush.put("video_id", param.getId().toString());//视频记录id
                        UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                        mapPush.put("user_id", infoEntity.getUser_id().toString());
                        mapPush.put("avatar", infoEntity.getAvatar());
                        mapPush.put("nick_name", infoEntity.getNick_name());
                        mapPush.put("appointment_id", userAppointmentEntities.get(0).getId().toString());
                        PushMessageParam pushMessageParam = new PushMessageParam();//推送
                        pushMessageParam.setMap(mapPush);
                        pushMessageParam.setPush_type("class");
                        pushMessageParam.setOpera_type("class");
                        pushMessageParam.setType("video"); //视频 个推
                        pushMessageParam.setSend_id(param.getUser_id());
                        pushMessageParam.setTarget_id(param.getOther_id());
                        pushMessageParam.setContent(JSONUtils.obj2Json(mapPush).toString());
                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);*/

                    }
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                } else if (param.getType() != null &&
                        ("live_connection" .equalsIgnoreCase(param.getType()) ||//直播连麦
                                "live_broadcast" .equalsIgnoreCase(param.getType()))) {//直播观看
                    //体验卷
                    if (param.getCard_type() != null && "experience" .equalsIgnoreCase(param.getCard_type())) {
                        CouponListEntity couponListEntity = new CouponListEntity();
                        couponListEntity.setPageSize(1);
                        if (param.getExperience_volume() != null) {//券id非空
                            couponListEntity.setCoupon_code(param.getExperience_volume());//设置码
                        } else {
                            return ResultUtil.error(CouponEum.coupon_30007.getCode(), CouponEum.coupon_30007.getDesc());
                        }
                        List<CouponListEntity> couponListEntities = couponListMapper.queryListPage(couponListEntity);
                        int res = 0;
                        UserCouponEntity userCouponEntity = new UserCouponEntity();
                        if (couponListEntities != null && couponListEntities.size() > 0) {//有券
                            couponListEntity = couponListEntities.get(0);
                            if (couponListEntity.getStatus() == 1) {//如果券状态1被领取
                                userCouponEntity.setCoupon_list_id(couponListEntity.getId());
                                userCouponEntity.setUser_id(param.getUser_id());
                                userCouponEntity.setPageSize(1);
                                List<UserCouponEntity> query = userCouponMapper.query(userCouponEntity);
                                if (query != null && query.size() > 0) {//查询是否是该用户领取
                                    userCouponEntity = query.get(0);
                                    if (userCouponEntity.getStatus() == 1) {//如果状态为可用
                                        res = 1;
                                    } else {
                                        return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
                                    }
                                } else {
                                    return ResultUtil.error(CouponEum.coupon_30003.getCode(), CouponEum.coupon_30003.getDesc());
                                }
                            } else {
                                userCouponEntity.setUser_id(param.getUser_id());
                                userCouponEntity.setType(couponListEntity.getCoupon_type());
                                userCouponEntity.setCoupon_id(couponListEntity.getCoupon_id());
                                userCouponEntity.setCoupon_list_id(couponListEntity.getId());
                                userCouponEntity.setCourse_num(1);
                                userCouponEntity.setIs_teach_paypal(true);
                                res = userCouponMapper.insertSelective(userCouponEntity);//新增劵使用记录
                                couponListEntity.setStatus(1);
                                couponListMapper.updateByPrimaryKeySelective(couponListEntity);
                            }
                            if (res > 0) {
                                ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                                //新增扣除记录
                                classRecordEntity.setCurriculum_num(1);
                                classRecordEntity.setType(param.getCard_type());
                                classRecordEntity.setUser_id(param.getUser_id());
                                classRecordEntity.setGroup_id(param.getOther_id());
                                classRecordEntity.setStatus(3);
                                //查询课程安排
                                MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                                masterAppointmentEntity.setGroup_id(param.getOther_id());
                                masterAppointmentEntity.setStatus(1);
                                masterAppointmentEntity.setPageSize(1);
                                masterAppointmentEntity.setStart_time(new Date());
                                List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentService.queryIsClassListPage(masterAppointmentEntity);
                                if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                                    classRecordEntity.setCurriculum_id(masterAppointmentEntities.get(0).getId());
                                }
                                /*CurriculumRecordEntity curriculumRecordEntity=new CurriculumRecordEntity();
                                curriculumRecordEntity.setGroup_id(param.getOther_id());
                                curriculumRecordEntity.setStatus(2);
                                curriculumRecordEntity.setPageSize(1);
                                List<CurriculumRecordEntity> curriculumRecordEntities=curriculumRecordMapper.queryListPage(curriculumRecordEntity);
                                if(curriculumRecordEntities!=null&&curriculumRecordEntities.size()>0){
                                    classRecordEntity.setCurriculum_id(curriculumRecordEntities.get(0).getId());
                                }else {

                                }*/
                                int ret = classRecordMapper.insertSelective(classRecordEntity);
                                if (ret > 0) {
                                    param.setOrder_id(classRecordEntity.getId());
                                    int ref = mapper.insertSelective(param);
                                    if (ref > 0) {
                                        aBoolean = true;
                                        userCouponEntity.setCourse_num(0);
                                        userCouponEntity.setStatus(3);
                                        userCouponMapper.updateByPrimaryKeySelective(userCouponEntity);
                                    }
                                }
                            }

                        }
                    } else {
                        //查询课程安排
                        MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
                        masterAppointmentEntity.setGroup_id(param.getOther_id());
                        //masterAppointmentEntity.setStatus(3);
                        masterAppointmentEntity.setPageSize(1);
//                        masterAppointmentEntity.setStart_time(new Date());
//                        masterAppointmentEntity.setOffset(BigDecimal.valueOf(8));
                        List<MasterAppointmentEntity> masterAppointmentEntities = masterAppointmentService.queryIsClassListPage(masterAppointmentEntity);
                        if (masterAppointmentEntities != null && masterAppointmentEntities.size() > 0) {
                            ClassRecordEntity classRecordEntity = new ClassRecordEntity();
                            classRecordEntity.setCurriculum_id(masterAppointmentEntities.get(0).getId());
                            classRecordEntity.setUser_id(param.getUser_id());
                            List<ClassRecordEntity> classRecordEntities = classRecordMapper.queryListPage(classRecordEntity);
                            if (classRecordEntities != null && classRecordEntities.size() > 0) {
                                classRecordEntity = classRecordEntities.get(0);
                                VideoDurationEntity videoDurationEntity = new VideoDurationEntity();
                                videoDurationEntity.setOrder_id(classRecordEntities.get(0).getId());
                                videoDurationEntity.setPageSize(1);
                                List<VideoDurationEntity> videoDurationEntities = mapper.queryListPage(videoDurationEntity);
                                if (videoDurationEntities != null && videoDurationEntities.size() > 0) {
                                    videoDurationEntity = videoDurationEntities.get(0);
                                    if (classRecordEntity.getFree_minute() <= 3) {//试听三分钟
                                        //新增试听记录
                                        classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                        int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                        if (req > 0) {
                                            aBoolean = true;
                                            videoDurationEntity.setDuration(videoDurationEntity.getDuration() + 60);
                                            mapper.updateByPrimaryKeySelective(videoDurationEntity);//时长记录
                                        }
                                    } else if (classRecordEntity.getFree_minute() == 4) {
                                        is_audition = false;
                                        //新增试听记录
                                        classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                        classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                    } else {
                                        classRecordEntity.setFree_minute(classRecordEntity.getFree_minute() + 1);
                                        classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);//添加扣费次数
                                        if (classRecordEntity.getType() != null && !classRecordEntity.getType().equals("")) {
                                            videoDurationEntity.setDuration(videoDurationEntity.getDuration() + 60);
                                            int result = mapper.updateByPrimaryKeySelective(videoDurationEntity);//时长记录
                                            if (result > 0) {
                                                if ("curriculum" .equalsIgnoreCase(classRecordEntity.getType())) {
                                                    aBoolean = true;
                                                } else if ("experience" .equalsIgnoreCase(classRecordEntity.getType())) {
                                                    aBoolean = true;
                                                } else if ("minute" .equalsIgnoreCase(classRecordEntity.getType())) {
                                                    UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                                    userClassCardEntity.setUser_id(param.getUser_id());
                                                    userClassCardEntity.setType(classRecordEntity.getType());
                                                    userClassCardEntity.setPageSize(1);
                                                    userClassCardEntity.setStatus(2);
                                                    List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);//查询所指定使用学习卡
                                                    if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                                        userClassCardEntity = userClassCardEntities.get(0);
                                                        if (userClassCardEntity.getMinute_num() > 0) {//学习卡内点卡
                                                            Integer minute_num = userClassCardEntity.getMinute_num() - 1;
                                                            userClassCardEntity.setMinute_num(minute_num);
                                                            int res = 0;
                                                            synchronized (userClassCardEntity) {
                                                                //扣除点数
                                                                res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                            }
                                                            if (res > 0) {
                                                                //新增扣除记录
                                                                classRecordEntity.setMinute_num(classRecordEntity.getMinute_num() + 1);
                                                                int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                                if (req > 0) {
                                                                    aBoolean = true;
                                                                }
                                                            }
                                                        } else {
                                                            return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            UserClassCardEntity userClassCardEntity = new UserClassCardEntity();
                                            userClassCardEntity.setUser_id(param.getUser_id());
                                            userClassCardEntity.setStatus(2);
                                            userClassCardEntity.setPageSize(1);
                                            List<UserClassCardEntity> classCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);
                                            if (classCardEntities == null || classCardEntities.size() == 0) {
                                                UserCouponEntity userCouponEntity = new UserCouponEntity();
                                                userCouponEntity.setUser_id(param.getUser_id());
                                                userCouponEntity.setAppointment_id(param.getAppointment_id());
                                                List<UserCouponEntity> userCouponEntities = userCouponMapper.queryListPage(userCouponEntity);
                                                if (userCouponEntities != null && userCouponEntities.size() > 0) {
                                                    map.put("is_audition", is_audition);
                                                    map.put("is_video", aBoolean);
                                                    map.put("id", param.getId());
                                                    return ResultUtil.success(map);
                                                }
                                                return ResultUtil.error(MasterEum.master_40006.getCode(), MasterEum.master_40006.getDesc());
                                            }
                                            userClassCardEntity.setDefault_use(true);
                                            userClassCardEntity.setStatus(2);
                                            userClassCardEntity.setPageSize(1);
                                            List<UserClassCardEntity> userClassCardEntities = userClassCardMapper.queryListPage(userClassCardEntity);//查询所指定使用学习卡
                                            if (userClassCardEntities != null && userClassCardEntities.size() > 0) {
                                                userClassCardEntity = userClassCardEntities.get(0);
                                                videoDurationEntity.setDuration(videoDurationEntity.getDuration() + 60);
                                                int result = mapper.updateByPrimaryKeySelective(videoDurationEntity);//时长记录
                                                if (result > 0) {
                                                    if ("curriculum" .equalsIgnoreCase(userClassCardEntity.getType())) {
                                                        if (userClassCardEntity.getCurriculum_num() > 0) {//学习卡内次卡
                                                            Integer curriculum_num = userClassCardEntity.getCurriculum_num() - 1;
                                                            userClassCardEntity.setCurriculum_num(curriculum_num);
                                                            int res = 0;
                                                            synchronized (userClassCardEntity) {
                                                                //扣除点数
                                                                res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                            }
                                                            if (res > 0) {
                                                                //新增扣除记录
                                                                classRecordEntity.setType(userClassCardEntity.getType());
                                                                classRecordEntity.setCurriculum_num(1);
                                                                int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                                if (req > 0) {
                                                                    aBoolean = true;
                                                                }
                                                            }
                                                        } else {
                                                            return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                        }
                                                    } else if ("minute" .equalsIgnoreCase(userClassCardEntity.getType())) {
                                                        if (userClassCardEntity.getMinute_num() > 0) {//学习卡内点卡
                                                            Integer minute_num = userClassCardEntity.getMinute_num() - 1;
                                                            userClassCardEntity.setMinute_num(minute_num);
                                                            int res = 0;
                                                            synchronized (userClassCardEntity) {
                                                                //扣除点数
                                                                res = userClassCardMapper.updateByPrimaryKeySelective(userClassCardEntity);
                                                            }
                                                            if (res > 0) {
                                                                //新增扣除记录
                                                                classRecordEntity.setType(userClassCardEntity.getType());
                                                                classRecordEntity.setMinute_num(classRecordEntity.getMinute_num() + 1);
                                                                int req = classRecordMapper.updateByPrimaryKeySelective(classRecordEntity);
                                                                if (req > 0) {
                                                                    aBoolean = true;
                                                                }
                                                            }
                                                        } else {
                                                            return ResultUtil.error(MasterEum.master_40004.getCode(), MasterEum.master_40004.getDesc());
                                                        }
                                                    }
                                                }
                                            } else {
                                                return ResultUtil.error(MasterEum.master_40012.getCode(), MasterEum.master_40012.getDesc());
                                            }
                                        }
                                    }
                                }
                                param.setId(videoDurationEntity.getId());
                            } else {
                                //新增扣除记录
                                classRecordEntity.setMinute_num(1);
                                classRecordEntity.setGroup_id(param.getOther_id());
                                classRecordEntity.setCurriculum_id(masterAppointmentEntities.get(0).getId());
                                int ret = classRecordMapper.insertSelective(classRecordEntity);
                                if (ret > 0) {
                                    param.setOrder_id(classRecordEntity.getId());
                                    param.setDuration(60);
                                    int ref = mapper.insertSelective(param);
                                    if (ref > 0) {
                                        aBoolean = true;
                                    }
                                }
                            }
                        } else {
                            return ResultUtil.error(MasterEum.master_40009.getCode(), MasterEum.master_40009.getDesc());
                        }
                    }
                    map.put("is_audition", is_audition);
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                } else {
                    UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                    SettledAnchorEntity settledAnchorEntity = new SettledAnchorEntity();
                    settledAnchorEntity.setUser_id(param.getUser_id());
                    settledAnchorEntity.setStatus(1);
                    settledAnchorEntity.setPageSize(1);
                    List<SettledAnchorEntity> settledAnchorEntities = settledAnchorService.queryListPage(settledAnchorEntity, request, response);
                    if (settledAnchorEntities == null || settledAnchorEntities.size() == 0) {
                        UserPriceEntity userPriceEntity = new UserPriceEntity();
                        userPriceEntity.setType(param.getType());
                        userPriceEntity.setStatus(1);
                        userPriceEntity.setPageSize(1);
                        System.out.println(param.getType());
                        List<UserPriceEntity> userPriceEntities = priceService.queryListPage(userPriceEntity, request, response);
                        if (userPriceEntities != null && userPriceEntities.size() > 0) {
                            if (infoEntity.getCat_coin().compareTo(userPriceEntities.get(0).getPrice()) >= 0) {
                                infoEntity.setCat_coin(userPriceEntities.get(0).getPrice());
                                int res = 0;
                                synchronized (infoEntity) {
                                    //扣除猫币
                                    res = userInfoMapper.updateReduceCatCoin(infoEntity);
                                }
                                if (res > 0) {
                                    BigDecimal bigDecimal = userPriceEntities.get(0).getPrice();
                                    if (param.getOrder_id() != null && param.getOrder_id() != 0) {
                                        UserOrderEntity entity1 = userOrderService.findById(param.getOrder_id(), request, response);
                                        bigDecimal = bigDecimal.add(entity1.getPay_count());
                                        entity1.setPay_count(bigDecimal);
                                        orderMapper.updateByPrimaryKeySelective(entity1);
                                    } else {
                                        UserOrderEntity orderEntity = new UserOrderEntity();
                                        orderEntity.setPay_count(bigDecimal);
                                        orderEntity.setType("video");
                                        orderEntity.setSource(param.getType());
                                        orderEntity.setUser_id(param.getUser_id());
                                        orderEntity.setType_id(param.getOther_id());
                                        orderEntity.setOrder_no(CommonUtils.generateOrderKey());
                                        orderMapper.insertSelective(orderEntity);
                                        param.setOrder_id(orderEntity.getId());
                                    }
                                    int ret = mapper.insertSelective(param);
                                    if (ret > 0) {
                                        aBoolean = true;
                                    }
                                }

                            }
                        }
                    } else {
                        param.setDuration(60);//时长
                        int ret = mapper.insertSelective(param);
                        if (ret > 0) {
                            aBoolean = true;
                        }

                    }
                    if ("video" .equalsIgnoreCase(param.getType())) {
                        Map<String, String> mapPush = new HashMap<>();
                        mapPush.put("video_id", param.getId().toString());
                        mapPush.put("user_id", infoEntity.getUser_id().toString());
                        mapPush.put("avatar", infoEntity.getAvatar());
                        mapPush.put("nick_name", infoEntity.getNick_name());
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setMap(mapPush);
                        pushMessageParam.setPush_type("chat");
                        pushMessageParam.setOpera_type("chat");
                        pushMessageParam.setType("video");
                        pushMessageParam.setSend_id(param.getUser_id());
                        pushMessageParam.setTarget_id(param.getOther_id());
                        pushMessageParam.setContent(JSONUtils.obj2Json(mapPush).toString());
                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                    }
                    map.put("is_video", aBoolean);
                    map.put("id", param.getId());
                }
            }
            updateGroupDuration(param.getOther_id(), param.getType());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl  insertVideoOrder");
        }
        return ResultUtil.success(map);
    }

    public void updateGroupDuration(Long group_id, String type) {
        if (type != null && !type.equals("")) {
            if ("live_connection" .equalsIgnoreCase(type) || "live_broadcast" .equalsIgnoreCase(type) ||
                    "video_connection" .equalsIgnoreCase(type) || "video_broadcast" .equalsIgnoreCase(type)) {
                Map<String, Object> map = new HashMap<>();
                map.put("group_id", group_id);
                map.put("type", type);
                rabbitTemplate.convertAndSend("updateGroupDuration", map);
            }
        }
    }

    @Override
    public ResultParam pushVideo(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = "";
            if (param.getType() != null && "class" .equals(param.getType())) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setUser_id(param.getUser_id());
                masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                if (StringUtil.isNotEmpty(masterInfoEntity.getFull_name())) {
                    name = userInfoMapper.selectByPrimaryKey(param.getUser_id()).getNick_name();
                } else {
                    name = masterInfoEntity.getFull_name();
                }
            } else {
                name = userInfoMapper.selectByPrimaryKey(param.getUser_id()).getNick_name();
            }
            super.insert(param, request, response);
            PushMessageParam pushMessageParam = new PushMessageParam();
            pushMessageParam.setPush_type("videoChat");
            pushMessageParam.setOpera_type("videoChat");
            pushMessageParam.setType_id(param.getId());
            pushMessageParam.setSend_id(param.getUser_id());
            pushMessageParam.setTarget_id(param.getOther_id());
            if (param.getType() != null && "class" .equals(param.getType())) {
                pushMessageParam.setContent(name + "老师邀请你上课啦");
                pushMessageParam.setTitle("上课邀请");
                pushMessageParam.setType("class");
            } else {
                pushMessageParam.setContent(name + "邀请你通话啦");
                pushMessageParam.setTitle("通话邀请");
                pushMessageParam.setType("video");
            }
            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl pushVideo");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam pushVideoIos(VideoDurationEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = "";
            if (param.getType() != null && "class" .equals(param.getType())) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setUser_id(param.getUser_id());
                masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                if (StringUtil.isNotEmpty(masterInfoEntity.getFull_name())) {
                    name = userInfoMapper.selectByPrimaryKey(param.getUser_id()).getNick_name();
                } else {
                    name = masterInfoEntity.getFull_name();
                }
            } else {
                name = userInfoMapper.selectByPrimaryKey(param.getUser_id()).getNick_name();
            }
            super.insert(param, request, response);
            Map<String, String> mapPush = new HashMap<>();
            PushMessageParam pushMessageParam = new PushMessageParam();
            pushMessageParam.setMap(mapPush);
            pushMessageParam.setPush_type("videoChat");
            pushMessageParam.setOpera_type("videoChat");
            pushMessageParam.setType("video");
            pushMessageParam.setType_id(param.getId());
            pushMessageParam.setSend_id(param.getUser_id());
            pushMessageParam.setTarget_id(param.getOther_id());
            if (param.getType() != null && "class" .equals(param.getType())) {
                pushMessageParam.setContent(name + "老师邀请你上课啦");
                pushMessageParam.setTitle("上课邀请");
            } else {
                pushMessageParam.setContent(name + "邀请你通话啦");
                pushMessageParam.setTitle("通话邀请");
            }
            rabbitTemplate.convertAndSend("videoPush", pushMessageParam);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("VideoDurationServiceImpl pushVideoIos");
        }
        return ResultUtil.success();
    }
}