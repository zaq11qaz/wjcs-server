package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.OrderTypeLogoEntity;

public interface OrderTypeLogoMapper extends BaseFrameworkMapper<OrderTypeLogoEntity, Long> {
    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    int insert(OrderTypeLogoEntity record);

    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    int insertSelective(OrderTypeLogoEntity record);

    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    OrderTypeLogoEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    int updateByPrimaryKeySelective(OrderTypeLogoEntity record);

    /**
     *
     * @mbggenerated 2019-05-20
     */
    @Override
    int updateByPrimaryKey(OrderTypeLogoEntity record);
}