package cn.aaron911.micro.friend.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
@Data
public class NoFriend implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    private String userid;
    @Id
    private String friendid;

}
