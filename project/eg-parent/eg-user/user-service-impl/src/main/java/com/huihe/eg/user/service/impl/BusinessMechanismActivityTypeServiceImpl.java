package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.OrderEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.*;
import com.huihe.eg.user.service.dao.BusinessMechanismActivityTypeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private ExhibitionPicMapper exhibitionPicMapper;
    @Resource
    private BusinessActivitySponsorsMapper businessActivitySponsorsMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private BusinessActivityUserHistoryMapper businessActivityUserHistoryMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public BusinessMechanismActivityTypeEntity queryActivityDetail(BusinessMechanismActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        BusinessMechanismActivityTypeEntity businessMechanismActivityTypeEntity = new BusinessMechanismActivityTypeEntity();
        try {
            ResultParam resultParam = this.inserHistory(param, request, response);
            BusinessActivityTypeEntity businessActivityTypeEntity = businessActivityTypeMapper.selectByPrimaryKey(param.getId().intValue());
            int i = businessActivityTypeMapper.updateClickCount(param.getId());
            businessMechanismActivityTypeEntity = mapper.selectByPrimaryKey(businessActivityTypeEntity.getBusiness_mechanism_activity_id());
            businessMechanismActivityTypeEntity.setAmount(businessActivityTypeEntity.getAmount());
            businessMechanismActivityTypeEntity.setDiscount_amount(businessActivityTypeEntity.getDiscount_amount());
            businessMechanismActivityTypeEntity.setSpike_status(businessActivityTypeEntity.getSpike_status());
            businessMechanismActivityTypeEntity.setActivity_starttime(businessActivityTypeEntity.getActivity_starttime());
            businessMechanismActivityTypeEntity.setSeparate_amount(businessActivityTypeEntity.getSeparate_amount());
            businessMechanismActivityTypeEntity.setActivity_endtime(businessActivityTypeEntity.getActivity_endtime());
            businessMechanismActivityTypeEntity.setShare_description(businessActivityTypeEntity.getShare_description());
            businessMechanismActivityTypeEntity.setNumber_of_people(businessActivityTypeEntity.getNumber_of_people());
            businessMechanismActivityTypeEntity.setEach_time_percentage(businessActivityTypeEntity.getEach_time_percentage());
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("background_image", businessActivityTypeEntity.getBackground_image());
            List<MasterMechanismEntity> list = masterMechanismMapper.queryMechanismActivityList(
                    businessActivityTypeEntity.getEntry_mechanism_ids().split(","));
            if (list != null && list.size() > 0) {
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
            RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity();
            rechargeRecordEntity.setFinished(true);
            rechargeRecordEntity.setActivity_id(param.getId());
            rechargeRecordEntity.setStatus(2);
            map.put("payUserCount", rechargeRecordMapper.queryListPageCount(rechargeRecordEntity));
            ExhibitionPicEntity exhibitionPicEntity = new ExhibitionPicEntity();
            exhibitionPicEntity.setPageSize(1);
            exhibitionPicEntity.setState(1);
            exhibitionPicEntity.setType("teachPay_share");
            map.put("share_image", exhibitionPicMapper.queryListPage(exhibitionPicEntity));

            List<BusinessActivitySponsorsEntity> sponsorsList = Lists.newArrayList();
            if (StringUtil.isNotEmpty(businessMechanismActivityTypeEntity.getSponsors_ids())) {
                String[] split = businessMechanismActivityTypeEntity.getSponsors_ids().split(",");
                sponsorsList = businessActivitySponsorsMapper.querySponsorsList(split);
                map.put("sponsorsList", sponsorsList);
            } else {
                map.put("sponsorsList", sponsorsList);
            }

            businessMechanismActivityTypeEntity.setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessMechanismActivityTypeEntity;
    }

    private synchronized ResultParam inserHistory(BusinessMechanismActivityTypeEntity param, HttpServletRequest request, HttpServletResponse response) {
        try {
            String access_token = request.getParameter("token");
            String user_id = redisService.getStr(access_token);
            if (StringUtil.isNotEmpty(user_id)) {
                BusinessActivityUserHistoryEntity businessActivityUserHistoryEntity = new BusinessActivityUserHistoryEntity();
                businessActivityUserHistoryEntity.setUser_id(Long.valueOf(user_id));
                businessActivityUserHistoryEntity.setActivity_id(param.getId());
                businessActivityUserHistoryMapper.insertSelective(businessActivityUserHistoryEntity);
            }else {
                return ResultUtil.error(OrderEum.order_70049.getCode(), OrderEum.order_70049.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
    }

    @Override
    @Transactional
    public synchronized ResultParam insert(BusinessMechanismActivityTypeEntity businessMechanismActivityTypeEntity, HttpServletRequest request, HttpServletResponse response) {
        businessMechanismActivityTypeEntity.setPageSize(1);
        List<BusinessMechanismActivityTypeEntity> businessMechanismActivityTypeEntities = mapper.queryListPage(businessMechanismActivityTypeEntity);
        if (businessMechanismActivityTypeEntities != null && businessMechanismActivityTypeEntities.size() > 0) {
            return ResultUtil.success();
        }
        return super.insert(businessMechanismActivityTypeEntity, request, response);
    }
}