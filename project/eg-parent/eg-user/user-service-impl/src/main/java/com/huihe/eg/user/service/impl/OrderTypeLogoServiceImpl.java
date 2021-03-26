package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.OrderTypeLogoEntity;
import com.huihe.eg.user.mybatis.dao.OrderTypeLogoMapper;
import com.huihe.eg.user.service.dao.OrderTypeLogoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderTypeLogoServiceImpl extends BaseFrameworkServiceImpl<OrderTypeLogoEntity, Long> implements OrderTypeLogoService {

    @Resource
    private OrderTypeLogoMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}