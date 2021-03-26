package com.huihe.eg.grab.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.grab.model.GrabFilterRuleEntity;
import com.huihe.eg.grab.mybatis.dao.GrabFilterRuleMapper;
import com.huihe.eg.grab.service.dao.GrabFilterRuleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GrabFilterRuleServiceImpl extends BaseFrameworkServiceImpl<GrabFilterRuleEntity, Long> implements GrabFilterRuleService {

    @Resource
    private GrabFilterRuleMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}