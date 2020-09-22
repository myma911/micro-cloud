package cn.aaron911.micro.im.webserver;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * WebsocketServer 使用 netty-socketio 实现
 */
@Slf4j
@Component
public class ImWebsocketServer  implements InitializingBean, DisposableBean {

    @Autowired
    private SocketIOServer server;

    @Override
    public void afterPropertiesSet() {
        server.start();
        log.info("websocket 服务器启动成功。。。");
    }


    @Override
    public void destroy() {
        server.stop();
        log.info("websocket 服务器关闭成功。。。");
    }
}
