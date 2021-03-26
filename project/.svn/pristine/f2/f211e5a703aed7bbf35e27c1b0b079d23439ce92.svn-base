package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.UserLikeEntity;
import com.huihe.eg.user.mybatis.dao.UserLikeMapper;
import com.huihe.eg.user.service.dao.UserLikeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLikeServiceImpl extends BaseFrameworkServiceImpl<UserLikeEntity, Long> implements UserLikeService {

    @Resource
    private UserLikeMapper mapper;
    @Resource
    private RedisService redisService;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserLikeEntity param, HttpServletRequest request, HttpServletResponse response) {
        this.init();
        try {
            List<UserLikeEntity> userLikeEntities = mapper.query(param);
            if (userLikeEntities != null && userLikeEntities.size() > 0) {
                UserLikeEntity userLikeEntity = new UserLikeEntity();
                userLikeEntity.setId(userLikeEntities.get(0).getId());
                userLikeEntity.setIs_like(!userLikeEntities.get(0).getIs_like());
                return super.update(userLikeEntity, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserLikeServiceImpl insert");
        }
        return super.insert(param, request, response);
    }

    @Override
    public List<UserLikeEntity> queryListPage(UserLikeEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserLikeEntity> userLikeEntities = null;
        try {
            userLikeEntities = mapper.queryListPage(param);
            for (UserLikeEntity entity : userLikeEntities) {
                Map<String, Object> map = new HashMap<>();
                if (param.getUser_id() == null || param.getUser_id() == 0) {
                    if (param.getOther_user_id() != null && param.getOther_user_id() != 0) {
                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    }
                } else {
                    map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getOther_user_id() + "userinfo")));
                }
                entity.setMap(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLikeEntities;
    }
}