package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.UserHistoryEntity;
import com.huihe.eg.authorization.service.dao.UserHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户请求客服记录表可用接口说明",description="用户请求客服记录表可用接口说明",tags = {"用户请求客服记录表"})
@RestController
@RequestMapping("userHistory")
public class UserHistoryController extends BaseFrameworkController<UserHistoryEntity, Long> {

    @Resource
    private UserHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}