package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.RechargeRecordRobotLogEntity;
import com.huihe.eg.user.mybatis.dao.RechargeRecordRobotLogMapper;
import com.huihe.eg.user.service.dao.RechargeRecordRobotLogService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RechargeRecordRobotLogServiceImpl extends BaseFrameworkServiceImpl<RechargeRecordRobotLogEntity, Integer> implements RechargeRecordRobotLogService {

    @Resource
    private RechargeRecordRobotLogMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}