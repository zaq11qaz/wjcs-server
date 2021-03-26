package com.huihe.eg.user.service.web;

import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.pay.IosPayParam;
import com.huihe.eg.user.service.dao.pay.IosPayService;
import com.huihe.eg.user.service.dao.pay.PayService;
import com.huihe.eg.user.service.dao.pay.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 支付
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "支付",description = "支付",tags = {"支付"})
@RequestMapping("pay")
@RestController
public class PayController {
    @Resource
    private PayService service;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private IosPayService iosPayService;

    /**
     * 支付宝支付
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝支付", httpMethod = "POST")
    @RequestMapping(value = "aliPay",method = RequestMethod.POST)
    public ResultParam aliPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.aliPay(entity,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/5 15:16
     * @ Description：支付宝退款
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付宝退款", httpMethod = "POST")
    @RequestMapping(value = "aliPayTradeRefund",method = RequestMethod.POST)
    public ResultParam aliPayTradeRefund(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliPayTradeRefund(entity,request,response);
    }
    /**
     * 支付宝回调
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝回调",httpMethod = "POST")
    @RequestMapping(value = "aliPayNotify")
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response){
        return service.aliPayNotify(request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/3/5 15:16
     * @ Description：微信退款
     * @ since: JDk1.8
     */
    @ApiOperation(value = "微信退款", httpMethod = "POST")
    @RequestMapping(value = "wxPayRefund",method = RequestMethod.POST)
    public ResultParam wxPayRefund(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxPayRefund(entity,request,response);
    }

    /**
     * 微信支付
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "微信支付", httpMethod = "POST")
    @RequestMapping(value = "wxPay",method = RequestMethod.POST)
    public ResultParam wxPay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxPay(entity,request,response);
    }
    /**
     * 微信回调
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "微信回调",httpMethod = "GET")
    @RequestMapping(value = "wxPayNotify",method = RequestMethod.POST)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response){
        service.wxPayNotify(request,response);
    }

    @ApiOperation(value = "ios内购验证")
    @PostMapping(value = "iosPayNotify")
    public ResultParam iosPayNotify(@RequestBody IosPayParam param, HttpServletRequest request, HttpServletResponse response){
        return iosPayService.setIapCertificate(param,request,response);
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

    /**
     * 支付宝转账
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "支付宝转账", httpMethod = "POST")
    @RequestMapping(value = "aliTransfer",method = RequestMethod.POST)
    public ResultParam aliTransfer(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.aliTransfer(entity,request,response));
    }

    /**
     * 支付宝拉取权限
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "H5支付宝拉取权限", httpMethod = "POST")
    @RequestMapping(value = "H5aliAuth",method = RequestMethod.POST)
    public ResultParam H5aliAuth(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.H5aliAuth(entity,request,response));
    }

    /**
     * 获得唯一标识
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "H5获得唯一标识", httpMethod = "POST")
    @RequestMapping(value = "H5aliGetToken",method = RequestMethod.POST)
    public ResultParam H5aliGetToken(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.H5aliGetToken(entity,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 11:47
     * @ Description：支付宝授权回调
     * @since: JDk1.8
     */
    @ApiOperation(value = "H5支付宝授权回调",httpMethod = "POST")
    @RequestMapping(value = "aliPayAuthNotify")
    public String H5aliPayAuthNotify(HttpServletRequest request, HttpServletResponse response){
        return service.H5aliPayAuthNotify(request,response);
    }
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 15:56
     * @ Description：用户登陆授权url
     * @since: JDk1.8
     */
    @ApiOperation(value = "用户登陆授权url",httpMethod = "POST")
    @RequestMapping(value = "aliUserAuthUrl")
    public ResultParam aliUserAuthUrl(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliUserAuthUrl( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/29 15:56
     * @ Description：用户登录授权
     * @since: JDk1.8
     */
    @ApiOperation(value = "用户登录授权",httpMethod = "POST")
    @RequestMapping(value = "aliUserAuth")
    public ResultParam aliUserAuth(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliUserAuth( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 16:00
     * @ Description：微信转账
     * @ since: JDk1.8
     */
    @ApiOperation(value = "wx转账",httpMethod = "POST")
    @RequestMapping(value = "wxTransfer")
    public ResultParam wxTransfer(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.wxTransfer( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 16:16
     * @ Description：阿里预支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "阿里预支付",httpMethod = "POST")
    @RequestMapping(value = "aliPrepayment")
    public ResultParam aliPrepayment(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        try {
            return service.aliPrepayment( entity,request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/14 10:43
     * @ Description：支付宝预授权回调
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付宝预授权回调",httpMethod = "POST")
    @RequestMapping(value = "aliPrepaymentNotify")
    public ResultParam aliPrepaymentNotify( HttpServletRequest request, HttpServletResponse response){
        return service.aliPrepaymentNotify(request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/7 19:09
     * @ Description：授权转支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "授权转支付",httpMethod = "POST")
    @RequestMapping(value = "aliPrepayment2Pay")
    public ResultParam aliPrepayment2Pay(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.aliPrepayment2Pay( entity,request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/22 17:46
     * @ Description：
     * @ since: JDk1.8
     */
    @ApiOperation(value = "支付前置",httpMethod = "POST")
    @RequestMapping(value = "payPre")
    public ResultParam payPre(@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.payPre( entity,request, response);
    }    
    
    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 16:17
     * @ Description：取消支付
     * @ since: JDk1.8
     */
    @ApiOperation(value = "取消支付",httpMethod = "POST")
    @RequestMapping(value = "fundAuthOrderUnFreeze")
    public ResultParam fundAuthOrderUnFreeze (@RequestBody RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response){
        try {
            return service.fundAuthOrderUnFreeze( entity,request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    

}
