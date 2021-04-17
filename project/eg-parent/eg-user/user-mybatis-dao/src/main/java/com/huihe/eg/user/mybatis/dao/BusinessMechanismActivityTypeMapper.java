package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.BusinessMechanismActivityTypeEntity;

public interface BusinessMechanismActivityTypeMapper extends BaseFrameworkMapper<BusinessMechanismActivityTypeEntity, Long> {
    /**
     *
     * @mbggenerated 2021-04-15
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-04-15
     */
    int insert(BusinessMechanismActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-04-15
     */
    int insertSelective(BusinessMechanismActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-04-15
     */
    BusinessMechanismActivityTypeEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-04-15
     */
    int updateByPrimaryKeySelective(BusinessMechanismActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-04-15
     */
    int updateByPrimaryKey(BusinessMechanismActivityTypeEntity record);

    BusinessMechanismActivityTypeEntity selectByActivityId(Long id);
}