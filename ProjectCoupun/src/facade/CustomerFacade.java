package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import exceptions.CouponAlreadyPurchasedException;
import exceptions.CouponSoldOutException;
import exceptions.LogginErrorExcption;
import infra.CLIENT_TYPE;
import infra.COUPON_TYPE;
import infra.Coupon;
import infra.Customer;
import infra.PriceComparator;

/**
 * customer facade a class that incorporates all of the methodes accessible to a registered customer
 * @author Orchay
 *
 */
public class CustomerFacade implements CouponClientFacade {

	private Customer loggedCustomer = null;
	private CustomerDBDAO myCustomerDBDAO;
	private CouponDBDAO myCouponDBDAO;
	
		//1 edited in 20.1 with notes		
	/**
	 *  constructor of class
	 */
		public CustomerFacade() //  constructor of class
		{	
			myCustomerDBDAO = new CustomerDBDAO();
			myCouponDBDAO =  new CouponDBDAO();
		}
		
		//2 edited in 20.1 with notes			
		public Customer getLoggedCustomer() 
		{
			return loggedCustomer;  //  //returns the logged customer DBDAO
		}

		//3 edited in 20.1 with notes
		/**
		 * login method to check if the user name and password matches and authorize as Customer  
		 */
		@Override
		public CouponClientFacade login(String name, String password, CLIENT_TYPE clientType) 
		{	
			boolean log = false;		//  flag checking if the login user name password are registered initialize as false
				
			try {
				log = myCustomerDBDAO.login(name, password);  //  sends the the received user name and password to be checked against
				}
				catch (ClassNotFoundException | SQLException | InterruptedException e) 
				{	
					e.getMessage();
				}
				catch (LogginErrorExcption e) 
				{
					System.out.println(e.getMessage());
					System.out.println("customer "+ name +" has failed to log into the system with the password" + password);
				}  
													
			if (log == true)
			{
				loggedCustomer = myCustomerDBDAO.getMyCoustomer();  //  initialize logged user private member
				myCouponDBDAO.setLoggedID(loggedCustomer.getCustId());
				System.out.println(this.loggedCustomer.getCustName() +" USER CONNECTED");  //  system notifies on successful login
			}				
			return this;  // returns this facade
		}
		
		//4 edited in 20.1 with notes
		/**
		 * purchase a coupon for  the logged customer
		 * @param coupon
		 */
		public void purchaseCoupon(Coupon coupon) // purchase a coupon for  the logged customer
		{
		
				try {
					myCustomerDBDAO.purchaseCoupon(coupon);  //  sends to the purchase coupon method in customer dbdao
				}
				catch (ClassNotFoundException | SQLException | InterruptedException e) 
				{	
					e.getMessage();
				}
				catch (CouponAlreadyPurchasedException e) 
				{
					System.out.println(e.getMessage());
					System.out.println("usser has tried to purchase coupon: " +coupon.getTitle() + " yet the coupon was already purchased by the user before" );
				} 
				catch (CouponSoldOutException e) 
				{
					System.out.println(e.getMessage());
					System.out.println("usser has tried to purchase coupon: " +coupon.getTitle() + " yet the coupon was out of stock" );
				}
			
			
		}
		
		//5 edited in 20.1 with notes		
		/**
		 * returns all purchased coupons of the logged customer
		 * @return
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public ArrayList<Coupon> getAllPurchesedCoupons() throws ClassNotFoundException, SQLException //  returns all purchased coupons of the logged customer
		{
			ArrayList<Coupon> allPurchasedCoupons = new ArrayList<>();  //  creates a new ArrayList of coupons
			try 
			{
				allPurchasedCoupons = myCustomerDBDAO.getCoupons();
			} 
			catch (InterruptedException e) 
			{
				e.getMessage();
			}			//  sends to the method of the customer dbdao to receive all of the logged customer coupons 

			return allPurchasedCoupons;	// returns the ArrayList contains the coupons
		}
		
		//6 edited in 20.1 with notes	
		/**
		 * returns all purchased coupons of the logged customer of a specified coupon type 
		 * @param type
		 * @return
		 */
		public Collection <Coupon> getAllPurchesedCouponsByType(COUPON_TYPE type) //  returns all purchased coupons of the logged customer of a specified coupon type 
		{
			ArrayList<Coupon> allPurchasedCoupons = new ArrayList<>(); 				    //  creates a new ArrayList of coupons
			ArrayList<Coupon> couponsByType = new ArrayList<>();						//  creates a new ArrayList of coupons

			try 
			{
				allPurchasedCoupons = this.getAllPurchesedCoupons();
				Iterator<Coupon> myIterator = allPurchasedCoupons.iterator();
				while(myIterator.hasNext())
				{
					Coupon typeCoupon = myIterator.next();
					if(typeCoupon.getCouponType()==type)
					{
						couponsByType.add(typeCoupon);
					}
				}
					
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			
			return couponsByType;  //  returns an ArrayList contains coupons
		}
		
		//7 edited in 20.1 with notes
		/**
		 * returns all purchased coupons of the logged customer arranged by price and under the specified price
		 * @param price
		 * @return
		 */
		public Collection<Coupon> getAllPurchesedCouponsByPrice(double price) //  returns all purchased coupons of the logged customer arranged by price and under the specified price 	  																	
		{
			ArrayList<Coupon> allPurchasedCoupons = new ArrayList<>(); 			//  creates a new ArrayList of coupons
			ArrayList <Coupon> couponsByPrice = new ArrayList<>();				//  creates a new ArrayList of coupons
			
			try {
				allPurchasedCoupons = this.getAllPurchesedCoupons();				    //  sends to the method of the customer dbdao to receive all of the 
				Iterator<Coupon> myIterator = allPurchasedCoupons.iterator();
				while(myIterator.hasNext())
				{
					Coupon priceCoupon = myIterator.next();
					if(priceCoupon.getPrice()<=price)
					{
						couponsByPrice.add(priceCoupon);
					}
				}
				Collections.sort(couponsByPrice, new PriceComparator());		//  sends the collection of coupons to be aranged by price
			
				
			}
			catch (ClassNotFoundException | SQLException e) 
			{
				e.getMessage();
			}
			
			return couponsByPrice;	//  returns the collection of coupons arranged by price
		}

}