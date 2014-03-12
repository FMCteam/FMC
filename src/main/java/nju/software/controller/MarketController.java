package nju.software.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Order;
import nju.software.service.CustomerService;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.drools.runtime.process.WorkflowProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MarketController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;

	@RequestMapping(value = "market/customerOrder.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String customerOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Map params = new HashMap();
		params.put("page", 1);
		params.put("number_per_page", 100);
		List list = customerService.listCustomer(params);
		model.addAttribute("customer_list", list.get(0));
		return "market/customer_order";
	}

	@RequestMapping(value = "market/add.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String addEmployee(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String id=request.getParameter("cid");
		Customer customer=customerService.findByCustomerId(Integer.parseInt(id));
		model.addAttribute("customer",customer);
		return "market/add_order";
	}

	@RequestMapping(value = "market/addMarketOrder.do", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String addMarketOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Order order = new Order();
		order.setAskAmount(10);
		order.setCustomerName("张三");
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		order.setOrderTime(timestamp);
		order.setOrderState("A");
		order.setCustomerId(1);
		order.setEmployeeId(1);
		order.setHasPostedSampleClothes((short) 0);
		order.setIsNeedSampleClothes((short) 1);
		orderService.addOrder(order);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		Account curUser = (Account) request.getSession().getAttribute(
				"cur_user");
		params.put("employeeId", curUser.getUserId());
		params.put("needclothes", true);
		params.put("sendclothes", true);

		params.put("receivedsample", false);
		params.put("editOrder", false);

		/*
		 * String actorId=(String) request.getSession().getAttribute("actorId");
		 * List<TaskSummary> list =jbpmAPIUtil.getAssignedTasks(actorId); for
		 * (TaskSummary task : list) { if(task.getName().equals("addOrder")){
		 * //直接进入到下一个流程时 Map<String, Object> map = new HashMap<>(); try {
		 * jbpmAPIUtil.completeTask(task.getId(), null, actorId); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } //需要获取task中的数据 WorkflowProcessInstance
		 * process=(WorkflowProcessInstance)
		 * jbpmAPIUtil.getKsession().getProcessInstance
		 * (task.getProcessInstanceId()); String orderId =(String)
		 * process.getVariable("orderId"); process.getVariable(""); }
		 * 
		 * }
		 */

		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程启动成功！");
		} catch (Exception e) {
			System.out.println("流程启动失败！");
			e.printStackTrace();
		}
		return "redirect:/market/add.do";
	}

}
