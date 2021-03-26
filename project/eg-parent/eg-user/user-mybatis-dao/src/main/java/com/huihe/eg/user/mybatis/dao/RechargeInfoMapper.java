package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.RechargeInfoEntity;

public interface RechargeInfoMapper extends BaseFrameworkMapper<RechargeInfoEntity, Long> {
    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    int insert(RechargeInfoEntity record);

    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    int insertSelective(RechargeInfoEntity record);

    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    RechargeInfoEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    int updateByPrimaryKeySelective(RechargeInfoEntity record);

    /**
     *
     * @mbggenerated 2020-09-29
     */
    @Override
    int updateByPrimaryKey(RechargeInfoEntity record);
}