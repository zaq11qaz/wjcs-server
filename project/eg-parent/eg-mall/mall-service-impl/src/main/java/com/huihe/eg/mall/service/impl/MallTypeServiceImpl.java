package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.MallTypeEntity;
import com.huihe.eg.mall.mybatis.dao.MallTypeMapper;
import com.huihe.eg.mall.service.dao.MallTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MallTypeServiceImpl extends BaseFrameworkServiceImpl<MallTypeEntity, Long> implements MallTypeService {

    @Resource
    private MallTypeMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}