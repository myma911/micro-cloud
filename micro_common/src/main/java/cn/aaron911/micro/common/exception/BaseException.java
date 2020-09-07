package cn.aaron911.micro.common.exception;

/**
 * 自定义 异常基类
 * @ClassName: BaseException
 * @author Aaron
 * @date 2020年9月4日 下午1:53:17
 */
public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private int code;
	
	private String msg;
	

	private StateCodeEnum stateCodeEnum;
	
	public BaseException(StateCodeEnum stateCodeEnum) {
		super(stateCodeEnum.getMessage());
		
		this.code = stateCodeEnum.getCode();
		this.msg = stateCodeEnum.getMessage();
		this.stateCodeEnum = stateCodeEnum;
	}

	public StateCodeEnum getStateCodeEnum() {
		return stateCodeEnum;
	}


	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	

}
