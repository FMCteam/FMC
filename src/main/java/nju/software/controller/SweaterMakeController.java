package nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.service.EmployeeService;
import nju.software.service.SweaterMakeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@Controller
public class SweaterMakeController {
	

	@Autowired
	private SweaterMakeService sweaterMakeService;
	@Autowired
	private EmployeeService employeeService;
	
	//=====================毛衣样衣确认和工艺制作======================
	@RequestMapping(value = "/sweater/sweaterSampleAndCraftList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterSampleAndCraftList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list=sweaterMakeService.getSweaterSampleAndCraftList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "毛衣样衣确认和工艺制作");
		model.addAttribute("url", "/sweater/sweaterSampleAndCraftDetail.do");
		model.addAttribute("searchurl", "/sweater/sweaterSampleAndCraftListSearch.do");

		return "/sweater/sweaterSampleAndCraftList";
	}

	@RequestMapping(value = "/sweater/sweaterSampleAndCraftListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterSampleAndCraftListSearch(HttpServletRequest request,
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
		List<Map<String,Object>> list=sweaterMakeService.
				getSearchSweaterSampleAndCraftList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "毛衣样衣确认和工艺制作订单搜索");
		model.addAttribute("url", "/sweater/sweaterSampleAndCraftDetail.do");
		model.addAttribute("searchurl", "/sweater/sweaterSampleAndCraftListSearch.do");

		return "/sweater/sweaterSampleAndCraftList";
	}
	
	@RequestMapping(value = "/sweater/sweaterSampleAndCraftDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getSweaterSampleAndCraftDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		Map<String,Object> orderInfo=sweaterMakeService.getSweaterSampleAndCraftDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/sweater/sweaterSampleAndCraftDetail";
	}
	
	@RequestMapping(value = "sweater/sweaterSampleAndCraftSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterSampleAndCraftSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId =  request.getParameter("taskId");
		String orderId = request.getParameter("orderId");
		sweaterMakeService.sweaterSampleAndCraftSubmit(Long.parseLong(taskId), result, orderId);
		return "forward:/sweater/sweaterSampleAndCraftList.do";
	}
	
	//=====================毛衣外发======================
	@RequestMapping(value = "/sweater/sendSweaterList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSweaterList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list=sweaterMakeService.getSendSweaterList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "毛衣外发");
		model.addAttribute("url", "/sweater/sendSweaterDetail.do");
		model.addAttribute("searchurl", "/sweater/sendSweaterListSearch.do");

		return "/sweater/sendSweaterList";
	}

	@RequestMapping(value = "/sweater/sendSweaterListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSweaterListSearch(HttpServletRequest request,
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
		List<Map<String,Object>> list=sweaterMakeService.
				getSearchSendSweaterList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "毛衣外发订单搜索");
		model.addAttribute("url", "/sweater/sendSweaterDetail.do");
		model.addAttribute("searchurl", "/sweater/sendSweaterListSearch.do");
		return "/sweater/sendSweaterList";
	}
	
	@RequestMapping(value = "/sweater/sendSweaterDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getSendSweaterDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		Map<String,Object> orderInfo=sweaterMakeService.getSendSweaterDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/sweater/sendSweaterDetail";
	}
	
	@RequestMapping(value = "sweater/sendSweaterSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSweaterSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId =  request.getParameter("taskId");
		String orderId = request.getParameter("orderId");
		sweaterMakeService.sendSweaterSubmit(Long.parseLong(taskId), result, orderId);
		return "forward:/sweater/sendSweaterList.do";
	}
	
}
