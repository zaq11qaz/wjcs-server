package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserAddressEntity;
import com.huihe.eg.user.service.dao.UserAddressService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地理位置
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户地理位置可用接口说明",description="用户地理位置可用接口说明",tags = {"用户地理位置"})
@RestController
@RequestMapping("userAddress")
public class UserAddressController extends BaseFrameworkController<UserAddressEntity, Long> {

    @Resource
    private UserAddressService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}