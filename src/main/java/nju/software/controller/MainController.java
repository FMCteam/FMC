package nju.software.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;
import nju.software.service.AccountService;

import org.apache.log4j.Logger;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
//	@Autowired
//	private JbpmAPIUtil jbpmAPIUtil;
	
	@Autowired
	private AccountService accountService;
	
//	@Autowired
//	private EmployeeService employeeService;
	
	private static Logger logger = Logger.getLogger(MainController.class);

	@RequestMapping(value = "login.do")
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
//		List<Teacher> t_list = teacherService.getAllTeachers();
//		model.addAttribute("teacherList", t_list);
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
	@Transactional(rollbackFor = Exception.class)
	public String logout(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		HttpSession session = request.getSession();
		session.setAttribute("cur_user", null);
		
		return "redirect:/login.do";

	}
	
	
	@RequestMapping(value = "doLogin.do", method= RequestMethod.POST)
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
//		List<Teacher> t_list = teacherService.getAllTeachers();
//		model.addAttribute("teacherList", t_list);
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
			System.out.println("//============doLogin.do");
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
	@Transactional(rollbackFor = Exception.class)
	public String getDefaultPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
//		List<Teacher> t_list = teacherService.getAllTeachers();
//		model.addAttribute("teacherList", t_list);
		System.out.println("//============default.do");
		return "/index";
	}

	
}
