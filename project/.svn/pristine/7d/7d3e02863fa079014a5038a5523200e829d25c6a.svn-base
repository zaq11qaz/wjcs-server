package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.authorization.model.ManagerIdentityEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.service.dao.ManagerIdentityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="可用接口说明",description="可用接口说明",tags = {""})
@RestController
@RequestMapping("managerIdentity")
public class ManagerIdentityController extends BaseFrameworkController<ManagerIdentityEntity, Long> {

    @Resource
    private ManagerIdentityService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 角色列表
     * @Description: 分页查询
     * @author zwx
     * @Date : 2019年1月7日
     */
    @ApiOperation(value = "角色列表", httpMethod = "GET", notes = "角色列表")
    @GetMapping({"queryRoleList"})
    public ResultParam queryRoleList(ManagerIdentityEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryRoleList(param,request,response));
    }
}