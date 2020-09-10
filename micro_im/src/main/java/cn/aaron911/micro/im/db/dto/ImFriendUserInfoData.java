package cn.aaron911.micro.im.db.dto;

import lombok.Data;

@Data
public class ImFriendUserInfoData {

	/**
	 * 好友ID
	 */
	public Long id;

	/**
	 * 好友昵称
	 */
	public String username;

	/**
	 * 好友头像
	 */
	public String avatar = "layui/images/0.jpg";

	/**
	 * 签名
	 */
	public String sign;

	/**
	 * 若值为offline代表离线，online或者不填为在线
	 */
	public String status="offline";
}
