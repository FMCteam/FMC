package nju.software.controller.mobile;

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
import nju.software.util.Constants;
import nju.software.util.DateUtil;
import nju.software.util.FileOperateUtil;
import nju.software.util.JSONUtil;
import nju.software.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author LeiMiaomiao
 * @date 2015/04/19
 */
@Controller
public class FinanceMobileController {
	@Autowired
	private JSONUtil jsonUtil;
	
	private final static String CONFIRM_FINALPAYMENT_URL = "D:/fmc/confirmFinalPaymentFile/";// 大货首定金收取钱款图片

	// ===========================样衣金确认=================================
	@RequestMapping(value = "/finance/mobile_confirmSampleMoneyList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmSampleMoneyList(HttpServletRequest request,
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
		
		jsonUtil.sendJson(response, model);
	}

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/finance/mobile_confirmSampleMoneyListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmSampleMoneyListSearch(HttpServletRequest request,
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
				stylename, employeename, startdate, enddate));// 将查询条件传回页面 
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmSampleMoneyDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmSampleMoneyDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService
				.getConfirmSampleMoneyDetail(actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmSampleMoneySubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmSampleMoneySubmit(HttpServletRequest request,
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
		boolean isSuccess = financeService.confirmSampleMoneySubmit(actorId, taskId, result, money,
				orderId);
		model.addAttribute(Constants.JSON_IS_SUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}

	// ===========================退还定金列表===================================
	@RequestMapping(value = "/finance/mobile_returnDepositList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void returnDepositList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getReturnDepositList(actorId);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "退还大货定金");
		model.addAttribute("url", "/finance/returnDepositDetail.do");
		model.addAttribute("searchurl", "/finance/returnDepositListSearch.do");
		
		jsonUtil.sendJson(response, model);
	}

	// ===========================退还定金列表搜索===================================
	@RequestMapping(value = "/finance/mobile_returnDepositListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void returnDepositListSearch(HttpServletRequest request,
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
		jsonUtil.sendJson(response, model);
	}

	// ===========================定金确认===================================
	@RequestMapping(value = "/finance/mobile_confirmDepositList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmDepositList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		List<Map<String, Object>> list = financeService
				.getConfirmDepositList(actorId);
		list = ListUtil.reserveList(list);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "确认大货定金");
		model.addAttribute("url", "/finance/confirmDepositDetail.do");
		model.addAttribute("searchurl", "/finance/confirmDepositListSearch.do");
		
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmDepositListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmDepositListSearch(HttpServletRequest request,
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
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_returnDepositDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void returnDepositDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService.getReturnDepositDetail(
				actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmDepositDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmDepositDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService.getConfirmDepositDetail(
				actorId, Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_returnDepositSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void returnDepositSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		String taskId = taskId_string;

		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		boolean isSuccess = financeService.returnDepositSubmit(actorId, taskId, orderId);
		model.addAttribute(Constants.JSON_IS_SUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmDepositSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmDepositSubmit(HttpServletRequest request,
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
		boolean isSuccess = financeService.confirmDepositSubmit(actorId, taskId, result, money,
				orderId);
		model.addAttribute(Constants.JSON_IS_SUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}

	// ===========================尾款确认===================================
	@RequestMapping(value = "finance/mobile_confirmFinalPaymentList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmFinalPaymentList(HttpServletRequest request,
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
		
		jsonUtil.sendJson(response, model);

	}

	@RequestMapping(value = "finance/mobile_confirmFinalPaymentListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmFinalPaymentListSearch(HttpServletRequest request,
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
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmFinalPaymentDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmFinalPaymentDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService
				.getConfirmFinalPaymentDetail(actorId,
						Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@Autowired
	private MarketService marketService;

	// 上传接收尾金截图
	@RequestMapping(value = "/finance/mobile_confirmFinalPaymentFileSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmFinalPaymentFileSubmit(HttpServletRequest request,
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

		boolean isSuccess = marketService.signConfirmFinalPaymentFileSubmit(
				Integer.parseInt(orderId), confirmFinalPaymentFileUrl,
				moneyremark);
		String actorId = FinanceServiceImpl.ACTOR_FINANCE_MANAGER;
		Map<String, Object> orderInfo = financeService
				.getConfirmFinalPaymentDetail(actorId,
						Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute(Constants.JSON_IS_SUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/finance/mobile_confirmFinalPaymentSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void confirmFinalPaymentSubmit(HttpServletRequest request,
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
		boolean isSuccess = financeService.confirmFinalPaymentSubmit(actorId, taskId, result,
				money, orderId);
		model.addAttribute(Constants.JSON_IS_SUCCESS, isSuccess);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/mobile_image.do")
	//@Transactional(rollbackFor = Exception.class)
	public void image(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		List<Map<String, Object>> list = financeService
				.getProcessState(orderId);
		model.addAttribute("list", list);
		jsonUtil.sendJson(response, model);
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
