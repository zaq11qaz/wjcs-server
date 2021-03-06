package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterSetPriceDisplayEntity;
import com.huihe.eg.user.service.dao.MasterSetPriceDisplayService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="机构商品直播列表",description="机构商品直播列表",tags = {"机构商品直播列表"})
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