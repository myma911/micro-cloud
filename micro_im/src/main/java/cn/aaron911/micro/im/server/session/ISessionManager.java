package cn.aaron911.micro.im.server.session;

import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.Session;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.ChannelHandlerContext;

import java.util.Set;


/**
 * session 集合管理
 */
public interface ISessionManager {

    /**
     * 添加session
     *
     */
    void addSession(Session session);

    /**
     * 更新session
     *
     */
    void updateSession(Session session);
 

    /**
     * 删除指定session
     *
     */
     void removeSession(String sessionId);

    /**
     * 删除指定session
     *
     */

    /**
     * 根据指定sessionId获取session
     *
     * @param sessionId
     * @return
     */
    Session getSession(String sessionId);

    /**
     * 获取所有的session
     *
     * @return
     */
    Session[] getSessions();

    /**
     * 获取所有的session的id集合
     *
     * @return
     */
    Set<String> getSessionKeys();

    /**
     * 获取所有的session数目
     *
     * @return
     */
    int getSessionCount();

    /**
     * 通过netty 创建的用户连接
     * @param wrapper
     * @param ctx
     * @return
     */
    Session createSession(String userId, MessageWrapper wrapper, ChannelHandlerContext ctx);

    /**
     * 通过websocket创建的用户连接
     * @param userId
     * @param client
     */
    Session createSession(String userId, SocketIOClient client);

    boolean exist(String sessionId) ;

}
