package cn.aaron911.micro.im.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="用户信息表")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("uid")
    private Long uid;

    @ApiModelProperty(value = "部门")
    @TableField("deptid")
    private Long deptid;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "性别（0女 1男）")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private String birthday;

    @ApiModelProperty(value = "身份证")
    @TableField("cardid")
    private String cardid;

    @ApiModelProperty(value = "签名")
    @TableField("signature")
    private String signature;

    @ApiModelProperty(value = "毕业院校")
    @TableField("school")
    private String school;

    @ApiModelProperty(value = "学历")
    @TableField("education")
    private Integer education;

    @ApiModelProperty(value = "现居住地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "个人头像")
    @TableField("profilephoto")
    private String profilephoto;

    @ApiModelProperty(value = "创建时间")
    @TableField("createdate")
    private LocalDateTime createdate;

    @ApiModelProperty(value = "创建人")
    @TableField("createuser")
    private Long createuser;

    @ApiModelProperty(value = "修改时间")
    @TableField("updatedate")
    private LocalDateTime updatedate;

    @ApiModelProperty(value = "修改人")
    @TableField("updateuser")
    private Long updateuser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
