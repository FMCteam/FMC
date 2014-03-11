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
public class DesignController {
	
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 设计验证跳转链接
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/verify.do", method= RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String verify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		System.out.println("design verify ================ show task");
		List<Order> orderList = new ArrayList<Order>();
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String actorId = account.getUserRole();
		System.out.println("actorId: " + actorId);
		String taskName = "design_verification";
		List<TaskSummary> list =jbpmAPIUtil.getAssignedTasksByTaskname(actorId, taskName);
		for (TaskSummary task : list) {
			//需要获取task中的数据	
			WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(task.getProcessInstanceId());
			int orderId  = (int) process.getVariable("orderId");
			Order order = orderService.getOrderById(orderId);
			if (order != null) {
				System.out.println("orderId: " + order.getOrderId());
				orderList.add(order);
			}
		}
		model.addAttribute("order_list", orderList);
		
		return "design/verify";
	}
	/**
	 * 设计验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design/doVerify.do", method= RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public String doVerify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("design verify ================");
		
		Account account = (Account) request.getSession().getAttribute("cur_user");
		String actorId = account.getUserRole();
		boolean designVal = Boolean.parseBoolean(request.getParameter("designVal"));
		String s_orderId_request = (String) request.getParameter("id");
		int orderId_request = Integer.parseInt(s_orderId_request);
		String taskName = "design_verification";
		List<TaskSummary> list =jbpmAPIUtil.getAssignedTasksByTaskname(actorId, taskName);
		for (TaskSummary task : list) {
			//需要获取task中的数据	
			WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(task.getProcessInstanceId());
			int orderId  = (int) process.getVariable("orderId");
			System.out.println("orderId: " + orderId);
			if (orderId_request == orderId) {
				Order order = orderService.getOrderById(orderId);
				//修改order内容

				//提交修改
				orderService.updateOrder(order);

				//修改流程参数
				Map<String, Object> data = new HashMap<>();
				data.put("designVal", designVal);
				//直接进入到下一个流程时
				try {
					jbpmAPIUtil.completeTask(task.getId(), data, actorId);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		return "redirect:/design/verify";
	}

}
