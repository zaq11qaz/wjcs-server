package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.SettledAnchorEntity;

public interface SettledAnchorMapper extends BaseFrameworkMapper<SettledAnchorEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    int insert(SettledAnchorEntity record);

    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    int insertSelective(SettledAnchorEntity record);

    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    SettledAnchorEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    int updateByPrimaryKeySelective(SettledAnchorEntity record);

    /**
     *
     * @mbggenerated 2019-09-18
     */
    @Override
    int updateByPrimaryKey(SettledAnchorEntity record);
}