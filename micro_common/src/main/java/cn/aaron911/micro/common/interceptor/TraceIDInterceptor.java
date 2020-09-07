package cn.aaron911.micro.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.plumelog.core.TraceId;
import cn.hutool.core.util.IdUtil;

/**
 * plumelog 日志链路追踪traceID
 *
 */
public class TraceIDInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String traceid = IdUtil.fastSimpleUUID();
		//设置TraceID值，不埋此点链路ID就没有
        TraceId.logTraceID.set(traceid);
        return true;
	}
}
