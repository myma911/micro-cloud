package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class TokenEmptyException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.TOKEN_EMPTY;
	
	public TokenEmptyException() {
		super(stateCodeEnum);
	}
}

