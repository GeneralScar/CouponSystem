package excption;

public class CouponSoldOutException extends Exception
{

	public CouponSoldOutException(String message)
	{
		super("SOLD OUT!");
	}
	
}
