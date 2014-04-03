package nju.software.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nju.software.dataobject.Account;
import nju.software.model.OrderModel;
import nju.software.service.OrderService;
import nju.software.service.QualityService;
import nju.software.util.JbpmAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QualityController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private QualityService qualityService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	
	//质检列表
	@RequestMapping(value = "quality/checkList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String qualityCheckList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		List<OrderModel> tasks = qualityService.getCheckList();
		model.addAttribute("tasks", tasks);
		return "quality/checkList";
	}
	
	//质检详情
	@RequestMapping(value = "quality/checkDetail.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProductDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("id");
		String s_taskId = request.getParameter("tid");
		String s_processId = request.getParameter("pid");
		long taskId = Long.parseLong(s_taskId);
		long processId = Long.parseLong(s_processId);
		OrderModel om = new OrderModel(orderService.findByOrderId(orderId),taskId,processId);
		model.addAttribute("orderModel", om);
		return "quality/checkDetail";
	}
	
	//质量检查
	@RequestMapping(value = "quality/checkQuality.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String modifyProduct(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String orderId = request.getParameter("orderId");
		String s_taskId = request.getParameter("taskId");
		String s_processId = request.getParameter("processId");
		int id = Integer.parseInt(orderId);
		long taskId = Long.parseLong(s_taskId);
		long processId = Long.parseLong(s_processId);
		qualityService.checkQuality(id,taskId,processId,true);
		//marketService.modifyProduct(account.getUserId(),id,taskId,processId,null);

		return "redirect:/quality/qualityCheckList.do";
	}
		
}
