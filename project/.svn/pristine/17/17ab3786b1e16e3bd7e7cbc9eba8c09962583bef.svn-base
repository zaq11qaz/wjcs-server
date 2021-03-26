package com.huihe.eg.user.service.dao.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 14:42
 */
public interface AliPayService {

    void init();

    AlipayClient getAlipayClient();

    String getAlipayAuthUrl();

    String getAlipayAuthUrl(String var0 , String var1, String var2, String var3, String var4);

    String app(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String wap(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String pc(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String appExchange(String order_code, BigDecimal bigDecimal,String account) throws AlipayApiException ;

    String appRefund(RechargeRecordEntity rechargeRecordEntity) throws AlipayApiException;

    public String aliTransfer(String order_code, String title,String account,String name , String type , BigDecimal amount,Long user_id)throws AlipayApiException;

    String getAccess_token(RechargeRecordEntity rechargeRecordEntity);

    String insertAccessToken(Map<String, String> params);

    String aliUserAuth(RechargeRecordEntity rechargeInfoEntity) throws AlipayApiException;

    String getAliUserAuthUrl() throws AlipayApiException;

    String getAliUserAuthUrl(String api_name, String method, String app_id, String app_name, String biz_type, String pid, String product_id, String scope, String toString, String auth_type, String sign_type) throws AlipayApiException;

    ResultParam aliPrepayment(RechargeRecordEntity entity, String payee_logon_id) throws AlipayApiException;

    ResultParam aliPrepayment2Pay(RechargeRecordEntity entity, String payer_user_id) throws AlipayApiException;

    void aliPrepayment2Cancel(RechargeRecordEntity recordEntity) throws AlipayApiException;

    ResultParam aliPayTradeRefund(RechargeRecordEntity entity);
}
