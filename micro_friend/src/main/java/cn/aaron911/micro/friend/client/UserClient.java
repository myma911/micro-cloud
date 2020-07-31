package cn.aaron911.micro.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @FeignClient注解用于指定从哪个服务中调用功能
 * @RequestMapping注解用于对被调用的微服务进行地址映射。
 */
@Component
@FeignClient("micro-user")
public interface UserClient {
	
    @RequestMapping(value = "/user/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(
    		@PathVariable("userid") String userid, 
    		@PathVariable("friendid") String friendid, 
    		@PathVariable("x") int x);

}
