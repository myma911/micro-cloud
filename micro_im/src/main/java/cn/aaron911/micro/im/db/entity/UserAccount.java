package cn.aaron911.micro.im.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户帐号
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_account")
@ApiModel(value="UserAccount对象", description="用户帐号")
public class UserAccount extends Model<UserAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "禁用状态（0 启用  1 禁用）")
    @TableField("disablestate")
    private Integer disablestate;

    @ApiModelProperty(value = "是否删除（0未删除1已删除）")
    @TableField("isdel")
    private Integer isdel;

    @ApiModelProperty(value = "创建日期")
    @TableField("createdate")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField("updatedate")
    private Date updatedate;

    @ApiModelProperty(value = "修改人")
    @TableField("updateuser")
    private Long updateuser;

    @TableField("user_info")
    private Blob userInfo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
