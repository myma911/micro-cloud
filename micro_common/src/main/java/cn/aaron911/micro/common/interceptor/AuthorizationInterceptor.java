package cn.aaron911.micro.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.aaron911.micro.common.annotation.LoginUser;
import cn.aaron911.micro.common.context.SystemContext;
import cn.aaron911.micro.common.exception.RedisException;
import cn.aaron911.micro.common.pojo.User;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.aaron911.micro.common.annotation.Login;
import cn.aaron911.micro.common.exception.TokenEmptyException;
import cn.aaron911.micro.common.exception.TokenInvalidException;

import java.util.concurrent.TimeUnit;

/**
 * Token 验证
 *
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String USERINFOKEY = "USERINFOKEY";
    /**
     * 用户在request请求头中自定义的token名称
     */
    private RedisTemplate redisTemplate;
    private String tokenName;
    private int ssoExpireMinite;


    public AuthorizationInterceptor(RedisTemplate redisTemplate, String tokenName, int ssoExpireMinite){
        if (null == redisTemplate) {
            throw new IllegalArgumentException("RedisTemplate is null");
        }
        if (StrUtil.isBlank(tokenName)) {
            throw new IllegalArgumentException("tokenName is blank");
        }
        if (ssoExpireMinite < 0) {
            throw new IllegalArgumentException("ssoExpireMinite 不能小于0");
        }
        this.redisTemplate = redisTemplate;
        this.tokenName = tokenName;
        this.ssoExpireMinite = ssoExpireMinite;
    }

	
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

    /**
     * 此处应与生产token加密时对应
     */
    private String security = "huoxianzhineng";
    final DES des = SecureUtil.des(security.getBytes());
    private void check(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader(tokenName);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
        	token = request.getParameter(tokenName);
        }

        //token为空
        if(StringUtils.isEmpty(token)){
            throw new TokenEmptyException();
        }
        String userid;
        try{
            userid = des.decryptStr(token);
        }catch (Exception e){
            throw new TokenInvalidException();
        }

        Object object = null;
        try{
            // 更新token有效期
            final Boolean expire = redisTemplate.expire(userid, ssoExpireMinite, TimeUnit.MINUTES);
            if (expire) {
                object = redisTemplate.opsForValue().get(userid);
            }
        }catch (Exception e){
            throw new RedisException();
        }

        if (null == object) {
            throw new TokenInvalidException();
        }
        User user = (User) object;
        request.setAttribute(USERINFOKEY, user);
        SystemContext.setUser(user);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        SystemContext.remove();
    }
}
