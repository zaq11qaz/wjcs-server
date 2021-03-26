package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.SpecialLecturerEntity;
import com.huihe.eg.user.service.dao.SpecialLecturerService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value="特约直播讲师接口",description="特约直播讲师接口",tags = {"特约直播讲师接口"})
@RestController
@RequestMapping("specialLecturer")
public class SpecialLecturerController extends BaseFrameworkController<SpecialLecturerEntity, Long> {

    @Resource
    private SpecialLecturerService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * 取消特约讲师身份
     * @author : zwy
     * 2020-08-24 10:14
     * @since JDK1.8
     */
    @PostMapping("updateCancel")
    @ApiOperation(value = "取消特约讲师身份")
    public ResultParam updateCancel(@RequestBody SpecialLecturerEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateCancel(param,request,response);
    }
}