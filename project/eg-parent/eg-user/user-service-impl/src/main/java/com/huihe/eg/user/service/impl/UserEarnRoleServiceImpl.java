package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.user.model.UserEarnRoleEntity;
import com.huihe.eg.user.mybatis.dao.UserEarnRoleMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderMapper;
import com.huihe.eg.user.service.dao.UserEarnRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserEarnRoleServiceImpl extends BaseFrameworkServiceImpl<UserEarnRoleEntity, Long> implements UserEarnRoleService {

    @Resource
    private UserEarnRoleMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRecommenderMapper userRecommenderMapper;

    @Override
    public ResultParam insert(UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response) {
        UserEarnRoleEntity userEarnRoleEntity = new UserEarnRoleEntity();
        userEarnRoleEntity.setName(param.getName());
        userEarnRoleEntity.setType(param.getType());
        List<UserEarnRoleEntity> query = mapper.query(userEarnRoleEntity);
        if (query!=null&&query.size()>0){
            param.setId(query.get(0).getId());
            return super.update(param,request,response);
        }
        param.setStatus(1);
        return super.insert(param, request, response);
    }


    @Override
    public ResultParam updateEffective(UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response) {
        userRecommenderMapper.updateRoleId(param);
        return  ResultUtil.success();
    }

    @Override
    public ResultParam updateUserEffective(UserEarnRoleEntity param, HttpServletRequest request, HttpServletResponse response) {
        userInfoMapper.updateRoleId(param);
        return ResultUtil.success();
    }
}