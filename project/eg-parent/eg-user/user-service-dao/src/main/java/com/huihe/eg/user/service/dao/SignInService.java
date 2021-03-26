package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.SignInEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface SignInService extends BaseFrameworkService<SignInEntity, Long> {
    public SignInEntity querySignDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertLogin(SignInEntity entity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryTeachPaypalDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response);
}