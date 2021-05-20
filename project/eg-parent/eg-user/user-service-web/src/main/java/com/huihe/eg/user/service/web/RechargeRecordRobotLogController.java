package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.RechargeRecordRobotLogEntity;
import com.huihe.eg.user.service.dao.RechargeRecordRobotLogService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="假数据添加记录可用接口说明",description="假数据添加记录可用接口说明",tags = {"假数据添加记录"})
@RestController
@RequestMapping("rechargerecordrobotLog")
public class RechargeRecordRobotLogController extends BaseFrameworkController<RechargeRecordRobotLogEntity, Integer> {

    @Resource
    private RechargeRecordRobotLogService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}