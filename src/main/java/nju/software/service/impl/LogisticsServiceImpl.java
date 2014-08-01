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
import nju.software.dao.impl.DeliveryRecordDAO;
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
			Order order = orderDAO.findById(orderId);
			model.put("order",order);
			model.put("logistics", logisticsDAO.findById(orderId));
			model.put("task", task);
			model.put("taskTime", service.getTaskTime(task.getCreatedOn()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSearchReceiveSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		// TODO Auto-generated method stub
		List<Order> orders = orderDAO.getSearchOrderList( ordernumber,
				 customername,  stylename,  startdate,enddate,employeeIds);
		
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_RECEIVE_SAMPLE);	
		List<Map<String, Object>> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			boolean isSearched = false;
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			for(int k =0;k<orders.size();k++){
				if(orderId.equals(orders.get(k).getOrderId())){
					isSearched = true;
					break;
				}
			}
			if(isSearched==true){
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			model.put("order",order);
			model.put("logistics", logisticsDAO.findById(orderId));
			model.put("task", task);
			model.put("taskTime", service.getTaskTime(task.getCreatedOn()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
			}
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
	public List<Map<String, Object>> getSearchSendSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		return service.getSearchOrderList(ACTOR_LOGISTICS_MANAGER, ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds, TASK_SEND_SAMPLE);

	}

	
	@Override
	public Map<String, Object> getSendSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_SAMPLE, orderId);
	}

	@Override
	public boolean sendSampleSubmit(Map<String, Object> map) {
		/*
		 * author:whh
		 * 测试新建流程分支，好多衣走简化流程,流程去掉各种验证
		 */
		
		Integer orderId = (Integer) map.get("orderId");
		
//		Order customerOrder = orderDAO.findById(orderId);
//		Customer cus =  customerDAO.findById(customerOrder.getCustomerId());
//		boolean takeSampleMoney = false;
//		if(cus.getCustomerName().equals("好多衣")){
//			  takeSampleMoney = false; 
//		}else{
//			  takeSampleMoney = true; 
//		}
		
		//更新物流信息
		Logistics logistics = logisticsDAO.findById(orderId);
		logistics.setSampleClothesTime(getTime((String) map.get("time")));//邮寄时间
		logistics.setSampleClothesNumber((String) map.get("number"));//快递单号
		logistics.setSampleClothesType((String) map.get("name"));//快递名称
		logisticsDAO.attachDirty(logistics);
		
		//需要记录每次发货的信息
		DeliveryRecord deliveryRecord = new DeliveryRecord();
		deliveryRecord.setOrderId(orderId);
		deliveryRecord.setSendType("sample");// 发货类型为“样衣”
		deliveryRecord.setRecipientName(logistics.getSampleClothesName());// 收件人姓名
		deliveryRecord.setRecipientPhone(logistics.getSampleClothesPhone());// 收件人手机
		deliveryRecord.setRecipientAddr(logistics.getSampleClothesAddress());// 收件人地址
		deliveryRecord.setExpressName((String) map.get("name"));// 快递名称
		deliveryRecord.setExpressNumber((String) map.get("number"));// 快递单号
		deliveryRecord.setSendTime(getTime((String) map.get("time")));// 邮寄时间
		deliveryRecordDAO.save(deliveryRecord);
		
		
		String isFinal = (String) map.get("isFinal");	
		//如果不是最终的样衣发货，不执行completeTask方法，直接返回
		if(isFinal.equals("false")){
			return true;
		}
		
		long taskId = (long) map.get("taskId");
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put(key, value);
//		data.put(RESULT_TAKE_SAMPLE_Money, takeSampleMoney);
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
		return getLogisticsList(TASK_WAREHOUSE, 0);
	}

	@Override
	public List<Map<String, Object>> getWarehouseList() {
		// TODO Auto-generated method stub
		return getLogisticsList(TASK_WAREHOUSE, 1);
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
	public Map<String, Object> getPrintWarehouseDetail(Integer orderId,
			Integer packageId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = new HashMap<>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("logistics",logisticsDAO.findById(orderId));
		model.put("pack", packageDAO.findById(packageId));
		model.put("packDetails", packageDetailDAO.findByPackageId(packageId));
		model.put("orderId", service.getOrderId(order));
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
	public List<Map<String, Object>> getMobileWarehouseList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE);
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			model.put("order", order);
			model.put("orderId", dateFormat2.format(order.getOrderTime())
					+ String.format("%06d", order.getOrderId()));

			if (order.getLogisticsState() == 1) {
				list.add(model);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getMobileWarehouseDetail(int orderId) {
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_LOGISTICS_MANAGER,
				TASK_WAREHOUSE, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("task", task);
		model.put(
				"orderId",
				dateFormat2.format(order.getOrderTime())
						+ String.format("%06d", order.getOrderId()));
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
		Short isHaoDuoYi = order.getIsHaoDuoYi();
		short ishaoduoyi = isHaoDuoYi.shortValue();
		boolean is_hao_duo_yi = (ishaoduoyi==1)?true:false;
		if (order != null) {
			order.setLogisticsState(2);
			orderDAO.attachDirty(order);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_IS_HAODUOYI, is_hao_duo_yi);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_LOGISTICS_MANAGER);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ===========================产品发货=================================
	@Override
	public List<Map<String, Object>> getScanClothesList() {
		// TODO Auto-generated method stub
		return getLogisticsList(TASK_SEND_CLOTHES, 2);
	}

	@Override
	public List<Map<String, Object>> getSendClothesList() {
		// TODO Auto-generated method stub
		return getLogisticsList(TASK_SEND_CLOTHES, 3);
	}

	@Override
	public Map<String, Object> getSendClothesDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model=service.getBasicOrderModelWithWareHouse(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_CLOTHES, orderId);
		List<Package>packages=packageDAO.findByOrderId(orderId);
		if(packages==null){
			model.put("packageNumber", 0);
		}else{
			model.put("packageNumber", packages.size());
		}	
		return model;
	}

	@Override
	public List<Map<String, Object>> getMobileSendClothesList() {
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil
					.getVariable(task, "orderId");
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			if (order.getLogisticsState() == 2) {
				model.put("order", order);
				model.put("task", task);
				model.put("taskTime", service.getTaskTime(task.getCreatedOn()));
				model.put("orderId", service.getOrderId(order));
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
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("task", task);
		model.put(
				"orderId",
				dateFormat2.format(order.getOrderTime())
						+ String.format("%06d", order.getOrderId()));
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
			/*
			 * whh:设置orderstate字段表示order结束状态
			 */
			Order order = orderDAO.findById(orderId);
			order.setOrderState("Done");
			orderDAO.merge(order);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Map<String, Object>> getLogisticsList(String taskName,
			Integer state) {
		List<Map<String, Object>> temp = service.getOrderList(
				ACTOR_LOGISTICS_MANAGER, taskName);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Map<String, Object> model : temp) {
			if (((Order) model.get("order")).getLogisticsState() == state) {
				list.add(model);
			}
		}
		return list;
	}

	private Timestamp getTime(String time) {
		Date outDate = DateUtil.parse(time, DateUtil.newFormat);
		return new Timestamp(outDate.getTime());
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
	private DeliveryRecordDAO deliveryRecordDAO;
	@Autowired
	private ServiceUtil service;

	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_SEND_CLOTHES = "sendClothes";
	public final static String RESULT_RECEIVE_SAMPLE = "receiveSample";
	public final static String RESULT_SEND_SAMPLE = "sendSample";
	public final static String RESULT_TAKE_SAMPLE_Money = "takeSampleMoney";
    public final static String RESULT_IS_HAODUOYI = "isHaoDuoYi";
	@Override
	public Logistics findByOrderId(String s_id) {
		// TODO Auto-generated method stub
		return logisticsDAO.findById(Integer.parseInt(s_id));
	}




}
