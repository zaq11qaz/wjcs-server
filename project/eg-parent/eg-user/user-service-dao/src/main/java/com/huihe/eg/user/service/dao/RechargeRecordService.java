package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeRecordEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface RechargeRecordService extends BaseFrameworkService<RechargeRecordEntity, Long> {
    Map<String,Object> studyCardCount(RechargeRecordEntity param);
    Map<String,Object> memberAssetsCount(RechargeRecordEntity param);
    Map<String,Object> AssetsCount(RechargeRecordEntity param);
    Map<String,Object> totalAssetsCount(RechargeRecordEntity param);

    Map<String,Object> queryPayCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryTodayFirstTimePay(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryTodayPayTotal(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryBuyCourse(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> queryMyRecordList(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateRechargeRecordStatus(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertCatCoinRecharge(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryRecommendListCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMyEarn(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> querySaleCourse(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismCash(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCardPayList(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCardPayCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryCardPayListByNickName(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryByMessage(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertExperienceCard(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertExperienceLiveCard(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMyRecordListCount(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam payOneCourse(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> queryMechanismList(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    void updateStudyCardStatus(RechargeRecordEntity rechargeRecordEntity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryPayDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> queryMechanismOfflineDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismOfflineDetailsCount(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> queryInfoDetail(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    List<RechargeRecordEntity> queryListPageByStatus(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam tradeRefund(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryPayLiveStreamDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam payOneCourseLiveStream(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryUpdateCourseDetails(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam updateInviteCouponCash(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryDayCash(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response);
}