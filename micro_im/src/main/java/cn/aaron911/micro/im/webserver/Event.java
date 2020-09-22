package cn.aaron911.micro.im.webserver;

/**
 * <p>
 * 事件常量
 * </p>
 *
 * @description: 事件常量
 */
public interface Event {
    /**
     * 聊天事件
     */
    String CHAT = "chat" ;

    /**
     * 广播消息
     */
    String BROADCAST = "broadcast" ;

    /**
     * 群聊
     */
    String GROUP = "group" ;

    /**
     * 加入群聊
     */
    String JOIN = "join" ;

}
