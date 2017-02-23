package excption;

public class CouponNotFoundExcption extends Exception
{

	public CouponNotFoundExcption(String message)
	{
		super("Coupon Not Found!");
	}
	
}
