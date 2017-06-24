package Orchay.projectCoupons;

import java.util.Collection;
import java.util.Date;

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

import facade.CompanyFacade;
import infra.CLIENT_TYPE;
import infra.COUPON_TYPE;
import infra.Company;
import infra.Customer;
import infra.Coupon;

//Sets the path to base URL + /company
/**
 * 
 * @author Orchay
 *
 */
@Path("/company")
public class CompanyService {

	@Context HttpServletRequest request;
	@Context private HttpServletResponse response;
	CompanyFacade companyfacade = null;
	
	/**
	 * simple get method to check if the service is on
	 * @return simple string to inform the user/tester that the service is on
	 */
	@GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getIt() {
	        return "Got it on CompanyService!";
	    }
	
	
	/**
	 * a private method to return the facade attribute	
	 * checks if the session is still on
	 * @return facade attribute
	 */
	private CompanyFacade getCompanyFacade() 
	{
		System.out.println("got to request a facade");
		companyfacade = (CompanyFacade) request.getSession().getAttribute("currentFacade");
		System.out.println("got a facade " + companyfacade);
		return companyfacade;
	}
	
	// Handling companies
	
	/**
	 * method receives an id and matches it to the DB to returns coupon
	 * method is not being used - web part use filter insted
	 * @param couponId
	 * @return jason-coupon
	 * @throws Exception
	 */
	@Path("/coupon/{id}") // id pattern (digits only)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon( @PathParam("id") long couponId ) throws Exception {

		CompanyFacade companyFacade = getCompanyFacade();
    	return companyFacade.getCoupon(couponId);
	}

	
	
	/**
	 * method to get all of the logged company's coupons 
	 * @return collection of coupons
	 * @throws Exception
	 */
	@Path("/coupons") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons() throws Exception{

		CompanyFacade companyFacade = getCompanyFacade();
    	return companyFacade.getAllCoupons();
	}

	/**
	 * method that returns logged user name
	 * @return string - company name
	 */
	@Path("/user") 
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser()
	{
		CompanyFacade companyFacade = getCompanyFacade();
    	System.out.println(companyFacade.getMyCompany().getCompName());
		return companyFacade.getMyCompany().getCompName();
	}

	/**
	 * method that returns user personal info
	 * @return returns jason-company 
	 */
	@Path("/LoggedUser") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Company getLoginUser()
	{
		CompanyFacade companyFacade = getCompanyFacade();
    	System.out.println(companyFacade.getMyCompany());
		return companyFacade.getMyCompany();
	}

	/**
	 * method that receive a coupon category-type and returns the matching coupons
	 * method is not used - being replaced by filter functionality
	 * @param couponType
	 * @return
	 * @throws Exception
	 */
	@Path("/coupons/{CouponType}") 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@PathParam("CouponType") COUPON_TYPE couponType) throws Exception {
		

		CompanyFacade companyFacade = getCompanyFacade();
    	return companyFacade.getCouponsByType(couponType);
	}
	
	/**
	 * creates a coupon in the DB and registers it to the company client
	 * @param coupon
	 * @throws Exception
	 */
	@POST
	@Path("/createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon coupon) throws Exception {

		CompanyFacade companyFacade = getCompanyFacade();
		
		System.out.println("u r in the web service resource");
		System.out.println(coupon);
		
		try {
			companyFacade.createCoupon(coupon);
		} catch (Exception e) {
			System.out.println("createCoupon failed : " + e.toString());
			throw e;
		}

	}	
	
	
//update a coupon
	/**
	 * method receives a coupon from the client matches it by its id and updates
	 * it's fileds
	 * @param coupon -jason
	 * @throws Exception
	 */
	@PUT
	@Path("/updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public void updateCoupon(Coupon coupon) throws Exception {
		System.out.println("updateCoupon " + coupon);

		CompanyFacade companyFacade = getCompanyFacade();
		
		try {
			companyFacade.updateCoupon(coupon);
		} catch (Exception e) {
			System.out.println("updateCoupon failed : " + e.toString());
			throw e;
		}

	}
	
	//remove coupon
	/**
	 * method to delete a coupon from the database
	 * @param coupon - jason
	 * @throws Exception
	 */
	@POST
	@Path("/removeCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public void removeCoupon(Coupon coupon) throws Exception {
		System.out.println("removeCoupon " + coupon);

		CompanyFacade companyFacade = getCompanyFacade();
		
		try {
			companyFacade.removeCoupon(coupon);
		} catch (Exception e) {
			System.out.println("removeCoupon failed : " + e.toString());
			throw e;
		}

	}

} 