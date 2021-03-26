package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.authorization.model.LogEntity;
import com.huihe.eg.authorization.service.dao.LogService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="后台管理操作记录可用接口说明",description="后台管理操作记录可用接口说明",tags = {"后台管理操作记录"})
@RestController
@RequestMapping("log")
public class LogController extends BaseFrameworkController<LogEntity, Integer> {

    @Resource
    private LogService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @Description: 新增日志
     * @author zwy
     * @Date : 2020年10月19日 16:08:17
     */
    @ApiOperation(value = "新增日志", httpMethod = "Post", notes = "新增日志")
    @PostMapping({"insertLog"})
    public ResultParam insertLog(String requestMethod , String requestURI , String body , String userID){
        return service.insertLog(requestMethod , requestURI , body , userID);
    }

}