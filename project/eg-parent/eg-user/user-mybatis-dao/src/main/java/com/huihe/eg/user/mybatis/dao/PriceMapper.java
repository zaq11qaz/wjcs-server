package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.PriceEntity;

public interface PriceMapper extends BaseFrameworkMapper<PriceEntity, Long> {
    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    int insert(PriceEntity record);

    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    int insertSelective(PriceEntity record);

    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    PriceEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    int updateByPrimaryKeySelective(PriceEntity record);

    /**
     *
     * @mbggenerated 2020-08-05
     */
    @Override
    int updateByPrimaryKey(PriceEntity record);
}