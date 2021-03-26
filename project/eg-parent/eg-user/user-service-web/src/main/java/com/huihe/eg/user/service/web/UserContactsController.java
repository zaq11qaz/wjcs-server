package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserContactsEntity;
import com.huihe.eg.user.service.dao.UserContactsService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通讯录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="通讯录可用接口说明",description="通讯录可用接口说明",tags = {"通讯录"})
@RestController
@RequestMapping("userContacts")
public class UserContactsController extends BaseFrameworkController<UserContactsEntity, Long> {

    @Resource
    private UserContactsService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 发送邀请
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "发送邀请")
    @PostMapping("sendInvitation")
    public ResultParam sendInvitation(@RequestBody UserContactsEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.sendInvitation(entity,request,response);
    }
}