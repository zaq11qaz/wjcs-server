package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserCommodityEntity;
import com.huihe.eg.user.mybatis.dao.UserCommodityMapper;
import com.huihe.eg.user.service.dao.UserCommodityService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserCommodityServiceImpl extends BaseFrameworkServiceImpl<UserCommodityEntity, Long> implements UserCommodityService {

    @Resource
    private UserCommodityMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}