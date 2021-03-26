package com.huihe.eg.mall.service.impl.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.StringUtil;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.model.pay.AliPayParam;
import com.huihe.eg.mall.service.dao.pay.AliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:43
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private AliPayParam aliPayParam;
    private AlipayClient alipayClient;

    public AliPayServiceImpl() {
    }

    public void init() {
        if (this.alipayClient == null) {
            this.alipayClient = new DefaultAlipayClient(this.aliPayParam.getUrl(), this.aliPayParam.getApp_id(), this.aliPayParam.getApp_private_key(), this.aliPayParam.getFormat(), this.aliPayParam.getCharset(), this.aliPayParam.getAlipay_public_key(), this.aliPayParam.getSign_type());
        }
    }

    public AlipayClient getAlipayClient() {
        this.init();
        return this.alipayClient;
    }


    public String getAlipayAuthUrl(String app_id, String scope, String redirect_uri, String state) {
        this.init();
        StringBuilder stringBuilder = new StringBuilder(this.aliPayParam.getAuth_url());
        stringBuilder.append("?app_id=").append(app_id).append("&scope=").append(scope).append("&redirect_uri=").append(URLEncoder.encode(redirect_uri)).append("&state=").append(state);
        this.logger.warn("url=" + stringBuilder.toString());

        return stringBuilder.toString();
    }

    public String getAlipayAuthUrl() {
        return this.getAlipayAuthUrl(this.aliPayParam.getApp_id(), this.aliPayParam.getScope(), this.aliPayParam.getRedirect_url(), UUID.randomUUID().toString());
    }

    public String app(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }
        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayTradeAppPayRequest.setBizModel(model);
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            //this.alipayClient.execute(alipayTradeAppPayRequest).get();
            return this.alipayClient.sdkExecute(alipayTradeAppPayRequest).getBody();
        }
    }

    public String wap(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }

        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getReturn_url())) {
            throw new RuntimeException("返回的url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("QUICK_WAP_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(this.aliPayParam.getNotify_url());
            request.setReturnUrl(this.aliPayParam.getReturn_url());
            return this.alipayClient.pageExecute(request).getBody();
        }
    }

    public String pc(String order_code, String title, String passbackParams, BigDecimal bigDecimal) throws AlipayApiException {
        this.init();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        if (StringUtil.isNotEmpty(passbackParams)) {
            model.setPassbackParams(passbackParams);
        }

        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getReturn_url())) {
            throw new RuntimeException("返回的url不能为空");
        } else {
            model.setSubject(StringUtil.isEmpty(title) ? "订单-" + CommonUtils.generateOrderCode() : title);
            model.setOutTradeNo(order_code);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(bigDecimal.setScale(2, 4).toString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(this.aliPayParam.getNotify_url());
            request.setReturnUrl(this.aliPayParam.getReturn_url());
            return this.alipayClient.pageExecute(request).getBody();
        }
    }

    /**
     * 提现,兑换
     *
     * @return
     * @throws AlipayApiException
     */
    public String appExchange(String order_code, BigDecimal bigDecimal, String account) throws AlipayApiException {
        this.init();
        Map<String, Object> map = new HashMap<>();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        if (StringUtil.isEmpty(order_code)) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            map.put("out_biz_no", order_code);
            map.put("payee_type", "ALIPAY_LOGONID");
            map.put("payee_account", account);
            map.put("amount", bigDecimal);
            alipayTradeAppPayRequest.setBizContent(map.toString());
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            return this.alipayClient.execute(alipayTradeAppPayRequest).getBody();
        }
    }

    /**
     * 退款
     *
     * @return
     * @throws AlipayApiException
     */
    public String appRefund(RechargeRecordEntity rechargeRecordEntity) throws AlipayApiException {
        this.init();
        Map<String, Object> map = new HashMap<>();
        AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
        if (StringUtil.isEmpty(rechargeRecordEntity.getFlowing_no())) {
            throw new RuntimeException("订单号不能为空");
        } else if (StringUtil.isEmpty(this.aliPayParam.getNotify_url())) {
            throw new RuntimeException("异步通知url不能为空");
        } else {
            map.put("out_trade_no", rechargeRecordEntity.getFlowing_no());
            map.put("refund_amount", rechargeRecordEntity.getAmount());
            map.put("refund_currency", "USD");
            alipayTradeAppPayRequest.setBizContent(map.toString());
            alipayTradeAppPayRequest.setNotifyUrl(this.aliPayParam.getNotify_url());
            return this.alipayClient.execute(alipayTradeAppPayRequest).getBody();
        }
    }
}
