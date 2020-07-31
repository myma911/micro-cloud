package cn.aaron911.micro.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import cn.aaron911.micro.common.util.JwtUtil;

/**
 * Zuul路由转发，管理后台微服务网关
 * 
 */
@SpringBootApplication
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
    
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
