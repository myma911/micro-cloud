package cn.aaron911.micro.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.aaron911.micro.search.dao.ArticleDao;
import cn.aaron911.micro.search.pojo.Article;

/**
 *
 */
@Service
public class ArticleSerachService {

    @Autowired
    private ArticleDao articleDao;
    /**
     * 增加文章
     * @param article
     */
    public void add(Article article) {
        articleDao.save(article);
    }

    /**
     * 根据文章搜索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTitleLike(String keywords,  int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(keywords, keywords, pageRequest);

    }
}
