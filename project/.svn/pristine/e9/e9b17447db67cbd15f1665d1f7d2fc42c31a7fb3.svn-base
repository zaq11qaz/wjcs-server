package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserSchoolEntity;
import com.huihe.eg.user.service.dao.UserSchoolService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户学校经历
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户学校经历可用接口说明",description="用户学校经历可用接口说明",tags = {"用户学校经历"})
@RestController
@RequestMapping("userSchool")
public class UserSchoolController extends BaseFrameworkController<UserSchoolEntity, Long> {

    @Resource
    private UserSchoolService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}