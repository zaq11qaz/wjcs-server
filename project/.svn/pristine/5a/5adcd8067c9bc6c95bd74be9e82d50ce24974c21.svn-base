package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.CommodityCouponEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.service.dao.CommodityCouponService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 优惠、商品劵列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="优惠、商品劵列表可用接口说明",description="优惠、商品劵列表可用接口说明",tags = {"优惠、商品劵列表"})
@RestController
@RequestMapping("commoditycoupon")
public class CommodityCouponController extends BaseFrameworkController<CommodityCouponEntity, Long> {

    @Resource
    private CommodityCouponService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 领取券
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    @ApiOperation(value = "领取券", httpMethod = "GET", notes = "领取券")
    @GetMapping({"ReceiveCoupon"})
    public ResultParam ReceiveCoupon(CommodityCouponEntity map, HttpServletRequest request, HttpServletResponse response) {
        return service.ReceiveCoupon(map, request, response);
    }

    /**
     * 创建券
     */
    @ApiOperation(value = "创建券", httpMethod = "POST", notes = "领取券")
    @PostMapping({"insertCouponList"})
    public ResultParam insertCouponList(@RequestBody CommodityCouponEntity commodityCouponEntity, HttpServletRequest request, HttpServletResponse response) {
        return service.insertCouponList(commodityCouponEntity, request, response);
    }

    /**
     * test
     */
    @GetMapping("getFinalAmount")
    public ResultParam getFinalAmount(RechargeRecordEntity entity) {
        return ResultUtil.success(service.getFinalAmount(entity));
    }

}