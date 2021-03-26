package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.GroupWhitelistedEntity;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface GroupWhitelistedMapper extends BaseFrameworkMapper<GroupWhitelistedEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    int insert(GroupWhitelistedEntity record);

    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    int insertSelective(GroupWhitelistedEntity record);

    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    GroupWhitelistedEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    int updateByPrimaryKeySelective(GroupWhitelistedEntity record);

    /**
     *
     * @mbggenerated 2019-09-19
     */
    @Override
    int updateByPrimaryKey(GroupWhitelistedEntity record);
    /**
     *
     * @mbggenerated 2020年4月27日14:44:48
     */
    List<Long> queryGroupIds(@Param("param") GroupWhitelistedEntity record);
}