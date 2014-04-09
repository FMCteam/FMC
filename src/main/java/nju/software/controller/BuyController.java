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
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.ProductModel;
import nju.software.service.BuyService;
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
public class BuyController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyService buyService;
	
	/**
	 * 采购确认1List
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren1List.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren1List(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="CAIGOUZHUGUAN";
		String action="comfirm_purchase";
		List<OrderModel> orderModelList=orderService.getOrderByActorIdAndTaskname(actor, action);
		model.put("order_model_List", orderModelList);
		model.put("end_url","caigouqueren1Detail");
		return "buy/product_simple_list";
	}
	/**
	 * 采购确认2List
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren2List.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren2List(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="CAIGOUZHUGUAN";
		String action="purchase_ok";
		List<OrderModel> orderModelList=orderService.getOrderByActorIdAndTaskname(actor, action);
		model.put("order_model_List", orderModelList);
		model.put("end_url","caigouqueren2Detail");
		return "buy/product_simple_list";
	}
	/**
	 * 采购确认3List
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren3List.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren3List(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor="CAIGOUZHUGUAN";
		String action="purchase_comfirm";
		List<OrderModel> orderModelList=orderService.getOrderByActorIdAndTaskname(actor, action);
		model.put("order_model_List", orderModelList);
		model.put("end_url","caigouqueren3Detail");
		return "buy/product_simple_list";
	}
	/**
	 * 采购确认1的详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren1Detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren1Detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		
		try
		{
			int int_orderId=Integer.parseInt(orderId);
			long int_taskId=Long.parseLong(taskId);
			long int_processId=Long.parseLong(processId);
			Order order=orderService.findByOrderId(orderId);
			OrderModel orderModel=new OrderModel();
			orderModel.setOrder(order);
			orderModel.setTaskId(int_taskId);
			orderModel.setProcessInstanceId(int_processId);
		
		model.put("orderModel",orderModel);
		model.put("end_url", "caigouqueren1DetailPost");
		return "buy/product_detail";
		}catch(Exception e)
		{
			
		}
		return "buy/product_detail";
	}
	/**
	 * 采购确认2的详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren2Detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren2Detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		try
		{
			int int_orderId=Integer.parseInt(orderId);
			long int_taskId=Long.parseLong(taskId);
			long int_processId=Long.parseLong(processId);
			Order order=orderService.findByOrderId(orderId);
			OrderModel orderModel=new OrderModel();
			orderModel.setOrder(order);
			orderModel.setTaskId(int_taskId);
			orderModel.setProcessInstanceId(int_processId);
		
		model.put("orderModel",orderModel);
		model.put("end_url", "caigouqueren2DetailPost");
		return "buy/product_detail";
		}catch(Exception e)
		{
			
		}
		return "buy/product_detail";
	}
	/**
	 * 采购确认3的详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren3Detail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren3Detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		try
		{
			int int_orderId=Integer.parseInt(orderId);
			long int_taskId=Long.parseLong(taskId);
			long int_processId=Long.parseLong(processId);
			Order order=orderService.findByOrderId(orderId);
			OrderModel orderModel=new OrderModel();
			orderModel.setOrder(order);
			orderModel.setTaskId(int_taskId);
			orderModel.setProcessInstanceId(int_processId);
		
		model.put("orderModel",orderModel);
		model.put("end_url", "caigouqueren3DetailPost");
		return "buy/product_detail";
		}catch(Exception e)
		{
			
		}
		return "buy/product_detail";
	}
	/**
	 * 采购确认1的提交
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren1DetailPost.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren1DetailPost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		boolean purchaseerror=request.getParameter("purchaseerror").equals("0")?true:false;
	//	String productIdList=request.getProductId
		/*
		String productId=request.getParameter("product_id");
		String[] productIdList=productId.split(",");
		String fabricId=request.getParameter("fabric_id");
		String[] fabricIdList=fabricId.split(",");
		String accessoryId=request.getParameter("accessoryId");
		String[] accessoryIdList=accessoryId.split(",");
		
		String ask_amount=request.getParameter("ask_amount");
		String[] ask_amountList=ask_amount.split(",");
		String product_amount=request.getParameter("product_amount");
		String[] product_amountList=product_amount.split(",");
		String qualified_amount=request.getParameter("qualified_amount");
		String[] qualified_amountList=qualified_amount.split(",");
		*/
		//保存各种数据
		//推进流程
		String actor="CAIGOUZHUGUAN";
		long long_taskId=Long.parseLong(taskId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("purchaseerror", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(long_taskId, map, actor);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/buy/caigouqueren1List.do";
	}
	/**
	 * 采购确认2的提交
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren2DetailPost.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren2DetailPost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		boolean purchaseerror=request.getParameter("purchaseerror").equals("1")?true:false;
	//	String productIdList=request.getProductId
		/*
		String productId=request.getParameter("product_id");
		String[] productIdList=productId.split(",");
		String fabricId=request.getParameter("fabric_id");
		String[] fabricIdList=fabricId.split(",");
		String accessoryId=request.getParameter("accessoryId");
		String[] accessoryIdList=accessoryId.split(",");
		
		String ask_amount=request.getParameter("ask_amount");
		String[] ask_amountList=ask_amount.split(",");
		String product_amount=request.getParameter("product_amount");
		String[] product_amountList=product_amount.split(",");
		String qualified_amount=request.getParameter("qualified_amount");
		String[] qualified_amountList=qualified_amount.split(",");
		*/
		//保存各种数据
		//推进流程
		String actor="CAIGOUZHUGUAN";
		long long_taskId=Long.parseLong(taskId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("isworksheet", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(long_taskId, map, actor);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/buy/caigouqueren2List.do";
	}
	/**
	 * 采购确认2的提交
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/caigouqueren3DetailPost.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String caigouqueren3DetailPost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("order_id");
		String taskId=request.getParameter("task_id");
		String processId=request.getParameter("process_id");
		boolean purchaseerror=request.getParameter("purchaseerror").equals("0")?true:false;
	//	String productIdList=request.getProductId
		/*
		String productId=request.getParameter("product_id");
		String[] productIdList=productId.split(",");
		String fabricId=request.getParameter("fabric_id");
		String[] fabricIdList=fabricId.split(",");
		String accessoryId=request.getParameter("accessoryId");
		String[] accessoryIdList=accessoryId.split(",");
		
		String ask_amount=request.getParameter("ask_amount");
		String[] ask_amountList=ask_amount.split(",");
		String product_amount=request.getParameter("product_amount");
		String[] product_amountList=product_amount.split(",");
		String qualified_amount=request.getParameter("qualified_amount");
		String[] qualified_amountList=qualified_amount.split(",");
		*/
		//保存各种数据
		//推进流程
		String actor="CAIGOUZHUGUAN";
		long long_taskId=Long.parseLong(taskId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("procurementerror", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(long_taskId, map, actor);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/buy/caigouqueren3List.do";
	}
	/**
	 * 采购验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("buy verify ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "CAIGOUZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "verification_purchased";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		return "buy/verify";
	}
	
	/**
	 * 采购验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("buy verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean buyVal = Boolean.parseBoolean(request.getParameter("buyVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String comment = request.getParameter("suggestion");
		String taskName = "verification_purchased";
		buyService.verify(account, orderId_request, taskId, processId, buyVal, comment);
		
		return "redirect:/buy/verify.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/verifyDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("buy verify ================ show detail");
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
		Logistics logistics = buyService.getLogisticsByOrderId(orderId_request);
		List<Fabric> fabricList = buyService.getFabricByOrderId(orderId_request);
		List<Accessory> accessoryList = buyService.getAccessoryByOrderId(orderId_request);
		model.addAttribute("orderModel", orderModel);
		model.addAttribute("logistics", logistics);
		model.addAttribute("fabric_list", fabricList);
		model.addAttribute("accessory_list", accessoryList);
		
		return "buy/verify_detail";
	}

	
	
	
	
	
	
	

	/**
	 * 成本核算跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/computeDesignCostList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String computeDesignCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<OrderInfo>list=buyService.getComputePurchaseCostList();
		model.addAttribute("list", list);		
		
		return "design/computeDesignCostList";
	}
	
	
	
	
	
	
	
	
	/**
	 * 显示成本核算详细信息
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/computeDesignCostDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String costAccountingDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getComputePurchaseCostInfo(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
	
		
		return "design/computeDesignCostDetail";
	}
	
	
	
	
	/**
	 * 成本核算
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	
	@RequestMapping(value = "buy/computeDesignCostSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doCostAccounting(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId = (String) request.getParameter("orderId");

		String taskId = request.getParameter("taskId");
			
		String[] fabric_names=request.getParameterValues("fabricName");
		String[] tear_per_meters=request.getParameterValues("tear_per_meter");
		String[] cost_per_meters=request.getParameterValues("cost_per_meter");
		String[] fabric_prices=request.getParameterValues("fabric_price");
		
		
		
		String[] accessory_names=request.getParameterValues("accessoryName");
		String[] tear_per_piece=request.getParameterValues("tear_per_piece");
		String[] cost_per_piece=request.getParameterValues("cost_per_piece");
		String[] accessory_prices=request.getParameterValues("accessory_price");
		
		
		
		 buyService.computePurchaseCostSubmit( 
				   Integer.parseInt(orderId),
				   Long.parseLong(taskId), 
				   fabric_names,
					tear_per_meters,
					cost_per_meters,
					fabric_prices
				   );
		
//        buyService.updateAccessoryCost(orderId_request, taskId, processId, accessory_names, tear_per_piece, cost_per_piece, accessory_prices);
		
		
		
		
		return "redirect:/design/computeDesignCostList.do";
		
	}
	
	
	
	
	
	
}
