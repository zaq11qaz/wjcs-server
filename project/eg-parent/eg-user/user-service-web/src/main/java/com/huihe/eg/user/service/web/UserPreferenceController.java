package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.UserPreferenceEntity;
import com.huihe.eg.user.service.dao.UserPreferenceService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户偏好
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="用户偏好可用接口说明",description="用户偏好可用接口说明",tags = {"用户偏好"})
@RestController
@RequestMapping("userPreference")
public class UserPreferenceController extends BaseFrameworkController<UserPreferenceEntity, Long> {

    @Resource
    private UserPreferenceService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}