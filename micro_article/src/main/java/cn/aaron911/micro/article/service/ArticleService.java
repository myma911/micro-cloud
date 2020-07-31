package cn.aaron911.micro.article.service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.aaron911.micro.article.dao.ArticleDao;
import cn.aaron911.micro.article.pojo.Article;

/**
 *
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate<String, Article> redisTemplate;

    /**
     * 文章审核
     * @param id
     */
    @Transactional
    public void examine(String id) {
        articleDao.examine(id);
    }

    /**
     * 点赞
     * @param id
     */
    @Transactional
    public void updateThumbup(String id){
        articleDao.updateThumbup(id);
    }

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    public Article findById(String id) {
        String cache_key = "articel_" + id;
        Article article = (Article)redisTemplate.opsForValue().get(cache_key);
        if (Objects.isNull(article)) {
            article = articleDao.findById(id).get();
            redisTemplate.opsForValue().set(cache_key, article, 1, TimeUnit.DAYS);
        }
        return article;
    }

    /**
     * 修改
     * @param article
     */
    public void update(Article article) {
        articleDao.save(article);
        //清除缓存中的数据
        redisTemplate.delete("article_"+article.getId());
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        articleDao.deleteById(id);
        //清除缓存中的数据
        redisTemplate.delete("article_"+id);
    }


}
