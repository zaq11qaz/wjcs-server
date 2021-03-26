package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.UserInfoEntity;
import com.huihe.eg.mall.service.dao.UserInfoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户基本资料可用接口说明",description="用户基本资料可用接口说明",tags = {"用户基本资料"})
@RestController
@RequestMapping("userInfo")
public class UserInfoController extends BaseFrameworkController<UserInfoEntity, Long> {

    @Resource
    private UserInfoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}