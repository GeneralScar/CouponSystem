package exceptions;

public class NameRegisterExcption extends Exception
{

	public NameRegisterExcption(String message)
	{
		super("Name allready registered!");
	}
	
}