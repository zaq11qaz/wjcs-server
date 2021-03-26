package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.MasterFollowEntity;
import com.huihe.eg.user.mybatis.dao.MasterFollowMapper;
import com.huihe.eg.user.service.dao.MasterFollowService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterFollowServiceImpl extends BaseFrameworkServiceImpl<MasterFollowEntity, Long> implements MasterFollowService {

    @Resource
    private MasterFollowMapper mapper;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<MasterFollowEntity> queryListPage(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<MasterFollowEntity> entityList = null;
        try {
            entityList = mapper.queryListPage(param);
            for (MasterFollowEntity entity : entityList) {
                Map<String, Object> map = new HashMap<>();
                //助学师资料
                map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getMaster_id() + "userinfo")));
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryClassListPage");
        }
        return entityList;
    }
    @Override
    public ResultParam queryIsFollow(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Boolean is_follow=false;
        try {
            List<MasterFollowEntity> entityList  = mapper.queryListPage(param);
            if(entityList!=null&&entityList.size()>0){
                is_follow=true;
            }
            map.put("is_follow",is_follow);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" MasterAppointmentServiceImpl     queryIsFollow");
        }
        return ResultUtil.success(map);
    }
    @Override
    public ResultParam deleteFollow(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response){
        try {
            int ret=mapper.deleteFollow(param);
            if(ret>0){
                return ResultUtil.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("MasterFollowServiceImpl deleteFollow");
        }
        return ResultUtil.error(ResultEnum.result_2.getCode(), ResultEnum.result_2.getDesc());

    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultParam insert(MasterFollowEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        try {
            List<MasterFollowEntity> entityList  = mapper.queryListPage(param);
            if(entityList!=null&&entityList.size()>0){
                return ResultUtil.success();
            }else{
                return super.insert(param,request,response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
}