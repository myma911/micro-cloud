package cn.aaron911.micro.im.webserver;

import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.aaron911.micro.im.webserver.payload.BroadcastMessageRequest;
import cn.aaron911.micro.im.webserver.payload.GroupMessageRequest;
import cn.aaron911.micro.im.webserver.payload.SingleMessageRequest;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <p>
 * 消息事件处理
 * </p>
 *
 * @description: 消息事件处理
 */
@Component
@Slf4j
public class ImWebSocketMessageUtil {

    @Autowired
    private SocketIOServer server;

    @Autowired
    private DbTemplate dbTemplate;



    /**
     * 单聊
     */
    public void sendToSingle(UUID sessionId, SingleMessageRequest message) {
        server.getClient(sessionId).sendEvent(Event.CHAT, message);
    }

    /**
     * 广播
     */
    public void sendToBroadcast(BroadcastMessageRequest message) {
        log.info("系统紧急广播一条通知：{}", message.getMessage());
        for (UUID clientId : dbTemplate.findAll()) {
            if (server.getClient(clientId) == null) {
                continue;
            }
            server.getClient(clientId).sendEvent(Event.BROADCAST, message);
        }
    }

    /**
     * 广播
     */
    public void sendToBroadcast(MessageProto messageProto) {
        log.info("系统紧急广播一条通知：{}", messageProto.getMessageBody().getContent());
        for (UUID clientId : dbTemplate.findAll()) {
            if (server.getClient(clientId) == null) {
                continue;
            }
            server.getClient(clientId).sendEvent(Event.BROADCAST, messageProto.getMessageBody().getContent());
        }
    }

    /**
     * 群聊
     */
    public void sendToGroup(GroupMessageRequest message) {
        server.getRoomOperations(message.getGroupId()).sendEvent(Event.GROUP, message);
    }
}
