package com.huihe.eg.user.service.web;

import com.cy.framework.service.web.BaseFrameworkController;
import com.huihe.eg.user.model.MemberRecordEntity;
import com.huihe.eg.user.service.dao.MemberRecordService;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员提交记录
 *
 * @author zwx
 * @date 2019年11月25日18:01:05
 * @since JDK1.8
 */
@Api(value="会员提交记录可用接口说明",description="会员提交记录可用接口说明",tags = {"会员提交记录"})
@RestController
@RequestMapping("memberRecord")
public class MemberRecordController extends BaseFrameworkController<MemberRecordEntity, Long> {

    @Resource
    private MemberRecordService service;
    @Override
    public void init() {
        setBaseService(service);
    }
}