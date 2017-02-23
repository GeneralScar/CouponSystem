package exceptions;

/**
 * Exception - points on a failed attempt to get a coupon (by ID)
 * @author Orchay
 *
 */
public class CouponNotFoundExcption extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public CouponNotFoundExcption(String message)
	{
		super("Coupon Not Found!");
	}
	
}
