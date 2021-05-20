package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MechanismExamItemsEntity;
import com.huihe.eg.user.mybatis.dao.MechanismExamItemsMapper;
import com.huihe.eg.user.service.dao.MechanismExamItemsService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MechanismExamItemsServiceImpl extends BaseFrameworkServiceImpl<MechanismExamItemsEntity, Long> implements MechanismExamItemsService {

    @Resource
    private MechanismExamItemsMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}