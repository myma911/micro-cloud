package cn.aaron911.micro.qa.client;

import org.springframework.stereotype.Component;

import cn.aaron911.micro.common.result.Result;
import cn.aaron911.micro.common.result.StatusCode;

/**
 * 熔断后调用的接口
 */
@Component
public class LabelClientImpl implements LabelClient{
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"获取标签失败，请稍后重试");
    }
}
