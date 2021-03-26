package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.UserMemberCardEntity;
import com.huihe.eg.user.model.UserMemberLevelEntity;
import com.huihe.eg.user.mybatis.dao.UserMemberCardMapper;
import com.huihe.eg.user.mybatis.dao.UserMemberLevelMapper;
import com.huihe.eg.user.service.dao.UserMemberLevelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMemberLevelServiceImpl extends BaseFrameworkServiceImpl<UserMemberLevelEntity, Long> implements UserMemberLevelService {

    @Resource
    private UserMemberLevelMapper mapper;
    @Resource
    private UserMemberCardMapper userMemberCardMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public List<UserMemberLevelEntity> query(UserMemberLevelEntity param, HttpServletRequest request, HttpServletResponse response) {
        List<UserMemberLevelEntity> levelEntities = null;
        try {
            levelEntities = mapper.query(param);
            if (levelEntities != null && levelEntities.size() > 0) {
                for (UserMemberLevelEntity entity : levelEntities) {
                    UserMemberCardEntity userMemberCardEntity = new UserMemberCardEntity();
                    userMemberCardEntity.setLevel_id(entity.getId());
                    userMemberCardEntity.setPlatform(param.getPlatform());
                    if (param.getStatus()==0){
                        userMemberCardEntity.setStatus(2);
                    }else {
                        userMemberCardEntity.setStatus(1);
                    }
                    List<UserMemberCardEntity> entityList = userMemberCardMapper.query(userMemberCardEntity);
                    entity.setCardEntityList(entityList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return levelEntities;
    }
}