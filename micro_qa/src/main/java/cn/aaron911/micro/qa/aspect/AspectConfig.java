package cn.aaron911.micro.qa.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.plumelog.trace.aspect.AbstractAspect;

/**
 * plumelog 链路追踪全局切面
 * @ClassName: AspectConfig
 * @author Aaron
 * @date 2020年9月4日 下午6:19:26
 * 
 * spring-cloud-starter-sleuth 的 链路追踪 和 plumelog 的链路追踪 traceId 命名冲突， 先不使用plumelog链路追踪
 * 
 * 
 */
//@Aspect
//@Component
public class AspectConfig extends AbstractAspect {

	@Around("within(cn.aaron911.micro.qa.controller..*))")
	public Object around(JoinPoint joinPoint) throws Throwable {
		return aroundExecute(joinPoint);
	}
}