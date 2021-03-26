package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MasterTypeEntity;
import com.huihe.eg.user.service.dao.MasterTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 助学师类型
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="助学师类型可用接口说明",description="助学师类型可用接口说明",tags = {"助学师类型"})
@RestController
@RequestMapping("masterType")
public class MasterTypeController extends BaseFrameworkController<MasterTypeEntity, Long> {

    @Resource
    private MasterTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}