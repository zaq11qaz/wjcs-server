package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.CommodityCouponEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface CommodityCouponService extends BaseFrameworkService<CommodityCouponEntity, Long> {
    ResultParam ReceiveCoupon(CommodityCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertCouponList(CommodityCouponEntity map, HttpServletRequest request, HttpServletResponse response);

    BigDecimal getFinalAmount(RechargeRecordEntity entity);
}