package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.mall.model.AuctionCommondityEntity;
import com.huihe.eg.mall.service.dao.AuctionCommondityService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="竞拍商品记录可用接口说明",description="竞拍商品记录可用接口说明",tags = {"竞拍商品记录"})
@RestController
@RequestMapping("auctionCommondity")
public class AuctionCommondityController extends BaseFrameworkController<AuctionCommondityEntity, Long> {

    @Resource
    private AuctionCommondityService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}