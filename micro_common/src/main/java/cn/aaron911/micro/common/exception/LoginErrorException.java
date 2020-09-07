package cn.aaron911.micro.common.exception;

/**
 * 
 */
public class LoginErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.LOGIN_ERROR;
	

	public LoginErrorException() {
		super(stateCodeEnum);
	}
}

