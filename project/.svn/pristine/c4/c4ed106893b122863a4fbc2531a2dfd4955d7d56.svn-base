package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.CommodityCouponTypeEntity;
import com.huihe.eg.user.mybatis.dao.CommodityCouponTypeMapper;
import com.huihe.eg.user.service.dao.CommodityCouponTypeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityCouponTypeServiceImpl extends BaseFrameworkServiceImpl<CommodityCouponTypeEntity, Integer> implements CommodityCouponTypeService {

    @Resource
    private CommodityCouponTypeMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(CommodityCouponTypeEntity commodityCouponTypeEntity, HttpServletRequest request, HttpServletResponse response) {
        CommodityCouponTypeEntity commodityCouponTypeEntity1 = new CommodityCouponTypeEntity();
        commodityCouponTypeEntity1.setType(commodityCouponTypeEntity.getType());
        commodityCouponTypeEntity1.setName(commodityCouponTypeEntity.getName());
        commodityCouponTypeEntity1.setPageSize(1);
        List<CommodityCouponTypeEntity> commodityCouponTypeEntities = mapper.queryListPage(commodityCouponTypeEntity1);
        if (commodityCouponTypeEntities!=null&&commodityCouponTypeEntities.size()>0){
            commodityCouponTypeEntity.setId(commodityCouponTypeEntities.get(0).getId());
            return super.update(commodityCouponTypeEntity, request, response);
        }
        return super.insert(commodityCouponTypeEntity, request, response);
    }

}