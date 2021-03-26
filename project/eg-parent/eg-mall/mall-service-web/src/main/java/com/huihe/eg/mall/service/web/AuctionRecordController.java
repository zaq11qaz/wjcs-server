package com.huihe.eg.mall.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.mall.model.AuctionRecordEntity;
import com.huihe.eg.mall.service.dao.AuctionRecordService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value="用户竞拍记录可用接口说明",description="用户竞拍记录可用接口说明",tags = {"用户竞拍记录"})
@RestController
@RequestMapping("auctionRecord")
public class AuctionRecordController extends BaseFrameworkController<AuctionRecordEntity, Long> {

    @Resource
    private AuctionRecordService service;
    @Override
    public void init() {
        setBaseService(service);
    }
    @ApiOperation(value = "竞拍", httpMethod = "POST", notes = "竞拍")
    @PostMapping("auction")
    public ResultParam auction(@RequestBody  AuctionRecordEntity param, HttpServletRequest request, HttpServletResponse response) {
        init();
        return service.auction(param,request,response);
    }
}