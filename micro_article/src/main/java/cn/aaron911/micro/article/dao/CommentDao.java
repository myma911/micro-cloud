package cn.aaron911.micro.article.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.aaron911.micro.article.pojo.Comment;

/*
 * 评论数据层
 */
public interface CommentDao extends MongoRepository<Comment, String> {

    /**
     * 根据文章id查询评论列表
     * @param articleid
     * @return
     */
    List<Comment> findByArticleid(String articleid);
}
