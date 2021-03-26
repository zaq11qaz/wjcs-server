package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.OverseasIdentityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OverseasIdentityMapper extends BaseFrameworkMapper<OverseasIdentityEntity, Long> {
    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    int insert(OverseasIdentityEntity record);

    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    int insertSelective(OverseasIdentityEntity record);

    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    OverseasIdentityEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    int updateByPrimaryKeySelective(OverseasIdentityEntity record);

    /**
     * @mbggenerated 2019-03-08
     */
    @Override
    int updateByPrimaryKey(OverseasIdentityEntity record);

    /**
     * 后台 认证中心 境外认证
     *
     * @return
     */
    List<OverseasIdentityEntity> queryOverseasAuthListPage(@Param("param") OverseasIdentityEntity record);

    Integer queryOverseasAuthListPageCount(@Param("param") OverseasIdentityEntity record);

    List<OverseasIdentityEntity> queryByMessage(@Param("param")OverseasIdentityEntity param);

    Integer queryByMessageCount(@Param("param") OverseasIdentityEntity param);
}