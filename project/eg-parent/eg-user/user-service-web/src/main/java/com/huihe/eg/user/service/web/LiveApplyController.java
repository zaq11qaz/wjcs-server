package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.LiveApplyEntity;
import com.huihe.eg.user.service.dao.LiveApplyService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：直播招募
 * @ since: JDk1.8
 */
@Api(value="直播招募可用接口说明",description="直播招募可用接口说明",tags = {"直播招募"})
@RestController
@RequestMapping("liveApply")
public class LiveApplyController extends BaseFrameworkController<LiveApplyEntity, Long> {

    @Resource
    private LiveApplyService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}