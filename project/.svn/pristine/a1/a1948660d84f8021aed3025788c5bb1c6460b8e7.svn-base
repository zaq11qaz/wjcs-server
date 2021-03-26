package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.mybatis.dao.BusinessActivityTypeMapper;
import com.huihe.eg.user.service.dao.BusinessActivityTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BusinessActivityTypeServiceImpl extends BaseFrameworkServiceImpl<BusinessActivityTypeEntity, Integer> implements BusinessActivityTypeService {

    @Resource
    private BusinessActivityTypeMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}