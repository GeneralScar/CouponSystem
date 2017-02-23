package infra;

import java.util.Comparator;

/**
 * price Comparator Class
 * @author Orchay
 *
 */
public class PriceComparator implements Comparator<Coupon>
{
	
	public PriceComparator() {
	}
	
	@Override
	public int compare(Coupon a, Coupon b)
	{
	
		return a.getPrice() > b.getPrice() ? 1 : a.getPrice() == b.getPrice() ? 0 : -1;

	}

}
