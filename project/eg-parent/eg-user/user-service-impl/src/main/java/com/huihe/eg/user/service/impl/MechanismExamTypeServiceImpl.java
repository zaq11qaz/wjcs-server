package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MechanismExamTypeEntity;
import com.huihe.eg.user.mybatis.dao.MechanismExamTypeMapper;
import com.huihe.eg.user.service.dao.MechanismExamTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MechanismExamTypeServiceImpl extends BaseFrameworkServiceImpl<MechanismExamTypeEntity, Long> implements MechanismExamTypeService {

    @Resource
    private MechanismExamTypeMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}