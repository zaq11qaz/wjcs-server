package com.huihe.eg.message.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.message.model.SystemVersionEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SystemVersionService extends BaseFrameworkService<SystemVersionEntity, Long> {
    ResultParam versionIteration(SystemVersionEntity param, HttpServletRequest request, HttpServletResponse response);
}