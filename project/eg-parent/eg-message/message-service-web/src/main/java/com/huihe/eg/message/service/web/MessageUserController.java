package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.MessageUserEntity;
import com.huihe.eg.message.service.dao.MessageUserService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户消息列表
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="用户消息列表可用接口说明",description="用户消息列表可用接口说明",tags = {"用户消息列表"})
@RestController
@RequestMapping("messageUser")
public class MessageUserController extends BaseFrameworkController<MessageUserEntity, Long> {

    @Resource
    private MessageUserService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}