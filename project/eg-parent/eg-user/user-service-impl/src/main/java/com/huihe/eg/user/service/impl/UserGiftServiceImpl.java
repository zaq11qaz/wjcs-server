package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserGiftEntity;
import com.huihe.eg.user.mybatis.dao.UserGiftMapper;
import com.huihe.eg.user.service.dao.UserGiftService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserGiftServiceImpl extends BaseFrameworkServiceImpl<UserGiftEntity, Long> implements UserGiftService {

    @Resource
    private UserGiftMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}