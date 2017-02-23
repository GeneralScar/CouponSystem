package infra;

import java.util.ArrayList;
/**
 * Customer Class
 * @author Orchay
 *
 */
public class Customer
{
    
	private long cust_id;
	private String password;
	private String custName;
	private ArrayList<Coupon> coupons = new ArrayList<>();
	
	/**
	 * 
	 * @param cust_id customer's ID in data base
	 * @param custName customer registered name in data base
	 * @param password customer's password
	 */
	public Customer(long cust_id, String custName,String password)
	{
		this.cust_id=cust_id;
		this.password = password;
		this.custName = custName;

	}
	
	
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}


	public long getCustId() {
		return cust_id;
	}
	public void setCustId(long cust_id) {
		this.cust_id = cust_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}


	@Override
	public String toString() {
		return "\n Customer [cust_id=" + cust_id + ", password=" + password + ", custName=" + getCustName()+"]"+  getCoupons();
	}

}