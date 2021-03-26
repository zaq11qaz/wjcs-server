package com.huihe.eg.mall.service.web;

import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.mall.model.RechargeRecordEntity;
import com.huihe.eg.mall.service.dao.pay.PayService;
import com.huihe.eg.mall.service.dao.pay.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 类描述：
 *
 * @author zhangjiacheng
 * @datetime 2019/1/25 15:43
 */
@Api(value = "支付",description = "支付",tags = {"支付"})
@RequestMapping("pay")
@RestController
public class PayController {
    @Resource
    private PayService service;
    @Resource
    private WxPayService wxPayService;
    @ApiOperation(value = "支付宝支付", httpMethod = "POST")
    @RequestMapping(value = "aliPay",method = RequestMethod.POST)
    public ResultParam aliPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.aliPay(entity,request,response));
    }

    @ApiOperation(value = "支付宝回调",httpMethod = "POST")
    @RequestMapping(value = "aliPayNotify")
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response){
        return service.aliPayNotify(request,response);
    }

    @ApiOperation(value = "微信支付", httpMethod = "POST")
    @RequestMapping(value = "wxPay",method = RequestMethod.POST)
    public ResultParam wxPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.wxPay(entity,request,response));
    }

    @ApiOperation(value = "微信回调",httpMethod = "GET")
    @RequestMapping(value = "wxPayNotify",method = RequestMethod.GET)
    public ResultParam wxPayNotify(HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.wxPayNotify(request,response));
    }



    @ApiOperation(value = "微信h5支付")
    @PostMapping(value = "wxH5Pay")
    public ResultParam wxH5Pay(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.wxH5Pay(param,request,response));
    }

    @ApiOperation(value = "退款")
    @PostMapping(value = "appRefund")
    public ResultParam appRefund(@RequestBody RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.appRefund(param,request,response));
    }
    @ApiOperation(value = "获取wx分享签名")
    @GetMapping(value = "getWxShareSign")
    public ResultParam getWxShareSign(@RequestParam String url , HttpServletRequest request, HttpServletResponse response){
        return wxPayService.getWxShareSign(url);
    }
}
