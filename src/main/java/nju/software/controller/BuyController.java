package nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.model.OrderInfo;
import nju.software.service.BuyService;
import nju.software.service.impl.JbpmTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BuyController {

	// ===========================采购验证=================================
	@RequestMapping(value = "/buy/verifyPurchaseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = buyService.getVerifyPurchaseList();
		model.put("list", list);
		model.addAttribute("taskName", "采购验证");
		model.addAttribute("url", "/buy/verifyPurchaseDetail.do");
		return "/buy/verifyPurchaseList";
	}

	@RequestMapping(value = "/buy/verifyPurchaseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = buyService
				.getVerifyPurchaseDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/verifyPurchaseDetail";
	}

	@RequestMapping(value = "/buy/verifyPurchaseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String verifyPurchaseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Long taskId = Long.parseLong(request.getParameter("taskId"));
		String comment = request.getParameter("suggestion");
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		buyService.verifyPurchaseSubmit(taskId, result, comment);
		return "forward:/buy/verifyPurchaseList.do";
	}

	// ===========================采购成本核算=================================
	@RequestMapping(value = "/buy/computePurchaseCostList.do")
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = buyService
				.getComputePurchaseCostList();
		/*if (list.size() == 0) {
			jbpmTest.completeVerify("1", true);
			list = buyService.getComputePurchaseCostList();
		}*/
		model.addAttribute("list", list);
		model.addAttribute("taskName", "采购成本核算");
		model.addAttribute("url", "/buy/computePurchaseCostDetail.do");
		return "/buy/computePurchaseCostList";
	}

	@RequestMapping(value = "/buy/computePurchaseCostDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = buyService
				.getComputePurchaseCostDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/computePurchaseCostDetail";
	}

	@RequestMapping(value = "/buy/computePurchaseCostSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String computePurchaseCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		long taskId = Long.parseLong(request.getParameter("taskId"));

		String[] fabric_names = request.getParameterValues("fabricName");
		String[] tear_per_meters = request.getParameterValues("tear_per_meter");
		String[] cost_per_meters = request.getParameterValues("cost_per_meter");
		
		
		String[] fabric_prices = request.getParameterValues("fabric_price");

		String[] accessory_names = request.getParameterValues("accessoryName");
		String[] tear_per_piece = request.getParameterValues("tear_per_piece");
		String[] cost_per_piece = request.getParameterValues("cost_per_piece");
		String[] accessory_prices = request
				.getParameterValues("accessory_price");

		buyService.computePurchaseCostSubmit(orderId, taskId, fabric_names,
				tear_per_meters, cost_per_meters,
				accessory_names, tear_per_piece, cost_per_piece
				);

		return "forward:/buy/computePurchaseCostList.do";

	}

	// ========================样衣原料采购===========================
	@RequestMapping(value = "/buy/purchaseSampleMaterialList.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = buyService
				.getPurchaseSampleMaterialList();
		model.put("list", list);
		model.addAttribute("taskName", "样衣原料采购");
		model.addAttribute("url", "/buy/purchaseSampleMaterialDetail.do");
		return "/buy/purchaseSampleMaterialList";
	}

	@RequestMapping(value = "/buy/purchaseSampleMaterialDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		Map<String, Object> orderInfo = buyService
				.getPurchaseSampleMaterialDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/purchaseSampleMaterialDetail";
	}

	@RequestMapping(value = "/buy/purchaseSampleMaterialSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseSampleMaterialSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		boolean result = request.getParameter("result").equals("1");
		buyService.purchaseSampleMaterialSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/purchaseSampleMaterialList.do";
	}

	// ========================生产验证===========================
	@RequestMapping(value = "/buy/confirmPurchaseList.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = buyService.getConfirmPurchaseList();
		model.put("list", list);
		model.addAttribute("taskName", "确认生产原料");
		model.addAttribute("url", "/buy/confirmPurchaseDetail.do");
		return "/buy/confirmPurchaseList";
	}

	@RequestMapping(value = "/buy/confirmPurchaseDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = buyService
				.getConfirmPurchaseDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/confirmPurchaseDetail";
	}

	@RequestMapping(value = "/buy/confirmPurchaseSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String confirmPurchaseSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		Boolean result = request.getParameter("result").equals("1");
		buyService.confirmPurchaseSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/confirmPurchaseList.do";
	}

	// ========================生产采购===========================
	@RequestMapping(value = "/buy/purchaseMaterialList.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String, Object>> list = buyService.getPurchaseMaterialList();
		model.put("list", list);
		model.addAttribute("taskName", "生产采购");
		model.addAttribute("url", "/buy/purchaseMaterialDetail.do");
		return "/buy/purchaseMaterialList";
	}

	@RequestMapping(value = "/buy/purchaseMaterialDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String, Object> orderInfo = buyService
				.getPurchaseMaterialDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/buy/purchaseMaterialDetail";
	}

	@RequestMapping(value = "/buy/purchaseMaterialSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String purchaseMaterialSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String taskId = request.getParameter("taskId");
		Boolean result = request.getParameter("result").equals("1");
		buyService.purchaseMaterialSubmit(Long.parseLong(taskId), result);
		return "forward:/buy/purchaseMaterialList.do";
	}

	@Autowired
	private BuyService buyService;
	@Autowired
	private JbpmTest jbpmTest;
}
