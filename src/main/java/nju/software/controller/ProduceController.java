package nju.software.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.service.ProduceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProduceController {

	@Autowired
	private ProduceService produceService;
	
	/**
	 * 生产验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyProduceList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyProduceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show task");
		Account account = (Account) request.getSession().getAttribute("cur_user");
//		String actorId = account.getUserRole();

		List<Map<String,Object>> orderList = produceService.getVerifyProduceList();
		model.addAttribute("list", orderList);
		model.addAttribute("taskName", "生产验证");
		model.addAttribute("url", "/produce/verifyProduceDetail.do");
		return "produce/verifyProduceList";
	}
	/**
	 * 生产验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyProduceSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String verifyProduceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("produce verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean productVal = Boolean.parseBoolean(request.getParameter("productVal"));
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String s_taskId = request.getParameter("taskId");
		long taskId = Long.parseLong(s_taskId);
		String comment = request.getParameter("suggestion");

		produceService.verifyProduceSubmit(account, orderId_request, taskId, productVal, comment);
		
		return "redirect:/produce/verifyProduceList.do";
	}
	
	/**
	 * 显示订单详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verifyProduceDetail.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verifyProduceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		String s_orderId_request = (String) request.getParameter("orderId");
		int orderId = Integer.parseInt(s_orderId_request);
		Map<String,Object> orderInfo = produceService.getVerifyProduceDetail(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "produce/verifyProduceDetail";
	}
	
	

	
	
	
	
	
	
	
	
	
	
	/**
	 * 生产成本核算跳转链接
	 * @author WangJian
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/computeProduceCostList.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<Map<String,Object>> list=produceService.getComputeProduceCostList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "生产成本核算");
		model.addAttribute("url", "/produce/computeProduceCostDetail.do");
		
		return "/produce/computeProduceCostList";
	}
	

	
	/**
	 * 显示成本核算详细信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/computeProduceCostDetail.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		Map<String,Object> orderInfo=produceService.getComputeProduceCostInfo(orderId);
		model.addAttribute("orderInfo", orderInfo);
		return "/produce/computeProduceCostDetail";
	}
	
	
	
	
	/**
	 * 成本核算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	
	
	
	
	@RequestMapping(value = "produce/computeProduceCostSubmit.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String computeProduceCostSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String orderId = (String) request.getParameter("orderId");
	    String taskId = request.getParameter("taskId");
		//裁剪费用
		String cut_cost = request.getParameter("cut_cost");
		//管理费用
		String manage_cost = request.getParameter("manage_cost");
		//缝制费用
		String nail_cost = request.getParameter("nail_cost");
		//整烫费用
	    String ironing_cost = request.getParameter("ironing_cost");
	  //锁订费用
		String swing_cost = request.getParameter("swing_cost");
		//包装费用
		String package_cost = request.getParameter("package_cost");
		//其他费用
		String other_cost = request.getParameter("other_cost");
	    //设计费用
		String design_cost = request.getParameter("design_cost");
		
		produceService.ComputeProduceCostSubmit(
				Integer.parseInt(orderId),
				Long.parseLong(taskId), 
				Float.parseFloat(cut_cost),
				Float.parseFloat(manage_cost),
				Float.parseFloat(nail_cost),
				Float.parseFloat(ironing_cost),
				Float.parseFloat(swing_cost),
				Float.parseFloat(package_cost),
				Float.parseFloat(other_cost),
				Float.parseFloat(design_cost)
						);
		
		return "redirect:/produce/computeProduceCostList.do";
	}
	
	
	//=======================样衣生产=======================
	@RequestMapping(value = "/produce/produceSampleList.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSampleList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list = produceService.getProduceSampleList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "样衣生产");
		model.addAttribute("url", "/produce/produceSampleDetail.do");
		return "/produce/produceSampleList";
	}
	
	
	@RequestMapping(value = "/produce/produceSampleDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSampleDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		Map<String,Object> orderInfo=produceService.getProduceSampleDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/produce/produceSampleDetail";
	}
	
	
	@RequestMapping(value = "/produce/produceSampleSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSampleSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId = request.getParameter("taskId");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		List<Produce> produceList = null;
//		if (!result) {
//			String produceColor = request.getParameter("produce_color");
//			String produceXS = request.getParameter("produce_xs");
//			String produceS = request.getParameter("produce_s");
//			String produceM = request.getParameter("produce_m");
//			String produceL = request.getParameter("produce_l");
//			String produceXL = request.getParameter("produce_xl");
//			String produceXXL = request.getParameter("produce_xxl");
//			produceList = produceService.getProduceList(orderId, produceColor, 
//					produceXS, produceS, produceM, produceL, produceXL, produceXXL);
//		}
		produceService.produceSampleSubmit(Long.parseLong(taskId), result, produceList);
		return "forward:/produce/produceSampleList.do";
	}
	
	
	//=====================批量生产======================
	@RequestMapping(value = "/produce/produceList.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>> list=produceService.getProduceList();
		model.addAttribute("list", list);
		model.addAttribute("taskName", "批量生产");
		model.addAttribute("url", "/produce/produceDetail.do");
		return "/produce/produceList";
	}
	
	
	@RequestMapping(value = "/produce/produceDetail.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId=request.getParameter("orderId");
		Map<String,Object> orderInfo=produceService.getProduceDetail(Integer.parseInt(orderId));
		model.addAttribute("orderInfo", orderInfo);
		return "/produce/produceDetail";
	}
	
	
	@RequestMapping(value = "produce/produceSubmit.do")
	@Transactional(rollbackFor = Exception.class)
	public String produceSubmit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {	
		boolean result = Boolean.parseBoolean(request.getParameter("result"));
		String taskId = request.getParameter("taskId");
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		List<Produce> produceList = null;
		if (result) {
			String produceColor = request.getParameter("produce_color");
			String produceXS = request.getParameter("produce_xs");
			String produceS = request.getParameter("produce_s");
			String produceM = request.getParameter("produce_m");
			String produceL = request.getParameter("produce_l");
			String produceXL = request.getParameter("produce_xl");
			String produceXXL = request.getParameter("produce_xxl");
			produceList = produceService.getProduceList(orderId, produceColor, produceXS, 
					produceS, produceM, produceL, produceXL, produceXXL, Produce.TYPE_PRODUCED);
		}
		produceService.pruduceSubmit(Long.parseLong(taskId), result, produceList);
		return "forward:/produce/produceList.do";
	}
}
