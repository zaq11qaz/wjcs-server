package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.SignInEntity;
import com.huihe.eg.user.service.dao.SignInService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 签到
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="签到可用接口说明",description="签到可用接口说明",tags = {"签到"})
@RestController
@RequestMapping("signIn")
public class SignInController extends BaseFrameworkController<SignInEntity, Long> {

    @Resource
    private SignInService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 签到明细
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "签到明细",httpMethod = "GET")
    @RequestMapping(value = "querySignDetail",method = RequestMethod.GET)
    public ResultParam querySignDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.querySignDetail(entity,request,response));
    }

    @ApiOperation(value = "教付保签到明细",httpMethod = "GET")
    @GetMapping(value = "queryTeachPaypalDetail")
    public ResultParam queryTeachPaypalDetail(SignInEntity entity, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryTeachPaypalDetail(entity,request,response));
    }

    @ApiOperation(value = "签到")
    @GetMapping(value = "insertLogin")
    public ResultParam insertLogin(SignInEntity entity, HttpServletRequest request, HttpServletResponse response){
        return service.insertLogin(entity,request,response);
    }
}