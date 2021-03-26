package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.CouponListEntity;
import com.huihe.eg.user.model.ExhibitionPicEntity;
import com.huihe.eg.user.service.dao.ExhibitionPicService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告banner图片信息
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="广告banner图片信息可用接口说明",description="广告banner图片信息可用接口说明",tags = {"广告banner图片信息"})
@RestController
@RequestMapping("exhibitionPic")
public class ExhibitionPicController extends BaseFrameworkController<ExhibitionPicEntity, Long> {

    @Resource
    private ExhibitionPicService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * 新增点击计数
     * @author : zwy
     * 2020-08-18 04:41
     * @since JDK1.8
     */
    @ApiOperation(value = "更新点击次数")
    @PostMapping("updateClickCount")
    public ResultParam updateClickCount(ExhibitionPicEntity param, HttpServletRequest request, HttpServletResponse response){
        return service.updateClickCount(param,request,response);
    }

}