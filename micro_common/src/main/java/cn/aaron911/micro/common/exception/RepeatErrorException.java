package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class RepeatErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REPEAT_ERROR;
	
	public RepeatErrorException() {
		super(stateCodeEnum);
	}
}

