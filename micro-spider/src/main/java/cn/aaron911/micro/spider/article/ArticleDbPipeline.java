package cn.aaron911.micro.spider.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aaron911.micro.article.dao.ArticleDao;
import cn.aaron911.micro.article.pojo.Article;
import cn.aaron911.micro.common.util.IdWorker;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 入库类
 */
@Component
public class ArticleDbPipeline implements Pipeline {
	
    @Autowired
    private ArticleDao articleDao;
    
    @Autowired
    private IdWorker idWorker;
    
    /** 频道ID */
    private String channelId;
    
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    
    @Override
    public void process(ResultItems resultItems, Task task) {    
        String title = resultItems.get("title");
        String content= resultItems.get("content");
        Article article=new Article();
        article.setId(idWorker.nextId()+"");
        article.setChannelid(channelId);
        article.setTitle(title);
        article.setContent(content);
        articleDao.save(article);
    }
}