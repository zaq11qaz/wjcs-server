package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterTypeEntity;

public interface MasterTypeMapper extends BaseFrameworkMapper<MasterTypeEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int insert(MasterTypeEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int insertSelective(MasterTypeEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    MasterTypeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKeySelective(MasterTypeEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKey(MasterTypeEntity record);
}