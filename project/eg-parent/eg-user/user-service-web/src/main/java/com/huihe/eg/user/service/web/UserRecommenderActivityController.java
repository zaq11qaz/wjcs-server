package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserRecommenderActivityEntity;
import com.huihe.eg.user.service.dao.UserRecommenderActivityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="推荐官活动管理表可用接口说明",description="推荐官活动管理表可用接口说明",tags = {"推荐官活动管理表"})
@RestController
@RequestMapping("userRecommenderActivity")
public class UserRecommenderActivityController extends BaseFrameworkController<UserRecommenderActivityEntity, Long> {

    @Resource
    private UserRecommenderActivityService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}