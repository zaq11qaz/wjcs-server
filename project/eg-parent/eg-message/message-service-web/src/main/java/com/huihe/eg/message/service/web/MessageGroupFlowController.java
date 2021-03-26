package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.MessageGroupFlowEntity;
import com.huihe.eg.message.service.dao.MessageGroupFlowService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * im实时流记录
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="im实时流记录可用接口说明",description="im实时流记录可用接口说明",tags = {"im实时流记录"})
@RestController
@RequestMapping("messageGroupFlow")
public class MessageGroupFlowController extends BaseFrameworkController<MessageGroupFlowEntity, Long> {

    @Resource
    private MessageGroupFlowService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}