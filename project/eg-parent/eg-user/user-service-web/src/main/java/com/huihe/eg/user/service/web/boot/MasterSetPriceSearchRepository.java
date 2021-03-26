package com.huihe.eg.user.service.web.boot;


import com.huihe.eg.user.model.MasterSetPriceEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service("masterSetPriceSearchRepository")
public interface MasterSetPriceSearchRepository extends ElasticsearchRepository<MasterSetPriceEntity,Long> {

}
