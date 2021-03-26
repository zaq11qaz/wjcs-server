package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserPriceEntity;
import com.huihe.eg.user.mybatis.dao.UserPriceMapper;
import com.huihe.eg.user.service.dao.UserPriceService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserPriceServiceImpl extends BaseFrameworkServiceImpl<UserPriceEntity, Long> implements UserPriceService {

    @Resource
    private UserPriceMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}