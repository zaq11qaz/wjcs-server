package com.huihe.eg.mall.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.mall.model.AuctionRecordEntity;

public interface AuctionRecordMapper extends BaseFrameworkMapper<AuctionRecordEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-02
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insert(AuctionRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insertSelective(AuctionRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    AuctionRecordEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKeySelective(AuctionRecordEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKey(AuctionRecordEntity record);
}