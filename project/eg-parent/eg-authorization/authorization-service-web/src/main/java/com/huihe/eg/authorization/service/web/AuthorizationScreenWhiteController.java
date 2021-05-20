package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.AuthorizationScreenWhiteEntity;
import com.huihe.eg.authorization.service.dao.AuthorizationScreenWhiteService;
import com.huihe.eg.comm.util.GetIp;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

@Api(value="大屏幕白名单可用接口说明",description="大屏幕白名单可用接口说明",tags = {"大屏幕白名单"})
@RestController
@RequestMapping("authorizationScreenWhite")
public class AuthorizationScreenWhiteController extends BaseFrameworkController<AuthorizationScreenWhiteEntity, Long> {

    @Resource
    private AuthorizationScreenWhiteService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * 是否需要授权登录/是否拥有权限
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(value = "loginScreen", method = RequestMethod.GET)
    public ResultParam loginScreen(String ip, String pwd, HttpServletRequest request, HttpServletResponse response) {
        AuthorizationScreenWhiteEntity entity = new AuthorizationScreenWhiteEntity();
        entity.setIp(ip);
        entity.setPassWord(pwd);
        return ResultUtil.success(service.loginScreen(entity, request, response));
    }
    */

    /**
     * 是否需要授权登录/是否拥有权限
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "loginScreen", method = RequestMethod.POST)
    public ResultParam loginScreen(@RequestBody String pwd, HttpServletRequest request, HttpServletResponse response) {
        String ip = GetIp.getIpAddr2(request);
        AuthorizationScreenWhiteEntity entity = new AuthorizationScreenWhiteEntity();
        entity.setIp(ip);
        entity.setPassWord(pwd);
        return service.loginScreen(entity, request, response);
    }
}