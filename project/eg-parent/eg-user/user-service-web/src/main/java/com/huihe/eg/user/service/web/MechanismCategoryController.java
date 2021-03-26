package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MechanismCategoryEntity;
import com.huihe.eg.user.service.dao.MechanismCategoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="机构类目可用接口说明",description="机构类目可用接口说明",tags = {"机构类目"})
@RestController
@RequestMapping("mechanismCategory")
public class MechanismCategoryController extends BaseFrameworkController<MechanismCategoryEntity, Long> {

    @Resource
    private MechanismCategoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }


    @ApiOperation(value = "查询类目列表")
    @GetMapping("queryListPageChild")
    public ResultParam queryListPageChild(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryListPageChild(param,request,response));
    }

    @ApiOperation(value = "查询教辅学科")
    @GetMapping("querySubjects")
    public ResultParam querySubjects(MechanismCategoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySubjects(param,request,response));
    }


}