package nju.software.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.service.LogisticsService;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogisticsController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "market/addMarketOrder1.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String addMarketOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Order order = new Order();
		order.setAskAmount(10);
		order.setCustomerName("张三");
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		order.setOrderTime(timestamp);
		order.setOrderState("A");
		order.setCustomerId(1);
		order.setEmployeeId(1);
		order.setHasPostedSampleClothes((short) 0);
		order.setIsNeedSampleClothes((short) 1);
		orderService.addOrder(order);
		Integer orderId = order.getOrderId();
		Logistics log = new Logistics();
		log.setOrderId(orderId);
		log.setInPostSampleClothesNumber("1111");
		log.setInPostSampleClothesType("shengtong");
		logisticsService.addLogistics(log);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		Account curUser = (Account) request.getSession().getAttribute(
				"cur_user");
		params.put("employeeId", curUser.getUserId());
		params.put("needclothes", true);
		params.put("sendclothes", true);

		params.put("receivedsample", false);
		params.put("editOrder", false);

		/*
		 * String actorId=(String) request.getSession().getAttribute("actorId");
		 * List<TaskSummary> list =jbpmAPIUtil.getAssignedTasks(actorId); for
		 * (TaskSummary task : list) { if(task.getName().equals("addOrder")){
		 * //直接进入到下一个流程时 Map<String, Object> map = new HashMap<>(); try {
		 * jbpmAPIUtil.completeTask(task.getId(), null, actorId); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } //需要获取task中的数据 WorkflowProcessInstance
		 * process=(WorkflowProcessInstance)
		 * jbpmAPIUtil.getKsession().getProcessInstance
		 * (task.getProcessInstanceId()); String orderId =(String)
		 * process.getVariable("orderId"); process.getVariable(""); }
		 * 
		 * }
		 */

		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程启动成功！");
		} catch (Exception e) {
			System.out.println("流程启动失败！");
			e.printStackTrace();
		}
		return "redirect:/market/add.do";
	}

	/**
	 * 得到物流样本列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/sampleOrderList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sampleOrderGet(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// first recieve order list that need sample
		/*
		 * String page = request.getParameter("page"); String number =
		 * request.getParameter("number_per_page"); int s_page = 1; int s_number
		 * = 10; if (!StringUtil.isEmpty(page)) { s_page =
		 * Integer.parseInt(page);
		 * 
		 * } if (!StringUtil.isEmpty(number)) { s_number =
		 * Integer.parseInt(number); } Map<String, Object> map = new
		 * HashMap<String, Object>(); map.put("page", s_page);
		 * map.put("number_per_page", s_number);
		 * 
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * map.put("page", 1); map.put("number_per_page", 1000); List<Object>
		 * object = orderService.getOrderWithSample(map); model.put("orderList",
		 * object.get(0)); // model.put("page", ((Map)
		 * object.get(1)).get("page")); // model.put("page_number", ((Map)
		 * object.get(1)).get("page_number"));
		 * 
		 * return "logistics/to_receive_sample_list";
		 */
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				"WULIUZHUGUAN", "received_sample");
		List<ComposeOrderAndLog> logList = new ArrayList<ComposeOrderAndLog>();
		for (TaskSummary task : list) {
			WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
					.getKsession().getProcessInstance(
							task.getProcessInstanceId());
			String orderId1 = process.getVariable("orderId").toString();
			Order o = orderService.findByOrderId(orderId1);

			Logistics l = logisticsService.findByOrderId(orderId1);
			ComposeOrderAndLog log = new ComposeOrderAndLog(
					task.getProcessInstanceId(), task.getId(),o, l);
			logList.add(log);
		}
		model.put("orderList", logList);

		return "logistics/to_receive_sample_list";
	}

	public class ComposeOrderAndLog {
		public long getTaskId() {
			return taskId;
		}

		public void setTaskId(long taskId) {
			this.taskId = taskId;
		}

		public long processId;
		public long taskId;
		public Order o;
		public Logistics log;

		public ComposeOrderAndLog(long processId,long taskId, Order o, Logistics log) {
			this.taskId=taskId;
			this.processId = processId;
			this.o = o;
			this.log = log;
		}

		public long getProcessId() {
			return processId;
		}

		public void setProcessId(long processId) {
			this.processId = processId;
		}

		public Order getO() {
			return o;
		}

		public void setO(Order o) {
			this.o = o;
		}

		public Logistics getLog() {
			return log;
		}

		public void setLog(Logistics log) {
			this.log = log;
		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/list.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String findAll(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Order> r = this.orderService.findAll();
		System.out.println(r.size());
		return "logistics/to_receive_sample_list";
	}

	/**
	 * 收到或者未收到样衣
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/sampleOrderRequest.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sampleOrderRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// get the orderid and complete the jbpm task
		int confirm = Integer.parseInt(request.getParameter("confirm"));
		boolean receivedSample = false;
		int confirmIntValue = 2;
		if (confirm == 1) {
			receivedSample = true;
			confirmIntValue = 1;
		}
		String orderId = request.getParameter("orderId");

		String processInstanceId = request.getParameter("processInstanceId");

		String taskId=request.getParameter("taskId");
		boolean success = false;

		// 直接进入到下一个流程时
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivedsample", receivedSample);
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(Long.parseLong(processInstanceId));
		String orderId1 = process.getVariable("orderId").toString();
		
		// task orderId is the same as the request's orderId
		if (orderId.equals(orderId1)) {
			// complete the workflow
			System.out.println("found it");
			try {
				
				jbpmAPIUtil.completeTask(Long.parseLong(taskId), map, "WULIUZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// modify the order state
			Order or = orderService.findByOrderId(orderId);
			or.setHasPostedSampleClothes((short) confirmIntValue);
			success = orderService.merge(or);
			
		}

		model.put("success", success);
		System.out.println("successfully");
		return "/market/sampleOrderList.do";

	}
}
