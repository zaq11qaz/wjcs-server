package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.SystemRechargeEntity;
import org.apache.ibatis.annotations.Param;

public interface SystemRechargeMapper extends BaseFrameworkMapper<SystemRechargeEntity, Long> {
    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    int insert(SystemRechargeEntity record);

    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    int insertSelective(SystemRechargeEntity record);

    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    SystemRechargeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    int updateByPrimaryKeySelective(SystemRechargeEntity record);

    /**
     *
     * @mbggenerated 2020-04-29
     */
    @Override
    int updateByPrimaryKey(SystemRechargeEntity record);

    //日收入
    Integer queryDayCount(@Param("param") SystemRechargeEntity entity);

    //周收入
    Integer queryWeekCount(@Param("param") SystemRechargeEntity entity);

    //月收入
    Integer queryMonthCount(@Param("param")SystemRechargeEntity entity);

    //总收入
    Integer queryPayCount(@Param("param")SystemRechargeEntity entity);

}