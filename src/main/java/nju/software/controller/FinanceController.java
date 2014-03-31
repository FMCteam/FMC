package nju.software.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Money;
import nju.software.model.OrderModel;
import nju.software.model.SampleMoneyConfirmTaskSummary;
import nju.software.service.FinanceService;
import nju.software.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FinanceController {
	@Autowired
	private FinanceService financeService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "finance/sampleMoneyConfirmList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleMoneyConfirmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		List<SampleMoneyConfirmTaskSummary> taskList = financeService
				.getSampleMoneyConfirmTaskSummaryList();
		model.addAttribute("taskList", taskList);

		return "finance/sampleMoneyConfirmList";
	}

	@RequestMapping(value = "finance/sampleMoneyConfirm.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleMoneyConfirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String taskId = request.getParameter("taskId");
		String orderId = request.getParameter("orderId");

		// model.addAttribute(attributeName, attributeValue)

		return "finance/sampleMoneyConfirm";
	}

	@RequestMapping(value = "finance/sampleMoneyConfirmSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sampleMoneyConfirmSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		boolean confirm = request.getParameter("confirm").equals("1");
		long taskId = Integer.parseInt(request.getParameter("taskId"));
		if (confirm) {
			financeService.sampleMoneyConfirm(taskId, getMoney(request));
		} else {
			financeService.sampleMoneyNoConfirm(taskId);
		}
		return "forword:/finance/sampleMoneyConfirmList.do";
	}

	private Money getMoney(HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		String moneyType = "样衣制作金";
		String moneyAmount = request.getParameter("money_amount");
		String moneyState = "确认收款";
		String moneyName = request.getParameter("money_name");
		String moneyBank = request.getParameter("money_bank");
		String moneyNumber = request.getParameter("money_number");
		String moneyRemark = request.getParameter("money_remark");
		Money money = new Money(Integer.parseInt(orderId), moneyType,
				Double.parseDouble(moneyAmount), moneyState, moneyName,
				moneyBank, moneyNumber, moneyRemark);
		return money;
	}
	
	/*
	 * =========================确认样衣制作金=================================
	 */
	
	/**
	 * 确认样衣制作金跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/confirmSample.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String confirmSample(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("sample confirm ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "CAIWUZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "comfirm_sample";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		return "finance/confirm_sample";
	}
	
	
	/**
	 * 确认样衣制作金
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/doConfirmSample.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doConfirmSample(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("sample confirm ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String s_moneyAmount = request.getParameter("money_amount");
		double moneyAmount = Double.parseDouble(s_moneyAmount);
		String moneyState = request.getParameter("money_state");
		String moneyType = request.getParameter("money_type");
		String moneyBank = request.getParameter("money_bank");
		String moneyName = request.getParameter("money_name");
		String moneyNumber = request.getParameter("money_number");
		String moneyRemark = request.getParameter("money_remark");
		boolean receivedsamplejin = true;
		
		if (!(moneyAmount < 0) && (moneyState != null) && (moneyType != null)) {
			Money money = new Money();
			money.setOrderId(orderId_request);
			money.setMoneyAmount(moneyAmount);
			money.setMoneyState(moneyState);
			money.setMoneyType(moneyType);
			money.setMoneyBank(moneyBank);
			money.setMoneyName(moneyName);
			money.setMoneyNumber(moneyNumber);
			money.setMoneyRemark(moneyRemark);
			financeService.confirmSample(account, orderId_request, taskId, processId, receivedsamplejin, money);
		}
		
		
		return "redirect:/finance/confirmSample.do";
	}
	
	/**
	 * 确认样衣制作金详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/confirmSampleDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("sample corfirm ================ show detail");
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
		model.addAttribute("orderModel", orderModel);
		
		return "finance/confirm_sample_detail";
	}
	
	/**
	 * 未收到样衣制作金，取消订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/cancelSample.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String cancelSample(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("cancel sample ===============");
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		boolean receivedsamplejin = false;
		financeService.confirmSample(account, orderId_request, taskId, processId, receivedsamplejin, null);
		
		return "redirect:/finance/confirmSample.do";
	}
	
	
	/*
	 * =========================确认30%定金=================================
	 */
	
	/**
	 * 确认30%定金跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/confirmDeposit.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String confirmDeposit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("Deposit confirm ================ show task");
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();
		String actorId = "CAIWUZHUGUAN";
		System.out.println("actorId: " + actorId);
		String taskName = "thirtypercent";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		if (orderList.isEmpty()) {
			System.out.println("no orderList ");
		}
		model.addAttribute("order_list", orderList);
		
		return "finance/confirm_deposit";
	}
	
	
	/**
	 * 确认30%定金
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/doConfirmDeposit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doConfirmDeposit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("Deposit confirm ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("pinId");
		long processId = Long.parseLong(s_processId);
		String s_moneyAmount = request.getParameter("money_amount");
		double moneyAmount = Double.parseDouble(s_moneyAmount);
		String moneyState = request.getParameter("money_state");
		String moneyType = request.getParameter("money_type");
		String moneyBank = request.getParameter("money_bank");
		String moneyName = request.getParameter("money_name");
		String moneyNumber = request.getParameter("money_number");
		String moneyRemark = request.getParameter("money_remark");
		boolean epositok = true;
		
		if (!(moneyAmount < 0) && (moneyState != null) && (moneyType != null)) {
			Money money = new Money();
			money.setOrderId(orderId_request);
			money.setMoneyAmount(moneyAmount);
			money.setMoneyState(moneyState);
			money.setMoneyType(moneyType);
			money.setMoneyBank(moneyBank);
			money.setMoneyName(moneyName);
			money.setMoneyNumber(moneyNumber);
			money.setMoneyRemark(moneyRemark);
			financeService.confirmSample(account, orderId_request, taskId, processId, epositok, money);
		}
		
		
		return "redirect:/finance/confirmDeposit.do";
	}
	
	/**
	 * 确认30%定金详情
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/confirmDepositDetail.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("Deposit corfirm ================ show detail");
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
		model.addAttribute("orderModel", orderModel);
		
		return "finance/confirm_deposit_detail";
	}
	
	/**
	 * 未收到30%定金，取消订单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "finance/cancelDeposit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String cancelDeposit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("cancel Deposit ===============");
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("task_id");
		long taskId = Long.parseLong(s_taskId);
		String s_processId = request.getParameter("process_id");
		long processId = Long.parseLong(s_processId);
		boolean epositok = false;
		financeService.confirmSample(account, orderId_request, taskId, processId, epositok, null);
		
		return "redirect:/finance/confirmDeposit.do";
	}
	
}
