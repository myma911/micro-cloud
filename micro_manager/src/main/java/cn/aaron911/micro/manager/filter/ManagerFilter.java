package cn.aaron911.micro.manager.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


/**
 * Zuul过滤器
 * 
 */
public class ManagerFilter extends ZuulFilter {

    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * pre ：可以在请求被路由之前调用
     * route ：在路由请求时候被调用
     * post ：在route和error过滤器之后被调用
     * error ：处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
    	// 前置过滤器
        return "pre"; 
    }

    /**
     * filterOrder ：通过int值来定义过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
    	// 优先级为0，数字越大，优先级越低
        return 0;
    }

    /**
     * shouldFilter ：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效
     */
    @Override
    public boolean shouldFilter() {
    	// 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    /**
     * run ：过滤器的具体逻辑
     * manager的过滤器, 因为是管理后台使用，所以需要在过滤器中对token进行验证
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        
        if (request.getRequestURI().contains("v2/api-docs")) {
            return null;
        }
        
        return null;
    }
}
