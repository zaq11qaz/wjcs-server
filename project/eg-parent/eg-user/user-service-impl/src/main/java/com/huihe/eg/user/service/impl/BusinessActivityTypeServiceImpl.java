package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.google.common.collect.Lists;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.BusinessActivityMapper;
import com.huihe.eg.user.mybatis.dao.BusinessActivityTypeMapper;
import com.huihe.eg.user.mybatis.dao.BusinessMechanismActivityTypeMapper;
import com.huihe.eg.user.service.dao.BusinessActivityTypeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessActivityTypeServiceImpl extends BaseFrameworkServiceImpl<BusinessActivityTypeEntity, Integer> implements BusinessActivityTypeService {

    @Resource
    private BusinessActivityTypeMapper mapper;
    @Resource
    private BusinessMechanismActivityTypeMapper businessMechanismActivityTypeMapper;
    @Resource
    private BusinessActivityMapper businessActivityMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam update(BusinessActivityTypeEntity businessActivityTypeEntity, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNotEmpty(businessActivityTypeEntity.getEntry_mechanism_ids())){
            BusinessActivityTypeEntity businessActivityTypeEntity1 = mapper.selectByPrimaryKey(businessActivityTypeEntity.getId());
            List<String> list = this.deleteMoreIds(businessActivityTypeEntity,businessActivityTypeEntity1.getEntry_mechanism_ids());
        }
        return super.update(businessActivityTypeEntity, request, response);
    }

    private List<String> deleteMoreIds(BusinessActivityTypeEntity businessActivityTypeEntity, String oldIds) {
        String[] oids = oldIds.split(",");
        String entry_mechanism_ids = businessActivityTypeEntity.getEntry_mechanism_ids();
        List<String> list = new ArrayList<String>();
        for(String str : oids){
            if(!entry_mechanism_ids.contains(str)) {
                list.add(str);
                BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                businessActivityEntity.setBusiness_activity_type_id(businessActivityTypeEntity.getId().longValue());
                businessActivityEntity.setMechanism_id(Long.parseLong(str));
                int i = businessActivityMapper.updateByActivityId(businessActivityEntity);
            }
        }
        return list;
    }

    @Override
    public List<BusinessActivityTypeEntity> queryNoRepeat(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<BusinessActivityTypeEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryNoRepeat(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BusinessActivityTypeEntity> queryByMessage(BusinessActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<BusinessActivityTypeEntity> list = Lists.newArrayList();
        try {
            list = mapper.queryByMessage(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public BigDecimal queryNeedPay(RechargeRecordEntity entity, HttpServletRequest request, HttpServletResponse response) {
        BusinessActivityTypeEntity businessActivityTypeEntity = mapper.selectByPrimaryKey(entity.getActivity_id().intValue());
        final Integer course_num = entity.getCourse_num();
        final Integer choose_num = businessActivityTypeEntity.getChoose_num();

        if (course_num <= choose_num) {
            if (entity.getIs_one_time_payment()!=null && entity.getIs_one_time_payment() ){
                return businessActivityTypeEntity.getSeparate_amount();
            }
            return businessActivityTypeEntity.getPay_amount();
        } else {
            if (entity.getIs_one_time_payment()!=null && entity.getIs_one_time_payment() ){
                return businessActivityTypeEntity.getSeparate_amount().add(businessActivityTypeEntity.getAdd_amount());
            }
            if (course_num > choose_num+1){
                return new BigDecimal("0");
            }
            return new BigDecimal(course_num - choose_num)
                    .multiply(businessActivityTypeEntity.getAdd_amount())
                    .add(businessActivityTypeEntity.getPay_amount());
        }
    }

    @Override
    public ActivityInfoEntity queryActivityInfo(RechargeRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
        try {
            activityInfoEntity = mapper.queryActivityInfo(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return activityInfoEntity;
    }

}