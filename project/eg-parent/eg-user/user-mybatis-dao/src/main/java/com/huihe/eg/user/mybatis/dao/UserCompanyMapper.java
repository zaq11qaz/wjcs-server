package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserCompanyEntity;

public interface UserCompanyMapper extends BaseFrameworkMapper<UserCompanyEntity, Long> {
    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insert(UserCompanyEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int insertSelective(UserCompanyEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    UserCompanyEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKeySelective(UserCompanyEntity record);

    /**
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKey(UserCompanyEntity record);
}