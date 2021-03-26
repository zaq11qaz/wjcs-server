package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserLikeEntity;
import com.huihe.eg.user.service.dao.UserLikeService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 是否喜欢该用户操作记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="是否喜欢该用户操作记录可用接口说明",description="是否喜欢该用户操作记录可用接口说明",tags = {"是否喜欢该用户操作记录"})
@RestController
@RequestMapping("userLike")
public class UserLikeController extends BaseFrameworkController<UserLikeEntity, Long> {

    @Resource
    private UserLikeService service;
    @Override
    public void init() {
        setBaseService(service);
    }


}