package Orchay.projectCoupons;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import facade.CompanyFacade;
import facade.CustomerFacade;
import DAO.CouponDBDAO;
import exceptions.CouponNotFoundExcption;
import exceptions.LogginErrorExcption;
import infra.CLIENT_TYPE;
import infra.COUPON_TYPE;
import infra.Company;
import infra.Coupon;
import infra.Customer;

//Sets the path to base URL + /customer
/**
 * 
 * @author Orchay
 *
 */
@Path("/customer")
public class CustomerService {

	
	@Context HttpServletRequest request;
	@Context private HttpServletResponse response;
	CustomerFacade customerfacade = null;
	
	/**
	 * simple get method to check if the service is on
	 * @return simple string to inform the user/tester that the service is on
	 */
	 @GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getIt() {
	        return "Got it on customer service!";
	    }
	
	/**
	 * a private method to return the facade attribute	
	 * checks if the session is still on
	 * @return facade attribute
	 */
	private CustomerFacade getCustomerFacade() 
	{
		return customerfacade = (CustomerFacade) request.getSession().getAttribute("currentFacade");
	}
	
	
	/**
	 * method that returns logged user information
	 * @return customer - jason
	 */
	@Path("/current")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCurrentCustomer() throws Exception {

		CustomerFacade customerFacade = getCustomerFacade();
    	Customer loggedCustomer = customerFacade.getLoggedCustomer();
		System.out.println(loggedCustomer);
    	return loggedCustomer;	
	}
	

	/**
	 * method that returns a collection of all coupon purchased by the client
	 * @return jason- collection of coupons
	 * @throws Exception
	 */
	@Path("/purchasedCoupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCoupons() throws Exception {

		CustomerFacade customerFacade = getCustomerFacade();
    	return customerFacade.getAllPurchesedCoupons();
	}

	/**
	 * method that returns a collection of all coupon available to purchase by the client 
	 * @return jason - collection of coupons
	 * @throws Exception
	 */
	@Path("/avilableCoupons")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllAvilableCoupons() throws Exception {

		CustomerFacade customerFacade = getCustomerFacade();
    	return customerFacade.getAllAvilableCoupons();
	}
	
	/**
	 * method receives a coupon type and returns all of the customer's purchased coupons 
	 * of that type
	 * function is not being used - replaced by filter functionality
	 * @param couponType
	 * @return jason - collection of coupons
	 * @throws Exception
	 */
	@Path("/coupons/{CouponType}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(@PathParam("CouponType") COUPON_TYPE couponType) throws Exception {
		
		CustomerFacade customerFacade = getCustomerFacade();
    	return customerFacade.getAllPurchesedCouponsByType(couponType);
	}

	/**
	 * method returns all of the purchased coupons by price
	 * not being used - replaced by filter funtioinality
	 * @param couponPrice
	 * @return
	 * @throws Exception
	 */
	@Path("/coupons/{price : \\d+}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@PathParam("price") int couponPrice) throws Exception {

		CustomerFacade customerFacade = getCustomerFacade();
    	return customerFacade.getAllPurchesedCouponsByPrice(couponPrice);
	}
	
	/**	
	 * receives a coupon from the customer to be bought matches its id
	 * and adds it to the customer's coupons
	 * @param Coupon
	 * @throws Exception
	 */
	@Path("/buy") 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void purchaseCoupon(Coupon Coupon) throws Exception {
		
		System.out.println(Coupon+ "got to webService method buy");
		CustomerFacade customerFacade = getCustomerFacade();
    	customerFacade.purchaseCoupon(Coupon);
	}
}