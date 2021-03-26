package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.SettledAnchorEntity;
import com.huihe.eg.user.mybatis.dao.SettledAnchorMapper;
import com.huihe.eg.user.service.dao.SettledAnchorService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SettledAnchorServiceImpl extends BaseFrameworkServiceImpl<SettledAnchorEntity, Long> implements SettledAnchorService {

    @Resource
    private SettledAnchorMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}