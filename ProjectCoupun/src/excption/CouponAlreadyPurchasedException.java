package excption;

public class CouponAlreadyPurchasedException extends Exception 
{


	public CouponAlreadyPurchasedException(String message)
	{
		super("THIS COUPON ALREADY PURCHASED BY THIS CUSTOMER!");
	}
}
