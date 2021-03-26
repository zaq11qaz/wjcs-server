package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.StudyPriceEntity;
import com.huihe.eg.user.service.dao.StudyPriceService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学习卡价格列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="学习卡价格列表可用接口说明",description="学习卡价格列表可用接口说明",tags = {"学习卡价格列表"})
@RestController
@RequestMapping("studyPrice")
public class StudyPriceController extends BaseFrameworkController<StudyPriceEntity, Long> {

    @Resource
    private StudyPriceService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}