package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.MessageContentEntity;
import com.huihe.eg.message.service.dao.MessageContentService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户聊天消息记录
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="用户聊天消息记录可用接口说明",description="用户聊天消息记录可用接口说明",tags = {"用户聊天消息记录"})
@RestController
@RequestMapping("messageContent")
public class MessageContentController extends BaseFrameworkController<MessageContentEntity, Integer> {

    @Resource
    private MessageContentService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}