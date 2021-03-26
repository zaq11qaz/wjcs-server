package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.DeliveryAddressEntity;
import com.huihe.eg.mall.mybatis.dao.DeliveryAddressMapper;
import com.huihe.eg.mall.service.dao.DeliveryAddressService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressServiceImpl extends BaseFrameworkServiceImpl<DeliveryAddressEntity, Long> implements DeliveryAddressService {

    @Resource
    private DeliveryAddressMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}