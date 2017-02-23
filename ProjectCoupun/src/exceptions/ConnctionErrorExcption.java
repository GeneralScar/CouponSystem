package exceptions;

/**
 * 
 * @author Orchay
 *
 */
public class ConnctionErrorExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public ConnctionErrorExcption(String message)
	{
		super("Can Not Connected!");
	}
	
}
