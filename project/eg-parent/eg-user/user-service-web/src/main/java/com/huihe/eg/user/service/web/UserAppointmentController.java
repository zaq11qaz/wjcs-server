package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.service.dao.UserAppointmentService;
import io.swagger.annotations.Api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用户预约助学师记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "用户预约助学师记录可用接口说明", description = "用户预约助学师记录可用接口说明", tags = {"用户预约助学师记录"})
@RestController
@RequestMapping("userAppointment")
public class UserAppointmentController extends BaseFrameworkController<UserAppointmentEntity, Long> {

    @Resource
    private UserAppointmentService service;

    @Override
    public void init() {
        setBaseService(service);
    }




    /**
     * 总结列表
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "总结列表")
    @GetMapping("querySummaryListPage")
    public ResultParam querySummaryListPage(UserAppointmentEntity userAppointmentEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySummaryListPage(userAppointmentEntity, request, response));
    }

    @ApiOperation(value = "总结列表pc")
    @GetMapping("querySummaryListPagePC")
    public ResultParam querySummaryListPagePC(UserAppointmentEntity userAppointmentEntity, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.querySummaryListPagePC(userAppointmentEntity, request, response));
    }

    /**
     * 学习中心-我的老师
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "学习中心-我的老师")
    @GetMapping("queryTeacherListPage")
    public ResultParam queryTeacherListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTeacherListPage(param, request, response));
    }

    /**
     * 教学中心-我的学生
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "教学中心-我的学生")
    @GetMapping("queryStudentListPage")
    public ResultParam queryStudentListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryStudentListPage(param, request, response));
    }

    /**
     * 学习中心-我的课程
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "学习中心-我的课程")
    @GetMapping("queryUserSchedule")
    public ResultParam queryUserSchedule(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserSchedule(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 18:46
     * @ Description：学习中心-我的课程PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "学习中心-我的课程PC")
    @GetMapping("queryUserScheduleListCount")
    public ResultParam queryUserScheduleListCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryUserScheduleListCount(param, request, response));
    }


    /**
     * 修改/取消的回复
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "修改/取消的回复",
            httpMethod = "POST",
            notes = "修改/取消的回复")
    @PostMapping("updateConfirm")
    public ResultParam updateConfirm(@RequestBody UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateConfirm(param, request, response);
    }

    /**
     * 教学中心-我的收益
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "教学中心-我的收益")
    @GetMapping("queryEarningsListPage")
    public ResultParam queryEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryEarningsListPage(param, request, response);
    }

    /**
     * 后台 助学师管理 收益统计
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台 助学师管理 收益统计")
    @GetMapping("queryEarningsStatistics")
    public ResultParam queryEarningsStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryEarningsStatistics(param, request, response));
    }

    /**
     * 后台-助学师中心-助学师收益
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "后台-助学师中心-助学师收益")
    @GetMapping("queryAppointmentTimeStatistics")
    public ResultParam queryAppointmentTimeStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryAppointmentTimeStatistics(param, request, response));
    }

    /**
     * 查询历史课程
     *
     * @author zwx
     * @date 2020年5月14日15:46:23
     * @since JDK1.8
     */
    @ApiOperation(value = "查询历史课程")
    @GetMapping("queryHistoryListPage")
    public ResultParam queryHistoryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryListPage(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/17 19:06
     * @ Description：查询历史课程PC
     * @ since: JDk1.8
     */
    @ApiOperation(value = "查询历史课程PC")
    @GetMapping("queryHistoryListPageCount")
    public ResultParam queryHistoryListPageCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryHistoryListPageCount(param, request, response));
    }

    /**
     * 线下课程签到
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "线下课程签到")
    @PostMapping("offlineAppointmentSign")
    public ResultParam offlineAppointmentSign(@RequestBody UserAppointmentEntity userAppointmentEntity, HttpServletRequest request, HttpServletResponse response) {
        return service.offlineAppointmentSign(userAppointmentEntity, request, response);
    }

    /**
     * 线下课程结束
     *
     * @author zwx
     * @date 2019年11月25日18:07:26
     * @since JDK1.8
     */
    @ApiOperation(value = "线下课程结束")
    @PostMapping("offlineAppointmentEnd")
    public ResultParam offlineAppointmentEnd(@RequestBody UserAppointmentEntity userAppointmentEntity, HttpServletRequest request, HttpServletResponse response) {
        return service.offlineAppointmentEnd(userAppointmentEntity, request, response);
    }

    /**
     * 我的订单
     *
     * @author zwx
     * @date 2020年7月2日14:24:40
     * @since JDK1.8
     */
    @ApiOperation(value = "我的订单")
    @GetMapping("queryMyOrderListPage")
    public ResultParam queryMyOrderListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMyOrderListPage(param, request, response));
    }

    /**
     * 今日课程
     *
     * @author zwx
     * @date 2020年7月2日19:24:42
     * @since JDK1.8
     */
    @ApiOperation(value = "今日课程")
    @GetMapping("queryTodayListPage")
    public ResultParam queryTodayListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryTodayListPage(param, request, response));
    }

    /**
     * 助学师收益
     *
     * @author zwx
     * @date 2020年7月13日21:04:37
     * @since JDK1.8
     */
    @ApiOperation(value = "助学师收益")
    @GetMapping("queryMasterEarningsListPage")
    public ResultParam queryMasterEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.queryMasterEarningsListPage(param, request, response);
    }

    /**
     * 查询助学师历史课程
     *
     * @author zwx
     * @date 2020年7月15日18:05:10
     * @since JDK1.8
     */
    @ApiOperation(value = "查询助学师历史课程")
    @GetMapping("queryMasterHistoryListPage")
    public ResultParam queryMasterHistoryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryMasterHistoryListPage(param, request, response));
    }

    /**
     * 条件查询
     *
     * @author : zwy
     * 2020-07-30 01:30
     * @since JDK1.8
     */
    @ApiOperation(value = "条件查询")
    @GetMapping("queryByMessage")
    public ResultParam queryByMessage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryByMessage(param, request, response));
    }

    /**
     * 预约专属课程
     *
     * @author : zwy
     * 2020-08-28 04:46
     * @since JDK1.8
     */
    @ApiOperation(value = "预约专属课程")
    @PostMapping("insertSpecialCourse")
    public ResultParam insertSpecialCourse(@RequestBody UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.insertSpecialCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/9/12 17:43
     * @ Description：课程统计
     * @since: JDk1.8
     */
    @ApiOperation(value = "课程统计")
    @GetMapping("queryCourseCount")
    public ResultParam queryCourseCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryCourseCount(param, request, response));
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2020/11/4 10:28
     * @ Description：小班课取消预约
     * @ since: JDk1.8
     */
    @ApiOperation(value = "小班课取消预约")
    @PostMapping("deleteAppointment")
    public ResultParam deleteAppointment(@RequestBody UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.deleteAppointment(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/11 15:56
     * @ Description：用户线下机构课程预约列表
     * @ since: JDk1.8
     */
    @ApiOperation(value = "用户线下机构课程预约列表")
    @GetMapping("queryOfflineSchedule")
    public ResultParam queryOfflineSchedule( UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.success(service.queryOfflineSchedule(param, request, response));
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 16:09
     * @ Description：用户取消预约
     * @ since: JDk1.8
     */
    @ApiOperation(value = "用户取消预约")
    @PostMapping("updateCancelCourse")
    public ResultParam updateCancelCourse(@RequestBody UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateCancelCourse(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2020/12/25 16:09
     * @ Description：修改状态
     * @ since: JDk1.8
     */
    @ApiOperation(value = "修改状态")
    @GetMapping("updateStatus")
    public void updateStatus( RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        service.updateStatus(param, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/22 19:26
     * @ Description：
     * @ since: JDk1.8
     */
    @ApiOperation(value = "同意取消")
    @PostMapping("updateUserConfirm")
    public ResultParam updateUserConfirm(@RequestBody UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.updateUserConfirm(param, request, response);
    }

}