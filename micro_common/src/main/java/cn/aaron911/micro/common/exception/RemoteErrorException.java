package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class RemoteErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REMOTE_ERROR;

	public RemoteErrorException() {
		super(stateCodeEnum.getMessage());
	}

	public StateCodeEnum getStateCodeEnum() {
		return stateCodeEnum;
	}
}

