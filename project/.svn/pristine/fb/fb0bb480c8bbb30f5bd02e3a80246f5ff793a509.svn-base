package com.huihe.eg.cloud.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name="eg-authorization")
public interface AuthorizationApiService {

    @RequestMapping(value = "/managerUser/queryManager")
    String queryManager(@Param("id")Long id);

    @RequestMapping(value = "/managerUser/queryManagerId")
    Long queryHelper(@Param("type")String type);
}
