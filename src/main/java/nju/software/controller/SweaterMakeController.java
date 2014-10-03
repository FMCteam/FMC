package nju.software.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.dataobject.OperateRecord;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.SearchInfo;
import nju.software.service.BuyService;
import nju.software.service.EmployeeService;
import nju.software.service.MarketService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.service.SweaterMakeService;
import nju.software.util.DateUtil;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class SweaterMakeController {
	

	@Autowired
	private SweaterMakeService sweaterMakeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProduceService produceService;
	@Autowired
	private BuyService buyService;
	
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
		/*TaskSummary task = (TaskSummary) orderInfo.get("task");
		long processId = task.getProcessInstanceId();
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		boolean sweaterMaterial = (boolean) process.getVariable("sweaterMaterial");
		model.addAttribute("buySweaterMaterial",sweaterMaterial);*/
		//从session中取默认责任人
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		model.addAttribute("employee_name", account.getUserName());
		model.addAttribute("orderInfo", orderInfo);
		return "/sweater/sweaterSampleAndCraftDetail";
	}
	
	@RequestMapping(value = "sweater/sweaterSampleAndCraftSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterSampleAndCraftSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {	
		
 		String taskId =  request.getParameter("taskId");
		String orderId = request.getParameter("orderId");
		String isFinalConfirm = request.getParameter("isFinal");
		String taskName = request.getParameter("task_name");
		boolean isfinalconfirm = Boolean.parseBoolean(isFinalConfirm);
		if("确认样衣".equals(taskName)){
			isfinalconfirm =true;
		}
		OperateRecord operateRecord = new OperateRecord();		
		String operateTime = request.getParameter("task_finish_date");
		String operatePerson = request.getParameter("person_in_charge");
		String operateRemark = request.getParameter("sweaterremark");			
		operateRecord.setOrderId(Integer.parseInt(orderId));
		operateRecord.setTaskName(taskName);
		operateRecord.setOperatePerson(operatePerson);
		operateRecord.setOperateRemark(operateRemark);
		operateRecord.setOperateTime(getDateTime(operateTime));
		sweaterMakeService.sweaterSampleAndCraftSubmit(Long.parseLong(taskId), isfinalconfirm, orderId,operateRecord);
		if(isfinalconfirm){//保存记录和确认完成跳转不同的方法
			return "forward:/sweater/sweaterSampleAndCraftList.do";
		}else{
			return "forward:/sweater/sweaterSampleAndCraftDetail.do";
		}
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
		//从session中取默认责任人
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("cur_user");
		model.addAttribute("employee_name", account.getUserName());
		model.addAttribute("orderInfo", orderInfo);
		return "/sweater/sendSweaterDetail";
	}
	
	@RequestMapping(value = "sweater/sendSweaterSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSweaterSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId =  request.getParameter("taskId");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
	    List<Produce> produceList = new ArrayList<Produce>();
		if (result) {
			String produceColor = request.getParameter("produce_color");
			int produceXS = Integer.parseInt(request.getParameter("produce_xs"));
			int produceS = Integer.parseInt(request.getParameter("produce_s"));
			int produceM = Integer.parseInt(request.getParameter("produce_m"));
			int produceL = Integer.parseInt(request.getParameter("produce_l"));
			int produceXL = Integer.parseInt(request.getParameter("produce_xl"));
			int produceXXL = Integer.parseInt(request.getParameter("produce_xxl"));
			String processing_side = request.getParameter("processing_side");
			String Purchase_director = request.getParameter("Purchase_director");
			String sendTime = request.getParameter("sendTime");
			Order torder = orderService.findByOrderId(orderId+"");
			torder.setPayAccountInfo(processing_side);
			orderService.updateOrder(torder);
			Produce Produce = new Produce();
			int total = produceXS + produceS + produceM + produceL +
					produceXL + produceXXL;
			Produce.setProduceAmount(total);
			Produce.setType(Produce.TYPE_PRODUCED);
			Produce.setSendTime(sendTime);
			Produce.setColor(produceColor);
			Produce.setXs(produceXS);
			Produce.setS(produceS);
			Produce.setM(produceM);
			Produce.setL(produceL);
			Produce.setXl(produceXL);
			Produce.setXxl(produceXXL);
			Produce.setProcessing_side(processing_side);
			Produce.setPurchase_director(Purchase_director);
			Produce.setOid(orderId);
			produceList.add(Produce);
		}
		sweaterMakeService.sendSweaterSubmit(Long.parseLong(taskId),result,produceList, orderId);
		return "forward:/sweater/sendSweaterList.do";
	}
	
	public static Timestamp getDateTime(String time) {
		if(time.equals("")) return null;
		Date outDate = DateUtil.parse(time, DateUtil.haveSecondFormat);
		return new Timestamp(outDate.getTime());
	}
	
	/**
	 * 毛衣所有订单查询(无权限)
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sweater/sweaterOrderList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterOrderList(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) {
			List<Map<String, Object>> list = sweaterMakeService.getOrders();//查询所有订单
			model.addAttribute("list", list);
			model.addAttribute("taskName", "毛衣列表");
			model.addAttribute("url", "/order/orderDetail.do");
			model.addAttribute("searchurl", "/sweater/sweaterOrderListSearch.do");
		
			return "/sweater/sweaterSearchList";
		}
	
	@RequestMapping(value = "/sweater/sweaterOrderListSearch.do")
	@Transactional(rollbackFor = Exception.class)
	public String sweaterOrderListSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderState = request.getParameter("orderState");
				
		List<Map<String, Object>> list = null;
		if("0".equals(orderState)||orderState == null){
			return "forward:/sweater/sweaterOrderList.do";
		}else{
			list = sweaterMakeService.getSerachOrders(orderState);;
		}
		
		model.addAttribute("list", list);
		model.addAttribute("taskName", "毛衣列表");
		model.addAttribute("url", "/order/orderDetail.do");
		model.addAttribute("searchurl", "/sweater/sweaterOrderListSearch.do");
		
		return "/sweater/sweaterSearchList";
	}
}
