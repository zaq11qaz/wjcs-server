package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserMemberCardEntity;
import com.huihe.eg.user.service.dao.UserMemberCardService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员时长
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="会员时长可用接口说明",description="会员时长可用接口说明",tags = {"会员时长"})
@RestController
@RequestMapping("userMemberCard")
public class UserMemberCardController extends BaseFrameworkController<UserMemberCardEntity, Long> {

    @Resource
    private UserMemberCardService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}