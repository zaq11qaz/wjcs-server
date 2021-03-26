package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.cy.framework.util.result.ResultParam;
import com.huihe.eg.user.model.MechanismClassesEntity;
import com.huihe.eg.user.service.dao.MechanismClassesService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/1/19 14:56
 * @ Description：机构班级
 * @ since: JDk1.8
 */

@Api(value="机构班级可用接口说明",description="机构班级可用接口说明",tags = {"机构班级"})
@RestController
@RequestMapping("mechanismClasses")
public class MechanismClassesController extends BaseFrameworkController<MechanismClassesEntity, Long> {

    @Resource
    private MechanismClassesService service;
    @Override
    public void init() {
        setBaseService(service);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/25 9:48
     * @ Description：班级设置课程
     * @ since: JDk1.8
     */
    @ApiOperation(value = "班级设置课程")
    @PostMapping("insertMechanismCourse")
    private ResultParam insertMechanismCourse(@RequestBody MechanismClassesEntity param , HttpServletRequest request , HttpServletResponse response){
        return service.insertMechanismCourse(param,request,response);
    }

    /**
     * @ Author     ：zwy
     * @ Date       ：2021/1/25 9:49
     * @ Description：合并班级
     * @ since: JDk1.8
     */
    @ApiOperation(value = "合并班级")
    @PostMapping("updateMergerClass")
    private ResultParam updateMergerClass(@RequestBody MechanismClassesEntity param , HttpServletRequest request , HttpServletResponse response){
        return service.updateMergerClass(param,request,response);
    }

}