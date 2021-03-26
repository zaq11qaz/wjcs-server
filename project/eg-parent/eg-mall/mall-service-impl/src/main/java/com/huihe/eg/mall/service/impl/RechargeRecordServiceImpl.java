package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.mybatis.dao.RechargeRecordMapper;
import com.huihe.eg.mall.service.dao.RechargeRecordService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RechargeRecordServiceImpl extends BaseFrameworkServiceImpl<RechargeRecordEntity, Long> implements RechargeRecordService {

    @Resource
    private RechargeRecordMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}