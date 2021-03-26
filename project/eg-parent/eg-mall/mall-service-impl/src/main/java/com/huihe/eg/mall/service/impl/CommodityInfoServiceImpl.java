package com.huihe.eg.mall.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.mall.model.CommodityInfoEntity;
import com.huihe.eg.mall.mybatis.dao.CommodityInfoMapper;
import com.huihe.eg.mall.service.dao.CommodityInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommodityInfoServiceImpl extends BaseFrameworkServiceImpl<CommodityInfoEntity, Long> implements CommodityInfoService {

    @Resource
    private CommodityInfoMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}