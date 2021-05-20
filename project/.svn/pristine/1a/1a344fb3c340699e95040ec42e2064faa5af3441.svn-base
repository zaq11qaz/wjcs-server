package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserRecommenderActivityEntity;
import com.huihe.eg.user.mybatis.dao.UserRecommenderActivityMapper;
import com.huihe.eg.user.service.dao.UserRecommenderActivityService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserRecommenderActivityServiceImpl extends BaseFrameworkServiceImpl<UserRecommenderActivityEntity, Long> implements UserRecommenderActivityService {

    @Resource
    private UserRecommenderActivityMapper mapper;
    public void init() {
        setMapper(mapper);
    }
}