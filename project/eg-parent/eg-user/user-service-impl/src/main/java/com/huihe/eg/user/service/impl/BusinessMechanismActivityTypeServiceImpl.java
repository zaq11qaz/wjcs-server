package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.BusinessActivityMapper;
import com.huihe.eg.user.mybatis.dao.BusinessActivityTypeMapper;
import com.huihe.eg.user.mybatis.dao.BusinessMechanismActivityTypeMapper;
import com.huihe.eg.user.mybatis.dao.MasterMechanismMapper;
import com.huihe.eg.user.service.dao.BusinessActivityTypeService;
import com.huihe.eg.user.service.dao.BusinessMechanismActivityTypeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessMechanismActivityTypeServiceImpl extends BaseFrameworkServiceImpl<BusinessMechanismActivityTypeEntity, Long> implements BusinessMechanismActivityTypeService {

    @Resource
    private BusinessMechanismActivityTypeMapper mapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public BusinessMechanismActivityTypeEntity queryActivityDetail(BusinessMechanismActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        BusinessMechanismActivityTypeEntity businessMechanismActivityTypeEntity = new BusinessMechanismActivityTypeEntity();
        try {
            businessMechanismActivityTypeEntity = mapper.selectByActivityId(param.getId());
            BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByPrimaryKey(businessMechanismActivityTypeEntity.getActivity_id().intValue());
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("background_image", businessActivityTypeEntity.getBackground_image());
            List<MasterMechanismEntity> list = masterMechanismMapper.queryMechanismActivityList(businessActivityTypeEntity.getEntry_mechanism_ids().split(","));
            if (list!=null&&list.size()>0){
                for (MasterMechanismEntity mechanismEntity : list) {
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMechanism_id(mechanismEntity.getId());
                    businessActivityEntity.setBusiness_activity_type_id(businessActivityTypeEntity.getId().longValue());
                    Map<String, Object> map1 = Maps.newHashMap();
                    map1.put("courseList", businessActivityMapper.queryActivityList(businessActivityEntity));
                    mechanismEntity.setMap(map1);
                }
            }
            map.put("mechanismList", list);
            businessMechanismActivityTypeEntity.setMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return businessMechanismActivityTypeEntity;
    }
}