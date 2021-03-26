package com.huihe.eg.mall.service.dao.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import java.math.BigDecimal;

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

    String getAlipayAuthUrl(String var1, String var2, String var3, String var4);

    String app(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String wap(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String pc(String var1, String var2, String var3, BigDecimal var4) throws AlipayApiException;

    String appExchange(String order_code, BigDecimal bigDecimal, String account) throws AlipayApiException ;

    String appRefund(RechargeRecordEntity rechargeRecordEntity) throws AlipayApiException;
}
