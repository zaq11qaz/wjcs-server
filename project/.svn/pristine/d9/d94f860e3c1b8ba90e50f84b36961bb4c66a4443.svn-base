package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.OrderTypeLogoEntity;
import com.huihe.eg.user.service.dao.OrderTypeLogoService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * type图标
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="type图标可用接口说明",description="type图标可用接口说明",tags = {"type图标"})
@RestController
@RequestMapping("orderTypeLogo")
public class OrderTypeLogoController extends BaseFrameworkController<OrderTypeLogoEntity, Long> {

    @Resource
    private OrderTypeLogoService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}