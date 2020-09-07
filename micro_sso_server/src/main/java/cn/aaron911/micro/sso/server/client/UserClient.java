package cn.aaron911.micro.sso.server.client;

import cn.aaron911.micro.common.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.aaron911.micro.common.result.Result;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @FeignClient注解用于指定从哪个服务中调用功能，注意 里面的名称与被调用的服务名保持一致，并且不能包含下划线
 * 
 * Feign 本身支持Hystrix  Hystrix 能使你的系统在出现依赖服务失效的时候，通过隔离系统所依赖的服务，防
 * 止服务级联失败，同时提供失败回退机制，更优雅地应对失效，并使系统能更快地从异常中恢复。
 * 
 */
@FeignClient(value = "micro-user", fallback = UserClientFallBack.class)
public interface UserClient {

    @RequestMapping(value="/user/findUser", method = RequestMethod.GET)
    Result<User> findUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
