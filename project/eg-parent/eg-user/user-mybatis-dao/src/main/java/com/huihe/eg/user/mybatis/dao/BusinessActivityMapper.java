package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.BusinessActivityEntity;
import com.huihe.eg.user.model.MechanismActivityCourseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessActivityMapper extends BaseFrameworkMapper<BusinessActivityEntity, Long> {
    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int insert(BusinessActivityEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int insertSelective(BusinessActivityEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    BusinessActivityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKeySelective(BusinessActivityEntity record);

    /**
     *
     * @mbggenerated 2019-06-12
     */
    @Override
    int updateByPrimaryKey(BusinessActivityEntity record);

    List<BusinessActivityEntity> queryIn12(@Param("param") BusinessActivityEntity businessActivityEntity);

    List<MechanismActivityCourseEntity> queryActivityList(@Param("param") BusinessActivityEntity businessActivityEntity);

    int updateByActivityId(@Param("param") BusinessActivityEntity businessActivityEntity);
}