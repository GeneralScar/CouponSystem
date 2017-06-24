package loginServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.LogginErrorExcption;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;
import infra.CLIENT_TYPE;
import infra.CouponSystem;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * post method of servlet
	 * receive user name password and a client type  
	 * compare received data with database
	 * assign a facade to the session (attribute)- if user is registered and entered the right parameters
	 * redirect to the correct SPA or to a login error page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("nameBox");
		String password = request.getParameter("passBox");
		String clientString = request.getParameter("client");
		CLIENT_TYPE type = null;
		
		switch (clientString)
		{
		case "Admin": type = CLIENT_TYPE.Admin; break;
		case "Customer": type = CLIENT_TYPE.Customer; break;
		case "Company":	type = CLIENT_TYPE.Company; break;
		default: type = null; break;
		}


		CouponClientFacade ccf = null;
		
		
		String redirectURL ="http://localhost:8080/projectCoupons/loginError.html";
		
		switch (clientString)
			
		{
				case "Admin": 				AdminFacade adFacade= null;
											try {
												adFacade = (AdminFacade)CouponSystem.getInstance().login(name, password, type);
											} catch (ClassNotFoundException | SQLException | InterruptedException | LogginErrorExcption e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											};								
											
											if (adFacade!=null)
											{
												request.getSession().setAttribute("currentFacade", adFacade);
												redirectURL = "http://localhost:8080/projectCoupons/admin/admin.html";
											}
											else
											{
												System.out.println("failed to log as an Administrator");
											}
											break;
									
											
				case "Customer": 			CustomerFacade custFacade= null;
											try {
												custFacade = (CustomerFacade)CouponSystem.getInstance().login(name, password, type);
											} catch (ClassNotFoundException | SQLException | InterruptedException | LogginErrorExcption e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}				
											if(custFacade!=null)
											{	
												request.getSession().setAttribute("currentFacade", custFacade);
												redirectURL = "http://localhost:8080/projectCoupons/customer/customer.html";
											}
											else
											{
												System.out.println("failed to log as a Customer client");
											}
											break;
						
												
				case "Company": 			CompanyFacade compFacade= null;
											try {
												compFacade = (CompanyFacade)CouponSystem.getInstance().login(name, password, type);
												System.out.println(compFacade);

											} catch (ClassNotFoundException | SQLException | InterruptedException | LogginErrorExcption e) 
											{
												e.printStackTrace();
											}
											if(compFacade!=null)
											{
												
												request.getSession().setAttribute("currentFacade", compFacade);
							//					System.out.println((request.getSession().getAttribute("currentFacade")));
												redirectURL = "http://localhost:8080/projectCoupons/company/company.html";
											}
											else
											{
												System.out.println("failed to log as a Company client");
											}
											break;
								
				default:						redirectURL ="http://localhost:8080/projectCoupons/loginError.html";
											break;
		}
		
		System.out.println((request.getSession().getAttribute("currentFacade")));
		response.sendRedirect(redirectURL);
		
	}	

	

}
