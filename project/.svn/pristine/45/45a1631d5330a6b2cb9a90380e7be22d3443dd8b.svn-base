package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.BusinessActivityUserHistoryEntity;
import com.huihe.eg.user.service.dao.BusinessActivityUserHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value="活动浏览用户记录可用接口说明",description="活动浏览用户记录可用接口说明",tags = {"活动浏览用户记录"})
@RestController
@RequestMapping("businessActivityUserHistory")
public class BusinessActivityUserHistoryController extends BaseFrameworkController<BusinessActivityUserHistoryEntity, Long> {

    @Resource
    private BusinessActivityUserHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/4/30 13:15
     * @ Description：查询参与用户列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询参与用户列表")
    @GetMapping("queryUserList")
    public ResultParam queryUserList( BusinessActivityUserHistoryEntity param, HttpServletRequest request, HttpServletResponse response){
        return ResultUtil.success(service.queryUserList(param,request,response));
    }
}