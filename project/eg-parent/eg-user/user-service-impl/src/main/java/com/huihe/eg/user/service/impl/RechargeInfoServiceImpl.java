package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.RechargeInfoEntity;
import com.huihe.eg.user.mybatis.dao.RechargeInfoMapper;
import com.huihe.eg.user.service.dao.RechargeInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechargeInfoServiceImpl extends BaseFrameworkServiceImpl<RechargeInfoEntity, Long> implements RechargeInfoService {

    @Resource
    private RechargeInfoMapper mapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(RechargeInfoEntity rechargeInfoEntity, HttpServletRequest request, HttpServletResponse response) {
        RechargeInfoEntity rechargeInfoEntity1 = new RechargeInfoEntity();
        if (rechargeInfoEntity.getUser_id()!=null&&rechargeInfoEntity.getUser_id()!=0){
            rechargeInfoEntity1.setUser_id(rechargeInfoEntity.getUser_id());
        }else {
            rechargeInfoEntity1.setMechanism_id(rechargeInfoEntity.getMechanism_id());
        }
        rechargeInfoEntity1.setPageSize(1);
        List<RechargeInfoEntity> rechargeInfoEntities = mapper.queryListPage(rechargeInfoEntity1);
         if (rechargeInfoEntities != null && rechargeInfoEntities.size() > 0) {
        rechargeInfoEntity1 = rechargeInfoEntities.get(0);
        if (StringUtil.isNotEmpty(rechargeInfoEntity.getWxPay())) {
            rechargeInfoEntity1.setWxPay(rechargeInfoEntity.getWxPay());
        }
        if (StringUtil.isNotEmpty(rechargeInfoEntity.getAliPay())) {
            rechargeInfoEntity1.setIdentity_type(rechargeInfoEntity.getIdentity_type());
            rechargeInfoEntity1.setAliPay(rechargeInfoEntity.getAliPay());
        }
        return super.update(rechargeInfoEntity1, request, response);
         }


        return super.insert(rechargeInfoEntity, request, response);
    }
}