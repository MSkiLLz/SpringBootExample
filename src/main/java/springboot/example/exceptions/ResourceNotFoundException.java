package springboot.example.exceptions;

public class ResourceNotFoundException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6083877254163273793L;

	public ResourceNotFoundException(String message)
	{
		super(message);
	}
	
	/*
	public ResourceNotFoundException()
	{
	}

	public ResourceNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public ResourceNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ResourceNotFoundException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
	*/
}
