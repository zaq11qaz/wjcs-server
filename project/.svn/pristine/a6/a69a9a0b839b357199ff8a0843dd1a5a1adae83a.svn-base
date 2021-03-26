package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.MallTypeEntity;
import com.huihe.eg.mall.service.dao.MallTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="商品分类可用接口说明",description="商品分类可用接口说明",tags = {"商品分类"})
@RestController
@RequestMapping("mallType")
public class MallTypeController extends BaseFrameworkController<MallTypeEntity, Long> {

    @Resource
    private MallTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}