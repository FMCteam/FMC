package nju.software.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Customer;

public interface CustomerService {

	// 添加客户,加入事物处理
	@Transactional(rollbackFor = Exception.class)
	public boolean addCustomer(Customer c, String userName, String userPassword);

	@Transactional(rollbackFor = Exception.class)
	public List<Object> listCustomer(Map<String, Object> m);

	@Transactional(rollbackFor = Exception.class)
	public Customer findByCustomerId(int customerId);

	@Transactional(rollbackFor = Exception.class)
	public boolean saveCustomer(Customer c);

	// 查找分页
	@Transactional(rollbackFor = Exception.class)
	public List<Object> searchByPropertyName(String propertyName,
			String propertyValue, Map<String, Object> m);

	// 删除
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteCustomerById(Integer customerId);
}
