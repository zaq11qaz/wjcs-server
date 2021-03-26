package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.SystemVersionEntity;

public interface SystemVersionMapper extends BaseFrameworkMapper<SystemVersionEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    int insert(SystemVersionEntity record);

    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    int insertSelective(SystemVersionEntity record);

    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    SystemVersionEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    int updateByPrimaryKeySelective(SystemVersionEntity record);

    /**
     *
     * @mbggenerated 2019-06-03
     */
    @Override
    int updateByPrimaryKey(SystemVersionEntity record);
}