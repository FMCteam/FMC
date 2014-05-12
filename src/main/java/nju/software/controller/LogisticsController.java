package nju.software.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.model.OrderInfo;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.service.LogisticsService;
import nju.software.service.OrderService;
import nju.software.service.impl.JbpmTest;
import nju.software.util.JbpmAPIUtil;
import nju.software.dataobject.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogisticsController {

	// =============================收取样衣=====================================
	@RequestMapping(value = "/logistics/receiveSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = logisticsService
				.getReceiveSampleList();
		model.addAttribute("list", list);
		return "/logistics/receiveSampleList";
	}

	@RequestMapping(value = "/logistics/receiveSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getReceiveSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/receiveSampleDetail";
	}

	@RequestMapping(value = "/logistics/receiveSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String receiveSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		long taskId = Long.parseLong(request.getParameter("taskId"));
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Short result = Short.parseShort(request.getParameter("result"));
		logisticsService.receiveSampleSubmit(taskId, orderId, result);
		return "forward:/logistics/receiveSampleList.do";
	}

	// ===========================样衣发货=================================
	@RequestMapping(value = "/logistics/sendSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = logisticsService.getSendSampleList();
		/*if (list.size() == 0) {
			jbpmTest.completeProduceSample("1");
			list = logisticsService.getSendSampleList();
		}*/
		model.put("list", list);
		model.addAttribute("taskName", "样衣发货");
		model.addAttribute("url", "/logistics/sendSampleDetail.do");
		return "/logistics/sendSampleList";
	}

	@RequestMapping(value = "/logistics/sendSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		int orderId = Integer.parseInt(orderId_string);
		Map<String, Object> orderInfo = logisticsService
				.getSendSampleDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/sendSampleDetail";
	}

	@RequestMapping(value = "/logistics/sendSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId_string = request.getParameter("orderId");
		Integer orderId = Integer.parseInt(orderId_string);
		String taskId_string = request.getParameter("taskId");
		long taskId = Long.parseLong(taskId_string);
		String time = request.getParameter("time");
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		Map<String, Object> map = new HashMap<String, Object>();
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
		List<Map<String, Object>> packageList = logisticsService
				.getPackageList();
		List<Map<String, Object>> warehouseList = logisticsService
				.getWarehouseList();
//		if (packageList.size() == 0) {
//			jbpmTest.completeCheckQuality("1");
//			packageList = logisticsService.getPackageList();
//			warehouseList = logisticsService.getWarehouseList();
//		}
		model.put("packageList", packageList);
		model.put("warehouseList", warehouseList);
		return "/logistics/warehouseList";
	}

	@RequestMapping(value = "/logistics/packageDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String packageDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getPackageDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/packageDetail";
	}

	@RequestMapping(value = "/logistics/addPackage.do")
	@Transactional(rollbackFor = Exception.class)
	public String addPackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		String size = request.getParameter("size");
		String color = request.getParameter("color");
		String number = request.getParameter("number");
		String sizes[] = size.split(",");
		String colors[] = color.split(",");
		String numbers[] = number.split(",");
		Package pack = new Package(orderId);
		List<PackageDetail> details = new ArrayList<PackageDetail>();
		for (int i = 0; i < sizes.length; i++) {
			PackageDetail detail = new PackageDetail();
			detail.setClothesAmount(Integer.parseInt(numbers[i]));
			detail.setClothesStyleColor(colors[i]);
			detail.setClothesStyleName(sizes[i]);
			details.add(detail);
		}
		logisticsService.addPackage(pack, details);
		return "forward:/logistics/packageDetail.do";
	}

	@RequestMapping(value = "/logistics/removePackage.do")
	@Transactional(rollbackFor = Exception.class)
	public String removePackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String packageId = request.getParameter("packageId");
		logisticsService.removePackage(Integer.parseInt(packageId));
		return "forward:/logistics/packageDetail.do";
	}

	@RequestMapping(value = "/logistics/packageSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String packageSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		logisticsService.packageSubmit(orderId);
		return "forward:/logistics/warehouseDetail.do";
	}

	@RequestMapping(value = "/logistics/warehouseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String warehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getPackageDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/warehouseDetail";
	}

	@RequestMapping(value = "/logistics/printWarehouseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String printWarehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Integer packageId = Integer.parseInt(request.getParameter("packageId"));
		Map<String, Object> orderInfo = logisticsService
				.getPrintWarehouseDetail(orderId, packageId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/printWarehouseDetail";
	}

	@RequestMapping(value = "/logistics/mobile/index.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "/logistics/mobile/index";
	}

	@RequestMapping(value = "/logistics/mobile/warehouseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileWarehouseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = logisticsService
				.getMobileWarehouseList();
		model.addAttribute("orderList", orderList);
		return "/logistics/mobile/warehouseList";
	}

	@RequestMapping(value = "/logistics/mobile/warehouseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileWarehouseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getMobileWarehouseDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/mobile/warehouseDetail";
	}

	@RequestMapping(value = "/logistics/mobile/updatePackage.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileUpdatePackage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer packageId = Integer.parseInt(request.getParameter("packageId"));
		String warehouse = request.getParameter("warehouseId");
		String shelf = request.getParameter("shelfId");
		String location = request.getParameter("location");
		logisticsService.updatePackage(packageId, warehouse, shelf, location);
		return "forward:/logistics/mobile/warehouseDetail.do";
	}

	@RequestMapping(value = "/logistics/mobile/warehouseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileWarehouseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Long taskId = Long.parseLong(request.getParameter("taskId"));
		logisticsService.mobileWarehouseSubmit(taskId, orderId);
		return "forward:/logistics/mobile/warehouseList.do";
	}

	// ===========================大货产品发货=================================
	@RequestMapping(value = "/logistics/sendClothesList.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> scanList = logisticsService.getScanClothesList();
		List<Map<String, Object>> sendList = logisticsService.getSendClothesList();
		model.addAttribute("scanList", scanList);
		model.addAttribute("sendList", sendList);
		return "/logistics/sendClothesList";
	}

	@RequestMapping(value = "/logistics/scanClothesDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String scanClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = logisticsService
				.getSendClothesDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/scanClothesDetail";
	}

	@RequestMapping(value = "/logistics/sendClothesDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = logisticsService
				.getSendClothesDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/sendClothesDetail";
	}

	@RequestMapping(value = "/logistics/mobile/sendClothesList.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileSendClothesList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> orderList = logisticsService
				.getMobileSendClothesList();
		model.addAttribute("orderList", orderList);
		return "/logistics/mobile/sendClothesList";
	}

	@RequestMapping(value = "/logistics/mobile/sendClothesDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileSendClothesDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = logisticsService
				.getMobileSendClothesDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/logistics/mobile/sendClothesDetail";
	}

	@RequestMapping(value = "/logistics/mobile/sendClothesSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String mobileSendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		logisticsService.mobileSendClothesSubmit(orderId);
		return "forward:/logistics/mobile/sendClothesList.do";
	}

	@RequestMapping(value = "/logistics/sendClothesSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String sendClothesSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		long taskId = Long.parseLong(request.getParameter("taskId"));
		String time = request.getParameter("time");
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		Float price = Float.parseFloat(request.getParameter("price"));
		String remark = request.getParameter("remark");
		logisticsService.sendClothesSubmit(orderId, taskId, price, name, time,
				number, remark);
		return "forward:/logistics/sendClothesList.do";
	}

	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private JbpmTest jbpmTest;
}
