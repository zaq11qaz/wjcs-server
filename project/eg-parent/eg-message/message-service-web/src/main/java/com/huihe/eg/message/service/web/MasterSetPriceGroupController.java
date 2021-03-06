package com.huihe.eg.message.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.message.model.MasterSetPriceGroupEntity;
import com.huihe.eg.message.service.dao.MasterSetPriceGroupService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="商品生成时创建组可用接口说明",description="商品生成时创建组可用接口说明",tags = {"商品生成时创建组"})
@RestController
@RequestMapping("masterSetPriceGroup")
public class MasterSetPriceGroupController extends BaseFrameworkController<MasterSetPriceGroupEntity, Long> {

    @Resource
    private MasterSetPriceGroupService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/14 19:25
     * @ Description：插入用户组记录
     * @ since: JDk1.8
     */
    @RequestMapping("insertMasterGroup")
    @ApiOperation(value = "插入用户组记录")
    @ResponseBody
    public Long insertMasterGroup(Long id,String title , Long user_id,String face_url, HttpServletRequest request, HttpServletResponse response) {
        return service.insertMasterGroup(id,title,user_id,face_url, request, response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/15 10:38
     * @ Description：加入群
     * @ since: JDk1.8
     */
    @RequestMapping("insertEnjoyGroup")
    @ApiOperation(value = "加入群")
    @ResponseBody
    public String insertEnjoyGroup(Long appointment_id,Long group_id, HttpServletRequest request, HttpServletResponse response) {
        return service.insertEnjoyGroup(appointment_id,group_id, request, response);
    }


}