package com.huihe.eg.authorization.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.LogEntity;

public interface LogService extends BaseFrameworkService<LogEntity, Integer> {
    ResultParam insertLog(String requestMethod, String requestURI, String body, String userID);

}