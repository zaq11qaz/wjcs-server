package com.huihe.eg.news.service.web.boot;


import com.huihe.eg.news.model.BrowesHistoryEntity;
import com.huihe.eg.news.service.dao.BrowesHistoryService;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadBrowes implements Runnable{

    private BrowesHistoryEntity browesHistoryEntity;
    private BrowesHistoryService browesHistoryService;
    private HttpServletRequest hrequest;
    private HttpServletResponse hresponse;

    public void init(BrowesHistoryEntity param, BrowesHistoryService service,HttpServletRequest request, HttpServletResponse response) {
        browesHistoryEntity = param;
        browesHistoryService = service;
        hrequest=request;
        hresponse=response;

    }
    @Override
    public void run() {
        browesHistoryService.insert(browesHistoryEntity,hrequest,hresponse);
    }
}
