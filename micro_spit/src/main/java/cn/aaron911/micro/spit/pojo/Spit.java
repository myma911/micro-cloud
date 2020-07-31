package cn.aaron911.micro.spit.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * 吐槽实体类
 */
@Data
public class Spit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    private String _id;
    private String content;//吐槽内容
    private Date publishtime;//发布日期
    private String userid;//发布人id
    private String nickname;//发布人昵称
    private Integer visits;//浏览量
    private Integer thumbup;//点赞数
    private Integer share;//分享数
    private Integer comment;//回复数
    private String state;//是否可见
    private String parentid;//上级id
}
