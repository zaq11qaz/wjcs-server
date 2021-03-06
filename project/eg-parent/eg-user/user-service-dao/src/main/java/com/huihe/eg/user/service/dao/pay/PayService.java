package com.huihe.eg.user.service.dao.pay;

import com.alipay.api.AlipayApiException;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.pay.WxH5PayParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 15:43
 */
public interface PayService {
    /**
     * 支付宝支付
     * @param entity
     * @param request
     * @param response
     * @return
     */
    String aliPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    /**
     * 支付宝回调
     * @param request
     * @param response
     * @return
     */
    String aliPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 微信支付
     * @param entity
     * @param request
     * @param response
     * @return
     */
    ResultParam wxPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    void updatePayNum(RechargeRecordEntity rechargeRecordEntity);

    /**
     * 微信回调+
     * @param request
     * @param response
     * @return
     */
    Long wxPayNotify(HttpServletRequest request, HttpServletResponse response);

    WxH5PayParam wxH5Pay(RechargeRecordEntity payOrderEntity, HttpServletRequest request, HttpServletResponse response);

    //退款
    String appRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam aliTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    String H5aliAuth(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    String H5aliGetToken(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    String H5aliPayAuthNotify(HttpServletRequest request, HttpServletResponse response);

    ResultParam aliUserAuth(RechargeRecordEntity rechargeInfoEntity , HttpServletRequest request, HttpServletResponse response);

    ResultParam aliUserAuthUrl(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam wxTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam aliPrepayment(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) throws AlipayApiException;

    ResultParam aliPrepayment2Pay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    void aliPrepayment2Cancel(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam aliPrepaymentNotify(HttpServletRequest request, HttpServletResponse response);

    ResultParam payPre(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam fundAuthOrderUnFreeze(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) throws AlipayApiException;

    ResultParam aliPayTradeRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam wxPayRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

}
