package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import exceptions.CompanyAlreadyExist;
import exceptions.CompanyNotFoundExcption;
import exceptions.CouponNotFoundExcption;
import exceptions.CustomerNotFoundExcption;
import infra.CLIENT_TYPE;
import infra.Company;
import infra.Coupon;
import infra.Customer;

/**
 * Admin facade a class that incorporates all of the methodes accessible to a registered Administor
 * @author Orchay
 *
 */
public class AdminFacade implements CouponClientFacade // Admin facade a class that incorporates all of the methodes accessible to a registered Administor
{

	private CouponDBDAO couponDBDAO = new CouponDBDAO();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();

	
	//1 edited in 20.1 with notes			
	/**
	 * empty constructor
	 */
	public AdminFacade() //  empty constructor
	{
	}

	//2 edited in 20.1 with notes			
	/**
	 * returns the private member coupon DBDAO
	 * @return
	 */
	public CouponDBDAO getCouponDBDAO()  //  returns the private member coupon DBDAO 
	{
		return couponDBDAO;			   	 //  returns the private member coupon DBDAO 	
	}

	//3 edited in 20.1 with notes			
	/**
	 * returns the private member customer DBDAO  
	 * @return
	 */
	public CustomerDBDAO getCustomerDBDAO()  //  returns the private member customer DBDAO 
	{
		return customerDBDAO;  //  returns the private member customer DBDAO 
	}

	//4 edited in 20.1 with notes			
	/**
	 *	returns the private member company DBDAO 
	 * @return
	 */
	public CompanyDBDAO getCompanyDBDAO()  //  returns the private member company DBDAO 
	{
		return companyDBDAO;
	}

	//5 edited in 20.1 with notes		
	/**
	 * login method to check if the user name and password matches and authorize as Administrator 
	 */
	@Override
	public CouponClientFacade login(String name, String password, CLIENT_TYPE clientType) 	// login method to check if the user name and password matches and authorize as Administrator 
{
	
		if ((name == "Admin")&&(password == "1234")) // condition of correct login as administrator
		{
			
			System.out.println("Admin USER CONNECTED"); // Notification on successful login
			return this; // returns this AdminFacade

		}
		else 
		{
			System.out.println("inValid userName and Password"); // Notification on UN successful login
			
			return null;  // returns null
		}
	}

	//6 edited in 20.1 with notes
	/**
	 * gets a company and sends it to be insert into the system database
	 * @param newCompany
	 */
	public void createCompany(Company newCompany) //  gets a company and sends it to be insert into the system database
	{
		try {
				companyDBDAO.createCompany(newCompany);			// sends the new company to be inserted into the database

			} 
			catch (ClassNotFoundException | SQLException | InterruptedException e) 
			{	
				e.getMessage();
			}

			catch (CompanyAlreadyExist e)
			{
			System.err.println(e.getMessage());
			System.out.println("admin failed to create a company since the company already exists");
			}
	}

	//7 edited in 20.1 with notes			
	/**
	 * method to remove a company and all of its coupons from the database tables
	 * @param newCompany
	 */
	public void removeCompany(Company newCompany)  // method to remove a company and all of its coupons from the database tables
	{
		
		companyDBDAO.setLoggedCompany(newCompany); // sets the loggedCompany at the company DBDAO to the company instance
												   // that is being removed
		
		ArrayList<Coupon> couponsToDelete = new ArrayList<>(); // Declaring an ArrayList to contain all of the coupons 
															   //of the company that is about to be removed
		
		//couponsToDelete = companyDBDAO.getCoupons(); // calls a method that returns all of the company's coupons
		couponsToDelete = newCompany.getCoupons(); // calls a method that returns all of the company's coupons

		
		Iterator<Coupon> deleter = couponsToDelete.iterator(); // initializing an iterator on the company's coupons ArrayList
		
		while (deleter.hasNext()) // loop to send each coupon to be deleted from the system
		{	
			Coupon deadCoupon2B = deleter.next(); // gets the next coupon from the ArrayList

			try 
			{
				couponDBDAO.removeCoupon(deadCoupon2B);
			} 
			catch (ClassNotFoundException | SQLException | InterruptedException e) 
			{
					e.getMessage();
			}

			catch (CouponNotFoundExcption e) 
			{
				System.out.println(e.getMessage());
				System.out.println("failed to find a coupon in DataBase that is registered as of the comapny");
			} // sends the coupon received to be deleted
		}
	
			try 
			{
				companyDBDAO.removeCompany(newCompany);
			} 
			catch (ClassNotFoundException | SQLException | InterruptedException e)
			{
				e.getMessage();
			}
		
			catch (CompanyNotFoundExcption e) {
			System.out.println(e.getMessage());
			System.out.println("failed to find comapny");
			}
			// sends the company to be removed
	}
	
	//8 edited in 20.1 with notes			
	/**
	 * method to update a company personal info
	 * @param company
	 */
	public void updateCompany(Company company) 	// method to update a company personal info
	{
		try 
		{
			companyDBDAO.updateCompany(company);  // sends the company to be updated - cannot change the company ID
		} 
		catch (ClassNotFoundException | SQLException | InterruptedException e) 
		{
			e.getMessage();
		}	
		catch (CompanyNotFoundExcption e) 
		{
		System.out.println(e.getMessage());
		System.out.println("system could not find company name in database");
		}
	}
	
	//10 edited in 20.1 with notes	
	/**
	 * gets an ID and returns the co-responded company
	 * @param cID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public Company getCompany(long cID) 	// gets an ID and returns the co-responded company

	{	
		Company company1 = null;
			try 
			{
				company1 = companyDBDAO.getCompany(cID);
				} 
			catch (ClassNotFoundException | SQLException | InterruptedException e) 
			{
				e.getMessage();
			} 
			catch (CompanyNotFoundExcption e) 
			{
				System.out.println(e.getMessage());
				System.out.println("system could not find company name in database");
			} //  calls a method that returns a company by a given ID

		
			try {
				company1.setCoupons(companyDBDAO.getCoupons());
			} 
			catch (ClassNotFoundException | SQLException | InterruptedException e) 
			{
				e.getMessage();
			}

			   //  gets all of the company coupons and insert it into the company instance
		
		return (company1); 								   //  returns Company
	}
	
	//11 edited in 20.1 with notes
	/**
	 * returns all of the registered Companies
	 * @return
	 */
	public ArrayList<Company> getAllCompanies()  	// returns all of the registered Companies
	{
		ArrayList<Company> companies = null;

			try {
				companies = companyDBDAO.getAllCompanies();
			} catch (ClassNotFoundException | SQLException | InterruptedException e) {

				e.getMessage();
			}
		 
	    return (companies);  											  //  returns an arrayList of companies
	}

	//12 edited in 20.1 with notes			
	/**
	 * gets a customer and sends it to be insert into the system database
	 * @param newCustomer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void createCustomer(Customer newCustomer) throws ClassNotFoundException, SQLException 	//  gets a customer and sends it to be insert into the system database
	{
		try 
		{
			customerDBDAO.createCustomer(newCustomer);	// sends the new Customer to be inserted into the database	
		} 
		catch (InterruptedException e) 
		{
			e.getMessage();
		} 
		catch (CustomerNotFoundExcption e) 
		{
			e.getMessage();
		}	
	}

	//13 edited in 20.1 with notes			
	/**
	 * method to remove a customer and all of its purchased coupons from the database tables
	 * @param newCustomer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void removeCustomer(Customer newCustomer) throws ClassNotFoundException, SQLException  	// method to remove a customer and all of its purchased coupons from the database tables
 
	{
		try {
			customerDBDAO.removeCustomer(newCustomer);
		} catch (InterruptedException e) 
		{
			e.getMessage();
		} 
		catch (CustomerNotFoundExcption e) 
		{
			System.out.println(e.getMessage());
			System.out.println("system could not find customer name in database");
		}	 /// sends the Customer to be deleted from the database's tables
	}

	//14 edited in 20.1 with notes	
	/**
	 * method to update a customer personal info may not alter customer name nor his id
	 * @param customer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException 	// method to update a customer personal info you may not change customer id nor his name!!!!;

	{
		try {
			customerDBDAO.updateCustomer(customer);
		}
		catch (InterruptedException e) 
		{
			e.getMessage();
		} 
		catch (CustomerNotFoundExcption e) 
		{
			System.out.println(e.getMessage());
			System.out.println("system could not find customer name in database");
		}			
	}

	//15 edited in 20.1 with notes	
	/**
	 * gets an ID and returns the co-responded customer
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Customer getCustomer (long id) throws ClassNotFoundException, SQLException 	// gets an ID and returns the co-responded customer
	{	
		Customer customer = null;
	
			try 
			{
				customer = customerDBDAO.getCustomer(id);  // gets customer fileds by id - all but the coupon purchased
			}
			catch (CustomerNotFoundExcption e) 
			{
				e.getMessage();
				System.out.println("system could not find customer name in database");
			} 
			catch (InterruptedException e) 
			{
				e.getMessage();
			}	
			
			try 
			{
				customer.setCoupons(customerDBDAO.getCoupons());    //  gets all of the company coupons and insert it into the company instance
			} 
			catch (InterruptedException e) 
			{
				e.getMessage();
			}
			
		return (customer); 	//  returns customer
	}
	
	//16 edited in 20.1 with notes	
	/**
	 * returns all of the registered Customers
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException	// returns all of the registered Customers
	{
		ArrayList<Customer> customers = new ArrayList<>();  //  ArrayList customers declaration and initializing
		
		try 
		{
			customers = customerDBDAO.getAllCustomers(); 	//  calls a method that returns all of the Customers registered in the system
		} 
		catch (InterruptedException e) 
		{
			e.getMessage();
		}

        return (customers);	//  returns an arrayList of companies
	}
	
	
}
