package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.model.OrderInfo;
import nju.software.model.QuoteConfirmTaskSummary;
import nju.software.service.MarketService;
import nju.software.util.JbpmAPIUtil;

@Service("marketServiceImpl")
public class MarketServiceImpl implements MarketService {

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<QuoteConfirmTaskSummary> getQuoteConfirmTaskSummaryList(
			Integer employeeId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "confirm_quoteorder");
		List<QuoteConfirmTaskSummary> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(employeeId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				QuoteConfirmTaskSummary summary = QuoteConfirmTaskSummary
						.getInstance(
								orderDAO.findById(orderId),
								(Quote) quoteDAO.findByProperty("order_id",
										orderId).get(0), task.getId());
				taskSummarys.add(summary);
			}
		}
		return taskSummarys;
	}

	@Override
	public void completeQuoteConfirmTaskSummary(long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		if (result.equals("1")) {
			data.put("confirmquote", true);
			data.put("eidtquote", false);
		}
		if (result.equals("2")) {
			data.put("confirmquote", false);
			data.put("eidtquote", true);
		}
		if (result.equals("3")) {
			data.put("confirmquote", false);
			data.put("eidtquote", false);
		}
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public List<Product> getProduct(int orderId, String productAskAmount,
			String productColor, String productStyle) {
		// TODO Auto-generated method stub

		String[] amountList = productAskAmount.split("，");
		String[] colorList = productColor.split("，");
		String[] styleList = productStyle.split("，");
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < amountList.length; i++) {
			Product product = new Product();
			product.setOrderId(orderId);
			product.setAskAmount(Integer.parseInt(amountList[i]));
			product.setColor(colorList[i]);
			product.setStyle(styleList[i]);
			product.setProduceAmount(0);
			product.setQualifiedAmount(0);
			productList.add(product);
		}

		return productList;
	}

	@Override
	public boolean confirmProduct(Account account, int orderId, long taskId,
			long processId, boolean comfirmworksheet, List<Product> productList) {
		// TODO Auto-generated method stub
		// String actorId = account.getUserRole();
		String actorId = "SHICHANGZHUANYUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			// 如果通过，创建合同加工单
			if (comfirmworksheet) {
				for (int i = 0; i < productList.size(); i++) {
					productDAO.save(productList.get(i));
				}
			}
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("comfirmworksheet", comfirmworksheet);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		return false;

	}

	@Override
	public List<OrderInfo> getOrderInfoList(Integer employeeId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "sign_contract");
		List<OrderInfo> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(employeeId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				OrderInfo summary = new OrderInfo(orderDAO.findById(orderId),
						task.getId());
				taskSummarys.add(summary);
			}
		}
		return taskSummarys;
	}

	@Override
	public OrderInfo getOrderInfo(Integer orderId,long taskId){
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		OrderInfo orderInfo=new OrderInfo(order, taskId);
		return orderInfo;
	}

	
	@Override
	public void completeSignContract(Integer orderId, double discount,
			long taskId) {
		// TODO Auto-generated method stub
		Order order=orderDAO.findById(orderId);
		order.setDiscount(discount);
		orderDAO.attachDirty(order);
		
		Map<String, Object> data = new HashMap<>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
