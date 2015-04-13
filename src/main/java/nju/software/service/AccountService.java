package nju.software.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;

public interface AccountService {

	
	/**
	 *  获取所有专员的列表
	 * @return 专员列表
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<Account>   getAllManagerStaff();  
	
	@Transactional(rollbackFor = Exception.class)
	public Account vertifyAccount(String accountName, String accountPassword);

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteAccount(Integer accountId);

	@Transactional(rollbackFor = Exception.class)
	public int addAccount(Account account);

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
	@Transactional(rollbackFor = Exception.class)
	public boolean addAccountRole(String roleName, int accountId);
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteAccountRole(int accountId);
}
