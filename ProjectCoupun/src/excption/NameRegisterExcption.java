package excption;

public class NameRegisterExcption extends Exception
{

	public NameRegisterExcption(String message)
	{
		super("Name Not Found!");
	}
	
}