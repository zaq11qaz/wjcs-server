package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageContentEntity;

public interface MessageContentMapper extends BaseFrameworkMapper<MessageContentEntity, Integer> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(MessageContentEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageContentEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageContentEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageContentEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageContentEntity record);
}