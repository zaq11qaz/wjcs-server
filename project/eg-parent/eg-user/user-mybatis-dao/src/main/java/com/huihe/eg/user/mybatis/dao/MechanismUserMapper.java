package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MechanismUserEntity;
import org.apache.ibatis.annotations.Param;

public interface MechanismUserMapper extends BaseFrameworkMapper<MechanismUserEntity, Integer> {
    /**
     *
     * @mbggenerated 2020-12-21
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-12-21
     */
    int insert(MechanismUserEntity record);

    /**
     *
     * @mbggenerated 2020-12-21
     */
    int insertSelective(MechanismUserEntity record);

    /**
     *
     * @mbggenerated 2020-12-21
     */
    MechanismUserEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2020-12-21
     */
    int updateByPrimaryKeySelective(MechanismUserEntity record);

    /**
     *
     * @mbggenerated 2020-12-21
     */
    int updateByPrimaryKey(MechanismUserEntity record);

    int updateSubFreeCourse(@Param("param") MechanismUserEntity mechanismUserEntity);
}