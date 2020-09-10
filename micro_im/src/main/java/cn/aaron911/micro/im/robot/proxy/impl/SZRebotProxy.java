package cn.aaron911.micro.im.robot.proxy.impl;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.robot.proxy.IRobotProxy;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import java.util.Date;


/**
 * 傻子机器人回复
 */
@Slf4j
@ConditionalOnExpression("!'${im.robotType}'.equalsIgnoreCase('moli') && !'${im.robotType}'.equalsIgnoreCase('tuling')")
@Component
public class SZRebotProxy implements IRobotProxy {

    @Override
    public MessageWrapper botMessageReply(String user, String content) {
        log.info("SZRebot reply user -->" + user + "--mes:" + content);
        try {
            String message = god(content);
            MessageProto.Model.Builder result = MessageProto.Model.newBuilder();
            result.setCmd(Constants.CmdType.MESSAGE);
            result.setMsgtype(Constants.ProtobufType.REPLY);
            result.setSender(Constants.ImserverConfig.REBOT_SESSIONID);
            result.setReceiver(user);
            result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            MessageBodyProto.MessageBody.Builder msgbody = MessageBodyProto.MessageBody.newBuilder();
            msgbody.setContent(message);
            result.setContent(msgbody.build().toByteString());
            return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, result.build());
        } catch (Exception e) {
            log.error("", e);
        }
        MessageProto.Model.Builder result = MessageProto.Model.newBuilder();
        return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, Constants.ImserverConfig.REBOT_SESSIONID, user, result.build());
    }


    private static String god(String content) {
        content = content.replaceAll("吗", "");
        content = content.replaceAll("?", "!");
        return content;
    }
}
