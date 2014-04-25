package nju.software.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dao.impl.VersionDataDAO;
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
	public List<Map<String, Object>> getReceiveSampleList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_RECEIVE_SAMPLE);
		List<Map<String, Object>> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", orderDAO.findById(orderId));
			model.put("logistics", logisticsDAO.findById(orderId));
			model.put("task", task);
			list.add(model);
		}
		return list;
	}

	@Override
	public Map<String, Object> getReceiveSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModel(ACTOR_LOGISTICS_MANAGER,
				TASK_RECEIVE_SAMPLE, orderId);
	}

	@Override
	public boolean receiveSampleSubmit(long taskId, Integer orderId,
			Short result) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		order.setHasPostedSampleClothes(result);
		orderDAO.attachDirty(order);
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put(RESULT_RECEIVE_SAMPLE, (int) result);
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
	public List<Map<String, Object>> getSendSampleList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_LOGISTICS_MANAGER, TASK_SEND_SAMPLE);
	}

	@Override
	public Map<String, Object> getSendSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModel(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_SAMPLE, orderId);
	}

	@Override
	public boolean sendSampleSubmit(Map<String, Object> map) {
		Integer orderId = (Integer) map.get("orderId");
		Logistics logistics = logisticsDAO.findById(orderId);
		logistics.setSampleClothesTime(getTime((String) map.get("time")));
		logistics.setSampleClothesNumber((String) map.get("number"));
		logistics.setSampleClothesName((String) map.get("name"));
		logisticsDAO.attachDirty(logistics);
		long taskId = (long) map.get("taskId");
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
	public List<Map<String, Object>> getPackageList() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Map<String, Object> model : temp) {
			if (((Order) model.get("order")).getLogisticsState() == 0) {
				list.add(model);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getWarehouseList() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Map<String, Object> model : temp) {
			if (((Order) model.get("order")).getLogisticsState() == 1) {
				list.add(model);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getPackageDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = service.getBasicOrderModel(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE, orderId);
		List<Package> packages = packageDAO.findByOrderId(orderId);
		model.put("packages", packages);
		model.put("packageDetails",
				packageDetailDAO.findByPackageList(packages));
		return model;
	}

	@Override
	public Integer addPackage(Package pack, List<PackageDetail> details) {
		// TODO Auto-generated method stub
		packageDAO.save(pack);
		for (PackageDetail detail : details) {
			detail.setPackageId(pack.getPackageId());
			packageDetailDAO.save(detail);
		}
		return pack.getPackageId();
	}

	@Override
	public boolean removePackage(Integer pid) {
		Package pack = packageDAO.findById(pid);
		packageDAO.delete(pack);
		List<PackageDetail> details = packageDetailDAO.findByPackageId(pid);
		for (PackageDetail detail : details) {
			packageDetailDAO.delete(detail);
		}
		return true;
	}

	@Override
	public boolean packageSubmit(Integer orderId) {
		Order order = orderDAO.findById(orderId);
		order.setLogisticsState(1);
		orderDAO.attachDirty(order);
		return true;
	}

	@Override
	public List<OrderInfo> getMobileWarehouseList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE);
		List<OrderInfo> unsore_models = new ArrayList<>();

		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			OrderInfo model = new OrderInfo();
			Order order = orderDAO.findById(orderId);
			model.setOrder(order);
			model.setTask(task);

			if (order.getLogisticsState() == 1) {
				unsore_models.add(model);
			}
		}
		return unsore_models;
	}

	@Override
	public Map<String, Object> getMobileWarehouseDetail(int orderId) {
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_WAREHOUSE, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", orderDAO.findById(orderId));
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("task", task);
		return model;
	}

	@Override
	public boolean updatePackage(int packageId, String warehouse, String shelf,
			String location) {
		// TODO Auto-generated method stub
		Package pk = packageDAO.findById(packageId);
		if (pk == null) {
			return false;
		}
		pk.setWarehouseId(warehouse);
		pk.setShelfId(shelf);
		pk.setLocation(location);
		try {
			packageDAO.attachDirty(pk);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean mobileWarehouseSubmit(long taskId, Integer orderId) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		if (order != null) {
			order.setLogisticsState(2);
			orderDAO.attachDirty(order);
		}
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

	// ===========================产品发货=================================
	@Override
	public List<Map<String, Object>> getSendClothesList() {
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
			if (order.getLogisticsState() == 3) {
				scan_models.add(model);
			} else {
				unscan_models.add(model);
			}
		}
		scan_models.addAll(unscan_models);
		// return scan_models;
		return null;
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
	public List<Map<String, Object>> getMobileSendClothesList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");

		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			if (order.getLogisticsState() == 2) {
				model.put("order", order);
				model.put("task", task);
				model.put("taskTime", dateFormat.format(task.getCreatedOn()));
				model.put("orderId", dateFormat2.format(order.getOrderTime())
						+ String.format("%06d", order.getOrderId()));
				list.add(model);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getMobileSendClothesDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_CLOTHES, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", orderDAO.findById(orderId));
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("task", task);
		return model;
	}

	@Override
	public boolean mobileSendClothesSubmit(int orderId) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		if (order == null) {
			return false;
		}
		try {
			order.setLogisticsState(3);
			orderDAO.attachDirty(order);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public void sendClothesSubmit(Integer orderId, long taskId, float price,
			String name, String time, String number, String remark) {
		// TODO Auto-generated method stub
		Logistics logistics = logisticsDAO.findById(orderId);
		logistics.setProductClothesNumber(number);
		logistics.setProductClothesRemark(remark);
		logistics.setProductClothesPrice(price + "");
		logistics.setProductClothesType(name);
		logistics.setProductClothesTime(getTime(time));
		logisticsDAO.attachDirty(logistics);
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Timestamp getTime(String time) {
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
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

	@Override
	public List<Package> getPackageListByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return packageDAO.findByOrderId(orderId);
	}

	@Override
	public List<PackageDetail> getPackageDetailList(int packageId) {
		return packageDetailDAO.findByPackageId(packageId);
	}

	@Override
	public Package getPackageByPackageId(int packageId) {
		return packageDAO.findById(packageId);
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
	@Autowired
	private VersionDataDAO versionDataDAO;
	@Autowired
	private ServiceUtil service;

	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_SEND_CLOTHES = "sendClothes";
	public final static String RESULT_RECEIVE_SAMPLE = "receiveSample";
	public final static String RESULT_SEND_SAMPLE = "sendSample";

	@Override
	public List<Map<String, Object>> getScanClothesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logistics findByOrderId(String s_id) {
		// TODO Auto-generated method stub
		return logisticsDAO.findById(Integer.parseInt(s_id));
	}
}
