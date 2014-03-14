package nju.software.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nju.software.dataobject.Money;
import nju.software.model.SampleMoneyConfirmTaskSummary;
import nju.software.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FinanceController {
	@Autowired
	private FinanceService financeService;

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
}
