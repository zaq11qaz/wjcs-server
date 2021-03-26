package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserAlbumEntity;
import com.huihe.eg.user.service.dao.UserAlbumService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相册
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户相册可用接口说明",description="用户相册可用接口说明",tags = {"用户相册"})
@RestController
@RequestMapping("userAlbum")
public class UserAlbumController extends BaseFrameworkController<UserAlbumEntity, Long> {

    @Resource
    private UserAlbumService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}