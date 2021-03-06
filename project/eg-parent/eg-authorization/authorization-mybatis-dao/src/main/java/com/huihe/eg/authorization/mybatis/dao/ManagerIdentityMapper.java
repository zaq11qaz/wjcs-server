package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.ManagerIdentityEntity;
import org.apache.ibatis.annotations.Param;

public interface ManagerIdentityMapper extends BaseFrameworkMapper<ManagerIdentityEntity, Long> {
    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    int insert(ManagerIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    int insertSelective(ManagerIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    ManagerIdentityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    int updateByPrimaryKeySelective(ManagerIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-07-10
     */
    @Override
    int updateByPrimaryKey(ManagerIdentityEntity record);

    int updateSort(@Param("param") ManagerIdentityEntity param);

    int updatesortMiddle(@Param("param") ManagerIdentityEntity param);

    int selectMaxSort();
}