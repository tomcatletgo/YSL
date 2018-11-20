package core.common.exception;

/**
 * 自定义业务异常处理类.
 * @author joe
 * @date 2015年10月4日
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2058818470700530781L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable cause)
	{
		super(cause);
	}
	
	public BusinessException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
