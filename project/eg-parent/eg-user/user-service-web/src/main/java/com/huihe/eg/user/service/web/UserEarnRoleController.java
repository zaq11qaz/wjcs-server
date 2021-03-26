package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserEarnRoleEntity;
import com.huihe.eg.user.service.dao.UserEarnRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:29
 * @ Description：招募师收益规则
 * @ since: JDk1.8
 */
@Api(value="招募师收益规则可用接口说明",description="招募师收益规则可用接口说明",tags = {"招募师收益规则"})
@RestController
@RequestMapping("userEarnRole")
public class UserEarnRoleController extends BaseFrameworkController<UserEarnRoleEntity, Long> {

    @Resource
    private UserEarnRoleService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * 设置生效规则
     * @author : zwy
     * 2020-08-25 05:57
     * @since JDK1.8
     */
    @ApiOperation(value = "设置生效规则")
    @PostMapping("updateEffective")
    public ResultParam updateEffective(@RequestBody UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateEffective(param, request, response);
    }

    /**
     * 设置用户生效规则
     * @author : zwy
     * 2020-08-25 05:57
     * @since JDK1.8
     */
    @ApiOperation(value = "设置用户生效规则")
    @PostMapping("updateUserEffective")
    public ResultParam updateUserEffective(@RequestBody UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateUserEffective(param, request, response);
    }

}