package cn.aaron911.micro.sso.server.client;

import org.springframework.stereotype.Component;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;

/**
 * 熔断后调用的接口
 */
@Component
public class UserClientFallBack implements UserClient{
    @Override
    public Result<StateCodeEnum> findUser(String username, String password) {
        return new Result<StateCodeEnum>(StateCodeEnum.REMOTE_ERROR);
    }
}
