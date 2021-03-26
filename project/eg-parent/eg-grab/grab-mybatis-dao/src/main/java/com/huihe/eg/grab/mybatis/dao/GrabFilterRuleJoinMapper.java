package com.huihe.eg.grab.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.grab.model.GrabFilterRuleJoinEntity;

public interface GrabFilterRuleJoinMapper extends BaseFrameworkMapper<GrabFilterRuleJoinEntity, Long> {
    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insert(GrabFilterRuleJoinEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int insertSelective(GrabFilterRuleJoinEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    GrabFilterRuleJoinEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKeySelective(GrabFilterRuleJoinEntity record);

    /**
     *
     * @mbggenerated 2019-02-15
     */
    @Override
    int updateByPrimaryKey(GrabFilterRuleJoinEntity record);
}