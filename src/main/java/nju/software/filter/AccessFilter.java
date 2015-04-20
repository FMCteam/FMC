package nju.software.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import nju.software.dao.impl.AccountDAO;
import nju.software.dataobject.Account;

@Controller
public class AccessFilter implements Filter {

	private static AccountDAO accountDao;
	List permissionList;
	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		setFilterConfig(null);

	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		String excludePath = filterConfig.getInitParameter("excludePath");
		String[] excludePaths = excludePath.split(";");

		String url = request.getServletPath();
		if(isContain(url, excludePaths)){
			chain.doFilter(request, response);
			return;
		}
		else{
			HttpSession session = request.getSession();
			Account curUser = (Account) session.getAttribute("cur_user");
	
			if (curUser == null) {
				System.out.println("session超时");
				response.sendRedirect(request.getContextPath() + "/overtime.do");
			} else {
				List permissionList = accountDao.findPermissionBYName(curUser.getUserName());
				if (permissionList != null) {
	
					for (int i = 0; i < permissionList.size(); i++) {
						request.setAttribute((String) permissionList.get(i), true);
	
					}
				}
				request.setAttribute("hasPermission", true);
	
				request.setAttribute("USER_nick_name", curUser.getNickName());
				request.setAttribute("USER_user_name", curUser.getUserName());
				request.setAttribute("USER_user_role", curUser.getUserRole());
				
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);

	}
	
	public boolean isContain(String url,String[] excludePaths){
		//移动端的访问链接不做过滤
		if ( url.contains("mobile")) {
			return true;
		}
		for (int i = 0; i < excludePaths.length; i++) {
			if (url.endsWith(excludePaths[i])) {
				return true;
			}
		}
		return false;
	}

	@Autowired
	public void setAccountDao(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	public AccountDAO getAccountDao() {
		return accountDao;

	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
