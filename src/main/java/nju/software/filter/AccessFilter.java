package nju.software.filter;

import java.io.IOException;

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

import nju.software.dao.BaseDao;
import nju.software.dataobject.Account;

public class AccessFilter implements Filter {
	// @Autowired
	// private ActivitiAPIUtil jbpmAPIUtil;
   private BaseDao baseDao ;
	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// String type = filterConfig.getInitParameter("type");
		// String role=filterConfig.getInitParameter("role");
		String hql="select p.MYID from permission p,account_role ar,account a,role_permission rp where p.pid=null and p.PERMISSION_ID=rp.permission_id and rp.role_id=ar.role_id and ar.account_id=a.account_id and a.username=";
		//baseDao.findByhql2(hql);
		
		
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
                 
				String info_role = filterConfig.getInitParameter(curUser.getUserRole());
				System.out.println(info_role);
                if (info_role != null) {
					String type[] = info_role.split(",");
					for (int i = 0; i < type.length; i++) {
						request.setAttribute("ROLE_" + type[i], true);
						}
				}

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

}
