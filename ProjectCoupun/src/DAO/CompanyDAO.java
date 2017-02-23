/**
 * hello
 */
package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import exceptions.CompanyAlreadyExist;
import exceptions.CompanyNotFoundExcption;
import exceptions.LogginErrorExcption;
import infra.Company;
import infra.Coupon;
/**
 * interface CompanyDAO implemented by CompanyDBDAO
 * @author Orchay
 *
 */
public interface CompanyDAO
{
	/**
	 * gets a company and insert it to the database at company table
	 * @param company
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CompanyAlreadyExist
	 */
	public void createCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyAlreadyExist;// gets a company and insert it to the database
	
	/**
	 * removes a company and its coupons from the system
	 * @param company
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws CompanyNotFoundExcption
	 */
	public void removeCompany(Company company) throws SQLException, ClassNotFoundException, InterruptedException, CompanyNotFoundExcption;
	
	/**
	 * updates a company info may not change its ID and name
	 * @param company
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CompanyNotFoundExcption
	 */
	public void updateCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundExcption;

	/**
	 * returns a company by inserted ID number
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws CompanyNotFoundExcption
	 */
	public Company getCompany(long id) throws SQLException, ClassNotFoundException, InterruptedException, CompanyNotFoundExcption;

	/**
	 * returns all register companies within the system
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public ArrayList<Company> getAllCompanies() throws SQLException, ClassNotFoundException, InterruptedException;	
	
	/**
	 * function that returns all the coupons of the logged company
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public ArrayList<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException;	
	
	/**
	 * login method - checks if the company's user name and password matches and registered in the system 
	 * @param compName
	 * @param password
	 * @return true or false
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws LogginErrorExcption
	 */
	public boolean login(String compName, String password) throws SQLException, ClassNotFoundException, InterruptedException, LogginErrorExcption;
}