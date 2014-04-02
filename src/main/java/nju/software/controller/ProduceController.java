package nju.software.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.QuoteConfirmTaskSummary;
import nju.software.model.SampleProduceTask;
import nju.software.model.SampleProduceTaskSummary;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.util.JbpmAPIUtil;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProduceController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProduceService produceService;
	
	/**
	 * 生产验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHENGCHANZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "production_verification";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		model.addAttribute("order_list", orderList);
		
		return "produce/verify";
	}
	/**
	 * 生产验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("produce verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean productVal = Boolean.parseBoolean(request.getParameter("productVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String comment = request.getParameter("suggestion");
		String taskName = "production_verification";
		produceService.verify(account, orderId_request, taskId, processId, productVal, comment);
		
		return "redirect:/produce/verify.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show detail");
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
		Logistics logistics =produceService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = produceService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = produceService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "produce/verify_detail";
	}
	
	
	@RequestMapping(value = "produce/sampleProduceList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleProduceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<SampleProduceTaskSummary> tasks = produceService.getSampleProduceTaskSummaryList();
		model.addAttribute("tasks", tasks);
		return "/produce/sampleProduceList";
	
	}
	
	
	@RequestMapping(value = "produce/sampleProduce.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleProduce(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		SampleProduceTask task=produceService.getSampleProduceTask(Integer.parseInt(orderId), Long.parseLong(taskId));
		model.addAttribute("task", task);
		return "/produce/sampleProduce";
	}
	
	
	@RequestMapping(value = "produce/sampleProduceSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleProduceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String result = request.getParameter("result");
		
		String taskId = request.getParameter("taskId");
		System.out.println(result+":"+taskId);
		produceService.completeSampleProduceTask(Long.parseLong(taskId), result);
		return "forward:/produce/sampleProduceList.do";
	}
	
	
	
	
	
	
	/**
	 * 生产成本核算跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/costAccounting.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String costAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println(" produce cost Accounting ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHENGCHANZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "business_accounting";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "produce/cost_accounting";
	}
	
	

	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/costAccountingDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String costAccountingDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce costAccounting Detail ================ costAccountingDetail");
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
		Logistics logistics = produceService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = produceService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = produceService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "produce/costAccounting_detail";
	}
	
	
	
	
	/**
	 * 成本核算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	
	
	
	@RequestMapping(value = "produce/doCostAccounting.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doCostAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
	System.out.println("do  produce cost Accounting ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
	
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		//裁剪费用
		String cut_cost_temp = request.getParameter("cut_cost");
		float cut_cost=Float.parseFloat(cut_cost_temp);
		//管理费用
		String manage_cost_temp = request.getParameter("manage_cost");
		float manage_cost=Float.parseFloat(manage_cost_temp);
		//缝制费用
		String nail_cost_temp = request.getParameter("nail_cost");
		float nail_cost=Float.parseFloat(nail_cost_temp);
		//整烫费用
	    String ironing_cost_temp = request.getParameter("ironing_cost");
		float ironing_cost=Float.parseFloat(ironing_cost_temp);
		//锁订费用
		String swing_cost_temp = request.getParameter("swing_cost");
		float swing_cost=Float.parseFloat(swing_cost_temp);
		//包装费用
		String package_cost_temp = request.getParameter("package_cost");
		float package_cost=Float.parseFloat(package_cost_temp);
		//其他费用
		String other_cost_temp = request.getParameter("other_cost");
		float other_cost=Float.parseFloat(other_cost_temp);
		
		String taskName = "design_accounting ";
		produceService.costAccounting(account, orderId_request, taskId, processId, cut_cost,
		manage_cost,nail_cost,ironing_cost,swing_cost,package_cost,other_cost);
		
		return "redirect:/produce/costAccounting.do";
	}
	
	
	
	
	
	@RequestMapping(value = "produce/produceList.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String produceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo>tasks=produceService.getProduceList();
		model.addAttribute("tasks", tasks);
		return "/produce/produceList";
	}
	
	
	
	
	@RequestMapping(value = "produce/produce.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String produce(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo task=produceService.getProduceInfo(Integer.parseInt(orderId));
		model.addAttribute("task", task);
		return "/produce/produce";
	}
	
	
	@RequestMapping(value = "produce/produceSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String produceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String pid=request.getParameter("pid");
		String askAmount=request.getParameter("askAmount");
		
		//String orderId=request.getParameter("orderId");
		//OrderInfo task=produceService.getProduceInfo(Integer.parseInt(orderId));
		//model.addAttribute("task", task);
		return "redirect:/produce/produceList.do";
	}

	
}
