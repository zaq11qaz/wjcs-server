package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.StudyPriceEntity;
import com.huihe.eg.user.mybatis.dao.StudyPriceMapper;
import com.huihe.eg.user.service.dao.StudyPriceService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StudyPriceServiceImpl extends BaseFrameworkServiceImpl<StudyPriceEntity, Long> implements StudyPriceService {

    @Resource
    private StudyPriceMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}