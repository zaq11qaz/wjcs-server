package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.BusinessActivitySponsorsEntity;
import com.huihe.eg.user.mybatis.dao.BusinessActivitySponsorsMapper;
import com.huihe.eg.user.service.dao.BusinessActivitySponsorsService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BusinessActivitySponsorsServiceImpl extends BaseFrameworkServiceImpl<BusinessActivitySponsorsEntity, Long> implements BusinessActivitySponsorsService {

    @Resource
    private BusinessActivitySponsorsMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}