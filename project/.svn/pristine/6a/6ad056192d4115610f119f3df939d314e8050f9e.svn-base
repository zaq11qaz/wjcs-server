package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserAppointmentEntity;
import com.huihe.eg.user.model.count.AppointmentParam;
import com.huihe.eg.user.model.count.ChartParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAppointmentMapper extends BaseFrameworkMapper<UserAppointmentEntity, Long> {
    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    int insert(UserAppointmentEntity record);

    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    int insertSelective(UserAppointmentEntity record);

    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    UserAppointmentEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    int updateByPrimaryKeySelective(UserAppointmentEntity record);

    /**
     * @mbggenerated 2019-06-14
     */
    @Override
    int updateByPrimaryKey(UserAppointmentEntity record);

    List<UserAppointmentEntity> queryClass(@Param("param") UserAppointmentEntity record);
    //查询某个时间段是否需要上课
    List<UserAppointmentEntity> queryIsAppointmentClass(@Param("param") UserAppointmentEntity record);

    Integer queryStudentCount(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryIsAppointment(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryTeacherListPage(@Param("param") UserAppointmentEntity record);

    Integer queryCourseCount(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryLatelyCourse(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryStudentListPage(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryUserSchedule(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> querySoonClass(@Param("param") UserAppointmentEntity record);

    Integer queryTypeCount(@Param("param") UserAppointmentEntity record);

    List<UserAppointmentEntity> queryEarningsListPage(@Param("param") UserAppointmentEntity record);

    //累计收益
    Double queryCumulativeEarnings(@Param("param") UserAppointmentEntity record);

    Integer queryNotEarnings(@Param("param") UserAppointmentEntity record);

    //查询老师课程表预约信息
    List<UserAppointmentEntity> queryAppointmentListPage(@Param("param") UserAppointmentEntity record);

    /**
     * 后台 统计图统计
     *
     * @return
     */
    ChartParam queryEarningsStatistics(@Param("param") UserAppointmentEntity record);
    List<AppointmentParam> queryAppointmentTimeStatistics(@Param("param") UserAppointmentEntity record);
    /**
     * 查询历史课堂
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    List<UserAppointmentEntity> queryHistoryListPage(@Param("param")UserAppointmentEntity entity);
    /**
     * 查询历史课堂数量
     * 2020年6月24日21:38:34
     * zwx
     * @param entity
     * @return
     */
    Integer queryHistoryListPageCount(@Param("param")UserAppointmentEntity entity);
    /**
     * 今日累计收益
     * 2020年6月24日21:59:22
     * zwx
     * @param entity
     * @return
     */
    Integer queryDayCumulativeEarnings(@Param("param")UserAppointmentEntity entity);
    /**
     * 今日待上课数
     * 2020年6月24日22:08:42
     * zwx
     * @param entity
     * @returnqueryDayBeginCount
     */
    Integer queryDayBeginCount(@Param("param")UserAppointmentEntity entity);
    /**
     * 今日上课数
     * @author : zwy
     * 2020-08-10 01:47
     * @since JDK1.8
     */
    Integer queryDayCount(@Param("param")UserAppointmentEntity entity);
    /**
     * 本周
     * @author : zwy
     * 2020-08-10 01:47
     * @since JDK1.8
     */
    Integer queryWeekCount(@Param("param")UserAppointmentEntity entity);

    /**
     * 本月
     * @author : zwy
     * 2020-08-10 01:47
     * @since JDK1.8
     */
    Integer queryMonthCount(@Param("param")UserAppointmentEntity entity);

    /**
     * 待上课数
     * 2020年6月24日22:13:22
     * zwx
     * @param entity
     * @returnqueryDayBeginCount
     */
    Integer queryBeginCount(@Param("param")UserAppointmentEntity entity);
    /**
     * 历史上课数
     * 2020年6月28日21:30:28
     * zwx
     * @param entity
     * @returnqueryDayBeginCount
     */
    Integer queryHistoryCount(@Param("param")UserAppointmentEntity entity);
    /**
     *  我的订单
     *  2020年7月2日14:21:34
     * zwx
     * @param entity
     * @return
     */
    List<UserAppointmentEntity> queryMyOrderListPage(@Param("param")UserAppointmentEntity entity);
    /**
     *  查询机构老师课程
     *  2020年7月8日20:51:03
     *  zwx
     * @param entity
     * @return
     */
    List<UserAppointmentEntity> queryMechanismAppointment(@Param("param")UserAppointmentEntity entity);
    /**
     * 查询助学师收益
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    List<UserAppointmentEntity> queryMasterEarningsListPage(@Param("param")UserAppointmentEntity entity);

    /**
     * 查询助学师历史课程
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    List<UserAppointmentEntity> queryMasterHistoryListPage(@Param("param")UserAppointmentEntity entity);

    /**
     * 查询最近课程
     * @param userAppointmentEntity
     * @return
     */
    UserAppointmentEntity queryNearCourse(@Param("param") UserAppointmentEntity userAppointmentEntity);

    List<UserAppointmentEntity> queryByMessage(@Param("param") UserAppointmentEntity param);

    Integer queryByMessageCount(@Param("param") UserAppointmentEntity param);

    Integer queryUserScheduleCount(@Param("param") UserAppointmentEntity param);

    List<UserAppointmentEntity> queryOfflineSchedule(@Param("param") UserAppointmentEntity param);

    List<UserAppointmentEntity> query2and3(@Param("param") UserAppointmentEntity userAppointmentEntity);

    List<UserAppointmentEntity> queryEndCourse(@Param("param") UserAppointmentEntity param);

    List<UserAppointmentEntity> queryListPage8or9(@Param("param") UserAppointmentEntity userAppointmentEntity);

    List<UserAppointmentEntity> querySoonMechanismCourse(@Param("param") UserAppointmentEntity userAppointmentEntity);

    List<UserAppointmentEntity> queryEndMechanismCourse(@Param("param")UserAppointmentEntity userAppointmentEntity);

    int updateByAppointMentId(@Param("param")UserAppointmentEntity userAppointmentEntity);

    List<UserAppointmentEntity> queryListPageAsc(@Param("param") UserAppointmentEntity userAppointmentEntity);

    int deleteByStudyCardId(@Param("id") Long id);
}