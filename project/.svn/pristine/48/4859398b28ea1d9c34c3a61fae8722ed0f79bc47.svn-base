package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserChattingEntity;
import com.huihe.eg.user.service.dao.UserChattingService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 畅聊记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="畅聊记录可用接口说明",description="畅聊记录可用接口说明",tags = {"畅聊记录"})
@RestController
@RequestMapping("userChatting")
public class UserChattingController extends BaseFrameworkController<UserChattingEntity, Long> {

    @Resource
    private UserChattingService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}