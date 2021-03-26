package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MechanismClassroomEntity;
import com.huihe.eg.user.service.dao.MechanismClassroomService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/7 15:10
 * @ Description：教室表
 * @ since: JDk1.8
 */
@Api(value="教室表可用接口说明",description="教室表可用接口说明",tags = {"教室表"})
@RestController
@RequestMapping("mechanismClassroom")
public class MechanismClassroomController extends BaseFrameworkController<MechanismClassroomEntity, Long> {

    @Resource
    private MechanismClassroomService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    @ApiOperation(value = "查询可用教室列表")
    @GetMapping("queryClassroomUnused")
    private ResultParam queryClassroomUnused(MechanismClassroomEntity mechanismClassroomEntity , HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryClassroomUnused(mechanismClassroomEntity,request,response));
    }


}