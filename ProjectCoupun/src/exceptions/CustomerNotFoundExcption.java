package exceptions;

/**
 * Exception - points on a failed attempt to get a company by name or ID
 * @author Orchay
 *
 */
public class CustomerNotFoundExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public CustomerNotFoundExcption(String message)
	{
		super("Customer already registered!");
	}
	
}