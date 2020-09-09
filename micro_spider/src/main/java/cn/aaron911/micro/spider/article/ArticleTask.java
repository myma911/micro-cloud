package cn.aaron911.micro.spider.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 文章任务类
 */
@Component
public class ArticleTask {
	
    @Autowired
    private ArticleDbPipeline articleDbPipeline;
    
    @Autowired
    private ArticleTxtPipeline articleTxtPipeline;
    
    @Autowired
    private RedisScheduler redisScheduler;
    
    @Autowired
    private ArticleProcessor articleProcessor;
    
    /**
     * 爬取ai数据
     */
    @Scheduled(cron="0 54 21 * *  ")
    public void aiTask(){
        System.out.println("爬取AI文章");
        Spider spider = Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/ai");
        articleTxtPipeline.setChannelId("ai");
        articleDbPipeline.setChannelId("ai");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
        spider.stop();
   }
    
    /**
     * 爬取db数据
     */
    @Scheduled(cron="20 17 11 * *  ")
    public void dbTask(){
        System.out.println("爬取DB文章");
        Spider spider = Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/db");
        articleTxtPipeline.setChannelId("db");
        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
        spider.stop();
    }
    
    /**
     * 爬取web数据
     */
    @Scheduled(cron="20 27 11 * *  ")
    public void webTask(){
        System.out.println("爬取WEB文章");
        Spider spider = Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/web");
        articleTxtPipeline.setChannelId("web");
        spider.addPipeline(articleTxtPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
        spider.stop();
    }
}