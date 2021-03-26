package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.authorization.model.UserMenuEntity;
import com.huihe.eg.authorization.mybatis.dao.UserMenuMapper;
import com.huihe.eg.authorization.service.dao.UserMenuService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserMenuServiceImpl extends BaseFrameworkServiceImpl<UserMenuEntity, Long> implements UserMenuService {

    @Resource
    private UserMenuMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}