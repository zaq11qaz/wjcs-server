package com.huihe.eg.user.service.web.boot;


import com.huihe.eg.user.model.MasterAppointmentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;


@Service("masterAppointmentSearchRepository")
public interface MasterAppointmentSearchRepository extends ElasticsearchRepository<MasterAppointmentEntity,Long> {

}
