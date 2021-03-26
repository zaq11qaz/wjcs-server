package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MechanismClassesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MechanismClassesMapper extends BaseFrameworkMapper<MechanismClassesEntity, Long> {
    /**
     *
     * @mbggenerated 2021-01-19
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-19
     */
    int insert(MechanismClassesEntity record);

    /**
     *
     * @mbggenerated 2021-01-19
     */
    int insertSelective(MechanismClassesEntity record);

    /**
     *
     * @mbggenerated 2021-01-19
     */
    MechanismClassesEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-19
     */
    int updateByPrimaryKeySelective(MechanismClassesEntity record);

    /**
     *
     * @mbggenerated 2021-01-19
     */
    int updateByPrimaryKey(MechanismClassesEntity record);

    List<MechanismClassesEntity> queryListPageAsc(@Param("param") MechanismClassesEntity mechanismClassesEntity);

    int updateStudentCount(@Param("param") MechanismClassesEntity mechanismClassesEntity);

    int updateIsAllTrue(@Param("param") MechanismClassesEntity masterMechanismEntity);

    int updateMergerClass(@Param("param") MechanismClassesEntity param);
}