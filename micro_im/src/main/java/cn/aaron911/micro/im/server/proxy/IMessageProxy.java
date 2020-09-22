package cn.aaron911.micro.im.server.proxy;

import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageProto;

public interface IMessageProxy {

    /**
     * 转换 消息为包装类
     * @param sessionId
     * @param message
     * @return
     */
    MessageWrapper convertToMessageWrapper(String sessionId, MessageProto message);

    /**
     * 保存在线消息
     * @param message
     */
    void  saveOnlineMessageToDB(MessageWrapper message);

    /**
     * 保存离线消息
     * @param message
     */
    void  saveOfflineMessageToDB(MessageWrapper message);

    /**
     * 获取上线状态消息
     * @param sessionId
     * @return
     */
    MessageProto  getOnLineStateMsg(String sessionId);

    /**
     * 重连状态消息
     * @param sessionId
     * @return
     */
    MessageWrapper  getReConnectionStateMsg(String sessionId);
    
    /**
     * 获取下线状态消息
     * @param sessionId
     * @return
     */
    MessageProto  getOffLineStateMsg(String sessionId);
}

