package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.UserAuctionCommondityEntity;
import com.huihe.eg.mall.service.dao.UserAuctionCommondityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户竞拍到的商品记录可用接口说明",description="用户竞拍到的商品记录可用接口说明",tags = {"用户竞拍到的商品记录"})
@RestController
@RequestMapping("userAuctionCommondity")
public class UserAuctionCommondityController extends BaseFrameworkController<UserAuctionCommondityEntity, Long> {

    @Resource
    private UserAuctionCommondityService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}