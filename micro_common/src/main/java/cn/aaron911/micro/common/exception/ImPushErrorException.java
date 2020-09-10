package cn.aaron911.micro.common.exception;


/**
 * IM 消息发送错误
 */
public class ImPushErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.IM_PUSH_ERROR;

	public ImPushErrorException() {
		super(stateCodeEnum);
	}
}

