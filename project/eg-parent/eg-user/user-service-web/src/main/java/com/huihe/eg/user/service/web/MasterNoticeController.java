package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterNoticeEntity;
import com.huihe.eg.user.service.dao.MasterNoticeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 助学师消息中心
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="助学师消息中心可用接口说明",description="助学师消息中心可用接口说明",tags = {"助学师消息中心"})
@RestController
@RequestMapping("masterNotice")
public class MasterNoticeController extends BaseFrameworkController<MasterNoticeEntity, Long> {

    @Resource
    private MasterNoticeService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 查询是否有未读消息
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "查询是否有未读消息")
    @GetMapping("queryIsRead")
    public ResultParam queryIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.queryIsRead(param,request,response);
    }

    /**
     * 修改未读消息
     * @author zwx
     * @date 2019年11月25日17:55:16
     * @since JDK1.8
     */
    @ApiOperation(value = "修改未读消息")
    @PostMapping("updateIsRead")
    public ResultParam updateIsRead(MasterNoticeEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateIsRead(param,request,response);
    }
}