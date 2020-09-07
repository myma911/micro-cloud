package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class AccessErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.ACCESS_ERROR;
	
	public AccessErrorException() {
		super(stateCodeEnum);
	}
}

