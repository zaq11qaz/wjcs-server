package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserMemberCardEntity;

public interface UserMemberCardMapper extends BaseFrameworkMapper<UserMemberCardEntity, Long> {
    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    int insert(UserMemberCardEntity record);

    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    int insertSelective(UserMemberCardEntity record);

    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    UserMemberCardEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    int updateByPrimaryKeySelective(UserMemberCardEntity record);

    /**
     *
     * @mbggenerated 2019-04-26
     */
    @Override
    int updateByPrimaryKey(UserMemberCardEntity record);
}