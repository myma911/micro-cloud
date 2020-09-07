package cn.aaron911.micro.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.micro.common.result.PageResult;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.search.pojo.Article;
import cn.aaron911.micro.search.service.ArticleSerachService;

/**
 * 文章搜索controller
 */
@RestController
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSerachService articleSerachService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        article.setState("0");
        articleSerachService.add(article);
        return Result.ok("操作成功");
    }

    /**
     * 分页查询文章
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}", method = RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageResult = articleSerachService.findByTitleLike(keywords, page, size);
        return Result.ok("查询成功", new PageResult<Article>(pageResult.getTotalElements(), pageResult.getContent()));


    }
}
