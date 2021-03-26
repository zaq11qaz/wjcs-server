package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.model.count.AppointmentParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserAppointmentService extends BaseFrameworkService<UserAppointmentEntity, Long> {
    List<UserAppointmentEntity> querySummaryListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryTeacherListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryStudentListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryUserSchedule(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    void querySoonClass();
    ResultParam updateConfirm(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam queryEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    Map<String, Object> queryEarningsStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<AppointmentParam> queryAppointmentTimeStatistics(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryHistoryListPage(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    ResultParam offlineAppointmentSign(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    ResultParam offlineAppointmentEnd(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryMyOrderListPage(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    Map<String, Object> queryTodayListPage(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    ResultParam queryMasterEarningsListPage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<UserAppointmentEntity> queryMasterHistoryListPage(UserAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryByMessage(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertSpecialCourse(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryCourseCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam deleteAppointment(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> querySummaryListPagePC(UserAppointmentEntity userAppointmentEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryUserScheduleListCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryHistoryListPageCount(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    List<UserAppointmentEntity> queryOfflineSchedule(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateCancelCourse(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    void querySoonMechanismCourse();

    void queryEndMechanismCourse();

    ResultParam insertMechanismUser(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateUserConfirm(UserAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
}