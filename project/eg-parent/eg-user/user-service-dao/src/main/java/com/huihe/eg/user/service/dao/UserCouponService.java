package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserCouponEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserCouponService extends BaseFrameworkService<UserCouponEntity, Long> {

    ResultParam useCoupon(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response);

    List<UserCouponEntity> queryMyCoupon(UserCouponEntity userCouponEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCoupListCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserCollection(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryUserCoupCount(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateCoupStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserCoupon198(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserCoupon177(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserCoupon77(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertStudyCardByCoup(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserCoupon99(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertUserStudyCardByCoup(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryDetail(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateBind(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateCheckIn(UserCouponEntity param, HttpServletRequest request, HttpServletResponse response);
}