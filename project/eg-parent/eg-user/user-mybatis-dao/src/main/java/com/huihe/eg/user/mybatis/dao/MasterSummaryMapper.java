package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterSummaryEntity;

public interface MasterSummaryMapper extends BaseFrameworkMapper<MasterSummaryEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    int insert(MasterSummaryEntity record);

    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    int insertSelective(MasterSummaryEntity record);

    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    MasterSummaryEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKeySelective(MasterSummaryEntity record);

    /**
     *
     * @mbggenerated 2019-06-20
     */
    @Override
    int updateByPrimaryKey(MasterSummaryEntity record);
}