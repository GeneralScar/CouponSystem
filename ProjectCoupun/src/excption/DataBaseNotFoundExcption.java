package excption;

public class DataBaseNotFoundExcption extends Exception
{

	public DataBaseNotFoundExcption(String message)
	{
		super("Can Not Connected!");
	}
	
}