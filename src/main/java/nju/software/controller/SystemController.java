package nju.software.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;
import nju.software.dataobject.AccountView;
import nju.software.dataobject.Permission;
import nju.software.dataobject.RolePriView;
import nju.software.dataobject.TreeNode;
import nju.software.dataobject.UserRoleView;
import nju.software.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

@Controller
public class SystemController {

	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        binder.setFieldDefaultPrefix("ep.");  
    }  
	
	
	@RequestMapping(value = "/system/moduleMessages.do")
	//@Transactional(rollbackFor = Exception.class)
	public String moduleMessages(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		/*List<TreeNode> treeNodes = systemService.findSystemMenu();
		String json = JSON.toJSONStringWithDateFormat(treeNodes, "yyyy-MM-dd HH:mm:ss");
		System.out.println(json);
		model.addAttribute("treeNodeList", json);
		model.addAttribute("111", "123");*/
		
		return "/system/moduleMessages";
	}
	
	@RequestMapping(value = "/system/rolePrivilege.do")
	//@Transactional(rollbackFor = Exception.class)
	public String rolePrivilege(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){

		return "/system/rolePrivilege";
	}
	
	
	@RequestMapping(value = "/system/roleAss.do")
	//@Transactional(rollbackFor = Exception.class)
	public String roleAss(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){

		return "/system/roleAss";
	}
	
	/** 
	* @Title: findSystemMenu 
	* @Description: TODO:查询显示列表(模组) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findSystemMenu.do")
	//@Transactional(rollbackFor = Exception.class)
	public String findSystemMenu(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<TreeNode> treeNodes = systemService.findSystemMenu();
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("cur_user");
		List<TreeNode> list= systemService.findLeftMenuByLogin(account);
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        System.out.println(json);
        session.setAttribute("aaa", json);
		for (TreeNode treeNode : list) {
			System.out.println("--------"+treeNode.getName());
		}
	
		session.setAttribute("treeNodeList", list);
		Object object = session.getAttribute("treeNodeList");
		for (TreeNode treeNode : treeNodes) {
			System.out.println("++++++++++++"+treeNode.getName());
		}
		printByJson(response, treeNodes);
		return null;	
	}	
	/** 
	* @Title: findSystemMenuTree 
	* @Description: TODO:MenuTree的列表
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*//*
	public String findSystemMenuTree() {
		List<TreeNode>list =systemService.findSystemMenuTree();
		printByJson(list);
		return null;	
		}
	*//** 
	* @Title: editSystemMenu 
	* @Description: TODO:修改模组数据
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/editSystemMenu.do")
	//@Transactional(rollbackFor = Exception.class)
	public String editSystemMenu(@ModelAttribute Permission ep,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Permission eps=systemService.findSystemMessage(Integer.valueOf(ep.getPermissionId()));
		printByJson(response,eps);
		return null;	
	}
	/** 
	* @Title: updateSystemMessage 
	* @Description: TODO:更新menu信息
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/updateSystemMessage.do")
	//@Transactional(rollbackFor = Exception.class)
	public String updateSystemMessage(@ModelAttribute Permission ep,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Date date =new Date();
		/*System.out.println(ep.getPid()+"++++++++++++++++");
		//新增父节点
		System.out.println(isAdd+"++++++++++++++++");
		//新增父节点*/		
		String isAdd = request.getParameter("isAdd");
		if("Add".equals(isAdd)){
			Permission epsAdd= new Permission();
			//新增子节点
			if (!"".equals(ep.getPermissionId())&&ep.getPermissionId()!= null) {
				epsAdd.setPid(Integer.valueOf(ep.getPermissionId()));
				epsAdd.setPname(ep.getPname());
			}
			epsAdd.setName(ep.getName());
			epsAdd.setSort(("".equals(ep.getSort())||ep.getSort()==null)?null:Integer.valueOf(ep.getSort()));
			epsAdd.setUrl(ep.getUrl());
			epsAdd.setMyid(ep.getMyid());
			epsAdd.setIsused(ep.getIsused());
			if (ep.getPid()==null||"".equals(ep.getPid())) {
				epsAdd.setType("F");
			}else {
				epsAdd.setType("");
			}
			epsAdd.setDescription(ep.getDescription());
			epsAdd.setStatus("A");
			epsAdd.setState("open");
			epsAdd.setCreated(date);
			epsAdd.setLastmod(date);
			systemService.updateSystemMessage(epsAdd);
			if ((ep.getPid()==null||"".equals(ep.getPid()))&&(ep.getPermissionId()!=null&&!"".equals(ep.getPermissionId()))) {
				Permission eppc= new Permission();
				eppc.setPid(epsAdd.getPermissionId());
				eppc.setName(epsAdd.getName());
				eppc.setDescription(epsAdd.getName());
				eppc.setMyid("Search");
				eppc.setUrl("javascript:void(0);");
				eppc.setSort(1);
				eppc.setCreated(date);
				eppc.setLastmod(date);
				eppc.setStatus("A");
				eppc.setIsused("Y");
				systemService.updateSystemMessage(eppc);
			}
		}else{
	    //修改
		Permission eps=systemService.findSystemMessage(Integer.valueOf(ep.getPermissionId()));
		eps.setName(ep.getName());
		eps.setSort(ep.getSort()==null||(ep.getSort().equals(""))?null:Integer.valueOf(ep.getSort()));
		eps.setUrl(ep.getUrl());
		eps.setMyid(ep.getMyid());
		eps.setIsused(ep.getIsused());
		eps.setDescription(ep.getDescription());
		systemService.updateSystemMessage(eps);
		}
		//HttpSession session = request.getSession();
		//session.invalidate();
		//List<TreeNode> list= systemService.findLeftMenuByLogin();
		//printByJson(response, "1");
		return "/system/moduleMessages";	
		
	}
	/** 
	* @Title: deleSystemMessage 
	* @Description: TODO:删除节点 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*//*
	public String deleSystemMessage(){
		EPermission eps=systemService.findSystemMessage(Integer.valueOf(ep.getPermissionId()));
		eps.setStatus("I");
		systemService.updateSystemMessage(eps);
		printByJson("1");
		return null;
	}
	*//** 
	* @Title: findDreeMapNew 
	* @Description: TODO: treeGrid遍历查找
	* @param @return    设定文件 
	* @return TreeGridPriView    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findDreeMapNew.do")
	//@Transactional(rollbackFor = Exception.class)
	public RolePriView findDreeMapNew(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		RolePriView total =systemService.findDreeMapNew();
		total.setId("0");
		printByJson(response,total);
		return null;
	}
	
	
	/** 
	* @Title: findRole 
	* @Description: TODO:角色列表
	* @param @return    设定文件 
	* @return List<UserRoleView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findRole.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> findRole(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		List<Object> list= systemService.findRole();
		List<UserRoleView> rloeList =new ArrayList<UserRoleView>(); 
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			UserRoleView roView =new UserRoleView();
			roView.setRoleId(obj[0].toString());
			roView.setName("".equals(obj[1])?null:obj[1].toString());
			roView.setDescription("".equals(obj[2])?null:obj[2].toString());
			rloeList.add(roView);
		}
		printByJson(response,rloeList);
		return null;
		
	}
	
	/** 
	* @Title: saveRolePriv 
	* @Description: TODO:保存角色所对应的权限
	* @param @return    设定文件 
	* @return List<UserRoleView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/saveRolePriv.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> saveRolePriv(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		String temp = request.getParameter("temp");
		String rpId = request.getParameter("rpId");
		String[] tempNew=temp.split(",");
		List<String> list= java.util.Arrays.asList(tempNew);
		if(systemService.updateRolePriv(list,Integer.valueOf(rpId))){
			printByJson(response, 1);
		}else{
			printByJson(response, 2);
		}
		return null;
	}
	
	@RequestMapping(value = "/system/addRole.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> addRole(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		String urViewId = request.getParameter("urViewId");
		String urViewName = request.getParameter("urViewName");
		String urViewDescription = request.getParameter("urViewDescription");
		if ("".equals(urViewId)) {
			if (systemService.addRole(urViewName,urViewDescription)) {
				printByJson(response, 1);
			}else {
				printByJson(response, 2);
			}
		}else {
			if (systemService.updRole(urViewName,urViewDescription,Integer.valueOf(urViewId))) {
				printByJson(response, 1);
			}else {
				printByJson(response, 2);
			}
		}
		return null;
		
	} 
	
	
	/** 
	* @Title: findRolePriv 
	* @Description: TODO:查找角色对应的权限
	* @param @return    设定文件 
	* @return List<UserRoleView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findRolePriv.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> findRolePriv(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		String rpId = request.getParameter("rpId");
		Integer[]list=systemService.findRolePriv(Integer.valueOf(rpId));
		printByJson(response,list);
		return null;
	}
	
	
	/** 
	* @Title: findUserMes 用户列表
	* @Description: TODO:
	* @param @return    设定文件 
	* @return List<UserView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findAccountMes.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<AccountView> findAccountMes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		List<AccountView> userList= new ArrayList<AccountView>();
		List<Object> list=systemService.findAccountMes();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			AccountView uView =new AccountView();
			uView.setAccountId( obj[0].toString());
			uView.setUserName((obj[1]==null||"".equals(obj[1]))?null:obj[1].toString());
			uView.setNickName((obj[2]==null||"".equals(obj[2]))?null:obj[2].toString());
			uView.setUserRole((obj[3]==null||"".equals(obj[3]))?null:obj[3].toString());
			userList.add(uView);
		}
		printByJson(response,userList);
		return null;
		
	}
	
	/** 
	* @Title: findUserRole 
	* @Description: TODO: 用户对应角色
	* @param @return    设定文件 
	* @return List<UserRoleView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/findAccountRole.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> findAccountRole(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		String urViewUserId = request.getParameter("urViewUserId");
		List<Object> list=systemService.findAccountRole(Integer.valueOf(urViewUserId));
		List<UserRoleView> uList =new ArrayList<UserRoleView>();
		for (Object object : list) {
			UserRoleView uView =new UserRoleView();
			Object[] obj= (Object[]) object;
			uView.setUserId( obj[0].toString());
			uView.setRoleId(obj[1].toString());
			uList.add(uView);
		}
		printByJson(response,uList);
		return null;
	}
	
	
	/** 
	* @Title: updateUserRole 
	* @Description: TODO:更新用户的角色
	* @param @return    设定文件 
	* @return List<UserRoleView>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/system/updateAccountRole.do")
	//@Transactional(rollbackFor = Exception.class)
	public List<UserRoleView> updateAccountRole(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		String temp = request.getParameter("temp");
		String urViewUserId = request.getParameter("urViewUserId");
		String[] tempNew=temp.split(",");
		List<String> list= java.util.Arrays.asList(tempNew);
		if(systemService.updateAccountRole(list,Integer.valueOf(urViewUserId))){
			printByJson(response,"1");
		}else{
			printByJson(response,"2");
		}
		return null;
	}
	
	/**
	 * 封装返回Json数据的方法
	 */
	private void printByJson(HttpServletResponse response, Object object) {
		PrintWriter out = null;
		response.setContentType("application/json");
        String json = null;      
        try
        {
            out = response.getWriter();
            json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            System.out.println(json);
            //System.out.println("----"+json);
            // serializer.config(SerializerFeature.QuoteFieldNames, false);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        out.print(json);
        out.close();
	}
	
	@Autowired
	private SystemService systemService;
    
}
