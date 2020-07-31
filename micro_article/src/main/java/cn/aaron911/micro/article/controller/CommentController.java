package cn.aaron911.micro.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.article.pojo.Comment;
import cn.aaron911.micro.article.service.CommentService;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.common.result.StatusCode;

/**
 * 评论文章Controller
 * 
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发表文章评论
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功");
    }

    /**
     * 根据文章id查询评论
     * @param articleid
     * @return
     */
    @RequestMapping(value = "/article/{articleid}", method = RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleid) {
        return new Result(true, StatusCode.OK, "查询成功", commentService.findByArticleid(articleid));
    }
    
    /**
     * 根据评论id删除评论
     * @param commentid
     * @return
     */
    @RequestMapping(value = "/delete/{commentid}", method = RequestMethod.GET)
    public Result deleteByCommentid(@PathVariable String commentid) {
    	commentService.deleteByCommentid(commentid);
        return new Result(true, StatusCode.OK, "删除成功");
    }


}
