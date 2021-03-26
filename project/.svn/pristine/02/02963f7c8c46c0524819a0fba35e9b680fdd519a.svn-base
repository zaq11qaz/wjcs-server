package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;

import com.huihe.eg.user.model.UserClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserClassMapper extends BaseFrameworkMapper<UserClassEntity, Long> {
    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    int insert(UserClassEntity record);

    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    int insertSelective(UserClassEntity record);

    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    UserClassEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    int updateByPrimaryKeySelective(UserClassEntity record);

    /**
     *
     * @mbggenerated 2019-11-26
     */
    @Override
    int updateByPrimaryKey(UserClassEntity record);
    List<UserClassEntity> queryUserSchedule(@Param("param") UserClassEntity record);
    List<UserClassEntity> queryIsClass(@Param("param") UserClassEntity record);
    /**
     * 查询历史订阅
     * 2020年5月14日16:16:35
     * zwx
     * @param entity
     * @return
     */
    List<UserClassEntity> queryHistoryListPage(@Param("param")UserClassEntity entity);

    Integer queryUserScheduleCount(@Param("param") UserClassEntity param);

    List<UserClassEntity> queryUserScheduleHistory(@Param("param") UserClassEntity param);

    Integer queryUserScheduleHistoryCount(@Param("param") UserClassEntity param);
}