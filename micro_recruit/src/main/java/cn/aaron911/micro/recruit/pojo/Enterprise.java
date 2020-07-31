package cn.aaron911.micro.recruit.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 招聘企业实体类
 */
@Entity
@Table(name = "tb_enterprise")
@Data
public class Enterprise implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    private String id;//ID

    private String name;//企业名称
    private String summary;//企业简介
    private String address;//企业地址
    private String labels;//标签列表
    private String coordinate;//坐标
    private String ishot;//是否热门 0：非热门 1：热门
    private String logo;//LOGO
    private Integer jobcount;//职位数
    private String url;//URL
}
