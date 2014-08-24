package nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.service.QualityService;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QualityController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private QualityService qualityService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ProduceService produceService;
	
	//质检列表
	@RequestMapping(value = "quality/checkQualityList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String qualityCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> orderList = qualityService.getCheckQualityList();
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/quality/checkQualityDetail.do");
		model.addAttribute("searchurl", "/quality/checkQualityListSearch.do");

		return "quality/checkQualityList";
	}

	@RequestMapping(value = "quality/checkQualityListSearch.do" )
	@Transactional(rollbackFor = Exception.class)
	public String checkQualityListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		//将用户输入的employeeName转化为employeeId,因为order表中没有employeeName属性
		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String,Object>> orderList = qualityService.getSearchCheckQualityList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/quality/checkQualityDetail.do");
		model.addAttribute("searchurl", "/quality/checkQualityListSearch.do");

		return "quality/checkQualityList";
	}
	
	//质检详情
	@RequestMapping(value = "quality/checkQualityDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProductDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		int id = Integer.parseInt(orderId);
		Map<String,Object> oi = qualityService.getCheckQualityDetail(id);
		model.addAttribute("orderInfo", oi);
		return "quality/checkQualityDetail";
	}
	
	//质量检查
	@RequestMapping(value = "quality/checkQualitySubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProduct(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		
		List<Produce> goodList = null;
		String goodColor = request.getParameter("good_color");
		String goodXS = request.getParameter("good_xs");
		String goodS = request.getParameter("good_s");
		String goodM = request.getParameter("good_m");
		String goodL = request.getParameter("good_l");
		String goodXL = request.getParameter("good_xl");
		String goodXXL = request.getParameter("good_xxl");
		goodList = produceService.getProduceList(orderId, goodColor, 
				goodXS, goodS, goodM, goodL, goodXL, goodXXL, Produce.TYPE_QUALIFIED);
		
		List<Produce> badList = null;
		String badColor = request.getParameter("bad_color");
		String badXS = request.getParameter("bad_xs");
		String badS = request.getParameter("bad_s");
		String badM = request.getParameter("bad_m");
		String badL = request.getParameter("bad_l");
		String badXL = request.getParameter("bad_xl");
		String badXXL = request.getParameter("bad_xxl");
		badList = produceService.getProduceList(orderId, badColor, 
				badXS, badS, badM, badL, badXL, badXXL, Produce.TYPE_UNQUALIFIED);
		
		qualityService.checkQualitySubmit(orderId, taskId, true, goodList, badList);
		//marketService.modifyProduct(account.getUserId(),id,taskId,processId,null);

		return "redirect:/quality/checkQualityList.do";
	}
		
}
