package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.PushHistoryEntity;
import com.huihe.eg.user.service.dao.PushHistoryService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 推送记录
 *
 * Author : zwy
 * 2020/7/29 0029 11:27
 * @since JDK1.8
 */
@Api(value="推送记录可用接口说明",description="推送记录可用接口说明",tags = {"推送记录"})
@RestController
@RequestMapping("pushHistory")
public class PushHistoryController extends BaseFrameworkController<PushHistoryEntity, Long> {

    @Resource
    private PushHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询是否有未读消息
     * @author: zwy
     * @date 2020/07/18 21:15
     * @since: JDK1.8
     */
    @ApiOperation(value = "查询是否有未读消息")
    @GetMapping("queryIsRead")
    public ResultParam queryIsRead(PushHistoryEntity pushHistoryEntity, HttpServletRequest request , HttpServletResponse response){
        return ResultUtil.success(service.queryIsRead(pushHistoryEntity,request,response));
    }

}