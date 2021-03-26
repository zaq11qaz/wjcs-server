package com.huihe.eg.user.service.impl;

import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.user.model.UserLookEntity;
import com.huihe.eg.user.mybatis.dao.UserLookMapper;
import com.huihe.eg.user.service.dao.UserLookService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLookServiceImpl extends BaseFrameworkServiceImpl<UserLookEntity, Long> implements UserLookService {

    @Resource
    private UserLookMapper mapper;
    @Resource
    private RedisService redisService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<UserLookEntity> queryListPage(UserLookEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserLookEntity> lookEntities=null;
        try {
            lookEntities=mapper.queryListPage(param);
            for (UserLookEntity entity:lookEntities) {
                Map<String,Object> map=new HashMap<>();
                if(param.getUser_id()==null||param.getUser_id()==0){
                    if(param.getOther_user_id()!=null&&param.getOther_user_id()!=0){
                        map.put("userinfo", JSONUtils.obj2Json(redisService.getStr(entity.getUser_id() + "userinfo")));
                    }
                }else{
                    map.put("userinfo",JSONUtils.obj2Json(redisService.getStr(entity.getOther_user_id() + "userinfo")));
                }
                entity.setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lookEntities;
    }
}