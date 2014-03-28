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
		produceService.completeSampleProduceTask(Long.parseLong(taskId), result);
		return "redirect:/produce/sampleProduceList";
	}
}
