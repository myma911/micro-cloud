package cn.aaron911.micro.im.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户帐号
 *
 */
@Entity
@Table(name="user_message")
@Data
public class UserAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	/**
	 * 帐号
	 */
	private String account;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 禁用状态（0 启用  1 禁用）
	 */
	private Integer disablestate;

	/**
	 * 是否删除（0未删除1已删除）
	 */
	private Integer isdel;

	/**
	 * 用户基本信息
	 */
	private UserInfoEntity userInfo;
}
