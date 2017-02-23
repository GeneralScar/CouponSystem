package exceptions;

/**
 * Exception - points on duplication of a Customer name attempt
 * may happen when trying to create and insert a new Customer into the database
 * @author Orchay
 *
 */
public class CustomerAlreadyExist extends Exception
{

	public CustomerAlreadyExist(String message)
	{
		super("Name allready registered!");
	}
	
}