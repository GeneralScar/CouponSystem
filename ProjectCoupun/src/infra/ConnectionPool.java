package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * ConnectionPool a pool of connections that allow interaction with the database
 * if no connection is available request for connection is put on hold
 * @author Orchay
 *
 */
public class ConnectionPool {

		private static ConnectionPool instance = null;
	    private static String conUrl = "jdbc:mysql://localhost/coupon_project";
	    private static String user = "Orchay";
	    private static String pw ="161187";
	    
	    private static Set<Connection> conSet = new HashSet();
	    public final int max_Con =5;
	    public static Object key = new Object();
	    
		private ConnectionPool() throws ClassNotFoundException, SQLException {
			
			Class.forName("com.mysql.jdbc.Driver");  

				for (int i = 0; i < max_Con; i++)
				{
					Connection con = DriverManager.getConnection(conUrl, user, pw);	
					conSet.add(con);
				}
		}
	
		public static Connection getConnection() throws ClassNotFoundException, SQLException, InterruptedException
		{
			synchronized (key)
			{
				while (conSet.isEmpty()) 
				{
					key.wait();
				}
				Iterator<Connection> myIterator = conSet.iterator();
				Connection connectionGiven = myIterator.next();
				System.out.println(conSet.size());
				conSet.remove(connectionGiven);

				return connectionGiven;
			}
		}
		public void returnConnection(Connection returnedCon)
		{
			synchronized (key)
			{
				conSet.add(returnedCon);
				key.notify();
			}	
		}
		
		public static synchronized ConnectionPool getInstance() throws ClassNotFoundException, SQLException
		{
			if (instance== null)
			{
				instance = new ConnectionPool();
			}
			return instance;
			}
		
		/**
		 * method to close all connection tries 5 times with 0.3 sec intervals to ensure the return of all connections
		 */
		public void closeAllConnections ()
		{
			int tryCounter = 0;
			
			while ((conSet.size()<=4)&(tryCounter<5))
			{
				for(Connection con : conSet)
				{
					try {
						con.close();
						} 
					catch (SQLException e) 
						{
							e.getMessage();
						}
				}
					try 
					{
						Thread.currentThread().wait(300);
					} 
					catch (InterruptedException e) 
					{
						e.getMessage();
					}
				tryCounter++;
			}
		}

	}	



