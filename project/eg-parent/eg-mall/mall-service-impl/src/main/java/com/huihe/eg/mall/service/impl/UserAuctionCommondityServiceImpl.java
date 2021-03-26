package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.UserAuctionCommondityEntity;
import com.huihe.eg.mall.mybatis.dao.UserAuctionCommondityMapper;
import com.huihe.eg.mall.service.dao.UserAuctionCommondityService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAuctionCommondityServiceImpl extends BaseFrameworkServiceImpl<UserAuctionCommondityEntity, Long> implements UserAuctionCommondityService {

    @Resource
    private UserAuctionCommondityMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}