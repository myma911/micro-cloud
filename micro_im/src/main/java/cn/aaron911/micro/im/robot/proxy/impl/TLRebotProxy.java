package cn.aaron911.micro.im.robot.proxy.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.aaron911.micro.im.config.ImProperty;
import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.robot.model.RobotMessage;
import cn.aaron911.micro.im.robot.model.RobotMessageArticle;
import cn.aaron911.micro.im.robot.proxy.IRobotProxy;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


/**
 * 图灵机器人回复
 */
@Slf4j
@Component
@ConditionalOnExpression("'${im.robotType}'.equalsIgnoreCase('tuling')")
public class TLRebotProxy implements IRobotProxy {

    @Autowired
    private ImProperty imProperty;

    @Override
    public MessageWrapper botMessageReply(String user, String content) {
        log.info("TLRebot reply user -->" + user + "--mes:" + content);
        String message;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("key", imProperty.getRobotKey());
            param.put("userid", user);
            param.put("info", content);
            message = HttpUtil.post(imProperty.getRobotApiUrl(), param, 62000);
            String msgStr = message.replace("&quot;", "'");
            msgStr = msgStr.replace("\r\n", "<br>");
            msgStr = msgStr.replace("<br />", "");

            RobotMessage msg = JSON.parseObject(msgStr, RobotMessage.class);
            switch (msg.getCode()) {
                case 100000:
                    message = msg.getText();
                    break;
                case 200000:
                    message = msg.getText() + "<a href='" + msg.getUrl() + "' target='_blank;'>" + msg.getUrl() + "</a></br>";
                    break;
                case 302000:
                    List<RobotMessageArticle> sublist = JSON.parseArray(msg.getList(), RobotMessageArticle.class);

                    for (RobotMessageArticle a : sublist) {
                        //message+="a("+a.getDetailurl()+")["+a.getArticle()+"] <br><br>"; //layim聊天窗口回复内容
                        message += "<a href='" + a.getDetailurl() + "' target='_blank;'>" + a.getArticle() + "</a></br>";
                    }
                    break;
                case 308000:
                    List<RobotMessageArticle> sublistOne = JSON.parseArray(msg.getList(), RobotMessageArticle.class);
                    for (RobotMessageArticle a : sublistOne) {
                        //message+="菜名："+a.getName()+"<br>用料："+a.getInfo()+"  <br>详情地址:a("+a.getDetailurl()+")["+a.getDetailurl()+"]      <br><br>";//layim聊天窗口回复内容
                        message += "菜名：" + a.getName() + "<br>用料：" + a.getInfo() + "  <br>详情地址:<a href='" + a.getDetailurl() + "' target='_blank;'>" + a.getDetailurl() + "</a>      </br>";
                    }
                    break;
                default:
                    break;
            }

            MessageProto.Model.Builder result = MessageProto.Model.newBuilder();
            result.setCmd(Constants.CmdType.MESSAGE);
            result.setMsgtype(Constants.ProtobufType.REPLY);
            result.setSender(Constants.ImserverConfig.REBOT_SESSIONID);//机器人ID
            result.setReceiver(user);//回复人
            result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            MessageBodyProto.MessageBody.Builder msgbody = MessageBodyProto.MessageBody.newBuilder();
            msgbody.setContent(message);
            result.setContent(msgbody.build().toByteString());
            return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, result.build());
        } catch (Exception e) {
            log.error("", e);
        }
        return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, null);
    }
}
