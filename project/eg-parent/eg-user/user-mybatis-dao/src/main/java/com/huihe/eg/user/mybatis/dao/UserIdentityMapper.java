package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserIdentityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserIdentityMapper extends BaseFrameworkMapper<UserIdentityEntity, Long> {
    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    int insert(UserIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    int insertSelective(UserIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    UserIdentityEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKeySelective(UserIdentityEntity record);

    /**
     *
     * @mbggenerated 2019-01-12
     */
    @Override
    int updateByPrimaryKey(UserIdentityEntity record);

    /**
     * 通过用户id 修改认证信息
     * @param record
     * @return
     */
    List<UserIdentityEntity> queryIdentityListPage(@Param("param") UserIdentityEntity record);
    /**
     *
     * @mbggenerated 2019年8月9日13:22:40
     */
    Integer queryIdentityListPageCount(@Param("param")UserIdentityEntity record);

    List<UserIdentityEntity> queryByMessage(@Param("param")UserIdentityEntity param);

    Integer queryByMessageCount(@Param("param") UserIdentityEntity param);
}