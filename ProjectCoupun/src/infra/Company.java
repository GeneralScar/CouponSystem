package infra;

import java.util.ArrayList;
/**
 * Company Class
 * @author Orchay
 *
 */
public class Company
{

	private long comp_id;
	
	private String password, compName, email;
	private ArrayList<Coupon> coupons = new ArrayList<>();

	/**
	 * Contractor of class company
	 * @param compName company name
	 * @param comp_id companny ID
	 * @param password company password
	 * @param email company E-mail
	 */
	public Company(String compName, long comp_id, String password, String email)
	{
		this.comp_id = comp_id;
		this.password = password;
		this.compName = compName;
		this.email = email;
	}

	
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}


	public long getCompId() {
		return comp_id;
	}

	public String getEmail() {
		return email;
	}

	public void seteMail(String email) {
		this.email = email;
	}

	public void setCompId(long comp_id) {
		this.comp_id = comp_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	@Override
	public String toString() {
		return "\n Company [comp_id=" + comp_id + ", password=" + password + ", compName=" + compName + ", email=" + email+ "]"+getCoupons();
	}
	
	
}