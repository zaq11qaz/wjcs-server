package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.SystemRechargeEntity;
import com.huihe.eg.user.mybatis.dao.SystemRechargeMapper;
import com.huihe.eg.user.service.dao.SystemRechargeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemRechargeServiceImpl extends BaseFrameworkServiceImpl<SystemRechargeEntity, Long> implements SystemRechargeService {

    @Resource
    private SystemRechargeMapper mapper;
    @Resource
    private RedisService redisService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<SystemRechargeEntity> queryListPage(SystemRechargeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<SystemRechargeEntity> systemRechargeEntities = null;
        try {
            systemRechargeEntities = mapper.queryListPage(param);
            for (SystemRechargeEntity systemRechargeEntity : systemRechargeEntities) {
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("userinfo", JSONUtils.obj2Json(redisService.getStr(systemRechargeEntity.getUser_id() + "userinfo")));
                systemRechargeEntity.setMap(stringObjectMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("SystemRechargeServiceImpl   queryListPage");
        }
        return systemRechargeEntities;
    }


    /**
     * 猫币收益统计
     * @param param
     * @param request
     * @param response
     * @return
     */
    @Override
    public Map<String,Object> queryCatCoinCount(SystemRechargeEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;

        try {
            map = new HashMap<>();
            Integer dayPayCount = mapper.queryDayCount(param);
            map.put("dayPayCount",dayPayCount);//日猫币收益

            Integer weekPayCount = mapper.queryWeekCount(param);
            map.put("weekPayCount",weekPayCount);//周猫币收益

            Integer monthPayCount = mapper.queryMonthCount(param);
            map.put("monthPayCount",monthPayCount);//月猫币收益

            Integer payCount = mapper.queryPayCount(param);
            map.put("payCount",payCount);//总猫币收益

        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }



}