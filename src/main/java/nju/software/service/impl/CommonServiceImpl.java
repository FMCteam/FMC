package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccountDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.process.service.MainProcessService;
import nju.software.process.service.MarketstaffAlterProcessService;
import nju.software.service.CommonService;
import nju.software.service.MarketService;
import nju.software.util.Constants;
import nju.software.util.JSONUtil;
import nju.software.util.SessionUtil;

@Service("commonServiceImpl")
public class CommonServiceImpl implements CommonService {

	public static final String ACTOR_ADMIN = "ADMIN";

	@Override
	public Integer getTaskNumber(String userId) {
		List<Task> tasks = mainProcessService.getTasksOfUser(userId);
		return tasks == null ? 0: tasks.size();
	}

	@Override
	public Integer getTaskNumber(String userId, String taskName) {
		List<Task> tasks = mainProcessService.getAllTasksOfUserByTaskName(userId, taskName);
		return tasks == null ? 0: tasks.size();
	}

	@Override
	public Integer getAllTaskNumber() {
		int sum = 0;
		for (int i = 0; i < groups.length; i++) {
			sum += getTaskNumber(groups[i]);
		}
		sum += getMarketStaffTaskNumber();
		return sum;
	}

	@Override
	public Integer getMarketStaffTaskNumber() {
		int sum = 0;
		List<Account> marketStaffs = accountDAO
				.findByUserRole(MarketServiceImpl.ACTOR_MARKET_STAFF);
		for (Account account : marketStaffs) {
			sum += getTaskNumber(String.valueOf(account.getUserId()));
		}
		// 这边也要加上Admin下的订单，以防万一
		List<Account> admin = accountDAO
				.findByUserRole(CommonServiceImpl.ACTOR_ADMIN);
		sum += getTaskNumber(admin.get(0).getUserId() + "");
		return sum;
	}

	@Override
	public Integer getMarketStaffTaskNumber(String taskName) {
		int sum = 0;
		List<Account> marketStaffs = accountDAO
				.findByUserRole(MarketServiceImpl.ACTOR_MARKET_STAFF);
		for (Account account : marketStaffs) {
			sum += getTaskNumber(String.valueOf(account.getUserId()), taskName);
		}
		// 这边也要加上Admin下的订单，以防万一
		List<Account> admin = accountDAO
				.findByUserRole(CommonServiceImpl.ACTOR_ADMIN);
		sum += getTaskNumber(admin.get(0).getUserId() + "", taskName);
		return sum;
	}
	
	
	@Override
	public Integer getMarketSecrtaryTaskNumber() {
		List<Map<String, Object>> orders = marketService.getOrdersTodo();
		if (orders!=null) {
			return orders.size();
		}
		return 0;
	}
	
	@Override
	public Account getCurAccount(HttpServletRequest request, HttpServletResponse response){
		String jsessionId = request.getParameter(Constants.PARAM_SESSION_ID);
		HttpSession session = SessionUtil.getSession(request, jsessionId);
		Map<String, Object> result = new HashMap<>();
		//session不存在，则通知session过期，操作失败
		if (session == null) {
			result.put(Constants.JSON_IS_SUCCESS, false);
			result.put(Constants.JSON_NOTIFY, "session overdue");
			jsonUtil.sendJson(response, result);
			return null;
		}
		Account account = (Account) session.getAttribute(Constants.PARAM_CUR_ACCOUNT);
		return account;
	}

	private String[] groups = new String[] {
			BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
			DesignServiceImpl.ACTOR_CRAFT_MANAGER,
			DesignServiceImpl.ACTOR_DESIGN_MANAGER,
			FinanceServiceImpl.ACTOR_FINANCE_MANAGER,
			LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER,
			MarketServiceImpl.ACTOR_MARKET_MANAGER,
			ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
			QualityServiceImpl.ACTOR_QUALITY_MANAGER,
			SweaterMakeServiceImpl.ACTOR_SWEATER_MANAGER };

	@Autowired
	private MainProcessService mainProcessService;
	
	@Autowired
	private MarketstaffAlterProcessService marketstaffAlterProcessService;

	@Autowired
	private MarketService marketService;
	
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	public JSONUtil jsonUtil;
}
