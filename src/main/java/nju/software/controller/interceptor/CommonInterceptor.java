package nju.software.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor{
	private Logger logger = Logger.getLogger(CommonInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		HttpSession session = arg0.getSession();
		Account currentUser = (Account)session.getAttribute("cur_user");
		String url = arg0.getServletPath();

		if (url.endsWith("doLogin.do") || url.endsWith("login.do")){
			return true;
		}
		if(currentUser == null){
			logger.info("session超时,跳转到overtime.jsp页面");
			arg1.sendRedirect(arg0.getContextPath()+"/overtime.jsp");
			return false;
		}
		return true;
	}

}
