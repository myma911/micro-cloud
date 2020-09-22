package cn.aaron911.micro.im.server.model;

import cn.aaron911.micro.im.constant.Constants;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.NotBlank;


public class Session  implements Serializable{

	/**
	 * 通过netty连接的用户会话
	 */
	private transient Channel channel;

	/**
	 * 通过websocket连接的用户会话
	 */
	private SocketIOClient socketIOClient;

	/**
	 * session在本台服务器上的ID
	 * 通常使用 sessionid
	 */
	@NotBlank
	private String nid;

	/**
	 * 来源 用于区分是websocket\socket
	 */
	private int source;

	/**
	 * 客户端ID  (设备号码+应用包名),ios为devicetoken
	 */
	private String deviceId;

	/**
	 * session绑定的服务器IP
	 */
	private String host;

	/**
	 * session绑定的账号
	 * 可能是userid
	 */
	@NotBlank
	private String account;

	/**
	 * 终端类型
	 */
	private String platform;

	/**
	 * 终端版本号
	 */
	private String platformVersion;

	/**
	 * 客户端key
	 */
	private String appKey;

	/**
	 * 登录时间
	 */
	private Long bindTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 维度
	 */
	private Double latitude;

	/**
	 * 位置
	 */
	private String location;

	/**
	 * 状态
	 */
	private int status;


	public Session(Channel channel) {
		this.channel = channel;
		this.nid = channel.id().asShortText();
	}

	public Session(SocketIOClient socketIOClient) {
		this.socketIOClient = socketIOClient;
		this.nid = socketIOClient.getSessionId().toString();
	}


	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		setAttribute("updateTime", updateTime);
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		setAttribute(Constants.SessionConfig.SESSION_KEY, account);
	}
 

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		setAttribute("longitude", longitude);
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		setAttribute("latitude", latitude);
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		setAttribute("location", location);
		this.location = location;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getDeviceId() {
		return deviceId;
	}
 
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
		
		setAttribute("deviceId", deviceId);
	}

	public String getHost() {
		return host;
	}

	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	    setAttribute("bindTime", bindTime);
	}

	public void setHost(String host) {
		this.host = host;
		 
		setAttribute("host", host);
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
 
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		setAttribute("status", status);
	}
	
	public Channel getChannel() {
		return channel;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
		setAttribute("platform", platform);
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
		setAttribute("platformVersion", platformVersion);
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
		setAttribute("appKey", appKey);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
		setAttribute("sign", sign);
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
		setAttribute("source", source);
	}

	public void setAttribute(String key, Object value) {
		if(channel!=null){
			channel.attr(AttributeKey.valueOf(key)).set(value);
		}
	}

	public boolean containsAttribute(String key) {
		if(channel!=null){
			return channel.hasAttr(AttributeKey.valueOf(key));
		}
		return false;
	}
	
	public Object getAttribute(String key) {
		if(channel!=null){
			return channel.attr(AttributeKey.valueOf(key)).get();
		}
		return null;
	}

	public void removeAttribute(String key) {
		if(channel!=null){
			channel.attr(AttributeKey.valueOf(key)).set(null);;
		}
	}

	public SocketAddress getRemoteAddress() {
		if(channel!=null){
			return channel.remoteAddress();
		}
		return null;
	}



	
	public boolean isConnected() {
		if(channel != null){
			return channel.isActive();
		}
		else if(socketIOClient!=null){
			return socketIOClient.isChannelOpen();
		}
		return false;
	}

	public boolean  isLocalhost() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip.equals(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
	}


	public void close() {
		if(channel!=null){
			channel.close();
		}
		else if(socketIOClient!=null){
			socketIOClient.disconnect();
		}
	}


	public void close(String nid) {
		if(getNid().equals(nid)){
			close();
		}
	}
	
	@Override
	public int hashCode(){
		return (deviceId + nid + host).hashCode();
	}

	@Override
	public boolean equals(Object o) {
        
		if(o instanceof Session){
			return hashCode() == o.hashCode();
		}
		return false;
	}

    public boolean fromOtherDevice(Object o) {
		if (o instanceof Session) {
			Session t = (Session) o;
			if(t.deviceId!=null && deviceId!=null) {
				return !t.deviceId.equals(deviceId);
			} 
		}  
		return false;
	}

    public boolean fromCurrentDevice(Object o) {
		return !fromOtherDevice(o);
	}

	@Override
	public String  toString(){
		return  JSON.toJSONString(Session.this);
	}







	public  boolean write(Object msg) {
		channel.write(msg);
		return false;
	}
}