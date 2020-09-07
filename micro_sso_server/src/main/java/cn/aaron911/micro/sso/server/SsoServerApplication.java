package cn.aaron911.micro.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 单点登录服务
 * @ClassName: SsoServerApplication
 * @author Aaron
 * @date 2020年9月7日 上午9:46:57
 */
@SpringBootApplication
@EnableEurekaClient 
@EnableFeignClients 
public class SsoServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
	}

}