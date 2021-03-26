package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.CountryLanguageEntity;
import com.huihe.eg.user.service.dao.CountryLanguageService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 国家语言列表
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="国家语言列表可用接口说明",description="国家语言列表可用接口说明",tags = {"国家语言列表"})
@RestController
@RequestMapping("countryLanguage")
public class CountryLanguageController extends BaseFrameworkController<CountryLanguageEntity, Long> {

    @Resource
    private CountryLanguageService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}