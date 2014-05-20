package nju.software.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

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

import nju.software.dataobject.Account;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.QualityServiceImpl;
import nju.software.util.JbpmAPIUtil;

public class AccessFilter implements Filter {

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	private FilterConfig filterConfig = null;
	public static HashMap<String, String> accessTable = new HashMap<String, String>();
	static {
		accessTable.put("employee", "ADMIN");
		// accessTable.put("customer",
		// "ADMIN, SHICHANGZHUANYUAN, SHICHANGZHUGUAN");
		// accessTable.put("buy", "CAIGOUZHUGUAN");
		// accessTable.put("design", "SHEJIZHUGUAN");
		// accessTable.put("finance", "CAIWUZHUGUAN");
		// accessTable.put("market", "marketStaff, marketManager");
		// accessTable.put("order", "ADMIN, designManager, marketManager");
		// accessTable.put("produce", "SHENGCHANZHUGUAN");
		// accessTable.put("logistics", "logisticsManager");
		// accessTable.put("quality", "qualityManager");

		accessTable.put("customer", "ADMIN,"
				+ MarketServiceImpl.ACTOR_MARKET_MANAGER + ","
				+ MarketServiceImpl.ACTOR_MARKET_STAFF);
		accessTable.put("buy", "ADMIN,"+BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		accessTable.put("design", "ADMIN,"+DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		accessTable.put("finance","ADMIN,"+ FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		accessTable.put("market","ADMIN,"+ MarketServiceImpl.ACTOR_MARKET_MANAGER + ","
				+ MarketServiceImpl.ACTOR_MARKET_STAFF);
		accessTable.put("order", "ALL");
		accessTable.put("produce", "ADMIN,"+ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		accessTable.put("logistics",
				"ADMIN,"+LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		accessTable.put("quality", "ADMIN,"+QualityServiceImpl.ACTOR_QUALITY_MANAGER);
		accessTable.put("other","ALL");
		accessTable.put("account", "ALL");
		// accessTable.put("other",
		// "ADMIN, SHICHANGZHUANYUAN, SHICHANGZHUGUAN");
	};

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String type = filterConfig.getInitParameter("type");
		boolean has_access = false;

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();

		Account curUser = (Account) session.getAttribute("cur_user");
		//System.out.println("null "+type);
		if (curUser == null) {
			// System.out.println("no user");
			// todo 从cookie读取数据，看看是否是记住密码用户。
			has_access = false;
			//System.out.println("null "+type);
		} else {
			String user_role = curUser.getUserRole();
			String access = accessTable.get(type);
			//System.out.println("null "+access);
			if (access != null
					&& ((access.equals("ALL") && !user_role.equals("CUSTOMER")) || access
							.contains(user_role))) {
				has_access = true;
			} else {
				has_access = false;
			}
//			 has_access = true;

		}
		//System.out.println("//============ "+curUser.getUserRole()+" "+has_access);
		if (has_access) {
			// System.out.println(curUser.getUserRole());

			for (Entry<String, String> acc : accessTable.entrySet()) {

				// System.out.println(acc.getKey() + "," +
				// acc.getValue().contains(curUser.getUserRole()));

				if (acc.getValue().contains(curUser.getUserRole())||acc.getValue().equals("ALL")) {

					request.setAttribute("ROLE_" + acc.getKey(), true);
				}

				// acc.getValue().contains(curUser.getUserRole()));
				// if(acc.getValue().contains(curUser.getUserRole())){

				//request.setAttribute("ROLE_" + acc.getKey(), true); // acc.getValue().contains(curUser.getUserRole()));
				// }
			}
			//System.out.println("//============filter.do");
			request.setAttribute("USER_nick_name", curUser.getNickName());
			request.setAttribute("USER_user_name", curUser.getUserName());

			//
			// if(jbpmAPIUtil==null){
			// System.out.println("jbpm null");
			// }
			// if(curUser.getUserRole().equals("marketStaff")){
			// System.out.println(jbpmAPIUtil.getAssignedTasks(curUser.getUserId()+""));
			// int
			// taskNumber=jbpmAPIUtil.getAssignedTasks(curUser.getUserId()+"").size();
			// request.setAttribute("taskNumber", taskNumber);
			// }else{
			//
			// System.out.println(jbpmAPIUtil.getAssignedTasks(curUser.getUserId()+""));
			// int
			// taskNumber=jbpmAPIUtil.getAssignedTasks(curUser.getUserRole()).size();
			// request.setAttribute("taskNumber", taskNumber);
			// }
			//
			// request.setAttribute(, arg1);

		} else {
			response.sendRedirect(request.getContextPath() + "/login.do");
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;

	}

}
