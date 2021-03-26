package com.huihe.eg.user.mybatis.dao;

import com.cy.framework.mybaties.dao.BaseFrameworkMapper;
import com.huihe.eg.user.model.MechanismClassroomEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MechanismClassroomMapper extends BaseFrameworkMapper<MechanismClassroomEntity, Long> {
    /**
     *
     * @mbggenerated 2021-01-07
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-07
     */
    int insert(MechanismClassroomEntity record);

    /**
     *
     * @mbggenerated 2021-01-07
     */
    int insertSelective(MechanismClassroomEntity record);

    /**
     *
     * @mbggenerated 2021-01-07
     */
    MechanismClassroomEntity selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2021-01-07
     */
    int updateByPrimaryKeySelective(MechanismClassroomEntity record);

    /**
     *
     * @mbggenerated 2021-01-07
     */
    int updateByPrimaryKey(MechanismClassroomEntity record);

    List<MechanismClassroomEntity> queryRoomInList(@Param("param") MechanismClassroomEntity param);

    List<MechanismClassroomEntity> queryRoomNotInList(@Param("param") MechanismClassroomEntity param);
}