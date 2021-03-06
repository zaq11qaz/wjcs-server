package com.huihe.eg.user.service.dao;

import com.cy.framework.service.dao.BaseFrameworkService;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.UserRecommenderIncomeLogEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface UserRecommenderIncomeLogService extends BaseFrameworkService<UserRecommenderIncomeLogEntity, Long> {
    List<UserRecommenderIncomeLogEntity> queryMyMechanismRecommenderList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryMechanismRecommenderCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserRecommenderIncomeLogEntity> queryMechanismCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    List<UserRecommenderIncomeLogEntity> queryInviteCashList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryInviteCash(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam querySettlementLog(UserRecommenderIncomeLogEntity param);

    List<UserRecommenderIncomeLogEntity> queryDetails(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryCountByTime(MasterAppointmentEntity entity, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryCourseDetails(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> queryMechanismManagerList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam queryMechanismCashTotalList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    void updateInviteCouponCash(UserRecommenderIncomeLogEntity param);

    ResultParam queryRanking(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> queryDetailList(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam detail(UserRecommenderIncomeLogEntity param, HttpServletRequest request, HttpServletResponse response);

    ResultParam insertTransfer(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response);
}