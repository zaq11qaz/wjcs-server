package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserEarnRoleEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserEarnRoleService extends BaseFrameworkService<UserEarnRoleEntity, Long> {
    ResultParam updateEffective(UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateUserEffective(UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response);
}