package cn.aaron911.micro.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import cn.aaron911.micro.common.util.IdWorker;
import cn.aaron911.micro.common.util.JwtUtil;

/**
 *	
 */
@SpringBootApplication
@EnableEurekaClient 
@EnableFeignClients 	//Feign实现服务间的调用，问答微服务调用基础微服务的方法
public class QaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
