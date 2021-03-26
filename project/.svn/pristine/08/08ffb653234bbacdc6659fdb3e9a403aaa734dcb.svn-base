package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageJoinGroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageJoinGroupMapper extends BaseFrameworkMapper<MessageJoinGroupEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insert(MessageJoinGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageJoinGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageJoinGroupEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageJoinGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageJoinGroupEntity record);
    /**
     *
     * @mbggenerated 2019-01-05
     */
    List<Long> queryGroupUserInfos(@Param("param") MessageJoinGroupEntity record);
    /**
     *
     * @mbggenerated 2019年9月5日10:04:24
     */
    int updateStatus(@Param("param")MessageJoinGroupEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    List<MessageJoinGroupEntity> queryMyListPage(@Param("param") MessageJoinGroupEntity record);
    /**
     *
     * @mbggenerated 2019-01-05
     */
    List<MessageJoinGroupEntity> queryGroupListPage(@Param("param") MessageJoinGroupEntity record);
}