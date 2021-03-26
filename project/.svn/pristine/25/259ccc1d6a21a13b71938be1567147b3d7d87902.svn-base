package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.SignInDayEntiy;
import com.huihe.eg.user.model.SignInEntity;
import org.apache.ibatis.annotations.Param;

public interface SignInMapper extends BaseFrameworkMapper<SignInEntity, Long> {
    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    int insert(SignInEntity record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    int insertSelective(SignInEntity record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    SignInEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    int updateByPrimaryKeySelective(SignInEntity record);

    /**
     *
     * @mbggenerated 2019-03-14
     */
    @Override
    int updateByPrimaryKey(SignInEntity record);

    /**
     * 统计近7天签到情况
     * @param record
     * @return
     */
    SignInDayEntiy countSevenDay(SignInEntity record);
    Integer queryToday(Long user_id);

    Integer query7DayInfo(@Param("param") SignInEntity entity);
}