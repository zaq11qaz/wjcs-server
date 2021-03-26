package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserChattingEntity;

public interface UserChattingMapper extends BaseFrameworkMapper<UserChattingEntity, Long> {
    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    int insert(UserChattingEntity record);

    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    int insertSelective(UserChattingEntity record);

    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    UserChattingEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    int updateByPrimaryKeySelective(UserChattingEntity record);

    /**
     *
     * @mbggenerated 2019-04-17
     */
    @Override
    int updateByPrimaryKey(UserChattingEntity record);
}