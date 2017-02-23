package excption;

public class ConnctionErrorExcption extends Exception
{

	public ConnctionErrorExcption(String message)
	{
		super("Can Not Connected!");
	}
	
}
