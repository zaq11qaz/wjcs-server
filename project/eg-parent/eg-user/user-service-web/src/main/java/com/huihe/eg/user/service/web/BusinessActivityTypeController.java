package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.service.dao.BusinessActivityTypeService;
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
 * @ Date       ：2021/4/14 13:39
 * @ Description：活动类型可用接口说明
 * @ since: JDk1.8
 */
@Api(value="活动类型可用接口说明",description="活动类型可用接口说明",tags = {"活动类型"})
@RestController
@RequestMapping("businessActivityType")
public class BusinessActivityTypeController extends BaseFrameworkController<BusinessActivityTypeEntity, Integer> {

    @Resource
    private BusinessActivityTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/12 20:38
     * @ Description：查询活动类型去除重复选项列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询活动类型去除重复选项列表")
    @GetMapping("queryNoRepeat")
    public ResultParam queryActivityList(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryNoRepeat(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/12 20:38
     * @ Description：条件查询
     * @ since: JDk1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryByMessage(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/12 20:38
     * @ Description：查询需要支付金额
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询需要支付金额")
    @GetMapping("queryNeedPay")
    public ResultParam queryNeedPay(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryNeedPay(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/12 20:38
     * @ Description：查询活动信息
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询活动信息")
    @GetMapping("queryActivityInfo")
    public ResultParam queryActivityInfo(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryActivityInfo(param,request,response));

    }

}