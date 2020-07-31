package cn.aaron911.micro.qa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 问题回复实体
 *
 */
@Entity
@Table(name="tb_reply")
@Data
public class Reply implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;//编号
	private String problemid;//问题ID
	private String content;//回答内容
	private java.util.Date createtime;//创建日期
	private java.util.Date updatetime;//更新日期
	private String userid;//回答人ID
	private String nickname;//回答人昵称
}
