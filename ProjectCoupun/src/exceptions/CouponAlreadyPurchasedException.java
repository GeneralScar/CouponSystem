package exceptions;

/**
 * Exception - points on of a repeated attempt to purchase a coupon by the same customer
 * may happen when trying to purchase a coupon
 * @author Orchay
 *
 */
public class CouponAlreadyPurchasedException extends Exception 
{

	/**
	 * 
	 * @param message
	 */
	public CouponAlreadyPurchasedException(String message)
	{
		super("THIS COUPON ALREADY PURCHASED BY THIS CUSTOMER!");
	}
}
