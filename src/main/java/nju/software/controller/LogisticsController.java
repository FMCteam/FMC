package nju.software.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.service.LogisticsService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
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
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	@Autowired
	private ProduceService produceService;
	/**
	 * 返回一个orderModelList
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/rukuList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String rukuList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="WULIUZHUGUAN";
		String action="putstorage";
		List<OrderModel> orderModelList=orderService.getOrderByActorIdAndTaskname(actor, action);
		model.put("order_model_list", orderModelList);
	    return "logistics/ruku_order_list";
	}
	
	/**
	 * 返回一个order package detail的界面，package列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/rukuDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String rukuDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="WULIUZHUGUAN";
		String action="putstorage";
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		//OrderInfo orderInfo=produceService.getProduceInfo(Integer.parseInt(orderId));
		List<Product> productList=produceService.getProductByOrderId(Integer.parseInt(orderId));
		List<nju.software.dataobject.Package> packageList=produceService.getPackageByOrderId(Integer.parseInt(orderId));
		List<List<PackageDetail>> packageDetailList=produceService.getProductDetailByPackage(packageList);
		model.put("order_id", orderId);
		model.put("task_id", taskId);
		model.put("process_id", processId);
		model.put("product_list", productList);
		model.put("package_list", packageList);
		model.put("package_detail_list", packageDetailList);
		/*
		model.put("product_list", orderInfo.getProducts());
		List<List<PackageDetail>> packagesDetailList=orderInfo.getPackageDetails();
		List<Package> packagesList=orderInfo.getPackages();
		int sum=0;
		for(List<PackageDetail> list:packagesDetailList)
		{
			sum+=list.size();
		}
		model.put("package_detail_list", packagesDetailList);
		model.put("package_list",packagesList);
		model.put("sum",sum);
		*/
	    return "logistics/ruku_detail";
	}
	
	/**
	 * order package detail的确认，
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/rukuDetailPost.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String rukuDetailPost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="WULIUZHUGUAN";
		String action="putstorage";
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		String iterator=request.getParameter("iterator");
		try
		{
			int int_iterator=Integer.parseInt(iterator);
			//保存package信息
			
			if(int_iterator==1)
			{
				//推进流程
				
			}
			return "redirct:/logistics/rukuList.do";
		}catch(Exception e)
		{
			
		}
		return "redirct:/logistics/rukuList.do";
	}
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
	 * 
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
		String page = request.getParameter("page");
		String number_per_page = request.getParameter("number_per_page");
		int s_page = 1;
		int s_number_per_page = 10;
		if (!StringUtil.isEmpty(page)) {
			s_page = Integer.parseInt(page);
		}
		if (!StringUtil.isEmpty(number_per_page)) {
			s_number_per_page = Integer.parseInt(number_per_page);
		}

		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				"WULIUZHUGUAN", "received_sample");
		int page_number = (int) Math.ceil((double) list.size()
				/ s_number_per_page);
		int start = s_number_per_page * (s_page - 1);
		List<ComposeOrderAndLog> logList = new ArrayList<ComposeOrderAndLog>();
		int i = 0;
		int j = 0;
		for (TaskSummary task : list) {
			if (i >= start && j < s_number_per_page) {
				WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
						.getKsession().getProcessInstance(
								task.getProcessInstanceId());
				String orderId1 = process.getVariable("orderId").toString();
				Order o = orderService.findByOrderId(orderId1);

				Logistics l = logisticsService.findByOrderId(orderId1);
				ComposeOrderAndLog log = new ComposeOrderAndLog(
						task.getProcessInstanceId(), task.getId(), o, l);
				logList.add(log);
				j++;
			}
			i++;
		}
		model.put("orderList", logList);

		model.put("page", s_page);
		model.put("page_number", page_number);
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

		public ComposeOrderAndLog(long processId, long taskId, Order o,
				Logistics log) {
			this.taskId = taskId;
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
	@RequestMapping(value = "logistics/list.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String findAll(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Order> r = this.orderService.findAll();
		System.out.println(r.size());
		return "logistics/to_receive_sample_list";
	}

	/**
	 * 收到或者未收到样衣
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/sampleOrderRequest.do", method = RequestMethod.POST)
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

		String taskId = request.getParameter("taskId");
		boolean success = false;

		// 直接进入到下一个流程时
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivedsample", receivedSample);
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(
						Long.parseLong(processInstanceId));
		String orderId1 = process.getVariable("orderId").toString();

		// task orderId is the same as the request's orderId
		if (orderId.equals(orderId1)) {
			// complete the workflow
			System.out.println("found it");
			try {

				jbpmAPIUtil.completeTask(Long.parseLong(taskId), map,
						"WULIUZHUGUAN");
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
		return "redirect:/logistics/sampleOrderList.do";

	}
	
	//发送样衣信息
	@RequestMapping(value = "logistics/sendSampleOrderDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String processId = request.getParameter("pid");
		long tid = Long.parseLong(taskId);
		long pid = Long.parseLong(processId);
		Order o = orderService.findByOrderId(id);
		Logistics l = logisticsService.findByOrderId(id);
		Customer c = customerService.findByCustomerId(o.getCustomerId());
		Employee e = employeeService.getEmployeeById(o.getEmployeeId());
		ComposeOrderAndLog log = new ComposeOrderAndLog(pid, tid, o, l);
		model.addAttribute("log", log);
		model.addAttribute("customer", c);
		model.addAttribute("employee", e);
		return "logistics/send_sample";
	}
	
	//发送样衣list
	@RequestMapping(value = "logistics/sendSampleList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String page = request.getParameter("page");
		String number_per_page = request.getParameter("number_per_page");
		int s_page = 1;
		int s_number_per_page = 10;
		if (!StringUtil.isEmpty(page)) {
			s_page = Integer.parseInt(page);
		}
		if (!StringUtil.isEmpty(number_per_page)) {
			s_number_per_page = Integer.parseInt(number_per_page);
		}

		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				"WULIUZHUGUAN", "deliver_sample");
		int page_number = (int) Math.ceil((double) list.size()
				/ s_number_per_page);
		int start = s_number_per_page * (s_page - 1);
		List<ComposeOrderAndLog> logList = new ArrayList<ComposeOrderAndLog>();
		int i = 0;
		int j = 0;
		for (TaskSummary task : list) {
			if (i >= start && j < s_number_per_page) {
				WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
						.getKsession().getProcessInstance(
								task.getProcessInstanceId());
				String orderId1 = process.getVariable("orderId").toString();
				Order o = orderService.findByOrderId(orderId1);

				Logistics l = logisticsService.findByOrderId(orderId1);
				ComposeOrderAndLog log = new ComposeOrderAndLog(
						task.getProcessInstanceId(), task.getId(), o, l);
				logList.add(log);
				j++;
			}
			i++;
		}
		model.put("orderList", logList);

		model.put("page", s_page);
		model.put("page_number", page_number);
		return "logistics/to_send_sample_list";
	}
	
	//发送样衣
	@RequestMapping(value = "logistics/sendSampleOrder.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String processId = request.getParameter("processId");
		long tid = Long.parseLong(taskId);
		long pid = Long.parseLong(processId);
		Order o = orderService.findByOrderId(id);
		Logistics l = logisticsService.findByOrderId(id);
		
		logisticsService.sendSample(tid, "WULIUZHUGUAN", pid);
		return "redirect:/logistics/sendSampleList.do";
	}
	
	
	/**
	 * 发货跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/shipments.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String shipments(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("logistics shipments ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "WULIUZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "confirm_shipments";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		
		model.addAttribute("order_list", orderList);
		
		return "logistic/shipments";
	}
	
	
	
	
	/**
	 * 发货页面详细信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/shipments_detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String shipments_detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("logistics shipments_detail================");
		
		OrderModel orderModel = null;
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		orderModel = orderService.getOrderDetail(orderId_request, taskId, processId);
//		Logistics logistics = logisticsService.getLogisticsByOrderId(orderId_request);

		model.addAttribute("orderModel", orderModel);
//		model.addAttribute("logistics", logistics);

		
		
		return "logistics/shipments_detail";
	}
	
	
	

	/**
	 * 发货
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/doShipments.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doShipments(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("logistics shipments_detail================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");

		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String comment = request.getParameter("logistics_cost");
		String taskName = "confirm_shipments ";
	
//		logisticsService.shipments(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/logistics/shipments";
	}
	
	
	
	
	
	
	
	
}
