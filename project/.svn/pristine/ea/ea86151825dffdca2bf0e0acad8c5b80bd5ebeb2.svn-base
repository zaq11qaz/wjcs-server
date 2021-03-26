package com.huihe.eg.authorization.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.ManagerUserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ManagerUserService extends BaseFrameworkService<ManagerUserEntity, Long> {
    String queryManager(Long id);

    ResultParam login(ManagerUserEntity entity, HttpServletRequest request, HttpServletResponse response);

    void queryManagerInfoInsertRedis(ManagerUserEntity param,HttpServletRequest request, HttpServletResponse response);

    Object logOut(ManagerUserEntity param, HttpServletRequest request, HttpServletResponse response);

    Long queryManagerId(String type, HttpServletRequest request, HttpServletResponse response);
}