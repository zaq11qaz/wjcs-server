package com.huihe.eg.authorization.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.authorization.model.ManagerCommentEntity;
import com.huihe.eg.authorization.service.dao.ManagerCommentService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="客服评论表可用接口说明",description="客服评论表可用接口说明",tags = {"客服评论表"})
@RestController
@RequestMapping("managerComment")
public class ManagerCommentController extends BaseFrameworkController<ManagerCommentEntity, Long> {

    @Resource
    private ManagerCommentService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}