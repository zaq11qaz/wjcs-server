package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.service.dao.RechargeRecordService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="充值订单可用接口说明",description="充值订单可用接口说明",tags = {"充值订单"})
@RestController
@RequestMapping("rechargerecord")
public class RechargeRecordController extends BaseFrameworkController<RechargeRecordEntity, Long> {

    @Resource
    private RechargeRecordService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}