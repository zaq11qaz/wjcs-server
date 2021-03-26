package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterSetPricePriceEntity;
import com.huihe.eg.user.service.dao.MasterSetPricePriceService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:28
 * @ Description：商品价格区间
 * @ since: JDk1.8
 */
@Api(value="商品价格区间可用接口说明",description="商品价格区间可用接口说明",tags = {"商品价格区间"})
@RestController
@RequestMapping("masterSetPricePrice")
public class MasterSetPricePriceController extends BaseFrameworkController<MasterSetPricePriceEntity, Long> {

    @Resource
    private MasterSetPricePriceService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}