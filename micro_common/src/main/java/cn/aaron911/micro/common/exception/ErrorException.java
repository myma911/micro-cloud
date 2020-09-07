package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class ErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.ERROR;
	
	public ErrorException() {
		super(stateCodeEnum);
	}

}

