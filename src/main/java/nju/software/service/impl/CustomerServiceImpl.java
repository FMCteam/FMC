package nju.software.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.service.CustomerService;
import nju.software.util.*;

@Service("customerServiceImpl")
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDAO customerDao;
	@Autowired
	private AccountDAO accountDao;
	@Transactional
	public boolean addCustomer(Customer c,String userName,String password) {
		// TODO Auto-generated method stub
		try{
			this.customerDao.save(c);
			Integer customerId=c.getCustomerId();
			Account a=new Account();
			a.setUserId(customerId);
			a.setUserType(Constants.USER_TYPE_CUSTOMER);
			a.setUserPassword(SecurityUtil.md5hex(password));
			
		    //login name
			a.setUserName(userName);
			a.setUserRole(Constants.USER_ROLE_CUSTOMER);
			a.setNickName(c.getCustomerName());
			a.setBigAvatar("");
			a.setSmallAvatar("");
			a.setPasswordCookieTime(null);
			a.setPasswordCookieValue(null);
			//
			//a.setUserCookieTime(null);
			//a.setUserCokkieValue(null);
			this.accountDao.save(a);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public Customer findByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		try{
		return this.customerDao.findById(customerId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveCustomer(Customer c) {
		// TODO Auto-generated method stub
		try{
			this.customerDao.merge(c);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Object> searchByPropertyName(String propertyName,
			String propertyValue, Map<String, Object> m) {
		// TODO Auto-generated method stub
		try{
			List<Object> list=this.customerDao.findByPropertyCustomerPage(propertyName, propertyValue, m);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteCustomerById(Integer customerId) {
		// TODO Auto-generated method stub
		try
		{
			this.customerDao.delete(customerDao.findById(customerId));
			return true;
		}catch(Exception e)
		{
			
		}
		return false;
	}



	@Override
	public List<Object> listCustomer(Map<String, Object> m) {
		try
		{
			List<Object> obj=this.customerDao.listCustomer(m);
			return obj;
		}catch(Exception e)
		{
			
			
		}
		return null;
		
	}

}
