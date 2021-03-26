package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.message.model.MessageContentEntity;
import com.huihe.eg.message.mybatis.dao.MessageContentMapper;
import com.huihe.eg.message.service.dao.MessageContentService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MessageContentServiceImpl extends BaseFrameworkServiceImpl<MessageContentEntity, Integer> implements MessageContentService {

    @Resource
    private MessageContentMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}