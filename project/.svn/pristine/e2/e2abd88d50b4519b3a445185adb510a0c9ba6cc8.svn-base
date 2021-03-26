package com.huihe.eg.grab.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.grab.model.GrabTaskEntity;
import com.huihe.eg.grab.service.dao.GrabTaskService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="抓取任务可用接口说明",description="抓取任务可用接口说明",tags = {"抓取任务"})
@RestController
@RequestMapping("grabTask")
public class GrabTaskController extends BaseFrameworkController<GrabTaskEntity, Long> {

    @Resource
    private GrabTaskService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}