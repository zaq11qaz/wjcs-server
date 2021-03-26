package com.huihe.eg.message.service.web.boot;


import com.huihe.eg.message.model.MessageGroupEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service("messageGroupSearchRepository")
public interface MessageGroupSearchRepository extends ElasticsearchRepository<MessageGroupEntity,Long> {

}
