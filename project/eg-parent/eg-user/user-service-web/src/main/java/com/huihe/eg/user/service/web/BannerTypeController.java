package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.BannerTypeEntity;
import com.huihe.eg.user.service.dao.BannerTypeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/5 13:31
 * @ Description：广告位类型
 * @ since: JDk1.8
 */
@Api(value="广告位类型可用接口说明",description="广告位类型可用接口说明",tags = {"广告位类型"})
@RestController
@RequestMapping("bannerType")
public class BannerTypeController extends BaseFrameworkController<BannerTypeEntity, Integer> {

    @Resource
    private BannerTypeService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}