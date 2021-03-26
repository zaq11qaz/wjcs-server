package com.huihe.eg.mall.service.impl.sms;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.common.utils.HttpUtil;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.http.HttpsClientRequest;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.mall.model.SmsParam;
import com.huihe.eg.mall.model.UserLoginEntity;
import com.huihe.eg.mall.mybatis.dao.UserLoginMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SmsService {

    @Resource
    private SmsParam param;
    @Resource
    private RedisService redisService;
    @Resource
    private UserLoginMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */
    public ResultParam sendSms(String mobile,String type) {
        if (StringUtil.isEmpty(mobile)) {
            return ResultUtil.error(UserEum.user_10015.getCode(), UserEum.user_10015.getDesc());
        }
        if(type!=null&&type.equalsIgnoreCase("register")){
            UserLoginEntity entity=new UserLoginEntity();
            entity.setLogin_name(mobile);
            entity.setPageSize(1);
            List<UserLoginEntity> userEntities=userMapper.query(entity);
            if(userEntities!=null&&userEntities.size()>0){
                return ResultUtil.error(UserEum.user_10016.getCode(), UserEum.user_10016.getDesc());
            }
        }
        if (!CommonUtils.isPhone(mobile)) {
            return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
        }
        String code = redisService.getStr("sms:" + mobile);
        if (StringUtil.isNotEmpty(code)) {
            return ResultUtil.error(UserEum.user_10009.getCode(), UserEum.user_10009.getDesc());
        }
        code = generateCode();
        String msg = param.getSmsg().replace("{code}", code);
        SmsSendRequest smsSingleRequest = new SmsSendRequest(param.getSname(), param.getSpwd(), param.getPsd(),msg,mobile,"");
        String param1 = "";
        Map map = JSON.parseObject(JSON.toJSON(smsSingleRequest).toString(), Map.class);
        try {
            param1 = HttpUtil.paramToQueryString(map, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("response after request result is :"+JSON.toJSON(smsSingleRequest).toString());
        String result = HttpsClientRequest.httpsGet(param.getUrl()+"?"+param1, null);
        logger.info("response after request result is :"+result);
        Document document = Jsoup.parse(result.toString());
        Elements select = document.select("State");
        if (!select.get(0).text().substring(0,1).equals("0")) {
            return ResultUtil.error(UserEum.user_10012.getCode(), UserEum.user_10012.getDesc());
        }
        redisService.set("sms:" + mobile, code, param.getSeconds());
        return ResultUtil.success();
    }

    /**
     * 验证
     *
     * @param mobile
     * @param vali_code
     */
    public ResultParam  validate(String mobile, String vali_code) {
        String code = redisService.getStr("sms:" + mobile);
        if (StringUtil.isEmpty(code)) {
            return ResultUtil.error(UserEum.user_10010.getCode(), UserEum.user_10010.getDesc());
        }
        if (!code.equals(vali_code)) {
            return ResultUtil.error(UserEum.user_10011.getCode(), UserEum.user_10011.getDesc());
        }
        return ResultUtil.success(true);
    }

    public String generateCode() {
        String code = "";
        Random rd = new Random();
        for (int i = 0; i < 4; i++) {
            code += rd.nextInt(10);
        }
        System.out.println(code);
        return code;
    }


    /**
     * 发送邀请短信
     *
     * @param mobile
     * @return
     */
    public ResultParam sendInvitation(String mobile,String user_name) {
        if (StringUtil.isEmpty(mobile)) {
            return ResultUtil.error(UserEum.user_10015.getCode(), UserEum.user_10015.getDesc());
        }
        if (!CommonUtils.isPhone(mobile)) {
            return ResultUtil.error(UserEum.user_10006.getCode(), UserEum.user_10006.getDesc());
        }
        String msg = param.getSmsv().replace("{name}", user_name);
        SmsSendRequest smsSingleRequest = new SmsSendRequest(param.getSname(), param.getSpwd(), param.getPsd(),msg,mobile,"");
        String param1 = "";
        Map map = JSON.parseObject(JSON.toJSON(smsSingleRequest).toString(), Map.class);
        try {
            param1 = HttpUtil.paramToQueryString(map, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("response after request result is :"+JSON.toJSON(smsSingleRequest).toString());
        String result = HttpsClientRequest.httpsGet(param.getUrl()+"?"+param1, null);
        logger.info("response after request result is :"+result);
        Document document = Jsoup.parse(result.toString());
        Elements select = document.select("State");
        if (!select.get(0).text().substring(0,1).equals("0")) {
            return ResultUtil.error(UserEum.user_10012.getCode(), UserEum.user_10012.getDesc());
        }
        return ResultUtil.success();
    }
}
