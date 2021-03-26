package com.huihe.eg.cloud.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "eg-news")
public interface NewsApiService {

    @RequestMapping(value = "/history/queryCuriosityInteraction")
    Integer queryCuriosityInteraction(@Param("user_id")Long user_id);
    @RequestMapping(value = "/curiosity/queryAddCount")
    Integer queryAddCount(@Param("user_id")Long user_id);
    @RequestMapping(value = "/curiosityViewpoint/queryAddViewpointCount")
    Integer queryAddViewpointCount(@Param("user_id")Long user_id);

    @RequestMapping(value = "/curiositycomment/queryCuriosityById")
    String queryCuriosityById(@Param("id")Long id);

    @RequestMapping(value = "/history/queryHistoryById")
    String queryHistoryById(@Param("id")Long id);

    @RequestMapping(value = "/curiosityViewpoint/queryViewpointById")
    String queryViewpointById(@Param("id")Long id);

}
