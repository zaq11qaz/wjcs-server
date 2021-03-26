package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.CommodityCouponEntity;

public interface CommodityCouponMapper extends BaseFrameworkMapper<CommodityCouponEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int insert(CommodityCouponEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int insertSelective(CommodityCouponEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    CommodityCouponEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int updateByPrimaryKeySelective(CommodityCouponEntity record);

    /**
     *
     * @mbggenerated 2019-06-10
     */
    @Override
    int updateByPrimaryKey(CommodityCouponEntity record);
}