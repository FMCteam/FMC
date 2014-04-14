package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.AccountDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dataobject.*;
import nju.software.dataobject.Package;
import nju.software.model.OrderInfo;
import nju.software.service.LogisticsService;
import nju.software.util.DateUtil;
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
			orderInfo.setLogistics(logisticsDAO.findById(orderId));
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
		orderInfo.setEmployee(employeeDAO.findById(orderInfo.getOrder()
				.getEmployeeId()));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		Produce produce=new Produce();
		produce.setType(Produce.TYPE_PRODUCE);
		produce.setOid(orderId);
		orderInfo.setProduce(produceDAO.findByExample(produce));
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		orderInfo.setSample(produceDAO.findByExample(produce));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean receiveSampleSubmit(long taskId, String result) {
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
	public List<OrderInfo> getSendSampleList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setLogistics(logisticsDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	@Override
	public OrderInfo getSendSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_SAMPLE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setEmployee(employeeDAO.findById(orderInfo.getOrder()
				.getEmployeeId()));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	
	@Override
	public boolean sendSampleSubmit(Map<String,Object>map) {
		Integer orderId=(Integer) map.get("orderId");
		Logistics logistics=logisticsDAO.findById(orderId);
		logistics.setSampleClothesTime(getTime((String)map.get("time")));
		logistics.setSampleClothesNumber((String)map.get("number"));
		logistics.setSampleClothesName((String)map.get("name"));
		logisticsDAO.attachDirty(logistics);
		long taskId=(long) map.get("taskId");
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
		// orderInfo.setpa
		// orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean warehouseSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		return false;
	}

	// ===========================产品发货=================================
	@Override
	public List<OrderInfo> getSendClothesList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES);
		List<OrderInfo> unscan_models = new ArrayList<>();
		List<OrderInfo> scan_models = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo model = new OrderInfo();
			Order order = orderDAO.findById(orderId);
			model.setOrder(order);
			model.setTask(task);
			if(order.isScanChecked()) {
				scan_models.add(model);
			} else {
				unscan_models.add(model);
			}
			 
		}
		scan_models.addAll(unscan_models);
		return scan_models;
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
	
	
	private Timestamp getTime(String time){
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
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
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private PackageDetailDAO packageDetailDAO;
	
	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_SEND_CLOTHES = "sendClothes";

	@Override
	public List<OrderInfo> getSendClothesUncheckedList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES);
		List<OrderInfo> unscan_models = new ArrayList<>();
		 
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo model = new OrderInfo();
			Order order = orderDAO.findById(orderId);
			model.setOrder(order);
			model.setTask(task);
			if(!order.isScanChecked() && order.isStored()) {
				unscan_models.add(model);
			}
			 
		}
 
		return unscan_models;
	}
	@Override
	public List<OrderInfo> getSendClothesUnstoredList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES);
		List<OrderInfo> unsore_models = new ArrayList<>();
		 
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo model = new OrderInfo();
			Order order = orderDAO.findById(orderId);
			model.setOrder(order);
			model.setTask(task);
			if(!order.isScanChecked() && !order.isStored()) {
				unsore_models.add(model);
			}
			 
		}
 
		return unsore_models;
	}
	@Override
	public boolean setOrderScanChecked(int orderId) {
		// TODO Auto-generated method stub
	 
		Order order = orderDAO.findById(orderId);
		if(order == null) {
			return false;
		}
		try {
			orderDAO.setOrderScanChecked(order);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	@Override
	public boolean setOrderStored(int orderId) {
		// TODO Auto-generated method stub
	 
		Order order = orderDAO.findById(orderId);
		if(order == null) {
			return false;
		}
		try {
			order.setStored(true);
			orderDAO.attachDirty(order);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	@Override
	public boolean updateSendClothesStoreInfo(int packageId, String warehouse, String shelf,
			String location) {
		// TODO Auto-generated method stub
		Package pk = packageDAO.findById(packageId);
		if(pk == null) {
			return false;
		}
		pk.setWarehouseId(warehouse);
		pk.setShelfId(shelf);
		pk.setLocation(location);
		try {
			packageDAO.attachDirty(pk);
			return true;
		} catch(Exception ex) {
			return false;
		}
		
	}
	
	@Override
	public OrderInfo getScanCheckDetail(int orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_CLOTHES, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		List<Package> pList = packageDAO.findByOrderId(orderId);
		model.setPackages(pList);
		model.setPackageDetails(packageDetailDAO.findByPackageList(pList));
		model.setTask(task);
		return model;
	}

	@Override
	public Package createPackageForOrder(int orderId) {
		// TODO Auto-generated method stub
		Package newPackage = new Package();
		newPackage.setPackageTime(new Timestamp(new Date().getTime()));
		newPackage.setOrderId(orderId);
		packageDAO.save(newPackage);
		return newPackage;
	}

	

	
}
