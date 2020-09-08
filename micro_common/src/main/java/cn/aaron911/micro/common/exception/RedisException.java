package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class RedisException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REDIS_ERROR;

	public RedisException() {
		super(stateCodeEnum);
	}
}

