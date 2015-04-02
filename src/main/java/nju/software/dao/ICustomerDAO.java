package nju.software.dao;

import java.util.*;

import nju.software.dataobject.Customer;

public interface ICustomerDAO {

	public abstract void save(Customer transientInstance);

	public abstract void delete(Customer persistentInstance);

	public abstract Customer findById(java.lang.Integer id);

	public abstract List<Customer> findByExample(Customer instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Customer> findByCompanyId(Object companyId);

	public abstract List<Customer> findByCompanyName(Object companyName);

	public abstract List<Customer> findByCustomerName(Object customerName);

	public abstract List<Customer> findByProvince(Object province);

	public abstract List<Customer> findByCity(Object city);

	public abstract List<Customer> findByWebsiteUrl(Object websiteUrl);

	public abstract List<Customer> findByWebsiteType(Object websiteType);

	public abstract List<Customer> findByCompanyAddress(Object companyAddress);

	public abstract List<Customer> findByCompanyFax(Object companyFax);

	public abstract List<Customer> findByCompanyPhone(Object companyPhone);

	public abstract List<Customer> findByBuyContact(Object buyContact);

	public abstract List<Customer> findByContactPhone1(Object contactPhone1);

	public abstract List<Customer> findByContactPhone2(Object contactPhone2);

	public abstract List<Customer> findByQq(Object qq);

	public abstract List<Customer> findByEmail(Object email);

	public abstract List<Customer> findByCustomerPhone(Object customerPhone);

	public abstract List<Customer> findByBossName(Object bossName);

	public abstract List<Customer> findByBossPhone(Object bossPhone);

	public abstract List<Customer> findByRegisterEmployeeId(
			Object registerEmployeeId);

	public abstract List findAll();

	public abstract Customer merge(Customer detachedInstance);

	public abstract void attachDirty(Customer instance);

	public abstract void attachClean(Customer instance);
	
	//public int countByProperty(String propertyName, Object value);

	public int countByProperty(String propertyName,String protpertyValue);
	public int countAll();
	public List<Object> listCustomer(Map<String,Object> params);
	public List<Object> findByPropertyCustomerPage(
			String propertyName,String propetyValue,Map<String, Object> params);


}