package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.comm.aop.WebLog;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.user.model.UserIdentityEntity;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.mybatis.dao.UserIdentityMapper;
import com.huihe.eg.user.service.dao.UserIdentityService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.huihe.eg.user.service.dao.UserInfoService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserIdentityServiceImpl extends BaseFrameworkServiceImpl<UserIdentityEntity, Long> implements UserIdentityService {

    @Resource
    private UserIdentityMapper mapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AmqpTemplate rabbitTemplate;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam update(UserIdentityEntity userIdentityEntity, HttpServletRequest request, HttpServletResponse response) {
        userIdentityEntity.setRefuse_contect("");//重新提交置空拒绝理由
        return super.update(userIdentityEntity, request, response);
    }

    @Override
    public ResultParam insert(UserIdentityEntity userIdentityEntity, HttpServletRequest request, HttpServletResponse response) {
        UserIdentityEntity userIdentityEntity1 = new UserIdentityEntity();
        userIdentityEntity1.setUser_id(userIdentityEntity.getUser_id());
        List<UserIdentityEntity> query = mapper.query(userIdentityEntity1);
        if (query != null && query.size() > 0){
            userIdentityEntity.setRefuse_contect("");
            userIdentityEntity.setStatus(2);
            userIdentityEntity.setId(query.get(0).getId());
            return super.update(userIdentityEntity,request,response);
        }
        return super.insert(userIdentityEntity, request, response);
    }

    @Override
    public Map<String, Object> queryIdentityListPage(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapResult=new HashMap<>();
        List<UserIdentityEntity> list = mapper.queryIdentityListPage(identityEntity);
        Integer integer=mapper.queryIdentityListPageCount(identityEntity);
        for (UserIdentityEntity entity : list){
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
            entity.setMap(map);
        }
        mapResult.put("identityListPageInfo",list);
        mapResult.put("identityListPageCount",integer);
        return mapResult;
    }
    @Override
    public List<UserIdentityEntity> queryListPage(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        List<UserIdentityEntity> list = super.queryListPage(identityEntity, request, response);
        for (UserIdentityEntity entity : list){
            Map<String,Object> map = new HashMap<>();
            map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
            entity.setMap(map);
        }
        return list;
    }

    @Override
    public ResultParam realNameAudit(UserIdentityEntity identityEntity, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String,Object> map = new HashMap<>();
        try {
            int ret = mapper.updateByPrimaryKeySelective(identityEntity);
            if(ret > 0){
                UserIdentityEntity userIdentityEntity = mapper.selectByPrimaryKey(identityEntity.getId());
                map = JSONUtils.generatorMap("修改成功",true);
                PushMessageParam pushMessageParam = new PushMessageParam();
                pushMessageParam.setTarget_id(userIdentityEntity.getUser_id());
                pushMessageParam.setType("authentication");
                pushMessageParam.setOpera_type("realNameAuthentication");
                pushMessageParam.setTitle("实名认证通知");
                pushMessageParam.setType_id(identityEntity.getId());
                if (identityEntity.getStatus() == 1){
                    pushMessageParam.setContent("实名认证审核已通过");
                    UserInfoEntity infoEntity = new UserInfoEntity();
                    infoEntity.setUser_id(identityEntity.getUser_id());
                    infoEntity.setIdentity_auth(true);
                    userInfoService.update(infoEntity,httpServletRequest,httpServletResponse);
                }else {
                    pushMessageParam.setContent("实名认证审核被拒绝");
                }
                /**
                 * 认证审核推送
                 */
                pushMessageParam.setIs_teach_paypal(identityEntity.getIs_teach_paypal());
                rabbitTemplate.convertAndSend("updateAuthMessage",pushMessageParam);
            }else {
                map = JSONUtils.generatorMap("修改失败",false);
            }
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return ResultUtil.success(map);
    }

    @Override
    public Map<String, Object> queryRealNameAuditCount(UserIdentityEntity identityEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            Integer total = mapper.queryListPageCount(identityEntity);
            map.put("total", total);

            identityEntity.setStatus(1);
            Integer agreeCount = mapper.queryListPageCount(identityEntity);
            map.put("agreeCount", agreeCount);

            identityEntity.setStatus(2);
            Integer waitCount = mapper.queryListPageCount(identityEntity);
            map.put("waitCount", waitCount);

            identityEntity.setStatus(3);
            Integer refuseCount = mapper.queryListPageCount(identityEntity);
            map.put("refuseCount", refuseCount);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> queryByMessage(UserIdentityEntity param, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = Maps.newHashMap();
        try {
            List<UserIdentityEntity> list = mapper.queryByMessage(param);
            Integer total = mapper.queryByMessageCount(param);
            map.put("rows", list);
            map.put("total", total);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;

    }
}