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
import nju.software.util.DateUtil;
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
	
	
	
	
	//=============================收取样衣=====================================
	/**
	 * 收取样衣列表
	 * @author 莫其凡
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logistics/receiveSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list = logisticsService.getReceiveSampleList();
		model.addAttribute("list",list);
		return "/logistics/receiveSampleList";
	}
	
	
	/**
	 * 收取样衣提交
	 * @author 莫其凡
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/receiveSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String result = request.getParameter("result");
		String taskId = request.getParameter("taskId");
		logisticsService.receiveSampleSubmit(Long.parseLong(taskId), result);
		return "forward:/logistics/receiveSampleList.do";
	}
	
	

	
	@RequestMapping(value = "/logistics/warehouseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list=logisticsService.getWarehouseList();
		model.put("list", list);
	    return "/logistics/warehouseList";
	}
	

	
	@RequestMapping(value = "/logistics/warehouseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=logisticsService.getWarehouseDetail(Integer.parseInt(orderId));

	    return "/logistics/warehouseDetail";
	}
	
	/**
	 * order package detail的确认，
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/warehouseSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String warehouseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="WULIUZHUGUAN";
		String action="putstorage";
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		//String iterator=request.getParameter("iterator");
		/*
		<input id="clothes_amount" name="clothes_amount" type="hidden" />
		<input id="clothes_style_color" name="clothes_style_color" type="hidden" />
		<input id="clothes_style_name" name="clothes_style_name" type="hidden" />
		*/
		String clothes_amount=request.getParameter("clothes_amount");
		String clothes_style_color=request.getParameter("clothes_style_color");
		String clothes_style_name=request.getParameter("clothes_style_name");
		String inTime = request.getParameter("package_date");
		Timestamp entryTime = null;
		if (inTime != null && inTime != "") {
			Date inDate = DateUtil.parse(inTime, DateUtil.newFormat);
			entryTime = new Timestamp(inDate.getTime());
		}
		try
		{
			String[] array_amount=clothes_amount.split(",");
			String[] array_color=clothes_style_color.split(",");
			String[] array_name=clothes_style_name.split(",");
			produceService.savePackageDetail(Integer.parseInt(orderId),array_amount,array_color,array_name,entryTime);
			
			//保存package信息
			jbpmAPIUtil.completeTask(Integer.parseInt(taskId), null, actor);
				//推进流程
				
			
			return "forward:/logistics/warehouseList.do";
		}catch(Exception e)
		{
			
		}
		return "redirect:/logistics/rukuList.do";
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


	
	//发送样衣信息
	@RequestMapping(value = "logistics/sendSampleDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleOrderDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String processId = request.getParameter("pid");
		int orderId = Integer.parseInt(id);
		long tid = Long.parseLong(taskId);
		long pid = Long.parseLong(processId);
		
		OrderInfo oi = logisticsService.getSendSampleDetail(orderId,tid);
		model.addAttribute("log", oi);
		return "logistics/sendSampleDetail";
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
		
		List<OrderInfo> orderList = logisticsService.getSendSampleList(s_page,s_number_per_page);
		int page_number = (int) Math.ceil((double) orderList.size()
				/ s_number_per_page);
		model.put("orderList", orderList);

		model.put("page", s_page);
		model.put("page_number", page_number);
		return "logistics/sendSampleList";
	}
	
	//发送样衣
	@RequestMapping(value = "logistics/sendSampleSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		String processId = request.getParameter("processId");
		long tid = Long.parseLong(taskId);
		long pid = Long.parseLong(processId);
		
		logisticsService.sendSampleSubmit(tid, pid);
		return "redirect:/logistics/sendSampleList.do";
	}
	
	
	
	
	/**
	 * 发货跳转链接
	 * @autor Wangjian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/getSendClothesList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String getSendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String user_agent = request.getHeader("user-agent");
		boolean is_wm = user_agent.contains("Windows Phone 6.5");
		
		if(is_wm) {
			List<OrderInfo>list=logisticsService.getSendClothesUncheckedList();
			model.addAttribute("list", list);		
			return "logistic/getSendClothesList2";
		} else {
			List<OrderInfo>list=logisticsService.getSendClothesList();
			model.addAttribute("list", list);		
			return "logistic/getSendClothesList";
		}
		
	}
	
	
	/**
	 * 扫描确认发货
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/checkSendClothes.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String checkSendClothes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		 
		// String orderId=request.getParameter("orderId");
		//  OrderInfo orderInfo=logisticsService.getSendClothesDetail(Integer.parseInt(orderId));
		//
		 // model.addAttribute("orderInfo", orderInfo);
		
		return "logistics/checkSendClothes";
	}
	
	
	/**
	 * 发货页面详细信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/getSendClothesDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String getSendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		 
		 String orderId=request.getParameter("orderId");
		   OrderInfo orderInfo=logisticsService.getSendClothesDetail(Integer.parseInt(orderId));
		   model.addAttribute("orderInfo", orderInfo);
		  
		
		
		return "logistics/getSendClothesDetail";
	}
	
	
	

	/**
	 * 发货
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "logistics/sendClothesSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
	
		String logistics_cost = request.getParameter("logistics_cost");
		
		String orderId = (String) request.getParameter("orderId");

		String taskId = request.getParameter("taskId");
		
	
	
	   logisticsService.sendClothesSubmit( 
			   Integer.parseInt(orderId),
			   Long.parseLong(taskId), 
			   Float.parseFloat(logistics_cost)
			   );
	
//		logisticsService.shipments(account, orderId_request, taskId, processId, design_cost);
		
		return "redirect:/logistics/getSendClothesList";
	}
}
