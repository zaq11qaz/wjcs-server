package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.AuthorizationScreenWhiteEntity;

public interface AuthorizationScreenWhiteMapper extends BaseFrameworkMapper<AuthorizationScreenWhiteEntity, Long> {
    /**
     *
     * @mbggenerated 2021-05-06
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-06
     */
    int insert(AuthorizationScreenWhiteEntity record);

    /**
     *
     * @mbggenerated 2021-05-06
     */
    int insertSelective(AuthorizationScreenWhiteEntity record);

    /**
     *
     * @mbggenerated 2021-05-06
     */
    AuthorizationScreenWhiteEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-06
     */
    int updateByPrimaryKeySelective(AuthorizationScreenWhiteEntity record);

    /**
     *
     * @mbggenerated 2021-05-06
     */
    int updateByPrimaryKey(AuthorizationScreenWhiteEntity record);
}