package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.mall.model.UserLoginEntity;
import com.huihe.eg.mall.service.dao.UserLoginService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户登录注册信息可用接口说明",description="用户登录注册信息可用接口说明",tags = {"用户登录注册信息"})
@RestController
@RequestMapping("userLogin")
public class UserLoginController extends BaseFrameworkController<UserLoginEntity, Long> {

    @Resource
    private UserLoginService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    @ApiOperation(
            value = "注册",
            httpMethod = "POST",
            notes = "注册"
    )
    @PostMapping({"register"})
    public ResultParam register(@RequestBody UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        return ResultUtil.success(service.register(entity, request, response));
    }
    @ApiOperation(
            value = "登录",
            httpMethod = "POST",
            notes = "登录"
    )
    @PostMapping({"login"})
    public ResultParam login(@RequestBody UserLoginEntity entity, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        ResultParam resultParam=new ResultParam();
        try {
            resultParam=service.login(entity, request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultParam;
    }
}