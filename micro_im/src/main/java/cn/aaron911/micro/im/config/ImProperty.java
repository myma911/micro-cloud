package cn.aaron911.micro.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "im")
@Configuration
public class ImProperty {

	/**
	 * server 默认端口号
	 */
	private int port = 2000;

	/**
	 * 图灵或者茉莉机器人（tuling/moli）
	 */
	private String robotType;

	/**
	 * 机器人apiUrl
	 */
	private String robotApiUrl;

	/**
	 * 机器人key
	 */
	private String robotKey;

	/**
	 * 机器人Secret
	 */
	private String robotSecret;

}