package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserGroupingEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserGroupingService extends BaseFrameworkService<UserGroupingEntity, Long> {
    ResultParam insertUserGrouping(UserGroupingEntity userGroupingEntity, HttpServletRequest request, HttpServletResponse response);


    ResultParam updateSettlementCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateOnPaymentSettlementCash(UserGroupingEntity userGroupingEntity);
}