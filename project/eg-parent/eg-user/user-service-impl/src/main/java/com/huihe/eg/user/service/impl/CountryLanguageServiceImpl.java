package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.CountryLanguageEntity;
import com.huihe.eg.user.mybatis.dao.CountryLanguageMapper;
import com.huihe.eg.user.service.dao.CountryLanguageService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CountryLanguageServiceImpl extends BaseFrameworkServiceImpl<CountryLanguageEntity, Long> implements CountryLanguageService {

    @Resource
    private CountryLanguageMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}