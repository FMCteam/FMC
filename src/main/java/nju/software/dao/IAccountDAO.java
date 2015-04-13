package nju.software.dao;

import java.util.*;

import nju.software.dataobject.Account;
import nju.software.dataobject.AccountRole;

public interface IAccountDAO {

	public abstract void save(Account transientInstance);

	public abstract void delete(Account persistentInstance);

	public abstract Account findById(java.lang.Integer id);

	public abstract List<Account> findByExample(Account instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List<Account> findByUserId(Object userId);

	public abstract List<Account> findByUserType(Object userType);

	public abstract List<Account> findByUserPassword(Object userPassword);

	public abstract List<Account> findByUserName(Object userName);

	public abstract List findAll();

	public abstract Account merge(Account detachedInstance);

	public abstract void attachDirty(Account instance);

	public abstract void attachClean(Account instance);
 
	public List<Account> findByUserIdAndUserName(String role,Integer userId);
	
	public boolean isEmptytOfTable();
	 
	public List findPermissionBYName(String username);
	 
	public boolean addAccountRole(String roleId,int accountId);
	
	public void deleteAccountRole(AccountRole persistentInstance);
	public List<AccountRole> findAccountRoleById(int accountId);
	
	/**
	 * 以用户名降序返回关键字搜索结果
	 * @param propertyName 关键字 
	 * @param value 关键字的值
	 * @return 结果列表
	 */
	public List findByPropertyinDESC(String propertyName, Object value);
	
	/**
	 * 获取全部市场专员
	 * @return
	 */
	public List getAllManagerStaff();
}
