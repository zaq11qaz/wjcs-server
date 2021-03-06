package com.huihe.eg.cloud.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name="eg-message")
public interface MessageApiService {

    @RequestMapping(value = "/noteUser/queryNoteAddCount")
    Integer queryNoteAddCount(@Param("user_id")Long user_id);

    @RequestMapping(value = "/noteComment/queryCommentAddCount")
    Integer queryCommentAddCount(@Param("user_id")Long user_id);
    @RequestMapping(value = "/history/queryNoteInteraction")
    Integer queryNoteInteraction(@Param("user_id")Long user_id);

    /*@RequestMapping(value = "/messageFriend/queryIsFriend")
    String  queryIsFriend(@Param("user_id")Long user_id,@Param("oather_id")Long oather_id);*/
    @RequestMapping(value = "/noteUser/queryNoteInfo")
    String queryNoteInfo(@Param("note_id")Long note_id);
    @RequestMapping(value = "/messageJoinGroup/queryGroupUserInfos")
    String queryGroupUserInfos(@Param("group_id")Long group_id);
    @RequestMapping(value = "/messageGroup/queryGroupInfo")
    String queryGroupInfo(@Param("group_id")Long group_id);
    @RequestMapping(value = "/messageGroup/queryMyClassCount")
    Integer queryMyClassCount(@Param("user_id")Long user_id);
    @RequestMapping(value = "/groupRecording/endCourseNotice")
    void endCourseNotice(@Param("appointment_id")Long appointment_id);//课程结束通知
    @RequestMapping(value = "/groupWhitelisted/queryGroupIds")
    String queryGroupIds(@Param("user_id")Long user_id);//查询白名单群id集合
    @RequestMapping(value = "/groupRecording/queryRecordingByAppointmentId")
    String queryRecordingByAppointmentId(@Param("appointment_id")Long appointment_id);//课程录播数据查询
    @RequestMapping(value = "/groupRecording/updateWatchCount")
    void updateWatchCount(@Param("appointment_id")Long appointment_id);//修改录播观看次数
    @RequestMapping(value = "/history/queryHistoryId")
    Integer queryHistoryId(@Param("id")Long id);//查询note userId
    @RequestMapping(value = "/groupRecording/queryRecordingUrl")
    String queryRecordingUrl(@Param("id")Long id);
    @RequestMapping(value = "/groupRecording/queryRecordingUrlTeachPaypal")
    String queryRecordingUrlTeachPaypal(@RequestParam("appointment_id")Long appointment_id ,@RequestParam("is_teach_paypal")Boolean is_teach_paypal);
    @RequestMapping(value = "/history/queryIsLike")
    Boolean queryIsLike(@RequestParam("user_id")Long user_id, @RequestParam("app_id")Long app_id);
    @RequestMapping(value = "/masterSetPriceGroup/insertMasterGroup")
    Long insertMasterGroup(@RequestParam("id")Long id,@RequestParam("title")String title,@RequestParam("user_id")Long user_id,@RequestParam("face_url")String face_url);

    @RequestMapping(value = "/masterSetPriceGroup/insertEnjoyGroup")
    String insertEnjoyGroup(@RequestParam("id")Long id,@RequestParam("group_id") Long group_id);
}
