package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Money;
import nju.software.model.OrderInfo;
import nju.software.service.FinanceService;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.service.impl.JbpmTest;
import nju.software.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 莫其凡
 * @date 2014/04/11
 */
@Controller
public class FinanceController {

	// ===========================样衣金确认=================================
	@RequestMapping(value = "/finance/confirmSampleMoneyList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleMoneyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmSampleMoneyList(actorId);
<<<<<<< HEAD
		/*if (list.size() == 0) {
			jbpmTest.completeConfirmQuote(FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
=======
		if (list.size() == 0) {
			jbpmTest.completeConfirmQuote("1");
>>>>>>> b64a5d2487575fd4b7b71c872d0460743d181f92
			list = financeService.getConfirmSampleMoneyList(actorId);
		}*/
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认样衣制作金");
		model.addAttribute("url", "/finance/confirmSampleMoneyDetail.do");
		return "/finance/confirmSampleMoneyList";
	}

	@RequestMapping(value = "/finance/confirmSampleMoneyDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleMoneyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService
				.getConfirmSampleMoneyDetail(actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/finance/confirmSampleMoneyDetail";
	}

	@RequestMapping(value = "/finance/confirmSampleMoneySubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleMoneySubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		long taskId = Long.parseLong(taskId_string);
		boolean result = request.getParameter("result").equals("1");
		Money money = null;
		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		financeService.confirmSampleMoneySubmit(actorId, taskId, result, money);
		return "forward:/finance/confirmSampleMoneyList.do";
	}

	// ===========================定金确认===================================
	@RequestMapping(value = "/finance/confirmDepositList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmDepositList(actorId);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货定金");
		model.addAttribute("url", "/finance/confirmDepositDetail.do");
		return "/finance/confirmDepositList";
	}

	@RequestMapping(value = "/finance/confirmDepositDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService.getConfirmDepositDetail(
				actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/finance/confirmDepositDetail";
	}

	@RequestMapping(value = "/finance/confirmDepositSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		long taskId = Long.parseLong(taskId_string);
		boolean result = request.getParameter("result").equals("1");
		Money money = null;

		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		financeService.confirmDepositSubmit(actorId, taskId, result, money);
		return "forward:/finance/confirmDepositList.do";
	}

	// ===========================尾款确认===================================
	@RequestMapping(value = "finance/confirmFinalPaymentList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmFinalPaymentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmFinalPaymentList(actorId);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货尾款");
		model.addAttribute("url", "/finance/confirmFinalPaymentDetail.do");
		return "/finance/confirmFinalPaymentList";

	}

	@RequestMapping(value = "/finance/confirmFinalPaymentDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmFinalPaymentDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService
				.getConfirmFinalPaymentDetail(actorId,
						Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/finance/confirmFinalPaymentDetail";
	}

	@RequestMapping(value = "/finance/confirmFinalPaymentSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmFinalPaymentSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		long taskId = Long.parseLong(taskId_string);
		boolean result = request.getParameter("result").equals("1");
		Money money = null;

		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		financeService
				.confirmFinalPaymentSubmit(actorId, taskId, result, money);
		return "forward:/finance/confirmFinalPaymentList.do";
	}

	@RequestMapping(value = "/image.do")
	@Transactional(rollbackFor = Exception.class)
	public String image(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		long pid=Long.parseLong(request.getParameter("pid"));
		List<Map<String, Object>> list=financeService.getProcessState(pid);
		model.addAttribute("list", list);
		return "/image";
	}

	private Money getMoney(HttpServletRequest request) {
		String money_amount_string = request.getParameter("money_amount");
		double moneyAmount = Double.parseDouble(money_amount_string);
		String moneyState = request.getParameter("money_state");
		String moneyType = request.getParameter("money_type");
		String moneyBank = request.getParameter("money_bank");
		String moneyName = request.getParameter("money_name");
		String moneyNumber = request.getParameter("money_number");
		String moneyRemark = request.getParameter("money_remark");
		String time = request.getParameter("time");
		String account = request.getParameter("account");

		Money money = new Money();
		money.setMoneyAmount(moneyAmount);
		money.setMoneyState(moneyState);
		money.setMoneyType(moneyType);
		money.setMoneyBank(moneyBank);
		money.setMoneyName(moneyName);
		money.setMoneyNumber(moneyNumber);
		money.setMoneyRemark(moneyRemark);
		money.setReceiveAccount(account);
		money.setReceiveTime(getTime(time));
		return money;
	}

	public Timestamp getTime(String time) {
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
	}

	@Autowired
	private FinanceService financeService;
	@Autowired
	private JbpmTest jbpmTest;
}
