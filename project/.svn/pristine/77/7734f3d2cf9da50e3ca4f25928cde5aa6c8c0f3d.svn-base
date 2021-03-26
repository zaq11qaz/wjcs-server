package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.NewsEum;
import com.huihe.eg.message.model.NoteUserEntity;
import com.huihe.eg.message.model.SystemVersionEntity;
import com.huihe.eg.message.service.dao.SystemVersionService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统版本管理
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="系统版本管理可用接口说明",description="系统版本管理可用接口说明",tags = {"系统版本管理"})
@RestController
@RequestMapping("systemVersion")
public class SystemVersionController extends BaseFrameworkController<SystemVersionEntity, Long> {

    @Resource
    private SystemVersionService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * APP版本更新
     * @author zwx
     * @date 2019年12月11日17:54:28
     * @since JDK1.8
     */
    @ApiOperation(value = "APP版本更新", httpMethod = "POST", notes = "APP版本更新")
    @PostMapping({"versionIteration"})
    public ResultParam versionIteration(@RequestBody @ApiParam(name = "添加对象",value = "添加对象",required = true) SystemVersionEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.versionIteration(param,request,response);
    }
}