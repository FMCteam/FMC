package nju.software.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.BaseDaoImpl;
import nju.software.dataobject.Account;
import nju.software.dataobject.AccountRole;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Role;
import nju.software.dataobject.RolePermission;
import nju.software.service.AccountService;
import nju.software.util.Constants;
import nju.software.util.SecurityUtil;

@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private BaseDaoImpl baseDao;
	@Override
	public Account vertifyAccount(String accountName, String accountPassword) {
		Account account = new Account();
		String pwd = SecurityUtil.md5hex(accountPassword);

		account.setUserName(accountName);
		account.setUserPassword(pwd);

		List<Account> accountList = accountDAO.findByExample(account);
		if (accountList.isEmpty()) {
			return null;
		} else
			return accountList.get(0);
	}

	@Override
	public boolean deleteAccount(Integer accountId) {
		// TODO Auto-generated method stub
		try {
			accountDAO.delete(getAccountByAccountId(accountId));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int addAccount(Account account) {
		// TODO Auto-generated method stub
		try {
			accountDAO.save(account);
			int id = account.getAccountId();
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean updateAccount(Account account) {
		// TODO Auto-generated method stub
		try {
			accountDAO.attachDirty(account);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Account getAccountByUsername(String username) {
		// TODO Auto-generated method stub
		List<Account> accountList = accountDAO.findByUserName(username);
		if (accountList != null && !accountList.isEmpty()) {
			return accountList.get(0);
		} else
			return null;
	}

	@Override
	public Account getAccountByAccountId(int accountId) {
		// TODO Auto-generated method stub
		List<Account> accountList = accountDAO.findByProperty("accountId",
				accountId);
		if (accountList != null && !accountList.isEmpty()) {
			return accountList.get(0);
		} else
			return null;
	}

	@Override
	public boolean checkExit(String userName) {
		// TODO Auto-generated method stub
		try {
			List<Account> a = this.accountDAO.findByProperty("userName",
					userName);
			if (a != null && a.size() >= 1)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account getAccoutByUserIdAndUserRole(String role, Integer userId) {
		// TODO Auto-generated method stub
		try {
			List<Account> a = this.accountDAO.findByUserIdAndUserName(role,
					userId);
			if (a != null && a.size() >= 1)
				return a.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveAccount(Customer c, String password1,String customerName) {
		// TODO Auto-generated method stub
		List<Account> a = this.accountDAO.findByUserIdAndUserName(
				Constants.USER_TYPE_CUSTOMER, c.getCustomerId());
		if (a != null && a.size() >= 1) {
			Account ac = a.get(0);
			ac.setUserPassword(SecurityUtil.md5hex(password1));
			ac.setNickName(customerName);
			this.accountDAO.merge(ac);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAccountRole(String roleName, int accountId) {
		// TODO Auto-generated method stub
		System.out.println("===================accountId:"+accountId);
		accountDAO.addAccountRole(roleName, accountId);
		
		
		
		return true;
	}
    
	public boolean updateAccountRole(int accountId,String newRole){
    	String hql="FROM AccountRole E WHERE E.status = 'A' and E.accountId="+accountId;
		List<AccountRole> rpList= baseDao.queryList(hql);
		System.out.println(rpList+"-----------"+rpList.size());
		List<String> tempList =new ArrayList<String>();
		
		Role role = accountDAO.getRoleByName(newRole);
		int roleId =role.getRoleId();
		System.out.println("===================roleID:"+roleId);
		boolean isRoleChanged = true ;
		for (AccountRole accountRole : rpList){
			if( accountRole.getRoleId().equals(roleId)){
				isRoleChanged= false ;
				break ;
			}
				
		}
		if(isRoleChanged){
		for (AccountRole accountRole : rpList) {
			accountRole.setStatus("I");
			baseDao.saveOrupdate(accountRole);
		}
		boolean success =accountDAO.addAccountRole(newRole, accountId);
		
		
		
		return success ;
		}
		return true ;
		
		
		
    }
	@Override
	public boolean deleteAccountRole(int accountId) {
		// TODO Auto-generated method stub
		try {
			List<AccountRole> list = accountDAO.findAccountRoleById(accountId);
			if(list!=null){
			for(AccountRole ar : list)
			    accountDAO.deleteAccountRole(ar);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    
	@Override
	public List<Account> getAllManagerStaff() {
		
		return accountDAO.getAllManagerStaff();
	}
   
}
