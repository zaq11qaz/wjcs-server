package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.RechargeRecordRobotLogEntity;

public interface RechargeRecordRobotLogMapper extends BaseFrameworkMapper<RechargeRecordRobotLogEntity, Integer> {
    /**
     *
     * @mbggenerated 2021-05-15
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2021-05-15
     */
    int insert(RechargeRecordRobotLogEntity record);

    /**
     *
     * @mbggenerated 2021-05-15
     */
    int insertSelective(RechargeRecordRobotLogEntity record);

    /**
     *
     * @mbggenerated 2021-05-15
     */
    RechargeRecordRobotLogEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2021-05-15
     */
    int updateByPrimaryKeySelective(RechargeRecordRobotLogEntity record);

    /**
     *
     * @mbggenerated 2021-05-15
     */
    int updateByPrimaryKey(RechargeRecordRobotLogEntity record);
}