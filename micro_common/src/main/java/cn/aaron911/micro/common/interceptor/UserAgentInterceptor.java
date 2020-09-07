package cn.aaron911.micro.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户ip 及 浏览器类型解析
 *
 */
@Slf4j
public class UserAgentInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String ua = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgentUtil.parse(ua);
		
		log.info("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");

		/** 是否为移动平台 */
		log.info("是否为移动平台: " + userAgent.isMobile());

		/** 浏览器类型 */
		log.info("浏览器类型: " + userAgent.getBrowser().toString());

		/** 浏览器版本 */
		log.info("浏览器版本: " + userAgent.getVersion());

		/** 平台类型 */
		log.info("平台类型: " + userAgent.getPlatform().toString());

		/** 系统类型 */
		log.info("系统类型: " + userAgent.getOs().toString());

		/** 引擎类型 */
		log.info("引擎类型: " + userAgent.getEngine().toString());

		/** 引擎版本 */
		log.info("引擎类型: " + userAgent.getEngineVersion());
		
		log.info("ip: " + getRemoteIP(request));

		log.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
		return true;
	}

	/**
	 * 获取访问IP地址
	 *
	 * @param request
	 * @return
	 */
	protected String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");

		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		int index = ip.indexOf(",");
		if (index != -1) {
			return ip.substring(0, index);
		} else {
			return ip;
		}
	}
}
