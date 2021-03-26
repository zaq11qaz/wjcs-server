package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.PriceEntity;
import com.huihe.eg.user.service.dao.PriceService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:30
 * @ Description：价格
 * @ since: JDk1.8
 */
@Api(value="价格可用接口说明",description="价格可用接口说明",tags = {"价格"})
@RestController
@RequestMapping("price")
public class PriceController extends BaseFrameworkController<PriceEntity, Long> {

    @Resource
    private PriceService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}