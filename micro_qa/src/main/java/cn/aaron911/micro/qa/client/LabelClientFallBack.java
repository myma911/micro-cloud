package cn.aaron911.micro.qa.client;

import org.springframework.stereotype.Component;

import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;

/**
 * 熔断后调用的接口
 */
@Component
public class LabelClientFallBack implements LabelClient{
    @Override
    public Result<StateCodeEnum> findById(String id) {
        return new Result<StateCodeEnum>(StateCodeEnum.REMOTE_ERROR);
    }
}
