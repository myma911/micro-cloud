package cn.aaron911.micro.im.server.proxy.impl;

import java.util.Date;

import cn.aaron911.micro.im.constant.Constants;
import cn.aaron911.micro.im.entity.UserMessageEntity;
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

@Slf4j
@Component
public class MessageProxyImpl implements IMessageProxy {

	@Autowired
	private IRobotProxy rebotProxy;

	@Autowired
	private UserMessageService userMessageServiceImpl;

    public MessageWrapper convertToMessageWrapper(String sessionId , MessageProto.Model message) {
    	
        
        switch (message.getCmd()) {
			case Constants.CmdType.BIND:
				 try {
		            	return new MessageWrapper(MessageWrapper.MessageProtocol.CONNECT, message.getSender(), null,message);
		            } catch (Exception e) {
				 		log.error("MessageProxyImpl.convertToMessageWrapper Constants.CmdType.BIND", e);
		            }
				break;
			case Constants.CmdType.HEARTBEAT:
				try {
	                 return new MessageWrapper(MessageWrapper.MessageProtocol.HEART_BEAT, sessionId,null, null);
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
						  MessageProto.Model.Builder  result = MessageProto.Model.newBuilder(message);
						  result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
						  result.setSender(sessionId);//存入发送人sessionId
						  message =  MessageProto.Model.parseFrom(result.build().toByteArray());
						  //判断消息是否有接收人
						  if(StringUtils.isNotEmpty(message.getReceiver())){
							  //判断是否发消息给机器人
							  if(message.getReceiver().equals(Constants.ImserverConfig.REBOT_SESSIONID)){
								  MessageBodyProto.MessageBody  msg =  MessageBodyProto.MessageBody.parseFrom(message.getContent());
								  return  rebotProxy.botMessageReply(sessionId, msg.getContent());
							  }else{
								  return new MessageWrapper(MessageWrapper.MessageProtocol.REPLY, sessionId,message.getReceiver(), message);
							  }
						  }else if(StringUtils.isNotEmpty(message.getGroupId())){
							  return new MessageWrapper(MessageWrapper.MessageProtocol.GROUP, sessionId, null,message);
						  }else {
							  return new MessageWrapper(MessageWrapper.MessageProtocol.SEND, sessionId, null,message);
						  }
		            } catch (Exception e) {
						log.error("MessageProxyImpl.convertToMessageWrapper Constants.CmdType.MESSAGE", e);
		            }
				break;  
		} 
        return null;
    }

    
 
    @Override
	public void saveOnlineMessageToDB(MessageWrapper message) {
    	try{
    		UserMessageEntity userMessage = convertMessageWrapperToBean(message);
    		if(userMessage!=null){
    			userMessage.setIsread(1);
            	userMessageServiceImpl.save(userMessage);
    		}
    	}catch(Exception e){
    		 log.error("MessageProxyImpl  user "+message.getSessionId()+" send msg to "+message.getReSessionId()+" error");
    		 throw new RuntimeException(e.getCause());
    	}
	}
    
    
    @Override
	public void saveOfflineMessageToDB(MessageWrapper message) {
    	try{
    		UserMessageEntity  userMessage = convertMessageWrapperToBean(message);
    		if(userMessage!=null){
    			userMessage.setIsread(0);
            	userMessageServiceImpl.save(userMessage);
    		}
    	}catch(Exception e){
    		 log.error("MessageProxyImpl  user "+message.getSessionId()+" send msg to "+message.getReSessionId()+" error");
    		 throw new RuntimeException(e.getCause());
    	} 
	}
    
    
    private UserMessageEntity convertMessageWrapperToBean(MessageWrapper message){
    	try{
    		//保存非机器人消息
    		if(!message.getSessionId().equals(Constants.ImserverConfig.REBOT_SESSIONID)){
    			MessageProto.Model  msg =  (MessageProto.Model)message.getBody();
            	MessageBodyProto.MessageBody  msgConten =  MessageBodyProto.MessageBody.parseFrom(msg.getContent());
            	UserMessageEntity  userMessage = new UserMessageEntity();
            	userMessage.setSenduser(message.getSessionId());
            	userMessage.setReceiveuser(message.getReSessionId());
            	userMessage.setContent(msgConten.getContent());
            	userMessage.setGroupid(msg.getGroupId());
            	userMessage.setCreatedate(msg.getTimeStamp());
            	userMessage.setIsread(1);
            	return userMessage;
    		}
    	}catch(Exception e){
    		 throw new RuntimeException(e.getCause());
    	} 
    	return null;
    }

	public void setRebotProxy(RebotProxy rebotProxy) {
		this.rebotProxy = rebotProxy;
	}



	@Override
	public MessageProto.Model getOnLineStateMsg(String sessionId) {
		MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setSender(sessionId);//存入发送人sessionId
		result.setCmd(Constants.CmdType.ONLINE);
		return result.build();
	}



	@Override
	public MessageProto.Model getOffLineStateMsg(String sessionId) {
		MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		result.setSender(sessionId);//存入发送人sessionId
		result.setCmd(Constants.CmdType.OFFLINE);
		return result.build();
	}



	@Override
	public MessageWrapper getReConnectionStateMsg(String sessionId) {
		 MessageProto.Model.Builder  result = MessageProto.Model.newBuilder();
		 result.setTimeStamp(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 result.setSender(sessionId);//存入发送人sessionId
		 result.setCmd(Constants.CmdType.RECON);
		 return  new MessageWrapper(MessageWrapper.MessageProtocol.SEND, sessionId, null,result.build());
	}
}
