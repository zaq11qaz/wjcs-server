package com.huihe.eg.user.service.web.webSocket;

import com.cy.framework.util.StringUtil;
import com.cy.framework.util.json.JSONUtils;
import com.huihe.eg.comm.util.EmojiUtil;
import com.huihe.eg.user.model.*;
import com.huihe.eg.user.mybatis.dao.RechargeRecordMapper;
import com.huihe.eg.user.mybatis.dao.UserInfoMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderGroupMapper;
import com.huihe.eg.user.mybatis.dao.UserRecommenderMapper;
import com.huihe.eg.user.service.web.RechargeRecordController;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/23 17:01
 * @ Description：
 * @ since: JDk1.8
 */
@Controller
@RequestMapping("/websocket")
@Api(value="购买推送长连接",description="购买推送长连接",tags = {"购买推送长连接"})
public class WebSocketController {

    @Resource//这个注解会从Spring容器拿出Bean
    private SpringWebSocketHandler springWebSocketHandler;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRecommenderGroupMapper userRecommenderGroupMapper;
    @Resource
    private UserRecommenderMapper userRecommenderMapper;

    @GetMapping("/send")
    public String send(String message , HttpServletRequest request, HttpServletResponse response) {
        this.pushNewMessage(Long.parseLong(message));
        return null;
    }

    public void pushNewMessage(Long id) {
        try {
            RechargeRecordEntity rechargeRecordEntity = rechargeRecordMapper.selectByPrimaryKey(id);
            if (rechargeRecordEntity.getFinished()) {
                List<PayUserInfoEntity> payUserInfoEntities = rechargeRecordMapper.queryPayUserList(rechargeRecordEntity);
                PayUserInfoEntity payUserInfoEntity = payUserInfoEntities.get(0);
                if (StringUtil.isNotEmpty(rechargeRecordEntity.getInvite_code())){
                    UserInfoEntity userInfoEntity = userInfoMapper.queryByInviteCode(rechargeRecordEntity.getInvite_code());
                    userInfoEntity.setNick_name(EmojiParser.parseToAliases(userInfoEntity.getNick_name()));
                    userInfoEntity.setNick_name(EmojiUtil.resolveToEmojiFromByte(userInfoEntity.getNick_name()));
                    payUserInfoEntity.setInviteName(userInfoEntity.getNick_name());
                    UserRecommenderEntity userRecommenderEntity = new UserRecommenderEntity();
                    userRecommenderEntity.setUser_id(userInfoEntity.getUser_id());
                    userRecommenderEntity.setType("share_recommender");
                    userRecommenderEntity.setPageSize(1);
                    List<UserRecommenderEntity> list = userRecommenderMapper.queryListPage(userRecommenderEntity);
                    if (list!=null && list.size()>0){
                        userRecommenderEntity = list.get(0);
                        if (userRecommenderEntity.getGroup_id()!=0) {
                            UserRecommenderGroupEntity userRecommenderGroupEntity = userRecommenderGroupMapper.selectByPrimaryKey(userRecommenderEntity.getGroup_id());
                            payUserInfoEntity.setInviteGroupName(userRecommenderGroupEntity.getGroup_name());
                        }
                    }
                }
                SpringWebSocketHandler.sendMessage(payUserInfoEntity.toString());
//                SpringWebSocketHandler.sendMessage(JSONUtils.obj2Map(payUserInfoEntity,null).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
