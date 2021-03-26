package com.huihe.eg.mall.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.mall.model.AuctionCommondityEntity;

public interface AuctionCommondityMapper extends BaseFrameworkMapper<AuctionCommondityEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-02
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insert(AuctionCommondityEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insertSelective(AuctionCommondityEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    AuctionCommondityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKeySelective(AuctionCommondityEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKey(AuctionCommondityEntity record);
}