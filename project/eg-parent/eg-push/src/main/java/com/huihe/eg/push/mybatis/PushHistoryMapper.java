package com.huihe.eg.push.mybatis;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.push.model.PushHistoryEntity;

public interface PushHistoryMapper extends BaseFrameworkMapper<PushHistoryEntity, Long> {
    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    int insert(PushHistoryEntity record);

    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    int insertSelective(PushHistoryEntity record);

    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    PushHistoryEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    int updateByPrimaryKeySelective(PushHistoryEntity record);

    /**
     *
     * @mbggenerated 2020-07-18
     */
    @Override
    int updateByPrimaryKey(PushHistoryEntity record);
}