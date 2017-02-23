package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import exceptions.CouponAlreadeyRegistered;
import exceptions.CouponNotFoundExcption;
import exceptions.LogginErrorExcption;
import infra.CLIENT_TYPE;
import infra.COUPON_TYPE;
import infra.Company;
import infra.Coupon;
/**
 *  company facade a class that incorporates all of the methodes accessible to a registered company
 * @author Orchay
 *
 */
public class CompanyFacade implements CouponClientFacade //  company facade a class that incorporates all of the methodes accessible to a registered customer
{

	private CompanyDBDAO loggedCompanyDBDAO = new CompanyDBDAO();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private Company myCompany = null;
	private long comp_ID =0;
	
	//1 edited in 20.1 with notes			
	public CompanyFacade()  //  empty builder 
	{
		
	}
	
	//2 edited in 20.1 with notes			
	public CompanyDBDAO getLoggedCompanyDBDAO() 
	{
		return loggedCompanyDBDAO; //returns the logged company DBDAO
	}

	//3 edited in 20.1 with notes			
	public CouponDBDAO getCouponDBDAO() 
	{
		return couponDBDAO; //returns the use coupon DBDAO
	}

	//4 edited in 20.1 with notes		
	/**
	 * login method to check if the user name and password matches and authorize as Company  
	 */
	@Override
	public CouponClientFacade login(String name, String password, CLIENT_TYPE clientType) { 	// login method to check if the user name 
		//and password matches and authorize as Administrator 

		boolean log = false;

			try {
				log = loggedCompanyDBDAO.login(name, password); // sends to method company DBDAO gets boolean result
			}
			
			catch (ClassNotFoundException | SQLException | InterruptedException e) 
			{	
				e.getMessage();
			
			} catch (LogginErrorExcption e) {
				System.out.println(e.getMessage());
				System.out.println("company" + name + "failed to log into the system");
			}	 
		  
			if (log == true)
			{
				myCompany = loggedCompanyDBDAO.getLoggedCompany();	//  sets private member of logged company ID
				comp_ID=myCompany.getCompId();
				couponDBDAO.setLoggedID(comp_ID);															//  sets private member of company DBDAO
				System.out.println(myCompany.getCompName() + " USER CONNECTED");							//  notify on a successful connection
				return this;																				//  returns this facade
			}
			else
			{
				System.out.println("inValid entry");							//  notify on a UN successful connection
				return null;													//  returns null
			}
					
	}

	//5 edited in 20.1 with notes
	/**
	 * insert a coupon of the logged company into the database
	 * @param coupon
	 */
	public void createCoupon(Coupon coupon) //  insert a coupon of the logged company into the database
	{
		try {
			couponDBDAO.createCoupon(coupon);
		} 
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{	
			e.getMessage();
		}
		catch (CouponAlreadeyRegistered e) 
		{
			System.out.println(e.getMessage());
			System.out.println("company failed to create a coupon since a coupon by this name: " + coupon.getTitle()+" already exists");
		} 
		
		//  inserts the new coupon into the coupon table
		
		//CompanyDBDAO.insertToJoin();		//  inserts the new coupon ID (generated) and the company ID into the coupon_compan join table	
	}
	
	//6 edited in 20.1 with notes		
	/**
	 * gets a coupon and removes it from the database's tables: coupon company_coupon and customer_coupon
	 * @param coupon
	 */
	public void removeCoupon(Coupon coupon) //  gets a coupon and removes it from the database's tables: coupon company_coupon and customer_coupon
	{
		try {
			couponDBDAO.removeCoupon(coupon); // sends a coupon to coupon DBDAO function to be remove
		} 
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{	
			e.getMessage();
		}
 catch (CouponNotFoundExcption e) {
	 	System.out.println(e.getMessage());
		System.out.println("company failed to remove a coupon since a coupon by this name: " + coupon.getTitle()+" dose not exists");
		}
	}
	
	//7 edited in 20.1 with notes			
	/**
	 * gets a coupon check if exists and updates it (by ID)
	 * @param coupon
	 */
	public void updateCoupon(Coupon coupon) //  gets a coupon check if exists and updates it (by ID)
	{
		try {
			couponDBDAO.updateCoupon(coupon);// sends the received coupon to the coupon DBDAO to update the database
		}
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{	
			e.getMessage();
		}
		catch (CouponNotFoundExcption e) 
		{
			System.out.println(e.getMessage());
			System.out.println("company failed to update a coupon since a coupon by this name: " + coupon.getTitle()+" already exists");
		}   
	}
	
	//8 edited in 20.1 with notes
	/**
	 * gets a coupon ID and returns the coupon co-responding from the coupon table database
	 * @param id
	 * @return
	 */
	public Coupon getCoupon(long id) 		//  gets a coupon ID and returns the coupon co-responding from the coupon table database
	{
		Coupon coupon = null;
		try {
			coupon = couponDBDAO.getCoupon(id);//  gets the given coupon ID to coupon DBDAO and returns the co-responded coupon
		}
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{	
			e.getMessage();
		}  
 catch (CouponNotFoundExcption e) {
		System.out.println(e.getMessage());
		System.out.println("could not find coupon ID in dataBase table coupon");
		}
		return coupon;	// returns coupon
	}
	
	//9 edited in 20.1 with notes			
	/**
	 * returns all coupons of the logged company
	 * @return
	 */
	public Collection<Coupon> getAllCoupons() 	//  returns all coupons of the logged company
	{
		ArrayList<Coupon> coupons = new ArrayList<>(); 						//  creates a new ArrayList of coupons
		try {
			coupons = (ArrayList<Coupon>) loggedCompanyDBDAO.getCoupons();	//  get an arrayList of coupons from the logged Company DBDAO method

		}
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{	
			e.getMessage();
		}
		return coupons;														//  returns the array list contains all of thhe logged company coupons
	}

	//10 edited in 20.1 with notes		
	/**
	 * returns an ArrayList of coupons contains all of the logged company coupons of a certain type
	 * @param CouponType
	 * @return
	 */
	public Collection<Coupon> getCouponsByType(COUPON_TYPE CouponType) /// returns an ArrayList of coupons contains all of
	// the logged company coupons of a certain type
	{
		ArrayList<Coupon> coupons = new ArrayList<>(); 									//  creates a new ArrayList of coupons
		ArrayList<Coupon> couponsByType = new ArrayList<>();							//  creates a new ArrayList of coupons
		
		coupons = (ArrayList<Coupon>) this.getAllCoupons();					//  get an arrayList of coupons from the logged Company DBDAO method
		
		Iterator<Coupon> myIterator = coupons.iterator();
		while(myIterator.hasNext())
		{
			Coupon typeCoupon = myIterator.next();
			if(typeCoupon.getCouponType()==CouponType)
			{
				couponsByType.add(typeCoupon);
			}
		}
		
        return couponsByType;		//  returns an ArrayList of coupons of a certain type of the company
	}
	
}
