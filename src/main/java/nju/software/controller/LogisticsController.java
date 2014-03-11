package nju.software.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.software.dataobject.Order;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.StringUtil;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogisticsController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@RequestMapping(value = "logistics/sampleOrderList.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sampleOrderGet(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// first recieve order list that need sample
		/*
		String page = request.getParameter("page");
		String number = request.getParameter("number_per_page");
		int s_page = 1;
		int s_number = 10;
		if (!StringUtil.isEmpty(page)) {
			s_page = Integer.parseInt(page);

		}
		if (!StringUtil.isEmpty(number)) {
			s_number = Integer.parseInt(number);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", s_page);
		map.put("number_per_page", s_number);
		*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);
		map.put("number_per_page", 1000);
		List<Object> object = orderService.getOrderWithSample(map);
		model.put("orderList", object.get(0));
	//	model.put("page", ((Map) object.get(1)).get("page"));
	//	model.put("page_number", ((Map) object.get(1)).get("page_number"));
		
		return "logistics/to_receive_sample_list";
	}
	@RequestMapping(value = "logistics/list.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String findAll(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		List<Order> r=this.orderService.findAll();
		System.out.println(r.size());
		return "logistics/to_receive_sample_list";
	}


	@RequestMapping(value = "logistics/sampleOrderRequest.do", method = RequestMethod.GET)
	@Transactional(rollbackFor = Exception.class)
	public String sampleOrderRequest(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// get the orderid and complete the jbpm task
		int confirm = Integer.parseInt(request.getParameter("confirm"));
		boolean receivedSample = false;
		int confirmIntValue = 2;
		if (confirm == 1) {
			receivedSample = true;
			confirmIntValue = 1;
		}
		String orderId = request.getParameter("orderId");

		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasks("WULIUZHUGUAN");
		boolean success = false;

	

		System.out.println("list.size"+list.size());
		for (TaskSummary task : list) {
			if (task.getName().equals("received_sample")) {
				// 直接进入到下一个流程时
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("receivedsample", receivedSample);
				// 需要获取task中的数据
				WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
						.getKsession().getProcessInstance(
								task.getProcessInstanceId());
				String orderId1 =  process.getVariable("orderId").toString();
				System.out.println(orderId1);
				// task orderId is the same as the request's orderId
				if (orderId.equals(orderId1)) {
					// complete the workflow
					System.out.println("found it");
					try {
						System.out.println(task.getId());
						jbpmAPIUtil.completeTask(task.getId(), map,
								"WULIUZHUGUAN");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// modify the order state
					Order or = orderService.findByOrderId(orderId);
					or.setHasPostedSampleClothes((short) confirmIntValue);
					success = orderService.merge(or);
					break;
				}

			}

		}

		model.put("success",success);
		return "/market/sampleOrderList.do";
	
	}
}
