package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserMemberLevelEntity;
import com.huihe.eg.user.service.dao.UserMemberLevelService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员等级
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="会员等级可用接口说明",description="会员等级可用接口说明",tags = {"会员等级"})
@RestController
@RequestMapping("userMemberLevel")
public class UserMemberLevelController extends BaseFrameworkController<UserMemberLevelEntity, Long> {

    @Resource
    private UserMemberLevelService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}