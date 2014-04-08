package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.controller.LogisticsController.ComposeOrderAndLog;
import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.model.OrderInfo;
import nju.software.service.LogisticsService;
import nju.software.util.JbpmAPIUtil;

@Service("logisticsServiceImpl")
public class LogisticsServiceImpl implements LogisticsService{
	
	
	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_SEND_CLOTHES = "sendClothes";
	
	
	
	
	
	

	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Override
	public Logistics findByOrderId(String orderId) {
		// TODO Auto-generated method stub
		try{
		List<Logistics> list= logisticsDAO.findByProperty("orderId",Integer.parseInt(orderId));
		if(list!=null&&list.size()>=1)
		{
			return list.get(0);
		}
		}catch(Exception e)
		{
			
		}
		return null;
	}
	@Override
	public boolean addLogistics(Logistics log) {
		// TODO Auto-generated method stub
		try{
		this.logisticsDAO.save(log);
		return true;
		}catch(Exception e)
		{
			
		}
		return false;
	}
	
	@Override
	public boolean sendSampleSubmit(long taskId, long processId){
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	@Override
	public List<OrderInfo> getSendSampleList(int s_page, int s_number_per_page) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
		int start = s_number_per_page * (s_page - 1);
		List<OrderInfo> logList = new ArrayList<OrderInfo>();
		int i = 0;
		int j = 0;
		for (TaskSummary task : list) {
			if (i >= start && j < s_number_per_page) {
				WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
						.getKsession().getProcessInstance(
								task.getProcessInstanceId());
				Integer orderId = (Integer)process.getVariable("orderId"); 
				Order o = orderDAO.findById(orderId);
				Logistics l = logisticsDAO.findById(orderId);
				OrderInfo log = new OrderInfo();
				log.setOrder(o);
				log.setLogistics(l);
				log.setTask(task);
				logList.add(log);
				j++;
			}
			i++;
		}
		return logList;
	}
	@Override
	public OrderInfo getSendSampleDetail(int id, long tid) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
		for (TaskSummary task : list) {
			WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
					.getKsession().getProcessInstance(
							task.getProcessInstanceId());
			Integer orderId = (Integer)process.getVariable("orderId"); 
			if(id==orderId && tid==task.getId()){
				Order o = orderDAO.findById(orderId);
				Logistics l = logisticsDAO.findById(orderId);
				Employee e = employeeDAO.findById(o.getEmployeeId());
				Customer c = customerDAO.findById(o.getCustomerId());
				OrderInfo log = new OrderInfo();
				log.setOrder(o);
				log.setLogistics(l);
				log.setEmployee(e);
				log.setCustomer(c);
				log.setTask(task);
				return log;
			}
		}
		return null;
	}

}
