package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.InviteRankEntity;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.model.UserRecommenderIncomeLogEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface UserRecommenderIncomeLogMapper extends BaseFrameworkMapper<UserRecommenderIncomeLogEntity, Long> {
    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    int insert(UserRecommenderIncomeLogEntity record);

    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    int insertSelective(UserRecommenderIncomeLogEntity record);

    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    UserRecommenderIncomeLogEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    int updateByPrimaryKeySelective(UserRecommenderIncomeLogEntity record);

    /**
     * @mbggenerated 2020-09-07
     */
    @Override
    int updateByPrimaryKey(UserRecommenderIncomeLogEntity record);

    /**
     * 机构外教收益
     * @param userRecommenderIncomeLogEntity1
     * @return
     */
    BigDecimal querySumMechanismMasterCash(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity1);

    List<UserRecommenderIncomeLogEntity> queryMechanismList(@Param("param") UserRecommenderIncomeLogEntity mechanismRecommenderEntity);

    List<UserRecommenderIncomeLogEntity> queryMasterList(@Param("param") UserRecommenderIncomeLogEntity masterRecommenderEntity);

    BigDecimal queryMechanismThisDayTotal(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    BigDecimal queryMasterThisDayTotal(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    BigDecimal queryDayCash(@Param("param")UserRecommenderIncomeLogEntity param);

    BigDecimal queryMonthCash(@Param("param")UserRecommenderIncomeLogEntity param);

    BigDecimal queryTotalCash(@Param("param")UserRecommenderIncomeLogEntity param);

    List<UserRecommenderIncomeLogEntity> queryMechanismCashList(@Param("param") UserRecommenderIncomeLogEntity param);

    Integer querySumMechanismCash(@Param("param") UserRecommenderIncomeLogEntity userRecommenderEntity);

    UserRecommenderIncomeLogEntity queryCashList(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    List<UserRecommenderIncomeLogEntity> queryInviteUserList(@Param("param") UserRecommenderIncomeLogEntity param);

    /**
     * 查询日邀请收入
     * @param param
     * @return
     */
    BigDecimal queryDayInviteCash(@Param("param") UserRecommenderIncomeLogEntity param);
    /**
     * 查询月邀请收入
     * @param param
     * @return
     */
    BigDecimal queryMonthInviteCash(@Param("param") UserRecommenderIncomeLogEntity param);
    /**
     * 查询总邀请收入
     * @param param
     * @return
     */
    BigDecimal queryTotalInviteCash(@Param("param") UserRecommenderIncomeLogEntity param);

    /**
     * 查询个人总收益
     * @param userRecommenderIncomeLogEntity
     * @return
     */
    BigDecimal querySelfCashTotal(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    /**
     * 查询个人今日收益
     * @param userRecommenderIncomeLogEntity
     * @return
     */
    BigDecimal querySelfCashToday(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    List<UserRecommenderIncomeLogEntity> querySettelList(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    BigDecimal queryByTimeCash(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    Integer queryByTimeCount(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    Integer queryMechanismListCount(@Param("param") UserRecommenderIncomeLogEntity param);

    List<UserRecommenderIncomeLogEntity> queryMechanismIdList(@Param("param") UserRecommenderIncomeLogEntity param);

    Integer queryMechanismIdListCount(@Param("param") UserRecommenderIncomeLogEntity param);

    BigDecimal queryWeekCash(@Param("param") UserRecommenderIncomeLogEntity param);

    List<UserRecommenderIncomeLogEntity> queryMoreThanWeek(@Param("param") UserRecommenderIncomeLogEntity param);

    ArrayList<InviteRankEntity> queryRanking(@Param("param") UserRecommenderIncomeLogEntity param);

    List<UserRecommenderIncomeLogEntity> queryMoreThanMinute(@Param("param") UserRecommenderIncomeLogEntity param);

    BigDecimal queryMonthSubCash(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    BigDecimal queryMonthAddCash(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);

    List<UserRecommenderIncomeLogEntity> queryCanSettel(@Param("param") UserRecommenderIncomeLogEntity param);

    int updateSettleTrue(@Param("param") UserRecommenderIncomeLogEntity userRecommenderIncomeLogEntity);
}