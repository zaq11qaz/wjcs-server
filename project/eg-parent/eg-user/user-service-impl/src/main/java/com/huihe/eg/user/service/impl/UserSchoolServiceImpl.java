package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserSchoolEntity;
import com.huihe.eg.user.mybatis.dao.UserSchoolMapper;
import com.huihe.eg.user.service.dao.UserSchoolService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserSchoolServiceImpl extends BaseFrameworkServiceImpl<UserSchoolEntity, Long> implements UserSchoolService {

    @Resource
    private UserSchoolMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}