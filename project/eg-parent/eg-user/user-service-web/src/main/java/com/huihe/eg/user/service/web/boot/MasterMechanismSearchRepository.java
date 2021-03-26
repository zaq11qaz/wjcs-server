package com.huihe.eg.user.service.web.boot;


import com.huihe.eg.user.model.MasterMechanismEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;


@Service("masterMechanismSearchRepository")
public interface MasterMechanismSearchRepository extends ElasticsearchRepository<MasterMechanismEntity,Long> {

}
