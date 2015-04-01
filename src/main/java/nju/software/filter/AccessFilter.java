package nju.software.filter;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import nju.software.dao.impl.AccountDAO;
import nju.software.dataobject.Account;
import nju.software.util.ActivitiAPIUtil;

@Controller

public class AccessFilter implements Filter {
	
	//@Autowired
	static private AccountDAO accountDao ;
	  List permissionList ;
	

	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		filterConfig = null;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
			boolean has_access2 = false;

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String url = request.getServletPath();

		if (url.endsWith("doLogin.do") || url.endsWith("login.do")) 
             chain.doFilter(request, response);
		
		else {
			HttpSession session = request.getSession();
			String uri = request.getRequestURI();
			String file[] = uri.split("/");
			Account curUser = (Account) session.getAttribute("cur_user");

			if (curUser == null) {
				System.out.println("no user");
				// todo 从cookie读取数据，看看是否是记住密码用户。
				if (file[file.length - 1].equals("registCustomer.do")|| file[file.length - 1].equals("forgetPassword.do")|| file[file.length - 1].equals("sendResetPassMail.do")|| file[file.length - 1].equals("checkResetPassLink.do")) {      
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(request.getContextPath()+ "/login.do");
				}
			} else {
				
               List permissionList=	accountDao.findPermissionBYName(curUser.getUserName());
				if(permissionList!=null){
					
					for(int i =0;i<permissionList.size();i++){
						//System.out.println(permissionList.get(i));
						request.setAttribute((String) permissionList.get(i), true);
						
						}
				}
				
				
				request.setAttribute("hasPermission", true);

				request.setAttribute("USER_nick_name", curUser.getNickName());
				request.setAttribute("USER_user_name", curUser.getUserName());
				request.setAttribute("USER_user_role", curUser.getUserRole());

				
			}
			
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;

	}
	@Autowired
	public void setAccountDao(AccountDAO accountDao) {
		this.accountDao = accountDao;
		System.out.println(accountDao==null);
	}
	public AccountDAO getAccountDao(){
		return accountDao ;
		
	}
     public void test(){
    	// permissionList=	accountDao.findPermissionBYName("ADMIN");
    	 
    	 
    	 
    	// System.out.println("==========accountDao:"+accountDao.getClass());
    	
     }
}
