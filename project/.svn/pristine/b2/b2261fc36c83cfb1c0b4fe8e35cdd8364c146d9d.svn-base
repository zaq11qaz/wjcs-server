package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterSetPricePriceEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MasterSetPricePriceMapper extends BaseFrameworkMapper<MasterSetPricePriceEntity, Long> {
    /**
     *
     * @mbggenerated 2020-12-19
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-12-19
     */
    int insert(MasterSetPricePriceEntity record);

    /**
     *
     * @mbggenerated 2020-12-19
     */
    int insertSelective(MasterSetPricePriceEntity record);

    /**
     *
     * @mbggenerated 2020-12-19
     */
    MasterSetPricePriceEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2020-12-19
     */
    int updateByPrimaryKeySelective(MasterSetPricePriceEntity record);

    /**
     *
     * @mbggenerated 2020-12-19
     */
    int updateByPrimaryKey(MasterSetPricePriceEntity record);

    List<MasterSetPricePriceEntity> queryPriceList(@Param("param") MasterSetPricePriceEntity masterSetPricePriceEntity);

    BigDecimal queryPrice(@Param("param") MasterSetPricePriceEntity masterSetPricePriceEntity);

    List<MasterSetPricePriceEntity> querypriceListBigger(@Param("param") MasterSetPricePriceEntity masterSetPricePriceEntity);

    int deleteByMasterSetPriceId(@Param("master_set_price_id") Long master_set_price_id);
}