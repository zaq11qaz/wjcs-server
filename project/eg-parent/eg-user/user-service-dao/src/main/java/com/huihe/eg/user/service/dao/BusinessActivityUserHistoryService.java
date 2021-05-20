package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.BaseMap;
import com.huihe.eg.user.model.BusinessActivityUserHistoryEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface BusinessActivityUserHistoryService extends BaseFrameworkService<BusinessActivityUserHistoryEntity, Long> {
    BaseMap queryUserList(BusinessActivityUserHistoryEntity param, HttpServletRequest request, HttpServletResponse response);
}