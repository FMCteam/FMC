package nju.software.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import nju.software.dataobject.Account;
import nju.software.dataobject.Permission;
import nju.software.dataobject.RolePriView;
import nju.software.dataobject.TreeNode;

public interface SystemService {

	@Transactional(rollbackFor = Exception.class)
	public List<TreeNode> findLeftMenuByLogin();
	@Transactional(rollbackFor = Exception.class)
	   public abstract List<TreeNode> findSystemMenu();
	@Transactional(rollbackFor = Exception.class)
	   public Permission findSystemMessage(Integer id);
	@Transactional(rollbackFor = Exception.class)
	   public Boolean updateSystemMessage(Permission eps);
	@Transactional(rollbackFor = Exception.class)
	   public Boolean onlyupdateSystemMessage(Permission eps);
	@Transactional(rollbackFor = Exception.class)
	   public List<TreeNode> findSystemMenuTree();
	@Transactional(rollbackFor = Exception.class)
	   public RolePriView findDreeMapNew();
	@Transactional(rollbackFor = Exception.class)
	   public List<Object> findRole();
	@Transactional(rollbackFor = Exception.class)
	   public boolean updateRolePriv(List<String> list,Integer str);
	@Transactional(rollbackFor = Exception.class)
	   public boolean addRole(String name, String description);
	@Transactional(rollbackFor = Exception.class)
	   public boolean updRole(String name, String description,Integer in);
	@Transactional(rollbackFor = Exception.class)
	   public Integer[] findRolePriv(Integer str);
	@Transactional(rollbackFor = Exception.class)
	   public List<Object> findAccountMes();
	@Transactional(rollbackFor = Exception.class)
	   public List<Object> findAccountRole(Integer accountId);
	@Transactional(rollbackFor = Exception.class)
	   public boolean updateAccountRole(List<String> list,Integer str);
	@Transactional(rollbackFor = Exception.class)
	   public List<TreeNode> findLeftMenuByLogin(Account account);
}
