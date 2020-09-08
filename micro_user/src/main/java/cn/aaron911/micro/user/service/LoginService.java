package cn.aaron911.micro.user.service;

import cn.aaron911.micro.common.exception.LoginErrorException;
import cn.aaron911.micro.common.exception.RemoteErrorException;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.exception.TokenInvalidException;
import cn.aaron911.micro.user.pojo.User;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author:
 * @time: 2020/9/7 17:42
 */
@Service
public class LoginService {

    /**
     * 单点登录 默认token有效时长1440分钟
     */
    @Value("${custom.expire.minite:1440}")
    private int customExpireMinite;

    @Value("${custom.security: huoxianzhineng}")
    private String customSecurity;


    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;




    public String login(String username, String password) {
        User user = userService.findUser(username, password);
        if(null == user){
            throw new LoginErrorException();
        }
        final String userId = user.getId();
        final DES des = SecureUtil.des(customSecurity.getBytes());
        final String token = des.encryptBase64(userId);
        redisTemplate.opsForValue().set(userId, user, customExpireMinite, TimeUnit.MINUTES);
        return token;
    }

    public void logout(String token) {
        final DES des = SecureUtil.des(customSecurity.getBytes());
        final String userid = des.decryptStr(token);
        redisTemplate.delete(userid);
    }

    /**
     * 判断是否登录
     * @param token
     * @return
     */
    public User loginCheck(String token) {
        if (StrUtil.isBlank(token)) {
            throw new IllegalArgumentException("token 参数不能为空");
        }
        final DES des = SecureUtil.des(customSecurity.getBytes());
        String userid;
        try{
            userid = des.decryptStr(token);
        }catch (Exception e){
            throw new TokenInvalidException();
        }
        final Object o = redisTemplate.opsForValue().get(userid);
        if (null == o) {
            return null;
        }
        return (User) o;
    }
}
