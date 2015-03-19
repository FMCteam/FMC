package nju.software.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nju.software.dao.impl.BaseDaoImpl;
import nju.software.dataobject.Account;
import nju.software.dataobject.AccountRole;
import nju.software.dataobject.Permission;
import nju.software.dataobject.Role;
import nju.software.dataobject.RolePermission;
import nju.software.dataobject.RolePriView;
import nju.software.dataobject.TreeNode;
import nju.software.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("systemServiceImpl")
public class SystemServiceImpl implements SystemService {

	
	@Override
	public List<TreeNode> findLeftMenuByLogin() {
		return null;
	}

	@Override
	public List<TreeNode> findSystemMenu() {
		List<TreeNode> treeList = service.findSystemMenu();
        return treeList;
	}

	@Override
	public Permission findSystemMessage(Integer id) {
		Permission ep=(Permission) baseDao.findById(Permission.class,id);
		return ep;
	}

	@Override
	public Boolean updateSystemMessage(Permission eps) {
		baseDao.saveOrupdate(eps);
		return true;
	}
	
	@Override
	public Boolean onlyupdateSystemMessage(Permission eps) {
		baseDao.update(eps);
		return true;
	}

	@Override
	public List<TreeNode> findSystemMenuTree() {
		return null;
	}
	
	@Override
	public RolePriView findDreeMapNew() {
		List<TreeNode> list=findSystemMenu();
		List<RolePriView>pList = new ArrayList<RolePriView>();
		RolePriView total= new RolePriView();
		for (TreeNode treeNode : list) { 
			if (treeNode.getPId()==null||"null".equals(treeNode.getPId())) {
				List<RolePriView> cList= findClist(list,treeNode.getId());
				RolePriView  pView= new RolePriView();
				pView.setId(treeNode.getId());
				String string ="Y".equals(treeNode.getIsUsed())?"<a style='font-size: 12;color: blue;'>启用</a>":"<a style='font-size: 12;color: red;'>停止</a>";
				pView.setText(treeNode.getName()+"["+string+"]");
				pView.setItem(cList);
				pList.add(pView);
			}
		}
		total.setItem(pList);
		return total;
	}	
	
	
	
	private List<RolePriView> findClist(List<TreeNode> list,String str){
		List<RolePriView> cList= new ArrayList<RolePriView>();
		for (TreeNode treeNode : list) {
			if (treeNode.getPId().equals(str)) {
				RolePriView cView = new RolePriView();
				cView.setId(treeNode.getId());
				String string ="Y".equals(treeNode.getIsUsed())?"<a style='font-size: 12;color: blue;'>启用</a>":"<a style='font-size: 12;color: red;'>停止</a>";
				cView.setText(treeNode.getName()+"["+string+"]");
				cView.setItem(findClist(list,treeNode.getId()));
				cList.add(cView);
			}
			
		}
		return cList;
		
	}
	@Override
	public List<Object> findRole() {
		String sqlString="SELECT t.role_id,t.name,t.description FROM role t WHERE t.status='A'";
		List<Object> list= baseDao.conditionQuery(sqlString);
		return list;
	}
	
	
	@Override
	public boolean updateRolePriv(List<String> list,Integer str) {
		Date date =new Date();
		String hql="FROM RolePermission E WHERE E.status = 'A' and E.roleId="+str;
		List<RolePermission> rpList= baseDao.queryList(hql);
		System.out.println(rpList+"-----------"+rpList.size());
		List<String> tempList =new ArrayList<String>();
		for (RolePermission rolePermission : rpList) {
			rolePermission.setStatus("I");
			baseDao.saveOrupdate(rolePermission);
		}
		
		if (rpList.size()>0&&!rpList.isEmpty()) {
			if(list.size()>1){
				for (String st: list) {
					RolePermission newrp= new RolePermission();
						for (RolePermission rp : rpList) {
						if(Integer.valueOf(st).equals(rp.getPermissionId())){
							rp.setStatus("A");
							baseDao.saveOrupdate(rp);
							tempList.add(String.valueOf(rp.getPermissionId()));
						}
						
					}
		
				}
				
				for (String string : list) {
					if (!tempList.contains(string)) {
						RolePermission newrp= new RolePermission();
						newrp.setStatus("A");
						newrp.setRoleId(str);
						newrp.setPermissionId(Integer.valueOf(string));
						newrp.setCreated(date);
						newrp.setLastmod(date);
						baseDao.saveOrupdate(newrp);
					}
					
				}
			}
		
		}else {
			if(list.size() >1){
				for (String st : list) {
					RolePermission newrp= new RolePermission();
					newrp.setStatus("A");
					newrp.setRoleId(str);
					newrp.setPermissionId(Integer.valueOf(st));
					newrp.setCreated(date);
					newrp.setLastmod(date);
					baseDao.saveOrupdate(newrp);
				}
			}
		}
		
		
		return true;
		
	}
	
	
	@Override
	public boolean addRole(String name, String description) {
		Date  date= new Date();
		Role role= new Role();
			role.setName(name);
			role.setDescription(description);
			role.setCreated(date);
			role.setLastmod(date);
			role.setStatus("A");
			role.setSort("1");
			baseDao.saveOrupdate(role);
		return true;
	}
	
	@Override
	public boolean updRole(String name, String description,Integer in) {
		Date date = new Date();
		Role role = (Role) baseDao.findById(Role.class, in);
		role.setName(name);
		role.setDescription(description);
		role.setLastmod(date);
		if (this.updRoleMes(role)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	@Override
	public Integer[] findRolePriv(Integer str) {
		String sql="SELECT E.PERMISSION_ID\n" +
			"  FROM ROLE_PERMISSION E\n" + 
			"  LEFT JOIN PERMISSION T\n" + 
			"    ON T.PERMISSION_ID = E.PERMISSION_ID\n" + 
			" WHERE E.STATUS = 'A'\n" + 
			"   AND T.PID IS NOT NULL and t.type is null AND E.ROLE_ID="+str;
		List<Object> rpList= baseDao.conditionQuery(sql);
		Integer[]ds=new Integer[rpList.size()];
		for (int i = 0;i < rpList.size(); i++) {
			ds[i]= Integer.valueOf(rpList.get(i).toString());
		}
	return ds;	
	}
	
	@Override
	public List<Object> findAccountMes() {
		String sql="SELECT t.account_id,t.user_name,t.nick_name,t.user_role from account t";
		List<Object> list =baseDao.conditionQuery(sql);
		return list;
		
	}
	
	
	@Override
	public List<Object> findAccountRole(Integer accountId) {
		String sql="SELECT t.account_id,t.role_id FROM account_role t WHERE t.status='A' AND t.account_id="+accountId;
	 List< Object> list=baseDao.conditionQuery(sql);
		return list;
	}

	@Override
	public boolean updateAccountRole(List<String> list,Integer str) {
		Date date =new Date();
		String hql="UPDATE AccountRole e SET e.status='I' WHERE e.accountId="+str;
			baseDao.updhql(hql);
		for (String string : list) {
			AccountRole accountRole=  new AccountRole();
			accountRole.setRoleId(Integer.valueOf(string));
			accountRole.setAccountId(str);
			accountRole.setStatus("A");
			accountRole.setCreated(date);
			accountRole.setLastmod(date);
			baseDao.saveOrupdate(accountRole);
		}
		return true;
	}
	
	@Override
    public List<TreeNode> findLeftMenuByLogin(Account account)
    {	
    	
    	if(account !=null){
    	String sql=null;
    	if ("admin".equals(account.getUserName())) {
    		sql="SELECT P.PERMISSION_ID,P.PID,P.NAME,P.SORT,P.ICONCLS,P.URL,P.MYID FROM PERMISSION P WHERE " +
			 "P.STATUS='A' AND P.TYPE='F'AND P.ISUSED='Y' ORDER BY p.sort";
    	}else {
			sql="SELECT DISTINCT P.PERMISSION_ID, P.PID, P.NAME, P.SORT, P.ICONCLS, P.URL, P.MYID\n" +
			"  FROM ROLE_PERMISSION RP\n" + 
			" INNER JOIN ROLE R\n" + 
			"    ON RP.ROLE_ID = R.ROLE_ID\n" + 
			" INNER JOIN ACCOUNT_ROLE UR\n" + 
			"    ON RP.ROLE_ID = UR.ROLE_ID\n" + 
			" INNER JOIN ACCOUNT U\n" + 
			"    ON U.ACCOUNT_ID = UR.ACCOUNT_ID\n" + 
			" INNER JOIN PERMISSION P\n" + 
			"    ON RP.PERMISSION_ID = P.PERMISSION_ID\n" + 
			" WHERE RP.STATUS = 'A'\n" + 
			"   AND R.STATUS = 'A'\n" + 
			"   AND UR.STATUS = 'A'\n" + 
			"   AND P.STATUS = 'A'\n" + 
			"   AND P.ISUSED = 'Y'\n" + 
			"   AND P.TYPE = 'F' AND U.ACCOUNT_ID="+account.getAccountId()+" ORDER BY p.sort";
		}
        List<Object> dbList = baseDao.conditionQuery(sql);
        List<TreeNode> parentList =  new ArrayList<TreeNode>();
       for (Object object : dbList) {
    	   Object[] objs= (Object[]) object;
    	   String idString=objs[0].toString();
    	  if (objs[1]==null) {
    		  TreeNode treeNode =new TreeNode();
    		  treeNode.setName(String.valueOf(objs[2]));
    		  treeNode.setSort(String.valueOf(objs[3]));
    		  treeNode.setUrl(String.valueOf(objs[5]));
    		  treeNode.setIconcls(String.valueOf(objs[4]));
    		  treeNode.setMyid(String.valueOf(objs[6]));
    		  List<TreeNode> childList =  new ArrayList<TreeNode>();
    		  for (Object object2 : dbList) {
        		  TreeNode treeNodeChild =new TreeNode();
    	    	   Object[] obj= (Object[]) object2;
    	    	   
    	    	   String pidString=String.valueOf(obj[1]==null?"":obj[1]);
    	    	   if (pidString.equals(idString)) {
    	    		   treeNodeChild.setName(String.valueOf(obj[2]));
    	    		   treeNodeChild.setSort(String.valueOf(obj[3]));
    	    		   treeNodeChild.setUrl(String.valueOf(obj[5]));
    	    		   treeNodeChild.setIconcls(String.valueOf(obj[4]));
    	    		   treeNodeChild.setMyid(String.valueOf(obj[6]));
    	    		   childList.add(treeNodeChild);
				}
			}
    		  treeNode.setChild(childList);
    		  parentList.add(treeNode);
			
    	  }
   		}
       		return parentList;
    	}else{
    		return null;
    		}
    }

	
	private  boolean updRoleMes(Role role){
		baseDao.saveOrupdate(role);
		return true;
	}
	@Autowired
	private ServiceUtil service;
	@Autowired
	private BaseDaoImpl baseDao;

}
