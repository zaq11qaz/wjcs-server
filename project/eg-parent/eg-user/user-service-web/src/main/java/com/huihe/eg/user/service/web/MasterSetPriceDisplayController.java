package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterSetPriceDisplayEntity;
import com.huihe.eg.user.service.dao.MasterSetPriceDisplayService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="可用接口说明",description="可用接口说明",tags = {""})
@RestController
@RequestMapping("masterSetPriceDisplay")
public class MasterSetPriceDisplayController extends BaseFrameworkController<MasterSetPriceDisplayEntity, Long> {

    @Resource
    private MasterSetPriceDisplayService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}