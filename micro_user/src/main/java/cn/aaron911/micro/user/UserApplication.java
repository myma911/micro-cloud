package cn.aaron911.micro.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import cn.aaron911.micro.common.util.IdWorker;

/**
 * 用户微服务
 */
@SpringBootApplication
@EnableEurekaClient

public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
