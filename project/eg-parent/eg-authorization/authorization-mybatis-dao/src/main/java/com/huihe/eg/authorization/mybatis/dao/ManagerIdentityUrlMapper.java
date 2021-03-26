package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.ManagerIdentityUrlEntity;

public interface ManagerIdentityUrlMapper extends BaseFrameworkMapper<ManagerIdentityUrlEntity, Long> {
    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    int insert(ManagerIdentityUrlEntity record);

    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    int insertSelective(ManagerIdentityUrlEntity record);

    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    ManagerIdentityUrlEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    int updateByPrimaryKeySelective(ManagerIdentityUrlEntity record);

    /**
     *
     * @mbggenerated 2020-08-07
     */
    @Override
    int updateByPrimaryKey(ManagerIdentityUrlEntity record);

    void deleteByIdentityId(Long id);
}