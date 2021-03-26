package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterScoreEntity;

public interface MasterScoreMapper extends BaseFrameworkMapper<MasterScoreEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    int insert(MasterScoreEntity record);

    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    int insertSelective(MasterScoreEntity record);

    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    MasterScoreEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    int updateByPrimaryKeySelective(MasterScoreEntity record);

    /**
     *
     * @mbggenerated 2019-06-15
     */
    @Override
    int updateByPrimaryKey(MasterScoreEntity record);
}