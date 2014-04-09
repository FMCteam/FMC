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
	@RequestMapping(value = "produce/verifyProduceList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show task");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();

		List<OrderInfo> orderList = produceService.getVerifyProduceList();
		model.addAttribute("order_list", orderList);
		
		return "produce/verifyProduceList";
	}
	/**
	 * 生产验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyProduceSubmit.do", method= RequestMethod.POST)
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

		produceService.verifyProduceSubmit(account, orderId_request, taskId, processId, productVal, comment);
		
		return "redirect:/produce/verifyProduceList.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyProduceDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show detail");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		OrderInfo orderModel = produceService.getVerifyProduceDetail(orderId_request, taskId);
		model.addAttribute("orderModel", orderModel);
		return "produce/verifyProduceDetail";
	}
	
	
	@RequestMapping(value = "/produce/produceSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleProduceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list = produceService.getProduceSampleList();
		model.addAttribute("list", list);
		return "/produce/produceSampleList";
	}
	
	
	@RequestMapping(value = "/produce/produceSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		OrderInfo orderInfo=produceService.getProduceSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/produce/produceSampleDetail";
	}
	
	
	@RequestMapping(value = "/produce/produceSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleProduceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String result = request.getParameter("result");
		String taskId = request.getParameter("taskId");
		produceService.produceSampleSubmit(Long.parseLong(taskId), result);
		return "forward:/produce/produceSampleList.do";
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 生产成本核算跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/computeProduceCostList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	List<OrderInfo>tasks=produceService.getComputeProduceCostList();
		model.addAttribute("tasks", tasks);
		
		return "/produce/computeProduceCostList";
	}
	

	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/computeProduceCostDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		
		String orderId=request.getParameter("orderId");
		OrderInfo task=produceService.getComputeProduceCostInfo(Integer.parseInt(orderId));
		model.addAttribute("task", task);
		return "/produce/computeProduceCostDetail";
	}
	
	
	
	
	/**
	 * 成本核算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	
	
	
	@RequestMapping(value = "produce/computeProduceCostSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doCostAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		
	
		String orderId = (String) request.getParameter("orderId");
	
		String taskId = request.getParameter("taskId");
		
	   //裁剪费用
		String cut_cost = request.getParameter("cut_cost");
		
		//管理费用
		String manage_cost = request.getParameter("manage_cost");
		
		//缝制费用
		String nail_cost = request.getParameter("nail_cost");
		
		//整烫费用
	    String ironing_cost = request.getParameter("ironing_cost");
	
		//锁订费用
		String swing_cost = request.getParameter("swing_cost");
		
		//包装费用
		String package_cost = request.getParameter("package_cost");
	
		//其他费用
		String other_cost = request.getParameter("other_cost");
		
		
	
		produceService.ComputeProduceCostSubmit(
				Integer.parseInt(orderId),
				 Long.parseLong(taskId), 
				Float.parseFloat(cut_cost),
				Float.parseFloat(manage_cost),
				Float.parseFloat(nail_cost),
				Float.parseFloat(ironing_cost),
				Float.parseFloat(swing_cost),
				Float.parseFloat(package_cost),
				Float.parseFloat(other_cost)
						);
		
		return "redirect:/produce/computeProduceCostList.do";
	}
	
	
	
	
	
	@RequestMapping(value = "/produce/produceList.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo>list=produceService.getProduceList();
		model.addAttribute("list", list);
		return "/produce/produceList";
	}
	
	
	
	
	@RequestMapping(value = "/produce/produceDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=produceService.getProduceDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/produce/produceDetail";
	}
	
	
	@RequestMapping(value = "produce/produceSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		String taskId=request.getParameter("taskId");
		String pid=request.getParameter("pid");
		String askAmount=request.getParameter("produceAmount");
		produceService.pruduceSubmit(pid.split(","), askAmount.split(","), Long.parseLong(taskId));
		return "forward:/produce/produceList.do";
	}
}
