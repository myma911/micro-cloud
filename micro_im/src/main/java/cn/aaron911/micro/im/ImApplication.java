package cn.aaron911.micro.im;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * IM微服务
 *
 **/
@SpringBootApplication
@EnableEurekaClient
public class ImApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // 设置为不以web方式启动
        new SpringApplicationBuilder(ImApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

    }

    /**
     * spring boot启动后，会进入该方法
     */
    @Override
    public void run(String... args) {
        //init();
    }

}
