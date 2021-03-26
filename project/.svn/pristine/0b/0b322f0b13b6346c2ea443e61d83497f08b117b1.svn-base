package com.huihe.eg.mall.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.mall.model.UserLoginEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserLoginService extends BaseFrameworkService<UserLoginEntity, Long> {

    ResultParam register(UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response);
    ResultParam login(UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response);
}