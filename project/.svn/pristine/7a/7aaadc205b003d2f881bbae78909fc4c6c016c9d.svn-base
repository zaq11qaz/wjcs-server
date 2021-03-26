package com.huihe.eg.news.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.news.model.OrderEntity;
import com.huihe.eg.news.mybatis.dao.OrderMapper;
import com.huihe.eg.news.service.dao.OrderService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseFrameworkServiceImpl<OrderEntity, Long> implements OrderService {

    @Resource
    private OrderMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }


}