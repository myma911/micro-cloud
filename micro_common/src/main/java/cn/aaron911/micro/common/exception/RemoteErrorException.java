package cn.aaron911.micro.common.exception;


/**
 * 远程调用失败
 */
public class RemoteErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REMOTE_ERROR;

	public RemoteErrorException() {
		super(stateCodeEnum);
	}
}

