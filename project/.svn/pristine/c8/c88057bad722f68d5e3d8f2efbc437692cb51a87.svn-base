package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserSchoolEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSchoolMapper extends BaseFrameworkMapper<UserSchoolEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    int insert(UserSchoolEntity record);

    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    int insertSelective(UserSchoolEntity record);

    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    UserSchoolEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    int updateByPrimaryKeySelective(UserSchoolEntity record);

    /**
     *
     * @mbggenerated 2019-01-21
     */
    @Override
    int updateByPrimaryKey(UserSchoolEntity record);

    /**
     * 查询是否是同学
     */

    List<UserSchoolEntity> queryIsClassmate(@Param("param")UserSchoolEntity userSchoolEntity);
}