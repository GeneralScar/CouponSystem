package infra;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Scanner;

/**
 * Coupon Class
 * @author Orchay
 *
 */
public class Coupon
{
	private long coupon_id;
	private COUPON_TYPE couponType;
	private Date dateStart; 
	private Date dateEnd;
	private String message;
	private double price;
	private int amount;
	private String image;
	private String title;
	
	/**
	 * 
	 * @param coupon_id coupon ID
	 * @param couponType coupon type
	 * @param dateStart coupon start date
	 * @param dateEnd coupon end date
	 * @param message description
	 * @param price price for 1 unit
	 * @param amount amount in stock
	 * @param image image representing the coupon
	 * @param title coupon's name
	 */
	public Coupon(long coupon_id, COUPON_TYPE couponType, Date dateStart, Date dateEnd, String message, 
			      double price, int amount, String image, String title)
	{
	this.coupon_id=coupon_id;
	this.couponType = couponType;
	this.dateStart = dateStart;
	this.dateEnd = dateEnd;
	this.message = message;
	this.price = price;
	this.amount = amount;
	this.image = image;
	this.title = title;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	ArrayList<Coupon> coupons = new ArrayList<>();
//
	public long getCouponId() {
		return coupon_id;
	}

	public void setCouponId(long coupon_id) {
		this.coupon_id = coupon_id;
	}

	public COUPON_TYPE getCouponType() {
		
		return couponType;
	}

	public void setCouponType(COUPON_TYPE couponT) {
		couponType = couponT;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) throws ParseException {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy/MM/dd");
		System.out.println("set start date of coupon by format yyyy/MM/dd");
		String startDateString = sc.next();
		dateStart = (Date) sdf.parse(startDateString);
		this.dateStart = dateStart;
		sc.close();
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) throws ParseException {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy/MM/dd");
		System.out.println("set exp date of coupon by format yyyy/MM/dd");
		String endDateString = sc.next();
		dateEnd = (Date) sdf.parse(endDateString);
		this.dateEnd = dateEnd;
		sc.close();
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		return "\n Coupon [coupon_id=" + coupon_id + ", couponType=" + couponType + ", dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd + ", message=" + message + ", price=" + price + ", amount=" + amount
				+ ", image=" + image + ", title=" + title + "]";
	}
	
}