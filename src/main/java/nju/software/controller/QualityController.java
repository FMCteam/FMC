package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.CheckRecord;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Produce;
import nju.software.dataobject.SearchInfo;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.service.QualityService;
import nju.software.util.DateUtil;
import nju.software.util.JbpmAPIUtil;

import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional 
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
		model.addAttribute("taskName", "质量检查");
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
		model.addAttribute("taskName", "质量检查");
		model.addAttribute("url", "/quality/checkQualityDetail.do");
		model.addAttribute("searchurl", "/quality/checkQualityListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
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
	

	/** 
	* @Title: modifyProduct 
	* @Description: TODO:质量检查（提交）
	* @param @param request
	* @param @param response
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "quality/checkQualitySubmit.do", method = RequestMethod.POST)
	@Transactional(rollbackFor=Exception.class)  
	public String modifyProduct(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)throws Exception {
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		
		String isFinal = request.getParameter("isFinal");//是否是最终质检
		String repairNumberStr = request.getParameter("repair_number");
		Integer repairNumber = 0;
		if(!StringUtils.isEmpty(repairNumberStr)){
			repairNumber = Integer.parseInt(request.getParameter("repair_number"));//本次质检回修数量
		}
		
		System.out.println(request.getParameter("repair_time").replace("T", " "));
		Date repairTime = this.getTime(request.getParameter("repair_time").replace("T", " "));//本次质检回修日期
		String repairSide = request.getParameter("repair_side");//本次质检回修加工方
		
		String invalidNumberStr = request.getParameter("invalid_number");
		Integer invalidNumber = 0;
		if(!StringUtils.isEmpty(invalidNumberStr)){
			invalidNumber = Integer.parseInt(request.getParameter("invalid_number"));//本次质检报废数量
		}
		
		// 本次质检记录
		CheckRecord checkRecord = new CheckRecord();
		checkRecord.setOrderId(orderId);
		checkRecord.setRepairAmount(repairNumber);
		checkRecord.setRepairTime(repairTime);
		checkRecord.setRepairSide(repairSide);
		checkRecord.setInvalidAmount(invalidNumber);
		
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
		
//		List<Produce> badList = null;
//		String badColor = request.getParameter("bad_color");
//		String badXS = request.getParameter("bad_xs");
//		String badS = request.getParameter("bad_s");
//		String badM = request.getParameter("bad_m");
//		String badL = request.getParameter("bad_l");
//		String badXL = request.getParameter("bad_xl");
//		String badXXL = request.getParameter("bad_xxl");
//		badList = produceService.getProduceList(orderId, badColor, 
//				badXS, badS, badM, badL, badXL, badXXL, Produce.TYPE_UNQUALIFIED);
		
		String msg = qualityService.checkQualitySubmit(orderId, taskId,
				isFinal, checkRecord, goodList);
		//qualityService.test(orderId,repairSide);
		if (msg.equals("")) {
			// 如果成功保存本次质检，返回质检列表页面
			return "redirect:/quality/checkQualityList.do";
		} else {
			// 否则还是保持在本页面
			Map<String,Object> oi = qualityService.getCheckQualityDetail(orderId);
			model.addAttribute("orderInfo", oi);
			model.addAttribute("notify", msg);
			return "/quality/checkQualityDetail";
		}
		
		// marketService.modifyProduct(account.getUserId(),id,taskId,processId,null);
	}
	
	public Date getTime(String time) {
		if(StringUtils.isEmpty(time)) 
			return null;
		Date outDate = DateUtil.parse(time, DateUtil.noSecondFormat);
		return outDate;
	}
	
//	// 质量检查
//	@RequestMapping(value = "quality/checkQualitySubmit.do", method = RequestMethod.POST)
//	@Transactional(rollbackFor = Exception.class)
//	public String modifyProduct(HttpServletRequest request,
//			HttpServletResponse response, ModelMap model) {
//		int orderId = Integer.parseInt(request.getParameter("orderId"));
//		String s_taskId = request.getParameter("taskId");
//		long taskId = Long.parseLong(s_taskId);
//
//		List<Produce> goodList = null;
//		String goodColor = request.getParameter("good_color");
//		String goodXS = request.getParameter("good_xs");
//		String goodS = request.getParameter("good_s");
//		String goodM = request.getParameter("good_m");
//		String goodL = request.getParameter("good_l");
//		String goodXL = request.getParameter("good_xl");
//		String goodXXL = request.getParameter("good_xxl");
//		goodList = produceService.getProduceList(orderId, goodColor, goodXS,
//				goodS, goodM, goodL, goodXL, goodXXL, Produce.TYPE_QUALIFIED);
//
//		List<Produce> badList = null;
//		String badColor = request.getParameter("bad_color");
//		String badXS = request.getParameter("bad_xs");
//		String badS = request.getParameter("bad_s");
//		String badM = request.getParameter("bad_m");
//		String badL = request.getParameter("bad_l");
//		String badXL = request.getParameter("bad_xl");
//		String badXXL = request.getParameter("bad_xxl");
//		badList = produceService.getProduceList(orderId, badColor, badXS, badS,
//				badM, badL, badXL, badXXL, Produce.TYPE_UNQUALIFIED);
//
//		qualityService.checkQualitySubmit(orderId, taskId, true, goodList,
//				badList);
//		// marketService.modifyProduct(account.getUserId(),id,taskId,processId,null);
//
//		return "redirect:/quality/checkQualityList.do";
//	}
	
}
