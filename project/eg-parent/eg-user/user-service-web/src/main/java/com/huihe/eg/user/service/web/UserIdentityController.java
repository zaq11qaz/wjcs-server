package com.huihe.eg.user.service.web;

import com.alibaba.druid.sql.visitor.functions.If;
import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserIdentityEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.service.dao.OverseasIdentityService;
import com.huihe.eg.user.service.dao.UserIdentityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 实名认证
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="实名认证可用接口说明",description="实名认证可用接口说明",tags = {"实名认证"})
@RestController
@RequestMapping("userIdentity")
public class UserIdentityController extends BaseFrameworkController<UserIdentityEntity, Long> {

    @Resource
    private UserIdentityService service;
    @Resource
    private OverseasIdentityService overseasIdentityService;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 后台 认证中心 实名认证列表
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 实名认证列表")
    @GetMapping("memberCount")
    public ResultParam queryIdentityListPage(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryIdentityListPage(identityEntity,request,response));
    }
    /**
     * 后台 认证中心 实名认证审核
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 认证中心 实名认证审核")
    @PostMapping("realNameAudit")
    public ResultParam realNameAudit(@RequestBody UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return service.realNameAudit(identityEntity,request,response);
    }

    /**
     * 实名状态统计
     * @author: zwy
     * @date: 16:36 2020/7/8
     * @since: JDk1.8
     */
    @ApiOperation(value = "实名状态统计", httpMethod = "GET", notes = "实名状态统计")
    @GetMapping("realNameAuditCount")
    public ResultParam queryRealNameAuditCount(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryRealNameAuditCount(identityEntity,request,response));
    }

    /**
     * 根据条件查询userIdentity
     * @author: zwy
     * @date: 11:20 2020/7/10
     * @since: JDk1.8
     */
    @ApiOperation(value = "根据条件查询userInfo", httpMethod = "GET", notes = "根据条件查询userInfo")
    @RequestMapping("queryByMessage")
    public ResultParam queryByMessage(UserIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

}