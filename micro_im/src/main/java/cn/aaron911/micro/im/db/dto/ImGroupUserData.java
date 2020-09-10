package cn.aaron911.micro.im.db.dto;

import lombok.Data;

@Data
public class ImGroupUserData {

	/**
	 * 分组ID
	 */
	public Long id;

	/**
	 * 分组Name
	 */
	public String groupname;

	public String avatar;

	/**
	 * 分组用户数
	 */
	public String members;

}
