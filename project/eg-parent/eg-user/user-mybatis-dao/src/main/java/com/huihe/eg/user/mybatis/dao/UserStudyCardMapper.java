package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserStudyCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStudyCardMapper extends BaseFrameworkMapper<UserStudyCardEntity, Long> {
    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int insert(UserStudyCardEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int insertSelective(UserStudyCardEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    UserStudyCardEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKeySelective(UserStudyCardEntity record);

    /**
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKey(UserStudyCardEntity record);


    int updateCourseNum(@Param("param") UserStudyCardEntity record);

    int updateAddCourseNum(@Param("param") UserStudyCardEntity record);

    //日统计
    Integer queryHelperDayCount(@Param("param")UserStudyCardEntity entity);

    //周统计
    Integer queryWeekCount(@Param("param") UserStudyCardEntity entity);

    //月统计
    Integer queryMonthCount(@Param("param")UserStudyCardEntity param);

    //总统计
    Integer queryPayCount(@Param("param")UserStudyCardEntity param);

    //日统计
    Integer queryDayCountNum(@Param("param")UserStudyCardEntity entity);

    //周统计
    Integer queryWeekCountNum(@Param("param") UserStudyCardEntity entity);

    //月统计
    Integer queryMonthCountNum(@Param("param")UserStudyCardEntity param);

    //根据时间查询总数
    Integer queryListPageByTime(@Param("param")UserStudyCardEntity param);

    //根据时间查个数
    Integer queryCountByTime(@Param("param")UserStudyCardEntity param);

    //课程不等于0
    List<UserStudyCardEntity> queryListPageNotEqual0(@Param("param") UserStudyCardEntity param);

    List<UserStudyCardEntity> queryExclusiveCard(@Param("param") UserStudyCardEntity param);

    List<UserStudyCardEntity> queryByMessage(@Param("param") UserStudyCardEntity param);

    Integer queryByMessageCount(@Param("param") UserStudyCardEntity param);

    Integer queryExclusiveCardCount(@Param("param") UserStudyCardEntity param);

    List<UserStudyCardEntity> queryExclusiveCoursesList(@Param("param") UserStudyCardEntity param);

    List<UserStudyCardEntity> queryNeedSettle13(@Param("param") UserStudyCardEntity userStudyCardEntity);

    List<UserStudyCardEntity> queryNeedSettle23(@Param("param") UserStudyCardEntity userStudyCardEntity);

    List<UserStudyCardEntity> queryNeedSettleAll(@Param("param") UserStudyCardEntity userStudyCardEntity);

    List<UserStudyCardEntity> queryIn3Minute(@Param("param") UserStudyCardEntity userStudyCardEntity2);
}