package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class TokenInvalidException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.TOKEN_INVALID;
	
	public TokenInvalidException() {
		super(stateCodeEnum);
	}
}

