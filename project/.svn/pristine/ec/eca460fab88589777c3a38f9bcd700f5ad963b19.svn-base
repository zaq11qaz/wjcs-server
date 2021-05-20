package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserRecommenderGroupEntity;
import com.huihe.eg.user.service.dao.UserRecommenderGroupService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

@Api(value="推荐官群组可用接口说明",description="推荐官群组可用接口说明",tags = {"推荐官群组"})
@RestController
@RequestMapping("userRecommenderGroup")
public class UserRecommenderGroupController extends BaseFrameworkController<UserRecommenderGroupEntity, Long> {

    @Resource
    private UserRecommenderGroupService service;
    @Override
    public void init() {
        setBaseService(service);
    }

}