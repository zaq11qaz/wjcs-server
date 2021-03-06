package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.model.RechargeRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MasterSetPriceMapper extends BaseFrameworkMapper<MasterSetPriceEntity, Long> {
    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    int insert(MasterSetPriceEntity record);

    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    int insertSelective(MasterSetPriceEntity record);

    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    MasterSetPriceEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    int updateByPrimaryKeySelective(MasterSetPriceEntity record);

    /**
     * @mbggenerated 2020-08-24
     */
    @Override
    int updateByPrimaryKey(MasterSetPriceEntity record);

    List<MasterSetPriceEntity> queryStatus12ListPage(@Param("param") MasterSetPriceEntity masterSetPriceEntity);

    Integer query12ListPageCount(@Param("param") MasterSetPriceEntity param);

    void updatePayNumByPrimaryKey(@Param("studyCard_id") Long studyCard_id);

    List<MasterSetPriceEntity> queryOnSale(@Param("param") MasterSetPriceEntity masterSetPriceEntity);

    List<MasterSetPriceEntity> queryRecommendList(@Param("param") MasterSetPriceEntity masterSetPriceEntity);

    Integer queryOnSaleCount(@Param("param") MasterSetPriceEntity masterSetPriceEntity);

    List<MasterSetPriceEntity> queryByMessage(@Param("param") MasterSetPriceEntity masterSetPriceEntity);

    Integer queryByMessageCount(@Param("param") MasterSetPriceEntity param);

    List<MasterSetPriceEntity> queryActivityListPageByType(@Param("param") MasterSetPriceEntity param);

    List<MasterSetPriceEntity> queryMechanismDisplay(@Param("param") MasterSetPriceEntity param);

    int updateStreamId(@Param("param")MasterSetPriceEntity masterSetPriceEntity);

    List<MasterSetPriceEntity> queryEqualsNotFu(@Param("param") MasterSetPriceEntity param);

    List<MasterSetPriceEntity> queryActivityListPageByActivityId(@Param("param") MasterSetPriceEntity param);

    List<MasterSetPriceEntity> queryByIdList(@Param("split") String[] split);
}