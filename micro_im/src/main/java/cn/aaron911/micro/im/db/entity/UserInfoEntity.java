package cn.aaron911.micro.im.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 用户信息表
 *
 */
@Entity
@Table(name="user_info")
@Data
public class UserInfoEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 部门
	 */
	private Long deptid;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别（0女 1男）
	 */
	private Integer sex;

	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 身份证
	 */
	private String cardid;

	/**
	 * 签名
	 */
	private String signature;

	/**
	 * 毕业院校
	 */
	private String school;

	/**
	 * 学历
	 */
	private Integer education;

	/**
	 * 现居住地址
	 */
	private String address;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 个人头像
	 */
	private String profilephoto;
}
