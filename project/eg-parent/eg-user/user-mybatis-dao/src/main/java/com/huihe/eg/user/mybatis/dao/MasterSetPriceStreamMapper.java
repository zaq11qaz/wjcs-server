package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;
import com.huihe.eg.user.model.MasterSetPriceStreamEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MasterSetPriceStreamMapper extends BaseFrameworkMapper<MasterSetPriceStreamEntity, Long> {
    /**
     *
     * @mbggenerated 2021-02-25
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-02-25
     */
    int insert(MasterSetPriceStreamEntity record);

    /**
     *
     * @mbggenerated 2021-02-25
     */
    int insertSelective(MasterSetPriceStreamEntity record);

    /**
     *
     * @mbggenerated 2021-02-25
     */
    MasterSetPriceStreamEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-02-25
     */
    int updateByPrimaryKeySelective(MasterSetPriceStreamEntity record);

    /**
     *
     * @mbggenerated 2021-02-25
     */
    int updateByPrimaryKey(MasterSetPriceStreamEntity record);

    List<MasterSetPriceStreamEntity> queryMasterSetPriceListPage(@Param("param") MasterSetPriceStreamEntity param);

    int updateCloseById(@Param("id")Long id);

    int updateAddClick(@Param("param") MasterSetPriceStreamEntity param);

    int updateAddEarnings(@Param("live_streaming_id") Long live_streaming_id, @Param("amount") BigDecimal amount);

    int updatePayNum(@Param("live_streaming_id") Long live_streaming_id);

    List<MasterSetPriceStreamEntity> queryHomeListPage(@Param("param") MasterSetPriceStreamEntity param);
}