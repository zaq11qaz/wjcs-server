package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.user.model.PushHistoryEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface PushHistoryService extends BaseFrameworkService<PushHistoryEntity, Long> {
    Map<String,Object> queryIsRead(PushHistoryEntity pushHistoryEntity, HttpServletRequest request, HttpServletResponse response);
}