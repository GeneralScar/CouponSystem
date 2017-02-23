package infra;

//import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

/**
 * CouponSystem - singleton
 * @author Orchay
 * 
 */
public class CouponSystem {

	private static CouponSystem INSTANCE = null;
	private CouponClientFacade loginResult = null;
	private CLIENT_TYPE clientType = null;
	private static LocalDate localDate;
	private static Date currentDate;
	DailyCouponExpirationTask dcet = null;
	private long loggedID;
	private static boolean updated = false;
	public static Object key = new Object();
	private boolean isConnected = false;

	
	private CouponSystem()  // Constructor
	{
		DailyCouponExpirationTask dcet = new DailyCouponExpirationTask();
		Thread task = new Thread(dcet);
		task.setDaemon(true);
		task.start();  //  starts once the expiration coupon deleting thread

	}
	
	/**
	 * returns private member logged ID
	 * @return
	 */
	public long getLoggedID() 
	{
		return loggedID;
	}
	
	/**
	 * returns current date
	 * @return
	 */
	public static Date getCurrentDate() //  returns the current date
	{ 
		localDate = LocalDate.now();
		currentDate = Date.valueOf(localDate) ;
		return currentDate;
	}
	
	/**
	 * returns the logged client type
	 * @return
	 */
	public CLIENT_TYPE getClientType() //  returns the client type
	{
		return clientType;
	}

	/**
	 * returns a facade of the logged client type
	 * @return
	 */
	public CouponClientFacade getLoginResult() //  returns a facade of the login type
	{
		return loginResult;
	}

	/**
	 * returns a connection instance and if none created yet creates a set of connections
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static synchronized CouponSystem getInstance() throws ClassNotFoundException, SQLException, InterruptedException // singleton coupon system instance 
	{
		
		if (INSTANCE == null)
		{
			INSTANCE = new CouponSystem();
		}
		return INSTANCE;
		
	}
	/*
	public void userLogin() // gets login info from the user
	{
		//Scanner sc = new Scanner(System.in);
		
		while (isConnected == false)
		{
			// admin
			System.out.println("Enter userName:");
		//	String name = sc.next();
		//	String name = "jack";
		//	String name = "DailyPlanet";	
			String name = "Admin";
		//	String name = "DailyPlanet";
			System.out.println("Enter Password:");
		//	String pass = String.valueOf(sc.nextLine());
			//String pass = "123456";
			//String pass = "151689";
			String pass = "1234";
		
			System.out.println("Enter userType:");
			CLIENT_TYPE type;
			
		//	type = CLIENT_TYPE.valueOf(sc.next());
		//	type = CLIENT_TYPE.Company;
		//	type = CLIENT_TYPE.Customer;
			type = CLIENT_TYPE.Admin;
			clientType = type;
				
			this.login(name, pass, type);  // sends received login data to be checked via the login method
										   // can not get to this row if connection data is false
	
			System.out.println("Enter userName:");
			//	String name = sc.next();
			//	String name = "jack";
			//	String name = "DailyPlanet";	
				//String name = "Admin";
			//	String name = "DailyPlanet";
				System.out.println("Enter Password:");
			//	String pass = String.valueOf(sc.nextLine());
				//String pass = "123456";
				//String pass = "151689";
				String pass = "1234";
			
				System.out.println("Enter userType:");
				CLIENT_TYPE type;
				
			//	type = CLIENT_TYPE.valueOf(sc.next());
			//	type = CLIENT_TYPE.Company;
			//	type = CLIENT_TYPE.Customer;
				type = CLIENT_TYPE.Admin;
				clientType = type;
					
				this.login(name, pass, type);  // sends received login data to be checked via the login method
											   // can not get to this row if connection data is false
		}
	
		System.out.println("welcome");
			sc.close();
	}
	
	*/
	
	/**
	 * receives login data from userLogin method and if true returns a co responding facade else throws an error login exception
	 * @param name
	 * @param password
	 * @param type
	 * @return
	 */
	public CouponClientFacade login(String name, String password, CLIENT_TYPE type)  //  receives login data from userLogin method and if true returns a co responding facade
																					 //  else sends the user to re enter login info
	{		
		CouponClientFacade result = null;  //  initializing upper facade class
		
		switch (type)					   //  defines the facade as a sub class according to the user type
		{
			case Company:
				result = new CompanyFacade();
				break;
			case Admin:
				result = new AdminFacade();
				break;
			case Customer:
				result = new CustomerFacade();
				break;				
		}
		
		loginResult = result.login(name, password, type);	//  sends the login info to the login method of the dbdao
															//  if true gets a facade specified for the logged user else gets null
		if (loginResult == null)	// false entry						  
		{
			System.out.println("inValid entry");  //  system notification for wrong login attempt
			        			  //  direct the user to re enter login info
		}
		
		return loginResult;						  //  gets to this row only if login info checks as true and then returns the facade
		
	}

	/**
	 * closes all connections available in the connection set
	 */
	public void shutDown()
	{
		dcet.setQuite(true); // stopping a daily coupon expiration task from being made
		
		try 
		{
			System.out.println(" preparing to shout Down ... closing all connetion to database");
			ConnectionPool.getInstance().closeAllConnections(); // closes all connections to database
		} 
		catch (ClassNotFoundException e) 
		{
			e.getMessage();
		} 
		catch (SQLException e) 
		{
			e.getMessage();
		}
	}

}