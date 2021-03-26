package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.MessageUserEntity;
import com.huihe.eg.message.mybatis.dao.MessageUserMapper;
import com.huihe.eg.message.service.dao.MessageUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class MessageUserServiceImpl extends BaseFrameworkServiceImpl<MessageUserEntity, Long> implements MessageUserService {

    @Resource()
    private MessageUserMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}