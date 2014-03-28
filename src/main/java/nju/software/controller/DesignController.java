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
import nju.software.model.OrderModel;
import nju.software.service.DesignService;
import nju.software.service.OrderService;
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
public class DesignController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DesignService designService;
	
	/**
	 * 设计验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "design_verification ";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		model.addAttribute("order_list", orderList);
		
		return "design/verify";
	}
	/**
	 * 设计验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("design verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean designVal = Boolean.parseBoolean(request.getParameter("designVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String comment = request.getParameter("suggestion");
		String taskName = "design_verification ";
		designService.verify(account, orderId_request, taskId, processId, designVal, comment);
		
		return "redirect:/design/verify.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verifyDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show detail");
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
		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "design/verify_detail";
	}

	
	


	/**
	 * 成本核算跳转链接
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/costAccounting.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String costAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println(" design cost Accounting ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "design_accounting";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		
		return "design/cost_accounting";
	}
	
	
	
	
	
	
	
	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/costAccountingDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String costAccountingDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("buy costAccounting Detail ================ costAccountingDetail");
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
		Logistics logistics = designService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = designService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = designService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "design/costAccounting_detail";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
