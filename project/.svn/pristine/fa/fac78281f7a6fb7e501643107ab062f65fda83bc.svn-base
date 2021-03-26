package com.huihe.eg.user.service.web;

import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.impl.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 短信
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "短信", description = "短信", tags = {"短信"})
@RequestMapping("sms")
@RestController
public class SmsController {
    @Resource
    private SmsService smsService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 发送短信
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "发送短信", httpMethod = "GET")
    @ApiImplicitParam(value = "手机号", name = "mobile", example = "17682449722")
    @RequestMapping(value = "send", method = RequestMethod.GET)
    public ResultParam send(@RequestParam String mobile,@RequestParam(required = false) String type, HttpServletRequest request, HttpServletResponse response) {
        return smsService.sendSms(mobile,type);
    }
    /**
     * 短信校验
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "校验", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "手机号", name = "mobile", example = "13018927256"),
            @ApiImplicitParam(value = "验证码", name = "code", example = "64356")
    })
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    public ResultParam validate(@RequestParam String mobile, String code, HttpServletRequest request, HttpServletResponse response) {
        return smsService.validate(mobile, code);
    }

    /**
     * 发送短信
     * @author zwy
     * @date 2020年9月12日 10:49:52
     * @since JDK1.8
     */
    @ApiOperation(value = "发送短信", httpMethod = "GET")
    @ApiImplicitParams( {
        @ApiImplicitParam(value = "手机号", name = "mobile", example = "17682449722"),
        @ApiImplicitParam(value = "内容", name = "content", example = "1")
    })
    @RequestMapping(value = "sendPassSms", method = RequestMethod.GET)
    public ResultParam sendPassSms(@RequestParam String mobile,String content, HttpServletRequest request, HttpServletResponse response) {
        return smsService.sendPassSms(mobile,content);
    }
}
