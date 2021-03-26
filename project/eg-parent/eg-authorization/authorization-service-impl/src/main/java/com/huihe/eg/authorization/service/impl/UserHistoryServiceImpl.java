package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.authorization.model.UserHistoryEntity;
import com.huihe.eg.authorization.mybatis.dao.UserHistoryMapper;
import com.huihe.eg.authorization.service.dao.UserHistoryService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryServiceImpl extends BaseFrameworkServiceImpl<UserHistoryEntity, Long> implements UserHistoryService {

    @Resource
    private UserHistoryMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}