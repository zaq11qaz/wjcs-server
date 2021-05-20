package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserRecommenderGroupInterlinkEntity;
import com.huihe.eg.user.service.dao.UserRecommenderGroupInterlinkService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="推荐官群组关联表可用接口说明",description="推荐官群组关联表可用接口说明",tags = {"推荐官群组关联表"})
@RestController
@RequestMapping("userRecommenderGroupInterlink")
public class UserRecommenderGroupInterlinkController extends BaseFrameworkController<UserRecommenderGroupInterlinkEntity, Long> {

    @Resource
    private UserRecommenderGroupInterlinkService service;
    @Override
    public void init() {
        setBaseService(service);
    }

}