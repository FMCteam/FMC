package nju.software.service;

import java.util.List;
import java.util.Map;

import nju.software.dataobject.Customer;

public interface CustomerService {

	// 添加客户,加入事物处理
	public boolean addCustomer(Customer c, String userName, String userPassword);

	public List<Object> listCustomer(Map<String, Object> m);

	public Customer findByCustomerId(int customerId);

	public boolean saveCustomer(Customer c);

	// 查找分页
	public List<Object> searchByPropertyName(String propertyName,
			String propertyValue, Map<String, Object> m);

	// 删除
	public boolean deleteCustomerById(Integer customerId);
}
