package nju.software.util;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.SqlUtilDAO;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@Controller("InitServlet")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SqlUtilDAO sqlUtilDAO ;
	@Autowired
	private AccountDAO accountdao ;
	

	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		System.out.println("==============program start!");
		ServletContext servletContext = servletConfig.getServletContext();
		// 获取Spring上下文
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		// 部署流程
		RepositoryService repositoryService = (RepositoryService) webApplicationContext
				.getBean("repositoryService");
		repositoryService.createDeployment().addClasspathResource("fmc.bpmn").deploy();
		System.out.println("=============fmc.bpmn deploy success!");
		repositoryService.createDeployment().addClasspathResource("marketStaffAlter.bpmn").deploy();
		System.out.println("=============marketStaffAlter.bpmn deploy sucess!");
		//System.out.println("==========="+accountdao.getClass());
		//System.out.println(new TestSql().getClass().getResource(""));
		boolean isEmpty =accountdao.isEmptytOfTable();
		System.out.println("===========isEmpty:"+isEmpty);
		if(isEmpty){
		sqlUtilDAO.initSQL_All();
		}
		else{
			
			sqlUtilDAO.initSQL_other();
		}
		//accountdao.getPermissionBYName("ADMIN");
		
		
		
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
	public void setSqlUtil(SqlUtilDAO sqlUtilDAO){
		this.sqlUtilDAO=sqlUtilDAO;
	}
	public void setAccountdao(AccountDAO accountdao) {
		this.accountdao = accountdao;
	}
}
