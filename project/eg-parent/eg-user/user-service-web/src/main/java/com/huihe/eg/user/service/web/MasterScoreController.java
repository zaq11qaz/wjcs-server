package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterScoreEntity;
import com.huihe.eg.user.service.dao.MasterScoreService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 助学师评分列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="助学师评分列表可用接口说明",description="助学师评分列表可用接口说明",tags = {"助学师评分列表"})
@RestController
@RequestMapping("masterScore")
public class MasterScoreController extends BaseFrameworkController<MasterScoreEntity, Long> {

    @Resource
    private MasterScoreService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}