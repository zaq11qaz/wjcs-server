package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.GroupRecordingEntity;
import org.apache.ibatis.annotations.Param;

public interface GroupRecordingMapper extends BaseFrameworkMapper<GroupRecordingEntity, Long> {
    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int insert(GroupRecordingEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int insertSelective(GroupRecordingEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    GroupRecordingEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int updateByPrimaryKeySelective(GroupRecordingEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int updateByPrimaryKey(GroupRecordingEntity record);
    /**
     *
     * @mbggenerated 2020年5月23日18:37:54
     */
    int updateWatchCount(GroupRecordingEntity record);

    String queryByAppointmentId(@Param("appointment_id") Long appointment_id);
}