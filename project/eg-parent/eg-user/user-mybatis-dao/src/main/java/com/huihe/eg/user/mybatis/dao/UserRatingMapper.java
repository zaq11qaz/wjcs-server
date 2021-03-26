package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserRatingEntity;

public interface UserRatingMapper extends BaseFrameworkMapper<UserRatingEntity, Long> {
    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    int insert(UserRatingEntity record);

    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    int insertSelective(UserRatingEntity record);

    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    UserRatingEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    int updateByPrimaryKeySelective(UserRatingEntity record);

    /**
     *
     * @mbggenerated 2019-05-30
     */
    @Override
    int updateByPrimaryKey(UserRatingEntity record);
}