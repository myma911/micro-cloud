package cn.aaron911.micro.spider.article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.aaron911.micro.common.util.HTMLUtil;
import cn.aaron911.micro.common.util.IKUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * 入库类(分词后保存为txt)
 */
@Component
public class ArticleTxtPipeline  implements Pipeline {
    
	@Value("${ai.dataPath}")
    private String dataPath;
	
    private String channelId;//频道ID
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    
    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");//获取标题
        //获取正文并删除html标签
        String content=  HTMLUtil.delHTMLTag(resultItems.get("content"));
        try {
            //将标题+正文分词后保存到相应的文件夹
            PrintWriter printWriter = new PrintWriter(new File(dataPath+"/"+channelId+ "/"+UUID.randomUUID()+".txt"));
            printWriter.print(IKUtil.split(title+" "+content," "));
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
