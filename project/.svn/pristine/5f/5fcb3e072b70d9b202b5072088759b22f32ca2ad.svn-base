package com.huihe.eg.grab.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.grab.model.GrabFilterRuleJoinEntity;
import com.huihe.eg.grab.mybatis.dao.GrabFilterRuleJoinMapper;
import com.huihe.eg.grab.service.dao.GrabFilterRuleJoinService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GrabFilterRuleJoinServiceImpl extends BaseFrameworkServiceImpl<GrabFilterRuleJoinEntity, Long> implements GrabFilterRuleJoinService {

    @Resource
    private GrabFilterRuleJoinMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}