package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.ActivityInfoEntity;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public interface BusinessActivityTypeService extends BaseFrameworkService<BusinessActivityTypeEntity, Integer> {
    List<BusinessActivityTypeEntity> queryNoRepeat(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response);

    List<BusinessActivityTypeEntity> queryByMessage(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response);

    BigDecimal queryNeedPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ActivityInfoEntity queryActivityInfo(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);
}