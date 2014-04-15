package nju.software.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.model.OrderInfo;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
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
import nju.software.dataobject.Order;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;

import org.apache.commons.lang.StringUtils;
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
		
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		String size=request.getParameter("size");
		String color=request.getParameter("color");
		String number=request.getParameter("number");
		
		if(size!=null&&size!=""){
			String sizes[]=size.split(",");
			String colors[]=color.split(",");
			String numbers[]=number.split(",");
			Package pack=new Package(orderId);
			List<PackageDetail>details=new ArrayList<PackageDetail>();
			for(int i=0;i<sizes.length;i++){
				PackageDetail detail=new PackageDetail();
				detail.setClothesAmount(Integer.parseInt(numbers[i]));
				detail.setClothesStyleColor(colors[i]);
				detail.setClothesStyleName(sizes[i]);
				details.add(detail);
			}
			logisticsService.addPackage(pack, details);
		}
		
		OrderInfo orderInfo = logisticsService.getWarehouseDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/warehouseDetail";
	}

	
	@RequestMapping(value = "/logistics/warehouseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Long taskId = Long.parseLong(request.getParameter("taskId"));
		logisticsService.warehouseSubmit(taskId, null);
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
			//System.out.println(list.size());
			return "/logistics/sendClothesList";
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
	private LogisticsService logisticsService;

	@Autowired
	private OrderService orderService;
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
	
	@RequestMapping(value = "/logistics/mobileStoreList.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileStore(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<OrderInfo> orderList = logisticsService.getSendClothesUnstoredList();
		model.addAttribute("orderList", orderList);
		return "logistics/mobileGetUnstoreList";
	}
	
	@RequestMapping(value = "/logistics/updateStore.do")
	@Transactional(rollbackFor = Exception.class)
	public String updateStoreInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		
		String packageId = (String) request.getParameter("packageId");
		if(packageId != null && !StringUtil.isEmpty(packageId)) {
			String warehouse = (String) request.getParameter("warehouseId");
			String shelf = (String) request.getParameter("shelfId");
			String location = (String) request.getParameter("location");
			logisticsService.updateSendClothesStoreInfo(Integer.parseInt(packageId), warehouse, shelf, location);
		}
		
		OrderInfo orderInfo = logisticsService.getStoreClothesDetail(Integer.parseInt(orderId));
		List<Package> packageList = logisticsService.getPackageListByOrderId(Integer.parseInt(orderId));
		
		ArrayList<String> parr = new ArrayList<String>();
		for(int i=0;i<packageList.size();i++) {
			parr.add(packageList.get(i).getPackageId().toString());
		}
		model.addAttribute("pidArray", StringUtils.join(parr.toArray(), ","));
		model.addAttribute("packageList", packageList);
		model.addAttribute("order", orderInfo.getOrder());
		model.addAttribute("task", orderInfo.getTask());
		
		return "logistics/mobileUpdateStore";
	}
	
	@RequestMapping(value = "/logistics/finishUpdateStore.do")
	@Transactional(rollbackFor = Exception.class)
	public String finishUpdateStore(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		String taskId = (String) request.getParameter("taskId");
		
		
		String actor = "WULIUZHUGUAN";
		try {
			logisticsService.setOrderStored(Integer.parseInt(orderId));
			// 保存package信息
			jbpmAPIUtil.completeTask(Integer.parseInt(taskId), null, actor);
			// 推进流程

			
		} catch (Exception e) {
			
		}

		return "redirect:/logistics/mobileStoreList.do";
	}
	/**
	 * 扫描确认发货
	 * 
	 */
	@RequestMapping(value = "logistics/mobileScanList.do")
	@Transactional(rollbackFor = Exception.class)
	public String checkSendClothes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<OrderInfo> orderList = logisticsService.getSendClothesUncheckedList();
		model.addAttribute("orderList", orderList);
		return "logistics/mobileGetUnsanList";

	}

	@RequestMapping(value = "/logistics/scanClothes.do")
	@Transactional(rollbackFor = Exception.class)
	public String scanClothes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		OrderInfo orderInfo = logisticsService.getStoreClothesDetail(Integer.parseInt(orderId));
		List<Package> packageList = logisticsService.getPackageListByOrderId(Integer.parseInt(orderId));
		
		model.addAttribute("packageList", packageList);
		model.addAttribute("order", orderInfo.getOrder());
		model.addAttribute("task", orderInfo.getTask());
		
		return "logistics/mobileCheckSendClothes";
	}

	@RequestMapping(value = "/logistics/finishScanClothes.do")
	@Transactional(rollbackFor = Exception.class)
	public String finishScanClothes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("orderId");
		String taskId = (String) request.getParameter("taskId");
		
		String actor = "WULIUZHUGUAN";
		try {
			logisticsService.setOrderScanChecked(Integer.parseInt(orderId));
			// 保存package信息
			jbpmAPIUtil.completeTask(Integer.parseInt(taskId), null, actor);
			// 推进流程

			
		} catch (Exception e) {
			
		}

		return "redirect:/logistics/mobileScanList.do";
	}
	
	@RequestMapping(value = "/logistics/printPackage.do")
	@Transactional(rollbackFor = Exception.class)
	public String printPackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = (String) request.getParameter("order_id");
		String packageId = (String) request.getParameter("package_id");
		
		Package packageInfo = logisticsService.getPackageByPackageId(Integer.parseInt(packageId));
		Order order = orderService.findByOrderId(orderId);
		List<PackageDetail> pdList = logisticsService.getPackageDetailList(Integer.parseInt(packageId));
		
		model.addAttribute("order", order);
		model.addAttribute("packageInfo", packageInfo);
		model.addAttribute("packageDetailList", pdList);
		
		return "logistics/printPackage";
	}
}
