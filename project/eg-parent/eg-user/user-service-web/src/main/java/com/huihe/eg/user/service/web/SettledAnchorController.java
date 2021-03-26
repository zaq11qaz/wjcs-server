package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.SettledAnchorEntity;
import com.huihe.eg.user.service.dao.SettledAnchorService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入驻主播名单
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="入驻主播名单可用接口说明",description="入驻主播名单可用接口说明",tags = {"入驻主播名单"})
@RestController
@RequestMapping("settledAnchor")
public class SettledAnchorController extends BaseFrameworkController<SettledAnchorEntity, Long> {

    @Resource
    private SettledAnchorService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}