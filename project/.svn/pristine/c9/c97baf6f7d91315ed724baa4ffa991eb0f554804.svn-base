package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserGiftEntity;
import com.huihe.eg.user.service.dao.UserGiftService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 礼物列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="礼物列表可用接口说明",description="礼物列表可用接口说明",tags = {"礼物列表"})
@RestController
@RequestMapping("userGift")
public class UserGiftController extends BaseFrameworkController<UserGiftEntity, Long> {

    @Resource
    private UserGiftService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}