package cn.aaron911.micro.qa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 问答中间表实体
 */
@Entity
@Table(name="tb_pl")
@Data
public class Pl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    private String problemid;//问题id
    @Id
    private String lableid;//回复id
}
