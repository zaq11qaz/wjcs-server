package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.SceneTypeEntity;
import com.huihe.eg.user.service.dao.SceneTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程类别
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="课程类别可用接口说明",description="课程类别可用接口说明",tags = {"课程类别"})
@RestController
@RequestMapping("sceneType")
public class SceneTypeController extends BaseFrameworkController<SceneTypeEntity, Long> {

    @Resource
    private SceneTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}