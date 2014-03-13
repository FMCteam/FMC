package nju.software.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.service.OrderService;
import nju.software.service.ProduceService;
import nju.software.util.JbpmAPIUtil;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProduceController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProduceService produceService;
	
	/**
	 * 生产验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("produce verify ================ show task");
		List<Order> orderList = new ArrayList<Order>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String actorId = account.getUserRole();
		System.out.println("actorId: " + actorId);
		String taskName = "production_verification";
		orderList = orderService.getOrderByActorIdAndTaskname(actorId, taskName);
		model.addAttribute("order_list", orderList);
		
		return "produce/verify";
	}
	/**
	 * 生产验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "produce/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("produce verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		boolean productVal = Boolean.parseBoolean(request.getParameter("productVal"));
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String taskName = "production_verification";
		produceService.verify(account, orderId_request, taskName, productVal);
		
		return "redirect:/produce/verify.do";
	}

}
