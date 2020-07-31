package cn.aaron911.micro.article.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.article.dao.CommentDao;
import cn.aaron911.micro.article.pojo.Comment;
import cn.aaron911.micro.common.util.IdWorker;

/**
 * 
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    public List<Comment> findByArticleid(String articleid) {
        return commentDao.findByArticleid(articleid);
    }

    /**
     * 根据评论id删除评论
     * @param commentid
     * @return Object
     */
	public void deleteByCommentid(String commentid) {
		commentDao.deleteById(commentid);
	}
}
