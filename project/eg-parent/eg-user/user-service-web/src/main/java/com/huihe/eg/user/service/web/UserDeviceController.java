package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.StudyCardEntity;
import com.huihe.eg.user.model.UserDeviceEntity;
import com.huihe.eg.user.service.dao.UserDeviceService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户设备
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="设备可用接口说明",description="设备可用接口说明",tags = {"设备"})
@RestController
@RequestMapping("userDevice")
public class UserDeviceController extends BaseFrameworkController<UserDeviceEntity, Long> {

    @Resource
    private UserDeviceService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询是否有
     * @author : zwy
     * 2020-08-04 03:06
     * @since JDK1.8
     */
    @ApiOperation(
            value = "查询是否有",
            httpMethod = "GET",
            notes = "查询是否有"
    )
    @GetMapping("queryIfExist")
    public ResultParam queryIfExist(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryIfExist(param,request,response));
    }

    /**
     * 条件查询
     * @author : zwy
     * 2020-08-20 04:50
     * @since JDK1.8
     */
    @ApiOperation(
            value = "条件查询",
            httpMethod = "GET",
            notes = "条件查询"
    )
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserDeviceEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryByMessage(param,request,response));
    }

}