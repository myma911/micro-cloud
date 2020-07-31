package cn.aaron911.micro.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @RefreshScope 此注解用于刷新配置
 * 当修改了git上自定义配置，需要添加此注解才能立即生效
 * 
 */
@RestController
@RefreshScope //此注解用于刷新配置
public class TestController{
	@Value("${sms.ip}")
	private String ip;
	
	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	public String ip() {
		return ip;
	}
}