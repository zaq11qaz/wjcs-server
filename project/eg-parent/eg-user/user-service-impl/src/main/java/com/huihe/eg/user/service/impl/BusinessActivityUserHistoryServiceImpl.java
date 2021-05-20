package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.util.EmojiUtil;
import com.cy.framework.util.StringUtil;
import com.google.common.collect.Maps;
import com.huihe.eg.user.model.BaseMap;
import com.huihe.eg.user.model.BusinessActivityUserHistoryEntity;
import com.huihe.eg.user.model.PayUserInfoEntity;
import com.huihe.eg.user.mybatis.dao.BusinessActivityUserHistoryMapper;
import com.huihe.eg.user.service.dao.BusinessActivityUserHistoryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessActivityUserHistoryServiceImpl extends BaseFrameworkServiceImpl<BusinessActivityUserHistoryEntity, Long> implements BusinessActivityUserHistoryService {

    @Resource
    private BusinessActivityUserHistoryMapper mapper;
    public void init() {
        setMapper(mapper);
    }

    @Override
    public BaseMap queryUserList(BusinessActivityUserHistoryEntity param, HttpServletRequest request, HttpServletResponse response) {
        BaseMap baseMap = new BaseMap();
        try {
            List<PayUserInfoEntity> list =  mapper.queryUserList(param);
            if (list!=null&&list.size()>0){
                for (PayUserInfoEntity payUserInfoEntity : list) {
                    if (StringUtil.isNotEmpty(payUserInfoEntity.getNick_name())) {
                        payUserInfoEntity.setNick_name(EmojiParser.parseToAliases(payUserInfoEntity.getNick_name()));
                        payUserInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(payUserInfoEntity.getNick_name()));
                    }
                }
            }
            baseMap.setRows(list);
            baseMap.setTotal(mapper.queryListPageCount(param));
        }catch (Exception e){
            e.printStackTrace();
        }
        return baseMap;
    }
}