package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.UserButtonEntity;
import com.huihe.eg.authorization.service.dao.UserButtonService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户所拥有的按钮可用接口说明",description="用户所拥有的按钮可用接口说明",tags = {"用户所拥有的按钮"})
@RestController
@RequestMapping("userButton")
public class UserButtonController extends BaseFrameworkController<UserButtonEntity, Long> {

    @Resource
    private UserButtonService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}