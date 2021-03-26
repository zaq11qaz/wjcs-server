package com.huihe.eg.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cy.framework.service.dao.RedisService;
import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.mall.model.UserInfoEntity;
import com.huihe.eg.mall.mybatis.dao.UserInfoMapper;
import com.huihe.eg.mall.service.dao.UserInfoService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl extends BaseFrameworkServiceImpl<UserInfoEntity, Long> implements UserInfoService {

    @Resource
    private UserInfoMapper mapper;
    @Resource
    private RedisService redisService;
    public void init() {
        setMapper(mapper);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultParam update(UserInfoEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        try {
            int ret=mapper.updateByPrimaryKeySelective(param);
            if(ret>0){
                redisService.remove(param.getUser_id() + "malluserinfo");
                param=super.findById(param.getUser_id(),request,response);
                redisService.set(param.getUser_id() + "malluserinfo", JSONObject.toJSONString(param));
            }else{
                return ResultUtil.error(ResultEnum.result_3.getCode(),ResultEnum.result_3.getDesc());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("UserInfoServiceImpl   update");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ResultUtil.success();
    }
}