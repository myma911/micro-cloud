package cn.aaron911.micro.im.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 部门
 *
 */
@Entity
@Table(name="user_department")
@Data
public class UserDepartmentEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 部门人数
	 */
	private Integer count;

	/**
	 * 等级
	 */
	private Integer level;

    /**
     * 上级部门ID
	 */
	private Long parentid;

    /**
     * 备注
	 */
	private String remark;

    /**
     * 是否删除（0否1是）
	 */
	private Integer isdel;


}
