package com.huihe.eg.mall.service.web;

import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.mall.service.dao.UserInfoService;
import com.huihe.eg.mall.service.impl.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "短信", description = "短信", tags = {"短信"})
@RequestMapping("sms")
@RestController
public class SmsController {
    @Resource
    private SmsService smsService;
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "发送短信", httpMethod = "GET")
    @ApiImplicitParam(value = "手机号", name = "mobile", example = "17682449722")
    @RequestMapping(value = "send", method = RequestMethod.GET)
    public ResultParam send(@RequestParam String mobile,@RequestParam(required = false) String type, HttpServletRequest request, HttpServletResponse response) {
        return smsService.sendSms(mobile,type);
    }

    @ApiOperation(value = "校验", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "手机号", name = "mobile", example = "13018927256"),
            @ApiImplicitParam(value = "验证码", name = "code", example = "64356")
    })
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    public ResultParam validate(@RequestParam String mobile, String code, HttpServletRequest request, HttpServletResponse response) {
        return smsService.validate(mobile, code);
    }
}
