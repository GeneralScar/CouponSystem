package exceptions;
/**
 * Exception - points on duplication of a company name attempt
 * may happen when trying to create and insert a new company into the database
 * @author Orchay
 *
 */
public class CompanyAlreadyExist  extends Exception {

	/**
	 * 
	 * @param message
	 */
	public CompanyAlreadyExist(String message) 
	{
		super(message);
	}	

}
