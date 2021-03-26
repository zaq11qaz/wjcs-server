package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.GroupVideoEntity;

public interface GroupVideoMapper extends BaseFrameworkMapper<GroupVideoEntity, Long> {
    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int insert(GroupVideoEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int insertSelective(GroupVideoEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    GroupVideoEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int updateByPrimaryKeySelective(GroupVideoEntity record);

    /**
     *
     * @mbggenerated 2020-04-11
     */
    @Override
    int updateByPrimaryKey(GroupVideoEntity record);
}