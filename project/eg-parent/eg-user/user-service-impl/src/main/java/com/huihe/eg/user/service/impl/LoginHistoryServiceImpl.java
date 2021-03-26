package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.LoginHistoryEntity;
import com.huihe.eg.user.mybatis.dao.LoginHistoryMapper;
import com.huihe.eg.user.service.dao.LoginHistoryService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryServiceImpl extends BaseFrameworkServiceImpl<LoginHistoryEntity, Long> implements LoginHistoryService {

    @Resource
    private LoginHistoryMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}