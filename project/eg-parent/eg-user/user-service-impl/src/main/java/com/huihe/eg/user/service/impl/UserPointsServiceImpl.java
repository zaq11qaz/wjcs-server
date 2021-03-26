package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserPointsEntity;
import com.huihe.eg.user.mybatis.dao.UserPointsMapper;
import com.huihe.eg.user.service.dao.UserPointsService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserPointsServiceImpl extends BaseFrameworkServiceImpl<UserPointsEntity, Integer> implements UserPointsService {

    @Resource
    private UserPointsMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}