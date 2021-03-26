package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.message.model.GroupWhitelistedEntity;
import com.huihe.eg.message.model.MessageGroupEntity;
import com.huihe.eg.message.service.dao.GroupWhitelistedService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用户的群白名单
 *
 * @author zwx
 * @date 2019年11月26日11:21:54
 * @since JDK1.8
 */
@Api(value="用户的群白名单可用接口说明",description="用户的群白名单可用接口说明",tags = {"用户的群白名单"})
@RestController
@RequestMapping("groupWhitelisted")
public class GroupWhitelistedController extends BaseFrameworkController<GroupWhitelistedEntity, Long> {

    @Resource
    private GroupWhitelistedService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 通过白名单查询是否加群
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "通过白名单查询是否加群", httpMethod = "GET", notes = "通过白名单查询是否加群")
    @GetMapping({"queryIsAdoptWhitelisted"})
    public ResultParam queryIsAdoptWhitelisted(GroupWhitelistedEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryIsAdoptWhitelisted(param,request,response);
    }
    /**
     * 多条删除
     * @author zwx
     * @date 2019年11月26日11:23:04
     * @since JDK1.8
     */
    @ApiOperation(value = "多条删除", httpMethod = "GET", notes = "多条删除")
    @GetMapping({"deleteList"})
    public ResultParam deleteList( String ids, HttpServletRequest request, HttpServletResponse response){
        return service.deleteList(ids,request,response);
    }
    @RequestMapping("queryGroupIds")
    @ResponseBody
    public String queryGroupIds(@RequestBody Long user_id) {
        return service.queryGroupIds(user_id).toString();
    }
}