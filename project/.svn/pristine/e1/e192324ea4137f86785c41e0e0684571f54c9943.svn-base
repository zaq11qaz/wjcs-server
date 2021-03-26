package com.huihe.eg.news.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.thread.ThreadPool;
import com.huihe.eg.news.model.BrowesHistoryEntity;
import com.huihe.eg.news.service.dao.BrowesHistoryService;
import com.huihe.eg.news.service.web.boot.ThreadBrowes;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="浏览记录可用接口说明",description="浏览记录可用接口说明",tags = {"浏览记录"})
@RestController
@RequestMapping("browesHistory")
public class BrowesHistoryController extends BaseFrameworkController<BrowesHistoryEntity, Long> {

    @Resource
    private BrowesHistoryService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    public void insertBrowes(BrowesHistoryEntity param, HttpServletRequest request, HttpServletResponse response){
        ThreadBrowes threadBrowes=new ThreadBrowes();
        threadBrowes.init(param,service,request,response);
        ThreadPool threadPool=ThreadPool.getInstance();
        threadPool.execute(threadBrowes);

    }
}