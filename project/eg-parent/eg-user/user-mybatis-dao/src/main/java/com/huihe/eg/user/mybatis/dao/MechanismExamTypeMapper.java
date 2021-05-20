package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MechanismExamTypeEntity;

public interface MechanismExamTypeMapper extends BaseFrameworkMapper<MechanismExamTypeEntity, Long> {
    /**
     *
     * @mbggenerated 2021-05-20
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-20
     */
    int insert(MechanismExamTypeEntity record);

    /**
     *
     * @mbggenerated 2021-05-20
     */
    int insertSelective(MechanismExamTypeEntity record);

    /**
     *
     * @mbggenerated 2021-05-20
     */
    MechanismExamTypeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-05-20
     */
    int updateByPrimaryKeySelective(MechanismExamTypeEntity record);

    /**
     *
     * @mbggenerated 2021-05-20
     */
    int updateByPrimaryKey(MechanismExamTypeEntity record);
}