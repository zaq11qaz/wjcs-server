package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserPointsEntity;

public interface UserPointsMapper extends BaseFrameworkMapper<UserPointsEntity, Integer> {
    /**
     *
     * @mbggenerated 2020-12-31
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-12-31
     */
    int insert(UserPointsEntity record);

    /**
     *
     * @mbggenerated 2020-12-31
     */
    int insertSelective(UserPointsEntity record);

    /**
     *
     * @mbggenerated 2020-12-31
     */
    UserPointsEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-12-31
     */
    int updateByPrimaryKeySelective(UserPointsEntity record);

    /**
     *
     * @mbggenerated 2020-12-31
     */
    int updateByPrimaryKey(UserPointsEntity record);
}