package nju.software.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.service.AccountService;
import nju.software.service.EmployeeService;
import nju.software.service.MarketService;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.QualityServiceImpl;
import nju.software.service.impl.SweaterMakeServiceImpl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mysql.jdbc.log.Log;


@Controller("InitServlet")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SqlUtil sqlUtil ;
	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		System.out.println("==============program start=====================");
		ServletContext servletContext = servletConfig.getServletContext();
		// 获取Spring上下文
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		// 部署流程
		RepositoryService repositoryService = (RepositoryService) webApplicationContext
				.getBean("repositoryService");
		repositoryService.createDeployment().addClasspathResource("fmc.bpmn").deploy();
		System.out.println("=============fmc.bpmn deploy success!====================");
	  
		//System.out.println(new TestSql().getClass().getResource(""));
		 sqlUtil.initSQL();
		
		
		
		
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest reqquest,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(1001, "本页面不允许发送请求");
	}
	public void setSqlUtil(SqlUtil sqlUtil){
		this.sqlUtil=sqlUtil;
	}
}
