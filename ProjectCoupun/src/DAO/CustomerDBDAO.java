package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import infra.COUPON_TYPE;
import infra.Company;
import infra.ConnectionPool;
import infra.Coupon;
import infra.Customer;
import exceptions.CouponAlreadyPurchasedException;
import exceptions.CouponSoldOutException;
import exceptions.CustomerNotFoundExcption;
import exceptions.LogginErrorExcption;

/**
 * Class CustomerDBDAO communicates between the CustomerFacade and SQL
 * @author Orchay
 *
 */
public class CustomerDBDAO implements CustomerDAO
{
	private Customer myCoustomer = null;
	private long loggedID=0;
	
	Connection con =null;

	//1 edited in 20.1 with notes			
	public CustomerDBDAO()  //  empty constructor
	{
	}
	
	//2 edited in 20.1 with notes			
	public Customer getMyCoustomer() // returns private member of logged customer
	{
		return myCoustomer;
	}

	//3 edited in 20.1 with notes			
	@Override
	public boolean login(String name, String password) throws ClassNotFoundException, SQLException, InterruptedException // login method to check if the customer user name and password matches
, LogginErrorExcption
	{
		Boolean bool = false;					  //  boolean flag declaration and initializing as false
		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		java.sql.Statement stmt;				  //  Statement Declaration


			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// check if the customer user name and password exist - match against the customer table at the database
			// execute query saves result in rs
			rs = stmt.executeQuery("SELECT * FROM coupon_project.customer where PASSWORD ='"+password+
					"' and CUST_NAME ='"+name+"'");
			
			// check if there is a result inserted to rs
			// if rs == null it means that there is no customer by that name registered and its possible
			// to insert a new customer by that name
			if (rs != null && rs.next()) 
			{	
					loggedID = rs.getLong("cust_id");
					String custName = rs.getString("cust_name");
		            String Password = rs.getString("password");
		            this.myCoustomer = new Customer(loggedID,custName, Password);
				
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
				bool = true;	// sets flag as true
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con);  // returns the connection 
				bool = false;  // sets flag as false
				throw new LogginErrorExcption ("user name "+ name + " is already registered");
			}
				
	return bool;  // returns the boolean login result
	}

	//4 edited in 20.1 with notes			
	@Override
	public void createCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption {  // insert a new customer to the database
		
		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		Statement stmt;							  //  Statement Declaration

			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// check if the customer user name and password exist - match against the customer table at the database
			// execute query saves result in rs
			rs=stmt.executeQuery("select * FROM coupon_project.customer where cust_name ='"+customer.getCustName()+"'");
	    	
			// check if there is a result inserted to rs
			// if rs == null it means that there is no customer by that name registered and its possible
			// to insert a new customer by that name
	    	if ( !rs.next() ) 
	    	{
			// string to insert a company into table company database for a prepared statement 
				String query = "INSERT INTO coupon_project.customer (cust_name , PASSWORD) VALUES (?,?)";
	
				PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
				
				preparedStmt.setString 	 (1, customer.getCustName());
				preparedStmt.setString   (2, customer.getPassword());
		
				preparedStmt.execute();	// execute the prepared statement	
				ConnectionPool.getInstance().returnConnection(con);

	    	}
	    	else
	    	{
				ConnectionPool.getInstance().returnConnection(con);  // returns connection
	    		throw new CustomerNotFoundExcption(customer.getCustName() + " can not be created, user name is taken");	
	    	}

	}
	
	//5 edited in 20.1 with notes			
	@Override
	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption {  //  get a customer and removes it from the database
		
		java.sql.PreparedStatement stmt=null;	  //  prepared Statement Declaration and initializing as null
		Connection con= null;					  //  connection declaration and initializing as null
		ResultSet rs;							  //  result set from SQL variant declaration
		String query = null;					  //  initializing a query as null


			
			con =ConnectionPool.getInstance().getConnection();  //  connection request

			query = "select * FROM coupon_project.customer where cust_name = '"+customer.getCustName()+"'";
			stmt = con.prepareStatement(query);		
			
            rs = stmt.executeQuery();

	    	if ( rs.next() ) 
	    	{			
				// string query declaring and initializing
				// remove customer id from table customer
	        	query = "delete FROM coupon_project.customer where cust_id ="+customer.getCustId();
	        	
				stmt = con.prepareStatement(query);  //  prepared statement override
				stmt.execute();  //  statement execute
				
				// string query override
				// remove customer id from table customer_coupon
	        	query = "delete FROM coupon_project.customer_coupon where cust_id ="+customer.getCustId();
	        	
				stmt = con.prepareStatement(query);  //  prepared statement override
				stmt.execute();  //  statement execute
				
	        	ConnectionPool.getInstance().returnConnection(con);  // returns connection
	    	}
	    	else
	    	{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
	    		throw new CustomerNotFoundExcption ("customer "+ customer.getCustName() + " cant be found in database");
	    	}
	}

	//6 edited in 20.1 with notes			
	@Override
	public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption {  //  get a customer and update in the database by ID

		java.sql.PreparedStatement stmt=null;				 //  prepared statement declaration and initializing as null
		Connection con = null;								 //  connection declaration and initializing as null
		ResultSet rs = null;			   				     //  result set from SQL variant declaration
		String query = null;


			con =ConnectionPool.getInstance().getConnection();  //  connection request
			
			
			query = "select * FROM coupon_project.customer where cust_name = '"+customer.getCustName()+"'";
			stmt = con.prepareStatement(query);		
			
            rs = stmt.executeQuery();

	    	if ( rs.next() ) 
	    	{			
	    		
					// initializing the query for a prepared statement to update a customer
					query = "update coupon_project.customer set cust_name = ?, PASSWORD =? "
				    				+ " where cust_id ="+ customer.getCustId();
		
					
				    PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
					preparedStmt.setString   (1, customer.getCustName());
					preparedStmt.setString   (2, customer.getPassword());
		
					preparedStmt.execute();  //  execute the prepared statement query
					
					 
					ConnectionPool.getInstance().returnConnection(con); //  returns connection
	    	}
	    	else
	    	{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
	    		throw new CustomerNotFoundExcption ("customer "+ customer.getCustName() + " cant be found in database");
	    	}
	}

	//7 edited in 20.1 with notes			
	@Override
	public Customer getCustomer(long customer_id)throws CustomerNotFoundExcption, ClassNotFoundException, SQLException, InterruptedException // returns a customer (by customer ID) from the table customer

	{
		Customer cust = null;							 //  customer declaration and initializing as null
		Statement stmt = null;							 //  prepared statement declaration and initializing as null
		Connection con = null;							 //  connection declaration and initializing as null
		loggedID = customer_id;

			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();	
		
			// gets result of a customer fileds (by customer ID) from the table customer
			// execute a query statement, insert result to rs
			ResultSet rs=stmt.executeQuery("select * FROM coupon_project.customer where cust_id ="+customer_id);
    	
			//  loop to create customer instance from fields
			if (rs!=null && rs.next() ) 
			{
				long cust_id = rs.getLong("cust_id");
				String custName = rs.getString("cust_name");
				String Password = rs.getString("password");
				cust = new Customer(cust_id,custName, Password);  //  creates a new customer
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
				throw new CustomerNotFoundExcption ("coustomer "+ customer_id +"can not  be found");
			}
			
    	ConnectionPool.getInstance().returnConnection(con); 	
	 
		return cust;  //  returns customer found by given ID
	}	

	//8 edited in 20.1 with notes			
	@Override
	public ArrayList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException, InterruptedException { //  returns all customers registered in the system
		
		ArrayList<Customer> allCustomers = new ArrayList<>();  //  ArrayList customer declaration and initializing
		Statement stmt = null;								   //  prepared statement declaration and initializing as null
		Customer cust = null;							 	   //  customer declaration and initializing as null
		Connection con = null;				  				   //  connection declaration and initializing as null
		ResultSet rs;										   //  result set from SQL variant declaration

		
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing

			// gets result of all the coupons registered in the system
			// execute a query statement, insert result to rs
		    rs = stmt.executeQuery("SELECT * FROM coupon_project.customer");
        
	        while ( rs.next()) 
	        {
	    		long cust_id = rs.getLong("cust_id");
				String custName = rs.getString("cust_name");
	            String Password = rs.getString("password");
	            cust = new Customer(cust_id,custName, Password);  // creates a coupon from received coupon fields
	            allCustomers.add(cust);  //  adds created coupon to a collection of coupon
	        }
			ConnectionPool.getInstance().returnConnection(con);  // returns connection

	return allCustomers;  /// returns all the coupons registered in the system
	}

	//9 edited in 20.1 with notes			
	@Override
	public ArrayList<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException // returns all coupons purchased by the customer
	{ 
		ArrayList<Coupon> customerCoupon = new ArrayList<>();  //  ArrayList customer declaration and initializing
		ArrayList<Long> customerCouponID = new ArrayList<>();  //  ArrayList customer declaration and initializing
		Statement stmt = null;								   //  prepared statement declaration and initializing as null
		Connection con = null;				  				   //  connection declaration and initializing as null
		ResultSet rs;										   //  result set from SQL variant declaration

		long coupID = 0;									   //  coupon id declaration and initializing
				
				con =ConnectionPool.getInstance().getConnection();  //  connection request
				stmt = con.createStatement();						//  statement initializing
		
				// gets result of all the coupons IDs 
				// execute a query statement, insert result to rs
				rs = stmt.executeQuery("SELECT * FROM coupon_project.customer_coupon where cust_id="
					+ loggedID);
				
				while  ((rs!=null)&&(rs.next()))  //  loop to insert values of coupons IDs to an ArrayList
				{
					coupID = rs.getLong("coupon_id");
					
					customerCouponID.add (coupID);
				}
				 
				Iterator<Long> myIterator = customerCouponID.iterator();  // iterator to pass over all values of the coupons ID
				
				while (myIterator.hasNext())  
				{
					Long couponID = myIterator.next();  //  takes next value from iterator
					
					// gets result of each coupon by ID  
					// execute a query statement, insert result to rs
					rs = stmt.executeQuery("SELECT * FROM coupon_project.coupon where coupon_id="
							+ couponID);
					
					while (rs.next()) 
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
	           
						// creates coupon by received coupon fields
						Coupon couponsOf = new Coupon(coupon_id, type, dateStart,dateEnd,msg ,price,
											amount, img, title);
						
						customerCoupon.add(couponsOf);  //  inserts the created coupons to a collection contains all of the customer
														// purchased coupons
						}
				}
				
				ConnectionPool.getInstance().returnConnection(con);  //  returns connection

				return customerCoupon;  // returns the collection contains all of the logged customer purchased coupons
	}

	//10 edited in 20.1 with notes			
	public void purchaseCoupon(Coupon coupon) throws SQLException, ClassNotFoundException, InterruptedException, CouponAlreadyPurchasedException, CouponSoldOutException 
	{

		Statement stmt = null;								   //  prepared statement declaration and initializing as null
		Connection con = null;				  				   //  connection declaration and initializing as null
		ResultSet rs;										   //  result set from SQL variant declaration
			
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// gets result if the customer has bought the requested coupon before 
			// execute a query statement, insert result to rs
			rs = stmt.executeQuery("SELECT * FROM coupon_project.customer_coupon WHERE cust_id = " 
					+ myCoustomer.getCustId() + " AND coupon_id = " + coupon.getCouponId());
			
				if (rs.next())
		        {
					ConnectionPool.getInstance().returnConnection(con); // returns the connection
					throw new CouponAlreadyPurchasedException ("coustomer "+ loggedID + " have already purchesed coupon "+ coupon.getTitle());
		        }   
			
				else
		        {
			
					if (coupon.getAmount() > 0)
					{
						// string query0 declaration and initializing
						// inserts the coupons ID and the company that produced them ID into the join table company_coupon
			    	    String query0 = "INSERT INTO coupon_project.customer_coupon (cust_id, coupon_id) VALUES (" + myCoustomer.getCustId() + ", " + coupon.getCouponId() + ")";
						
						// string query1 declaration and initializing
						// updates the coupon's amount in the database
			    	    String query1 = "UPDATE coupon_project.coupon SET amount = " + (coupon.getAmount() - 1) + " WHERE coupon_id = " + coupon.getCouponId();
			    	   
			    	    stmt.addBatch(query0); //  insert the query into a batch to be executed
	
			    	    stmt.addBatch(query1); //  insert the query into a batch to be executed
			    	    stmt.executeBatch();   //  executes all the queries added to the batch 
					}
					else
					{				
						ConnectionPool.getInstance().returnConnection(con); // returns the connection
						throw new CouponSoldOutException ("coupon "+ coupon.getTitle()+" is out of stock");
					}
		       }
		       
				ConnectionPool.getInstance().returnConnection(con);  // returns connection
		       
		}
	}
