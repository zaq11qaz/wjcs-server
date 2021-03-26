package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.TimeZoneEntity;
import com.huihe.eg.user.service.dao.TimeZoneService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 时区设置列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="时区设置列表可用接口说明",description="时区设置列表可用接口说明",tags = {"时区设置列表"})
@RestController
@RequestMapping("timeZone")
public class TimeZoneController extends BaseFrameworkController<TimeZoneEntity, Long> {

    @Resource
    private TimeZoneService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}