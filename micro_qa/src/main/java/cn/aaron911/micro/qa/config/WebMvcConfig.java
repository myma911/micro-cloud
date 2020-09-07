package cn.aaron911.micro.qa.config;

import cn.aaron911.micro.common.interceptor.AuthorizationInterceptor;
import cn.aaron911.micro.common.interceptor.LoginUserHandlerMethodArgumentResolver;
import cn.aaron911.micro.common.interceptor.TraceIDInterceptor;
import cn.aaron911.micro.common.interceptor.UserAgentInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {




	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// plumelog 日志链路追踪traceID
		registry.addInterceptor(new TraceIDInterceptor()).addPathPatterns("/**");

		//用户信息解析
		registry.addInterceptor(new UserAgentInterceptor()).addPathPatterns("/**");

		// token 认证
		registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/**");

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}

}