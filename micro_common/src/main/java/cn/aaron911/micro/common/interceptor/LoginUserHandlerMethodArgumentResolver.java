package cn.aaron911.micro.common.interceptor;

import cn.aaron911.micro.common.annotation.LoginUser;
import cn.aaron911.micro.common.exception.LoginErrorException;
import cn.aaron911.micro.common.pojo.User;
import org.springframework.core.MethodParameter;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;




/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USERINFOKEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            throw new LoginErrorException();
        }

        // 从 存储中 获取用户信息   redis 还是数据库？
        // TO-DO....
        User user = (User) object;
        return user;
    }
}
