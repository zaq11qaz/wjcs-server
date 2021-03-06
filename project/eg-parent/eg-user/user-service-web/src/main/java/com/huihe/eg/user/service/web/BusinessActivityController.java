package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.BusinessActivityEntity;
import com.huihe.eg.user.mybatis.dao.MasterSetPriceMapper;
import com.huihe.eg.user.service.dao.BusinessActivityService;
import com.huihe.eg.user.service.web.boot.MasterSetPriceSearchRepository;
import io.swagger.annotations.Api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 活动列表可
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value = "活动列表可用接口说明", description = "活动列表可用接口说明", tags = {"活动列表"})
@RestController
@RequestMapping("businessActivity")
public class BusinessActivityController extends BaseFrameworkController<BusinessActivityEntity, Long> {

    @Resource
    private BusinessActivityService service;
    @Resource
    private MasterSetPriceMapper masterSetPriceMapper;
    @Resource
    private MasterSetPriceSearchRepository masterSetPriceSearchRepository;

    @Override
    public void init() {
        setBaseService(service);
    }


    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/25 15:47
     * @ Description：加入活动
     * @ since: JDk1.8
     */
    @ApiOperation(value = "加入活动")
    @PostMapping("insertActivity")
    public ResultParam insertActivity(@RequestBody BusinessActivityEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = service.insertActivity(param, request, response);
        if (StringUtil.isNotEmpty(param.getMaster_set_price_ids())) {
            String[] stringList = param.getMaster_set_price_ids().split(",");
            for (String s : stringList) {
                masterSetPriceSearchRepository.save(masterSetPriceMapper.selectByPrimaryKey(Long.valueOf(s)));
            }
        }
        if (param.getInsertIds()!=null&&param.getInsertIds().size()>0){
            for (Long insertId : param.getInsertIds()) {
                masterSetPriceSearchRepository.save(masterSetPriceMapper.selectByPrimaryKey((insertId)));
            }
        }
        return resultParam;
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/25 15:47
     * @ Description：删除活动
     * @ since: JDk1.8
     */
    @ApiOperation(value = "删除活动")
    @PostMapping("deleteByActivityInfo")
    public ResultParam deleteByActivityInfo(@RequestBody BusinessActivityEntity param, HttpServletRequest request, HttpServletResponse response) {
        return service.deleteByActivityInfo(param, request, response);
    }


}