package excption;

public class CompanyNotFoundExcption extends Exception
{

	public CompanyNotFoundExcption(String message)
	{
		super("Company Not Found!");
	}
	
}