package com.huihe.eg.message.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.result.ResultEnum;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.SystemEum;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.comm.push.PushMessageParam;
import com.huihe.eg.message.model.SystemVersionEntity;
import com.huihe.eg.message.mybatis.dao.SystemVersionMapper;
import com.huihe.eg.message.service.dao.SystemVersionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemVersionServiceImpl extends BaseFrameworkServiceImpl<SystemVersionEntity, Long> implements SystemVersionService {

    @Resource
    private SystemVersionMapper mapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultParam versionIteration(SystemVersionEntity param, HttpServletRequest request, HttpServletResponse response) {
        boolean aBoolean = false;
        Map<String, Object> map = new HashMap<>();
        try {
            if (param != null && param.getVersion() != null) {
                SystemVersionEntity systemVersionEntity = new SystemVersionEntity();
                systemVersionEntity.setPlatform(param.getPlatform());
                systemVersionEntity.setPageSize(1);
                List<SystemVersionEntity> systemVersionEntities = mapper.queryListPage(systemVersionEntity);
                if (systemVersionEntities != null && systemVersionEntities.size() > 0) {
                    systemVersionEntity = systemVersionEntities.get(0);

                    String newSum = param.getVersion().replaceAll("\\.", "");
                    String odlSum = systemVersionEntity.getVersion().replaceAll("\\.", "");

                    if (newSum.compareTo(odlSum) > 0) {
                        systemVersionEntity.setVersion(param.getVersion());
                        mapper.updateByPrimaryKeySelective(systemVersionEntity);
                        PushMessageParam pushMessageParam = new PushMessageParam();
                        pushMessageParam.setType_id(param.getId());
                        pushMessageParam.setPush_type("systemUpdate");
                        pushMessageParam.setOpera_type("systemUpdate");
                        pushMessageParam.setContent("系统版本更新至" + param.getVersion());
                        pushMessageParam.setType("systemUpdate");
                        if (param.getPlatform().contains("ios")) {
                            pushMessageParam.setSource("2");
                        } else {
                            pushMessageParam.setSource("1");
                        }
                        pushMessageParam.setTitle("系统版本更新，请前往升级");
                        pushMessageParam.setIs_teach_paypal(param.getIs_teach_paypal());
                        rabbitTemplate.convertAndSend("systemUpdate", pushMessageParam);
                    } else if (newSum.compareTo(odlSum) == 0) {

                    } else {
                        aBoolean = true;
                        map.put("new_info", systemVersionEntity);
                    }
                } else {
                    return ResultUtil.error(SystemEum.system_20008.getCode(), SystemEum.system_20008.getDesc());
                }
            } else {
                return ResultUtil.error(UserEum.user_10029.getCode(), UserEum.user_10029.getDesc());
            }
            map.put("is_iteration", aBoolean);
        } catch (
                Exception e) {
            e.printStackTrace();
            logger.info("SystemVersionServiceImpl versionIteration");
        }
        return ResultUtil.success(map);
    }
}