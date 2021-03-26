package com.huihe.eg.user.service.web.boot;


import com.huihe.eg.user.model.UserInfoEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service("userInfoSearchRepository")
public interface UserInfoSearchRepository extends ElasticsearchRepository<UserInfoEntity,Long> {

}
