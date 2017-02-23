package exceptions;

/**
  * Exception - points on a failed attempt to log into the system
 * may happen when trying to log into the system
 * @author Orchay
 *
 */
public class LogginErrorExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public LogginErrorExcption(String message)
	{
		super("Can Not Loggin!");
	}
	
}
