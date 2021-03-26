package com.huihe.eg.authorization.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.authorization.model.ApiUrlEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerUserMapper extends BaseFrameworkMapper<ManagerUserEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(ManagerUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(ManagerUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    ManagerUserEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(ManagerUserEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(ManagerUserEntity record);

    Integer queryIdenetity(@Param("param") ApiUrlEntity entity);

    Long queryManagerId(@Param("type") String type);

    int updateStudentCount(@Param("id") Long id);
}