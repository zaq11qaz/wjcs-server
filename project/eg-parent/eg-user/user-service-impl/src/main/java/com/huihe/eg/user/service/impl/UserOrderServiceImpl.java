package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.model.PageInfo;
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
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.dao.UserOrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.UserPriceService;
import com.huihe.eg.user.service.dao.VideoDurationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserOrderServiceImpl extends BaseFrameworkServiceImpl<UserOrderEntity, Long> implements UserOrderService {

    @Resource
    private UserOrderMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Resource
    private UserGiftMapper userGiftMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserGoldTypeMapper userGoldTypeMapper;
    @Resource
    private OrderTypeLogoMapper orderTypeLogoMapper;
    @Resource
    private MessageApiService messageApiService;
    @Resource
    private VideoDurationService videoDurationService;
    @Resource
    private UserPriceService priceService;
    @Resource
    private UserAppointmentMapper userAppointmentMapper;
    @Resource
    private MasterAppointmentMapper masterAppointmentMapper;


    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional
    public synchronized ResultParam insert(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param.getIs_group() != null && param.getIs_group()) {
                String user_ids = messageApiService.queryGroupUserInfos(param.getPay_id());//查询群内成员
                if (StringUtil.isNotEmpty(user_ids)) {
                    List<Long> longList = JSONUtils.json2List(user_ids, Long.class);
                    for (Long aLong : longList) {
                        param.setPayment_id(aLong);
                        param.setOrder_no(CommonUtils.generateOrderKey());
                        param.setId(null);
                        int ret = mapper.insertSelective(param);
                        if (ret > 0) {
                             if (param.getType() != null && "login".equalsIgnoreCase(param.getType())) {//登录奖励
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getPayment_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else if (param.getType() != null && "register".equalsIgnoreCase(param.getType())) {//注册奖励
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getPayment_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else if (param.getType() != null && "task".equalsIgnoreCase(param.getType())) {//任务奖励
                                UserGoldTypeEntity userGoldTypeEntity = userGoldTypeMapper.selectByPrimaryKey(param.getType_id());
                                UserInfoEntity infoEntity = new UserInfoEntity();
                                infoEntity.setCat_coin(userGoldTypeEntity.getGold_num());
                                infoEntity.setUser_id(param.getPayment_id());
                                int rec = 0;
                                synchronized (infoEntity) {
                                    rec = userInfoMapper.updateAddCatCoin(infoEntity);
                                }
                                if (rec < 0) {
                                    return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                } else {
                                    param.setStatus(2);
                                    mapper.updateByPrimaryKeySelective(param);
                                }
                                //推送
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getUser_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已到账" + userGoldTypeEntity.getGold_num() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setSource(param.getSource());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else if (param.getType() != null && "chat".equalsIgnoreCase(param.getType())) {//畅聊扣除
                                //推送
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getUser_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setSource(param.getSource());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else if (param.getType() != null && "recharge".equalsIgnoreCase(param.getType())) {//充值
                                //推送
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getUser_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setSource(param.getSource());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else if (param.getType() != null && "sign".equalsIgnoreCase(param.getType())) {//签到
                                //推送
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(param.getId());
                                pushMessageParam.setTarget_id(param.getPayment_id());
                                pushMessageParam.setPush_type(param.getType());
                                pushMessageParam.setOpera_type(param.getType());
                                pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                                pushMessageParam.setType(param.getType());
                                pushMessageParam.setSource(param.getSource());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            } else {
                                UserInfoEntity entity = userInfoService.findById(param.getUser_id(), request, response);
                                if (entity != null) {
                                    //判断剩余猫币
                                    if (entity.getCat_coin().compareTo(param.getPay_count()) >= 0 ||
                                            (entity.getChatting_count() > 0 && param.getType() != null && "add_friends".equalsIgnoreCase(param.getType()))) {
                                        int res = 0;
                                        if (param.getType() != null && "add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                            res = userInfoMapper.updateReduceChattingCount(entity);
                                        } else {
                                            entity.setCat_coin(param.getPay_count());
                                            //扣除猫币
                                            synchronized (entity) {
                                                res = userInfoMapper.updateReduceCatCoin(entity);
                                            }
                                        }
                                        if (!(res > 0)) {
                                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                        } else {
                                            param.setStatus(2);
                                            mapper.updateByPrimaryKeySelective(param);
                                            if (param.getType() != null && "redenvelopes".equalsIgnoreCase(param.getType())) {//红包
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setType_id(param.getId());
                                                pushMessageParam.setTarget_id(param.getUser_id());
                                                pushMessageParam.setPush_type(param.getType());
                                                pushMessageParam.setOpera_type(param.getType());
                                                pushMessageParam.setType(param.getType());
                                                pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                                                pushMessageParam.setSource(param.getSource());
                                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                                UserOrderEntity entity2 = super.findById(param.getId(), request, response);
                                                return ResultUtil.success(entity2.getOrder_no());
                                            }
                                            if (param.getType() == null && !"add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                                //推送
                                                PushMessageParam pushMessageParam1 = new PushMessageParam();
                                                pushMessageParam1.setType_id(param.getId());
                                                pushMessageParam1.setTarget_id(param.getUser_id());
                                                pushMessageParam1.setPush_type(param.getType());
                                                pushMessageParam1.setOpera_type(param.getType());
                                                pushMessageParam1.setType(param.getType());
                                                pushMessageParam1.setSource(param.getSource());
                                                pushMessageParam1.setContent("已支出" + param.getPay_count() + "猫币");
                                                pushMessageParam1.setIs_teach_paypal(param.getIs_teach_paypal());
                                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam1);
                                            } else {
                                                UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                                                if (entity1 != null) {
                                                    entity1.setCat_coin(param.getPay_count());
                                                    int rea = 0;
                                                    synchronized (entity1) {
                                                        rea = userInfoMapper.updateAddCatCoin(entity1);
                                                    }
                                                    if (!(rea > 0)) {
                                                        return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                                    } else {
                                                        param.setStatus(2);
                                                        mapper.updateByPrimaryKeySelective(param);
                                                    }//推送
                                                    if (param.getType() != null && "add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                                        pushMessageParam.setType_id(param.getId());
                                                        pushMessageParam.setTarget_id(param.getPayment_id());
                                                        pushMessageParam.setPush_type(param.getType());
                                                        pushMessageParam.setSend_id(param.getUser_id());
                                                        pushMessageParam.setOpera_type(param.getType());
                                                        pushMessageParam.setType(param.getType());
                                                        pushMessageParam.setType_id(param.getPay_id());
                                                        pushMessageParam.setSource(param.getSource());
                                                        pushMessageParam.setContent(entity.getNick_name() + "请求添加你为好友");
                                                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                                    }
                                                    if (param.getType() != null && "gift".equalsIgnoreCase(param.getType())) {//礼物
                                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                                        pushMessageParam.setType_id(param.getId());
                                                        pushMessageParam.setTarget_id(param.getPayment_id());
                                                        pushMessageParam.setPush_type(param.getType());
                                                        pushMessageParam.setSend_id(param.getUser_id());
                                                        pushMessageParam.setOpera_type(param.getType());
                                                        pushMessageParam.setType(param.getType());
                                                        pushMessageParam.setType_id(param.getPay_id());
                                                        pushMessageParam.setSource(param.getSource());
                                                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                                        if (param.getSource() != null && "note".equalsIgnoreCase(param.getSource())) {//地球圈送礼物
                                                            Map<String, Object> map = new HashMap<>();
                                                            map.put("type", "gift");
                                                            map.put("new_time", new Date());
                                                            String noteInfo = messageApiService.queryNoteInfo(param.getPay_id());
                                                            //友圈新消息
                                                            if (StringUtil.isNotEmpty(noteInfo)) {
                                                                map.put("noteinfo", JSONUtils.obj2Json(noteInfo));
                                                                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(param.getUser_id() + "userinfo")));
                                                                String str = redisService.getStr(param.getPayment_id() + "notenews");
                                                                if (StringUtil.isNotEmpty(str)) {
                                                                    List<String> list1 = JSONUtils.json2List(str, null);
                                                                    list1.add(JSONUtils.obj2Json(map).toString());
                                                                    redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                                } else {
                                                                    List<String> list1 = new ArrayList<>();
                                                                    list1.add(JSONUtils.obj2Json(map).toString());
                                                                    redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                                                }
                                            }
                                        }
                                    } else {
                                        return ResultUtil.error(OrderEum.order_70008.getCode(), OrderEum.order_70008.getDesc());
                                    }
                                } else {
                                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                                }
                            }
                        } else {
                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(param.getPayment_ids())) {
                List<Long> longList = JSONUtils.json2List(param.getPayment_ids(), Long.class);
                for (Long aLong : longList) {
                    param.setPayment_id(aLong);
                    param.setOrder_no(CommonUtils.generateOrderKey());
                    param.setId(null);
                    int ret = mapper.insertSelective(param);
                    if (ret > 0) {
                        if (param.getType() != null && "login".equalsIgnoreCase(param.getType())) {//登录奖励
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getPayment_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else if (param.getType() != null && "register".equalsIgnoreCase(param.getType())) {//注册奖励
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getPayment_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else if (param.getType() != null && "task".equalsIgnoreCase(param.getType())) {//任务奖励
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setChatting_count(1);
                            infoEntity.setUser_id(param.getPayment_id());
                            int rec = 0;
                            synchronized (infoEntity) {
                                rec = userInfoMapper.updateAddCatCoin(infoEntity);
                            }
                            if (rec < 0) {
                                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                            } else {
                                param.setStatus(2);
                                mapper.updateByPrimaryKeySelective(param);
                            }//推送
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getUser_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setSource(param.getSource());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else if (param.getType() != null && "chat".equalsIgnoreCase(param.getType())) {//畅聊扣除
                            //推送
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getUser_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setSource(param.getSource());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else if (param.getType() != null && "recharge".equalsIgnoreCase(param.getType())) {//充值
                            //推送
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getUser_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setSource(param.getSource());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else if (param.getType() != null && "sign".equalsIgnoreCase(param.getType())) {//签到
                            //推送
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getPayment_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setSource(param.getSource());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                        } else {
                            UserInfoEntity entity = userInfoService.findById(param.getUser_id(), request, response);
                            if (entity != null) {
                                //判断剩余猫币
                                if (entity.getCat_coin().compareTo(param.getPay_count()) >= 0) {
                                    entity.setCat_coin(param.getPay_count());
                                    int res = 0;
                                    //扣除猫币
                                    synchronized (entity) {
                                        res = userInfoMapper.updateReduceCatCoin(entity);
                                    }
                                    if (!(res > 0)) {
                                        return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                    } else {
                                        param.setStatus(2);
                                        mapper.updateByPrimaryKeySelective(param);
                                        if (param.getType() != null && "redenvelopes".equalsIgnoreCase(param.getType())) {//红包
                                            PushMessageParam pushMessageParam = new PushMessageParam();
                                            pushMessageParam.setType_id(param.getId());
                                            pushMessageParam.setTarget_id(param.getUser_id());
                                            pushMessageParam.setPush_type(param.getType());
                                            pushMessageParam.setOpera_type(param.getType());
                                            pushMessageParam.setType(param.getType());
                                            pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                                            pushMessageParam.setSource(param.getSource());
                                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                            rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                            UserOrderEntity entity2 = super.findById(param.getId(), request, response);
                                            return ResultUtil.success(entity2.getOrder_no());
                                        }
                                        //推送
                                        PushMessageParam pushMessageParam1 = new PushMessageParam();
                                        pushMessageParam1.setType_id(param.getId());
                                        pushMessageParam1.setTarget_id(param.getUser_id());
                                        pushMessageParam1.setPush_type(param.getType());
                                        pushMessageParam1.setOpera_type(param.getType());
                                        pushMessageParam1.setType(param.getType());
                                        pushMessageParam1.setSource(param.getSource());
                                        pushMessageParam1.setContent("已支出" + param.getPay_count() + "猫币");
                                        pushMessageParam1.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam1);
                                        UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                                        if (entity1 != null) {
                                            entity1.setCat_coin(param.getPay_count());
                                            int rea = 0;
                                            synchronized (entity1) {
                                                rea = userInfoMapper.updateAddCatCoin(entity1);
                                            }
                                            if (!(rea > 0)) {
                                                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                            } else {
                                                param.setStatus(2);
                                                mapper.updateByPrimaryKeySelective(param);
                                            }//推送
                                            if (param.getType() != null && "gift".equalsIgnoreCase(param.getType())) {//礼物
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setType_id(param.getId());
                                                pushMessageParam.setTarget_id(param.getPayment_id());
                                                pushMessageParam.setPush_type(param.getType());
                                                pushMessageParam.setSend_id(param.getUser_id());
                                                pushMessageParam.setOpera_type(param.getType());
                                                pushMessageParam.setType(param.getType());
                                                pushMessageParam.setType_id(param.getPay_id());
                                                pushMessageParam.setSource(param.getSource());
                                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                                if (param.getSource() != null && "note".equalsIgnoreCase(param.getSource())) {//地球圈送礼物
                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("type", "gift");
                                                    map.put("new_time", new Date());
                                                    String noteInfo = messageApiService.queryNoteInfo(param.getPay_id());
                                                    //友圈新消息
                                                    if (StringUtil.isNotEmpty(noteInfo)) {
                                                        map.put("noteinfo", JSONUtils.obj2Json(noteInfo));
                                                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(param.getUser_id() + "userinfo")));
                                                        String str = redisService.getStr(param.getPayment_id() + "notenews");
                                                        if (StringUtil.isNotEmpty(str)) {
                                                            List<String> list1 = JSONUtils.json2List(str, null);
                                                            list1.add(JSONUtils.obj2Json(map).toString());
                                                            redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                        } else {
                                                            List<String> list1 = new ArrayList<>();
                                                            list1.add(JSONUtils.obj2Json(map).toString());
                                                            redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                                        }
                                    }
                                } else {
                                    return ResultUtil.error(OrderEum.order_70008.getCode(), OrderEum.order_70008.getDesc());
                                }
                            } else {
                                return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                            }
                        }
                    } else {
                        return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                    }
                }
            } else {
                param.setOrder_no(CommonUtils.generateOrderKey());
                int ret = mapper.insertSelective(param);
                if (ret > 0) {
                    if (param.getType() != null && "login".equalsIgnoreCase(param.getType())) {//登录奖励
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getPayment_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "register".equalsIgnoreCase(param.getType())) {//注册奖励
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getPayment_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "signIn".equalsIgnoreCase(param.getType())) {//签到积分奖励
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getPayment_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "积分");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "task".equalsIgnoreCase(param.getType())) {//任务奖励
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setChatting_count(1);
                        infoEntity.setUser_id(param.getPayment_id());
                        int rec = 0;
                        synchronized (infoEntity) {
                            rec = userInfoMapper.updateAddCatCoin(infoEntity);
                        }
                        if (rec < 0) {
                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                        } else {
                            param.setStatus(2);
                            mapper.updateByPrimaryKeySelective(param);
                        }//推送
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getUser_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setSource(param.getSource());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "chat".equalsIgnoreCase(param.getType())) {//畅聊扣除
                        //推送
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getUser_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setSource(param.getSource());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "recharge".equalsIgnoreCase(param.getType())) {//充值
                        //推送
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setTarget_id(param.getUser_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setSource(param.getSource());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "sign".equalsIgnoreCase(param.getType())) {//签到
                        //推送
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getPayment_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setContent("已到账" + param.getPay_count() + "猫币");
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setSource(param.getSource());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                    } else if (param.getType() != null && "watch_recording".equalsIgnoreCase(param.getType())) {//观看录播
                        UserInfoEntity entity = userInfoService.findById(param.getUser_id(), request, response);
                        if (entity != null) {
                            UserPriceEntity userPriceEntity = new UserPriceEntity();
                            userPriceEntity.setType(param.getType());
                            userPriceEntity.setStatus(1);
                            userPriceEntity.setPageSize(1);
                            List<UserPriceEntity> userPriceEntities = priceService.queryListPage(userPriceEntity, request, response);
                            if (userPriceEntities != null && userPriceEntities.size() > 0) {
                                param.setPay_count(userPriceEntities.get(0).getPrice());
                            } else {
                                param.setPay_count(new BigDecimal(0));
                            }
                            //判断剩余猫币
                            if (entity.getCat_coin().compareTo(param.getPay_count()) >= 0) {
                                entity.setCat_coin(param.getPay_count());
                                int res = 0;
                                synchronized (entity) {
                                    //扣除猫币
                                    res = userInfoMapper.updateReduceCatCoin(entity);
                                }
                                if (!(res > 0)) {
                                    return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                }
                                param.setStatus(2);
                                mapper.updateByPrimaryKeySelective(param);
                                UserOrderEntity userOrderEntity = mapper.selectByPrimaryKey(param.getId());
                                //推送
                                PushMessageParam pushMessageParam = new PushMessageParam();
                                pushMessageParam.setType_id(userOrderEntity.getId());
                                if (param.getPayment_id() != null && param.getPayment_id() != 0) {
                                    pushMessageParam.setTarget_id(userOrderEntity.getPayment_id());
                                } else {
                                    pushMessageParam.setTarget_id(userOrderEntity.getUser_id());
                                }
                                pushMessageParam.setPush_type(userOrderEntity.getType());
                                pushMessageParam.setOpera_type(userOrderEntity.getType());
                                pushMessageParam.setContent("已支出" + userOrderEntity.getPay_count() + "猫币。");
                                pushMessageParam.setType(userOrderEntity.getType());
                                pushMessageParam.setSource(userOrderEntity.getSource());
                                pushMessageParam.setTarget_id(userOrderEntity.getUser_id());
                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                            }
                        }
                    } else {
                        UserInfoEntity entity = userInfoService.findById(param.getUser_id(), request, response);
                        if (entity != null) {
                            //判断剩余猫币
                            if (entity.getCat_coin().compareTo(param.getPay_count()) >= 0 ||
                                    (entity.getChatting_count() > 0 && param.getType() != null && "add_friends".equalsIgnoreCase(param.getType()))) {
                                int res = 0;
                                if (param.getType() != null && "add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                    res = userInfoMapper.updateReduceChattingCount(entity);
                                } else {
                                    entity.setCat_coin(param.getPay_count());
                                    //扣除猫币
                                    synchronized (entity) {
                                        res = userInfoMapper.updateReduceCatCoin(entity);
                                    }
                                }
                                if (!(res > 0)) {
                                    return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                } else {
                                    param.setStatus(2);
                                    mapper.updateByPrimaryKeySelective(param);
                                    if (param.getType() != null && "redenvelopes".equalsIgnoreCase(param.getType())) {//红包
                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                        pushMessageParam.setType_id(param.getId());
                                        pushMessageParam.setTarget_id(param.getUser_id());
                                        pushMessageParam.setPush_type(param.getType());
                                        pushMessageParam.setOpera_type(param.getType());
                                        pushMessageParam.setType(param.getType());
                                        pushMessageParam.setContent("已支出" + param.getPay_count() + "猫币");
                                        pushMessageParam.setSource(param.getSource());
                                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam);
                                        UserOrderEntity entity2 = super.findById(param.getId(), request, response);
                                        return ResultUtil.success(entity2.getOrder_no());
                                    }
                                    if (param.getType() == null && !"add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                        //推送
                                        PushMessageParam pushMessageParam1 = new PushMessageParam();
                                        pushMessageParam1.setType_id(param.getId());
                                        pushMessageParam1.setTarget_id(param.getUser_id());
                                        pushMessageParam1.setPush_type(param.getType());
                                        pushMessageParam1.setOpera_type(param.getType());
                                        pushMessageParam1.setType(param.getType());
                                        pushMessageParam1.setSource(param.getSource());
                                        pushMessageParam1.setContent("已支出" + param.getPay_count() + "猫币");
                                        pushMessageParam1.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam1);
                                    } else {
                                        UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                                        if (entity1 != null) {
                                            entity1.setCat_coin(param.getPay_count());
                                            int rea = 0;
                                            synchronized (entity1) {
                                                rea = userInfoMapper.updateAddCatCoin(entity1);
                                            }
                                            if (!(rea > 0)) {
                                                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                            } else {
                                                param.setStatus(2);
                                                mapper.updateByPrimaryKeySelective(param);
                                            }//推送
                                            if (param.getType() != null && "add_friends".equalsIgnoreCase(param.getType())) {//加好友
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setType_id(param.getId());
                                                pushMessageParam.setTarget_id(param.getPayment_id());
                                                pushMessageParam.setPush_type(param.getType());
                                                pushMessageParam.setSend_id(param.getUser_id());
                                                pushMessageParam.setOpera_type(param.getType());
                                                pushMessageParam.setType(param.getType());
                                                pushMessageParam.setType_id(param.getPay_id());
                                                pushMessageParam.setSource(param.getSource());
                                                pushMessageParam.setContent(entity.getNick_name() + "请求添加你为好友");
                                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                            }
                                            if (param.getType() != null && "gift".equalsIgnoreCase(param.getType())) {//礼物
                                                PushMessageParam pushMessageParam = new PushMessageParam();
                                                pushMessageParam.setType_id(param.getId());
                                                pushMessageParam.setTarget_id(param.getPayment_id());
                                                pushMessageParam.setPush_type(param.getType());
                                                pushMessageParam.setSend_id(param.getUser_id());
                                                pushMessageParam.setOpera_type(param.getType());
                                                pushMessageParam.setType(param.getType());
                                                pushMessageParam.setType_id(param.getPay_id());
                                                pushMessageParam.setSource(param.getSource());
                                                pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                                rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                                if (param.getSource() != null && "note".equalsIgnoreCase(param.getSource())) {//地球圈送礼物
                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("type", "gift");
                                                    map.put("new_time", new Date());
                                                    String noteInfo = messageApiService.queryNoteInfo(param.getPay_id());
                                                    //友圈新消息
                                                    if (StringUtil.isNotEmpty(noteInfo)) {
                                                        map.put("noteinfo", JSONUtils.obj2Json(noteInfo));
                                                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(param.getUser_id() + "userinfo")));
                                                        String str = redisService.getStr(param.getPayment_id() + "notenews");
                                                        if (StringUtil.isNotEmpty(str)) {
                                                            List<String> list1 = JSONUtils.json2List(str, null);
                                                            list1.add(JSONUtils.obj2Json(map).toString());
                                                            redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                        } else {
                                                            List<String> list1 = new ArrayList<>();
                                                            list1.add(JSONUtils.obj2Json(map).toString());
                                                            redisService.set(param.getPayment_id() + "notenews", JSONUtils.list2Json(list1).toString());
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                                        }
                                    }
                                }
                            } else {
                                return ResultUtil.error(OrderEum.order_70008.getCode(), OrderEum.order_70008.getDesc());
                            }
                        } else {
                            return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                        }
                    }
                } else {
                    return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   update");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.success("订单出现未知错误");
        }
        return ResultUtil.success("订单完成");
    }

    @Override
    public List<UserOrderEntity> queryListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserOrderEntity> userOrderEntities = null;
        try {
            userOrderEntities = mapper.queryListPage(param);
            setInfo(param, userOrderEntities);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryListPage");
        }
        return userOrderEntities;
    }

    private void setInfo(UserOrderEntity param, List<UserOrderEntity> userOrderEntities) {
        for (UserOrderEntity entity : userOrderEntities) {
            Map<String, Object> map = new HashMap<>();
            if (param.getUser_id() != null && param.getPayment_id() == null) {
                map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
            } else if (param.getUser_id() == null && param.getPayment_id() != null) {
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
            } else {
                map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
            }
            if ("gift".equalsIgnoreCase(entity.getType())) {
                UserGiftEntity userGiftEntity = new UserGiftEntity();
                userGiftEntity.setId(entity.getType_id());
                userGiftEntity.setPageSize(1);
                List<UserGiftEntity> userGiftEntities = userGiftMapper.queryListPage(userGiftEntity);
                map.put("giftinfo", userGiftEntities);
            }
            if (entity.getSource() != null && "note".equalsIgnoreCase(entity.getSource())) {
                String string = messageApiService.queryNoteInfo(entity.getPay_id());
                //友圈新消息
                map.put("noteinfo", JSONObject.parseObject(string));
            }
            entity.setMap(map);
        }
    }

    /**
     * 查询我送的排行榜
     * zwx
     * 2019年5月14日15:40:02
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserOrderEntity> queryPaymentRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserOrderEntity> userOrderEntities = null;
        try {
            userOrderEntities = mapper.queryRankingListPage(param);
            for (UserOrderEntity entity : userOrderEntities) {
                Map<String, Object> map = new HashMap<>();
                if (param.getUser_id() != null && param.getPayment_id() == null) {
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                } else if (param.getUser_id() == null && param.getPayment_id() != null) {
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                } else {
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryPaymentRankingListPage");
        }
        return userOrderEntities;
    }

    /**
     * 查询友圈的排行榜
     * zwx
     * 2019年5月14日15:40:02
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserOrderEntity> queryPayRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserOrderEntity> userOrderEntities = null;
        try {
            userOrderEntities = mapper.queryPayRankingListPage(param);
            for (UserOrderEntity entity : userOrderEntities) {
                Map<String, Object> map = new HashMap<>();
                if (param.getUser_id() != null && param.getPayment_id() == null) {
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                } else if (param.getUser_id() == null && param.getPayment_id() != null) {
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                } else {
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryPayRankingListPage");
        }
        return userOrderEntities;
    }

    /**
     * 查询送我的排行榜
     * zwx
     * 2019年5月14日15:40:02
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<UserOrderEntity> queryUserRankingListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserOrderEntity> userOrderEntities = null;
        try {
            userOrderEntities = mapper.queryUserRankingListPage(param);
            for (UserOrderEntity entity : userOrderEntities) {
                Map<String, Object> map = new HashMap<>();
                if (param.getUser_id() != null && param.getPayment_id() == null) {//付钱用户id!=null 收钱用户id null
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                } else if (param.getUser_id() == null && param.getPayment_id() != null) {//付钱用户id==null 收钱用户id!= null
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                } else {//付钱用户id!=null 收钱用户id!= null
                    map.put("payinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryUserRankingListPage");
        }
        return userOrderEntities;
    }

    /**
     * 明细
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String, Object> queryDetailListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserOrderEntity> userOrderEntities = Lists.newArrayList();
        Map<String, Object> stringObjectMap = new HashMap<>();
        Integer getCount = 0;//获得总数值
        Integer consumeCount = 0;//消耗总数值
        try {
            List<UserOrderEntity> userOrderEntities1 = mapper.queryDetailListPage(param);
            for (UserOrderEntity entity : userOrderEntities1) {
                //畅聊金币给系统
                if ("chat".equalsIgnoreCase(entity.getType()) && param.getUser_id() != null) {
                    if (param.getOper_id().longValue() != entity.getPayment_id().longValue()) {
                        userOrderEntities.add(entity);
                        continue;
                    } else {
                        continue;
                    }
                }
                if ("gift".equalsIgnoreCase(entity.getType())) {
                    //免费礼物
                    if (entity.getSource() != null && "free".equalsIgnoreCase(entity.getSource())) {
                        if (param.getOper_id() != null && !param.getOper_id().equals(entity.getUser_id())) {
                            userOrderEntities.add(entity);
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
                userOrderEntities.add(entity);
            }
            if (userOrderEntities.size() > 0) {
                for (UserOrderEntity entity : userOrderEntities) {
                    Map<String, Object> map = new HashMap<>();
                    if ("gift".equalsIgnoreCase(entity.getType())) {
                        UserGiftEntity userGiftEntity = new UserGiftEntity();
                        userGiftEntity.setId(entity.getType_id());
                        userGiftEntity.setPageSize(1);
                        List<UserGiftEntity> userGiftEntities = userGiftMapper.queryListPage(userGiftEntity);
                        map.put("giftinfo", userGiftEntities);
                    } else if ("task".equalsIgnoreCase(entity.getType())) {
                        UserGoldTypeEntity userGoldTypeEntity = new UserGoldTypeEntity();
                        userGoldTypeEntity.setId(entity.getPay_id());
                        userGoldTypeEntity.setPageSize(1);
                        List<UserGoldTypeEntity> userGoldTypeEntities = userGoldTypeMapper.query(userGoldTypeEntity);
                        map.put("taskinfo", userGoldTypeEntities);
                    } else if (entity.getType() != null && "video".equalsIgnoreCase(entity.getType())) {
                        if (entity.getSource() != null && !entity.getSource().equals("") && !"video".equalsIgnoreCase(entity.getSource())) {
                            String group_info = messageApiService.queryGroupInfo(entity.getType_id());
                            map.put("groupinfo", JSONObject.parseObject(group_info));
                        }
                    }
                    OrderTypeLogoEntity orderTypeLogoEntity = new OrderTypeLogoEntity();
                    orderTypeLogoEntity.setType(entity.getType());
                    orderTypeLogoEntity.setPageSize(1);
                    List<OrderTypeLogoEntity> orderTypeLogoEntities = orderTypeLogoMapper.queryListPage(orderTypeLogoEntity);
                    if (orderTypeLogoEntities != null && orderTypeLogoEntities.size() > 0) {
                        entity.setLogo_url(orderTypeLogoEntities.get(0).getUrl());
                    }
                    if (param.getOper_id() != null && param.getOper_id() != 0) {
                        if (entity.getUser_id().longValue() == param.getOper_id().longValue()) {
                            entity.setTransaction_type(false);
                            if (entity.getPayment_id() != null) {
                                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                            }
                        } else if (entity.getPayment_id().longValue() == param.getOper_id().longValue()) {
                            entity.setTransaction_type(true);
                            if (entity.getUser_id() != null) {
                                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                            }
                        }
                    } else {
                        if (entity.getUser_id().longValue() == param.getUser_id().longValue()) {
                            entity.setTransaction_type(false);
                            if (entity.getPayment_id() != null) {
                                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getPayment_id() + "userinfo")));
                            }
                        } else if (entity.getPayment_id().longValue() == param.getUser_id().longValue()) {
                            entity.setTransaction_type(true);
                            if (entity.getUser_id() != null) {
                                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                            }
                        }
                    }
                    entity.setMap(map);
                }
                param.setUser_id(param.getOper_id());
                consumeCount = mapper.queryListPageSum(param);
                param.setUser_id(null);
                param.setPayment_id(param.getOper_id());
                getCount = mapper.queryListPageSum(param);
                stringObjectMap.put("userOrderEntities", userOrderEntities);
                stringObjectMap.put("getCount", getCount);
                stringObjectMap.put("consumeCount", consumeCount);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryDetailListPage");
        }
        return stringObjectMap;
    }

    /**
     * 登录弹窗
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryDayListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<UserOrderEntity> userOrderEntities = mapper.queryDayListPage(param);
            if (userOrderEntities != null && userOrderEntities.size() > 0) {
                map.put("state", false);
            } else {
                BigDecimal bigDecimal = new BigDecimal("10");
                param.setOrder_no(CommonUtils.generateOrderKey());
                param.setPay_count(bigDecimal);
                super.insert(param, request, response);
                UserInfoEntity infoEntity = new UserInfoEntity();
                infoEntity.setUser_id(param.getPayment_id());
                infoEntity.setCat_coin(bigDecimal);
                userInfoMapper.updateAddCatCoin(infoEntity);
                map.put("state", true);
                map.put("reward", "10");
                return ResultUtil.success(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryDayListPage");
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam update(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setPageSize(1);
            List<UserOrderEntity> userOrderEntities = mapper.query(param);
            if (userOrderEntities != null && userOrderEntities.size() > 0) {
                if (userOrderEntities.get(0).getStatus() == 1) {
                    UserInfoEntity entity1 = userInfoService.findById(userOrderEntities.get(0).getPayment_id(), request, response);
                    if (entity1 != null) {
                        entity1.setCat_coin(userOrderEntities.get(0).getPay_count());
                        int rea = userInfoMapper.updateAddCatCoin(entity1);
                        if (!(rea > 0)) {
                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                        } else {
                            UserOrderEntity entity = new UserOrderEntity();
                            entity.setId(userOrderEntities.get(0).getId());
                            entity.setStatus(2);
                            mapper.updateByPrimaryKeySelective(entity);
                        }
                    } else {
                        return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                    }
                } else {
                    return ResultUtil.error(OrderEum.order_70012.getCode(), OrderEum.order_70012.getDesc());
                }
            } else {
                return ResultUtil.error(OrderEum.order_70013.getCode(), OrderEum.order_70013.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam queryFreeFriendsCount(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        long tt = calendar.getTime().getTime() / 1000;
        System.out.println(tt);
        String result = redisService.getStr(param.getUser_id() + "freefriendscount");
        if (StringUtil.isNotEmpty(result)) {
            Integer integer = Integer.valueOf(result);
            map.put("freeCount", integer);
        } else {
            Integer integer = 3;
            map.put("freeCount", integer);
            redisService.set(param.getUser_id() + "freefriendscount", integer.toString(), tt - System.currentTimeMillis() / 1000);
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam updateFreeFriends(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean aBoolean = false;
        Map<String, Object> map = new HashMap<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            long tt = calendar.getTime().getTime() / 1000;
            String result = redisService.getStr(param.getUser_id() + "freefriendscount");
            if (StringUtil.isNotEmpty(result)) {
                Integer integer = Integer.valueOf(result);
                if (integer > 0) {
                    integer = integer - 1;
                    aBoolean = true;
                    redisService.set(param.getUser_id() + "freefriendscount", integer.toString(), tt - System.currentTimeMillis() / 1000);
                    param.setOrder_no(CommonUtils.generateOrderKey());
                    param.setSource("free");
                    int ret = mapper.insertSelective(param);
                    if (ret > 0) {
                        UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                        if (entity1 != null) {
                            entity1.setCat_coin(param.getPay_count());
                            int rea = 0;
                            synchronized (entity1) {
                                rea = userInfoMapper.updateAddCatCoin(entity1);
                            }
                            if (!(rea > 0)) {
                                return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                            } else {
                                param.setStatus(2);
                                mapper.updateByPrimaryKeySelective(param);
                            }//推送
                            PushMessageParam pushMessageParam = new PushMessageParam();
                            pushMessageParam.setType_id(param.getId());
                            pushMessageParam.setTarget_id(param.getPayment_id());
                            pushMessageParam.setPush_type(param.getType());
                            pushMessageParam.setSend_id(param.getUser_id());
                            pushMessageParam.setOpera_type(param.getType());
                            pushMessageParam.setType(param.getType());
                            pushMessageParam.setType_id(param.getPay_id());
                            pushMessageParam.setSource(param.getSource());
                            pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                        }
                    }
                } else {
                    param.setOrder_no(CommonUtils.generateOrderKey());
                    int ret = mapper.insertSelective(param);
                    if (ret > 0) {
                        UserInfoEntity entity = userInfoService.findById(param.getUser_id(), request, response);
                        if (entity != null) {
                            //判断剩余猫币
                            if (entity.getCat_coin().compareTo(param.getPay_count()) >= 0) {
                                entity.setCat_coin(param.getPay_count());
                                int res = 0;
                                //扣除猫币
                                synchronized (entity) {
                                    res = userInfoMapper.updateReduceCatCoin(entity);
                                }
                                if (!(res > 0)) {
                                    return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                } else {
                                    //推送
                                    PushMessageParam pushMessageParam1 = new PushMessageParam();
                                    pushMessageParam1.setType_id(param.getId());
                                    pushMessageParam1.setTarget_id(param.getUser_id());
                                    pushMessageParam1.setPush_type(param.getType());
                                    pushMessageParam1.setOpera_type(param.getType());
                                    pushMessageParam1.setType(param.getType());
                                    pushMessageParam1.setSource(param.getSource());
                                    pushMessageParam1.setContent("已支出" + param.getPay_count() + "猫币");
                                    pushMessageParam1.setIs_teach_paypal(param.getIs_teach_paypal());
                                    rabbitTemplate.convertAndSend("assetsChangeMessage", pushMessageParam1);
                                    UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                                    if (entity1 != null) {
                                        entity1.setCat_coin(param.getPay_count());
                                        int rea = 0;
                                        synchronized (entity1) {
                                            rea = userInfoMapper.updateAddCatCoin(entity1);
                                        }
                                        if (!(rea > 0)) {
                                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                                        } else {
                                            param.setStatus(2);
                                            mapper.updateByPrimaryKeySelective(param);
                                            aBoolean = true;
                                        }
                                        //推送
                                        PushMessageParam pushMessageParam = new PushMessageParam();
                                        pushMessageParam.setType_id(param.getId());
                                        pushMessageParam.setTarget_id(param.getPayment_id());
                                        pushMessageParam.setPush_type(param.getType());
                                        pushMessageParam.setSend_id(param.getUser_id());
                                        pushMessageParam.setOpera_type(param.getType());
                                        pushMessageParam.setType(param.getType());
                                        pushMessageParam.setType_id(param.getPay_id());
                                        pushMessageParam.setSource(param.getSource());
                                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                                    }
                                }
                            }
                        }
                    }
                }
                map.put("freeCount", integer);
            } else {
                param.setSource("free");
                param.setOrder_no(CommonUtils.generateOrderKey());
                int ret = mapper.insertSelective(param);
                if (ret > 0) {
                    UserInfoEntity entity1 = userInfoService.findById(param.getPayment_id(), request, response);
                    if (entity1 != null) {
                        entity1.setCat_coin(param.getPay_count());
                        int rea = 0;
                        synchronized (entity1) {
                            rea = userInfoMapper.updateAddCatCoin(entity1);
                        }
                        if (!(rea > 0)) {
                            return ResultUtil.error(OrderEum.order_70009.getCode(), OrderEum.order_70009.getDesc());
                        } else {
                            param.setStatus(2);
                            mapper.updateByPrimaryKeySelective(param);
                        }//推送
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setTarget_id(param.getPayment_id());
                        pushMessageParam.setPush_type(param.getType());
                        pushMessageParam.setSend_id(param.getUser_id());
                        pushMessageParam.setOpera_type(param.getType());
                        pushMessageParam.setType(param.getType());
                        pushMessageParam.setType_id(param.getPay_id());
                        pushMessageParam.setSource(param.getSource());
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
                    }
                }
                Integer integer = 2;
                aBoolean = true;
                map.put("freeCount", integer);
                redisService.set(param.getUser_id() + "freefriendscount", integer.toString(), tt - System.currentTimeMillis() / 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result", aBoolean);
        return ResultUtil.success(map);
    }

    /**
     * 查询累计
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResultParam queryListPageSum(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {

        return ResultUtil.success(mapper.queryListPageSum(param));
    }

    /**
     * 礼物中心
     *
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String, Object> queryGiftListPage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Integer getCount = 0;//获得总数值
        Integer consumeCount = 0;//消耗总数值
        Map<String, Object> stringObjectMap = new HashMap<>();
        try {
            Integer total = mapper.queryListPageCount(param);
            List<UserOrderEntity> userOrderEntities = mapper.queryListPage(param);
            if (userOrderEntities != null && userOrderEntities.size() > 0) {
                this.setInfoList(userOrderEntities, param, request, response);
            }
            if (param.getUser_id() != null && param.getPayment_id() == null) {
                consumeCount = mapper.queryListPageSum(param);
            } else if (param.getUser_id() == null && param.getPayment_id() != null) {
                getCount = mapper.queryListPageSum(param);
            }
            stringObjectMap.put("userOrderEntities", userOrderEntities);
            stringObjectMap.put("getCount", getCount);
            stringObjectMap.put("consumeCount", consumeCount);
            stringObjectMap.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserOrderServiceImpl   queryListPage");
        }
        return stringObjectMap;
    }

    @Override
    public ResultParam queryNeedPayment(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            boolean flag = true;
            List<UserOrderEntity> list = mapper.queryByTime(param);
            if (list != null && list.size() > 0) {
                flag = false;
            }
            UserAppointmentEntity userAppointmentEntity = new UserAppointmentEntity();
            userAppointmentEntity.setPageSize(1);
            userAppointmentEntity.setUser_id(param.getUser_id());
            userAppointmentEntity.setAppointment_id(param.getPay_id());
            List<UserAppointmentEntity> list1 = userAppointmentMapper.queryListPage(userAppointmentEntity);
            if (list1 != null && list1.size() > 0) {
                flag = false;
            }

            MasterAppointmentEntity masterAppointmentEntity = new MasterAppointmentEntity();
            masterAppointmentEntity.setId(param.getPay_id());
            masterAppointmentEntity.setMaster_id(param.getUser_id());
            masterAppointmentEntity.setPageSize(1);
            List<MasterAppointmentEntity> list2 = masterAppointmentMapper.queryListPage(masterAppointmentEntity);
            if (list2 != null && list2.size() > 0) {
                flag = false;
            }
            UserPriceEntity userPriceEntity = new UserPriceEntity();
            userPriceEntity.setType(param.getType());
            userPriceEntity.setStatus(1);
            userPriceEntity.setPageSize(1);
            List<UserPriceEntity> userPriceEntities = priceService.queryListPage(userPriceEntity, request, response);

            if (userPriceEntities != null && userPriceEntities.size() > 0) {
                map.put("price", userPriceEntities.get(0).getPrice());
                map.put("needPayment", flag);
                return ResultUtil.success(map);
            } else {
                return ResultUtil.error(UserEum.user_10044.getCode(), UserEum.user_10044.getDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public Map<String, Object> queryByMessage(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (param.getNick_name() != null && !param.getNick_name().equals("")) {
                List<Long> list1 = userInfoMapper.queryIdByNickName(param.getNick_name());
                if (list1 != null && list1.size() > 0) {
                    param.setNickNameIDs(list1);
                }
            }
            List<UserOrderEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            if (total > 0) {
                setInfoList(list, param, request, response);
            }
            map.put("rows", list);
            map.put("total", total);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional
    public ResultParam insertPoint(UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserGoldTypeEntity userGoldTypeEntity = new UserGoldTypeEntity();
            userGoldTypeEntity.setType(param.getType());
            userGoldTypeEntity.setStatus(1);
            userGoldTypeEntity.setPageSize(1);
            userGoldTypeEntity = userGoldTypeMapper.queryListPage(userGoldTypeEntity).get(0);
            if (!"buy_course_return".equals(param.getType())) {
                UserOrderEntity userOrderEntity = new UserOrderEntity();
                userOrderEntity.setUser_id(param.getUser_id());
                userOrderEntity.setStatus(2);
                int count = mapper.queryDayListPageCount(userOrderEntity);
                if (count <= userGoldTypeEntity.getFrequency()) {
                    param.setOrder_no(CommonUtils.generateOrderKey());
                    int i = mapper.insertSelective(param);
                }
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                userInfoEntity.setPoints(userOrderEntity.getPay_count().divide(userGoldTypeEntity.getGold_num(), 0).intValue());
                int i = userInfoMapper.updateAddPoint(userInfoEntity);
                return ResultUtil.success();
            }else {
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                userInfoEntity.setPoints(userGoldTypeEntity.getGold_num().intValue());
                int i = userInfoMapper.updateAddPoint(userInfoEntity);
                return ResultUtil.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(ResultEnum.result_1.getCode(), ResultEnum.result_1.getDesc());
    }


    private void setInfoList(List<UserOrderEntity> list, UserOrderEntity param, HttpServletRequest request, HttpServletResponse response) {
        setInfo(param, list);
    }

}