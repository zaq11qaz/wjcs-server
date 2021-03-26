package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.OverseasIdentityEntity;
import com.huihe.eg.user.model.UserIdentityEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.service.dao.OverseasIdentityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 境外身份认证
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="境外身份认证可用接口说明",description="境外身份认证可用接口说明",tags = {"境外身份认证"})
@RestController
@RequestMapping("overseasIdentity")
public class OverseasIdentityController extends BaseFrameworkController<OverseasIdentityEntity, Long> {

    @Resource
    private OverseasIdentityService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 后台 认证中心 境外认证
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 境外认证")
    @GetMapping("queryOverseasAuthListPage")
    public ResultParam queryOverseasAuthListPage(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryOverseasAuthListPage(identityEntity,request,response));
    }

    /**
     * 后台 认证中心 境外认证审核
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 境外认证审核")
    @PostMapping("overseasAudit")
    public ResultParam overseasAudit(@RequestBody  OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return service.overseasAudit(identityEntity,request,response);
    }

    /**
     * 身份认证统计
     * @author: zwy
     * @date: 16:36 2020/7/8
     * @since: JDk1.8
     */
    @GetMapping("overseasAuditCount")
    public ResultParam queryOverseasAuditCount(OverseasIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryOverseasAuditCount(identityEntity,request,response));
    }

    /**
     * 根据条件查询审核列表
     * @author: zwy
     * @date: 11:20 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "根据条件查询审核列表", httpMethod = "GET", notes = "根据条件查询审核列表")
    @RequestMapping("queryByMessage")
    public ResultParam queryByMessage(OverseasIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

}