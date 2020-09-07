package cn.aaron911.micro.common.result;

import cn.aaron911.micro.common.exception.StateCodeEnum;
import lombok.Data;

/**
 * 返回结果实体类
 */
@Data
public class Result<T> {

	private Integer code;
    private boolean flag;
    private String message;
    private T data;

    public Result() {}

    public Result (boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;

        this.message = message;
    }
    
    public Result(StateCodeEnum stateCodeEnum) {
    	this.code = stateCodeEnum.getCode();
        this.flag = false;
        this.message = stateCodeEnum.getMessage();
    }
    
    
    
    /*********************************************************************/
    public static <T> Result<T> ok() {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setFlag(true);
    	return result;
    }
    
    public static <T> Result<T> ok(String message, T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setFlag(true);
    	result.setMessage(message);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> ok(T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setFlag(true);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> ok(String message) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setFlag(true);
    	result.setMessage(message);
    	return result;
    }
    /*********************************************************************/
    
    
    
    /*********************************************************************/
    public static <T> Result<T> failed() {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setFlag(false);
    	return result;
    }
    
    public static <T> Result<T> failed(String message, T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setFlag(false);
    	result.setMessage(message);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> failed(T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setFlag(false);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> failed(String message) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setFlag(false);
    	result.setMessage(message);
    	return result;
    }
    /*********************************************************************/
    
}
