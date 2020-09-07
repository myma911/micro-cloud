package cn.aaron911.micro.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.aaron911.micro.common.annotation.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.aaron911.micro.common.annotation.Login;
import cn.aaron911.micro.common.exception.TokenEmptyException;
import cn.aaron911.micro.common.exception.TokenExpiredException;
import cn.aaron911.micro.common.exception.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Token 验证
 *
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_KEY = "userId";
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        final MethodParameter[] methodParameters = ((HandlerMethod) handler).getMethodParameters();
        for(MethodParameter methodParameter : methodParameters){
            if (methodParameter.hasParameterAnnotation(LoginUser.class)) {
                check(request);
            }
        }

        if(annotation == null){
            return true;
        }
        check(request);
        return true;
    }

    private void check(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
        	token = request.getParameter("token");
        }

        //token为空
        if(StringUtils.isEmpty(token)){
            throw new TokenEmptyException();
        }

        //硬核检查，查询token信息
        //TokenEntity tokenEntity = tokenService.queryByToken(token);
        //if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
        //    throw new LoginErrorException();
        //}

        // 校验token
        try {
        	//jwtUtil.parseJWT(token);
		} catch (UnsupportedJwtException e) {
			throw new TokenInvalidException();
		} catch (MalformedJwtException e) {
			throw new TokenInvalidException();
		} catch (SignatureException e) {
			throw new TokenInvalidException();
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (IllegalArgumentException e) {
			throw new TokenInvalidException();
		} catch (Exception e) {
			throw new TokenInvalidException();
		}
        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, token);
    }
}
