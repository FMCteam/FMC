package nju.software.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import nju.software.dataobject.Account;
import nju.software.dataobject.TreeNode;
import nju.software.service.AccountService;
import nju.software.service.SystemService;
import nju.software.util.JSONUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private JSONUtil jsonUtil;
	
	private static Logger logger = Logger.getLogger(MainController.class);

	@RequestMapping(value = "login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		HttpSession session = request.getSession();
		Account cur_user = (Account) session.getAttribute("cur_user");
		 
		if(cur_user != null) {
			return "redirect:default.do";
		} else {
			String user_agent = request.getHeader("user-agent").toLowerCase();
			
			if(user_agent.contains("windows phone") || user_agent.contains("android") || user_agent.contains("iphone")) {
				return "login";
			} else {
				return "login";
			}
			
		}
	}
	
	@RequestMapping(value = "logout.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String logout(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		HttpSession session = request.getSession();
		session.setAttribute("cur_user", null);
		session.removeAttribute("treeNodeList");
		
		return "redirect:/login.do";

	}
	
	
	@RequestMapping(value = "doLogin.do", method= RequestMethod.POST)
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
 
		String user_name = request.getParameter("user_name");
		String user_password = request.getParameter("user_password");
		HttpSession session = request.getSession();
		Account account = accountService.vertifyAccount(user_name, user_password);		
		String user_agent = request.getHeader("user-agent").toLowerCase();
		boolean is_wm = user_agent.contains("windows phone") || user_agent.contains("android") || user_agent.contains("iphone");
		if (account != null) {
			session.setAttribute("cur_user", account);
			if("ADMIN, WULIUZHUGUAN".contains(account.getUserRole()) && is_wm) {
				return "redirect:/logistics/mobile/index.do";
			}
		//	System.out.println("//============doLogin.do");
			return "redirect:default.do";
		} else {
			model.addAttribute("state", "wrong");
			if(is_wm) {
				return "login";
			} else {
				return "login";
			}
			 
		}
	}
	
	@RequestMapping(value = "default.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String getDefaultPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		/**
		 * 权限
		 */
		/*
		 */
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("cur_user");
		List<TreeNode> list= systemService.findLeftMenuByLogin(account);
		session.setAttribute("treeNodeList", list);
		return "/index";
	}

	@RequestMapping(value = "defaultContent.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String getDefaultPageContent(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		HttpSession session = request.getSession();
		Account account = (Account)session.getAttribute("cur_user");
		List<TreeNode> list= systemService.findLeftMenuByLogin(account);
		session.setAttribute("treeNodeList", list);
		return "/index_new";
	}
	
	@RequestMapping(value = "overtime.do", method= RequestMethod.GET)
	//@Transactional(rollbackFor = Exception.class)
	public String getOverTimePageContent(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		return "/overtime";
	}
}
