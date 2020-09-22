package cn.aaron911.micro.im.constant;

import io.netty.util.AttributeKey;

import com.googlecode.protobuf.format.JsonFormat;

public class Constants {

    public interface WebSite {
        int SUCCESS = 0;
        int ERROR = -1;
    }

    public interface ViewTemplateConfig {
        String template = "pctemplate/";
        String mobiletemplate = "mobiletemplate/";
    }

    public interface NotifyConfig {
        int NOTIFY_FAILURE = 0;
        int NOTIFY_SUCCESS = 1;
        int NOTIFY_NO_SESSION = 2;
    }


    public interface ImserverConfig {
        /**
         * 连接空闲时间(秒)
         */
        int READ_IDLE_TIME = 60;

        /**
         * 发送心跳包循环时间(秒)
         */
        int WRITE_IDLE_TIME = 40;

        /**
         * 心跳响应 超时时间(秒   需大于空闲时间)
         */
        int PING_TIME_OUT = 70;

        /**
         * 最大协议包长度(10k)
         */
        int MAX_FRAME_LENGTH = 1024 * 10;

        int MAX_AGGREGATED_CONTENT_LENGTH = 65536;

        /**
         * 机器人SessionID
         */
        String REBOT_SESSIONID = "0";

        /**
         * websocket标识
         */
        int WEBSOCKET = 1;

        /**
         * socket标识
         */
        int SOCKET = 0;


    }

    /**
     * 2020.9.22 去掉
     */
    @Deprecated
    public interface DWRConfig {
        String DWR_SCRIPT_FUNCTIONNAME = "showMessage";//dwr显示消息的script方法名
        String SS_KEY = "scriptSession_Id";
        String BROWSER = "browser";
        String BROWSER_VERSION = "browserVersion";
        JsonFormat JSONFORMAT = new JsonFormat();
    }

    public interface SessionConfig {
        String SESSION_KEY = "account";
        AttributeKey<String> SERVER_SESSION_ID = AttributeKey.valueOf(SESSION_KEY);
        AttributeKey SERVER_SESSION_HEARBEAT = AttributeKey.valueOf("heartbeat");
    }

    public interface ProtobufType {

        /**
         * 请求
         */
        byte SEND = 1;

        /**
         * 接收
         */
        byte RECEIVE = 2;

        /**
         * 通知
         */
        byte NOTIFY = 3;

        /**
         * 回复
         */
        byte REPLY = 4;
    }

    public interface CmdType {
        /**
         * 绑定
         */
        byte BIND = 1;

        /**
         * 心跳
         */
        byte HEARTBEAT = 2;

        /**
         * 上线
         */
        byte ONLINE = 3;

        /**
         * 下线
         */
        byte OFFLINE = 4;

        /**
         * 消息
         */
        byte MESSAGE = 5;

        /**
         * 重连
         */
        byte RECON = 6;
    }

}
