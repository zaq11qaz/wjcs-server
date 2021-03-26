package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.SystemFeedBackEntity;
import com.huihe.eg.message.mybatis.dao.SystemFeedBackMapper;
import com.huihe.eg.message.service.dao.SystemFeedBackService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SystemFeedBackServiceImpl extends BaseFrameworkServiceImpl<SystemFeedBackEntity, Long> implements SystemFeedBackService {

    @Resource
    private SystemFeedBackMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}