package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Employee;
import nju.software.service.DesignService;
import nju.software.service.EmployeeService;
import nju.software.service.OrderService;
import nju.software.service.impl.JbpmTest;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class DesignController {
	
	private final static String CAD_URL = "D:/fmc/cad/";

	// ===========================设计验证=================================
	@RequestMapping(value = "/design/verifyDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = designService
				.getVerifyDesignList();
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/verifyDesignListSearch.do");
		
		return "/design/verifyDesignList";
	}
	@Autowired
	private EmployeeService employeeService;
	// ===========================设计验证订单搜索=================================
	@RequestMapping(value = "/design/verifyDesignListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		ordernumber = ordernumber == null || ordernumber.length() == 0 ? null:  ordernumber;
		customername = customername == null || customername.length() == 0 ? null: customername;
		stylename = stylename == null || stylename.length() == 0 ? null: stylename;
		startdate = startdate == null || startdate.length() == 0 ? null: startdate;
		enddate = enddate == null || enddate.length() == 0 ? null: enddate;

		employeename = employeename == null || employeename.length() == 0 ? null: employeename;

		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
		List<Map<String, Object>> orderList = designService
				.getSearchVerifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "设计验证订单搜索");
		model.addAttribute("url", "/design/verifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/verifyDesignListSearch.do");
		
		return "/design/verifyDesignList";
	}
	
	
	@RequestMapping(value = "/design/verifyDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = designService
				.getVerifyDesignDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/design/verifyDesignDetail";
	}

	@RequestMapping(value = "/design/verifyDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		long taskId = Long.parseLong(request.getParameter("taskId"));
		boolean result = Boolean
				.parseBoolean(request.getParameter("designVal"));
		String comment = request.getParameter("suggestion");
		designService.verifyDesignSubmit(taskId, result, comment);
		return "forward:/design/verifyDesignList.do";
	}

	// ===========================上传版型=================================
	@RequestMapping(value = "/design/getUploadDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getUploadDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/getUploadDesignListSearch.do");

		return "/design/getUploadDesignList";
	}

	// ===========================上传版型搜索=================================
	@RequestMapping(value = "/design/getUploadDesignListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		ordernumber = ordernumber == null || ordernumber.length() == 0 ? null:  ordernumber;
		customername = customername == null || customername.length() == 0 ? null: customername;
		stylename = stylename == null || stylename.length() == 0 ? null: stylename;
		startdate = startdate == null || startdate.length() == 0 ? null: startdate;
		enddate = enddate == null || enddate.length() == 0 ? null: enddate;

		employeename = employeename == null || employeename.length() == 0 ? null: employeename;

		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
//		List<Map<String, Object>> orderList = designService
//				.getSearchVerifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		List<Map<String, Object>> list = designService.getSearchUploadDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "录入版型数据");
		model.addAttribute("url", "/design/getUploadDesignDetail.do");
		model.addAttribute("searchurl", "/design/getUploadDesignListSearch.do");

		return "/design/getUploadDesignList";
	}
	
	@RequestMapping(value = "/design/getUploadDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getUploadDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getUploadDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getUploadDesignDetail";
	}

	@RequestMapping(value = "/design/uploadDesignSubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String uploadDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		String url = CAD_URL + orderId;
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, url, null, fileid);
//		FileOperateUtil.Upload(file, url);
		url = url + "/" + filename;
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getUploadDesignList.do";
	}

	// ===========================修改版型=================================
	@RequestMapping(value = "/design/getModifyDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getModifyDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/getModifyDesignListSearch.do");

		return "/design/getModifyDesignList";
	}

	// ===========================修改版型=================================
	@RequestMapping(value = "/design/getModifyDesignListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		ordernumber = ordernumber == null || ordernumber.length() == 0 ? null:  ordernumber;
		customername = customername == null || customername.length() == 0 ? null: customername;
		stylename = stylename == null || stylename.length() == 0 ? null: stylename;
		startdate = startdate == null || startdate.length() == 0 ? null: startdate;
		enddate = enddate == null || enddate.length() == 0 ? null: enddate;
		employeename = employeename == null || employeename.length() == 0 ? null: employeename;

		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
//		List<Map<String, Object>> orderList = designService
//				.getSearchVerifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);

		List<Map<String, Object>> list = designService.getSearchModifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计部确认");
		model.addAttribute("url", "/design/getModifyDesignDetail.do");
		model.addAttribute("searchurl", "/design/getModifyDesignListSearch.do");

		return "/design/getModifyDesignList";
	}
	
	@RequestMapping(value = "/design/getModifyDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getModifyDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getModifyDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getModifyDesignDetail";
	}

	@RequestMapping(value = "/design/modifyDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String modifyDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();
		String url = CAD_URL + orderId;
		String fileid = "CADFile";
		FileOperateUtil.Upload(request, url, null, fileid);
//		FileOperateUtil.Upload(file, url);
		url = url + "/" + filename;
		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.modifyDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getModifyDesignList.do";
	}

	
	// ===========================确认版型=================================
	@RequestMapping(value = "/design/getConfirmDesignList.do")
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = designService.getConfirmDesignList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmDesignListSearch.do");

		return "/design/getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/getConfirmDesignListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ordernumber = request.getParameter("ordernumber");
		String customername = request.getParameter("customername");
		String stylename = request.getParameter("stylename");
		String employeename = request.getParameter("employeename");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		ordernumber = ordernumber == null || ordernumber.length() == 0 ? null:  ordernumber;
		customername = customername == null || customername.length() == 0 ? null: customername;
		stylename = stylename == null || stylename.length() == 0 ? null: stylename;
		startdate = startdate == null || startdate.length() == 0 ? null: startdate;
		enddate = enddate == null || enddate.length() == 0 ? null: enddate;
		employeename = employeename == null || employeename.length() == 0 ? null: employeename;

		List<Employee> employees = employeeService.getEmployeeByName(employeename);
		Integer[] employeeIds = new Integer[employees.size()];
		for(int i=0;i<employeeIds.length;i++){
			employeeIds[i] = employees.get(i).getEmployeeId();
		}
//		List<Map<String, Object>> orderList = designService
//				.getSearchVerifyDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);

		
		List<Map<String, Object>> list = designService.getSearchConfirmDesignList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		model.addAttribute("list", list);
		model.addAttribute("taskName", "设计生产确认");
		model.addAttribute("url", "/design/getConfirmDesignDetail.do");
		model.addAttribute("searchurl", "/design/getConfirmDesignListSearch.do");

		return "/design/getConfirmDesignList";
	}
	
	@RequestMapping(value = "/design/getConfirmDesignDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String getConfirmDesignDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = designService
				.getConfirmDesignDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/design/getConfirmDesignDetail";
	}

	@RequestMapping(value = "/design/confirmDesignSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmDesignSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("CADFile");
		String filename = file.getOriginalFilename();

 String url = CAD_URL + orderId;

 String fileid = "CADFile";
FileOperateUtil.Upload(request, url, null, fileid);
//		FileOperateUtil.Upload(file, url);
		url = url + "/" + filename;

		Timestamp uploadTime = new Timestamp(new Date().getTime());
		designService.uploadDesignSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), url, uploadTime);
		return "forward:/design/getConfirmDesignList.do";
	}

	
	@RequestMapping(value = "/downloadCadSubmit.do", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public void downloadCadSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String url = request.getParameter("cadUrl");
		System.out.println(url+"------------------------");
		FileOperateUtil.Download(response, url);
	}



	@Autowired
	private DesignService designService;
	@Autowired
	private JbpmTest jbpmTest;
}
