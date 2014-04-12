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
	

	
	
	//========================样衣采购===========================
	@RequestMapping(value = "/buy/purchaseSampleMaterialList.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list=buyService.getPurchaseSampleMaterialList();
		model.put("list", list);
		return "/buy/purchaseSampleMaterialList";
	}

	
	@RequestMapping(value = "/buy/purchaseSampleMaterialDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getPurchaseSampleMaterialDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/purchaseSampleMaterialDetail";
	}
	
	
	@RequestMapping(value = "/buy/purchaseSampleMaterialSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId=request.getParameter("task_id");
		String result=request.getParameter("purchaseerror");
		buyService.purchaseSampleMaterialSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/purchaseSampleMaterialList.do";
	}
	

	//========================生产验证===========================
	@RequestMapping(value = "/buy/confirmPurchaseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list=buyService.getConfirmPurchaseList();
		model.put("list", list);
		return "/buy/confirmPurchaseList";
	}

	@RequestMapping(value = "/buy/confirmPurchaseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getConfirmPurchaseDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/confirmPurchaseDetail";
	}
	
	
	@RequestMapping(value = "/buy/confirmPurchaseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId=request.getParameter("task_id");
		String result=request.getParameter("purchaseerror");
		buyService.purchaseSampleMaterialSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/confirmPurchaseList.do";
	}
	
	
	//========================生产采购===========================
	@RequestMapping(value = "/buy/purchaseMaterialList.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list=buyService.getPurchaseMaterialList();
		model.put("list", list);
		return "/buy/purchaseMaterialList";
	}

	
	@RequestMapping(value = "/buy/purchaseMaterialDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getPurchaseMaterialDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/purchaseMaterialDetail";
	}
		

	@RequestMapping(value = "/buy/purchaseMaterialDetailSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialDetailSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId=request.getParameter("task_id");
		String result=request.getParameter("purchaseerror");
		buyService.purchaseSampleMaterialSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/purchaseMaterialList.do";
	}


	
	


	
	/**
	 * 采购验证跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/verifyPurchaseList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<OrderInfo> list=buyService.getVerifyPurchaseList();
		model.put("list", list);
		
		
//		System.out.println("buy verify ================ show task");
//		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
//		Account account = (Account) request.getSession().getAttribute("cur_user");
//
//		orderList = buyService.getVerifyPurchaseList();
//		if (orderList.isEmpty()) {
//			System.out.println("no orderList ");
//		}
//		model.addAttribute("order_list", orderList);
		
		return "buy/verifyPurchaseList";
	}
	
	/**
	 * 采购验证
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/verifyPurchaseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		
		
//		String s_orderId_request = (String) request.getParameter("orderId");
		
		String taskId=request.getParameter("taskId");

		String comment = request.getParameter("suggestion");
		boolean buyVal = Boolean.parseBoolean(request.getParameter("buyVal"));
		
		buyService.verifyPurchaseSubmit(Long.parseLong(taskId), buyVal, comment);
		
//		System.out.println("buy verify ================");
//		
//		Account account = (Account) request.getSession().getAttribute("cur_user");
//		boolean buyVal = Boolean.parseBoolean(request.getParameter("buyVal"));
//		String s_orderId_request = (String) request.getParameter("orderId");
//		int orderId_request = Integer.parseInt(s_orderId_request);
//		String s_taskId = request.getParameter("taskId");
//		long taskId = Long.parseLong(s_taskId);
//		String s_processId = request.getParameter("pinId");
//		long processId = Long.parseLong(s_processId);
//		String comment = request.getParameter("suggestion");
//		
//		buyService.verifyPurchaseSubmit(account, orderId_request, taskId, processId, buyVal, comment);
//		
		return "redirect:/buy/verifyPurchaseList.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	

	@RequestMapping(value = "buy/verifyPurchaseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getVerifyPurchaseDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		
		
		
		
//		System.out.println("buy verify ================ show detail");
//		Account account = (Account) request.getSession().getAttribute("cur_user");
////		String actorId = account.getUserRole();
//		String s_orderId_request = (String) request.getParameter("id");
//		int orderId_request = Integer.parseInt(s_orderId_request);
//		String s_taskId = request.getParameter("task_id");
//		long taskId = Long.parseLong(s_taskId);
//		String s_processId = request.getParameter("process_id");
//		long processId = Long.parseLong(s_processId);
//		
//		OrderInfo orderModel = buyService.getVerifyPurchaseDetail(orderId_request, taskId);	
//		model.addAttribute("orderModel", orderModel);	
		return "buy/verifyPurchaseDetail";
	}

	
	
	
	
	
	
	

	/**
	 * 成本核算跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/computePurchaseCostList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<OrderInfo>list=buyService.getComputePurchaseCostList();
		model.addAttribute("list", list);		
		
		return "buy/computePurchaseCostList";
	}
	
	
	
	
	
	
	
	
	/**
	 * 显示成本核算详细信息
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "buy/computePurchaseCostDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String orderId=request.getParameter("orderId");
		OrderInfo orderInfo=buyService.getComputePurchaseCostInfo(Integer.parseInt(orderId));
		
		model.addAttribute("orderInfo", orderInfo);
	model.addAttribute("fabric_list", orderInfo.getFabrics());
	model.addAttribute("accessory_list", orderInfo.getAccessorys());
		
		return "buy/computePurchaseCostDetail";
	}
	
	
	
	
	/**
	 * 成本核算
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	
	@RequestMapping(value = "buy/computePurchaseCostSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostSubmit(HttpServletRequest request,
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
		
		
		
		
		return "redirect:/buy/computePurchaseCostList.do";
		
	}
	
	
	
	
	
	
}
