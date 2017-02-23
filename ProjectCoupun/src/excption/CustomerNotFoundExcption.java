package excption;

public class CustomerNotFoundExcption extends Exception
{

	public CustomerNotFoundExcption(String message)
	{
		super("Customer Not Found!");
	}
	
}