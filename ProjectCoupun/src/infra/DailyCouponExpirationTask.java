package infra;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CouponNotFoundExcption;
import facade.AdminFacade;

/**
 * a daily task to remove expired coupons from the database
 * @author Orchay
 *
 */
public class DailyCouponExpirationTask implements Runnable {
	private static LocalDate localDate;
	private static Date currentDate;
	private AdminFacade af = new AdminFacade();
	private boolean quite = false;
	
	
	public DailyCouponExpirationTask() // this class uses a thread to remove expired coupons from the database
	{
	}
	
	public void run() 
	{

		while(!quite)  // inserts the thread into an infinite loop
		{

			ArrayList<Long> expCouponsID = new ArrayList<>(); 	  //  initialize array to contain all the register companies of the system
			Connection con= null;						  		  //  connection declaration and initializing as null
			ResultSet rs;										  //  result set from SQL variant declaration
			Statement stmt;								  		  //  Statement Declaration
			Coupon coupon = null;
			
				try {
					con =ConnectionPool.getInstance().getConnection();  //  connection initializing
					stmt = con.createStatement();						//  statement initializing
					
					// gets result of all the expired coupons from table coupon - execute a query statement, insert result to rs
					rs = stmt.executeQuery("SELECT * FROM coupon_project.coupon where End_Date < current_date()");

				    while ( rs.next() & !quite) // loop that inserts all of the expired coupons id into an arraList of coupons
				    {	
				    	long coupon_id =  rs.getLong("coupon_id");
				    	expCouponsID.add(coupon_id);
				    }
				    
				    Iterator deleteCouponsID = expCouponsID.iterator();   // iterator that runs on the coupons ID ArrayList
				    
				    while (deleteCouponsID.hasNext() & !quite)  // loop that removes all of the coupons from expCouponsID ArrayList by coupon id
				    	{
				    		long couponToDeleteID = (long) deleteCouponsID.next();
							coupon = af.getCouponDBDAO().getCoupon(couponToDeleteID);
							
				    		af.getCouponDBDAO().removeCoupon(coupon);
		        		}
	        
			ConnectionPool.getInstance().returnConnection(con);  // returns connection
			System.out.println("system updated ");       
			Thread.sleep(24*60*60*1000);  // sends the cleaning thread to sleep for one day
			} 
				
				 catch (ClassNotFoundException | SQLException | InterruptedException e) 
				{
					 e.getMessage();
				}
			
			catch (CouponNotFoundExcption e) {
		 	System.out.println(e.getMessage());
			System.out.println("task failed to remove a coupon since a coupon by this name: " + coupon.getTitle()+" does not exists");
			System.out.println("please contact administrator and check DataBases of coupon-company and coupon");
		}
				
		}
	}
	
	
	public boolean isQuite() 
	{
		return quite;
	}

	public void setQuite(boolean quite) 
	{
		this.quite = quite;
	}

	public static Date getLastDate()
	{
		return currentDate;
	}

	public static Date getCurrentDate() 
	{
		localDate = LocalDate.now();
		currentDate = Date.valueOf(localDate) ;
		return currentDate;
	}
	
}
