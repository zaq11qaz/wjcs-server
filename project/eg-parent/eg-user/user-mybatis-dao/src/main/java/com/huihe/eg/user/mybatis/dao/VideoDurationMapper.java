package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.VideoDurationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDurationMapper extends BaseFrameworkMapper<VideoDurationEntity, Long> {
    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    int insert(VideoDurationEntity record);

    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    int insertSelective(VideoDurationEntity record);

    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    VideoDurationEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    int updateByPrimaryKeySelective(VideoDurationEntity record);

    /**
     * @mbggenerated 2019-06-11
     */
    @Override
    int updateByPrimaryKey(VideoDurationEntity record);

    List<VideoDurationEntity> queryVideoReconnection(@Param("param") VideoDurationEntity userSchoolEntity);

    List<VideoDurationEntity> queryUnsuspendedVideo(@Param("param") VideoDurationEntity userSchoolEntity);

}