package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.UserRecommenderEntity;
import com.huihe.eg.user.model.UserRecommenderGroupEntity;
import com.huihe.eg.user.model.UserRecommenderGroupInterlinkEntity;
import com.huihe.eg.user.model.UserRecommenderIncomeLogEntity;
import com.huihe.eg.user.mybatis.dao.UserRecommenderGroupInterlinkMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderGroupMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderMapper;
import com.huihe.eg.user.service.dao.UserRecommenderGroupService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRecommenderGroupServiceImpl extends BaseFrameworkServiceImpl<UserRecommenderGroupEntity, Long> implements UserRecommenderGroupService {

    @Resource
    private UserRecommenderGroupMapper mapper;
    @Resource
    private UserRecommenderGroupInterlinkMapper userRecommenderGroupInterlinkMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;

    public void init() {
        setMapper(mapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultParam insert(UserRecommenderGroupEntity userRecommenderGroupEntity, HttpServletRequest request, HttpServletResponse response) {
        UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(userRecommenderGroupEntity.getAdmin_id());
        userRecommenderGroupEntity.setPhone(userRecommenderEntity.getPhone().toString());
        ResultParam insert = super.insert(userRecommenderGroupEntity, request, response);
        int i = userRecommenderMapper.updateGroupId(userRecommenderGroupEntity);
        int i1 = mapper.updateGroupCount(userRecommenderGroupEntity.getId());
        UserRecommenderGroupInterlinkEntity userRecommenderGroupInterlinkEntity = new UserRecommenderGroupInterlinkEntity();
        userRecommenderGroupInterlinkEntity.setRecommender_id(userRecommenderGroupEntity.getAdmin_id());
        userRecommenderGroupInterlinkEntity.setGroup_id(userRecommenderGroupEntity.getId());
        int i2 = userRecommenderGroupInterlinkMapper.insertSelective(userRecommenderGroupInterlinkEntity);
        return insert;
    }

    @Override
    public Map<String, Object> queryManagerListPage(UserRecommenderGroupEntity userRecommenderGroupEntity, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Integer total = this.queryListPageCount(userRecommenderGroupEntity, request, response);
        map.put("total", total);
        List<UserRecommenderGroupEntity> userRecommenderGroupEntityList = this.queryListPage(userRecommenderGroupEntity, request, response);
        if (total > 0) {
            this.setAdminInfo(userRecommenderGroupEntityList);
        }
        map.put("rows", userRecommenderGroupEntityList);
        return map;
    }

    private void setAdminInfo(List<UserRecommenderGroupEntity> userRecommenderGroupEntityList) {
        try {
            for (UserRecommenderGroupEntity recommenderGroupEntity : userRecommenderGroupEntityList) {
                HashMap<String, Object> map1 = Maps.newHashMap();
                UserRecommenderEntity userRecommenderEntity = userRecommenderMapper.selectByPrimaryKey(recommenderGroupEntity.getAdmin_id());
                map1.put("adminPhone", userRecommenderEntity.getPhone());
                map1.put("adminName", userRecommenderEntity.getName());
                recommenderGroupEntity.setMap(map1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}