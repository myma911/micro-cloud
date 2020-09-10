package cn.aaron911.micro.im.db.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImFriendUserData {

	/**
	 * 分组ID
	 */
	public Long id;

	/**
	 * 好友分组Name
	 */
	public String groupname;

	/**
	 * 分组好友列表
	 */
	public List<ImFriendUserInfoData> list;
}
