package com.huihe.eg.cloud.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Service
@FeignClient(name="eg-user")
public interface UserApiService {

    @RequestMapping(value = "/userOrder/queryUser")
    String userOrderQuery(@RequestParam("user_id")Long user_id,@RequestParam("type_id")Long type_id,@RequestParam("type")String type,@RequestParam("source")String source);
    @RequestMapping(value = "/curriculumRecord/insertCurriculum")
    void insertCurriculum(@RequestParam("user_id")Long user_id,@RequestParam("type")String type,@RequestParam("group_id")Long group_id,@RequestParam("status")Integer status);
    @RequestMapping(value = "/userInfo/queryIsEstablishGroupClass")
    Boolean queryIsEstablishGroupClass(@RequestParam("user_id")Long user_id);
    @RequestMapping(value = "/masterAppointment/queryIsShow")
    String  queryIsShow(@RequestParam("user_id")Long user_id,@RequestParam("group_id")Long group_id);
    @RequestMapping(value = "/masterAppointment/queryNewAppointment")
    String queryNewAppointment(@RequestParam("group_id")Long group_id);
    @RequestMapping(value = "/masterAppointment/queryNewAppointments")
    String queryNewAppointments(@RequestParam("group_id")Long group_id);
    @RequestMapping(value = "/masterAppointment/queryAppointmentInfo")
    String queryAppointmentInfo(@RequestParam("Aappointment_id")Long Aappointment_id);
    @RequestMapping(value = "/masterAppointment/queryAppointmentCount")
    Integer queryAppointmentCount(@RequestParam("group_id")Long group_id);
    @RequestMapping(value = "/masterAppointment/updateRecording")
    void updateRecording(@Param("id")Long id);

    @RequestMapping(value = "/masterAppointment/queryIsShowClose")
    String  queryIsShowClose(@RequestParam("user_id")Long user_id,@RequestParam("group_id")Long group_id);

    @RequestMapping(value = "/masterAppointment/updateLikeCount")
    void updateLikeCount(@Param("id")Long id);
    @RequestMapping(value = "/masterAppointment/updateLikeCountCancel")
    void updateLikeCountCancel(@Param("id")Long id);
    @RequestMapping(value = "/masterAppointment/updateShareCount")
    void updateShareCount(@Param("id")Long id);
    @RequestMapping(value = "/masterComment/updateCommentLikeCount")
    void updateCommentLikeCount(@Param("id")Long id);
    @RequestMapping(value = "/masterComment/updateCommentLikeCountCancel")
    void updateCommentLikeCountCancel(@Param("id")Long history_id);
    @RequestMapping(value = "/masterComment/updateCommentShareCount")
    void updateCommentShareCount(Long history_id);

    @RequestMapping(value = "/masterSetPrice/queryMasterSetPriceById")
    Object queryMasterSetPriceById(@RequestParam("id")Long id);

    @RequestMapping(value = "/masterSetPriceStream/updateOpenLiving")
    String  updateOpenLiving(@RequestParam("user_id")Long user_id);

    @RequestMapping(value = "/masterSetPriceStream/queryIsLiveStream")
    String  queryIsLiveStream(@RequestParam("user_id")Long user_id,@RequestParam("id")Long id);

    @RequestMapping(value = "/masterSetPriceStream/queryIsLiveStreamClose")
    String  queryIsLiveStreamClose(@RequestParam("id")Long id);
}
