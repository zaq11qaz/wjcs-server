package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserLookEntity;
import com.huihe.eg.user.service.dao.UserLookService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查看用户纪录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="查看用户纪录可用接口说明",description="查看用户纪录可用接口说明",tags = {"查看用户纪录"})
@RestController
@RequestMapping("userLook")
public class UserLookController extends BaseFrameworkController<UserLookEntity, Long> {

    @Resource
    private UserLookService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}