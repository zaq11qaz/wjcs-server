package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.BannerTypeEntity;
import com.huihe.eg.user.mybatis.dao.BannerTypeMapper;
import com.huihe.eg.user.service.dao.BannerTypeService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerTypeServiceImpl extends BaseFrameworkServiceImpl<BannerTypeEntity, Integer> implements BannerTypeService {

    @Resource
    private BannerTypeMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(BannerTypeEntity bannerTypeEntity, HttpServletRequest request, HttpServletResponse response) {
        BannerTypeEntity bannerTypeEntity1 = new BannerTypeEntity();
        bannerTypeEntity1.setType(bannerTypeEntity.getType());
        bannerTypeEntity1.setStatus("2");
        bannerTypeEntity1.setPageSize(1);
        List<BannerTypeEntity> bannerTypeEntities = mapper.queryListPage(bannerTypeEntity1);
        if (bannerTypeEntities!=null&&bannerTypeEntities.size()>0){
            bannerTypeEntity.setId(bannerTypeEntities.get(0).getId());
            return super.update(bannerTypeEntity, request, response);
        }
        return super.insert(bannerTypeEntity, request, response);
    }
}