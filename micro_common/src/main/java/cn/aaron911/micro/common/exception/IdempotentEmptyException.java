package cn.aaron911.micro.common.exception;


/**
 * 请求头或者请求参数中 不存在幂等性校验码
 * 
 */
public class IdempotentEmptyException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.IDEMPOTENT_EMPTY;

	public IdempotentEmptyException() {
		super(stateCodeEnum);
	}

}

