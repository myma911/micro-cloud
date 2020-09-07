package cn.aaron911.micro.common.exception;


/**
 * 
 */
public class FailedException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.FAILED;

	public FailedException() {
		super(stateCodeEnum);
	}

}

