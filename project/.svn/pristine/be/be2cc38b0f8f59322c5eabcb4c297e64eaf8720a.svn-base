package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.service.dao.BusinessActivityTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="活动类型可用接口说明",description="活动类型可用接口说明",tags = {"活动类型"})
@RestController
@RequestMapping("businessActivityType")
public class BusinessActivityTypeController extends BaseFrameworkController<BusinessActivityTypeEntity, Integer> {

    @Resource
    private BusinessActivityTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}