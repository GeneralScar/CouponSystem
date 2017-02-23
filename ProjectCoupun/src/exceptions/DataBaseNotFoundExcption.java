package exceptions;

/**
 * Exception - points on a failed attempt to log into the database - was not able to find the database
 * @author Orchay
 *
 */
public class DataBaseNotFoundExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public DataBaseNotFoundExcption(String message)
	{
		super("Can Not Connected!");
	}
	
}