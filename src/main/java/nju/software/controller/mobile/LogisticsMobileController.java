package nju.software.controller.mobile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Employee;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.SearchInfo;
import nju.software.service.EmployeeService;
import nju.software.service.LogisticsService;
import nju.software.util.JSONUtil;
import nju.software.util.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogisticsMobileController {
	@Autowired
	private JSONUtil jsonUtil;
	// =============================收取样衣=====================================
	@RequestMapping(value = "/logistics/mobile_receiveSampleList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void receiveSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = logisticsService
				.getReceiveSampleList();
		model.addAttribute("list", list);
 
		jsonUtil.sendJson(response, model);
	}
 
	
	@RequestMapping(value = "/logistics/mobile_receiveSampleDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void receiveSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getReceiveSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_receiveSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void receiveSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Short result = Short.parseShort(request.getParameter("result"));
		boolean isSucess = logisticsService.receiveSampleSubmit(taskId, orderId, result);
		model.addAttribute("isSucess", isSucess);
		jsonUtil.sendJson(response, model);
	}

	// ===========================样衣发货=================================
	@RequestMapping(value = "/logistics/mobile_sendSampleList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = logisticsService.getSendSampleList();
		list = ListUtil.reserveList(list);
//		if (list.size() == 0) {
//			jbpmTest.completeProduceSample("1");
//			list = logisticsService.getSendSampleList();
//		}
		model.put("list", list);
		model.addAttribute("taskName", "样衣发货");
		model.addAttribute("url", "/logistics/sendSampleDetail.do");
		model.addAttribute("searchurl", "/logistics/sendSampleListSearch.do");
		jsonUtil.sendJson(response, model);
	}
	@Autowired
	private EmployeeService employeeService;
	@RequestMapping(value = "/logistics/sendSampleListSearch.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendSampleListSearch(HttpServletRequest request,
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
		List<Map<String, Object>> list = logisticsService.getSearchSendSampleList(ordernumber,customername,stylename,startdate,enddate,employeeIds);
		list = ListUtil.reserveList(list);
//		if (list.size() == 0) {
//			jbpmTest.completeProduceSample("1");
//			list = logisticsService.getSendSampleList();
//		}
		model.put("list", list);
		model.addAttribute("taskName", "样衣发货");
		model.addAttribute("url", "/logistics/sendSampleDetail.do");
		model.addAttribute("searchurl", "/logistics/sendSampleListSearch.do");

		model.addAttribute("info", new SearchInfo(ordernumber, customername, stylename, employeename, startdate, enddate));//将查询条件传回页面  hcj
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile_sendSampleDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		Map<String, Object> orderInfo = logisticsService
				.getSendSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_sendSampleSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		Integer orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		String taskId = taskId_string;
		
		String name = "" ;//快递名称
		String number = "";//快递单号
		String price = "";//快递价格
		String time = "";//邮寄时间
		String isFinal = request.getParameter("isFinal");//是否是最终发货
		if(isFinal.equals("false")){
			name = request.getParameter("name");//快递名称
			number = request.getParameter("number");//快递单号
			price = request.getParameter("price");//快递价格
			time = request.getParameter("time");//邮寄时间
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("taskId", taskId);
		map.put("time", time);
		map.put("name", name);
		map.put("number", number);
		map.put("price", price);
		map.put("isFinal", isFinal);
		boolean success = logisticsService.sendSampleSubmit(map);
		
		if(success){
			model.addAttribute("isSucess", success);
		}else{
			Map<String, Object> orderInfo = logisticsService.getSendSampleDetail(orderId);
			model.addAttribute("orderInfo", orderInfo);
			model.addAttribute("notify", "没有样衣发货记录，不能完成最终发货");
			model.addAttribute("isSucess", success);
			jsonUtil.sendJson(response, model);
		}
	}

	// ===========================产品入库=================================
	@RequestMapping(value = "/logistics/mobile_warehouseList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void warehouseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> packageList = logisticsService
				.getPackageList();
		List<Map<String, Object>> packageHaoDuoYiList = logisticsService
				.getPackageHaoDuoYiList();
		List<Map<String, Object>> warehouseList = logisticsService
				.getWarehouseList();
		List<Map<String, Object>> warehouseHaoDuoYiList = logisticsService
				.getWarehouseHaoDuoYiList();
//		if (packageList.size() == 0) {
//			jbpmTest.completeCheckQuality("1");
//			packageList = logisticsService.getPackageList();
//			warehouseList = logisticsService.getWarehouseList();
//		}

		model.put("packageList", packageList);
		model.put("packageHaoDuoYiList", packageHaoDuoYiList);
		model.put("warehouseList", warehouseList);
		model.put("warehouseHaoDuoYiList", warehouseHaoDuoYiList);
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile_packageDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void packageDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getPackageDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile_addPackage.do")
	//@Transactional(rollbackFor = Exception.class)
	public void addPackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String size = request.getParameter("size");
		String color = request.getParameter("color");
		String number = request.getParameter("number");
		String sizes[] = size.split(",");
		String colors[] = color.split(",");
		String numbers[] = number.split(",");
		Package pack = new Package(orderId);
		pack.setPackageTime(new Timestamp(System.currentTimeMillis()));
		List<PackageDetail> details = new ArrayList<PackageDetail>();
		for (int i = 0; i < sizes.length; i++) {
			PackageDetail detail = new PackageDetail();
			detail.setClothesAmount(Integer.parseInt(numbers[i]));
			detail.setClothesStyleColor(colors[i]);
			detail.setClothesStyleName(sizes[i]);
			details.add(detail);
		}
		int packageId = logisticsService.addPackage(pack, details);
		model.addAttribute("packageId", packageId);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_removePackage.do")
	//@Transactional(rollbackFor = Exception.class)
	public void removePackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String packageId = request.getParameter("packageId");
		boolean isSuccess = logisticsService.removePackage(Integer.parseInt(packageId));
		model.addAttribute("isSucess", isSuccess);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_packageSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void packageSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		boolean isSucess = logisticsService.packageSubmit(orderId);
		model.addAttribute("isSucess", isSucess);
		jsonUtil.sendJson(response, model);
		
	}

	@RequestMapping(value = "/logistics/mobile_warehouseDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void warehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getPackageDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile_printWarehouseDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void printWarehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Integer packageId = Integer.parseInt(request.getParameter("packageId"));
		Map<String, Object> orderInfo = logisticsService
				.getPrintWarehouseDetail(orderId, packageId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile/mobile_warehouseList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileWarehouseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = logisticsService
				.getMobileWarehouseList();
		model.addAttribute("orderList", orderList);
		
		List<Map<String, Object>> orderHaoDuoYiList = logisticsService
				.getMobileWarehouseHaoDuoYiList();
		model.addAttribute("orderHaoDuoYiList", orderHaoDuoYiList);
		
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile/mobile_warehouseDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileWarehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService.getMobileWarehouseDetail(orderId);

		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}
	
	@RequestMapping(value = "/logistics/mobile/mobile_updatePackage.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileUpdatePackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer packageId = Integer.parseInt(request.getParameter("packageId"));
		String warehouse = request.getParameter("warehouseId");
		String shelf = request.getParameter("shelfId");
		String location = request.getParameter("location");
		boolean isSuccess = logisticsService.updatePackage(packageId, warehouse, shelf, location);
		model.addAttribute("isSucess", isSuccess);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile/mobile_warehouseSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileWarehouseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String taskId = request.getParameter("taskId");
		boolean isSucess = logisticsService.mobileWarehouseSubmit(taskId, orderId);
		model.addAttribute("isSucess", isSucess);
		jsonUtil.sendJson(response, model);
	}

	// ===========================大货产品发货=================================
	@RequestMapping(value = "/logistics/mobile_sendClothesList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> scanList = logisticsService.getScanClothesList();
		List<Map<String, Object>> sendList = logisticsService.getSendClothesList();
		model.addAttribute("scanList", scanList);
		model.addAttribute("sendList", sendList);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_scanClothesDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void scanClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = logisticsService
				.getSendClothesDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile_sendClothesDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = logisticsService
				.getSendClothesDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile/mobile_sendClothesList.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileSendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = logisticsService
				.getMobileSendClothesList();
		model.addAttribute("orderList", orderList);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile/mobile_sendClothesDetail.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileSendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getMobileSendClothesDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		jsonUtil.sendJson(response, model);
	}

	@RequestMapping(value = "/logistics/mobile/mobile_sendClothesSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void mobileSendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		boolean isSucess = logisticsService.mobileSendClothesSubmit(orderId);
		model.addAttribute("isSucess",isSucess);
		jsonUtil.sendJson(response, model);
	}
//==============================发货完成，整个流程结束=========================================================
	@RequestMapping(value = "/logistics/mobile_sendClothesSubmit.do")
	//@Transactional(rollbackFor = Exception.class)
	public void sendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String taskId = request.getParameter("taskId");
		String time = request.getParameter("time");
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		Float price = Float.parseFloat(request.getParameter("price"));
		String remark = request.getParameter("remark");
		String isFinal = request.getParameter("isFinal");//判断是否最终发货
		boolean isSucess = logisticsService.sendClothesSubmit(orderId, taskId, price, name, time,
				number, remark, isFinal);
		model.addAttribute("isSucess", isSucess);
		jsonUtil.sendJson(response, model);
	}

	@Autowired
	private LogisticsService logisticsService;
}
