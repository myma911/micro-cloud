package cn.aaron911.micro.im.server.session.impl;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.server.group.ImChannelGroup;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.Session;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.aaron911.micro.im.server.proxy.IMessageProxy;
import cn.aaron911.micro.im.server.session.ISessionManager;
import cn.aaron911.micro.im.webserver.ImWebSocketMessageUtil;
import cn.hutool.core.collection.CollUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Session会话管理实现类
 */
@Slf4j
@Component
public class SessionManagerImpl implements ISessionManager {

    /**
     * 保存当前所有会话session
     * key sessionid
     * value Session
     */
    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 保存当前用户登录的所有会话id
     * key userid
     * value sessionid的set集合
     */
    private Map<String, Set<String>> userMap = new ConcurrentHashMap<>();


    @Autowired
    private IMessageProxy proxy;

    @Autowired
    private ImWebSocketMessageUtil webSocketMessageUtil;


    @Override
    public synchronized void addSession(Session session) {
        if (null == session) {
            return;
        }
        sessionMap.put(session.getNid(), session);
        final String userid = session.getAccount();
        Set<String> sessionidSet = userMap.get(userid);
        if (CollUtil.isEmpty(sessionidSet)) {
            sessionidSet = new HashSet<>();
            sessionidSet.add(session.getNid());
        }else{
            sessionidSet.add(session.getNid());
        }
        userMap.put(userid, sessionidSet);

        if (session.getSource() == Constants.ImserverConfig.SOCKET) {
            ImChannelGroup.add(session.getChannel());
        }
        //全员发送上线消息
        MessageProto messageProto = proxy.getOnLineStateMsg(session.getAccount());
        ImChannelGroup.broadcast(messageProto);
        webSocketMessageUtil.sendToBroadcast(messageProto);
    }

    @Override
    public synchronized void updateSession(Session session) {
//        session.setUpdateTime(System.currentTimeMillis());
//        sessions.put(session.getAccount(), session);
    }

    /**
     * 根据sessionid 删除会话, 注意：先关闭后删除
     * 1. 根据sessionId 删除sessionMap 的value
     * 2. 根据userid 删除 sessionidSet 中的 sessionid
     * 3. 如果 sessionidSet 为空，则删除userMap的 key
     * 4. 发送离线通知
     */
    @Override
    public synchronized void removeSession(String sessionid) {
        try {
            Session session = getSession(sessionid);
            if (session != null) {
                session.close();
                // * 1. 根据sessionId 删除sessionMap 的value
                sessionMap.remove(sessionid);
                // * 2. 根据userid 删除 sessionidSet 中的 sessionid
                final String userid = session.getAccount();
                final Set<String> sessionidSet = userMap.get(userid);
                if (CollUtil.isNotEmpty(sessionidSet)) {
                    sessionidSet.remove(sessionid);
                }
                //  * 3. 如果 sessionidSet 为空，则删除userMap的 key
                if (CollUtil.isNotEmpty(sessionidSet)) {
                    userMap.put(userid, sessionidSet);
                }else{
                    userMap.remove(userid);
                }
                // * 4. 发送离线通知
                MessageProto messageProto = proxy.getOffLineStateMsg(sessionid);
                ImChannelGroup.broadcast(messageProto);
                webSocketMessageUtil.sendToBroadcast(messageProto);
            }
        } catch (Exception e) {
            log.error("怎么会出错呢", e);
        }
    }



    @Override
    public Session getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public Session[] getSessions() {
        return sessionMap.values().toArray(new Session[sessionMap.size()]);
    }

    @Override
    public Set<String> getSessionKeys() {
        return sessionMap.keySet();
    }

    @Override
    public int getSessionCount() {
        return sessionMap.size();
    }

    /**
     * netty
     * @param wrapper
     * @param ctx
     * @return
     */
    @Override
    public Session createSession(String userId, MessageWrapper wrapper, ChannelHandlerContext ctx) {
        String sessionId = wrapper.getSessionId();
        Session session = sessionMap.get(sessionId);
        if (session != null) {
            //当链接来源不是同一来源或者 是socket链接，踢掉已经登录的session
            if (session.getSource() == Constants.ImserverConfig.SOCKET) {
                // 如果session已经存在则销毁session
                //从广播组清除
                ImChannelGroup.remove(session.getChannel());
                session.close(session.getNid());
                sessionMap.remove(session.getNid());
                log.info("session " + sessionId + " have been closed!");
            } else if (session.getSource() == Constants.ImserverConfig.WEBSOCKET) {
                // 2020.9.22 不同源先不管
            } else  {
                log.info("暂未实现");
            }
        }
        session = setSessionContent(ctx, wrapper, userId);
        addSession(session);
        return session;
    }


    /**
     * SocketIOClient
     * @param userId
     * @param client
     * @return
     */
    @Override
    public Session createSession(String userId, SocketIOClient client) {
        Session socketIOClientSession = new Session(client);
        socketIOClientSession.setAccount(userId);
        socketIOClientSession.setSource(Constants.ImserverConfig.WEBSOCKET);
        //socketIOClientSession.setPlatform((String)scriptSession.getAttribute(Constants.DWRConfig.BROWSER));
        //socketIOClientSession.setPlatformVersion((String)scriptSession.getAttribute(Constants.DWRConfig.BROWSER_VERSION));
        socketIOClientSession.setBindTime(System.currentTimeMillis());
        socketIOClientSession.setUpdateTime(System.currentTimeMillis());

        final Set<String> sessionIdSet = userMap.get(userId);
        // 该用户已经存在会话
        if (CollUtil.isNotEmpty(sessionIdSet)) {
            for(String sessionId: sessionIdSet){
                final Session session = sessionMap.get(sessionId);
                if (session != null) {
                    if (session.getSource() == Constants.ImserverConfig.SOCKET) {
                        //从广播组清除
                        //ImChannelGroup.remove(session.getChannel());

                        // 2020.9.22 不同类型的会话先不管
                    }else if (session.getSource() == Constants.ImserverConfig.WEBSOCKET) {
                        sessionMap.put(sessionId, socketIOClientSession);
                        return socketIOClientSession;
                    }
                }
            }
        }
        addSession(socketIOClientSession);
        return socketIOClientSession;
    }


    /**
     * 设置session内容
     *
     * @param ctx
     * @param wrapper
     * @param userId
     * @return
     */
    private Session setSessionContent(ChannelHandlerContext ctx, MessageWrapper wrapper, String userId) {
        log.info("create new session " + userId + ", ctx -> " + ctx.toString());
        MessageProto model = (MessageProto) wrapper.getBody();
        Session session = new Session(ctx.channel());
        session.setAccount(userId);
        session.setSource(wrapper.getSource());
        session.setAppKey(model.getAppKey());
        session.setDeviceId(model.getDeviceId());
        session.setPlatform(model.getPlatform());
        session.setPlatformVersion(model.getPlatformVersion());
        session.setSign(model.getSign());
        session.setBindTime(System.currentTimeMillis());
        session.setUpdateTime(session.getBindTime());
        return session;
    }


    @Override
    public boolean exist(String sessionId) {
        Session session = getSession(sessionId);
        return session != null ? true : false;
    }


}
