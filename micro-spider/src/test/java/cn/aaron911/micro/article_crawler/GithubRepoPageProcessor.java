package cn.aaron911.micro.article_crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * PageProcessor的定制分为三个部分，分别是爬虫的配置、页面元素的抽取和链接的发现。
 * 
 */
public class GithubRepoPageProcessor implements PageProcessor {

    /**
     *  部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     *  
     *  也包括一些模拟的参数，例如User Agent、cookie，以及代理的设置，这里先简单设置一下：重试次数为3次，抓取间隔为一秒。
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    /**
     * 部分二：定义如何抽取页面信息，并保存下来
     * 
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     * 对于下载到的Html页面，如何从中抽取到想要的信息？WebMagic里主要使用了三种抽取技术：XPath、正则表达式和CSS选择器
     * 另外，对于JSON格式的内容，可使用JsonPath进行解析
     */
    @Override
    public void process(Page page) {
        
    	// 正则表达式则是一种通用的文本抽取语言
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        
        // XPath本来是用于XML中获取元素的一种查询语言，但是用于Html也是比较方便的，下面使用了XPath，意思是“查找所有class属性为'entry-title public'的h1元素，并找到他的strong子节点的a子节点，并提取a节点的文本信息”
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new GithubRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://github.com/code4craft")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}