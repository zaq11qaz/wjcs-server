package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.ActivityInfoEntity;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessActivityTypeMapper extends BaseFrameworkMapper<BusinessActivityTypeEntity, Integer> {
    /**
     *
     * @mbggenerated 2021-01-27
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2021-01-27
     */
    int insert(BusinessActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-01-27
     */
    int insertSelective(BusinessActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-01-27
     */
    BusinessActivityTypeEntity selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2021-01-27
     */
    int updateByPrimaryKeySelective(BusinessActivityTypeEntity record);

    /**
     *
     * @mbggenerated 2021-01-27
     */
    int updateByPrimaryKey(BusinessActivityTypeEntity record);

    BusinessActivityTypeEntity selectByType(@Param("type") String type);

    List<BusinessActivityTypeEntity> queryNoRepeat(@Param("param") BusinessActivityTypeEntity param);

    List<BusinessActivityTypeEntity> queryByMessage(@Param("param") BusinessActivityTypeEntity param);

    int updateClickCount(@Param("id") Long id);

    int updatePayCount(@Param("id") Long id);

    ActivityInfoEntity queryActivityInfo(@Param("param") RechargeRecordEntity param);
}