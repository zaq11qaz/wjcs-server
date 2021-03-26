package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserPointsEntity;
import com.huihe.eg.user.service.dao.UserPointsService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="积分记录可用接口说明",description="积分记录可用接口说明",tags = {"积分记录"})
@RestController
@RequestMapping("userPoints")
public class UserPointsController extends BaseFrameworkController<UserPointsEntity, Integer> {

    @Resource
    private UserPointsService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}