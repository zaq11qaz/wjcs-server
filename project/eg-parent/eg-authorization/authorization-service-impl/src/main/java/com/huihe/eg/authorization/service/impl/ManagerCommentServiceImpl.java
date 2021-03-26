package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.authorization.model.ManagerCommentEntity;
import com.huihe.eg.authorization.mybatis.dao.ManagerCommentMapper;
import com.huihe.eg.authorization.service.dao.ManagerCommentService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ManagerCommentServiceImpl extends BaseFrameworkServiceImpl<ManagerCommentEntity, Long> implements ManagerCommentService {

    @Resource
    private ManagerCommentMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}