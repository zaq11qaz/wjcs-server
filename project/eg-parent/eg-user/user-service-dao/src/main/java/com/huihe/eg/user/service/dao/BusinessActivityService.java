package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.BusinessActivityEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BusinessActivityService extends BaseFrameworkService<BusinessActivityEntity, Long> {
    ResultParam insertActivity(BusinessActivityEntity param, HttpServletRequest request, HttpServletResponse response);
}