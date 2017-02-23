package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

import com.mysql.jdbc.PreparedStatement;

import exceptions.CouponAlreadeyRegistered;
import exceptions.CouponNotFoundExcption;
import infra.COUPON_TYPE;
import infra.ConnectionPool;
import infra.Coupon;

/**
 * Class CouponDBDAO communicates between the CouponFacade and SQL
 * @author Orchay
 *
 */
public class CouponDBDAO implements CouponDAO
{

	private long loggedID;

	// edited in 20.1 with notes			
	public CouponDBDAO() 				    // empty constructor
	{

	}
	
	// edited in 20.1 with notes			
	public long getLoggedID()			    //  returns private member of the logged ID
	{
		return loggedID;
	}

	// edited in 20.1 with notes			
	public void setLoggedID(long loggedID)  //  set the private member logged ID to the given ID 
	{
		this.loggedID = loggedID;
	}

	// edited in 20.1 with notes			
	@Override
	public void createCoupon(Coupon coupon) throws CouponAlreadeyRegistered // gets a coupon of a company and insert it into the database
, ClassNotFoundException, SQLException, InterruptedException
	{
		String query=null;								//  query String declaration and initialization	
		Connection con = null;							//  connection declaration and initialization as null
		Statement stmt;									//  statement declaration
		COUPON_TYPE ct = coupon.getCouponType();		//  coupon type declaration and initialization
		ResultSet rs = null;							//  result set declaration and initialization as null 
		long coupID = 0;								//  coupon ID declaration and initialization
		
		
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
        	
			// Run the query - inserts coupon fields into rs
			rs = stmt.executeQuery("select * FROM coupon_project.coupon where title ='"+coupon.getTitle()+"'");

			if (rs.next())  // check  if the coupon exists before attempting to insert it into the database
	        	{
					ConnectionPool.getInstance().returnConnection(con); // returns the connection
	        		throw new CouponAlreadeyRegistered("the coupon has already been created / this title is already taken ");
	        	}
	        	else
	        	{
					// string to insert a coupon into table coupon database for a prepared statement 
		        	query = "INSERT INTO coupon_project.coupon ( TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE)"
	        				+ "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
		        	
					java.sql.PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);

					preparedStmt.setString (1, coupon.getTitle());
					preparedStmt.setDate   (2, coupon.getDateStart());
					preparedStmt.setDate   (3, coupon.getDateEnd());
					preparedStmt.setInt    (4, coupon.getAmount());
					preparedStmt.setString (5, ct.toString());
					preparedStmt.setString (6, coupon.getMessage());
					preparedStmt.setDouble (7, coupon.getPrice());
					preparedStmt.setString (8, coupon.getImage());
					
			        preparedStmt.execute();	// execute the prepared statement

			     // Run the query - inserts coupon fields into rs in order to get the created coupon generated ID
					rs = stmt.executeQuery("select * FROM coupon_project.coupon where title ='"+coupon.getTitle()+"'");

		        	while (rs.next())
		        	{
		        		coupID=rs.getLong("coupon_id");
		        	}

		        	coupon.setCouponId(coupID);  // sets the ID of the coupon
		        
					// override string to insert a coupon into table company_coupon database for a prepared statement 
			        query = "INSERT INTO coupon_project.company_coupon (COMP_ID, COUP_ID) VALUES (?,?)";
			        preparedStmt = (PreparedStatement) con.prepareStatement(query);
			        
			        preparedStmt.setLong   (1, loggedID);
			        preparedStmt.setLong   (2, coupID);

			        preparedStmt.execute();	// execute the prepared statement
	        	}
				ConnectionPool.getInstance().returnConnection(con);  //  returns connection          
			
	}

	// edited in 20.1 with notes			
	@Override
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException // removes a coupon from tables of database
, CouponNotFoundExcption
	{
		Connection con = null;							//  connection declaration and initialization as null
		Statement stmt;									//  statement declaration
		ResultSet rs = null;							//  resultSet declaration and initialization as null
		
		
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			rs = stmt.executeQuery("select * from coupon_project.coupon where coupon_id ="+coupon.getCouponId());
			
			if (rs.next()&rs!=null)
			{
				// Run the query - deletes coupon from table coupon of database by coupon ID
				stmt.executeUpdate("delete FROM coupon_project.coupon where coupon_id ="+coupon.getCouponId());
				
				// Run the query - deletes coupon from table company_coupon of database by coupon ID
				stmt.executeUpdate("delete FROM coupon_project.company_coupon where coup_id ="+coupon.getCouponId());
	
				// Run the query - deletes coupon from table customer_coupon of database by coupon ID
				stmt.executeUpdate("delete FROM coupon_project.customer_coupon where coupon_id ="+coupon.getCouponId());
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
				throw new CouponNotFoundExcption("The coupon cannot be found in the DataBase");
			}
			ConnectionPool.getInstance().returnConnection(con);  //  returns connection
	}

	// edited in 20.1 with notes			
	@Override
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException // updates a coupon info may not change its ID
, CouponNotFoundExcption
	{

		COUPON_TYPE ct = coupon.getCouponType();		//  coupon type declaration and initialization
		String query=null;								//  query String declaration and initialization	
		Connection con = null;							//  connection declaration and initialization as null
		Statement stmt;									//  statement declaration
		ResultSet rs = null;
		
		
			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			rs = stmt.executeQuery("select * FROM coupon_project.coupon where title ='"+coupon.getTitle()+"'");
			
			if (rs.next())
			{
				// string to update a coupon in table coupon of database for a prepared statement 
				query = "update coupon_project.coupon SET TITLE = ?" + ", START_DATE = ?" + ", END_DATE = ?" + ",AMOUNT = ?" 
						+ ", TYPE = ?" + ", MESSAGE = ?" + ", PRICE = ?" + ", IMAGE = ?" + " WHERE coupon_id = ?";
	
					PreparedStatement preparedStmt =  (PreparedStatement) con.prepareStatement(query);
					preparedStmt.setString (1, coupon.getTitle());
					preparedStmt.setDate   (2, coupon.getDateStart());
					preparedStmt.setDate   (3, coupon.getDateEnd());
					preparedStmt.setInt    (4, coupon.getAmount());
					preparedStmt.setString (5, ct.toString());
					preparedStmt.setString (6, coupon.getMessage());
					preparedStmt.setDouble (7, coupon.getPrice());
					preparedStmt.setString (8, coupon.getImage());
					preparedStmt.setLong (9, coupon.getCouponId());
	
					preparedStmt.executeUpdate();	// execute the prepared statement
			}
			else
			{
				ConnectionPool.getInstance().returnConnection(con);  //  returns connection		
				throw new CouponNotFoundExcption(" failed to find the coupon in the system, coupon"+ coupon.getTitle()+"might not be registered");
			}
			ConnectionPool.getInstance().returnConnection(con); // returns the connection
	}

	// edited in 20.1 with notes			
	@Override
	public Coupon getCoupon(long coupon_id) throws SQLException , ClassNotFoundException, InterruptedException, CouponNotFoundExcption  // gets a coupon's ID and returns a coupon from the database

	{
		Connection con = null;							//  connection declaration and initialization as null
		Statement stmt;									//  statement declaration
		Coupon coupon = null;							//  declaration and initialization a coupon as nul
		ResultSet rs = null;							//  result set declaration and initialization as null 


			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
			// Run the query - gets coupon fields co-responds to the received coupon ID
        	rs = stmt.executeQuery("select * FROM coupon_project.coupon where coupon_id ="+coupon_id);
        	
        	if ( rs.next() ) 
	        	{
		            coupon_id = rs.getLong("coupon_id");
		            String title = rs.getString("TITLE");
		        	Date dateStart = rs.getDate("START_DATE"); 
		        	Date dateEnd =  rs.getDate("END_DATE");
		            int amount = rs.getInt("AMOUNT");
		            COUPON_TYPE type = COUPON_TYPE.valueOf(rs.getString("TYPE"));
		            String msg = rs.getString("MESSAGE");
		            double price = rs.getDouble("PRICE");
		            String img = rs.getString("IMAGE");
	        		
		            coupon = new Coupon(coupon_id, type, dateStart, dateEnd,msg ,price,
		            				   amount, img, title);
        		}
        	else
        	{
				ConnectionPool.getInstance().returnConnection(con); // returns the connection
        		throw new CouponNotFoundExcption ("coupon ID "+coupon_id+" not found");
        	}

        		ConnectionPool.getInstance().returnConnection(con); // returns connection

		return coupon;
	}

	// edited in 20.1 with notes			
	@Override
	public ArrayList<Coupon> getAllCoupons() throws SQLException, ClassNotFoundException, InterruptedException // returns all the coupons registered in the system from the database

	{	
		ArrayList<Coupon> allCoupons = new ArrayList<>();  //  declare and initialize ArrayList of coupons
		Connection con = null;							   //  connection declaration and initialization as null
		Statement stmt;		  							   //  statement declaration
		ResultSet rs = null;							   //  result set declaration and initialization as null 

			con =ConnectionPool.getInstance().getConnection();  //  connection request
			stmt = con.createStatement();						//  statement initializing
			
				// Run the query - gets all coupons fields from table coupon of database
				rs = stmt.executeQuery("SELECT * FROM coupon_project.coupon");
	        
			    while ( rs.next() ) 
	        	{   
			    	//  gets fields of coupons from database
		        	long coupon_id = rs.getLong("coupon_id");
		            String title = rs.getString("TITLE");
		        	Date dateStart = rs.getDate("START_DATE"); 
		        	Date dateEnd =  rs.getDate("END_DATE");
		            int amount = rs.getInt("AMOUNT");
		            COUPON_TYPE type = COUPON_TYPE.valueOf(rs.getString("TYPE"));
		            String msg = rs.getString("MESSAGE");
		            double price = rs.getDouble("PRICE");
		            String img = rs.getString("IMAGE");
		           
		            //  creates coupon from from the fields returned
		            Coupon coupon = new Coupon(coupon_id, type, dateStart, dateEnd,msg ,price,
		            				   amount, img, title);
		            
		            //  adds created coupon to the ArrayList
		            allCoupons.add(coupon);
		 	    }
				
	        	ConnectionPool.getInstance().returnConnection(con);  //  returns connection
			        	
		return allCoupons;  //  returns ArrayList of coupons
		}
	
	// edited in 20.1 with notes			
	@Override
	public Collection<Coupon> getCouponByType(COUPON_TYPE CouponType) throws ClassNotFoundException, SQLException, InterruptedException // gets a coupon type and returns an ArrayList contains 
																	  //all of the company coupons of the specified type
	{
		//ArrayList<Long> CouponsOfCompany = new ArrayList<>();  //  declare and initialize ArrayList of longd
		ArrayList<Coupon> CouponByType = new ArrayList<>();	   //  declare and initialize ArrayList of coupons

		Connection con = null;							   //  connection declaration and initialization as null
		Statement stmt;		  							   //  statement declaration
		ResultSet rs = null;							   //  result set declaration and initialization as null 
		//String query = null;							   //  declare and initialize a string for query	
		long coupon_id = 0;								   //  declare and initialize long coupon ID 
		
		

				con =ConnectionPool.getInstance().getConnection();  //  connection request
				stmt = con.createStatement();						//  statement initializing
			
			// Run the query - gets all coupons ID of the logged company from table company_coupon of database
			rs = stmt.executeQuery("select * from coupon_project.company_coupon where comp_ID = " + getLoggedID());
					
						while ( rs.next()) 
						{
							coupon_id = rs.getLong("coupon_id");
				            String title = rs.getString("TITLE");
				        	Date dateStart = rs.getDate("START_DATE"); 
				        	Date dateEnd =  rs.getDate("END_DATE");
				            int amount = rs.getInt("AMOUNT");
				            COUPON_TYPE type = COUPON_TYPE.valueOf(rs.getString("TYPE"));
				            String msg = rs.getString("MESSAGE");
				            double price = rs.getDouble("PRICE");
				            String img = rs.getString("IMAGE");
				           
				            if (type.equals(CouponType))  //  check if the coupon fields received is of the requested coupon type
				            {
				            	// creates a coupon
			            		Coupon coupon = new Coupon(coupon_id, type, dateStart, dateEnd,msg ,price,
			            				   amount, img, title);
			            	
			            		CouponByType.add(coupon);  //  adds the coupon to coupons of company by type
			            	}
						}
	
		return CouponByType;  //  returns the ArrayList contains all of the company coupons of the specified type
	}
	
}