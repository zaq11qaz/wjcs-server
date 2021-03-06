package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.BusinessMechanismActivityTypeEntity;
import com.huihe.eg.user.service.dao.BusinessMechanismActivityTypeService;
import com.huihe.eg.user.service.web.aop.ClickLog;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="机构活动列表可用接口说明",description="机构活动列表可用接口说明",tags = {"机构活动列表"})
@RestController
@RequestMapping("businessMechanismActivityType")
public class BusinessMechanismActivityTypeController extends BaseFrameworkController<BusinessMechanismActivityTypeEntity, Long> {

    @Resource
    private BusinessMechanismActivityTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/12 20:38
     * @ Description：查询活动详情
     * @ since: JDk1.8
     */
    @ClickLog(description = "用户点击活动详情")
    @ApiOperation(value = "查询活动详情")
    @GetMapping("queryActivityDetail")
    public ResultParam queryActivityDetail(BusinessMechanismActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryActivityDetail(param,request,response));
    }


}