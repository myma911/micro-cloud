package cn.aaron911.micro.recruit.controller;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.aaron911.micro.common.exception.BaseException;
import cn.aaron911.micro.common.exception.StateCodeEnum;
import cn.aaron911.micro.common.result.Result;

/**
 * 异常处理
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	
	@ExceptionHandler(value = BaseException.class)
    public Result<StateCodeEnum> error(BaseException baseException){
        return new Result<StateCodeEnum>(baseException.getStateCodeEnum());
    }
	

    @ExceptionHandler(value = Exception.class)
    public Result<StateCodeEnum> error(Exception e){
        return new Result<StateCodeEnum>(StateCodeEnum.SYSTEM_ERROR);
    }
}
