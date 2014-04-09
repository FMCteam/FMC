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
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.model.OrderInfo;
import nju.software.service.LogisticsService;
import nju.software.util.JbpmAPIUtil;

@Service("logisticsServiceImpl")
public class LogisticsServiceImpl implements LogisticsService {
		
	
	// ===========================收取样衣=================================
	@Override
	public List<OrderInfo> getReceiveSampleList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_RECEIVE_SAMPLE);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}
	
	
	@Override
	public OrderInfo getReceiveSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_RECEIVE_SAMPLE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	
	@Override
	public boolean receiveSampleSubmit(long taskId,String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("receivedsample", result.equals("1"));
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}

	
	// ===========================样衣发货=================================
	@Override
	public List<OrderInfo> getSendSampleList(int s_page, int s_number_per_page) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
		List<OrderInfo> logList = new ArrayList<OrderInfo>();
		for (TaskSummary task : list) {
				Integer orderId = (Integer)jbpmAPIUtil.getVariable(task,"orderId"); 
				Order o = orderDAO.findById(orderId);
				Logistics l = logisticsDAO.findById(orderId);
				OrderInfo log = new OrderInfo();
				log.setOrder(o);
				log.setLogistics(l);
				log.setTask(task);
				logList.add(log);
		}
		return logList;
	}
	
	
	@Override
	public OrderInfo getSendSampleDetail(int id, long tid) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
		for (TaskSummary task : list) {
			Integer orderId = (Integer)jbpmAPIUtil.getVariable(task,"orderId"); 
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
		return null;
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
	

	// ===========================产品入库=================================
	@Override
	public List<OrderInfo> getWarehouseList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	@Override
	public OrderInfo getWarehouseDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_WAREHOUSE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setProducts(productDAO.findByOrderId(orderId));
		orderInfo.setPackages(packageDAO.findByOrderId(orderId));
		//orderInfo.setpa
		//orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean warehouseSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public List<OrderInfo> getSendClothesList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES );
		List<OrderInfo> models = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil.getVariable(task,"orderId");
			OrderInfo model = new OrderInfo();
			model.setOrder(orderDAO.findById(orderId));
			model.setTask(task);
			models.add(model);
		}
		return models;
	}

	@Override
	public OrderInfo getSendClothesDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_CLOTHES, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public void sendClothesSubmit(int orderId, long taskId, float logistics_cost) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


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
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private PackageDAO packageDAO;
	
	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_SEND_CLOTHES = "sendClothes";
}
