package com.huihe.eg.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.thread.ThreadPool;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.NewsApiService;
import com.huihe.eg.cloud.feign.UserApiService;
import com.huihe.eg.comm.MasterEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.message.model.GroupRecordingEntity;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.model.MessageJoinGroupEntity;
import com.huihe.eg.message.mybatis.dao.GroupRecordingMapper;
import com.huihe.eg.message.mybatis.dao.MessageGroupFlowMapper;
import com.huihe.eg.message.mybatis.dao.MessageGroupMapper;
import com.huihe.eg.message.mybatis.dao.MessageJoinGroupMapper;
import com.huihe.eg.message.service.dao.GroupRecordingService;
import com.huihe.eg.message.service.dao.MessageGroupFlowService;
import com.huihe.eg.message.service.dao.MessageGroupService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.message.service.dao.callback.MessageCallbackFactoryBean;
import com.huihe.eg.message.service.impl.Thread.EstablishRecordThread;
import com.huihe.eg.message.service.impl.Thread.MixedFlowThread;

import io.swagger.models.auth.In;
import org.apache.catalina.Server;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import com.huihe.eg.message.service.impl.tim.TimConfig;
import com.tencentyun.TLSSigAPIv2;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageGroupServiceImpl extends BaseFrameworkServiceImpl<MessageGroupEntity, Long> implements MessageGroupService {

    @Resource
    private MessageGroupMapper mapper;
    @Resource
    private TimConfig timConfig;
    @Resource
    private RedisService redisService;
    @Resource
    private UserApiService userApiService;
    @Resource
    private MessageJoinGroupMapper messageJoinGroupMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MessageCallbackFactoryBean bean;
    @Resource
    private MessageGroupFlowService messageGroupFlowService;
    @Resource
    private GroupRecordingMapper groupRecordingMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @WebLog(description = "?????????")
    public ResultParam insert(MessageGroupEntity messageGroupEntity, HttpServletRequest request, HttpServletResponse response) {
        init();
        try {
            System.out.println(JSONUtils.obj2Map(messageGroupEntity, null).toString());
            //??????
            Map<String, Object> map = new HashMap<>();
            //????????????????????????  '??????  voice_chat????????????  online_video????????????   online_voice????????????   realtime_video???????????? realtime_voice????????????,life_live????????????,recreation_live????????????',
            if (!"realtime_video".equalsIgnoreCase(messageGroupEntity.getType()) && !"realtime_voice".equalsIgnoreCase(messageGroupEntity.getType())) {
                map.put("Owner_Account", messageGroupEntity.getOwner_id().toString());//?????????id
            }
            if (messageGroupEntity.getOwner_id() != null && "online".equalsIgnoreCase(messageGroupEntity.getType())) {//????????????
                if (!userApiService.queryIsEstablishGroupClass(messageGroupEntity.getOwner_id())) {
                    return ResultUtil.error(UserEum.user_10030.getCode(), UserEum.user_10030.getDesc());
                }
            }
            map.put("Type", "Public");//??????
            messageGroupEntity.setGroup_type("Public");
            if (messageGroupEntity.getId() != null && messageGroupEntity.getId() != 0) {
                map.put("GroupId", messageGroupEntity.getGroup_id());
            }
            map.put("Name", messageGroupEntity.getGroup_name());
            map.put("Introduction", messageGroupEntity.getIntroduction());
            map.put("Notification", messageGroupEntity.getNotification());
            map.put("FaceUrl", messageGroupEntity.getFaceUrl());
            map.put("ApplyJoinOption", "FreeAccess");
            map.put("GroupId", String.valueOf(System.currentTimeMillis() / 1000));
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());//????????????api
            String adminSign = tlsSigAPIv2.genSig(timConfig.getAdminName(), timConfig.getExpire());//TIM??????
            UUID uuid = UUID.randomUUID();
            String url = timConfig.getUrl() + "/group_open_http_svc/create_group?sdkappid=" + timConfig.getSdkAppId() + "&identifier=" +
                    timConfig.getAdminName() + "&usersig=" + adminSign + "&random=" + uuid + "&contenttype=json";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);//map??????json
            url = url.replaceAll("\r|\n", "");//??????url???\r\n
            String str = HttpsClientRequest.post(url, json.toString(), null, null);//????????????????????? api
            Map<String, Object> resultMap = JSONUtils.obj2Map(str, null);
            logger.info(JSONUtils.obj2Json(resultMap).toString());
            //?????????????????? ??????ok
            if (resultMap != null && resultMap.containsKey("ActionStatus") && "OK".equals(resultMap.get("ActionStatus"))) {//??????
                Map<String, Object> mapResult = new HashMap<>();
                messageGroupEntity.setGroup_id(resultMap.get("GroupId").toString());//?????? ???????????????groupid
                if (messageGroupEntity.getId() == null || messageGroupEntity.getId() == 0) {//?????????id
                    int ret = mapper.insertSelective(messageGroupEntity);//???????????????
                    if (ret > 0) {
                        MessageJoinGroupEntity entity = new MessageJoinGroupEntity();
                        entity.setGroup_id(messageGroupEntity.getId());
                        entity.setUser_id(messageGroupEntity.getOwner_id());
                        //  ????????????Apply?????????????????????Invited??????????????????',
                        entity.setJoin_type("Apply");
                        ret = messageJoinGroupMapper.insertSelective(entity);//??????????????????
                        if (ret > 0) {
                            //mapResult.put("push_addr",push_addr);
                            mapResult.put("groupId", map.get("GroupId"));
                            mapResult.put("id", messageGroupEntity.getId());
                            mapResult.put("result", "????????????");
                            return ResultUtil.success(mapResult);
                        } else {
                            return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
                        }
                    } else {
                        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
                    }
                } else {
                    mapper.updateByPrimaryKeySelective(messageGroupEntity);
                }
            } else {
                return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl   insert");
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }

    @Override
    //@WebLog(description = "?????????????????????")
    public List<MessageGroupEntity> queryListPage(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageGroupEntity> entities = null;
        try {
            if (param.getIs_life() != null && param.getIs_life()) {
                //????????????????????????
                entities = mapper.queryLifeListPage(param);
            } else {
                entities = mapper.queryListPage(param);
            }
            for (MessageGroupEntity entity : entities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getOwner_id() + "userinfo")));//??????userinfo
//                /*String class_info=userApiService.queryNewAppointment(entity.getId());
//                String class_infos=userApiService.queryNewAppointments(entity.getId());*/
               /* map.put("class_info",JSONUtils.obj2Json(userApiService.queryNewAppointment(entity.getId())));
                map.put("class_infos",JSONUtils.obj2Json(userApiService.queryNewAppointments(entity.getId())));*/
                map.put("comment_infos", new ArrayList<>());//????????????
                map.put("appointmentCount", userApiService.queryAppointmentCount(entity.getId()));//????????????
                String result = userApiService.queryIsShow(entity.getOwner_id(), entity.getId());//?????????????????????
                Map<String, Object> stringObjectMap = new HashMap<>();
                if (StringUtil.isNotEmpty(result)) {
                    stringObjectMap = JSONUtils.json2Map(result);
                }
                map.put("appointmentInfo", stringObjectMap == null ? Maps.newHashMap() : stringObjectMap);//????????????
                entity.setMap(map);
                //????????????
                MessageJoinGroupEntity messageJoinGroupEntity = new MessageJoinGroupEntity();
                messageJoinGroupEntity.setGroup_id(entity.getId());
                messageJoinGroupEntity.setStatus(1);
                Integer integer = messageJoinGroupMapper.queryListPageCount(messageJoinGroupEntity);
                entity.setOnline_num(integer);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl    queryListPage");
        }
        return entities;
    }

    @Override
    public List<MessageGroupEntity> query(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MessageGroupEntity> entities = null;
        try {
            entities = mapper.queryListPage(param);
            for (MessageGroupEntity entity : entities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getOwner_id() + "userinfo")));
                entity.setMap(map);
                MessageJoinGroupEntity messageJoinGroupEntity = new MessageJoinGroupEntity();
                messageJoinGroupEntity.setGroup_id(entity.getId());
                messageJoinGroupEntity.setStatus(1);
                Integer integer = messageJoinGroupMapper.queryListPageCount(messageJoinGroupEntity);
                entity.setOnline_num(integer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl    queryListPage");
        }
        return entities;
    }

    @Override
    public ResultParam update(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
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
            logger.info("MessageGroupServiceImpl   update");
        }
        return ResultUtil.success();
    }

    @Override
    public void updateGroupDuration(Long group_id, String type) throws Exception {
        try {
            MessageGroupEntity messageGroupEntity = new MessageGroupEntity();
            messageGroupEntity.setId(group_id);
            if (("live_connection".equalsIgnoreCase(type)) || ("video_connection".equalsIgnoreCase(type))) {
                synchronized (messageGroupEntity) {
                    mapper.updateLiveDuration(messageGroupEntity);
                }
            } else if (("live_broadcast".equalsIgnoreCase(type)) || ("video_broadcast".equalsIgnoreCase(type))) {
                synchronized (messageGroupEntity) {
                    mapper.updateWatchDuration(messageGroupEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @Description: ??????????????????
     * @author zwx
     * @Date : 2019???9???5???10:04:09
     */
    @Override
    public synchronized ResultParam updateClassBeginsStatus(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            MessageGroupEntity messageGroupEntity = super.findById(param.getId(), request, response);
            if (messageGroupEntity.getOwner_id() == 36) {
                mapper.updateByPrimaryKeySelective(param);
                return ResultUtil.success();
            }
            if (param.getStatus() != null && param.getStatus() == 1) {//????????????
                if (messageGroupEntity.getType() != null) {//???????????????
                    if ("online_video".equalsIgnoreCase(messageGroupEntity.getType()) ||//???????????? ?????? ??????
                            "online_voice".equalsIgnoreCase(messageGroupEntity.getType()) || "online".equalsIgnoreCase(messageGroupEntity.getType())) {
                        //???????????????????????????????????????
                        String result = userApiService.queryIsShow(messageGroupEntity.getOwner_id(), messageGroupEntity.getId());
                        if (StringUtil.isNotEmpty(result)) {
                            Map<String, Object> stringObjectMap = JSONUtils.json2Map(result);
                            int ret = mapper.updateByPrimaryKeySelective(param);//??????????????????
                            if (ret > 0) {
                                MessageJoinGroupEntity messageJoinGroupEntity = new MessageJoinGroupEntity();
                                messageJoinGroupEntity.setGroup_id(messageGroupEntity.getId());//????????????id
                                messageJoinGroupEntity.setStatus(4);//??????
                                List<MessageJoinGroupEntity> list = messageJoinGroupMapper.query(messageJoinGroupEntity);//??????????????????
                                for (MessageJoinGroupEntity entity : list) {//???????????????
                                    if (messageGroupEntity.getOwner_id().intValue() != entity.getUser_id().intValue()) {
                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                        pushMessageParam.setTarget_id(entity.getUser_id());
                                        pushMessageParam.setPush_type("live_class_start");
                                        Map<String, String> mapPush = new HashMap<>();
                                        mapPush.put("type", param.getType());
                                        mapPush.put("id", param.getId().toString());
                                        mapPush.put("group_id", param.getGroup_id());
                                        pushMessageParam.setOpera_type("live_class_start");
                                        pushMessageParam.setMap(mapPush);
                                        pushMessageParam.setContent("????????????????????????????????????");
                                        pushMessageParam.setType("groupClassNotice");
                                        pushMessageParam.setType_id(param.getId());
                                        //????????????
                                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend(DelayedConfig.EXCHANGE_NAME, DelayedConfig.QUEUE_NAME, pushMessageParam, new MessagePostProcessor() {//??????
                                            @Override
                                            public Message postProcessMessage(Message message) throws AmqpException {
                                                message.getMessageProperties().setHeader("x-delay", 180000);//??????3??????
                                                return message;
                                            }
                                        });
                                    }
                                }
                                //????????????????????????
                                //System.out.println(stringObjectMap.get("start_time").toString());
                                GroupRecordingEntity groupRecordingEntity = new GroupRecordingEntity();//???????????????????????????
                                groupRecordingEntity.setGroup_id(messageGroupEntity.getId());//????????????id
                                groupRecordingEntity.setUser_id(messageGroupEntity.getOwner_id());//????????????id
                                groupRecordingEntity.setAppointment_id(Long.parseLong(stringObjectMap.get("id").toString()));//????????????id
                                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//????????????
                                Date start_time = simpleFormat.parse(stringObjectMap.get("start_time").toString());//?????????????????????
//                                BigDecimal offset = BigDecimal.valueOf(Long.parseLong(stringObjectMap.get("offset").toString()));//??????
                                BigDecimal offset = new BigDecimal(stringObjectMap.get("offset").toString());//??????
                                BigDecimal bigDecimal = offset.subtract(new BigDecimal(8)).multiply(new BigDecimal(3600000));//60??????
                                long start_longs = start_time.getTime() - bigDecimal.longValue();//??????????????????
                                groupRecordingEntity.setStart_time(new Date(start_longs));//??????????????????
                                Date end_time = simpleFormat.parse(stringObjectMap.get("end_time").toString());//????????????
                                long end_longs = end_time.getTime() - bigDecimal.longValue();//??????????????????
                                groupRecordingEntity.setEnd_time(new Date(end_longs));//??????????????? ??????????????????
                                groupRecordingEntity.setStream_id("1400255047_" + messageGroupEntity.getOwner_id());//?????????id
                                EstablishRecordThread messageServive = new EstablishRecordThread();//new ????????????
                                messageServive.init(groupRecordingEntity, groupRecordingEntity.getStream_id(), groupRecordingMapper);//?????????
                                ThreadPool pool = ThreadPool.getInstance();//?????????????????????
                                pool.execute(messageServive);//???????????? ????????????
                            }
                            return ResultUtil.success(stringObjectMap);
                        } else {
                            return ResultUtil.error(MasterEum.master_40010.getCode(), MasterEum.master_40010.getDesc());
                        }
                    } else {
                        return super.update(param, request, response);
                    }
                }
            } else {
                int ret = mapper.updateByPrimaryKeySelective(param);
                if (ret > 0) {
                    /*MixedFlowThread messageServive = new MixedFlowThread();
                    messageServive.init(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"app",messageGroupFlowService);
                    ThreadPool pool = ThreadPool.getInstance();
                    pool.execute(messageServive);*/
                    /*MixedFlowThread mixedFlowThread = new MixedFlowThread();
                    mixedFlowThread.init(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"ipad","Cancel",messageGroupFlowService);
                    pool.execute(mixedFlowThread);*/
                    userApiService.queryIsShowClose(messageGroupEntity.getOwner_id(), messageGroupEntity.getId());
                    return ResultUtil.success();
                }
                    /*bean.mixedFlow(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"app","Cancel");//??????????????????
                    bean.mixedFlow(messageGroupEntity.getGroup_id(),messageGroupEntity.getType(),"ipad","Cancel");*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageJoinGroupServiceImpl    queryListPage");
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    @Override
    @Transactional
    public synchronized ResultParam updateLivingBeginsStatus(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse response) {
        //// TODO: 2021/2/25
        try {

            String result = userApiService.queryIsLiveStream(param.getOwner_id(), param.getId());
            if (result != null) {
                Map<String, Object> stringObjectMap = JSONUtils.json2Map(result);
                if (!"2".equals(stringObjectMap.get("status"))) {
                    //????????????????????????
                    //System.out.println(stringObjectMap.get("start_time").toString());
                    GroupRecordingEntity groupRecordingEntity = new GroupRecordingEntity();//???????????????????????????
//                            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//????????????
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.HOUR_OF_DAY, 2);
                    Date start_time = c.getTime();//?????????????????????
                    Date end_time = c.getTime();
//                            Date start_time = simpleFormat.parse(stringObjectMap.get("start_time").toString());//????????????
//                                BigDecimal offset = BigDecimal.valueOf(Long.parseLong(stringObjectMap.get("offset").toString()));//??????
//                            BigDecimal offset = new BigDecimal(stringObjectMap.get("offset").toString());//??????
                    BigDecimal offset = new BigDecimal("8");//??????
                    BigDecimal bigDecimal = offset.subtract(new BigDecimal(8)).multiply(new BigDecimal(3600000));//60??????
                    long start_longs = start_time.getTime() - bigDecimal.longValue();//??????????????????
                    groupRecordingEntity.setStart_time(new Date(start_longs));//??????????????????
//                            Date end_time = simpleFormat.parse(stringObjectMap.get("end_time").toString());//????????????
                    long end_longs = end_time.getTime() - bigDecimal.longValue();//??????????????????
                    groupRecordingEntity.setEnd_time(new Date(end_longs));//??????????????? ??????????????????
                    groupRecordingEntity.setStream_id(stringObjectMap.get("room_id").toString());//?????????id
                    groupRecordingEntity.setIs_teach_paypal(true);
                    groupRecordingEntity.setAppointment_id(Long.parseLong(stringObjectMap.get("id").toString()));
                    groupRecordingEntity.setUser_id(Long.parseLong(stringObjectMap.get("master_id").toString()));
                    groupRecordingEntity.setStream_id(stringObjectMap.get("room_id").toString());
                    groupRecordingMapper.insertSelective(groupRecordingEntity);
                    EstablishRecordThread messageServive = new EstablishRecordThread();//new ????????????
                    messageServive.init(groupRecordingEntity, groupRecordingEntity.getStream_id(), groupRecordingMapper);//?????????
                    ThreadPool pool = ThreadPool.getInstance();//?????????????????????
                    pool.execute(messageServive);//???????????? ????????????
                    return ResultUtil.success(stringObjectMap);
                }

            } else {
                return ResultUtil.error(MasterEum.master_40029.getCode(), MasterEum.master_40029.getDesc());
            }
            /*
            String s = userApiService.queryIsLiveStreamClose(param.getId());
            if ("success".equalsIgnoreCase(s)) {

                return ResultUtil.success();
            }

             */
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_3.getCode(), ResultEnum.result_3.getDesc());
    }

    //????????????????????????
    @Override
    public List<MessageGroupEntity> queryMyListPage(MessageGroupEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        List<MessageGroupEntity> entities = null;
        try {
            entities = mapper.queryMyListPage(param);
            for (MessageGroupEntity entity : entities) {
                Map<String, Object> map = new HashMap<>();
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getOwner_id() + "userinfo")));
                entity.setMap(map);
                MessageJoinGroupEntity messageJoinGroupEntity = new MessageJoinGroupEntity();
                messageJoinGroupEntity.setGroup_id(entity.getId());
                messageJoinGroupEntity.setStatus(1);
                Integer integer = messageJoinGroupMapper.queryListPageCount(messageJoinGroupEntity);
                entity.setOnline_num(integer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl    queryListPage");
        }
        return entities;
    }

    //??????????????????????????????
    @Override
    public Integer queryMyClassCount(MessageGroupEntity param, HttpServletRequest request, HttpServletResponse
            response) {
        Integer integer = 0;
        try {
            integer = mapper.queryMyClassCount(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("MessageGroupServiceImpl    queryListPage");
        }
        return integer;
    }

    @Override
    public Map<String, Object> queryClassCount(MessageGroupEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer LanguageClassCount = queryListPageCount(param, request, response);
            map.put(param.getLanguage() + "ClassCount", LanguageClassCount);

            param.setLanguage(null);
            Integer classTotal = queryListPageCount(param, request, response);
            map.put("classTotal", classTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCourseCount(MessageGroupEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();

        try {
            Integer dayCount = mapper.queryDayCount(param);
            map.put("dayCount", dayCount);//?????????

            Integer weekCount = mapper.queryWeekCount(param);
            map.put("weekCount", weekCount);//?????????

            Integer monthCount = mapper.queryMonthCount(param);
            map.put("monthCount", monthCount);//?????????

            Integer CountTotal = mapper.queryCountTotal(param);
            map.put("CountTotal", CountTotal);//??????


            Integer havingClassCount = mapper.queryListPageCount(param);
            map.put(param.getLanguage() + "HavingClassCount", havingClassCount);//???????????????????????????

            Integer endClassCount = mapper.queryEndClassCount(param);
            map.put(param.getLanguage() + "EndClassCount", endClassCount);//????????????????????????

            Integer soonClassCount = mapper.querySoonCount(param);
            map.put(param.getLanguage() + "SoonClassCount", soonClassCount);//????????????????????????

            param.setStatus(null);//????????????
            Integer englishClassCount = mapper.queryDayCount(param);
            map.put(param.getLanguage() + "ClassCount", englishClassCount);//?????????????????????

            param.setType("online_video");
            Integer englishVideoCount = mapper.queryDayCount(param);
            map.put(param.getLanguage() + "VideoCount", englishVideoCount);//???????????????

            param.setType("online_voice");
            Integer englishVoiceCount = mapper.queryDayCount(param);
            map.put(param.getLanguage() + "VoiceCount", englishVoiceCount);//???????????????

            param.setType("");//todo
            Integer englishCount = mapper.queryDayCount(param);
            map.put(param.getLanguage() + "", englishCount);//??????????????????


            param.setLanguage("French");//????????????
            Integer FrenchClassCount = mapper.queryListPageCount(param);
            map.put(param.getLanguage() + "ClassCount", FrenchClassCount);//??????????????????

            param.setLanguage("Japanese");//????????????
            Integer JapaneseClassCount = mapper.queryListPageCount(param);
            map.put(param.getLanguage() + "ClassCount", JapaneseClassCount);//??????????????????

            map.put("otherClassCount", dayCount - FrenchClassCount - JapaneseClassCount - englishClassCount);//??????????????????


            param.setLanguage(null);
            Integer havingClassCountAll = mapper.queryListPageCount(param);
            map.put("havingClassCountAll", havingClassCountAll);//?????????????????????

            Integer endClassCountAll = mapper.queryEndClassCount(param);
            map.put("endClassCountAll", endClassCountAll);//??????????????????

            Integer soonClassCountAll = mapper.querySoonCount(param);
            map.put("soonClassCountAll", soonClassCountAll);//??????????????????


            param.setLanguage(null);
            param.setType("online_video");
            Integer videoCount = mapper.queryDayCount(param);
            map.put("videoCount", videoCount);//?????????

            param.setType("online_voice");
            Integer voiceCount = mapper.queryDayCount(param);
            map.put("voiceCount", voiceCount);//?????????

            param.setType("");//todo
            Integer Count = mapper.queryDayCount(param);
            map.put("", Count);//????????????
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<MessageGroupEntity> queryByMessage(MessageGroupEntity param, HttpServletRequest
            request, HttpServletResponse response) {
        List<MessageGroupEntity> list = null;
        try {
            list = new ArrayList<>();
            if (StringUtil.isEmpty(param.getFull_name())) {
                list = mapper.queryByMessage(param);
                setAppointmentInfo(list);
            } else {
                String ip = request.getHeader("x-forwarded-for");
//                HashMap map = this.restTemplate.getForObject("http://localhost:8768/eg-api/user/userInfo/queryByNickName?nick_name="+param.getOperator_account(), HashMap.class);
                HashMap<String, ArrayList> map = this.restTemplate.getForObject("http://EG-USER/masterInfo/queryByFullName?full_name=" + param.getFull_name(), HashMap.class);
                HashMap<String, ArrayList> map1 = this.restTemplate.getForObject("http://EG-USER/user/queryByLoginName?login_name=" + param.getLogin_name(), HashMap.class);
                ArrayList<Long> list1 = map.get("userIds"); //??????list
                ArrayList<Long> list2 = map1.get("userIds"); //??????list
                if (list1 != null) {
                    param.setIds(list1);
                }
                if (list2 != null) {
                    param.setLoginIds(list2);
                }
                list = mapper.queryByMessage(param);
                if (list != null && list.size() > 0) {
                    setAppointmentInfo(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private void setAppointmentInfo(List<MessageGroupEntity> list) {
        for (MessageGroupEntity entity : list) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("masterInfo", JSONUtils.obj2Json(redisService.getStr(entity.getOwner_id() + "masterInfo")));//??????masterInfo
            stringObjectHashMap.put("comment_infos", new ArrayList<>());//????????????
            stringObjectHashMap.put("appointmentCount", userApiService.queryAppointmentCount(entity.getId()));//????????????
            String result = userApiService.queryIsShow(entity.getOwner_id(), entity.getId());//?????????????????????
            Map<String, Object> stringObjectMap = new HashMap<>();
            if (StringUtil.isNotEmpty(result)) {
                stringObjectMap = JSONUtils.json2Map(result);
            }
            stringObjectHashMap.put("appointmentInfo", stringObjectMap);//????????????
            entity.setMap(stringObjectHashMap);
            //????????????
            MessageJoinGroupEntity messageJoinGroupEntity = new MessageJoinGroupEntity();
            messageJoinGroupEntity.setGroup_id(entity.getId());
            messageJoinGroupEntity.setStatus(1);
            Integer integer = messageJoinGroupMapper.queryListPageCount(messageJoinGroupEntity);
            entity.setOnline_num(integer);
        }
    }

    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}