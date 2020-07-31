package cn.aaron911.micro.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.aaron911.micro.common.util.JwtUtil;
import io.jsonwebtoken.Claims;

/**
 * jwt验证拦截器
 *
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //拦截器只是负责把头请求头中包含token的令牌进行一个解析验证。
        String token = request.getHeader("Authorization");
        if(token!=null && !"".equals(token)){
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                if(roles!=null && roles.equals("admin")){
                    request.setAttribute("claims_admin", token);
                }
                if(roles!=null && roles.equals("user")){
                    request.setAttribute("claims_user", token);
                }
            }catch (Exception e){
                throw new RuntimeException("令牌不正确！");
            }
        }
        return true;
    }
}
