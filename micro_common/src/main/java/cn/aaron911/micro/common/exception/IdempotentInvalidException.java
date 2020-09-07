package cn.aaron911.micro.common.exception;


/**
 * 幂等性校验码 校验失败
 * 
 */
public class IdempotentInvalidException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.IDEMPOTENT_INVALID;
	
	public IdempotentInvalidException() {
		super(stateCodeEnum);
	}
}

