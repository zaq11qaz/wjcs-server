package com.huihe.eg.mall.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.mall.model.UserInfoEntity;

public interface UserInfoMapper extends BaseFrameworkMapper<UserInfoEntity, Long> {
    /**
     *
     * @mbggenerated 2019-09-02
     */
    int deleteByPrimaryKey(Long user_id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insert(UserInfoEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int insertSelective(UserInfoEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    UserInfoEntity selectByPrimaryKey(Long user_id);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKeySelective(UserInfoEntity record);

    /**
     *
     * @mbggenerated 2019-09-02
     */
    int updateByPrimaryKey(UserInfoEntity record);
}