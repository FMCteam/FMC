package nju.software.service;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;

public interface AccountService {

	public Account vertifyAccount(String accountName, String accountPassword);

	public boolean deleteAccount(Integer accountId);

	public boolean addAccount(Account account);

	public boolean updateAccount(Account account);

	public Account getAccountByUsername(String username);

	public Account getAccountByAccountId(int accountId);

	public boolean checkExit(String userName);

	// 按照角色和用户的id来查找数据，感觉还是需要这样查找，因为customer表中并不包含登录名称
	public Account getAccoutByUserIdAndUserRole(String role, Integer userId);

	public boolean saveAccount(Customer c, String password1, String customerName);
}
