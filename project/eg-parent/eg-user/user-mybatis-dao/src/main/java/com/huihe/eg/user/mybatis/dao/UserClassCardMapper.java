package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserClassCardEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserClassCardMapper extends BaseFrameworkMapper<UserClassCardEntity, Long> {
    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insert(UserClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int insertSelective(UserClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    UserClassCardEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKeySelective(UserClassCardEntity record);

    /**
     *
     * @mbggenerated 2019-10-18
     */
    @Override
    int updateByPrimaryKey(UserClassCardEntity record);
    /**
     *
     * @mbggenerated 2019-10-18
     */
    int updateDefaultUse(@Param("param") UserClassCardEntity record);

    Integer queryDayTotal(@Param("param") UserClassCardEntity record);

    Integer queryWeekTotal(@Param("param")UserClassCardEntity record);

    Integer queryMonthTotal(@Param("param")UserClassCardEntity record);

    Integer queryPayTotal(@Param("param")UserClassCardEntity record);

    List<UserClassCardEntity> queryByMessage(@Param("param") UserClassCardEntity param);

    Integer queryByMessageCount(@Param("param") UserClassCardEntity param);
}