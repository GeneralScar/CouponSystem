package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Scanner;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import exceptions.CompanyAlreadyExist;
import exceptions.CompanyNotFoundExcption;
import exceptions.LogginErrorExcption;
import infra.COUPON_TYPE;
import infra.Company;
import infra.ConnectionPool;
import infra.Coupon;
/**
 * Class CompanyDBDAO communicates between the CompanyFacade and SQL
 * @author Orchay
 *
 */

public class CompanyDBDAO implements CompanyDAO
{
	private Company loggedCompany = null;
	private long loggedID = 0;
	/**
	 * method CompanyDBDAO is an empty constructor
	 */
	// edited in 20.1 with notes			
	public CompanyDBDAO() // empty constructor
	{

	}
	/**
	 * sets the private member loggedComopany
	 * @param loggedCompany
	 */
	// edited in 20.1 with notes	
	public void setLoggedCompany(Company loggedCompany) // sets the private member loggedComopany
	{
		this.loggedCompany = loggedCompany;
	}
	
	/**
	 * returns the private member loggedCompany
	 * @return
	 */
	// edited in 20.1 with notes	
	public Company getLoggedCompany() { // returns the private member loggedCompany
		return loggedCompany; 			// returns the private member loggedCompany
	}

	/**
	 * returns the private member loggedCompany's id (class Company)
	 * @return
	 */
	// edited in 20.1 with notes	
	public long getLoggedID() // returns the private member loggedCompany's id (class Company)
	{
		return loggedID;
	}

	/**
	 * sets private member loggedID
	 * @param loggedID
	 */
	// edited in 20.1 with notes	
	public void setLoggedID(long loggedID) // sets private member loggedID
	{
		this.loggedID = loggedID;
	}

	/**
	 *  login method to check if the company user name and password matches 
	 */
	// edited in 20.1 with notes
	@Override
	public boolean login(String compName, String password) throws SQLException, ClassNotFoundException, InterruptedException, LogginErrorExcption { // login method to check if the company user name and password matches 
				
		Boolean bool = false;					  //  boolean flag declaration and initializing as false
		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		java.sql.Statement stmt;				  //  Statement Declaration
		Company company = null;					  //  initializing a company as null
				
			con = ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// check if the company user name and password exist - match against the company table at the database
			// execute query saves result in rs
			rs = stmt.executeQuery("select * from coupon_project.company where PASSWORD ='"+password+
					"' and COMP_NAME ='"+compName+"'");

			// check if there is a result inserted to rs
			// if rs == null it means that there is no company by that name registered and its possible
			// to insert a new company by that name
			if (rs.next()==true) 
			{	
				// get the logged company and sets the private logged company member 
				
				long comp_id = rs.getLong("comp_id");
				String compNameSQL = rs.getString("comp_name");
				String Password = rs.getString("password");
				String eMail = rs.getString("email");
				company = new Company (compNameSQL, comp_id, Password, eMail); // creates a company
				loggedCompany = company; // sets the private members
				
				
				ConnectionPool.getInstance().returnConnection(con); // returns the connection

				bool = true; // sets true for the login
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con); //  returns the connection
				bool = false; // sets false for the login
				throw new LogginErrorExcption ("ilegal company loggin");
			}
			
		
		return bool; // returns the boolean login result
	}
	
	/**
	 * returns a company by inserted ID number for Admin Facade
	 */
	// edited in 20.1 with notes	
	public Company getCompany(long comp_id) throws SQLException, ClassNotFoundException, InterruptedException, CompanyNotFoundExcption  // returns a company by inserted ID number for Admin Facade

	{
		
		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		Statement stmt;							  //  Statement Declaration
		Company comp = null;					  //  initializing a company as null
				
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// Run the query - gets company fields into rs
			rs = stmt.executeQuery("select * FROM coupon_project.company where comp_id ="+ comp_id);
			
			// loop create company and set it as logged company
			if ( rs.next() ) 
			{
			    comp_id = rs.getLong("comp_id");
			    String compName = rs.getString("comp_name");
			    String Password = rs.getString("password");  ;
			    String eMail = rs.getString("eMail");
			    comp = new Company( compName, comp_id, Password, eMail);
			    loggedCompany = comp;
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
				throw new CompanyNotFoundExcption(" failed to find company in DataBase (by ID)");
			}
			ConnectionPool.getInstance().returnConnection(con);

			return comp;
	}
	
	/**
	 * returns all register companies within the system for Admin Facade
	 */
	// edited in 20.1 with notes
	public ArrayList<Company> getAllCompanies() throws SQLException, ClassNotFoundException, InterruptedException //  returns all register companies within the system for AdminFacade

	{
		ArrayList<Company> allCompanies = new ArrayList<>(); //  initialize array to contain all the register companies of the system

		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		Statement stmt;							  //  Statement Declaration
			
			
				con =ConnectionPool.getInstance().getConnection();  //  connection request
				stmt = con.createStatement();						//  statement initializing
				
				// gets result of all the companies from table company - execute a query statement, insert result to rs
			    rs = stmt.executeQuery("SELECT * FROM coupon_project.company");
	        
		        while ( rs.next() )  // loop to insert company values into an arrayList 
		        {
		            long comp_id = rs.getLong("comp_id");
		            String name = rs.getString("COMP_name");
		            String password = rs.getString("password");
		            String eMail = rs.getString("EMAIL");
		    		
		            Company comp = new Company(name, comp_id, password, eMail); // creates a company from rs data
		    		
		    		allCompanies.add(comp);  // adds company  to an ArrayList
		    		
		        }
				ConnectionPool.getInstance().returnConnection(con);

		return allCompanies; // returns ArrayList of all companies registered in the system
	}
	
	/**
	 * returns all the coupons of the logged company
	 */
	// edited in 20.1 with notes
	public ArrayList<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException /// function that returns all the coupons of the logged company

	{
		ArrayList<Coupon> companyCoupon = new ArrayList<>(); //  initialize ArrayList to contain all the coupons of logged company
		ArrayList<Long> companyCouponID = new ArrayList<>(); //  initialize ArrayList to contain all the coupons' ID of the logged company
		Statement stmt; 									 //  Statement Declaration 
		ResultSet rs;										 //  result set from SQL variant declaration
		long coupID = 0;	 								 //  variant to contain coupons Id's
		Connection con = null;								 //  connection declaration and initializing as null
		
				con =ConnectionPool.getInstance().getConnection();  //  connection request
				stmt = con.createStatement();						//  statement initializing
		
				// gets result of all the coupons ID's  of the logged company (by company ID) from the join table company_coupon
				// execute a query statement, insert result to rs
				rs = stmt.executeQuery("SELECT * FROM coupon_project.company_coupon where comp_id="
					+ getLoggedCompany().getCompId());
				
				// loop to input query result into ArrayList of ID
				while  ((rs!=null)&&(rs.next())) // check if there result is not null (company has coupons in the system)
				{
					coupID = rs.getLong("coup_id");
					
					companyCouponID.add (coupID);
				}

				
				
				Iterator<Long> myIterator = companyCouponID.iterator();  // iterator that runs on the coupons ID ArrayList
				
				while (myIterator.hasNext())  
				{
					Long couponID = myIterator.next(); // gets next coupon ID
					
					// query to get coupons by ID 
					// execute a query statement, insert result to rs
					rs = stmt.executeQuery("SELECT * FROM coupon_project.coupon where coupon_id="
							+ couponID);
					
					while (rs.next()) // loop to get query result (coupon) and insert into ArrayList of coupons
					{
						long coupon_id = rs.getLong("coupon_id");
						String title = rs.getString("TITLE");
						Date dateStart = rs.getDate("START_DATE"); 
						Date dateEnd =  rs.getDate("END_DATE");
						int amount = rs.getInt("AMOUNT");
						COUPON_TYPE type = COUPON_TYPE.valueOf(rs.getString("TYPE"));
						String msg = rs.getString("MESSAGE");
						double price = rs.getDouble("PRICE");
						String img = rs.getString("IMAGE");
	           
						// creating a coupon from the result set
						Coupon couponsOf = new Coupon(coupon_id, type, dateStart,dateEnd,msg ,price,
											amount, img, title);
	            
						companyCoupon.add(couponsOf);		//  adds coupon to ArrayList of coupons of company
					}
				}
				ConnectionPool.getInstance().returnConnection(con);  // returns connection

				return companyCoupon;  // returns the array of coupons of the logged company
			}
	
	// edited in 20.1 with notes	
	@Override
	public void createCompany(Company company) throws CompanyAlreadyExist, ClassNotFoundException, SQLException, InterruptedException// gets a company and insert it to the database

	{
		Statement stmt; 									 //  Statement Declaration 
		ResultSet rs;										 //  result set from SQL variant declaration
		Connection con = null;								 //  connection declaration and initializing as null
		java.sql.PreparedStatement preparedStmt=null;		 //  Initializing a prepared statement
		String query = "";									 //  declaring and initializing a string query
		
		
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// gets result of a company from table company by name - execute a query statement, insert result to rs
			rs=stmt.executeQuery("select * FROM coupon_project.company where comp_name ='"+company.getCompName()+"'");
	    	
			// check if there is a result inserted to rs
			// if rs == null it means that there is no company by that name registered and its possible
			// to insert a new company by that name
			if ( (rs.next())) 
	    	{
				ConnectionPool.getInstance().returnConnection(con);

	    		throw new CompanyAlreadyExist(company.getCompName() + " already exists");
	
	    	}
	    	else
	    	{
	    		// string to insert a company into table company database for a prepared statement 
				query = "INSERT INTO coupon_project.company (comp_name , PASSWORD , Email ) VALUES (?,?,?)";
	
				preparedStmt = (PreparedStatement) con.prepareStatement(query);
	
				preparedStmt.setString 	 (1, company.getCompName());
				preparedStmt.setString   (2, company.getPassword());
				preparedStmt.setString   (3, company.getEmail());
		
				preparedStmt.execute();	// execute the prepared statement
	    	}
			
			ConnectionPool.getInstance().returnConnection(con);
		
				
	}
	
	// edited in 20.1 with notes	
	// edited with notes 20.1
	/**
	 * removes a company and its coupons from the system
	 */
	@Override
	public void removeCompany(Company company) throws SQLException, ClassNotFoundException, InterruptedException, CompanyNotFoundExcption {  /// removes a company and its coupons from the system

		java.sql.PreparedStatement stmt=null;	  //  prepared Statement Declaration
		Connection con= null;					  //  connection declaration and initializing as null
		
	
	
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			
			// string query declaring and initializing
			// remove company id
			Company checkCompany = this.getCompany(company.getCompId());
			
			if (checkCompany != null)
			{
        	String query = "delete FROM coupon_project.company where comp_id ="+company.getCompId();
        
			stmt = con.prepareStatement(query); //  statement initializing
			stmt.execute(); // execute statement
			
			// string query override
			// removes the coupons of the company that being removed from the join table company_coupon
        	query = "delete FROM coupon_project.company_coupon where comp_id ="+company.getCompId();
        	
			stmt = con.prepareStatement(query);//  statement override
			stmt.execute(); // execute statement
			}
			else
			{
	    		throw new CompanyNotFoundExcption(company.getCompName() + " not found in database"); 
			}
			ConnectionPool.getInstance().returnConnection(con);
	}


	/**
	 * updates a company info may not change its ID and name
	 */
	// edited with notes 20.1
	@Override
	public void updateCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundExcption { /// updates a company info may not change its ID
		
		java.sql.PreparedStatement stmt=null;		  //  prepared Statement Declaration
		Connection con= null;					  	  //  connection declaration and initializing as null
		ResultSet rs = null;						  //  result set from SQL variant declaration
		String query = null;

		
				con =ConnectionPool.getInstance().getConnection();  //  connection request
				query = "select * FROM coupon_project.company where comp_name ='"+company.getCompName()+"'";
				stmt = con.prepareStatement(query);						//  statement initializing
				
	            rs = stmt.executeQuery();
			
		    	if ( rs.next() ) 
		    	{			
		    		
				// query to fill with preparedStatement and the execute
				// query to update an existing company 
			    query = "update coupon_project.company set Comp_name = ?, Password = ?, eMail =? "
	    				+ " where comp_id ="+ company.getCompId();
	    		
			    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
				preparedStmt.setString 	 (1, company.getCompName());
				preparedStmt.setString   (2, company.getPassword());
				preparedStmt.setString   (3, company.getEmail());
	
				 preparedStmt.execute(); // execute prepared statement
	
	    		ConnectionPool.getInstance().returnConnection(con);  // returns the connection
	    		}
		    	else
		    	{
					ConnectionPool.getInstance().returnConnection(con);

		    		throw new CompanyNotFoundExcption(company.getCompName() + " not found");
		    		
		    	}
	}
	
}
