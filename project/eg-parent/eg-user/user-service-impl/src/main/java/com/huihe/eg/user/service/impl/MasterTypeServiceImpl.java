package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MasterTypeEntity;
import com.huihe.eg.user.mybatis.dao.MasterTypeMapper;
import com.huihe.eg.user.service.dao.MasterTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MasterTypeServiceImpl extends BaseFrameworkServiceImpl<MasterTypeEntity, Long> implements MasterTypeService {

    @Resource
    private MasterTypeMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}