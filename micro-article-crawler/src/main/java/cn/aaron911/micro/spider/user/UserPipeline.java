package cn.aaron911.micro.spider.user;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.aaron911.micro.common.util.IdWorker;
import cn.aaron911.micro.spider.util.DownloadUtil;
import cn.aaron911.micro.user.dao.UserDao;
import cn.aaron911.micro.user.pojo.User;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 入库类
 * 
 */
@Component
public class UserPipeline implements Pipeline {
	
    @Autowired
    private IdWorker idWorker;
    
    @Autowired
    private UserDao userDao;
    
    @Override
    public void process(ResultItems resultItems, Task task) {
        User user=new User();
        user.setId(idWorker.nextId()+"");
        user.setNickname(resultItems.get("nickname"));
        String image = resultItems.get("image");//图片地址
        String fileName = image.substring(image.lastIndexOf("/")+1);
        user.setAvatar(fileName);
        userDao.save(user);
        //下载图片
        try {
            DownloadUtil.download(image,fileName,"e:/userimg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}