package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.MasterMechanismEntity;
import com.huihe.eg.user.model.MechanismUserEntity;
import com.huihe.eg.user.mybatis.dao.MasterMechanismMapper;
import com.huihe.eg.user.mybatis.dao.MechanismUserMapper;
import com.huihe.eg.user.service.dao.MechanismUserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MechanismUserServiceImpl extends BaseFrameworkServiceImpl<MechanismUserEntity, Integer> implements MechanismUserService {

    @Resource
    private MechanismUserMapper mapper;
    @Resource
    private RedisService redisService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MasterMechanismMapper masterMechanismMapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(MechanismUserEntity mechanismUserEntity, HttpServletRequest request, HttpServletResponse response) {
        mechanismUserEntity.setPageSize(1);
        List<MechanismUserEntity> mechanismUserEntities = mapper.queryListPage(mechanismUserEntity);
        if (mechanismUserEntities!=null&&mechanismUserEntities.size()>0 &&"course".equals(mechanismUserEntities.get(0).getType())){
            mechanismUserEntity = mechanismUserEntities.get(0);
            if (mechanismUserEntity.getFree_course()>0){
                int i = mapper.updateSubFreeCourse(mechanismUserEntity);
                return ResultUtil.success(true);
            }
            return ResultUtil.success(false);
        }
        MasterMechanismEntity mechanismEntity = masterMechanismMapper.selectByPrimaryKey(mechanismUserEntity.getMechanism_id());
        mechanismUserEntity.setFree_course(mechanismEntity.getResource_classes_new()==null?6:mechanismEntity.getResource_classes_new());
        ResultParam insert = super.insert(mechanismUserEntity, request, response);
        if (insert.getCode()==0){
            PushMessageParam pushMessageParam = new PushMessageParam();
            pushMessageParam.setType_id(mechanismUserEntity.getId().longValue());
            pushMessageParam.setTarget_id(mechanismEntity.getUser_id());
            pushMessageParam.setPush_type("mechanism_user_need_check");
            pushMessageParam.setOpera_type("mechanism_user_need_check");
            pushMessageParam.setContent("新老客待确认");
            pushMessageParam.setType("mechanism_user_need_check");
            pushMessageParam.setTitle("新老客待确认");
            pushMessageParam.setIs_teach_paypal(mechanismEntity.getIs_teach_paypal());
            rabbitTemplate.convertAndSend("addNewsMessage", pushMessageParam);
        }
        return insert;
    }

    @Override
    public synchronized ResultParam update(MechanismUserEntity param, HttpServletRequest request, HttpServletResponse response) {
        MechanismUserEntity mechanismUserEntity = mapper.selectByPrimaryKey(param.getId());
        if (mechanismUserEntity.getStatus()!=1){
            return ResultUtil.error(UserEum.user_10054.getCode(),UserEum.user_10054.getDesc());
        }
        if (!param.getIs_new()){
            int i = mechanismUserEntity.getFree_course()-2;
            mechanismUserEntity.setFree_course(Math.max(i, 0));
            mechanismUserEntity.setIs_new(false);
        }
        mechanismUserEntity.setStatus(2);
        return super.update(mechanismUserEntity, request, response);
    }

    @Override
    public List<MechanismUserEntity> queryListPage(MechanismUserEntity mechanismUserEntity, HttpServletRequest request, HttpServletResponse response) {
        List<MechanismUserEntity> mechanismUserEntities = super.queryListPage(mechanismUserEntity, request, response);
        if (mechanismUserEntities!=null&&mechanismUserEntities.size()>0){
            for (MechanismUserEntity userEntity : mechanismUserEntities) {
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(userEntity.getUser_id()+"userinfo")));
                userEntity.setMap(map);
            }
        }
        return mechanismUserEntities;
    }
}