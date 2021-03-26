package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.DeliveryAddressEntity;
import com.huihe.eg.mall.service.dao.DeliveryAddressService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户收货地址可用接口说明",description="用户收货地址可用接口说明",tags = {"用户收货地址"})
@RestController
@RequestMapping("deliveryAddress")
public class DeliveryAddressController extends BaseFrameworkController<DeliveryAddressEntity, Long> {

    @Resource
    private DeliveryAddressService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}