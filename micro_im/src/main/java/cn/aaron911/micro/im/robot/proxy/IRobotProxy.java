package cn.aaron911.micro.im.robot.proxy;

import cn.aaron911.micro.im.server.model.MessageWrapper;

public  interface  IRobotProxy {
	/**
	 * 机器人回复
	 * @param user  用于区分回复谁，机器人接口短暂记忆
	 * @param content
	 * @return
	 */
	 MessageWrapper botMessageReply(String user, String content);
}
