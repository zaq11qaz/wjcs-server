package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.news.model.NewsInformationInfoEntity;
import com.huihe.eg.news.service.dao.NewsInformationInfoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="资讯可用接口说明",description="资讯可用接口说明",tags = {"资讯"})
@RestController
@RequestMapping("newsInformationInfo")
public class NewsInformationInfoController extends BaseFrameworkController<NewsInformationInfoEntity, Long> {

    @Resource
    private NewsInformationInfoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}