package exceptions;

/**
 * Exception - points on a failed attempt to get a company by name or ID
 * @author Orchay
 *
 */
public class CompanyNotFoundExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public CompanyNotFoundExcption(String message)
	{
		super("Company Not Found!");
	}
	
}