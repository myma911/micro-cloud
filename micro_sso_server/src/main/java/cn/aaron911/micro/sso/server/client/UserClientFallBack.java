package cn.aaron911.micro.sso.server.client;

import cn.aaron911.micro.common.exception.RemoteErrorException;
import cn.aaron911.micro.common.pojo.User;
import org.springframework.stereotype.Component;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;

/**
 * 熔断后调用的接口
 */
@Component
public class UserClientFallBack implements UserClient{
    @Override
    public Result<User> findUser(String username, String password) {
        throw new RemoteErrorException();
        //return new Result<>(StateCodeEnum.REMOTE_ERROR);
    }
}