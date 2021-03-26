package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.CommodityCouponTypeEntity;
import com.huihe.eg.user.service.dao.CommodityCouponTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：券类型
 * @ since: JDk1.8
 */
@Api(value="券类型可用接口说明",description="券类型可用接口说明",tags = {"券类型"})
@RestController
@RequestMapping("commoditycouponType")
public class CommodityCouponTypeController extends BaseFrameworkController<CommodityCouponTypeEntity, Integer> {

    @Resource
    private CommodityCouponTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}