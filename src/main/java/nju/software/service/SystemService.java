package nju.software.service;

import java.util.List;

import nju.software.dataobject.Account;
import nju.software.dataobject.Permission;
import nju.software.dataobject.RolePriView;
import nju.software.dataobject.TreeNode;

public interface SystemService {

	public List<TreeNode> findLeftMenuByLogin();
	   public abstract List<TreeNode> findSystemMenu();
	   public Permission findSystemMessage(Integer id);
	   public Boolean updateSystemMessage(Permission eps);
	   public Boolean onlyupdateSystemMessage(Permission eps);
	   public List<TreeNode> findSystemMenuTree();
	   public RolePriView findDreeMapNew();
	   public List<Object> findRole();
	   public boolean updateRolePriv(List<String> list,Integer str);
	   public boolean addRole(String name, String description);
	   public boolean updRole(String name, String description,Integer in);
	   public Integer[] findRolePriv(Integer str);
	   public List<Object> findAccountMes();
	   public List<Object> findAccountRole(Integer accountId);
	   public boolean updateAccountRole(List<String> list,Integer str);
	   public List<TreeNode> findLeftMenuByLogin(Account account);
}
