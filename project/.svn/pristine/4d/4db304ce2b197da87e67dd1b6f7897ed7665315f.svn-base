package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.news.model.CuriosityClassfiyEntity;
import com.huihe.eg.news.service.dao.CuriosityClassfiyService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="好奇标签可用接口说明",description="好奇标签可用接口说明",tags = {"好奇标签"})
@RestController
@RequestMapping("curiosityclassfiy")
public class CuriosityClassfiyController extends BaseFrameworkController<CuriosityClassfiyEntity, Long> {

    @Resource
    private CuriosityClassfiyService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}