package com.huihe.eg.message.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.message.model.MessageFriendEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageFriendMapper extends BaseFrameworkMapper<MessageFriendEntity, Long> {
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
    int insert(MessageFriendEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int insertSelective(MessageFriendEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    MessageFriendEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKeySelective(MessageFriendEntity record);

    /**
     *
     * @mbggenerated 2019-01-05
     */
    @Override
    int updateByPrimaryKey(MessageFriendEntity record);

    /**
     * 查询好友
     */
    List<MessageFriendEntity> queryUserFirendInfo(@Param("param") MessageFriendEntity messageFriendEntity);
}