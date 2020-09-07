package cn.aaron911.micro.qa.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.aaron911.micro.common.interceptor.AuthorizationInterceptor;
import cn.aaron911.micro.common.interceptor.TraceIDInterceptor;
import cn.aaron911.micro.common.interceptor.UserAgentInterceptor;
import cn.aaron911.micro.common.util.JwtUtil;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    

    
    /**
     * 方法参数用户对象数据自动注入
     */
//    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
    
    
    
    //private List<String> patterns = CollUtil.newArrayList("/");
    
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// plumelog 日志链路追踪traceID
    	registry.addInterceptor(new TraceIDInterceptor()).addPathPatterns("/**");
    	
    	//用户信息解析
    	registry.addInterceptor(new UserAgentInterceptor()).addPathPatterns("/**");

    	// token 身份认证
        registry.addInterceptor(new AuthorizationInterceptor(jwtUtil())).addPathPatterns("/**");
        
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
    
 
	
	
	
	
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//        //注册拦截器要声明拦截器对象和要拦截的请求
//        registry.addInterceptor(new AuthorizationInterceptor(jwtUtil())).addPathPatterns("/**");
//    }
//    

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
    
    
}
