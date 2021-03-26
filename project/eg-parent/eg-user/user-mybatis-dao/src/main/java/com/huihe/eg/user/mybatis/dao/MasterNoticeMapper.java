package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MasterNoticeEntity;
import org.apache.ibatis.annotations.Param;

public interface MasterNoticeMapper extends BaseFrameworkMapper<MasterNoticeEntity, Long> {
    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    int insert(MasterNoticeEntity record);

    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    int insertSelective(MasterNoticeEntity record);

    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    MasterNoticeEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    int updateByPrimaryKeySelective(MasterNoticeEntity record);

    /**
     * @mbggenerated 2019-07-10
     */
    @Override
    int updateByPrimaryKey(MasterNoticeEntity record);

    int updateIsRead(@Param("param") MasterNoticeEntity record);
}