package cn.aaron911.micro.article.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.Data;

/**
 *  文章评论（mongoDB）
 */
@Data
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    private String _id;
    private String articleid;//文章id
    private String content;//评论内容
    private String userid;//评论人id
    private String parentid;//评论id，如果为0，表示文章的顶级评论
    private Date publishdate;//评论日期
}
