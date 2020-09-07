package cn.aaron911.micro.sso.server.service;

import cn.aaron911.micro.common.exception.LoginErrorException;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.pojo.User;
import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.sso.server.client.UserClient;
import cn.aaron911.micro.sso.server.util.RedisUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Value("${sso.expire.minite:1440}")
    private int sso_expire_minite;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RedisUtil redisUtil;


    public String login(String username, String password) {
        Result result = userClient.findUser(username, password);
        Object data = result.getData();
        if (result.getCode() != StateCodeEnum.OK.getCode() || null == data) {
            throw new LoginErrorException();
        }
        User user = (User) data;
        String token = IdUtil.fastSimpleUUID();
        final boolean set = redisUtil.set(token, user, sso_expire_minite * 60);
        // 成功
        if (set) {
            return token;
        }
        throw new LoginErrorException();
    }

    public void logout(String token) {
        redisUtil.del(token);
    }

    /**
     * 判断是否登录
     * @param token
     * @return
     */
    public User loginCheck(String token) {
        final Object o = redisUtil.get(token);
        if (null == o) {
            return null;
        }
        return (User) o;
    }
}
