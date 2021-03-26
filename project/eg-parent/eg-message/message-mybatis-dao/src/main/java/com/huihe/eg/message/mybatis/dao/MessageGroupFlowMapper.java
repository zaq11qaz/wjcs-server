package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageGroupFlowEntity;

public interface MessageGroupFlowMapper extends BaseFrameworkMapper<MessageGroupFlowEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    int insert(MessageGroupFlowEntity record);

    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    int insertSelective(MessageGroupFlowEntity record);

    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    MessageGroupFlowEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    int updateByPrimaryKeySelective(MessageGroupFlowEntity record);

    /**
     *
     * @mbggenerated 2019-09-04
     */
    @Override
    int updateByPrimaryKey(MessageGroupFlowEntity record);
}