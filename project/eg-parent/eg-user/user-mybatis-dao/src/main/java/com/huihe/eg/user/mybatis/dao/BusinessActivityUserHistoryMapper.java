package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.BusinessActivityUserHistoryEntity;
import com.huihe.eg.user.model.PayUserInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessActivityUserHistoryMapper extends BaseFrameworkMapper<BusinessActivityUserHistoryEntity, Long> {
    /**
     *
     * @mbggenerated 2021-04-30
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-04-30
     */
    int insert(BusinessActivityUserHistoryEntity record);

    /**
     *
     * @mbggenerated 2021-04-30
     */
    int insertSelective(BusinessActivityUserHistoryEntity record);

    /**
     *
     * @mbggenerated 2021-04-30
     */
    BusinessActivityUserHistoryEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-04-30
     */
    int updateByPrimaryKeySelective(BusinessActivityUserHistoryEntity record);

    /**
     *
     * @mbggenerated 2021-04-30
     */
    int updateByPrimaryKey(BusinessActivityUserHistoryEntity record);

    List<PayUserInfoEntity> queryUserList(@Param("param") BusinessActivityUserHistoryEntity param);
}