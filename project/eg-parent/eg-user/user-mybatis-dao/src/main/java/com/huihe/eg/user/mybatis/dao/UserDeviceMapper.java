package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.UserDeviceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDeviceMapper extends BaseFrameworkMapper<UserDeviceEntity, Long> {
    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    int insert(UserDeviceEntity record);

    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    int insertSelective(UserDeviceEntity record);

    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    UserDeviceEntity selectByPrimaryKey(Long id);

    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    int updateByPrimaryKeySelective(UserDeviceEntity record);

    /**
     * @mbggenerated 2019-04-15
     */
    @Override
    int updateByPrimaryKey(UserDeviceEntity record);

    List<UserDeviceEntity> queryByMessage(@Param("param") UserDeviceEntity userDeviceEntity);
}