package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterFollowEntity;
import com.huihe.eg.user.service.dao.MasterFollowService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注直播讲师记录
 *
 * @author zwx
 * @date 2019年11月28日15:44:55
 * @since JDK1.8
 */
@Api(value="用户关注直播讲师记录表可用接口说明",description="用户关注直播讲师记录表可用接口说明",tags = {"用户关注直播讲师记录表"})
@RestController
@RequestMapping("masterFollow")
public class MasterFollowController extends BaseFrameworkController<MasterFollowEntity, Long> {

    @Resource
    private MasterFollowService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询是否关注老师
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询是否关注老师",
            httpMethod = "GET",
            notes = "查询是否关注老师"
    )
    @GetMapping({"queryIsFollow"})
    public ResultParam queryIsFollow(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryIsFollow(param,request,response);
    }

    /**
     * 删除关注记录
     * @author zwx
     * @date 2019年11月28日15:45:42
     * @since JDK1.8
     */
    @ApiOperation(
            value = "删除关注记录",
            httpMethod = "GET",
            notes = "删除关注记录"
    )
    @GetMapping({"deleteFollow"})
    public ResultParam deleteFollow(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.deleteFollow(param,request,response);
    }
}