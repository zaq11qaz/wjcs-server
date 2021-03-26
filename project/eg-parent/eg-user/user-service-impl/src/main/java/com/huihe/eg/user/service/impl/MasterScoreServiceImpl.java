package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MasterScoreEntity;
import com.huihe.eg.user.mybatis.dao.MasterScoreMapper;
import com.huihe.eg.user.service.dao.MasterInfoService;
import com.huihe.eg.user.service.dao.MasterScoreService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MasterScoreServiceImpl extends BaseFrameworkServiceImpl<MasterScoreEntity, Long> implements MasterScoreService {

    @Resource
    private MasterScoreMapper mapper;
    @Resource
    private MasterInfoService masterInfoService;
    @Override
    public void init() {
        setMapper(mapper);
    }

}