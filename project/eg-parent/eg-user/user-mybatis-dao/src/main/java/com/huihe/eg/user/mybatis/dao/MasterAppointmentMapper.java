package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterAppointmentEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MasterAppointmentMapper extends BaseFrameworkMapper<MasterAppointmentEntity, Long> {
    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    int insert(MasterAppointmentEntity record);

    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    int insertSelective(MasterAppointmentEntity record);

    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    MasterAppointmentEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    int updateByPrimaryKeySelective(MasterAppointmentEntity record);

    /**
     *
     * @mbggenerated 2019-12-13
     */
    @Override
    int updateByPrimaryKey(MasterAppointmentEntity record);

    /**
     * 查询课程表
     * 2019年6月17日20:10:08
     * zwx
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> querySchedule(@Param("param")MasterAppointmentEntity entity);

    /**
     * 查询当天的课程安排
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> queryDaySchedule(@Param("param")MasterAppointmentEntity entity);

    /**
     * 根据课程时间查询老师
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> queryAppointmentListPage(@Param("param")MasterAppointmentEntity entity);

    /**
     * 修改查询助学师课程
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> queryAppointmentUpdate(@Param("param")MasterAppointmentEntity entity);

    /**
     * 查询是否有公开直播课程
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> queryClassListPage(@Param("param")MasterAppointmentEntity entity);

    Integer queryCourseCount(@Param("param") MasterAppointmentEntity record);
    Integer querySurplusCount(@Param("param") MasterAppointmentEntity record);
    List<MasterAppointmentEntity> queryLatelyCourse(@Param("param") MasterAppointmentEntity record);

    /**
     * 查询快要结束课程
     * @return
     */
    List<MasterAppointmentEntity> queryExpireClass();

    /**
     * 查询已结束10分钟的课程
     * @return
     */
    List<MasterAppointmentEntity> queryStayComment();

    /**
     * 课程开始前推送
     * @return
     */
    List<MasterAppointmentEntity> queryClassStartPush();

    /**
     * 查询历史课程
     * @param record
     * @return
     */
    List<MasterAppointmentEntity> queryHistoryListPage(@Param("param") MasterAppointmentEntity record);

    /**
     * 查询正在开播和待开播课程
     * @param record
     * @return
     */
    List<MasterAppointmentEntity> queryBeginListPage(@Param("param") MasterAppointmentEntity record);

    /**
     * 查询首页课堂
     * 2019年6月17日20:10:08
     * zwx
     * @param entity
     * @return
     */
    List<MasterAppointmentEntity> queryHomeListPage(@Param("param")MasterAppointmentEntity entity);
    /**
     * 查询正在开播和待开播课程数量
     * @param record
     * @return
     */
    Integer queryBeginListPageCount(@Param("param") MasterAppointmentEntity record);

    /**
     * 查询历史播课程数量
     * @param record
     * @return
     */
    Integer queryHistoryListPageCount(@Param("param") MasterAppointmentEntity record);

    /**
     * 查询机构课程
     * @param record
     * @return
     */
    List<MasterAppointmentEntity> queryMechanismAppointmentListPage(@Param("param") MasterAppointmentEntity record);


    /**
     * 日总数
     * @param entity
     * @return
     */
    Integer queryDayCount(@Param("param")MasterAppointmentEntity entity);

    /**
     * 周总数
     * @param entity
     * @return
     */
    Integer queryWeekCount(@Param("param")MasterAppointmentEntity entity);

    /**
     * 月总数
     * @param entity
     * @return
     */
    Integer queryMonthCount(@Param("param")MasterAppointmentEntity entity);

    /**
     * 总数
     * @param entity
     * @return
     */
    Integer queryCountTotal(@Param("param") MasterAppointmentEntity entity);

    /**
     * 根据时间查询各语言人数
     * @param param
     * @return
     */
    Integer queryMasterAppointmentCount(@Param("param")MasterAppointmentEntity param);

    /**
     * 即将开课
     * @param param
     * @return
     */
    Integer querySoonCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 未上课
     * @param param
     * @return
     */
    Integer queryEndClassCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 条件查询
     * @param param
     * @return
     */
    List<MasterAppointmentEntity> queryByMessage(@Param("param") MasterAppointmentEntity param);

    Integer updateIsFirstpageShow(@Param("id") long id, @Param("is") boolean getIs_firstpage_show);

    /** 查询机构课程统计
     * @param masterAppointmentEntity
     * @return
     */
    Integer queryMechanismAppointmentCount(@Param("param") MasterAppointmentEntity masterAppointmentEntity);

    /**
     * 条件查询总数
     * @param param
     * @return
     */
    Integer queryByMessageCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 结算课程
     * @param param
     * @return
     */
    List<MasterAppointmentEntity> querySettlementList(@Param("param") MasterAppointmentEntity param);

    /**
     * @param param
     * @return
     */
    Integer updateByPrimaryKeySettlement(@Param("param") MasterAppointmentEntity param);

    /**
     * 查询即将开播课程
     * @param param
     * @return
     */
    List<MasterAppointmentEntity> querySoonClass(@Param("param") MasterAppointmentEntity param);

    /**
     * 即将开播课程
     * @param param
     * @return
     */
    Integer querySoonClassCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 已开播课程
     * @param param
     * @return
     */
    List<MasterAppointmentEntity> queryBeginList(@Param("param") MasterAppointmentEntity param);

    /**
     * 已开播课程统计
     * @param param
     * @return
     */
    Integer queryBeginListCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 通过时间查询课程统计
     * @param param
     * @return
     */
    Integer queryAppointmentCountByTime(@Param("param") MasterAppointmentEntity param);

    /**
     * @param param
     * @return
     */
    Integer querySettlementListCount(@Param("param") MasterAppointmentEntity param);

    /**
     * 更新课程收益
     * @param param
     * @return
     */
    Integer updateProfit(@Param("param") MasterAppointmentEntity param);

    Integer queryMechanismAppointmentListPageCount(@Param("param") MasterAppointmentEntity param);

    Integer updateLikeCount(@Param("id") Long id);
    Integer updateLikeCountCancel(@Param("id") Long id);
    Integer updateShareCount(@Param("id") Long id);
    Integer updateCommentCount(@Param("id") Long id);

    Integer queryHomeListPageBeginCount(@Param("param") MasterAppointmentEntity param);

    Integer queryHomeListPageEndCount(@Param("param") MasterAppointmentEntity param);

    Integer queryHomeListPageNearCount(@Param("param") MasterAppointmentEntity param);

    Integer queryHomeListPageNearCountTotal(@Param("param") MasterAppointmentEntity param);

    Integer queryDayCountAfterNow(@Param("param") MasterAppointmentEntity masterAppointmentEntity);

    List<MasterAppointmentEntity> queryScheduleListPage(@Param("param") MasterAppointmentEntity param);

    List<MasterAppointmentEntity> queryMechanismSchedule(@Param("param") MasterAppointmentEntity param);

    BigDecimal queryEarnTotal(@Param("param") MasterAppointmentEntity param);

    BigDecimal querydayEarn(@Param("param") MasterAppointmentEntity param);

    List<MasterAppointmentEntity> queryEarnList(@Param("param") MasterAppointmentEntity param);

    List<MasterAppointmentEntity> queryEndCourse(@Param("param") MasterAppointmentEntity param);

    List<MasterAppointmentEntity> queryMechanismScheduleListPage(@Param("param")MasterAppointmentEntity param);

    Integer queryMechanismScheduleListPageCount(@Param("param")MasterAppointmentEntity param);

    List<MasterAppointmentEntity> querySoonMechanismCourse(@Param("param") MasterAppointmentEntity masterAppointmentEntity);

    List<String> queryStartTimeIn(@Param("param")MasterAppointmentEntity masterAppointmentEntity);

    List<MasterAppointmentEntity> queryAsc(@Param("param") MasterAppointmentEntity masterAppointmentEntity2);

    List<MasterAppointmentEntity> queryListPageAsc(@Param("param")MasterAppointmentEntity masterAppointmentEntity);

    Integer querylistpageNotIn1(@Param("param")MasterAppointmentEntity masterAppointmentEntity);

    int deleteByMechanismClassId(@Param("id") long id);
}