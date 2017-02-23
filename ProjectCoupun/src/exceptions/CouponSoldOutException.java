package exceptions;

/**
 * Exception - points on a purchase attempt of coupon that has been sold out
 * may happen when trying to purchase a coupon
 * @author Orchay
 *
 */
public class CouponSoldOutException extends Exception
{

	/**
	 * 
	 * @param message
	 */
	public CouponSoldOutException(String message)
	{
		super("SOLD OUT!");
	}
	
}
