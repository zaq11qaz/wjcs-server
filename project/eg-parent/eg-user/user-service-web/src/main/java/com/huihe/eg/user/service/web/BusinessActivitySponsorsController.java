package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.BusinessActivitySponsorsEntity;
import com.huihe.eg.user.service.dao.BusinessActivitySponsorsService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="活动赞助商可用接口说明",description="活动赞助商可用接口说明",tags = {"活动赞助商"})
@RestController
@RequestMapping("businessActivitySponsors")
public class BusinessActivitySponsorsController extends BaseFrameworkController<BusinessActivitySponsorsEntity, Long> {

    @Resource
    private BusinessActivitySponsorsService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}