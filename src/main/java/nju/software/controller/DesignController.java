package nju.software.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public String addEmployee(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
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
	public String addMarketOrder(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		System.out.println("design verify ================");
		
		String actorId = (String) request.getSession().getAttribute("actorId");
		boolean isPassed = (boolean) request.getSession().getAttribute("");
		List<TaskSummary> list =jbpmAPIUtil.getAssignedTasks(actorId);
		for (TaskSummary task : list) {
			if(task.getName().equals("design_verification ")){
				//需要获取task中的数据	
				WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(task.getProcessInstanceId());
				String s_orderId =(String) process.getVariable("orderId");
				int orderId = Integer.parseInt(s_orderId);
				Order order = orderService.getOrderById(orderId);
				//修改order内容
				
				//提交修改
				orderService.updateOrder(order);
				
				//修改流程参数
				Map<String, Object> data = new HashMap<>();
				
				//直接进入到下一个流程时
				
				try {
					jbpmAPIUtil.completeTask(task.getId(), null, actorId);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return "redirect:/design/verify";
	}

}
