package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserIdentityEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserIdentityService extends BaseFrameworkService<UserIdentityEntity, Long> {
    Map<String, Object> queryIdentityListPage(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response);
    ResultParam realNameAudit(UserIdentityEntity identityEntity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    Map<String,Object> queryRealNameAuditCount(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserIdentityEntity param, HttpServletRequest request, HttpServletResponse response);

}