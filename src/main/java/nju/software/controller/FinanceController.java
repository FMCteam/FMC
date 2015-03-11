package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Employee;
import nju.software.dataobject.Money;
import nju.software.dataobject.SearchInfo;
import nju.software.service.EmployeeService;
import nju.software.service.FinanceService;
import nju.software.service.MarketService;
import nju.software.service.impl.FinanceServiceImpl;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author 莫其凡
 * @date 2014/04/11
 */
@Controller
public class FinanceController {
	private final static String CONFIRM_FINALPAYMENT_URL = "D:/fmc/confirmFinalPaymentFile/";// 大货首定金收取钱款图片

	// ===========================样衣金确认=================================
	@RequestMapping(value = "/finance/confirmSampleMoneyList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleMoneyList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmSampleMoneyList(actorId);
		list = ListUtil.reserveList(list);
		// if (list.size() == 0) {
		// jbpmTest.completeConfirmQuote(FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		// list = financeService.getConfirmSampleMoneyList(actorId);
		// }
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认样衣制作金");
		model.addAttribute("url", "/finance/confirmSampleMoneyDetail.do");
		model.addAttribute("searchurl",
				"/finance/confirmSampleMoneyListSearch.do");

		return "/finance/confirmSampleMoneyList";
	}

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/finance/confirmSampleMoneyListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmSampleMoneyListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		// 将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService
				.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for (int i = 0; i < employeeIds.length; i++) {
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getSearchConfirmSampleMoneyList(actorId, ordernumber,
						customername, stylename, startdate, enddate,
						employeeIds);
		list = ListUtil.reserveList(list);
		// if (list.size() == 0) {
		// jbpmTest.completeConfirmQuote(FinanceServiceImpl.ACTOR_FINANCE_MANAGER);
		// list = financeService.getConfirmSampleMoneyList(actorId);
		// }
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认样衣制作金");
		model.addAttribute("url", "/finance/confirmSampleMoneyDetail.do");
		model.addAttribute("searchurl",
				"/finance/confirmSampleMoneyListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername,
				stylename, employeename, startdate, enddate));// 将查询条件传回页面 hcj
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
		String taskId = taskId_string;
		boolean result = request.getParameter("result").equals("1");
		Money money = null;
		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		// financeService.confirmSampleMoneySubmit(actorId, taskId, result,
		// money);
		financeService.confirmSampleMoneySubmit(actorId, taskId, result, money,
				orderId);

		return "forward:/finance/confirmSampleMoneyList.do";
	}

	// ===========================退还定金列表===================================
	@RequestMapping(value = "/finance/returnDepositList.do")
	@Transactional(rollbackFor = Exception.class)
	public String returnDepositList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getReturnDepositList(actorId);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "退还大货定金");
		model.addAttribute("url", "/finance/returnDepositDetail.do");
		model.addAttribute("searchurl", "/finance/returnDepositListSearch.do");

		return "/finance/returnDepositList";
	}

	// ===========================退还定金列表搜索===================================
	@RequestMapping(value = "/finance/returnDepositListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String returnDepositListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		// 将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService
				.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for (int i = 0; i < employeeIds.length; i++) {
			employeeIds[i] = employees.get(i).getEmployeeId();
		}

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService.getReturnDepositList(
				actorId, ordernumber, customername, stylename, startdate,
				enddate, employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "退还大货定金");
		model.addAttribute("url", "/finance/returnDepositDetail.do");
		model.addAttribute("searchurl", "/finance/returnDepositListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername,
				stylename, employeename, startdate, enddate));// 将查询条件传回页面 hcj
		return "/finance/returnDepositList";
	}

	// ===========================定金确认===================================
	@RequestMapping(value = "/finance/confirmDepositList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmDepositList(actorId);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货定金");
		model.addAttribute("url", "/finance/confirmDepositDetail.do");
		model.addAttribute("searchurl", "/finance/confirmDepositListSearch.do");

		return "/finance/confirmDepositList";
	}

	@RequestMapping(value = "/finance/confirmDepositListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		// 将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService
				.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for (int i = 0; i < employeeIds.length; i++) {
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getSearchConfirmDepositList(actorId, ordernumber,
						customername, stylename, startdate, enddate,
						employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货定金");
		model.addAttribute("url", "/finance/confirmDepositDetail.do");
		model.addAttribute("searchurl", "/finance/confirmDepositListSearch.do");
		model.addAttribute("info", new SearchInfo(ordernumber, customername,
				stylename, employeename, startdate, enddate));// 将查询条件传回页面 hcj
		return "/finance/confirmDepositList";
	}

	@RequestMapping(value = "/finance/returnDepositDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String returnDepositDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService.getReturnDepositDetail(
				actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/finance/returnDepositDetail";
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

	@RequestMapping(value = "/finance/returnDepositSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String returnDepositSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		String taskId = taskId_string;

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		financeService.returnDepositSubmit(actorId, taskId, orderId);
		return "forward:/finance/returnDepositList.do";
	}

	@RequestMapping(value = "/finance/confirmDepositSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDepositSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		String taskId = taskId_string;
		boolean result = request.getParameter("result").equals("1");
		Money money = null;

		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		// financeService.confirmDepositSubmit(actorId, taskId, result, money);
		financeService.confirmDepositSubmit(actorId, taskId, result, money,
				orderId);

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
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货尾款");
		model.addAttribute("url", "/finance/confirmFinalPaymentDetail.do");
		model.addAttribute("searchurl",
				"/finance/confirmFinalPaymentListSearch.do");

		return "/finance/confirmFinalPaymentList";

	}

	@RequestMapping(value = "finance/confirmFinalPaymentListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmFinalPaymentListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		// 将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService
				.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for (int i = 0; i < employeeIds.length; i++) {
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getSearchConfirmFinalPaymentList(actorId, ordernumber,
						customername, stylename, startdate, enddate,
						employeeIds);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货尾款");
		model.addAttribute("url", "/finance/confirmFinalPaymentDetail.do");
		model.addAttribute("searchurl",
				"/finance/confirmFinalPaymentListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername,
				stylename, employeename, startdate, enddate));// 将查询条件传回页面 hcj
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

	@Autowired
	private MarketService marketService;

	// 上传接收尾金截图
	@RequestMapping(value = "/finance/confirmFinalPaymentFileSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmFinalPaymentFileSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String moneyremark = request.getParameter("moneyremark");// 金额备注
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile confirmFinalPaymentFile = multipartRequest
				.getFile("confirmFinalPaymentFile");
		String confirmFinalPaymentFileName = confirmFinalPaymentFile
				.getOriginalFilename();
		String confirmFinalPaymentFileUrl = CONFIRM_FINALPAYMENT_URL + orderId;
		String confirmFinalPaymentFileId = "confirmFinalPaymentFile";
		FileOperateUtil.Upload(request, confirmFinalPaymentFileUrl, null,
				confirmFinalPaymentFileId);
		confirmFinalPaymentFileUrl = confirmFinalPaymentFileUrl + "/"
				+ confirmFinalPaymentFileName;
		// 上传合同，上传首定金收据，一般是截图，

		marketService.signConfirmFinalPaymentFileSubmit(
				Integer.parseInt(orderId), confirmFinalPaymentFileUrl,
				moneyremark);
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
		String taskId = taskId_string;
		boolean result = request.getParameter("result").equals("1");
		Money money = null;

		if (result) {
			money = getMoney(request);
			money.setOrderId(orderId);
		}

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		financeService.confirmFinalPaymentSubmit(actorId, taskId, result,
				money, orderId);
		return "forward:/finance/confirmFinalPaymentList.do";
	}

	@RequestMapping(value = "/image.do")
	@Transactional(rollbackFor = Exception.class)
	public String image(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		List<Map<String, Object>> list = financeService
				.getProcessState(orderId);
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
		Date outDate = DateUtil.parse(time, DateUtil.haveSecondFormat);
		return new Timestamp(outDate.getTime());
	}

	@Autowired
	private FinanceService financeService;
}
