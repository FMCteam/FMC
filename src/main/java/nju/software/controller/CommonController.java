package nju.software.controller;

import net.sf.json.JSONObject;
import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.service.impl.BuyServiceImpl;
import nju.software.service.impl.DesignServiceImpl;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.service.impl.MarketServiceImpl;
import nju.software.service.impl.ProduceServiceImpl;
import nju.software.service.impl.QualityServiceImpl;
import nju.software.util.JbpmAPIUtil;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {
	private static List<String> departments = new ArrayList<String>();
	private static Map<String, Object> map = new HashMap<>();

	static {
		departments.add(MarketServiceImpl.ACTOR_MARKET_MANAGER);
		departments.add(DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		departments.add(BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		departments.add(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		departments.add(FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		departments.add(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		departments.add(QualityServiceImpl.ACTOR_QUALITY_MANAGER);

		map.put(MarketServiceImpl.TASK_VERIFY_QUOTE,
				MarketServiceImpl.ACTOR_MARKET_MANAGER);

		map.put(DesignServiceImpl.TASK_VERIFY_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_UPLOAD_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_MODIFY_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		map.put(DesignServiceImpl.TASK_CONFIRM_DESIGN,
				DesignServiceImpl.ACTOR_DESIGN_MANAGER);

		map.put(BuyServiceImpl.TASK_VERIFY_PURCHASE,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_PURCHASE_SAMPLE_MATERIAL,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_CONFIRM_PURCHASE,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		map.put(BuyServiceImpl.TASK_PURCHASE_MATERIAL,
				BuyServiceImpl.ACTOR_PURCHASE_MANAGER);

		map.put(ProduceServiceImpl.TASK_VERIFY_PRODUCE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_PRODUCE_SAMPLE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
		map.put(ProduceServiceImpl.TASK_PRODUCE,
				ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);

		map.put(FinanceServiceImpl.TASK_CONFIRM_SAMPLE_MONEY,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		map.put(FinanceServiceImpl.TASK_CONFIRM_DEPOSIT,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		map.put(FinanceServiceImpl.TASK_CONFIRM_FINAL_PAYMENT,
				FinanceServiceImpl.ACTOR_FINANCE_MANAGER);

		map.put(LogisticsServiceImpl.RESULT_RECEIVE_SAMPLE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.RESULT_SEND_SAMPLE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_WAREHOUSE,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		map.put(LogisticsServiceImpl.TASK_SEND_CLOTHES,
				LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);

		map.put(QualityServiceImpl.TASK_CHECK_QUALITY,
				QualityServiceImpl.ACTOR_QUALITY_MANAGER);
	}

	@RequestMapping(value = "/common/getTaskNumber.do")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public void getTaskNumber(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = getActorId(request);
		Integer number = getTaskNumber(actorId);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("taskNumber", number);
		for (String department : departments) {
			jsonobj.put(department, getTaskNumber(department));
		}
		// jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER,
		// getTaskNumber(actorId));
		if (isMarketStaff(request)) {
			map.put(MarketServiceImpl.TASK_MODIFY_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_MERGE_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_CONFIRM_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_MODIFY_QUOTE, actorId);
			map.put(MarketServiceImpl.TASK_CONFIRM_PRODUCE_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_MODIFY_PRODUCE_ORDER, actorId);
			map.put(MarketServiceImpl.TASK_SIGN_CONTRACT, actorId);
			jsonobj.put(MarketServiceImpl.ACTOR_MARKET_MANAGER, number);
		}

		for (String task : map.keySet()) {
			jsonobj.put(task, getTaskNumber((String) map.get(task), task));
		}
		sendJson(response, jsonobj);
	}

	@RequestMapping(value = "/common/getPic.do")
	@Transactional(rollbackFor = Exception.class)
	public void getPic(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		response.setContentType("image/jpg"); // 设置返回的文件类型
		Integer orderId =Integer.parseInt(request.getParameter("orderId"));
		Order order=orderDAO.findById(orderId);
		String type = request.getParameter("type");
		String file = null;
		if (type.equals("sample")) {
			file = order.getSampleClothesPicture();
		}else if(type.equals("confirmSampleMoney")){
			file = order.getConfirmSampleMoneyFile();
		}else if(type.equals("confirmDepositFile")){
			file = order.getConfirmDepositFile();
		}else if(type.equals("confirmFinalPaymentFile")){
			file = order.getConfirmFinalPaymentFile();
		}else {
			file = order.getReferencePicture();
		}
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			while ((in.read(b)) != -1) {
				os.write(b);
				os.flush();
			}
			in.close();
			os.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private String getActorId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		if (account.getUserRole().equals(MarketServiceImpl.ACTOR_MARKET_STAFF)) {
			return account.getUserId() + "";
		} else {
			return account.getUserRole();
		}
	}

	private boolean isMarketStaff(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		return account.getUserRole().equals(
				MarketServiceImpl.ACTOR_MARKET_STAFF);
	}

	private Integer getTaskNumber(String actorId) {
		List<TaskSummary> task = jbpmAPIUtil.getAssignedTasks(actorId);
		Integer number = 0;
		if (task != null) {
			number = task.size();
		}
		return number;
	}

	private Integer getTaskNumber(String actorId, String taskName) {
		List<TaskSummary> task = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
		Integer number = 0;
		if (task != null) {
			number = task.size();
		}
		return number;
	}

	/**
	 * 封装返回Json数据的方法
	 */
	private void sendJson(HttpServletResponse response, JSONObject jsonobj) {
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonobj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderDAO orderDAO;
}
