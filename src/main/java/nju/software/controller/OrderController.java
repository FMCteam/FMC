package nju.software.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Order;
import nju.software.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	// =======================被终止订单列表=================================
	@RequestMapping(value = "/order/endList.do")
	@Transactional(rollbackFor = Exception.class)
	public String endList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Map<String,Object>>list=orderService.findByProperty("orderState","1");
		model.put("list", list);
		model.addAttribute("taskName", "被终止订单列表");
		model.addAttribute("url", "/order/orderDetail.do");
		return "/order/endList";
	}
	
	
	
	// ===========================结束订单=================================
	@RequestMapping(value = "/order/end.do")
	@Transactional(rollbackFor = Exception.class)
	public String end(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		orderService.endOrder(orderId);
		return "forward:/order/endList.do";
	}
}
