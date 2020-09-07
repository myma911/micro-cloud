package cn.aaron911.micro.spider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.aaron911.micro.common.util.IdWorker;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@SpringBootApplication
@EnableScheduling
public class CrawlerApplication {
	
	@Value("${redis.host}")
	private String redis_host;

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}
	
	@Bean
	public RedisScheduler redisScheduler() {
		return new RedisScheduler(redis_host);
	}

}
