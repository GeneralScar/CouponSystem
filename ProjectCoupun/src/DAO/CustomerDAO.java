package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.CustomerNotFoundExcption;
import exceptions.LogginErrorExcption;
import exceptions.CustomerAlreadyExist;
import facade.CouponClientFacade;
import infra.CLIENT_TYPE;
import infra.Coupon;
import infra.Customer;
/**
 * interface CustomerDAO implemented by CustomerDBDAO
 * @author Orchay
 *
 */
public interface CustomerDAO
{
	/**
	 * insert a new customer to the database
	 * @param cost
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CustomerNotFoundExcption
	 */
	public void createCustomer(Customer cost ) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption;
	
	/**
	 * get a customer and removes it from the database
	 * @param cost 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CustomerNotFoundExcption
	 */
	public void removeCustomer(Customer cost) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption;
	
	/**
	 * get a customer and update in the database by ID
	 * @param cost
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CustomerNotFoundExcption
	 */
	public void updateCustomer(Customer cost) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundExcption;
	
	/**
	 * returns a customer (by customer ID) from the table customer
	 * @param id
	 * @return
	 * @throws CustomerNotFoundExcption
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public Customer getCustomer(long id) throws CustomerNotFoundExcption, ClassNotFoundException, SQLException, InterruptedException;

	/**
	 * returns all customers registered in the system
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public ArrayList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException, InterruptedException;
	
	/**
	 * returns all coupons purchased by the customer
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public ArrayList<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException;

	/**
	 * login method - checks if the customer user name and password matches 
	 * @param Name
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws LogginErrorExcption
	 */
	boolean login(String Name, String password) throws ClassNotFoundException, SQLException, InterruptedException, LogginErrorExcption;
	
}
