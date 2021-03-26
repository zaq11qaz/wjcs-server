package com.huihe.eg.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.google.common.collect.Maps;
import com.huihe.eg.cloud.feign.AuthorizationApiService;
import com.huihe.eg.comm.TokenUtils;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.comm.util.GenSerial;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.MasterInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserMapper;
import com.huihe.eg.user.service.dao.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.user.service.dao.pay.WxPayService;
import com.huihe.eg.user.service.impl.mail.MailService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import com.huihe.eg.user.service.impl.tim.TimConfig;
import com.tencentyun.TLSSigAPIv2;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;

import java.util.*;

@Service
public class UserServiceImpl extends BaseFrameworkServiceImpl<UserEntity, Long> implements UserService {

    @Resource
    private UserMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private SmsService smsService;
    @Resource
    private TimConfig timConfig;
    @Resource
    private MailService mailService;
    @Resource
    private UserContactsService userContactsService;
    @Resource
    private RedisService redisService;
    @Resource
    private UserOrderService orderService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private AuthorizationApiService authorizationApiService;
    @Resource
    private MasterInfoMapper masterInfoMapper;
    @Resource
    private UserCouponService userCouponService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam login(UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(entity.getNick_name())) {
                entity.setNick_name(EmojiUtil.resolveToByteFromEmoji(entity.getNick_name()));
                entity.setNick_name(EmojiParser.parseToUnicode(entity.getNick_name()));
            }
//            entity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());

            if (StringUtil.isEmpty(entity.getLogin_type())) {//如果登录方式为空
                return ResultUtil.error(UserEum.user_10002.getCode(), UserEum.user_10002.getDesc());
            }
            if (StringUtil.isEmpty(entity.getLogin_name())) {//如果登录名为空
                return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
            }
            UserEntity userEntity1 = new UserEntity();
            userEntity1.setLogin_name(entity.getLogin_name());
            userEntity1.setPageSize(1);
//            userEntity1.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
            List<UserEntity> query1 = mapper.queryListPage(userEntity1);
            if (query1 != null && query1.size() > 0) {
                UserEntity userEntity = query1.get(0);
                userEntity.setLogin_platform(entity.getPlatform());
                mapper.updateByPrimaryKeySelective(userEntity);
            } else {
                return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
            }
            if (query1.get(0).getStatus() == 4) {
                return ResultUtil.error(UserEum.user_10046.getCode(), UserEum.user_10046.getDesc());
            }

            if (query1.get(0).getStatus() == 3) {
                return ResultUtil.error(UserEum.user_10034.getCode(), UserEum.user_10034.getDesc());
            }


            if ("1".equals(entity.getLogin_type()) || "2".equals(entity.getLogin_type())) {//密码登录或验证码登录
                if (entity.getLogin_name().contains("@")) {//如果有@
                    if (!mailService.isValidEmail(entity.getLogin_name())) {
                        return ResultUtil.error(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
                    }
                }/*else if (!CommonUtils.isPhone(entity.getLogin_name())){
                return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
            }*/

                List<UserEntity> list = null;
                List<UserInfoEntity> infoEntities = null;
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                if ("1".equals(entity.getLogin_type())) {//密码登录
                    if (StringUtil.isEmpty(entity.getLogin_pass())) {//密码非空
                        return ResultUtil.error(UserEum.user_10004.getCode(), UserEum.user_10004.getDesc());
                    }
                    entity.setLogin_type(null);//设置登录方式 空
                    String str = MD5Util.GetMD5Code(//帐号
                            entity.getLogin_name() + entity.getLogin_pass()//+密码
                    );//md5加密
                    entity.setLogin_pass(str);//设置密码
//                    entity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
                    list = super.query(entity, request, response);//查询
                    if (list != null && list.size() > 0) {//存在
                        entity = list.get(0);
                        userInfoEntity.setUser_id(list.get(0).getUser_id());
                        userInfoEntity.setPageSize(1);
                        infoEntities = userInfoService.queryListPage(userInfoEntity, request, response);
                        Map<String, Object> map = Maps.newHashMap();
                        if (infoEntities != null && infoEntities.size() > 0) {
                            userInfoEntity = infoEntities.get(0);
                            if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
                                userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                                userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
                            }
                            map.put("userInfoEntity", userInfoEntity);

                            //            int ret = userInfoService.TIMregister(userInfoEntity);

                            //聊天sign
//                            String sign = redisService.getStr("usersign" + entity.getUser_id());
//                            if (StringUtil.isNotEmpty(sign)) {
//                                map.put("userSign", sign);
//                            } else {
                            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                            String userSign = tlsSigAPIv2.genSig(infoEntities.get(0).getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                            redisService.set("usersign" + infoEntities.get(0).getUser_id(), userSign, timConfig.getExpire());
                            map.put("userSign", userSign);
//                            }

                            //登录刷新token
                            String userToken = "usertoken" + TokenUtils.createJWT(infoEntities.get(0).getUser_id().toString(), "user", "qmore", -1);
                            redisService.set(userToken, infoEntities.get(0).getUser_id().toString(), 86400);
                            map.put("userToken", userToken);
                        } else {
                            return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
                        }
                        return ResultUtil.success(map);
                    } else {
                        return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
                    }
                } else {
                    if (StringUtil.isEmpty(entity.getVerification_code())) {//获得验证码
                        return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
                    }
                    if (entity.getLogin_name().contains("@")) {//如果邮箱
                        if (!mailService.isValidEmail(entity.getLogin_name())) {
                            return ResultUtil.error(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
                        }
                        ResultParam resultParam1 = mailService.validate(entity.getLogin_name(), entity.getVerification_code());
                        if (resultParam1.getCode() != 0) {
                            return resultParam1;
                        } else {
                            entity.setLogin_type(null);
//                            entity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
                            list = super.query(entity, request, response);
                            if (list != null && list.size() > 0) {
                                entity = list.get(0);
                                userInfoEntity.setUser_id(list.get(0).getUser_id());
                                infoEntities = userInfoService.query(userInfoEntity, request, response);
                                Map<String, Object> map = new HashMap<>();
                                if (infoEntities != null && infoEntities.size() > 0) {
                                    userInfoEntity = infoEntities.get(0);
                                    if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
                                        userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                                        userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
                                    }
                                    map.put("userInfoEntity", userInfoEntity);
                                    //            int ret = userInfoService.TIMregister(userInfoEntity);

                                    //聊天sign
//                                    String sign = redisService.getStr("usersign" + entity.getUser_id());
//                                    if (StringUtil.isNotEmpty(sign)) {
//                                        map.put("userSign", sign);
//                                    } else {
                                    TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                                    String userSign = tlsSigAPIv2.genSig(infoEntities.get(0).getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                                    redisService.set("usersign" + infoEntities.get(0).getUser_id(), userSign, timConfig.getExpire());
                                    map.put("userSign", userSign);
//                                    }

                                    //登录刷新token
                                    String userToken = "usertoken" + TokenUtils.createJWT(infoEntities.get(0).getUser_id().toString(), "user", "qmore", -1);
                                    redisService.set(userToken, infoEntities.get(0).getUser_id().toString(), 86400);
                                    map.put("userToken", userToken);
                                    return ResultUtil.success(map);
                                } else {
                                    return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
                                }
                            } else {
                                return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
                            }
                        }
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        if (StringUtil.isEmpty(entity.getVerification_code())) {//验证码非空
                            return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
                        }
                        ResultParam resultParam = smsService.validate(entity.getLogin_name(), entity.getVerification_code());//验证redis中验证码
                        if (resultParam.getCode() != 0) {//无
                            return resultParam;
                        } else {
                            entity.setLogin_type(null);
                            entity.setPageSize(1);
                            list = queryListPage(entity, request, response);
                            if (list != null && list.size() > 0) {
                                entity = list.get(0);
                                userInfoEntity.setUser_id(list.get(0).getUser_id());
                                userInfoEntity.setPageSize(1);
                                infoEntities = userInfoService.queryListPage(userInfoEntity, request, response);
                                if (infoEntities != null && infoEntities.size() > 0) {
                                    userInfoEntity = infoEntities.get(0);
                                    if (StringUtil.isNotEmpty(userInfoEntity.getNick_name())){
                                        userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                                        userInfoEntity.setNick_name(EmojiParser.parseToUnicode(userInfoEntity.getNick_name()));
                                    }
                                    map.put("userInfoEntity", userInfoEntity);
                                    //            int ret = userInfoService.TIMregister(userInfoEntity);
                                    //聊天sign
//                                    String sign = redisService.getStr("usersign" + entity.getUser_id());
//                                    if (sign != null && !"".equals(sign)) {
//                                        map.put("userSign", sign);
//                                    } else {
                                    TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                                    String userSign = tlsSigAPIv2.genSig(infoEntities.get(0).getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                                    redisService.set("usersign" + infoEntities.get(0).getUser_id(), userSign, timConfig.getExpire());
                                    map.put("userSign", userSign);
//                                    }

                                    //登录刷新token
                                    String userToken = "usertoken" + TokenUtils.createJWT(infoEntities.get(0).getUser_id().toString(), "user", "qmore", -1);
                                    redisService.set(userToken, infoEntities.get(0).getUser_id().toString(), 86400);
                                    map.put("userToken", userToken);
                                }
                                return ResultUtil.success(map);
                            } else {
                                //return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                            /* ResultParam resultParam = insert(entity, request, response);
                            if (resultParam.getCode() != 0){
                                return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
                            }
                            list = super.query(entity, request, response);
                            userInfoEntity.setUser_id(list.get(0).getUser_id());
                            infoEntities = userInfoService.query(userInfoEntity, request, response);
                            return ResultUtil.success(infoEntities.get(0));*/
                                return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
                            }
                        }
                    }
                }
            /*
        } else if ("3".equals(entity.getLogin_type())) {

        } else if ("4".equals(entity.getLogin_type())) {

             */
            }
            return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam register(UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(entity.getNick_name())) {
                entity.setNick_name(EmojiUtil.resolveToByteFromEmoji(entity.getNick_name().trim()));
                entity.setNick_name(EmojiParser.parseToUnicode(entity.getNick_name()));
            }
            if (StringUtil.isEmpty(entity.getLogin_name())) {
                return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
            }
            if (StringUtil.isEmpty(entity.getVerification_code())) {
                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
            }
            entity.setPageSize(1);
//            entity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
            List<UserEntity> list = super.query(entity, request, response);
            if (list != null && list.size() > 0 && list.get(0).getStatus() != 4) {
                return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
            }
            if (StringUtil.isEmpty(entity.getRegister_platform())) {
                entity.setRegister_platform(entity.getPlatform());
            }
            if (entity.getLogin_name().contains("@")) {
                if (mailService.isValidEmail(entity.getLogin_name())) {
                    ResultParam resultParam1 = mailService.validate(entity.getLogin_name(), entity.getVerification_code());
                    if (resultParam1.getCode() != 0) {
                        return resultParam1;
                    } else {
                        ResultParam resultParam = insert(entity, request, response);
                        if (resultParam.getCode() != 0) {
                            return resultParam;
                        }
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
                }
            } else {
                if (CommonUtils.isPhone(entity.getLogin_name())) {//验证登录名
                    ResultParam resultParam1 = smsService.validate(entity.getLogin_name(), entity.getVerification_code());
                    if (resultParam1.getCode() != 0) {
                        return resultParam1;
                    } else {
                        ResultParam resultParam = insert(entity, request, response);
                        if (resultParam.getCode() != 0) {
                            return resultParam;
                        } else {
                            //增加邀请注册用户
                            UserContactsEntity contactsEntity = new UserContactsEntity();
                            contactsEntity.setMobile(entity.getLogin_name());//设置手机号
                            contactsEntity.setPageSize(1);
                            List<UserContactsEntity> contactsEntities = userContactsService.queryListPage(contactsEntity, request, response);//查询邀请人集合
                            if (contactsEntities != null && contactsEntities.size() > 0) {
                                for (UserContactsEntity userContactsEntity : contactsEntities) {//遍历
                                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                                    userInfoEntity.setUser_id(userContactsEntity.getUser_id());
                                }
                            }
                        }
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
                }
            }

            //腾讯云注册
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUser_id(entity.getUser_id());
//            int ret = userInfoService.TIMregister(userInfoEntity);//todo
            Map<String, Object> map = new HashMap<>();
            map.put("wx_code", entity.getWx_code());
            map.put("wx_login_name", entity.getWx_login_name());
            //聊天sign
//            String sign = redisService.getStr("usersign" + entity.getUser_id());
//            if (sign != null && !"".equals(sign)) {
//                map.put("userSign", sign);
//            } else {
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
            String userSign = tlsSigAPIv2.genSig(entity.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
            redisService.set("usersign" + entity.getUser_id(), userSign, timConfig.getExpire());
            map.put("userSign", userSign);
//            }

            if (StringUtil.isNotEmpty(entity.getInvitation_code())) {
                userInfoEntity.setInvite_code(entity.getInvitation_code());
                userInfoService.AddInviteCash(userInfoEntity.getUser_id(), entity.getUser_id(), request, response);
            }


            //注册奖励
            UserOrderEntity entity1 = new UserOrderEntity();//明细订单记录
            entity1.setPay_count(new BigDecimal("50"));
            entity1.setPayment_id(entity.getUser_id());
            entity1.setType("register");
            orderService.insert(entity1, request, response);
            userInfoEntity.setCat_coin(new BigDecimal("50"));//增加猫币
            userInfoMapper.updateAddCatCoin(userInfoEntity);
            map.put("reward", userInfoEntity.getCat_coin());
            map.put("user_id", entity.getUser_id());
//            map.put("TIMMessage", ret);
            map.put("message", "注册成功");
            return ResultUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10014.getCode(), UserEum.user_10014.getDesc());
    }

    @Override
    public ResultParam insert(UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(entity.getNick_name())) {
            entity.setNick_name(EmojiUtil.resolveToByteFromEmoji(entity.getNick_name().trim()));
            entity.setNick_name(EmojiParser.parseToUnicode(entity.getNick_name()));
        }
        UserEntity userEntity = new UserEntity();
        if (StringUtil.isNotEmpty(entity.getLogin_name())) {
            userEntity.setLogin_name(entity.getLogin_name());
            userEntity.setPageSize(1);
//            userEntity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
            List<UserEntity> userEntities = mapper.queryListPage(userEntity);
            if (userEntities != null && userEntities.size() > 0) {
                entity.setUser_id(userEntities.get(0).getUser_id());
                return ResultUtil.error(UserEum.user_10000.getCode(), UserEum.user_10000.getDesc());
            }
        }
        ResultParam resultParam = super.insert(entity, request, response);
        if (resultParam.getCode() == 0 && entity.getUser_id() != null && entity.getUser_id() != 0) {
            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setAvatar(entity.getAvatar());
            infoEntity.setSex(entity.getSex());
            String inviteCode = this.getCode(entity.getUser_id());
            infoEntity.setInvite_code(inviteCode);
            if (entity.getNick_name() != null && !"".equals(entity.getNick_name())) {
                infoEntity.setNick_name(entity.getNick_name());
            } else {
                infoEntity.setNick_name(entity.getLogin_name());
            }
            if (entity.getInvitation_code() != null) {
                infoEntity.setInvite_code(entity.getInvitation_code());
            }
            if (entity.getAge() != null) {
                infoEntity.setAge(entity.getAge());
            }
            if (entity.getPreference() != null) {
                infoEntity.setPreference(entity.getPreference());
            }
            if (entity.getAdmin_id() != null) {
                infoEntity.setAdmin_id(entity.getAdmin_id());
            }
            if (entity.getRelationships() != null) {
                infoEntity.setRelationship(entity.getRelationships());
            }
            if (StringUtil.isNotEmpty(entity.getLogin_name())) {
                if (entity.getLogin_name().contains("@")) {
                    infoEntity.setMail(entity.getLogin_name());
                } else {
                    infoEntity.setMobile(entity.getLogin_name());
                }
            }
            infoEntity.setUser_id(entity.getUser_id());
            if (entity.getLogin_name() != null) {
                if (entity.getLogin_name().contains("@")) {
                    infoEntity.setMail(entity.getLogin_name());
                } else {
                    if (StringUtil.isNotEmpty(entity.getMobile())) {
                        infoEntity.setMobile(entity.getMobile());
                    } else {
                        infoEntity.setMobile(entity.getLogin_name());
                    }
                }
            }
            if (StringUtil.isNotEmpty(entity.getRegister_platform()) && "shakeSpeare".equals(entity.getRegister_platform())) {
                Long user_id;
                if (entity.getAge() < 18) {
                    user_id = authorizationApiService.queryHelper("childrenHelper");
                } else if (entity.getAge() == 18) {
                    user_id = authorizationApiService.queryHelper("youthsHelper");
                } else {
                    user_id = authorizationApiService.queryHelper("adultsHelper");
                }
                if (user_id != 0L) {
                    UserInfoEntity userInfoEntity = userInfoMapper.queryHelperList(user_id);
                    infoEntity.setTeacher_id(userInfoEntity.getUser_id());
                    infoEntity.setBirth(entity.getBirth());
                    entity.setWx_code(userInfoEntity.getWx_code_url());
                    entity.setWx_login_name(userInfoEntity.getWx_code());
                }
            }
            if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                infoEntity.setAdmin_id(entity.getMechanism_id());
            }
            infoEntity.setUrl("http://www.curiousmore.com:8768/eg-api/push/people.html?qmore_id=" + entity.getUser_id());
            infoEntity.setIs_teach_paypal(entity.getIs_teach_paypal());
            resultParam = userInfoService.insert(infoEntity, request, response);
            if (resultParam != null && resultParam.getCode() != 0) {
                redisService.set(entity.getUser_id() + "userinfo", JSONObject.toJSONString(infoEntity));
            }
        } else {
            return ResultUtil.error(UserEum.user_10014.getCode(), UserEum.user_10014.getDesc());
        }
        return resultParam;
    }

    @Override
    public ResultParam insertUserInfo(UserEntity entity, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(entity.getNick_name())) {
            entity.setNick_name(EmojiParser.parseToHtmlDecimal(entity.getNick_name()));
        }
        UserEntity userEntity = new UserEntity();
        if (StringUtil.isNotEmpty(entity.getLogin_name())) {
            userEntity.setLogin_name(entity.getLogin_name());
            userEntity.setPageSize(1);
//            userEntity.setIs_teach_paypal(entity.getIs_teach_paypal() != null && entity.getIs_teach_paypal());
            List<UserEntity> userEntities = mapper.queryListPage(userEntity);
            if (userEntities != null && userEntities.size() > 0) {
                entity.setUser_id(userEntities.get(0).getUser_id());
                return ResultUtil.success();
            }
        }
        ResultParam resultParam = super.insert(entity, request, response);
        if (resultParam.getCode() == 0 && entity.getUser_id() != null && entity.getUser_id() != 0) {
            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setAvatar(entity.getAvatar());
            infoEntity.setSex(entity.getSex());
            String inviteCode = this.getCode(entity.getUser_id());
            infoEntity.setInvite_code(inviteCode);
            if (entity.getNick_name() != null && !"".equals(entity.getNick_name())) {
                infoEntity.setNick_name(entity.getNick_name());
            } else {
                infoEntity.setNick_name(entity.getLogin_name());
            }
            if (entity.getInvitation_code() != null) {
                infoEntity.setInvite_code(entity.getInvitation_code());
            }
            if (entity.getAge() != null) {
                infoEntity.setAge(entity.getAge());
            }
            if (entity.getPreference() != null) {
                infoEntity.setPreference(entity.getPreference());
            }
            if (entity.getAdmin_id() != null) {
                infoEntity.setAdmin_id(entity.getAdmin_id());
            }
            if (entity.getRelationships() != null) {
                infoEntity.setRelationship(entity.getRelationships());
            }
            infoEntity.setUser_id(entity.getUser_id());
            if (entity.getLogin_name() != null) {
                if (entity.getLogin_name().contains("@")) {
                    infoEntity.setMail(entity.getLogin_name());
                } else {
                    if (StringUtil.isNotEmpty(entity.getMobile())) {
                        infoEntity.setMobile(entity.getMobile());
                    } else {
                        infoEntity.setMobile(entity.getLogin_name());
                    }
                }
            }
            if (StringUtil.isNotEmpty(entity.getRegister_platform()) && "shakeSpeare".equals(entity.getRegister_platform())) {
                Long user_id;
                if (entity.getAge() < 18) {
                    user_id = authorizationApiService.queryHelper("childrenHelper");
                } else if (entity.getAge() == 18) {
                    user_id = authorizationApiService.queryHelper("youthsHelper");
                } else {
                    user_id = authorizationApiService.queryHelper("adultsHelper");
                }
                if (user_id != 0L) {
                    UserInfoEntity userInfoEntity = userInfoMapper.queryHelperList(user_id);
                    infoEntity.setTeacher_id(userInfoEntity.getUser_id());
                    infoEntity.setBirth(entity.getBirth());
                    entity.setWx_code(userInfoEntity.getWx_code_url());
                    entity.setWx_login_name(userInfoEntity.getWx_code());
                }
            }
            if (entity.getMechanism_id() != null && entity.getMechanism_id() != 0) {
                infoEntity.setAdmin_id(entity.getMechanism_id());
            }
            infoEntity.setUrl("http://www.curiousmore.com:8768/eg-api/push/people.html?qmore_id=" + entity.getUser_id());
            infoEntity.setIs_teach_paypal(entity.getIs_teach_paypal());
            resultParam = userInfoService.insert(infoEntity, request, response);
            if (resultParam != null && resultParam.getCode() != 0) {
                redisService.set(entity.getUser_id() + "userinfo", JSONObject.toJSONString(infoEntity));
            }
        } else {
            return ResultUtil.error(UserEum.user_10014.getCode(), UserEum.user_10014.getDesc());
        }
        return resultParam;
    }

    private String getCode(Long user_id) {
        return GenSerial.generateNewCode(8, user_id.intValue(), 5, 2);
    }


    /**
     * 清空签名
     *
     * @param param
     */
    @Override
    public ResultParam removeSign(UserEntity param) {
        try {
            redisService.remove("usersign" + param.getUser_id());
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(1001, "失败");
    }

    @Override
    public ResultParam update(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(param.getNick_name())) {
                param.setNick_name(EmojiParser.parseToHtmlDecimal(param.getNick_name()));
            }
            if (param.getLogin_name() != null && !"".equals(param.getLogin_name()) &&
                    !"".equals(param.getLogin_pass()) && param.getLogin_pass() != null) {
                UserEntity entity = new UserEntity();
                entity.setLogin_name(param.getLogin_name());
//                entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                entity.setPageSize(1);
                List<UserEntity> userEntities = mapper.queryListPage(entity);
                if (userEntities != null && userEntities.size() > 0) {
                    param.setUser_id(userEntities.get(0).getUser_id());
                } else {
                    return ResultUtil.error(UserEum.user_10018.getCode(), UserEum.user_10018.getDesc());
                }
                String str = MD5Util.GetMD5Code(param.getLogin_name() + param.getLogin_pass());
                param.setLogin_pass(str);
            } else if (param.getSecurity_pass() != null && !"".equals(param.getSecurity_pass())) {
                UserEntity entity = super.findById(param.getUser_id(), request, response);
                if (entity.getLogin_name().contains("@")) {
                    if (!mailService.isValidEmail(entity.getLogin_name())) {
                        return ResultUtil.error(UserEum.user_10017.getCode(), UserEum.user_10017.getDesc());
                    }
                    ResultParam resultParam1 = mailService.validate(entity.getLogin_name(), param.getVerification_code());
                    if (resultParam1.getCode() != 0) {
                        return resultParam1;
                    } else {
                        String str = MD5Util.GetMD5Code(param.getSecurity_pass());
                        param.setSecurity_pass(str);
                    }
                } else {
                    if (StringUtil.isEmpty(param.getVerification_code())) {
                        return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
                    }
                    ResultParam resultParam = smsService.validate(entity.getLogin_name(), param.getVerification_code());
                    if (resultParam.getCode() != 0) {
                        return resultParam;
                    } else {
                        String str = MD5Util.GetMD5Code(param.getSecurity_pass());
                        param.setSecurity_pass(str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.update(param, request, response);
    }

    @Override
    public void updateUserOnlineState(Map<String, Object> param) {
        try {
            UserEntity entity = new UserEntity();
            entity.setUser_id(Long.parseLong(param.get("user_id").toString()));
            entity.setPageSize(1);
            if (param.containsKey("")) {
//                entity.setIs_teach_paypal(param.get("is_teach_paypal").toString().equals("true"));
            }
            List<UserEntity> userEntities = mapper.queryListPage(entity);
            if (userEntities != null && userEntities.size() > 0) {
                if ("Login".equalsIgnoreCase(param.get("logonStatus").toString())) {
                    entity.setOnline_state(1);
                    mapper.updateByPrimaryKeySelective(entity);
                }
                if ("Logout".equalsIgnoreCase(param.get("logonStatus").toString())) {
                    entity.setOnline_state(0);
                    mapper.updateByPrimaryKeySelective(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserServiceImpl  queueUpdateUser");
        }
    }

    @Override
    public ResultParam verificationSecurityPass(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean aBoolean = false;
        try {
            String str = MD5Util.GetMD5Code(param.getSecurity_pass());
            param.setSecurity_pass(str);
            param.setPageSize(1);
//            param.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
            List<UserEntity> userEntities = mapper.queryListPage(param);
            if (userEntities != null && userEntities.size() > 0) {
                if (param.getIs_teenagers() != null && param.getIs_teenagers()) {
                    UserInfoEntity infoEntity = userInfoService.findById(param.getUser_id(), request, response);
                    if (infoEntity != null) {
                        infoEntity.setIs_teenagers(!infoEntity.getIs_teenagers());
                        int ret = userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                        if (ret > 0) {
                            aBoolean = true;
                        }
                    }
                } else {
                    aBoolean = true;
                }
            } else {
                return ResultUtil.error(UserEum.user_10033.getCode(), UserEum.user_10033.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserServiceImpl  verificationSecurityPass");
        }
        return ResultUtil.success(aBoolean);
    }

    @Override
    public ResultParam securityPassIsNull(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean aBoolean = false;
        try {
            System.out.println(JSONUtils.obj2Json(param));
            UserEntity entity = super.findById(param.getUser_id(), request, response);
            System.out.println(JSONUtils.obj2Json(entity));
            if (entity != null) {
                if (entity.getSecurity_pass() != null && !Objects.equals(entity.getSecurity_pass(), "")) {
                    aBoolean = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserServiceImpl  verificationSecurityPass");
        }
        return ResultUtil.success(aBoolean);
    }

    /**
     * 方法描述：wx第三方登录验证
     *
     * @param param
     * @return
     * @author zhouweixiang
     * @datetime 2019年10月16日16:35:50
     */
    @Override
    public ResultParam openIdLoginVerification(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(param.getNick_name())) {
            param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
            param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
        }
        Map<String, Object> map = Maps.newHashMap();
        com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenid(param.getVerification_code());
        Map<String, Object> mapSessionKey = JSONUtils.obj2Map(string, null);
        try {
            UserEntity userEntity = new UserEntity();
            //userEntity.setQq_openid(param.getQq_openid());
            if (mapSessionKey.containsKey("openid") && mapSessionKey.get("openid") != null) {
                param.setWx_openid(mapSessionKey.get("openid").toString());
                userEntity.setWx_openid(mapSessionKey.get("openid").toString());
                userEntity.setPageSize(1);
//                userEntity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                List<UserEntity> userEntities = mapper.queryListPage(userEntity);
                if (userEntities != null && userEntities.size() > 0) {
                    if (userEntities.get(0).getStatus() == 3) {
                        return ResultUtil.error(UserEum.user_10034.getCode(), UserEum.user_10034.getDesc());
                    }
                    param.setUser_id(userEntities.get(0).getUser_id());
                    //聊天sign
                    String sign = redisService.getStr("usersign" + param.getUser_id());
                    if (sign != null && !"".equals(sign)) {
                        map.put("userSign", sign);
                    } else {
                        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                        redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                        map.put("userSign", userSign);
                    }

                    //登录刷新token
                    String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                    redisService.set(userToken, param.getUser_id().toString(), 86400);
                    map.put("userToken", userToken);
                    map.put("is_login", true);
                    map.put("message", "登陆成功");
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(param.getUser_id());
                    List<UserInfoEntity> infoEntities = userInfoService.query(infoEntity, request, response);
                    if (infoEntities != null && userEntities.size() > 0) {
                        infoEntity = infoEntities.get(0);
                        if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                            infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                            infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                        }
                        map.put("userInfoEntity", infoEntity);
                    } else {
                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                    }
                } else {
                    map.put("is_login", false);
                }
            } else {
                map.put("is_login", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserServiceImpl openIdLoginVerification");
        }
        return ResultUtil.success(map);
    }

    /**
     * 方法描述：wxcode登录验证
     *
     * @param param
     * @return
     * @author zhouweixiang
     * @datetime 2019年10月8日17:26:01
     */
    @Override
    public ResultParam openIdVerification(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(param.getNick_name())) {
            param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
            param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
        }
        Map<String, Object> map = new HashMap<>();
        com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenid(param.getWx_code());
        Map<String, Object> mapOpenId = JSONUtils.obj2Map(string, null);
        try {
            if (mapOpenId.containsKey("openid") && mapOpenId.get("openid") != null) {
                UserEntity entity = new UserEntity();
                entity.setPageSize(1);
                entity.setStatus(1);
                entity.setWx_openid(mapOpenId.get("openid").toString());
//                entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                List<UserEntity> userEntities = mapper.queryListPage(entity);
                if (userEntities != null && userEntities.size() > 0) {
                    param.setUser_id(userEntities.get(0).getUser_id());
                    if (userEntities.get(0).getLogin_name() != null && !"".equals(userEntities.get(0).getLogin_name())) {
                        map.put("result", false);
                        /*
                        String sign = redisService.getStr("usersign" + param.getUser_id());
                        if (sign != null && !"".equals(sign)) {
                            map.put("userSign", sign);
                        } else {
                            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                            String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                            redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                            map.put("userSign", userSign);
                        }

                         */
                        //登录刷新token
                        String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                        redisService.set(userToken, param.getUser_id().toString(), 86400);
                        map.put("userToken", userToken);
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setUser_id(param.getUser_id());
                        List<UserInfoEntity> infoEntities = userInfoService.query(infoEntity, request, response);
                        if (infoEntities != null && infoEntities.size() > 0) {
                            infoEntity = infoEntities.get(0);
                            if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                                infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                                infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                            }
                            map.put("userInfoEntity", infoEntity);
                        } else {
                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                        }
                    } else {
                        map.put("result", true);
                    }
                } else {
                    map.put("result", true);
                }
                map.put("opend_id", mapOpenId.get("openid"));
            } else {
                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    /**
     * 方法描述：wxopenid登录验证
     *
     * @param param
     * @return
     * @author zhouweixiang
     * @datetime 2019年10月8日17:26:01
     */
    @Override
    public ResultParam openIdAppVerification(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(param.getNick_name())) {
            param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
            param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
        }
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (StringUtil.isNotEmpty(param.getCode())) {
                com.alibaba.fastjson.JSONObject string = wxPayService.getSessionKeyOropenidTAppid(param.getCode());
                Map<String, Object> map1 = JSONUtils.obj2Map(string, null);
                //System.out.println(map.toString());
                param.setWx_openid(map1.get("openid").toString());
                map.put("open_id", map1.get("openid").toString());
            }

            UserEntity entity = new UserEntity();
            entity.setPageSize(1);
            entity.setStatus(1);
            entity.setWx_openid(param.getWx_openid());
//            entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
            List<UserEntity> userEntities = mapper.queryListPage(entity);
            if (userEntities != null && userEntities.size() > 0) {
                if (userEntities.get(0).getStatus() == 3) {
                    return ResultUtil.error(UserEum.user_10034.getCode(), UserEum.user_10034.getDesc());
                }
                param.setUser_id(userEntities.get(0).getUser_id());
                if (!StringUtil.isEmpty(userEntities.get(0).getLogin_name())) {
                    map.put("result", false);
//                    String sign = redisService.getStr("usersign" + param.getUser_id());
//                    if (sign != null && !"".equals(sign)) {
//                        map.put("userSign", sign);
//                    } else {
                    TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                    String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                    redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                    map.put("userSign", userSign);
//                    }

                    //登录刷新token
                    String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                    redisService.set(userToken, param.getUser_id().toString(), 86400);
                    map.put("userToken", userToken);
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(param.getUser_id());
                    infoEntity.setPageSize(1);
                    List<UserInfoEntity> infoEntities = userInfoService.queryListPage(infoEntity, request, response);
                    if (infoEntities != null && infoEntities.size() > 0) {
                        infoEntity = infoEntities.get(0);
                        if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                            infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                            infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                        }
                        map.put("userInfoEntity", infoEntity);
                    } else {
                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                    }
                } else {
                    map.put("result", true);
                }
            } else {
                map.put("result", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    /**
     * 方法描述：wx第三方登录/绑定手机号
     *
     * @param param
     * @return
     * @author zhouweixiang
     * @datetime 2019年10月8日17:26:01
     */
    @Override
    @Transactional
    public ResultParam openIdLoginOrRegist(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(param.getNick_name())) {
            param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
            param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
        }

        Map<String, Object> map = Maps.newHashMap();
        //System.out.println(map.toString());
        try {
            if (StringUtil.isEmpty(param.getLogin_name())) {
                return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
            }
            ResultParam validate = smsService.validate(param.getLogin_name(), param.getVerification_code());
            if (validate.getCode() != 0) {
                return validate;
            }
            if (StringUtil.isEmpty(param.getPlatform())) {
                param.setRegister_platform(param.getPlatform());
            }

            UserEntity entity = new UserEntity();
            entity.setPageSize(1);
            entity.setStatus(1);
//            entity.setUser_id(param.getUser_id());
            if (StringUtil.isNotEmpty(param.getLogin_name())) {
                /*
                if (!StringUtil.isEmpty(param.getApple_id())) {
                    entity.setApple_id(param.getApple_id());
                } else if (!StringUtil.isEmpty(param.getWx_openid())) {
                    entity.setWx_openid(param.getWx_openid());
                }

                 */
                entity.setLogin_name(param.getLogin_name());
//                entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                List<UserEntity> userEntities = mapper.queryListPage(entity);
                if (userEntities != null && userEntities.size() > 0) {
                    param.setUser_id(userEntities.get(0).getUser_id());
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        //聊天sign
//                        String sign = redisService.getStr("usersign" + param.getUser_id());
//                        if (sign != null && !"".equals(sign)) {
//                            map.put("userSign", sign);
//                        } else {
                        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                        redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                        map.put("userSign", userSign);
//                        }
                    }

                    //登录刷新token
                    String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                    redisService.set(userToken, param.getUser_id().toString(), 86400);
                    map.put("userToken", userToken);
                    map.put("type", "login");
                    map.put("message", "登陆成功");
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(param.getUser_id());
                    infoEntity.setPageSize(1);
                    List<UserInfoEntity> infoEntities = userInfoService.queryListPage(infoEntity, request, response);
                    if (infoEntities != null && infoEntities.size() > 0) {
                        infoEntity = infoEntities.get(0);
                        infoEntity.setSex(param.getSex());
                        infoEntity.setAvatar(param.getAvatar());
                        infoEntity.setNick_name(param.getNick_name());
                        infoEntity.setMobile(param.getLogin_name());
                        userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                        infoEntity = infoEntities.get(0);
                        if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                            infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                            infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                        }
                        map.put("userInfoEntity", infoEntity);

                    } else {
                        UserInfoEntity userInfoEntity = new UserInfoEntity();
                        userInfoEntity.setUser_id(param.getUser_id());
                        userInfoEntity.setSex(param.getSex());
                        userInfoEntity.setAvatar(param.getAvatar());
                        userInfoEntity.setMobile(param.getLogin_name());
                        userInfoEntity.setNick_name(param.getNick_name());
                        userInfoEntity.setIs_teach_paypal(param.getIs_teach_paypal());
                        userInfoMapper.insertSelective(userInfoEntity);
                        if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                            infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                            infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                        }
                        map.put("userInfoEntity", userInfoEntity);
//                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                    }
//                    }
                } else {
                    int i = mapper.insertSelective(param);
                    if (i > 0) {
                        ResultParam resultParam = insert(param, request, response);
                        if (resultParam.getCode() == 0) {
                            //腾讯云注册
                            UserInfoEntity userInfoEntity = new UserInfoEntity();
                            userInfoEntity.setUser_id(param.getUser_id());
                            int ret = userInfoService.TIMregister(userInfoEntity);//todo
                            if (ret == 1) {
                                //聊天sign
//                                String sign = redisService.getStr("usersign" + param.getUser_id());
//                                if (sign != null && !"".equals(sign)) {
//                                    map.put("userSign", sign);
//                                } else {
                                TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                                String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                                redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                                map.put("userSign", userSign);
//                                }

                            }

                            //登录刷新token
                            String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                            redisService.set(userToken, param.getUser_id().toString(), 86400);
                            map.put("userToken", userToken);
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setUser_id(param.getUser_id());
                            infoEntity.setPageSize(1);
                            List<UserInfoEntity> infoEntities = userInfoMapper.queryListPage(infoEntity);
                            if (infoEntities != null && infoEntities.size() > 0) {
                                infoEntity = infoEntities.get(0);
                                if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                                    infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                                    infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                                }
                                map.put("userInfoEntity", infoEntity);
                            } else {
                                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                            }
                            //注册奖励
                            UserOrderEntity entity1 = new UserOrderEntity();//明细订单记录
                            entity1.setPay_count(new BigDecimal("50"));
                            entity1.setPayment_id(param.getUser_id());
                            entity1.setType("register");
                            orderService.insert(entity1, request, response);
                            UserInfoEntity info = new UserInfoEntity();
                            info.setUser_id(param.getUser_id());
                            info.setCat_coin(new BigDecimal("50"));//增加猫币
                            userInfoMapper.updateAddCatCoin(info);
                            map.put("reward", infoEntity.getCat_coin());
//                        map.put("TIMMessage", ret);
                            map.put("message", "注册成功");
                            map.put("type", "register");
                        } else {
                            return resultParam;
                        }
                    }
                }
            } else if (StringUtil.isNotEmpty(param.getWx_login_name()) || StringUtil.isNotEmpty(param.getApple_id())) {
//                ResultParam validate = smsService.validate(param.getLogin_name(), param.getVerification_code());
//                if (validate.getCode() != 0) {
//                    return validate;
//                }
//                    entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                /*
                List<UserEntity> userEntities = mapper.queryListPage(entity);
                if (userEntities != null && userEntities.size() > 0) {

                 */
                UserEntity userEntity = new UserEntity();
                userEntity.setPageSize(1);
                userEntity.setStatus(1);
//                    userEntity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                if (!StringUtil.isEmpty(param.getApple_id())) {
                    userEntity.setApple_id(param.getApple_id());
                } else if (!StringUtil.isEmpty(param.getWx_openid())) {
                    userEntity.setWx_openid(param.getWx_openid());
                }
                List<UserEntity> userEntityList = mapper.queryListPage(userEntity);
                if (userEntityList != null && userEntityList.size() > 0) {
                    param.setUser_id(userEntityList.get(0).getUser_id());
                    int ret = mapper.updateByPrimaryKeySelective(param);
                    if (ret > 0) {
                        //聊天sign
//                        String sign = redisService.getStr("usersign" + param.getUser_id());
//                        if (StringUtil.isNotEmpty(sign)) {
//                            map.put("userSign", sign);
//                        } else {
                        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                        String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                        redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                        map.put("userSign", userSign);
//                        }
                    }

                    //登录刷新token
                    String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                    redisService.set(userToken, param.getUser_id().toString(), 86400);
                    map.put("userToken", userToken);
                    map.put("type", "login");
                    map.put("message", "登陆成功");
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(param.getUser_id());
                    infoEntity.setPageSize(1);
                    List<UserInfoEntity> infoEntities = userInfoService.queryListPage(infoEntity, request, response);
                    if (infoEntities != null && infoEntities.size() > 0) {
                        infoEntity = infoEntities.get(0);
                        infoEntity.setSex(param.getSex());
                        infoEntity.setAvatar(param.getAvatar());
                        infoEntity.setNick_name(param.getNick_name());
                        userInfoMapper.updateByPrimaryKeySelective(infoEntity);
                        infoEntity = userInfoMapper.selectByPrimaryKey(infoEntity.getUser_id());
                        if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                            infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                            infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                        }
                        map.put("userInfoEntity", infoEntity);
                    } else {
                        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                    }
//                }
//                    }

                     /*
                } else {
                    UserEntity userEntity1 = new UserEntity();
                    userEntity1.setPageSize(1);
                    userEntity1.setStatus(1);
                    if (!StringUtil.isEmpty(param.getApple_id())) {
                        userEntity1.setApple_id(param.getApple_id());
                    } else if (!StringUtil.isEmpty(param.getWx_openid())) {
                        userEntity1.setWx_openid(param.getWx_openid());
                    }
                    userEntity1.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                    List<UserEntity> userEntityList1 = mapper.queryListPage(userEntity);
                    if (userEntityList1 != null && userEntityList.size() > 0) {
                        param.setUser_id(userEntityList1.get(0).getUser_id());
                        int ret1 = mapper.updateByPrimaryKeySelective(param);
                        if (ret1 > 0) {
                            /*
                            //聊天sign
                            String sign = redisService.getStr("usersign" + param.getUser_id());
                            if (StringUtil.isNotEmpty(sign)) {
                                map.put("userSign", sign);
                            } else {
                                TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                                String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                                redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                                map.put("userSign", userSign);
                            }

                            //登录刷新token
                            String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                            redisService.set(userToken, param.getUser_id().toString(), 86400);
                            map.put("userToken", userToken);
                            map.put("type", "login");
                            map.put("message", "登陆成功");
                            UserInfoEntity infoEntity = new UserInfoEntity();
                            infoEntity.setUser_id(param.getUser_id());
                            infoEntity.setPageSize(1);
                            List<UserInfoEntity> infoEntities = userInfoService.queryListPage(infoEntity, request, response);
                            if (infoEntities != null && infoEntities.size() > 0) {
                                map.put("userInfoEntity", infoEntities.get(0));
                            } else {
                                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                            }
                        }
                                                     */
                }
            } else {
//                    ResultParam resultParam = insert(param, request, response);
//                    if (resultParam.getCode() == 0) {
                //腾讯云注册
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                userInfoEntity.setUser_id(param.getUser_id());
                            /*
                            int ret = userInfoService.TIMregister(userInfoEntity);
                            if (ret == 1) {
                                //聊天sign
                                String sign = redisService.getStr("usersign" + param.getUser_id());
                                if (StringUtil.isNotEmpty(sign)) {
                                    map.put("userSign", sign);
                                } else {
                                    TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                                    String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                                    redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                                    map.put("userSign", userSign);
                                }
                            }

                             */

                if (param.getUser_id() == null) {
                    this.insert(param, request, response);
                }
                UserInfoEntity infoEntity = new UserInfoEntity();
                infoEntity.setUser_id(param.getUser_id());
                infoEntity.setPageSize(1);
                List<UserInfoEntity> infoEntities = userInfoMapper.queryListPage(infoEntity);
                infoEntity = infoEntities.get(0);
                if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                    infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                    infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                }
                map.put("userInfoEntity", infoEntity);

                //登录刷新token
                String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                redisService.set(userToken, param.getUser_id().toString(), 86400);
                map.put("userToken", userToken);
                //注册奖励
                UserOrderEntity entity1 = new UserOrderEntity();//明细订单记录
                entity1.setPay_count(new BigDecimal("50"));
                entity1.setPayment_id(param.getUser_id());
                entity1.setType("register");
                orderService.insert(entity1, request, response);
                UserInfoEntity info = new UserInfoEntity();
                info.setUser_id(param.getUser_id());
                if (param.getIs_teach_paypal()) {
                    info.setPoints(50);
                    userInfoMapper.updateAddPoint(info);
                } else {
                    info.setCat_coin(new BigDecimal("50"));//增加猫币
                    userInfoMapper.updateAddCatCoin(info);
                }
                map.put("reward", infoEntity.getCat_coin());
//                            map.put("TIMMessage", ret);
                map.put("message", "注册成功");
                map.put("type", "register");
                //                    String sign = redisService.getStr("usersign" + param.getUser_id());
//                    if (sign != null && !"".equals(sign)) {
//                        map.put("userSign", sign);
//                    } else {
                TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                map.put("userSign", userSign);
//                    }
//                    } else {
//                        return resultParam;
//                    }
            }
//            }
//        }
        } catch (
                Exception e) {
            e.printStackTrace();
            logger.info("UserServiceImpl  openIdLoginOrRegist");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        map.put("user_id", param.getUser_id());
        return ResultUtil.success(map);
    }


    /**
     * 方法描述：apple第三方登录
     *
     * @param param
     * @return
     * @author zhouweixiang
     * @datetime 2019年10月8日17:26:01
     */
    @Override
    public ResultParam appleIdLoginOrRegist(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        //构建client_secret关键代码：
        try {
            if (StringUtil.isNotEmpty(param.getNick_name())) {
                param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
                param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
            }
            Map<String, Object> map = Maps.newHashMap();
            try {
                if (StringUtil.isEmpty(param.getPlatform())) {
                    param.setRegister_platform(param.getPlatform());
                }
                UserEntity entity = new UserEntity();
                entity.setPageSize(1);
                entity.setStatus(1);
                entity.setApple_id(param.getApple_id());
//                entity.setIs_teach_paypal(param.getIs_teach_paypal() != null && param.getIs_teach_paypal());
                List<UserEntity> userEntities = mapper.queryListPage(entity);
                if (userEntities != null && userEntities.size() > 0) {
                    param.setUser_id(userEntities.get(0).getUser_id());
                    if (!StringUtil.isEmpty(userEntities.get(0).getLogin_name())) {
                        if (userEntities.get(0).getStatus() == 3) {
                            return ResultUtil.error(UserEum.user_10034.getCode(), UserEum.user_10034.getDesc());
                        }
                        map.put("result", false);
                        /*
                        String sign = redisService.getStr("usersign" + param.getUser_id());
                        if (StringUtil.isNotEmpty(sign)) {
                            map.put("userSign", sign);
                        } else {
                            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
                            String userSign = tlsSigAPIv2.genSig(param.getUser_id().toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
                            redisService.set("usersign" + param.getUser_id(), userSign, timConfig.getExpire());
                            map.put("userSign", userSign);
                        }

                         */
                        //登录刷新token
                        String userToken = "usertoken" + TokenUtils.createJWT(param.getUser_id().toString(), "user", "qmore", -1);
                        redisService.set(userToken, param.getUser_id().toString(), 86400);
                        map.put("userToken", userToken);
                        UserInfoEntity infoEntity = new UserInfoEntity();
                        infoEntity.setUser_id(param.getUser_id());
                        List<UserInfoEntity> infoEntities = userInfoService.query(infoEntity, request, response);
                        if (infoEntities != null && infoEntities.size() > 0) {
                            infoEntity = infoEntities.get(0);
                            if (StringUtil.isNotEmpty(infoEntity.getNick_name())){
                                infoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(infoEntity.getNick_name()));
                                infoEntity.setNick_name(EmojiParser.parseToUnicode(infoEntity.getNick_name()));
                            }
                            map.put("userInfoEntity", infoEntity);
                        } else {
                            return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                        }
                    } else {
                        map.put("result", true);
                    }
                } else {
                    map.put("result", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultUtil.success(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();

    }

    @Override
    public ResultParam queryByMessage(UserEntity param, HttpServletRequest request, HttpServletResponse
            response) {
        List<UserEntity> list = null;
        try {
            list = mapper.queryByMessage(param);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(list);
    }

    @Override
    public ResultParam queryRegistDayTotal(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            param.setRegister_platform("ios");
            Integer iosDayTotal = mapper.queryDayTotal(param);
            map.put("iosRegistDayTotal", iosDayTotal);

            param.setRegister_platform("android");
            Integer androidRegistTotal = mapper.queryDayTotal(param);
            map.put("androidRegistTotal", androidRegistTotal);

            param.setRegister_platform("pc");
            Integer pcRegistTotal = mapper.queryDayTotal(param);
            map.put("pcRegistTotal", pcRegistTotal);

            param.setRegister_platform(null);
            param.setLogin_type("3");
            Integer ThirdPartyRegistTotal = mapper.queryDayTotal(param);
            map.put("ThirdPartyRegistTotal", ThirdPartyRegistTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public ResultParam queryRegistCount(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Integer DayTotal = mapper.queryDayTotal(param);
            map.put("dayCount", DayTotal);

            Integer monthTotal = mapper.queryMonthTotal(param);
            map.put("monthTotal", monthTotal);

            Integer total = mapper.queryListPageCount(param);
            map.put("total", total);

            param.setRegister_platform("teach_paypal_android");
            Integer androidRegistTotal = mapper.queryListPageCount(param);
            map.put("androidRegistTotal", androidRegistTotal);

            param.setRegister_platform("teach_paypal_ios");
            Integer iosRegistTotal = mapper.queryListPageCount(param);
            map.put("iosRegistTotal", iosRegistTotal);

            param.setRegister_platform("pc");
            Integer pcRegistTotal = mapper.queryListPageCount(param);
            map.put("pcRegistTotal", pcRegistTotal);

            param.setRegister_platform("wx");
            Integer wxRegistTotal = mapper.queryListPageCount(param);
            map.put("wxRegistTotal", wxRegistTotal);

            map.put("elseTotal", total - iosRegistTotal - pcRegistTotal - wxRegistTotal - androidRegistTotal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public Map<String, Object> queryByLoginName(String login_name, HttpServletRequest
            request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            List<Long> userInfoEntities = mapper.queryIdByLoginName(login_name);
            map.put("userIds", userInfoEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public ResultParam setUserSign(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
        if (map.containsKey("userSign")) {
            String str = map.get("userSign").toString();
            String user_id = map.get("user_id").toString();
            str = str.replaceAll("\r|\n", "");
            redisService.set("usersign" + user_id, str, timConfig.getExpire());
        } else {
            String sign = redisService.getStr("usersign" + map.get("user_id").toString());
            if (StringUtil.isNotEmpty(sign)) {
                redisService.del("usersign" + map.get("user_id").toString());
            }
            TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(timConfig.getSdkAppId(), timConfig.getSecretKey());
            String userSign = tlsSigAPIv2.genSig(map.get("user_id").toString(), timConfig.getExpire()).replaceAll("\r|\n", "");
            redisService.set("usersign" + map.get("user_id").toString(), userSign, timConfig.getExpire());
            map.put("userSign", userSign);
        }
        return ResultUtil.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam insertMechanismMaster(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isNotEmpty(param.getNick_name())) {
                param.setNick_name(EmojiUtil.resolveToByteFromEmoji(param.getNick_name()));
                param.setNick_name(EmojiParser.parseToUnicode(param.getNick_name()));
            }
            if (param.getUser_id() == null) {
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setLogin_pass(param.getLogin_pass());

                String str = MD5Util.GetMD5Code(//帐号
                        param.getLogin_name() + param.getLogin_pass()//+密码
                );//md5加密
                param.setLogin_pass(str);//设置密码
                param.setRegister_platform("teach_paypal_mechanism_master");
                ResultParam insert = this.insert(param, request, response);
                if (insert.getCode() != 0 || insert.getData() != null) {
                    return insert;
                }
                masterInfoEntity.setUser_id(param.getUser_id());
                masterInfoEntity.setType("teach_paypal");
                masterInfoEntity.setStatus(2);
                masterInfoEntity.setFull_name(param.getNick_name());
                masterInfoEntity.setMechanism_id(param.getMechanism_id());
                masterInfoEntity.setSex(param.getSex());
                masterInfoEntity.setPhoto(param.getAvatar());
                masterInfoEntity.setMobile(param.getMobile());
                masterInfoEntity.setLogin_name(param.getLogin_name());
                int i = masterInfoMapper.insertSelective(masterInfoEntity);
                if (i > 0) {
                    return ResultUtil.success();
                }
            } else {
                UserEntity userEntity = mapper.selectByPrimaryKey(param.getUser_id());
                String str = MD5Util.GetMD5Code(//帐号
                        param.getLogin_name() + param.getLogin_pass()//+密码
                );//md5加密
                MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
                masterInfoEntity.setLogin_pass(param.getLogin_pass());

                userEntity.setLogin_pass(str);
                int i = mapper.updateByPrimaryKeySelective(userEntity);
                if (i > 0) {
                    masterInfoEntity.setUser_id(param.getUser_id());
                    masterInfoEntity.setPageSize(1);
                    masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
                    masterInfoEntity.setUser_id(param.getUser_id());
//                    masterInfoEntity.setType("teach_paypal");
                    masterInfoEntity.setStatus(param.getStatus());
                    masterInfoEntity.setFull_name(param.getNick_name());
                    masterInfoEntity.setMechanism_id(param.getMechanism_id());
                    masterInfoEntity.setSex(param.getSex());
                    masterInfoEntity.setPhoto(param.getAvatar());
                    masterInfoEntity.setMobile(param.getMobile());
                    masterInfoEntity.setLogin_name(param.getLogin_name());
                    int res = masterInfoMapper.updateByPrimaryKeySelective(masterInfoEntity);
                    if (res > 0) {
                        return ResultUtil.success();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam updateMechanismMaster(UserEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            UserEntity userEntity = mapper.selectByPrimaryKey(param.getUser_id());
            userEntity.setLogin_name(param.getLogin_name());
            userEntity.setLogin_pass(param.getLogin_pass());
            mapper.updateByPrimaryKeySelective(userEntity);

            UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(param.getUser_id());
            userInfoEntity.setAvatar(param.getAvatar());
            userInfoEntity.setNick_name(param.getNick_name());
            userInfoEntity.setMobile(param.getMobile());
            int i1 = userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);

            MasterInfoEntity masterInfoEntity = new MasterInfoEntity();
            masterInfoEntity.setUser_id(param.getUser_id());
            masterInfoEntity.setPageSize(1);
            masterInfoEntity = masterInfoMapper.queryListPage(masterInfoEntity).get(0);
            masterInfoEntity.setFull_name(param.getNick_name());
            masterInfoEntity.setSex(param.getSex());
            masterInfoEntity.setPhoto(param.getAvatar());
            masterInfoEntity.setMobile(param.getMobile());
            masterInfoEntity.setLogin_name(param.getLogin_name());
            masterInfoEntity.setLogin_pass(param.getLogin_pass());
            if (param.getStatus() != null && param.getStatus() != 0) {
                masterInfoEntity.setStatus(param.getStatus());
            }
            int i = masterInfoMapper.updateByPrimaryKeySelective(masterInfoEntity);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    public ResultParam queryUserInfoByLoginName(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            userEntity.setPageSize(1);
//            userEntity.setIs_teach_paypal(userEntity.getIs_teach_paypal() != null && userEntity.getIs_teach_paypal());
            List<UserEntity> userEntities = mapper.queryListPage(userEntity);
            if (userEntities != null && userEntities.size() > 0) {
                return ResultUtil.success(JSONUtils.obj2Json(redisService.getStr(userEntities.get(0).getUser_id() + "userinfo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized ResultParam insertH5GetCoupon(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtil.isEmpty(userEntity.getVerification_code())) {//获得验证码
                return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
            }
            if (CommonUtils.isPhone(userEntity.getLogin_name())) {//验证登录名
                ResultParam resultParam1 = smsService.validate(userEntity.getLogin_name(), userEntity.getVerification_code());
                if (resultParam1.getCode() != 0) {
                    return resultParam1;
                } else {
                    userEntity.setRegister_platform("get_coupon");
                    ResultParam insert = this.insert(userEntity, request, response);
                    if (insert.getData() != null) {
                        userEntity.setPageSize(1);
                        userEntity = mapper.queryListPage(userEntity).get(0);
                    }
                    UserInfoEntity userInfoEntity = userInfoMapper.selectByPrimaryKey(userEntity.getUser_id());
                    if (userInfoEntity.getIs_collection()) {
                        return ResultUtil.error(UserEum.user_10047.getCode(), UserEum.user_10047.getDesc());
                    } else {
                        UserCouponEntity userCouponEntity = new UserCouponEntity();
                        userCouponEntity.setUser_id(userEntity.getUser_id());
                        return userCouponService.insertUserCollection(userCouponEntity, request, response);
                    }
                }
            } else {
                return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    @Override
    public ResultParam queryMd5(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(MD5Util.GetMD5Code(userEntity.getLogin_name() + userEntity.getLogin_pass()));
        return ResultUtil.success();
    }


    public static void main(String[] args) {
       /* String str="eJyrVgrxCdZLrSjILEpVsjIytTQyMDDQAQuWpRYpWSkZ6RkoQfjFKdmJBQWZKUpWhiYGBkampgYm\n5hCZzJTUvJLMtEywBksDS0uYjsx0oECiS4RJuLm2t3l6SbZblUFOubdReXJhaKCJf0ZYsru-Z0WQ\nv3Opn2FAeXGoLVRjSWYu0DWGphaWRmbGpkYGtQCQZC-*";
        System.out.println(str);
        System.out.println( str.replaceAll("\r|\n", ""));

*/
//        System.out.println(MD5Util.GetMD5Code("13956398573123456"));
        /*try{
            Jedis jedis = new Jedis("101.37.66.151",6379);
            jedis.auth("123456");

            System.out.println("connect successfully !");
            for (int i=0;true;i++) {
                String ob=jedis.get("usersign" + i);
                if(ob!=null&&ob!=""){
                    System.out.println(ob);
                    Long aLong=jedis.del("usersign" + i);
                    System.out.println(aLong);
                }

            }

        }catch(Exception e){
            System.out.println(e);
        }*/
    }
}