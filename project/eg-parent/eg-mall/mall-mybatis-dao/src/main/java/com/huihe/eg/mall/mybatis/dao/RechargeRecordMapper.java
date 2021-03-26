package com.huihe.eg.mall.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.mall.model.RechargeRecordEntity;

public interface RechargeRecordMapper extends BaseFrameworkMapper<RechargeRecordEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-02
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insert(RechargeRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insertSelective(RechargeRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    RechargeRecordEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKeySelective(RechargeRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKey(RechargeRecordEntity record);
}