package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.news.model.OrderEntity;
import com.huihe.eg.news.service.dao.OrderService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="订单可用接口说明",description="订单可用接口说明",tags = {"订单"})
@RestController
@RequestMapping("order")
public class OrderController extends BaseFrameworkController<OrderEntity, Long> {

    @Resource
    private OrderService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}