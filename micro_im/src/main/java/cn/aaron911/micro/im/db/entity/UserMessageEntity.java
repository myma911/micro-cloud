package cn.aaron911.micro.im.db.entity;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 
 * 用户消息
 *
 */
@Entity
@Table(name="user_info")
@Data
public class UserMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 发送人
     */
	private String senduser;

    /**
     * 发送人昵称或姓名
     */
	private String sendusername;

    /**
     * 发送人头像
     */
	private String avatar = "layui/images/0.jpg";

    /**
     * 接收人
     */
	private String receiveuser;

    /**
     * 群ID
     */
	private String groupid;

    /**
     * 是否已读 0 未读  1 已读
     */
	private Integer isread;

    /**
     * 类型 0 单聊消息  1 群消息
     */
	private Integer type=0;

    /**
     * 消息内容
     */
	private String content;

	/**
	 * 设置：群ID
	 */
	public void setGroupid(String groupid) {
		if(StringUtils.isNotEmpty(groupid)){
			setType(1);
		}
		this.groupid = groupid;
	}
}
