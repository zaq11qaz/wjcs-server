package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageUserEntity;

public interface MessageUserMapper extends BaseFrameworkMapper<MessageUserEntity, Long> {
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
    int insert(MessageUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageUserEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageUserEntity record);
}