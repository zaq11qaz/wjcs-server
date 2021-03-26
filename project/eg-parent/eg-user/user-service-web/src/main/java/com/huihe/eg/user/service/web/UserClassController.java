package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.model.UserClassEntity;
import com.huihe.eg.user.service.dao.UserClassService;
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
 * @ Date       ：2021/1/5 13:29
 * @ Description：用户直播课程订阅记录
 * @ since: JDk1.8
 */
@Api(value="用户直播课程订阅记录可用接口说明",description="用户直播课程订阅记录可用接口说明",tags = {"用户直播课程订阅记录"})
@RestController
@RequestMapping("userClass")
public class UserClassController extends BaseFrameworkController<UserClassEntity, Long> {

    @Resource
    private UserClassService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    /**
     * 学习中心-直播课程
     * @author zwx
     * @date 2019年11月26日15:12:30
     * @since JDK1.8
     */
    @ApiOperation(value = "学习中心-直播课程")
    @GetMapping("queryUserClassSchedule")
    public ResultParam queryUserClassSchedule(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserClassSchedule(param,request,response));
    }

    /**
     * 查询历史订阅
     * @author zwx
     * @date 2020年5月14日15:46:23
     * @since JDK1.8
     */
    @ApiOperation(value = "查询历史订阅")
    @GetMapping("queryHistoryListPage")
    public ResultParam queryHistoryListPage(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryListPage(param,request,response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 18:38
     * @ Description：查询直播课程表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询直播课程表")
    @GetMapping("queryHistoryListPageCount")
    public ResultParam queryHistoryListPageCount(UserClassEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryListPageCount(param,request,response));
    }

}