package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.SceneTypeEntity;
import com.huihe.eg.user.mybatis.dao.SceneTypeMapper;
import com.huihe.eg.user.service.dao.SceneTypeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SceneTypeServiceImpl extends BaseFrameworkServiceImpl<SceneTypeEntity, Long> implements SceneTypeService {

    @Resource
    private SceneTypeMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}