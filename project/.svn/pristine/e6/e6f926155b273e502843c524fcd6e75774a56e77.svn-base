package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.MessageBlacklistEntity;
import com.huihe.eg.message.service.dao.MessageBlacklistService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 屏蔽、举报记录
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="屏蔽、举报记录可用接口说明",description="屏蔽、举报记录可用接口说明",tags = {"屏蔽、举报记录"})
@RestController
@RequestMapping("messageBlacklist")
public class MessageBlacklistController extends BaseFrameworkController<MessageBlacklistEntity, Long> {

    @Resource
    private MessageBlacklistService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}