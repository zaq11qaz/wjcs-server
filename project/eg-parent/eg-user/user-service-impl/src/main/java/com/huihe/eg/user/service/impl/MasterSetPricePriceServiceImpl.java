package com.huihe.eg.user.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.huihe.eg.user.model.MasterSetPricePriceEntity;
import com.huihe.eg.user.model.UserStudyCardEntity;
import com.huihe.eg.user.mybatis.dao.MasterSetPricePriceMapper;
import com.huihe.eg.user.service.dao.MasterSetPricePriceService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MasterSetPricePriceServiceImpl extends BaseFrameworkServiceImpl<MasterSetPricePriceEntity, Long> implements MasterSetPricePriceService {

    @Resource
    private MasterSetPricePriceMapper mapper;

    public void init() {
        setMapper(mapper);
    }

    @Override
    public BigDecimal queryNeedPay(UserStudyCardEntity param) {
        BigDecimal num = new BigDecimal("0");
        try {
            MasterSetPricePriceEntity masterSetPricePriceEntity = new MasterSetPricePriceEntity();
            masterSetPricePriceEntity.setMaster_set_price_id(param.getStudycard_id());
            masterSetPricePriceEntity.setCourse_num(masterSetPricePriceEntity.getCourse_num());
            masterSetPricePriceEntity.setStatus(2);
            List<MasterSetPricePriceEntity> list = mapper.querypriceListBigger(masterSetPricePriceEntity);
            for (MasterSetPricePriceEntity setPricePriceEntity : list) {
                if (setPricePriceEntity.getUpper_limit() > param.getCourse_num()) {
                    num = setPricePriceEntity.getPrice().multiply(
                            new BigDecimal(setPricePriceEntity.getLower_limit() - setPricePriceEntity.getUpper_limit() + ""));
                }else {
                    num = setPricePriceEntity.getPrice().multiply(
                            new BigDecimal(param.getCourse_num() - setPricePriceEntity.getUpper_limit() + ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
}