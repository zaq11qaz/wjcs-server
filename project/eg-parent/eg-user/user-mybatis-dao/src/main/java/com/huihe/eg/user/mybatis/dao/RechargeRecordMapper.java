package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterMechanismEntity;
import com.huihe.eg.user.model.PayUserInfoEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import com.huihe.eg.user.model.count.AssetsParam;
import com.huihe.eg.user.model.count.ChartParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RechargeRecordMapper extends BaseFrameworkMapper<RechargeRecordEntity, Long> {
    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    int insert(RechargeRecordEntity record);

    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    int insertSelective(RechargeRecordEntity record);

    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    RechargeRecordEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    int updateByPrimaryKeySelective(RechargeRecordEntity record);

    /**
     * @mbggenerated 2019-01-26
     */
    @Override
    int updateByPrimaryKey(RechargeRecordEntity record);

    //学习卡资产折线图数据
    List<ChartParam> studyCardStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //学习卡总累计资产
    List<AssetsParam> studyCardTotalProportionStatistics();

    //学习卡今日累计资产
    List<AssetsParam> studyCardTodayProportionStatistics();

    //学习卡次数统计
    List<AssetsParam> studyCardProportionStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //会员资产折线图数据
    List<ChartParam> memberAssetsStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //会员次数统计
    List<AssetsParam> memberProportionStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //会员总累计资产
    List<AssetsParam> memberTotalProportionStatistics();

    //会员今日累计资产
    List<AssetsParam> memberTodayProportionStatistics();

    //猫币资产折线图数据
    ChartParam accountAssetsStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //猫币总累计资产
    AssetsParam assetsTotalProportionStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //猫币今日累计资产
    AssetsParam assetsTodayProportionStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //总计资产折线图数据
    List<ChartParam> totalAssetsStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //总计次数统计
    List<AssetsParam> totalProportionStatistics(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    //总计总累计资产
    List<AssetsParam> totalTotalProportionStatistics();

    //总计今日累计资产
    List<AssetsParam> totalTodayProportionStatistics();

    //当日充值
    BigDecimal queryDayTotal(@Param("param") RechargeRecordEntity entity);

    //周充值
    BigDecimal queryWeekTotal(@Param("param") RechargeRecordEntity entity);

    //月充值
    BigDecimal queryMonthTotal(@Param("param") RechargeRecordEntity entity);

    //总充值
    BigDecimal queryPayTotal(@Param("param") RechargeRecordEntity entity);

    //日充值统计
    Integer queryDayCount(@Param("param") RechargeRecordEntity entity);

    //周充值统计
    Integer queryWeekCount(@Param("param") RechargeRecordEntity entity);

    //月充值统计
    Integer queryMonthCount(@Param("param") RechargeRecordEntity entity);

    //总充值统计
    Integer queryPayCount(@Param("param") RechargeRecordEntity entity);

    List<RechargeRecordEntity> queryDayList(@Param("param") RechargeRecordEntity entity);


    BigDecimal queryMechanismMounthTotal(@Param("id") Long id);

    List<RechargeRecordEntity> queryOrderList(@Param("param") RechargeRecordEntity entity);

    BigDecimal queryThisDay(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    List<RechargeRecordEntity> querySaleCourse(@Param("param") RechargeRecordEntity param);

    BigDecimal queryDayCash(@Param("param")RechargeRecordEntity param);

    BigDecimal queryMonthCash(@Param("param")RechargeRecordEntity param);

    BigDecimal queryTotalCash(@Param("param")RechargeRecordEntity param);

    List<RechargeRecordEntity> queryCardList(@Param("param") RechargeRecordEntity param);

    Integer queryCardListCount(@Param("param") RechargeRecordEntity param);

    Integer queryCardDayCount(@Param("param") RechargeRecordEntity param);

    Integer queryCardWeekCount(@Param("param") RechargeRecordEntity param);

    Integer queryCardMonthCount(@Param("param") RechargeRecordEntity param);

    List<RechargeRecordEntity> queryCardPayListByNickName(@Param("param") RechargeRecordEntity param);

    Integer queryCardPayListByNickNameCount(@Param("param") RechargeRecordEntity param);

    BigDecimal querySaleCardCash(@Param("id") Long id);

    BigDecimal querySaleCourseCash(@Param("id") Long id);

    List<RechargeRecordEntity> queryIfByCard(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    Integer queryOrderListCount(@Param("param") RechargeRecordEntity param);

    List<RechargeRecordEntity> queryTimeOUtLIst(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    List<RechargeRecordEntity> queryMoreThan1(@Param("param") RechargeRecordEntity entity);

    List<RechargeRecordEntity> queryListPageByStatus(@Param("param") RechargeRecordEntity entity);

    int updateNumberOfLesson(@Param("number_of_lessons") Long number_of_lessons, @Param("id") Long id);

    List<RechargeRecordEntity> queryNeedSettlementList(@Param("param") RechargeRecordEntity entity);

    int updateSettlement (@Param("id") Long id);

    List<PayUserInfoEntity> queryPayUserList(@Param("param") RechargeRecordEntity param);

    int updateRchStatus(@Param("param") RechargeRecordEntity rechargeRecordEntity);

    Long insertRobot( RechargeRecordEntity entity);

    Long selectLastInsertId();
}