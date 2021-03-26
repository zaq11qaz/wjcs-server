package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.LogEntity;

public interface LogMapper extends BaseFrameworkMapper<LogEntity, Integer> {
    /**
     *
     * @mbggenerated 2020-10-19
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-10-19
     */
    int insert(LogEntity record);

    /**
     *
     * @mbggenerated 2020-10-19
     */
    int insertSelective(LogEntity record);

    /**
     *
     * @mbggenerated 2020-10-19
     */
    LogEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-10-19
     */
    int updateByPrimaryKeySelective(LogEntity record);

    /**
     *
     * @mbggenerated 2020-10-19
     */
    int updateByPrimaryKey(LogEntity record);
}