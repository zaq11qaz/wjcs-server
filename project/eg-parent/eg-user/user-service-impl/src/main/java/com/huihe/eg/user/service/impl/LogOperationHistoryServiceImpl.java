package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.LogOperationHistoryEntity;
import com.huihe.eg.user.mybatis.dao.LogOperationHistoryMapper;
import com.huihe.eg.user.service.dao.LogOperationHistoryService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LogOperationHistoryServiceImpl extends BaseFrameworkServiceImpl<LogOperationHistoryEntity, Integer> implements LogOperationHistoryService {

    @Resource
    private LogOperationHistoryMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}