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
 * 部门
 * </p>
 *
 * @author Aaron
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_department")
@ApiModel(value="UserDepartment对象", description="部门")
public class UserDepartment extends Model<UserDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "部门人数")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "等级")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "上级部门ID")
    @TableField("parentid")
    private Long parentid;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField("createdate")
    private LocalDateTime createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField("updatedate")
    private LocalDateTime updatedate;

    @ApiModelProperty(value = "修改人")
    @TableField("updateuser")
    private Long updateuser;

    @ApiModelProperty(value = "是否删除（0否1是）")
    @TableField("isdel")
    private Integer isdel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
