package cn.aaron911.micro.im.server.proxy.impl;

import java.util.Date;

import cn.aaron911.micro.im.constant.Constants;

import cn.aaron911.micro.im.db.entity.UserMessage;
import cn.aaron911.micro.im.db.service.IUserMessageService;
import cn.aaron911.micro.im.robot.proxy.IRobotProxy;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageBodyProto;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.aaron911.micro.im.server.proxy.IMessageProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息转换实现类
 */
@Slf4j
@Component
public class MessageProxyImpl implements IMessageProxy {

    @Autowired
    private IRobotProxy rebotProxy;

    @Autowired
    private IUserMessageService userMessageService;

    @Override
    public MessageWrapper convertToMessageWrapper(String sessionId, MessageProto message) {


        switch (message.getCmd()) {
            case Constants.CmdType.BIND:
                try {
                    return new MessageWrapper(MessageWrapper.MessageProtocol.CONNECT, message.getSender(), null, message);
                } catch (Exception e) {
                    log.error("MessageProxyImpl.convertToMessageWrapper Constants.CmdType.BIND", e);
                }
                break;
            case Constants.CmdType.HEARTBEAT:
                try {
                    return new MessageWrapper(MessageWrapper.MessageProtocol.HEART_BEAT, sessionId, null, null);
                } catch (Exception e) {
                    log.error("MessageProxyImpl.convertToMessageWrapper Constants.CmdType.HEARTBEAT", e);
                }
                break;
            case Constants.CmdType.ONLINE:

                break;
            case Constants.CmdType.OFFLINE:

                break;
            case Constants.CmdType.MESSAGE:
                try {
                    message.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    //存入发送人sessionId
                    message.setSender(sessionId);

                    //判断消息是否有接收人
                    if (StringUtils.isNotEmpty(message.getReceiver())) {
                        //判断是否发消息给机器人
                        if (message.getReceiver().equals(Constants.ImserverConfig.REBOT_SESSIONID)) {
                            MessageBodyProto messageBody = message.getMessageBody();
                            return rebotProxy.botMessageReply(sessionId, messageBody.getContent());
                        } else {
                            return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, sessionId, message.getReceiver(), message);
                        }
                    } else if (StringUtils.isNotEmpty(message.getGroupId())) {
                        return new MessageWrapper(MessageWrapper.MessageProtocol.GROUP, sessionId, null, message);
                    } else {
                        return new MessageWrapper(MessageWrapper.MessageProtocol.SEND, sessionId, null, message);
                    }
                } catch (Exception e) {
                    log.error("MessageProxyImpl.convertToMessageWrapper Constants.CmdType.MESSAGE", e);
                }
                break;
            default:
                log.error("暂未实现的指令");
                break;
        }
        return null;
    }


    @Override
    public void saveOnlineMessageToDB(MessageWrapper message) {
        try {
            UserMessage userMessage = convertMessageWrapperToBean(message);
            if (userMessage != null) {
                userMessage.setIsread(1);
                userMessageService.save(userMessage);
            }
        } catch (Exception e) {
            log.error("MessageProxyImpl  user " + message.getSessionId() + " send msg to " + message.getReSessionId() + " error");
            throw new RuntimeException(e.getCause());
        }
    }


    @Override
    public void saveOfflineMessageToDB(MessageWrapper message) {
        try {
            UserMessage userMessage = convertMessageWrapperToBean(message);
            if (userMessage != null) {
                userMessage.setIsread(0);
                userMessageService.save(userMessage);
            }
        } catch (Exception e) {
            log.error("MessageProxyImpl  user " + message.getSessionId() + " send msg to " + message.getReSessionId() + " error");
            throw new RuntimeException(e.getCause());
        }
    }


    private UserMessage convertMessageWrapperToBean(MessageWrapper message) {
        try {
            //保存非机器人消息
            if (!message.getSessionId().equals(Constants.ImserverConfig.REBOT_SESSIONID)) {
                MessageProto msg = (MessageProto) message.getBody();
                MessageBodyProto msgConten = msg.getMessageBody();
                UserMessage userMessage = new UserMessage();
                userMessage.setSenduser(message.getSessionId());
                userMessage.setReceiveuser(message.getReSessionId());
                userMessage.setContent(msgConten.getContent());
                userMessage.setGroupid(msg.getGroupId());
                userMessage.setCreatedate(new Date());
                userMessage.setIsread(1);
                return userMessage;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
        return null;
    }

    @Override
    public MessageProto getOnLineStateMsg(String sessionId) {
        MessageProto result = new MessageProto();
        result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //存入发送人sessionId
        result.setSender(sessionId);
        result.setCmd(Constants.CmdType.ONLINE);
        return result;
    }


    @Override
    public MessageProto getOffLineStateMsg(String sessionId) {
        MessageProto result = new MessageProto();
        result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //存入发送人sessionId
        result.setSender(sessionId);
        result.setCmd(Constants.CmdType.OFFLINE);
        return result;
    }


    @Override
    public MessageWrapper getReConnectionStateMsg(String sessionId) {
        MessageProto result = new MessageProto();
        result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //存入发送人sessionId
        result.setSender(sessionId);
        result.setCmd(Constants.CmdType.RECON);
        return new MessageWrapper(MessageWrapper.MessageProtocol.SEND, sessionId, null, result);
    }
}
