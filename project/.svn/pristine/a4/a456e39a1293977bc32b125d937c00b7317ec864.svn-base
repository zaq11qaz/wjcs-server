package com.huihe.eg.news.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.news.model.NewsInformationInfoEntity;
import com.huihe.eg.news.mybatis.dao.NewsInformationInfoMapper;
import com.huihe.eg.news.service.dao.NewsInformationInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class NewsInformationInfoServiceImpl extends BaseFrameworkServiceImpl<NewsInformationInfoEntity, Long> implements NewsInformationInfoService {

    @Resource
    private NewsInformationInfoMapper mapper;
    @Override
    public void init() {
        setMapper(mapper);
    }
}