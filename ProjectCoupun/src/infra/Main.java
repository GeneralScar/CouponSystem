package infra;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

//import java.util.Scanner;
/**
 * 
 * @author Orchay
 *
 */
public class Main {

	public static void main(String[] args){
	
		Scanner sc = new Scanner(System.in);
		AdminFacade adminFacade = null;
		CustomerFacade customerFacade = null;
		CompanyFacade companyFacade = null;
		
		System.out.println("creating a recycable coupon");
		//Scanner sc = new Scanner(System.in);
		//System.out.println("couponType");
		//String scp =sc.next();
		String scp2 = "Camping";
		COUPON_TYPE cp2 = COUPON_TYPE.valueOf(scp2.toString());
		//System.out.println("setDateStart format yyyy-MM-dd");
		//Date ds = Date.valueOf(sc.next());
		Date ds2 = Date.valueOf("2019-01-01");

		//System.out.println("setDateEnd format yyyy-MM-dd");
		//Date de = Date.valueOf(sc.next());
		Date de2 = Date.valueOf("2020-03-03");

		//System.out.println("id");
		long id2 = 65;//sc.nextLong();
		//System.out.println("setAmount");
		//int Amount = (sc.nextInt());
		int Amount2 = 6;
//		System.out.println("set image on null for now");
		String setImage2 = "leaf.png";
		//System.out.println("setMessage");
	//	String message = (sc.next());
		String message2 = "soCamping";
		//System.out.println("setTitle");
		//String Title = (sc.next());
		String Title2 = "TentFor4";
		//System.out.println("setPrice");
	//	double Price = (sc.nextDouble());
		double Price2 = 79.99;
		Coupon coupon2 = new Coupon (id2,cp2,ds2,de2,message2,Price2,Amount2,setImage2,Title2);
	//	System.out.println(coupon.toString());
		
		
		System.out.println("creating a recycable coupon");
		//Scanner sc = new Scanner(System.in);
		//System.out.println("couponType");
		//String scp =sc.next();
		String scp = "Sports";
		COUPON_TYPE cp = COUPON_TYPE.valueOf(scp.toString());
		//System.out.println("setDateStart format yyyy-MM-dd");
		//Date ds = Date.valueOf(sc.next());
		Date ds = Date.valueOf("2018-01-01");

		//System.out.println("setDateEnd format yyyy-MM-dd");
		//Date de = Date.valueOf(sc.next());
		Date de = Date.valueOf("2018-03-03");

		//System.out.println("id");
		long id = 0;//sc.nextLong();
		//System.out.println("setAmount");
		//int Amount = (sc.nextInt());
		int Amount = 14;
//		System.out.println("set image on null for now");
		String setImage = "waff.png";
		//System.out.println("setMessage");
	//	String message = (sc.next());
		String message = "REALYNewUpdated";
		//System.out.println("setTitle");
		//String Title = (sc.next());
		String Title = "newTitle";
		//System.out.println("setPrice");
	//	double Price = (sc.nextDouble());
		double Price = 111.22;
		Coupon coupon = new Coupon (id,cp,ds,de,message,Price,Amount,setImage,Title);
		System.out.println(coupon.toString());
		//	sc.close();
		
		System.out.println("--------------------------------------------------");
		System.out.println("creating a recycable company");
		String password = "11991199";
		String compName = "recycableCompany";
		String email = "recycable@fun.com";
		Company recycableCompany = new Company(compName, 0, password, email);
		System.out.println(recycableCompany.toString());
		
		System.out.println("--------------------------------------------------");
		
		System.out.println("creating a recycable customer");
		
		String password2 = "11991199";
		String custName2 = "marco_rossi";
		Customer recycableCustomer = new Customer(0, custName2, password2);
		System.out.println(recycableCustomer.toString());
		
		
		
		
		try {
		CouponSystem cs = CouponSystem.getInstance();
		System.out.println("enter:\n 1 for admin \n 2 for customer \n 3 for company");
		
		int log = 0;
		log = sc.nextInt();
		
		switch (log)
		{
		case 1:			System.out.println("Enter userName:");
					//	String name = sc.next();
					//	String name = "jack";
					//	String name = "DailyPlanet";	
						String name1 = "Admin";
					//	String name = "DailyPlanet";
						System.out.println("Enter Password:");
					//	String pass = String.valueOf(sc.nextLine());
						//String pass = "123456";
						//String pass = "151689";
						String pass1 = "1234";
					
						System.out.println("Enter userType:");
						CLIENT_TYPE type1;
						
			
						type1 = CLIENT_TYPE.Admin;
							
						cs.login(name1, pass1, type1);  // sends received login data to be checked via the login method
													   // can not get to this row if connection data is false
						adminFacade = (AdminFacade) CouponSystem.getInstance().getLoginResult();
						break;
						
		case 2:				System.out.println("Enter userName:");
						//	String name = sc.next();
							String name2 = "jack_sparrow";
						//	String name = "DailyPlanet";	
						//	String name = "Admin";
						//	String name = "DailyPlanet";
							System.out.println("Enter Password:");
						//	String pass = String.valueOf(sc.nextLine());
							//String pass = "123456";
							String pass2 = "151689";
							//String pass = "1234";
						
							System.out.println("Enter userType:");
							CLIENT_TYPE type2;
							
			
							type2 = CLIENT_TYPE.Customer;
								
							cs.login(name2, pass2, type2);  // sends received login data to be checked via the login method
														   // can not get to this row if connection data is false
							customerFacade = (CustomerFacade) CouponSystem.getInstance().getLoginResult();
							break;
			
							
			
							
							
		case 3:					System.out.println("Enter userName:");
							//	String name = sc.next();
							//	String name = "jack";
								String name3 = "DailyPlanet";	
							//	String name = "Admin";
							//	String name = "DailyPlanet";
								System.out.println("Enter Password:");
							//	String pass = String.valueOf(sc.nextLine());
								String pass3 = "123456";
								//String pass = "151689";
							//	String pass = "1234";
							
								System.out.println("Enter userType:");
								CLIENT_TYPE type3;
								
			
								type3 = CLIENT_TYPE.Company;
									
								cs.login(name3, pass3, type3);  // sends received login data to be checked via the login method
															   // can not get to this row if connection data is false
								companyFacade = (CompanyFacade) CouponSystem.getInstance().getLoginResult();
								break;
		}
		
			
		
		switch (log)
		{
		case 1:					System.out.println("--------------------------------------------------");

								System.out.println("--------------COMPANY MANIPULATION----------------");
				
								System.out.println("--------------------------------------------------");

								System.out.println(adminFacade.getAllCompanies());
								System.out.println("--------------------------------------------------");
								adminFacade.createCompany(recycableCompany);
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getAllCompanies());
								System.out.println("--------------------------------------------------");
								System.out.println("enter the recycable company id from the table above");
								long RECcompanyid = sc.nextLong();
								recycableCompany.setCompId(RECcompanyid);
								System.out.println(adminFacade.getCompany(RECcompanyid));
								System.out.println("--------------------------------------------------");
								System.out.println("setting the recycable company password and email to 'updated'");
								recycableCompany.seteMail("updated");
								recycableCompany.setPassword("updated");
								adminFacade.updateCompany(recycableCompany);								
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getCompany(RECcompanyid));
								System.out.println("--------------------------------------------------");
								adminFacade.removeCompany(recycableCompany);
								System.out.println("company has been removed");
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getAllCompanies());
								System.out.println("--------------------------------------------------");
								System.out.println("exemple of company with coupons - DailyPlanet");
								Company longRegComp = adminFacade.getCompany(2);
								System.out.println("--------------------------------------------------");
								System.out.println(longRegComp.toString());
								System.out.println("--------------------------------------------------");
								
								System.out.println("--------------------------------------------------");

								System.out.println("--------------CUSTOMER MANIPULATION---------------");
				
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getAllCustomers());
								System.out.println("--------------------------------------------------");
								adminFacade.createCustomer(recycableCustomer);
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getAllCustomers());
								System.out.println("--------------------------------------------------");
								System.out.println("enter the recycable customer id from the table above");
								long RECcustomerid = sc.nextLong();
								recycableCustomer.setCustId(RECcustomerid);
								System.out.println(adminFacade.getCustomer(RECcustomerid));					
								System.out.println("--------------------------------------------------");
								System.out.println("setting the recycable customer password to 'updated'");
								recycableCustomer.setPassword("updated");
								adminFacade.updateCustomer(recycableCustomer);
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getCustomer(RECcustomerid));					
								System.out.println("--------------------------------------------------");
								adminFacade.removeCustomer(recycableCustomer);
								System.out.println("customer has been removed");
								System.out.println("--------------------------------------------------");
								System.out.println(adminFacade.getAllCustomers());
								System.out.println("--------------------------------------------------");
								System.out.println("exemple of customer with coupons - jack_Sparrow");
								System.out.println(adminFacade.getCustomer(1));
								System.out.println("--------------------------------------------------");
								System.out.println("--------------------------------------------------");
								sc.close();
								break;			
		
		
		case 2:					long customer_id = customerFacade.getLoggedCustomer().getCustId();
								System.out.println("loged as customer :"+customer_id);
								System.out.println("-------------------------------------");
								System.out.println(customerFacade.getAllPurchesedCoupons());
								System.out.println("-------------------------------------");
								customerFacade.purchaseCoupon(coupon2);
								System.out.println("-------------------------------------");
								System.out.println(customerFacade.getAllPurchesedCoupons());
								System.out.println("-------------------------------------");
								System.out.println("-------------------------------------");
								System.out.println("enter the max coupon price");
								double couponMaxPrice = sc.nextDouble();
								System.out.println("-------------------------------------");
								System.out.println(customerFacade.getAllPurchesedCouponsByPrice(couponMaxPrice));
								System.out.println("-------------------------------------");
								System.out.println("set to return only coupons of food type");
								System.out.println(customerFacade.getAllPurchesedCouponsByType(COUPON_TYPE.Food));
								System.out.println("-------------------------------------");
								sc.close();
								break;
								
		case 3:					
								System.out.println("------------------getAllCoupons-------------------");
								System.out.println(companyFacade.getAllCoupons());
								System.out.println("-------------------creating coupon (recycled)------------------");
								companyFacade.createCoupon(coupon);
								System.out.println("--------------------getAllCoupons reccycled added-----------------");
								System.out.println(companyFacade.getAllCoupons());
								System.out.println("-------------------------------------");
								System.out.println("insert the new coupon's id from the table above");
								long coupon_id = sc.nextLong();
								System.out.println("--------------------getting coupon by id generated-----------------");
								Coupon coupon2UpDate = companyFacade.getCoupon(coupon_id);
								System.out.println("update coupon's amount and price");
								System.out.println("-------------------------------------");
								System.out.println("insert a double for price");
								coupon2UpDate.setPrice(sc.nextDouble());
								System.out.println("--------------------setting coupon's price-----------------");
								System.out.println("insert an integr for amount");
								System.out.println("-------------------------------------");
								coupon2UpDate.setAmount(sc.nextInt());
								System.out.println("--------------------setting coupon's amount in stock-----------------");
								companyFacade.updateCoupon(coupon2UpDate);
								System.out.println("-----------------updating coupon in database--------------------");
								System.out.println(companyFacade.getAllCoupons());
								System.out.println("------------------getAllCoupons-------------------");
								companyFacade.removeCoupon(coupon2UpDate);
								System.out.println("-----------------removing coupon after update--------------------");
								System.out.println(companyFacade.getAllCoupons());
								System.out.println("--------------------getAllCoupons-----------------");
								System.out.println("returns company food type coupons");
								System.out.println(companyFacade.getCouponsByType(COUPON_TYPE.Food));
								System.out.println("-------------------------------------");
								sc.close();
								break;
		}
								
				

		
			//CouponSystem.getInstance().userLogin();
			//Customer cust = adminFacade.getCustomer(3);
		//	Customer cust = adminFacade.getCustomer(4);
//			System.out.println(adminFacade.getAllCompanies());
//			System.out.println("-------------------------------------");
//			System.out.println(adminFacade.getAllCustomers());
//			System.out.println("------------------------------------");
//			System.out.println(adminFacade.getCouponDBDAO().getAllCoupons());
//			//cust.setPassword("Argentina");
			//adminFacade.removeCustomer(cust);
			//System.out.println(cust);
			//System.out.println(cust);
			//System.out.println(adminFacade.getCustomer(2));
	//		System.out.println(companyFacade.getCouponsByType(COUPON_TYPE.Camping.Sports));
			//Collection<Company> allCompanies=adminFacade.getAllCompanies();
			//System.out.println(allCompanies);
			//Company comp = adminFacade.getCompany(43);
			//adminFacade.removeCompany(comp);
			//comp.seteMail("WEF?WEF?WTF");
			//adminFacade.removeCompany(comp);
			//System.out.println(comp);
			//long bla = CouponSystem.getInstance().getLoggedID();
			//Collection<Coupon> couponR=compFacade.getCoupons();
			//System.out.println(couponR);
			
			//allCompanies=adminFacade.getAllCompanies();
			//System.out.println(allCompanies);
			//Collection<Customer> allCustomers=adminFacade.getAllCustomers();
			//System.out.println(allCustomers);
			//ArrayList<Coupon> ca = new ArrayList<>();
			//compFacade.updateCoupon(coupon);
			//System.out.println(ca.toString());
			//ca=Facade.getAllPurchesedCoupons();
			//System.out.println(ca.toString());
			//System.out.println(couponR.toString());
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
		