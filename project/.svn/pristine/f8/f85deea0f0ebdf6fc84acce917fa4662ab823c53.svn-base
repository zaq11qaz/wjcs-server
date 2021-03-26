package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.CouponListEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface CouponListService extends BaseFrameworkService<CouponListEntity, Long> {
//    ResultParam useDiscountVolume(CouponListEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam useVoucher(CouponListEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateStatus(CouponListEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(CouponListEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCoupListCount(CouponListEntity param, HttpServletRequest request, HttpServletResponse response);
}