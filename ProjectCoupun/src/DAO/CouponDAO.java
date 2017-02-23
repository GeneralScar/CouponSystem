package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.CouponAlreadeyRegistered;
import exceptions.CouponNotFoundExcption;
import infra.COUPON_TYPE;
import infra.Coupon;
/**
 * interface CouponDAO implemented by CouponDBDAO
 * @author Orchay
 *
 */
public interface CouponDAO
{
	/**
	 * gets a coupon of a company and insert it into the database 
	 * @param coupon
	 * @throws CouponAlreadeyRegistered
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public void createCoupon(Coupon coupon) throws CouponAlreadeyRegistered, ClassNotFoundException, SQLException, InterruptedException;
	
	/**
	 * removes a coupon from tables of database
	 * @param coupon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CouponNotFoundExcption
	 */
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundExcption;
	
	/**
	 * updates a coupon info may not change its ID
	 * @param coupon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CouponNotFoundExcption
	 */
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundExcption;

	/**
	 * gets a coupon's ID and returns a coupon from the database
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 * @throws CouponNotFoundExcption
	 */
	public Coupon getCoupon(long id) throws SQLException, ClassNotFoundException, InterruptedException, CouponNotFoundExcption;
	
	/**
	 * returns all the coupons registered in the system from the database
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public ArrayList<Coupon> getAllCoupons() throws SQLException, ClassNotFoundException, InterruptedException;
	
	/**
	 * gets a coupon type and returns an ArrayList contains all of the company coupons of the specified type
	 * @param CouponType
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public Collection<Coupon> getCouponByType(COUPON_TYPE CouponType) throws ClassNotFoundException, SQLException, InterruptedException;
	
	
}
