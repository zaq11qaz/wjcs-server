package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.TimeZoneEntity;

public interface TimeZoneMapper extends BaseFrameworkMapper<TimeZoneEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    int insert(TimeZoneEntity record);

    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    int insertSelective(TimeZoneEntity record);

    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    TimeZoneEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    int updateByPrimaryKeySelective(TimeZoneEntity record);

    /**
     *
     * @mbggenerated 2019-06-25
     */
    @Override
    int updateByPrimaryKey(TimeZoneEntity record);
}