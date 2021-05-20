package com.huihe.eg.authorization.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.authorization.model.AuthorizationScreenWhiteEntity;
import com.huihe.eg.authorization.model.ManagerUserEntity;
import com.huihe.eg.authorization.mybatis.dao.AuthorizationScreenWhiteMapper;
import com.huihe.eg.authorization.mybatis.dao.ManagerUserMapper;
import com.huihe.eg.authorization.service.dao.AuthorizationScreenWhiteService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihe.eg.comm.AuthorizationEum;
import com.huihe.eg.comm.TokenUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthorizationScreenWhiteServiceImpl extends BaseFrameworkServiceImpl<AuthorizationScreenWhiteEntity, Long> implements AuthorizationScreenWhiteService {

    @Resource
    private AuthorizationScreenWhiteMapper mapper;
    @Resource
    private ManagerUserMapper managerUserMapper;
    @Resource
    private RedisService redisService;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public synchronized ResultParam loginScreen(AuthorizationScreenWhiteEntity entity, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = Maps.newHashMap();
        entity.setStatus(2);
        String userToken = "";
        Long id = 0L;
//        List<AuthorizationScreenWhiteEntity> authorizationScreenWhiteEntities = mapper.queryListPage(entity);
//        if (authorizationScreenWhiteEntities == null || authorizationScreenWhiteEntities.size() == 0) {
            if (StringUtil.isNotEmpty(entity.getPassWord())) {
                ManagerUserEntity managerUserEntity = new ManagerUserEntity();
                managerUserEntity.setType("screen");
                managerUserEntity.setLogin_name("screen");
                JSONObject jsonObject = JSONUtils.obj2Json(entity.getPassWord());
                entity.setPassWord(jsonObject.get("pwd").toString());
                managerUserEntity.setPwd(entity.getPassWord());
                List<ManagerUserEntity> managerUserEntities = managerUserMapper.queryListPage(managerUserEntity);
                if (managerUserEntities == null || managerUserEntities.size() == 0) {
                    return ResultUtil.error(AuthorizationEum.authorization_30007.getCode(), AuthorizationEum.authorization_30007.getDesc());
                }
                int i1 = mapper.insertSelective(entity);
                userToken = "screen" + TokenUtils.createJWT(entity.getId().toString(), "screen", "qmore", -1);
                redisService.set(userToken, entity.getId().toString(), 86400);
                id = managerUserEntities.get(0).getActivity_id();
            }else {
                return ResultUtil.error(AuthorizationEum.authorization_30007.getCode(), AuthorizationEum.authorization_30007.getDesc());
            }
//        }else {
//            userToken = "screen" + TokenUtils.createJWT(authorizationScreenWhiteEntities.get(0).getId().toString(), "screen", "qmore", -1);
//            redisService.set(userToken, authorizationScreenWhiteEntities.get(0).getId().toString(), 86400);
//            id = authorizationScreenWhiteEntities.get(0).getac
//        }
        map.put("token", userToken);
        map.put("id", id);
        return ResultUtil.success(map);
    }
}