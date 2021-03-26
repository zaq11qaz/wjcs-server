package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterSetPriceDisplayEntity;
import org.apache.ibatis.annotations.Param;

public interface MasterSetPriceDisplayMapper extends BaseFrameworkMapper<MasterSetPriceDisplayEntity, Long> {
    /**
     *
     * @mbggenerated 2021-03-03
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-03-03
     */
    int insert(MasterSetPriceDisplayEntity record);

    /**
     *
     * @mbggenerated 2021-03-03
     */
    int insertSelective(MasterSetPriceDisplayEntity record);

    /**
     *
     * @mbggenerated 2021-03-03
     */
    MasterSetPriceDisplayEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-03-03
     */
    int updateByPrimaryKeySelective(MasterSetPriceDisplayEntity record);

    /**
     *
     * @mbggenerated 2021-03-03
     */
    int updateByPrimaryKey(MasterSetPriceDisplayEntity record);

    int updateCloseById(@Param("id") Long id);

    MasterSetPriceDisplayEntity selectMasterSetPriceId(@Param("param") MasterSetPriceDisplayEntity masterSetPriceDisplayEntity);
}