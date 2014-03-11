package nju.software.util;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 采用代理模式:
 * 在spring启动完成后执行初始化操作,这样做的目的是让tomcat在启动后，spring实例化HumanTaskServer所需的对象
 * @author william
 * @
 */
public class DelegatingServletProxy extends GenericServlet {

	private static final long serialVersionUID = 1L;
	private String targetBean;
	private Servlet proxy;

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		proxy.service(request, response);
	}

	@Override
	public void init() throws ServletException {
		this.targetBean = getServletName();
		getServletBean();
		proxy.init(getServletConfig());
	}

	private void getServletBean(){
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.proxy = (Servlet)wac.getBean(targetBean);
	}
}

