package com.huihe.eg.mall.service.dao.pay;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.util.result.ResultParam;
import com.github.wxpay.sdk.WXPayConfig;

import java.util.Map;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:43
 */
public interface WxPayService {
    Map<String, Object> getWxData();

    JSONObject getSessionKeyOropenid(String var1);

    JSONObject getUserInfo(String var1, String var2, String var3);

    String returnXml(String var1, String var2);

    WXPayConfig getWxPayConfig(Integer var1);

    WXPayConfig getWxPayConfig(Integer var1, String var2, String var3);

    ResultParam getWxShareSign(String url);

}
