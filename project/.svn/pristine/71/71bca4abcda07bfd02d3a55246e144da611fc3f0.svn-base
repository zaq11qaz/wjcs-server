package com.huihe.eg.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.cy.framework.util.safe.MD5Util;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.mall.model.UserInfoEntity;
import com.huihe.eg.mall.model.UserLoginEntity;
import com.huihe.eg.mall.mybatis.dao.UserLoginMapper;
import com.huihe.eg.mall.service.dao.UserInfoService;
import com.huihe.eg.mall.service.dao.UserLoginService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.mall.service.impl.mail.MailService;
import com.huihe.eg.mall.service.impl.sms.SmsService;
import com.tencentyun.TLSSigAPIv2;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLoginServiceImpl extends BaseFrameworkServiceImpl<UserLoginEntity, Long> implements UserLoginService {

    @Resource
    private UserLoginMapper mapper;
    @Resource
    private SmsService smsService;
    @Resource
    private MailService mailService;
    @Resource
    private RedisService redisService;
    @Resource
    private UserInfoService userInfoService;
    public void init() {
        setMapper(mapper);
    }
    @Override
    public ResultParam register(UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam =new ResultParam();
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isEmpty(entity.getLogin_name())) {
            return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
        }
        if (StringUtil.isEmpty(entity.getVerification_code())) {
            return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
        }
        if (CommonUtils.isPhone(entity.getLogin_name())) {//验证登录名
            ResultParam resultParam1 = smsService.validate(entity.getLogin_name(), entity.getVerification_code());
            if (resultParam1.getCode() != 0) {
                return resultParam1;
            }
            List<UserLoginEntity> userLoginEntities=mapper.queryListPage(entity);
            if(userLoginEntities!=null&&userLoginEntities.size()>0){
                UserInfoEntity infoEntity=userInfoService.findById(userLoginEntities.get(0).getUser_id(),request,response);
                map.put("infoEntity",infoEntity);
                map.put("message", "登陆成功");
                map.put("status","login");
                resultParam.setData(map);
            }else{
                resultParam = insert(entity, request, response);
                if (resultParam.getCode() != 0) {
                    return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
                }
                map.put("infoEntity", entity);
                map.put("message", "注册成功");
                map.put("status","register");
                resultParam.setData(map);
            }
        } else {
            return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
        }
        return resultParam;
    }
    public static void main(String[] args) {
        System.out.println(CommonUtils.isPhone("15514861585"));
    }
    @Override
    public ResultParam insert(UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response) {
        List<UserLoginEntity> list = super.query(entity, request, response);
        if (list != null && list.size() > 0) {
            return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
        }
        ResultParam resultParam = super.insert(entity, request, response);
        if (resultParam.getCode() == 0 && entity.getUser_id() != null && entity.getUser_id() != 0) {
            UserInfoEntity infoEntity = new UserInfoEntity();
            infoEntity.setUser_id(entity.getUser_id());
            infoEntity.setMobile(entity.getLogin_name());
            infoEntity.setIs_teach_paypal(param.getIs_teach_paypal());
            resultParam = userInfoService.insert(infoEntity, request, response);
            if (resultParam != null && resultParam.getCode() != 0) {
                redisService.set(entity.getUser_id() + "malluserinfo", JSONObject.toJSONString(infoEntity));
            }
        } else {
            return ResultUtil.error(UserEum.user_10013.getCode(), UserEum.user_10013.getDesc());
        }
        return resultParam;
    }
    @Override
    public ResultParam login(UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isEmpty(entity.getLogin_name())) {
            return ResultUtil.error(UserEum.user_10003.getCode(), UserEum.user_10003.getDesc());
        }
        List<UserInfoEntity> infoEntities = null;
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        List<UserLoginEntity> list = null;
        if (entity.getLogin_type().equals("1") ) {
            if (StringUtil.isEmpty(entity.getLogin_pass())) {
                return ResultUtil.error(UserEum.user_10004.getCode(), UserEum.user_10004.getDesc());
            }
            StringBuilder builder = new StringBuilder(entity.getLogin_name());
            builder.append(entity.getLogin_pass());
            String str = MD5Util.GetMD5Code(builder.toString());
            entity.setLogin_pass(str);
            entity.setLogin_type(null);
            list = query(entity, request, response);
            if (list != null && list.size() > 0) {
                userInfoEntity.setUser_id(list.get(0).getUser_id());
                infoEntities = userInfoService.query(userInfoEntity, request, response);
                Map<String, Object> map = new HashMap<>();
                if (infoEntities != null && infoEntities.size() > 0) {
                    map.put("userInfoEntity", infoEntities.get(0));
                }
                return ResultUtil.success(map);
            }
        }else{
            if (CommonUtils.isPhone(entity.getLogin_name())) {//验证登录名
                if (StringUtil.isEmpty(entity.getVerification_code())) {
                    return ResultUtil.error(UserEum.user_10007.getCode(), UserEum.user_10007.getDesc());
                }
                ResultParam resultParam = smsService.validate(entity.getLogin_name(), entity.getVerification_code());
                if (resultParam.getCode() == 1) {
                    return resultParam;
                }
                entity.setLogin_type(null);
                list = query(entity, request, response);
                if (list != null && list.size() > 0) {
                    userInfoEntity.setUser_id(list.get(0).getUser_id());
                    infoEntities = userInfoService.query(userInfoEntity, request, response);
                    Map<String, Object> map = new HashMap<>();
                    if (infoEntities != null && infoEntities.size() > 0) {
                        map.put("userInfoEntity", infoEntities.get(0));
                    }
                    return ResultUtil.success(map);
                }
            } else {
                return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
            }
        }
        return ResultUtil.error(UserEum.user_10005.getCode(), UserEum.user_10005.getDesc());
    }
}