package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.CommodityInfoEntity;
import com.huihe.eg.mall.service.dao.CommodityInfoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="商品信息可用接口说明",description="商品信息可用接口说明",tags = {"商品信息"})
@RestController
@RequestMapping("commodityInfo")
public class CommodityInfoController extends BaseFrameworkController<CommodityInfoEntity, Long> {

    @Resource
    private CommodityInfoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}