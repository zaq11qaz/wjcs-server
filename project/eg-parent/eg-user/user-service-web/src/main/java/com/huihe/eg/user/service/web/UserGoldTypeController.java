package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserGoldTypeEntity;
import com.huihe.eg.user.service.dao.UserGoldTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务奖励列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="任务奖励列表可用接口说明",description="任务列表可用接口说明",tags = {"任务奖励列表"})
@RestController
@RequestMapping("userGoldType")
public class UserGoldTypeController extends BaseFrameworkController<UserGoldTypeEntity, Long> {

    @Resource
    private UserGoldTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/23 14:26
     * @ Description：查询教付保任务
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询教付保任务")
    @GetMapping("queryTeachPaypal")
    public ResultParam queryTeachPaypal(UserGoldTypeEntity param ,HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryTeachPaypal(param,request,response));
    }

}