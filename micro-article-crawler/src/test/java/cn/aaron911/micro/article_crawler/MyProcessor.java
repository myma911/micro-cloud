package cn.aaron911.micro.article_crawler;

import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;


/**
 * 爬取页面全部内容 https://blog.csdn.net/nav/ai
 * 
 */
public class MyProcessor implements PageProcessor {
	public void process(Page page) {
		//System.out.println(page.getHtml().toString());
		
		//添加目标地址，我们可以通过添加目标地址，从种子页面爬取到更多的页面将当前页面里的所有链接都添加到目标页面中
		page.addTargetRequests(page.getHtml().links().all());

		page.addTargetRequests(page.getHtml().links().regex("https://blog.csdn.net/[a‐z 0‐9‐]+/article/details/[0‐9]{8}").all());
		System.out.println(page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div[1]/h1/text()").toString());
		
	}

	public Site getSite() {
		return Site.me().setSleepTime(100).setRetryTimes(3);
	}

	/**
	 * Spider是爬虫启动的入口。在启动爬虫之前，我们需要使用一个PageProcessor创建一个Spider对象，然后使用run()进行启动
	 * 
	 */
	public static void main(String[] args) {
		Spider.create(new MyProcessor())
		// 起始url 入口
		.addUrl("https://blog.csdn.net/nav/ai")
		
		//Scheduler(URL管理) 最基本的功能是实现对已经爬取的URL进行标示。可以实现URL的增量去重。	目前scheduler主要有三种实现方式：
		//1）内存队列 QueueScheduler　2）文件队列 FileCacheQueueScheduler　　3) Redis队列 RedisScheduler
		//1）设置内存队列
		//.setScheduler(new QueueScheduler())
		
		//2）设置文件队列,使用文件保存抓取URL，可以在关闭程序并下次启动时，从之前抓取到的URL继续抓取
		//.setScheduler(new FileCacheQueueScheduler("E:\\scheduler"))
		
		//3) 设置Redis队列,使用Redis保存抓取队列，可进行多台机器同时合作抓取
		.setScheduler(new RedisScheduler("127.0.0.1"))
		
		// 控制台输出
		.addPipeline(new ConsolePipeline())
		
		//以文件方式保存
		.addPipeline(new FilePipeline("e:/data"))
		
		// 以json方式保存
		.addPipeline(new JsonFilePipeline("e:/json"))
		
		//定制化输出
		.addPipeline(new MyPipeline())
		.run();
	}
}


/**
 * 定制Pipeline
 * 
 * 创建类MyPipeline实现接口Pipelin
 * 
 */
class MyPipeline implements Pipeline{
	public void process(ResultItems resultItems, Task task) {
		String title = resultItems.get("title");
		System.out.println("我的定制的 title:"+title);
	}
}