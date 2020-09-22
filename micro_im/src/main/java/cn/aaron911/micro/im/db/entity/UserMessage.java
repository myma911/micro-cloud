package cn.aaron911.micro.im.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_message")
@ApiModel(value="UserMessage对象", description="")
public class UserMessage extends Model<UserMessage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发送人")
    @TableField("senduser")
    private String senduser;

    @ApiModelProperty(value = "接收人")
    @TableField("receiveuser")
    private String receiveuser;

    @ApiModelProperty(value = "群ID")
    @TableField("groupid")
    private String groupid;

    @ApiModelProperty(value = "是否已读 0 未读  1 已读")
    @TableField("isread")
    private Integer isread;

    @ApiModelProperty(value = "类型 0 单聊消息  1 群消息")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "消息内容")
    @TableField("content")
    private String content;

    @TableField("createuser")
    private Long createuser;

    @TableField("createdate")
    private Date createdate;

    @TableField("updatedate")
    private Date updatedate;

    @TableField("avatar")
    private String avatar;

    @TableField("sendusername")
    private String sendusername;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
