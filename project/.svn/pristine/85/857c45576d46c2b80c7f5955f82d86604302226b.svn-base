package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.GroupVideoEntity;
import com.huihe.eg.message.mybatis.dao.GroupVideoMapper;
import com.huihe.eg.message.service.dao.GroupVideoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GroupVideoServiceImpl extends BaseFrameworkServiceImpl<GroupVideoEntity, Long> implements GroupVideoService {

    @Resource
    private GroupVideoMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}