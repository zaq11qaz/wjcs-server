package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.BusinessActivityEntity;
import com.huihe.eg.user.model.BusinessActivityTypeEntity;
import com.huihe.eg.user.model.MasterSetPriceEntity;
import com.huihe.eg.user.mybatis.dao.BusinessActivityMapper;
import com.huihe.eg.user.mybatis.dao.BusinessActivityTypeMapper;
import com.huihe.eg.user.mybatis.dao.MasterMechanismMapper;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceMapper;
import com.huihe.eg.user.service.dao.BusinessActivityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BusinessActivityServiceImpl extends BaseFrameworkServiceImpl<BusinessActivityEntity, Long> implements BusinessActivityService {

    @Resource
    private BusinessActivityMapper mapper;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;
    @Resource
    private BusinessActivityTypeMapper businessActivityTypeMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }


    @Override
    public List<BusinessActivityEntity> queryListPage(BusinessActivityEntity businessActivityEntity, HttpServletRequest request, HttpServletResponse response) {
        List<BusinessActivityEntity> businessActivityEntities = super.queryListPage(businessActivityEntity, request, response);
        if (businessActivityEntities != null && businessActivityEntities.size() > 0) {
            for (BusinessActivityEntity activityEntity : businessActivityEntities) {
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("masterSetPriceEntity", masterSetPriceMapper.selectByPrimaryKey(activityEntity.getMaster_set_price_id()));
                map.put("mechanismEntity", masterMechanismMapper.selectByPrimaryKey(activityEntity.getMechanism_id()));
                activityEntity.setMap(map);
            }
        }
        return businessActivityEntities;
    }

    @Override
    @Transactional
    public synchronized ResultParam insertActivity(BusinessActivityEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Long> longs = new ArrayList<>();
            if (StringUtil.isNotEmpty(param.getMaster_set_price_ids())) {
                String[] stringList = param.getMaster_set_price_ids().split(",");
                int j = 0;
                for (String s : stringList) {
                    param.setMaster_set_price_id(Long.parseLong(s));
                    param.setId(null);
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setMaster_set_price_id(param.getMaster_set_price_id());
                    if (param.getBusiness_activity_type_id()!=null){
                        businessActivityEntity.setBusiness_activity_type_id(param.getBusiness_activity_type_id());
                    }else {
                        businessActivityEntity.setType(param.getType());
                    }
                    businessActivityEntity.setStatus(2);
                    Integer integer = mapper.queryListPageCount(businessActivityEntity);
                    if (integer > 0 && StringUtil.isEmpty(param.getMaster_set_price_ids())) {
                        return ResultUtil.error(OrderEum.order_70036.getCode(), OrderEum.order_70036.getDesc());
                    }
                    BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByPrimaryKey(param.getBusiness_activity_type_id().intValue());
                    param.setType(businessActivityTypeEntity.getType());
                    param.setTitle(businessActivityTypeEntity.getTitle());
                    MasterSetPriceEntity masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(Long.parseLong(s));
                    param.setTags(businessActivityTypeEntity.getTags());

                    switch (businessActivityTypeEntity.getType()) {
                        case "experience_specials":
                            masterSetPriceEntity.setId(null);
                            masterSetPriceEntity.setCourse_num(6);
                            masterSetPriceEntity.setAmout(new BigDecimal("99"));
                            masterSetPriceEntity.setDefault_discount_price(new BigDecimal("99"));
                            masterSetPriceEntity.setDiscount_amout(new BigDecimal("0"));
                            masterSetPriceEntity.setOriginal_price(new BigDecimal("0"));
                            masterSetPriceEntity.setPrice_per_session(new BigDecimal("0"));
                            masterSetPriceEntity.setIs_enlightenment(true);
                            masterSetPriceEntity.setDiscount(new BigDecimal("0"));
                            masterSetPriceEntity.setActivity_list("");
//                            masterSetPriceEntity.setOriginal_price(new BigDecimal("99"));
//                        if (StringUtil.isNotEmpty(param.getActivity_list())) {
//                            param.setActivity_list(param.getActivity_list() + "," + businessActivityTypeEntity.getTags());
//                        } else {
//                            param.setActivity_list(param.getActivity_list() + businessActivityTypeEntity.getTags());
//                            masterSetPriceEntity.setActivity_list(businessActivityTypeEntity.getTags());
//                        }
                            masterSetPriceEntity.setIs_attend_activities(true);
                            int i = masterSetPriceMapper.insertSelective(masterSetPriceEntity);
                            longs.add(masterSetPriceEntity.getId());
                            if (i > 0) {
                                param.setMaster_set_price_id(masterSetPriceEntity.getId());
                                int i1 = mapper.insertSelective(param);
                                if (i1 == 0) {
                                    throw new Exception("??????????????????");
                                }
                                return ResultUtil.success();
                            } else {
                                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
                            }
                        case "single_payment":
                            masterSetPriceEntity.setOriginal_price(param.getPrice());
                            break;
                        case "salesCourse":
                            masterSetPriceEntity.setDiscount_amout(param.getDiscount_amount());
                            break;
                        default:

                    }

                    int i2 = mapper.insertSelective(param);
                    if (i2 == 0) {
                        throw new Exception("??????????????????");
                    }
                    if (StringUtil.isNotEmpty(param.getPrice_list())) {
                        masterSetPriceEntity = masterSetPriceMapper.selectByPrimaryKey(Long.valueOf(s));
                        masterSetPriceEntity.setActivity_id(businessActivityEntity.getId());
                        masterSetPriceMapper.updateByPrimaryKeySelective(masterSetPriceEntity);
                        j++;
                    }
                    if (StringUtil.isNotEmpty(masterSetPriceEntity.getActivity_list())) {
                        masterSetPriceEntity.setActivity_list(masterSetPriceEntity.getActivity_list() + "," + businessActivityTypeEntity.getTags());
                    } else {
                        masterSetPriceEntity.setActivity_list(masterSetPriceEntity.getActivity_list() + businessActivityTypeEntity.getTags());
                    }
                    int i = masterSetPriceMapper.updateByPrimaryKeySelective(masterSetPriceEntity);
                }
            } else if ("double_science_Activity".equals(param.getType())) {
                String[] strings = param.getTitleList().split(",");
                for (String string : strings) {
                    MasterSetPriceEntity masterSetPriceEntity = new MasterSetPriceEntity();
                    masterSetPriceEntity.setStatus(2);
                    masterSetPriceEntity.setType(param.getType());
                    masterSetPriceEntity.setIs_teach_paypal(true);
                    masterSetPriceEntity.setMechanism_id(param.getMechanism_id());
                    masterSetPriceEntity.setTitle(string);
                    masterSetPriceEntity.setCourse_num(12);
                    masterSetPriceEntity.setActivity_id(param.getBusiness_activity_type_id());
                    int i = masterSetPriceMapper.insertSelective(masterSetPriceEntity);
                    if (i == 0) {
                        throw new Exception("??????????????????");
                    }
                    longs.add(masterSetPriceEntity.getId());
                    BusinessActivityEntity businessActivityEntity = new BusinessActivityEntity();
                    businessActivityEntity.setStatus(1);
                    businessActivityEntity.setMaster_set_price_id(masterSetPriceEntity.getId());
                    businessActivityEntity.setStart_time(param.getStart_time());
                    businessActivityEntity.setEnd_time(param.getEnd_time());
                    businessActivityEntity.setType(param.getType());
                    businessActivityEntity.setMechanism_id(param.getMechanism_id());
                    businessActivityEntity.setBusiness_activity_type_id(param.getBusiness_activity_type_id());
                    int i1 = mapper.insertSelective(businessActivityEntity);
                    if (i1 == 0) {
                        throw new Exception("??????????????????");
                    }
                }
            }
            param.setInsertIds(longs);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public ResultParam deleteByActivityInfo(BusinessActivityEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            param.setPageSize(1);
            param.setStatus(1);
            param = mapper.queryListPage(param).get(0);
            param.setStatus(0);
            int i = mapper.updateByPrimaryKey(param);
            if (i>0){
                return ResultUtil.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }
}