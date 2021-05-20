package com.huihe.eg.authorization.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.AuthorizationScreenWhiteEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorizationScreenWhiteService extends BaseFrameworkService<AuthorizationScreenWhiteEntity, Long> {
    ResultParam loginScreen(AuthorizationScreenWhiteEntity entity, HttpServletRequest request, HttpServletResponse response);
}