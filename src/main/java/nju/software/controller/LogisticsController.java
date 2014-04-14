package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.model.OrderInfo;
import nju.software.service.CustomerService;
import nju.software.service.EmployeeService;
import nju.software.service.LogisticsService;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.service.impl.JbpmTest;
import nju.software.service.impl.LogisticsServiceImpl;
import nju.software.util.DateUtil;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 莫其凡
 * @date 2014/04/11
 */
@Controller
public class LogisticsController {

	
	// =============================收取样衣=====================================
	@RequestMapping(value = "/logistics/receiveSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list = logisticsService.getReceiveSampleList();
		model.addAttribute("list", list);
		return "/logistics/receiveSampleList";
	}

	
	@RequestMapping(value = "/logistics/receiveSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		OrderInfo orderInfo = logisticsService.getReceiveSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/receiveSampleDetail";
	}

	
	@RequestMapping(value = "/logistics/receiveSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String result = request.getParameter("result");
		String taskId = request.getParameter("taskId");
		logisticsService.receiveSampleSubmit(Long.parseLong(taskId), result);
		return "forward:/logistics/receiveSampleList.do";
	}

	
	// ===========================样衣发货=================================
	@RequestMapping(value = "/logistics/sendSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list = logisticsService.getSendSampleList();
		if(list.size()==0){
			jbpmTest.completeProduceSample("1");
			list = logisticsService.getSendSampleList();
		}
		model.put("list", list);
		return "/logistics/sendSampleList";
	}

	
	@RequestMapping(value = "/logistics/sendSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		OrderInfo orderInfo = logisticsService.getSendSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/sendSampleDetail";
	}

	
	@RequestMapping(value = "/logistics/sendSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		Integer orderId=Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		long taskId=Long.parseLong(taskId_string);
		String time=request.getParameter("time");
		String name=request.getParameter("name");
		String number=request.getParameter("number");
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("taskId", taskId);
		map.put("time", time);
		map.put("name", name);
		map.put("number", number);
		logisticsService.sendSampleSubmit(map);
		return "forward:/logistics/sendSampleList.do";
	}

	
	// ===========================产品入库=================================
	@RequestMapping(value = "/logistics/warehouseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> list = logisticsService.getWarehouseList();
		if(list.size()==0){
			jbpmTest.completeCheckQuality("1");
			list = logisticsService.getWarehouseList();
		}
		model.put("list", list);
		return "/logistics/warehouseList";
	}

	
	@RequestMapping(value = "/logistics/warehouseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		OrderInfo orderInfo = logisticsService.getWarehouseDetail(Integer
				.parseInt(orderId));
		return "/logistics/warehouseDetail";
	}

	
	@RequestMapping(value = "/logistics/warehouseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String actor = "WULIUZHUGUAN";
		String orderId = request.getParameter("order_id");
		String taskId = request.getParameter("task_id");
		String clothes_amount = request.getParameter("clothes_amount");
		String clothes_style_color = request
				.getParameter("clothes_style_color");
		String clothes_style_name = request.getParameter("clothes_style_name");
		String inTime = request.getParameter("package_date");
		Timestamp entryTime = null;
		if (inTime != null && inTime != "") {
			Date inDate = DateUtil.parse(inTime, DateUtil.newFormat);
			entryTime = new Timestamp(inDate.getTime());
		}
		try {
			String[] array_amount = clothes_amount.split(",");
			String[] array_color = clothes_style_color.split(",");
			String[] array_name = clothes_style_name.split(",");
			produceService.savePackageDetail(Integer.parseInt(orderId),
					array_amount, array_color, array_name, entryTime);

			// 保存package信息
			jbpmAPIUtil.completeTask(Integer.parseInt(taskId), null, actor);
			// 推进流程

			return "forward:/logistics/warehouseList.do";
		} catch (Exception e) {

		}
		return "forward:/logistics/warehouseList.do";
	}


	// ===========================大货产品发货=================================
	@RequestMapping(value = "/logistics/sendClothesList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String user_agent = request.getHeader("user-agent");
		boolean is_wm = user_agent.contains("Windows Phone 6.5");
		jbpmTest.completeTaskLogistics("1");
		if (is_wm) {
			List<OrderInfo> list = logisticsService
					.getSendClothesUncheckedList();
			model.addAttribute("list", list);
			return "logistic/getSendClothesList2";
		} else {
			List<OrderInfo> list = logisticsService.getSendClothesList();
			model.addAttribute("list", list);
			return "/logistic/sendClothesList";
		}

	}

	

	
	@RequestMapping(value = "/logistics/sendClothesDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId = request.getParameter("orderId");
		OrderInfo orderInfo = logisticsService.getSendClothesDetail(Integer
				.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);

		return "/logistics/sendClothesDetail";
	}

	
	@RequestMapping(value = "/logistics/sendClothesSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String logistics_cost = request.getParameter("logistics_cost");
		String orderId = (String) request.getParameter("orderId");
		String taskId = request.getParameter("taskId");
		logisticsService.sendClothesSubmit(Integer.parseInt(orderId),
				Long.parseLong(taskId), Float.parseFloat(logistics_cost));
		// logisticsService.shipments(account, orderId_request, taskId,
		// processId, design_cost);
		return "forward:/logistics/sendClothesList";
	}

	@Autowired
	private OrderService orderService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ProduceService produceService;
	@Autowired
	private JbpmTest jbpmTest;

	@RequestMapping(value = "/logistics/mobile.do")
	@Transactional(rollbackFor = Exception.class)
	public String showMobilePage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		 
		return "logistics/mobile";
	}
	
	@RequestMapping(value = "/logistics/mobileStore.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileStore(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		 
		return "logistics/mobileUpdateStore";
	}
	/**
	 * 扫描确认发货
	 * 
	 */
	@RequestMapping(value = "logistics/mobileScan.do")
	@Transactional(rollbackFor = Exception.class)
	public String checkSendClothes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		// String orderId=request.getParameter("orderId");
		// OrderInfo
		// orderInfo=logisticsService.getSendClothesDetail(Integer.parseInt(orderId));
		//
		// model.addAttribute("orderInfo", orderInfo);

		return "logistics/mobileCheckSendClothes";
	}



}
