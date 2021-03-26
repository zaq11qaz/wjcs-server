package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseFrameworkMapper<UserEntity, Long> {
    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    int deleteByPrimaryKey(Long user_id);

    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    int insert(UserEntity record);

    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    int insertSelective(UserEntity record);

    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    UserEntity selectByPrimaryKey(Long user_id);

    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    int updateByPrimaryKeySelective(UserEntity record);

    /**
     * @mbggenerated 2019-01-15
     */
    @Override
    int updateByPrimaryKey(UserEntity record);

    //查询全部手机号
    List<String> queryAllNum();

    List<UserEntity> queryByMessage( @Param("param") UserEntity userEntity);

    Integer queryDayTotal(@Param("param") UserEntity param);

    List<Long> queryIdByLoginName(@Param("login_name") String login_name);

    Integer queryMonthTotal(@Param("param") UserEntity param);
}