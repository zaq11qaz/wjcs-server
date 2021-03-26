package com.huihe.eg.grab.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.huihe.eg.grab.model.GrabTaskEntity;

import java.util.Map;

public interface GrabTaskService extends BaseFrameworkService<GrabTaskEntity, Long> {
    Map<String, Object> execute(String taskId);
}