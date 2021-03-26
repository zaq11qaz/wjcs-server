package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserLookEntity;

public interface UserLookMapper extends BaseFrameworkMapper<UserLookEntity, Long> {
    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    int insert(UserLookEntity record);

    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    int insertSelective(UserLookEntity record);

    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    UserLookEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    int updateByPrimaryKeySelective(UserLookEntity record);

    /**
     *
     * @mbggenerated 2019-04-24
     */
    @Override
    int updateByPrimaryKey(UserLookEntity record);
}