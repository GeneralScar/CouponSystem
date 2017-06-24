package Orchay.projectCoupons;

//import java.io.IOException;
import java.sql.SQLException;
//import java.util.Collection;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import facade.AdminFacade;
//import infra.CLIENT_TYPE;
import infra.Company;
import infra.Customer;
//import exceptions.CompanyAlreadyExist;


/**
 * Root resource (exposed at "admin" path)
 */
@Path("/admin")
public class AdminService {
	
		@Context HttpServletRequest request;
		@Context private HttpServletResponse response;
		AdminFacade adminfacade = null;
		
	
		/**
		 * simple get method to check if the service is on
		 * @return simple string to inform the user/tester that the service is on
		 */
		@GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getIt() {
	        return "Got it on AdminService!";
	    }
	
	
	/**
	 * a private method to return the facade attribute	
	 * checks if the session is still on
	 * @return facade attribute
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	private AdminFacade getAdminFacade() 
	{
		return adminfacade = (AdminFacade) request.getSession().getAttribute("currentFacade");
	}
	
	
	public AdminService() 
	{
		
	}
	
// COMPANY.CREATE
	/**
	 * method to create a company and insert it into the DB 
	 * @param company (received from client side)
	 * @return (string response)
	 */
	@Path("/company")	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createCompany(Company company) //  gets a company and sends it to be insert into the system database
	{
		adminfacade = this.getAdminFacade();

		try {
			System.out.println("company at service" + company);
			adminfacade.createCompany(company);
			return ("created Company " + company.getCompName());
		} catch (Exception e) {
			return ("failed to create company "+ company.getCompName()+" : " + e.toString());

		}
	}

// COMPANY.DELETE
	/**
	 * method to remove a company and its coupons from the database
	 * @param newCompany (received from client side)
	 * @return (string response)
	 */
	@Path("/removeCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeCompany(Company newCompany)  // method to remove a company and all of its coupons from the database tables
	{
		System.out.println("got on service try to delete" + newCompany.getCompName());
		adminfacade = this.getAdminFacade();
		try {
		adminfacade.removeCompany(newCompany);
		return ("removed Company " + newCompany.getCompName());

		} catch (Exception e) {
			return ("failed to remove company "+ newCompany.getCompName()+" : " + e.toString());
		}
	}
	
//	COMPANY UPDATE
	/**
	 * gets a company updated from the client side
	 * update filed of company by comparing the primary key 
	 * @param company (received from client side)
	 * @return (string response)
	 */
	@Path("/updateCompany")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateCompany(Company company) 	// method to update a company personal info
	{
		adminfacade = this.getAdminFacade();

		try{		
				adminfacade.updateCompany(company);
				return ("updated Company " + company.getCompName());

			} catch (Exception e) {
				return ( "update company failed : " + e.toString());		
			}

	}

//	COMPANY.GETBYID
	/**
	 * gets an id and response with the data of the matching company
	 * not used in the web part replaced by filter
	 * @param company (id)
	 * @return (jason - company )
	 */
	@Path("/company/{id}") // id pattern (digits only)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany( @PathParam("id") long companyId) 	// gets an ID and returns the co-responded company
	{
		adminfacade = this.getAdminFacade();
		Company newCompany = null;
		
		try{		
			newCompany = adminfacade.getCompany(companyId);
			return newCompany;
			
		} 
		catch (Exception e) 
		{
		return newCompany;
		}
	}
	
//	COMPANY.GETALL	
	/**
	 * method to return all the companies listed
	 * @return (jason - collection of company )
	 * @throws Exception
	 */
	@Path("/getAllCompanies")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() throws Exception  	// returns all of the registered Companies
	{
		Collection<Company> Companies = null;

		adminfacade = this.getAdminFacade();
		
		Companies =  adminfacade.getAllCompanies();

		return Companies;
	}
	

///	CUSTOMERS
	

//	CUSTOMER.CREATE
	/**
	 * method to create a company and add it as a user to the database
	 * @param newCustomer
	 * @return string - response
	 * @throws Exception
	 */
	@Path("/createCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	public String createCustomer(Customer newCustomer) throws Exception 	//  gets a customer and sends it to be insert into the system database
	{	
		adminfacade = this.getAdminFacade();
		
		try{
		adminfacade.createCustomer(newCustomer);
		return  ("created Customer " + newCustomer.getCustName());
		
		} catch (Exception e) {
		return ("failed to create Customer " + newCustomer.getCustName());
		}

	}

//	CUSTOMER.DELETE
	/**
	 * method to remove a customer from the database
	 * @param customer
	 * @return string - response
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Path("/removeCustomer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)	
	public String removeCustomer(Customer customer) throws ClassNotFoundException, SQLException  	// method to remove a customer and all of its purchased coupons from the database tables
	{
		adminfacade = this.getAdminFacade();
		try {
			adminfacade.removeCustomer(customer);
			return( "remove Customer :" + customer.getCustName());

		} catch (Exception e) {
			return ("failed to remove Customer " + customer.getCustName());
		}
		
	}
	
//	CUSTOMER.UPDATE
	/**
	 * method to update details of customer
	 * may not alter customer ID nor name
	 * @param customer
	 * @return string-response
	 * @throws Exception
	 */
	@Path("/updateCustomer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(Customer customer) throws Exception 	// method to update a customer personal info you may not change customer id nor his name!!!!;
	{
		adminfacade = this.getAdminFacade();
			
		try {
		adminfacade.updateCustomer(customer);
		return ("updated Customer :" + customer.getCustName());

		} catch (Exception e) {
			return("failed to update Customer " + customer.getCustName());
		}
	}
	
//	CUSTOMER.GETBYID
	/**
	 * method that receives a customer ID and returns 
	 * a jason contains customer details 
	 * method is not being used in the project
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	@Path("/customer/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer ( @PathParam("id") long customerId) throws Exception 	// gets an ID and returns the co-responded customer
	{
		adminfacade = this.getAdminFacade();

		Customer mycustomer = null;
		try {
		mycustomer = adminfacade.getCustomer(customerId);
		return mycustomer;
		} 
		catch (Exception e) {
		
			return mycustomer;
		}		
	}
	
//	CUSTOMER.GETALLCUST
	/**
	 * method to get all customers registered in the system
	 * @return collection of customers
	 * @throws Exception
	 */
	@Path("/getAllCustomers")
	@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() throws Exception	// returns all of the registered Customers
	{
		adminfacade = this.getAdminFacade();
		Collection<Customer> customers = null;

		try{
			customers = adminfacade.getAllCustomers();
			return customers;
		} 
		catch (Exception e) 
		{
			return customers;
		}
	}

}
