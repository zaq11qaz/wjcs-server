package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserCommodityEntity;
import com.huihe.eg.user.service.dao.UserCommodityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 猫币商品列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="猫币商品列表可用接口说明",description="猫币商品列表可用接口说明",tags = {"猫币商品列表"})
@RestController
@RequestMapping("userCommodity")
public class UserCommodityController extends BaseFrameworkController<UserCommodityEntity, Long> {

    @Resource
    private UserCommodityService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}