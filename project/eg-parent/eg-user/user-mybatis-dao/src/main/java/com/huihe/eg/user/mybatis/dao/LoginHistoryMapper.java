package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.LoginHistoryEntity;

public interface LoginHistoryMapper extends BaseFrameworkMapper<LoginHistoryEntity, Long> {
    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    int insert(LoginHistoryEntity record);

    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    int insertSelective(LoginHistoryEntity record);

    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    LoginHistoryEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    int updateByPrimaryKeySelective(LoginHistoryEntity record);

    /**
     * @mbggenerated 2019-07-06
     */
    @Override
    int updateByPrimaryKey(LoginHistoryEntity record);
}