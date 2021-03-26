package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.ChannelListEntity;

public interface ChannelListMapper extends BaseFrameworkMapper<ChannelListEntity, Integer> {
    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    int insert(ChannelListEntity record);

    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    int insertSelective(ChannelListEntity record);

    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    ChannelListEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    int updateByPrimaryKeySelective(ChannelListEntity record);

    /**
     *
     * @mbggenerated 2020-09-02
     */
    @Override
    int updateByPrimaryKey(ChannelListEntity record);
}