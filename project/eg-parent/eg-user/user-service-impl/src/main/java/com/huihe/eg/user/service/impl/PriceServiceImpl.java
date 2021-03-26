package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.PriceEntity;
import com.huihe.eg.user.mybatis.dao.PriceMapper;
import com.huihe.eg.user.service.dao.PriceService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends BaseFrameworkServiceImpl<PriceEntity, Long> implements PriceService {

    @Resource
    private PriceMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}