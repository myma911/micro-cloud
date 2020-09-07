package cn.aaron911.micro.base.controller;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;

/**
 * 异常处理
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> error(Exception e){
        e.printStackTrace();
        return new Result<>(StateCodeEnum.SYSTEM_ERROR);
    }
}
