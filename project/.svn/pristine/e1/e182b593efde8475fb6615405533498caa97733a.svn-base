package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterInfoEntity;
import com.huihe.eg.user.model.SystemRechargeEntity;
import com.huihe.eg.user.service.dao.SystemRechargeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="后台充值猫币或学习卡记录可用接口说明",description="后台充值猫币或学习卡记录可用接口说明",tags = {"后台充值猫币或学习卡记录"})
@RestController
@RequestMapping("systemRecharge")
public class SystemRechargeController extends BaseFrameworkController<SystemRechargeEntity, Long> {

    @Resource
    private SystemRechargeService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * 猫币充值统计
     * @author: zwy
     * @date: 18:11 2020/7/7
     * @since: JDk1.8
     */
    @ApiOperation(value = "猫币充值统计")
    @GetMapping("queryCatCoinCount")
    public ResultParam queryCatCoinCount(SystemRechargeEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCatCoinCount(param, request, response));
    }

}