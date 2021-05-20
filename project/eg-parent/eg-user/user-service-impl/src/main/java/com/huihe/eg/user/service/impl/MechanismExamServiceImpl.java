package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MechanismExamEntity;
import com.huihe.eg.user.mybatis.dao.MechanismExamMapper;
import com.huihe.eg.user.service.dao.MechanismExamService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MechanismExamServiceImpl extends BaseFrameworkServiceImpl<MechanismExamEntity, Long> implements MechanismExamService {

    @Resource
    private MechanismExamMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}