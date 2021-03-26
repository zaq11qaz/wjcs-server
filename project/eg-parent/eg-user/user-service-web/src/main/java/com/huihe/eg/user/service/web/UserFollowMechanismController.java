package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserFollowMechanismEntity;
import com.huihe.eg.user.service.dao.UserFollowMechanismService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:29
 * @ Description：用户关注机构列表
 * @ since: JDk1.8
 */
@Api(value="用户关注机构列表可用接口说明",description="用户关注机构列表可用接口说明",tags = {"用户关注机构列表"})
@RestController
@RequestMapping("userFollowMechanism")
public class UserFollowMechanismController extends BaseFrameworkController<UserFollowMechanismEntity, Long> {

    @Resource
    private UserFollowMechanismService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    @ApiOperation(value = "查询数量")
    @Override
    @GetMapping("queryListPageCount")
    public ResultParam queryListPageCount(UserFollowMechanismEntity map, HttpServletRequest request, HttpServletResponse response) {
        return super.queryListPageCount(map, request, response);
    }

    @ApiOperation(value = "批量添加假数据")
//    @GetMapping("insertForList")
    public ResultParam insertForList(UserFollowMechanismEntity map, HttpServletRequest request, HttpServletResponse response) {
        return service.insertForList(map, request, response);
    }


}