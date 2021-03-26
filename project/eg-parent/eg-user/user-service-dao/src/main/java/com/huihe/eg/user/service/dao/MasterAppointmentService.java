package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MasterAppointmentService extends BaseFrameworkService<MasterAppointmentEntity, Long> {

    List<MasterAppointmentEntity> querySchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    ResultParam queryAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryClassListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryIsClassListPage(MasterAppointmentEntity param );
    List<MasterAppointmentEntity> queryIsClassListPageClose(MasterAppointmentEntity param );
    void getQueryExpireClass();
    void queryClassStartPush();
    ResultParam deleteAppointment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryNewAppointment(MasterAppointmentEntity param );
    List<MasterAppointmentEntity> queryHistoryListPage(MasterAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryBeginListPage(MasterAppointmentEntity param,HttpServletRequest request, HttpServletResponse response );
    List<MasterAppointmentEntity> queryHomeListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryDetailsListPage(MasterAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);
    ResultParam masterAppointmentAudit(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryMechanismAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryMechanismSchedule(MasterAppointmentEntity param , HttpServletRequest request, HttpServletResponse response);
    Map<String,Object> mechanismCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
    List<MasterAppointmentEntity> queryProfitListPage(MasterAppointmentEntity param ,HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryOfflineMaster(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> countAppoinment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryEachType(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCourseCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object>  queryByMessage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCountNum(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateShowList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam checkIsSpecial(Long user_id, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateCourseMaster(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryLiveCash(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateSettlement(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object>  querySettlementList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> querySoonList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryBeginClass(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryHistoryList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMasterAppointmentListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismAppCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismAppointment(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateLikeCount(Long id, HttpServletRequest request, HttpServletResponse response);
    void updateLikeCountCancel(Long id, HttpServletRequest request, HttpServletResponse response);
    void updateShareCount(Long id, HttpServletRequest request, HttpServletResponse response);
    void updateCommentCount(Long id, HttpServletRequest request, HttpServletResponse response);

    void updateCash(final RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryBeginListPageCount(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryCourseSettlement(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateSingleCourseCash(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertMechanismOfflineCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterAppointmentEntity> queryOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    List<MasterAppointmentEntity> queryMechanismOfflineSchedule(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryPayPalEarn(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterAppointmentEntity> queryPayPalEarnList(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertMechanismCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertOfflineCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryMechanismCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    void querySoonMechanismCourse();

    ResultParam queryStatusById(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    List<MasterAppointmentEntity> queryMechanismOfflineScheduleListPage(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertCopyCourse(MasterAppointmentEntity param, HttpServletRequest request, HttpServletResponse response);
}