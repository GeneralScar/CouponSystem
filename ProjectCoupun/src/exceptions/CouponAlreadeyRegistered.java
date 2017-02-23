package exceptions;

/**
 * Exception - points on duplication of a coupon name attempt
 * may happen when trying to create and insert a new coupon into the database
 * @author Orchay
 *
 */
public class CouponAlreadeyRegistered extends Exception 
{

	/**
	 * 
	 * @param message
	 */
	public CouponAlreadeyRegistered(String message)
	{
		super("THERE IS A COUPON REGISTERED IN THE SYSTEM WITH THE SAME NAME");
	}
}
