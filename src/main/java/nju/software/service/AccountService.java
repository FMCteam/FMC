package nju.software.service;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;

public interface AccountService {

	@Transactional(rollbackFor = Exception.class)
	public Account vertifyAccount(String accountName, String accountPassword);

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteAccount(Integer accountId);

	@Transactional(rollbackFor = Exception.class)
	public boolean addAccount(Account account);

	@Transactional(rollbackFor = Exception.class)
	public boolean updateAccount(Account account);

	@Transactional(rollbackFor = Exception.class)
	public Account getAccountByUsername(String username);

	@Transactional(rollbackFor = Exception.class)
	public Account getAccountByAccountId(int accountId);

	@Transactional(rollbackFor = Exception.class)
	public boolean checkExit(String userName);

	// 按照角色和用户的id来查找数据，感觉还是需要这样查找，因为customer表中并不包含登录名称
	@Transactional(rollbackFor = Exception.class)
	public Account getAccoutByUserIdAndUserRole(String role, Integer userId);

	@Transactional(rollbackFor = Exception.class)
	public boolean saveAccount(Customer c, String password1, String customerName);
}
