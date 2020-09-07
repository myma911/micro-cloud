package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class SystemErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.SYSTEM_ERROR;

	public SystemErrorException() {
		super(stateCodeEnum);
	}
}

