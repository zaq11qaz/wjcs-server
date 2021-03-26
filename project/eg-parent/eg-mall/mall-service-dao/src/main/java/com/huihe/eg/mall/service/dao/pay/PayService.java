package com.huihe.eg.mall.service.dao.pay;

import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.model.pay.WxH5PayParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    Map<String, String> wxPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    /**
     * 微信回调+
     * @param request
     * @param response
     * @return
     */
    String wxPayNotify(HttpServletRequest request, HttpServletResponse response);

    WxH5PayParam wxH5Pay(RechargeRecordEntity payOrderEntity, HttpServletRequest request, HttpServletResponse response);

    //退款
    String appRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

}
