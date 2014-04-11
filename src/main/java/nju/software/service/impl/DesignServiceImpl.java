package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.TimeStamp;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.dataobject.DesignCad;
import nju.software.model.OrderInfo;
import nju.software.service.DesignService;
import nju.software.util.JbpmAPIUtil;

@Service("designServiceImpl")
public class DesignServiceImpl implements DesignService {

	public final static String ACTOR_DESIGN_MANAGER = "designManager";
	public final static String TASK_VERIFY_DESIGN = "verifyDesign";
	public final static String TASK_COMPUTE_DESIGN_COST = "computeDesignCost";
	public final static String TASK_UPLOAD_DESIGN = "uploadDegisn";
	public final static String TASK_MODIFY_DESIGN = "modifyDesign";
	public final static String TASK_CONFIRM_DESIGN = "confirmDesign";

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private QuoteDAO QuoteDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private DesignCadDAO DesignCadDAO;

	@Override
	public boolean verifyDesignSubmit(Account account, int orderId, long taskId,
			boolean designVal, String comment) {
		// TODO Auto-generated method stub
		// String actorId = account.getUserRole();
			Order order = orderDAO.findById(orderId);
			// 修改order内容

			// 提交修改
			orderDAO.attachDirty(order);

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("designVal", designVal);
			data.put("designComment", comment);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
	}

	@Override
	public boolean costAccounting(Account account, int orderId, long taskId,
			long processId, float design_cost) {
		// TODO Auto-generated method stub

		String actorId = "SHEJIZHUGUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {

			Quote quote = QuoteDAO.findById(orderId);
			if (quote == null) {
				// 数据库中无quote对象
				// 修改QUote内容
				quote = new Quote();
				quote.setOrderId(orderId);
				quote.setDesignCost(design_cost);
				QuoteDAO.save(quote);
			} else {
				// quote已存在于数据库
				// 修改QUote内容
				quote.setDesignCost(design_cost);
				QuoteDAO.attachDirty(quote);
			}

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			// data.put("designVal", designVal);
			data.put("designcost", design_cost);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;

	}

	@Override
	public Logistics getLogisticsByOrderId(int orderId) {
		// TODO Auto-generated method stub
		Logistics logistic = logisticsDAO.findById(orderId);
		return logistic;
	}

	@Override
	public List<Fabric> getFabricByOrderId(int orderId) {
		// TODO Auto-generated method stub
		List<Fabric> list = fabricDAO.findByOrderId(orderId);
		return list;
	}

	@Override
	public List<Accessory> getAccessoryByOrderId(int orderId) {
		// TODO Auto-generated method stub
		List<Accessory> list = accessoryDAO.findByOrderId(orderId);
		return list;
	}

	@Override
	public boolean uploadCAD(Account account, int orderId, long taskId,
			long processId, String url, Timestamp uploadTime) {
		String actorId = "SHEJIZHUGUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {

			DesignCad designCad = DesignCadDAO.findById(orderId);
			if (designCad == null) {
				// 数据库中无designCad对象
				// 新建QUote内容
				designCad = new DesignCad();
				designCad.setOrderId(orderId);
				designCad.setCadUrl(url);
				designCad.setCadVersion((short) 1);
				designCad.setUploadTime(uploadTime);
				DesignCadDAO.save(designCad);
			} else {
				// designCad已存在于数据库
				// 修改QUote内容

				short newVersion = (short) (designCad.getCadVersion() + 1);
				designCad.setCadUrl(url);
				designCad.setCadVersion(newVersion);
				designCad.setUploadTime(uploadTime);
				DesignCadDAO.attachDirty(designCad);
			}

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			// data.put("designVal", designVal);
			data.put("OrderId", orderId);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}


	
	
	@Override
	public List<OrderInfo> getComputeDesignCostList() {
		
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER, TASK_COMPUTE_DESIGN_COST );
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
	public OrderInfo getComputeDesignCostDetail(Integer orderId) {
		
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_DESIGN_MANAGER,
				TASK_COMPUTE_DESIGN_COST, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	
	@Override
	public void computeDesignCostSubmit(int orderId, long taskId,
			float design_cost) {
		// TODO Auto-generated method stub
		Quote quote = QuoteDAO.findById(orderId);

		if (quote == null) {
			quote = new Quote();
			quote.setOrderId(orderId);
			quote.setDesignCost(design_cost);
			QuoteDAO.save(quote);
		} else {
			quote.setDesignCost(design_cost);
			QuoteDAO.attachDirty(quote);
		}
		
		
				Map<String, Object> data = new HashMap<String, Object>();
				try {
					data.put("ComputeDesignCost", true);
					jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	
	@Override
	public List<OrderInfo> getUploadDesignList() {
		
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER, TASK_UPLOAD_DESIGN );
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
	public OrderInfo getUploadDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_DESIGN_MANAGER,
				TASK_UPLOAD_DESIGN, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public void uploadDesignSubmit(int orderId, long taskId, String url,
			Timestamp uploadTime) {
		// TODO Auto-generated method stub
		DesignCad designCad = DesignCadDAO.findById(orderId);
		if (designCad == null) {
			// 数据库中无designCad对象
			// 新建QUote内容
			designCad = new DesignCad();
			designCad.setOrderId(orderId);
			designCad.setCadUrl(url);
			designCad.setCadVersion((short) 1);
			designCad.setUploadTime(uploadTime);
			DesignCadDAO.save(designCad);
		} else {
			// designCad已存在于数据库
			// 修改QUote内容

			short newVersion = (short) (designCad.getCadVersion() + 1);
			designCad.setCadUrl(url);
			designCad.setCadVersion(newVersion);
			designCad.setUploadTime(uploadTime);
			DesignCadDAO.attachDirty(designCad);
		}

		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("Design", true);
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_DESIGN_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	@Override
	public List<OrderInfo> getModifyDesignList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER,  TASK_MODIFY_DESIGN  );
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
	public OrderInfo getModifyDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
				TaskSummary task = jbpmAPIUtil.getTask(ACTOR_DESIGN_MANAGER,
						 TASK_MODIFY_DESIGN , orderId);
				OrderInfo model = new OrderInfo();
				model.setOrder(orderDAO.findById(orderId));
				model.setTask(task);
				return model;
	}

	@Override
	public List<OrderInfo> getConfirmDesignList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER,  TASK_CONFIRM_DESIGN  );
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
	public OrderInfo getConfirmDesignDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_DESIGN_MANAGER,
				 TASK_CONFIRM_DESIGN , orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	
		
	

	@Override
	public List<OrderInfo> getVerifyDesignList() {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER, TASK_VERIFY_DESIGN);
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		if (list.isEmpty()) {
			System.out.println("no task list");
		}
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		WorkflowProcessInstance process = null;
		for (TaskSummary task : list) {
			// 需要获取task中的数据
			long processId = task.getProcessInstanceId();
			process = (WorkflowProcessInstance) session
					.getProcessInstance(processId);
			int orderId = (int) process.getVariable("orderId");
			OrderInfo oi = new OrderInfo();
			oi.setOrder(orderDAO.findById(orderId));
			oi.setTask(task);
			orderList.add(oi);
		}
		return orderList;
	}

	@Override
	public OrderInfo getVerifyDesignDetail(int orderId, long taskId) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_DESIGN_MANAGER, TASK_VERIFY_DESIGN);
		for (TaskSummary task : list) {
				OrderInfo oi = new OrderInfo();
				oi.setOrder(orderDAO.findById(orderId));
				oi.setLogistics(logisticsDAO.findById(orderId));
				oi.setFabrics(fabricDAO.findByOrderId(orderId));
				oi.setAccessorys(accessoryDAO.findByOrderId(orderId));
				oi.setTask(task);
				return oi;
			
		}
		return null;
	}


}
