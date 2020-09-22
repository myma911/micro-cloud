package cn.aaron911.micro.im.robot.proxy.impl;

/**
 * 茉莉机器人回复
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.aaron911.micro.im.config.ImProperty;
import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.robot.proxy.IRobotProxy;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


@Slf4j
@ConditionalOnExpression("'${im.robotType}'.equalsIgnoreCase('moli')")
@Component
public class MLRebotProxy implements IRobotProxy {

    @Autowired
    private ImProperty imProperty;


    @Override
    public MessageWrapper botMessageReply(String user, String content) {
        log.info("MLRebot reply user -->" + user + "--mes:" + content);
        String message;
        try {
            //只实现了基础的  具体的请自行修改
            Map<String, Object> param = new HashMap<>();
            param.put("api_key", imProperty.getRobotKey());
            param.put("api_secret", imProperty.getRobotSecret());
            param.put("limit", "5");
            param.put("question", content);
            message = HttpUtil.post(imProperty.getRobotApiUrl(), param, 62000);
            MessageProto messageProto = new MessageProto();
            messageProto.setCmd(Constants.CmdType.MESSAGE);
            messageProto.setMsgtype(Constants.ProtobufType.REPLY);
            //机器人ID
            messageProto.setSender(Constants.ImserverConfig.REBOT_SESSIONID);
            //回复人
            messageProto.setReceiver(user);
            messageProto.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            MessageBodyProto messageBodyProto = new MessageBodyProto();
            messageBodyProto.setContent(message);
            messageProto.setMessageBody(messageBodyProto);
            return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, messageProto);
        } catch (Exception e) {
            log.error("", e);
        }
        MessageProto messageProto = new MessageProto();
        return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, messageProto);
    }
}
