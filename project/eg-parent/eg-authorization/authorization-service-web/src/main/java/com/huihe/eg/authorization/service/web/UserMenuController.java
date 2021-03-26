package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.UserMenuEntity;
import com.huihe.eg.authorization.service.dao.UserMenuService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户的菜单可用接口说明",description="用户的菜单可用接口说明",tags = {"用户的菜单"})
@RestController
@RequestMapping("userMenu")
public class UserMenuController extends BaseFrameworkController<UserMenuEntity, Long> {

    @Resource
    private UserMenuService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}