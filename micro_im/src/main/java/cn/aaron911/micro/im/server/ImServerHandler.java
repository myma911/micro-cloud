package cn.aaron911.micro.im.server;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.server.connector.ImConnertor;
import cn.aaron911.micro.im.server.model.MessageWrapper;
import cn.aaron911.micro.im.server.model.proto.MessageProto;
import cn.aaron911.micro.im.server.proxy.IMessageProxy;
import cn.aaron911.micro.im.util.ImUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

@Slf4j
@Sharable
public class ImServerHandler  extends ChannelInboundHandlerAdapter{
    
    private IMessageProxy proxy;
    private ImConnertor connertor;

    public ImServerHandler(IMessageProxy proxy, ImConnertor connertor) {
        this.connertor = connertor;
        this.proxy = proxy;
    }

    /**
     * 用户事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
    	 String sessionId = ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_ID).get();
    	//发送心跳包
    	if (o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.WRITER_IDLE)) {
		      //ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(System.currentTimeMillis());
			  if(StringUtils.isNotEmpty(sessionId)){
				 MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
				 builder.setCmd(Constants.CmdType.HEARTBEAT);
			     builder.setMsgtype(Constants.ProtobufType.SEND);
				 ctx.channel().writeAndFlush(builder);
			  } 
 			 log.debug(IdleState.WRITER_IDLE +"... from "+sessionId+"-->"+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
 	    } 
	        
	    //如果心跳请求发出70秒内没收到响应，则关闭连接
	    if ( o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.READER_IDLE)){
			log.debug(IdleState.READER_IDLE +"... from "+sessionId+" nid:" +ctx.channel().id().asShortText());
	    	Long lastTime = (Long) ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).get();
	    	Long currentTime = System.currentTimeMillis();
	    	if(lastTime == null || ( (currentTime - lastTime)/1000 >= Constants.ImserverConfig.PING_TIME_OUT)) {
	     		connertor.close(ctx);
	     	}
	     	//ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(null);
	    }
	}

    /**
     * 读取消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {
        try {
            if (o instanceof MessageProto.Model) {
                MessageProto.Model message = (MessageProto.Model) o;
                String sessionId = connertor.getChannelSessionId(ctx);
                // inbound
                if (message.getMsgtype() == Constants.ProtobufType.SEND) {
                	ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(System.currentTimeMillis());
                    MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
                    if (wrapper != null){
                        receiveMessages(ctx, wrapper);
                    }
                }
                // outbound
                if (message.getMsgtype() == Constants.ProtobufType.REPLY) {
                	MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
                	if (wrapper != null){
                        receiveMessages(ctx, wrapper);
                    }
                }
            } else {
                log.warn("ImServerHandler 读取消息异常");
            }
        } catch (Exception e) {
            log.error("ImServerHandler 读取消息错误", e);
            throw e;
        }
    }


    /**
     * 消息读取完成
     *
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)  {
        System.out.println("消息读取完成");
    }



    /**
     * 注册
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	log.info("ImServerHandler  join from "+ ImUtils.getRemoteAddress(ctx)+" nid:" + ctx.channel().id().asShortText());
    }

    /**
     * 注销
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("ImServerHandler Disconnected from {" +ctx.channel().remoteAddress()+"--->"+ ctx.channel().localAddress() + "}");
    }

    /**
     * 激活
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("ImServerHandler channelActive from (" + ImUtils.getRemoteAddress(ctx) + ")");
    }

    /**
     * 断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.debug("ImServerHandler channelInactive from (" + ImUtils.getRemoteAddress(ctx) + ")");
        String sessionId = connertor.getChannelSessionId(ctx);
        receiveMessages(ctx,new MessageWrapper(MessageWrapper.MessageProtocol.CLOSE, sessionId,null, null));  
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("ImServerHandler (" + ImUtils.getRemoteAddress(ctx) + ") -> Unexpected exception from downstream." + cause);
    }

    /**
     * to send  message
     *
     * @param hander
     * @param wrapper
     */
    private void receiveMessages(ChannelHandlerContext hander, MessageWrapper wrapper) {
    	//设置消息来源为socket
    	wrapper.setSource(Constants.ImserverConfig.SOCKET);
        if (wrapper.isConnect()) {
       	    connertor.connect(hander, wrapper); 
        } else if (wrapper.isClose()) {
        	connertor.close(hander,wrapper);
        } else if (wrapper.isHeartbeat()) {
        	connertor.heartbeatToClient(hander,wrapper);
        }else if (wrapper.isGroup()) {
        	connertor.pushGroupMessage(wrapper);
        }else if (wrapper.isSend()) {
        	connertor.pushMessage(wrapper);
        } else if (wrapper.isReply()) {
        	connertor.pushMessage(wrapper.getSessionId(),wrapper);
        }  
    }
}
