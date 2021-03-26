package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.authorization.model.UserButtonEntity;
import com.huihe.eg.authorization.mybatis.dao.UserButtonMapper;
import com.huihe.eg.authorization.service.dao.UserButtonService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserButtonServiceImpl extends BaseFrameworkServiceImpl<UserButtonEntity, Long> implements UserButtonService {

    @Resource
    private UserButtonMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}