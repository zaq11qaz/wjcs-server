package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserMemberCardEntity;
import com.huihe.eg.user.mybatis.dao.UserMemberCardMapper;
import com.huihe.eg.user.service.dao.UserMemberCardService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class UserMemberCardServiceImpl extends BaseFrameworkServiceImpl<UserMemberCardEntity, Long> implements UserMemberCardService {

    @Resource
    private UserMemberCardMapper mapper;

    @Override
    public void init() {
        setMapper(mapper);
    }
}