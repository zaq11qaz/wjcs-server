package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.cy.framework.util.result.ResultParam;
import com.cy.framework.util.result.ResultUtil;
import com.huihe.eg.comm.UserEum;
import com.huihe.eg.user.model.UserInfoEntity;
import com.huihe.eg.user.model.UserPreferenceEntity;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserPreferenceMapper;
import com.huihe.eg.user.service.dao.UserPreferenceService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferenceServiceImpl extends BaseFrameworkServiceImpl<UserPreferenceEntity, Long> implements UserPreferenceService {

    @Resource
    private UserPreferenceMapper mapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public ResultParam insert(UserPreferenceEntity param, HttpServletRequest request, HttpServletResponse response) {
        ResultParam resultParam = null;
        StringBuilder preference = new StringBuilder();
        try {
            if (StringUtil.isNotEmpty(param.getEntityList())) {
                String str = param.getEntityList();
                List<UserPreferenceEntity> entities = JSONUtils.json2List(str, UserPreferenceEntity.class);
                if (entities != null && entities.size() > 0) {
                    UserPreferenceEntity entitys = new UserPreferenceEntity();
                    entitys.setUser_id(entities.get(0).getUser_id());
                    entitys.setStatus(2);
                    int res = mapper.updateStatus(entitys);
                    if (res < 0) {
                        return ResultUtil.error(1013, "失败");
                    }
                    for (UserPreferenceEntity entity : entities) {
                        resultParam = super.insert(entity, request, response);
                        if (resultParam != null && resultParam.getCode() == 0) {
                            preference.append("/").append(entity.getPreference());
                        }
                    }
                    if (preference.length() > 0) {
                        preference = new StringBuilder(preference.substring(1));
                    }
                    UserInfoEntity userInfoEntity = new UserInfoEntity();
                    userInfoEntity.setUser_id(entities.get(0).getUser_id());
                    userInfoEntity.setPreference(preference.toString());
                    int ret = userInfoMapper.updateByPrimaryKeySelective(userInfoEntity);
                    if (ret < 0) {
                        return ResultUtil.error(UserEum.user_10021.getCode(), UserEum.user_10021.getDesc());
                    }
                } else {
                    return ResultUtil.error(UserEum.user_10022.getCode(), UserEum.user_10022.getDesc());
                }

                return ResultUtil.success("成功");
            } else {
                return super.insert(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UserPreferenceServiceImpl   insert");
        }
        return ResultUtil.error(UserEum.user_10023.getCode(), UserEum.user_10023.getDesc());
    }

}