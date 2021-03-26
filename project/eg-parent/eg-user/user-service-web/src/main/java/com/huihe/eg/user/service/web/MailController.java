package com.huihe.eg.user.service.web;

import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.service.dao.UserInfoService;
import com.huihe.eg.user.service.impl.mail.MailService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮箱
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "邮箱", description = "邮箱", tags = {"邮箱"})
@RequestMapping("mail")
@RestController
public class MailController {
    @Resource
    private MailService mailService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 发送邮箱验证码
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    @ApiOperation(value = "发送邮箱验证码", httpMethod = "GET")
    @ApiImplicitParam(value = "邮箱号", name = "mail", example = "960990560@qq.com")
    @RequestMapping(value = "send", method = RequestMethod.GET)
    public ResultParam send(@RequestParam String mail, @RequestParam(required = false)String type, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(mailService.sendMail(mail,type));
    }

    /**
     * 校验邮箱验证码
     * @author zwx
     * @date 2019年11月25日17:52:49
     * @since JDK1.8
     */
    @ApiOperation(value = "校验邮箱验证码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "邮箱号", name = "mail", example = "13018927256"),
            @ApiImplicitParam(value = "验证码", name = "code", example = "64356")
    })
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    public ResultParam validate(@RequestParam String mail, @RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        return mailService.validate(mail, code);
    }
}
