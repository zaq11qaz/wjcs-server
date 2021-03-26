package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.MessageBlacklistEntity;
import com.huihe.eg.message.mybatis.dao.MessageBlacklistMapper;
import com.huihe.eg.message.service.dao.MessageBlacklistService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MessageBlacklistServiceImpl extends BaseFrameworkServiceImpl<MessageBlacklistEntity, Long> implements MessageBlacklistService {

    @Resource
    private MessageBlacklistMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}