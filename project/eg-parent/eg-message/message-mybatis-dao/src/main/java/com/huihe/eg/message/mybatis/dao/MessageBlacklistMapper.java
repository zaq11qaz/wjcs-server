package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageBlacklistEntity;

public interface MessageBlacklistMapper extends BaseFrameworkMapper<MessageBlacklistEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(MessageBlacklistEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageBlacklistEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageBlacklistEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageBlacklistEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageBlacklistEntity record);
}