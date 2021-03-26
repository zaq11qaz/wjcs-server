package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.OverseasIdentityEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface OverseasIdentityService extends BaseFrameworkService<OverseasIdentityEntity, Long> {
    Map<String, Object> queryOverseasAuthListPage(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response);
    ResultParam overseasAudit(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryOverseasAuditCount(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryByMessage(OverseasIdentityEntity param, HttpServletRequest request, HttpServletResponse response);
}