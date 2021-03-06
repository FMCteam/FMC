package nju.software.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.DeliveryRecordDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.MoneyDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.VersionDataDAO;
import nju.software.dataobject.DeliveryRecord;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Package;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Produce;
import nju.software.process.service.FMCProcessService;
import nju.software.service.LogisticsService;
import nju.software.util.DateUtil;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("logisticsServiceImpl")
public class LogisticsServiceImpl implements LogisticsService {

	// ===========================收取样衣=================================
	@Override
	public List<Map<String, Object>> getReceiveSampleList() {
		List<Task> tasks = mainProcessService.getReceiveSampleTasks(ACTOR_LOGISTICS_MANAGER);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Task task : tasks) {
			Integer orderId = mainProcessService.getOrderIdInProcess(task.getProcessInstanceId());
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			model.put("order",order);
			model.put("logistics", logisticsDAO.findById(orderId));
//			model.put("task", task);
			model.put("taskTime", service.getTaskTime(task.getCreateTime()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSearchReceiveSampleList(
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds,String userRole,Integer userId) {
		List<Order> orders = orderDAO.getSearchOrderList( ordernumber,
				 customername,  stylename,  startdate,enddate,employeeIds,userRole,userId);
		
		List<Task> tasks = mainProcessService.getReceiveSampleTasks(ACTOR_LOGISTICS_MANAGER);	
		List<Map<String, Object>> list = new ArrayList<>();
		for (Task task : tasks) {
			boolean isSearched = false;
			Integer orderId = mainProcessService.getOrderIdInProcess(task.getProcessInstanceId());
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
//			model.put("task", task);
			model.put("taskTime", service.getTaskTime(task.getCreateTime()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
			}
		}
		return list;
 	}
	
	@Override
	public Map<String, Object> getReceiveSampleDetail(Integer orderId) {
		return service.getBasicOrderModel(ACTOR_LOGISTICS_MANAGER,
				TASK_RECEIVE_SAMPLE, orderId);
	}

	@Override
	public boolean receiveSampleSubmit(String taskId, Integer orderId,
			Short result) {
		Order order = orderDAO.findById(orderId);

		order.setHasPostedSampleClothes(result);
		orderDAO.attachDirty(order);
		Map<String, Object> data = new HashMap<String, Object>();
			data.put(RESULT_RECEIVE_SAMPLE, (int) result);
			try {
				mainProcessService.completeTask(taskId, ACTOR_LOGISTICS_MANAGER, data);
			if(result.intValue()==1){//如果result的的值为1，即为未收取到样衣，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				order.setOrderProcessStateName("被终止");
				orderDAO.attachDirty(order);
				
			}
			return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
	}

	// ===========================样衣发货=================================
	@Override
	public List<Map<String, Object>> getSendSampleList() {
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
		Map<String, Object> model = service.getBasicOrderModelWithQuote(ACTOR_LOGISTICS_MANAGER,
				TASK_SEND_SAMPLE, orderId);
		// 样衣发货记录
		List<DeliveryRecord> deliveryRecord = deliveryRecordDAO.findSampleRecordByOrderId(orderId);
		model.put("deliveryRecord", deliveryRecord);
		return model;
	}

	@Override
	public boolean sendSampleSubmit(Map<String, Object> map) {
		Integer orderId = (Integer) map.get("orderId");		
		
		String isFinal = (String) map.get("isFinal");	
		//如果不是最终的样衣发货，保存本次发货记录，不执行completeTask方法，直接返回
		if(isFinal.equals("false")){
			//更新物流信息
			Logistics logistics = logisticsDAO.findById(orderId);
			logistics.setSampleClothesTime(getTime((String) map.get("time")));//邮寄时间
			logistics.setSampleClothesNumber((String) map.get("number"));//快递单号
			logistics.setSampleClothesType((String) map.get("name"));//快递名称
			//logistics.setSampleClothesPrice((String) map.get("price"));//快递价格
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
			deliveryRecord.setExpressPrice((String) map.get("price"));// 快递价格
			deliveryRecord.setSendTime(getTime((String) map.get("time")));// 邮寄时间
			deliveryRecordDAO.save(deliveryRecord);
			return true;
		}
		
		//如果是最终发货，需要先判断是否有发货记录，若没有，不能完成最终的发货
		List<DeliveryRecord> list = deliveryRecordDAO.findByOrderId(orderId);
		if(list == null || list.size() == 0){
			return false;
		}
		
		String taskId = (String) map.get("taskId");
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put(key, value);
//		data.put(RESULT_TAKE_SAMPLE_Money, takeSampleMoney);
			try {
				mainProcessService.completeTask(taskId, ACTOR_LOGISTICS_MANAGER, data);
				return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
	}

	// ===========================产品入库=================================
	@Override
	public List<Map<String, Object>> getPackageList() {
		return getLogisticsList(TASK_WAREHOUSE, 0);
	}
	
	@Override
	public List<Map<String, Object>> getPackageHaoDuoYiList() {
		return getLogisticsList(TASK_WAREHOUSE_HAODUOYI, 0);
	}

	@Override
	public List<Map<String, Object>> getWarehouseList() {
		return getLogisticsList(TASK_WAREHOUSE, 1);
	}
	
	@Override
	public List<Map<String, Object>> getWarehouseHaoDuoYiList() {
		return getLogisticsList(TASK_WAREHOUSE_HAODUOYI, 1);
	}

	@Override
	public Map<String, Object> getPackageDetail(Integer orderId) {
		Order order = orderDAO.findById(orderId);
		short isHaoDuoYi = order.getIsHaoDuoYi();
		Map<String, Object> model = null;
		if(isHaoDuoYi == 1){
			model = service.getBasicOrderModel(
					ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE_HAODUOYI, orderId);
		}else{
			model = service.getBasicOrderModel(
					ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE, orderId);
		}
		
		List<Package> packages = packageDAO.findByOrderId(orderId);
		List<Produce> produceList = produceDAO.findProduceByOrderId(orderId);
		model.put("produceList", produceList);
		model.put("packages", packages);
		model.put("packageDetails",
				packageDetailDAO.findByPackageList(packages));
		return model;
	}
	

	@Override
	public Map<String, Object> getPrintWarehouseDetail(Integer orderId,
			Integer packageId) {
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
		List<Task> tasks = mainProcessService.getWarehouseTasks(ACTOR_LOGISTICS_MANAGER);
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		for (Task task : tasks) {
			Integer orderId = mainProcessService.getOrderIdInProcess(task.getProcessInstanceId());
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
	public List<Map<String, Object>> getMobileWarehouseHaoDuoYiList() {
		List<Task> tasks = mainProcessService.getWarehouseHaoduoyiTasks(ACTOR_LOGISTICS_MANAGER);
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		for (Task task : tasks) {
			Integer orderId = mainProcessService.getOrderIdInProcess(task.getProcessInstanceId());
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
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		
		Order order = orderDAO.findById(orderId);
		short isHaoDuoYi = order.getIsHaoDuoYi();
		Task task = null;
		if (isHaoDuoYi == 1) {
			task = mainProcessService.getTaskOfUserByTaskNameWithSpecificOrderId(ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE_HAODUOYI, orderId);
		} else {
			task = mainProcessService.getTaskOfUserByTaskNameWithSpecificOrderId(ACTOR_LOGISTICS_MANAGER, TASK_WAREHOUSE, orderId);
		}
		
		model.put("order", order);
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("taskId", task.getId());
		model.put(
				"orderId",
				dateFormat2.format(order.getOrderTime())
						+ String.format("%06d", order.getOrderId()));
		return model;
	}
	
	@Override
	public boolean updatePackage(int packageId, String warehouse, String shelf,
			String location) {
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
	public boolean mobileWarehouseSubmit(String taskId, Integer orderId) {
		Order order = orderDAO.findById(orderId);
		Short isHaoDuoYi = order.getIsHaoDuoYi();
		short ishaoduoyi = isHaoDuoYi.shortValue();
		boolean is_hao_duo_yi = (ishaoduoyi==1)?true:false;
		if (order != null) {
			order.setLogisticsState(2);
			if(is_hao_duo_yi){
				order.setOrderState("Done");
				order.setOrderProcessStateName("已完成");
			}
			orderDAO.attachDirty(order);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_IS_HAODUOYI, is_hao_duo_yi);
			try {
				mainProcessService.completeTask(taskId, ACTOR_LOGISTICS_MANAGER, data);
			
			//如果是好多衣，入库完则直接结束整个流程（无需发货）
			if(is_hao_duo_yi){
				order.setOrderState("Done");
				order.setOrderProcessStateName("已完成");
				orderDAO.merge(order);
			}
			return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return false;
	}

	// ===========================产品发货=================================
	@Override
	public List<Map<String, Object>> getScanClothesList() {
		return getLogisticsList(TASK_SEND_CLOTHES, 2);
	}

	@Override
	public List<Map<String, Object>> getSendClothesList() {
		return getLogisticsList(TASK_SEND_CLOTHES, 3);
	}

	@Override
	public Map<String, Object> getSendClothesDetail(Integer orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithWareHouse(
				ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES, orderId);
		List<Package> packages = packageDAO.findByOrderId(orderId);
		if (packages == null) {
			model.put("packageNumber", 0);
		} else {
			model.put("packageNumber", packages.size());
		}
		
		// 大货发货记录
		List<DeliveryRecord> deliveryRecord = deliveryRecordDAO.findProductRecordByOrderId(orderId);
		model.put("sendProductRecord", deliveryRecord);
		
		return model;
	}

	@Override
	public List<Map<String, Object>> getMobileSendClothesList() {
		List<Task> tasks = mainProcessService.getSendClothesTasks(ACTOR_LOGISTICS_MANAGER);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Task task : tasks) {
			Integer orderId = mainProcessService.getOrderIdInProcess(task.getProcessInstanceId());
			Map<String, Object> model = new HashMap<String, Object>();
			Order order = orderDAO.findById(orderId);
			if (order.getLogisticsState() == 2) {
				model.put("order", order);
//				model.put("task", task);
				model.put("taskTime", service.getTaskTime(task.getCreateTime()));
				model.put("orderId", service.getOrderId(order));
				list.add(model);
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> getMobileSendClothesDetail(Integer orderId) {
		Task task = mainProcessService.getTaskOfUserByTaskNameWithSpecificOrderId(ACTOR_LOGISTICS_MANAGER, TASK_SEND_CLOTHES, orderId);
		Map<String, Object> model = new HashMap<String, Object>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("packs", packageDAO.findByOrderId(orderId));
		model.put("taskId", task.getId());
		model.put(
				"orderId",
				dateFormat2.format(order.getOrderTime())
						+ String.format("%06d", order.getOrderId()));
		return model;
	}

	@Override
	public boolean mobileSendClothesSubmit(int orderId) {
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
	public boolean sendClothesSubmit(Integer orderId, String taskId, float price,
			String name, String time, String number, String remark, String isFinal) {
		//更新物流信息
		Logistics logistics = logisticsDAO.findById(orderId);
		logistics.setProductClothesNumber(number);//快递单号
		logistics.setProductClothesRemark(remark);//备注
		logistics.setProductClothesPrice(price + "");//快递价格
		logistics.setProductClothesType(name);//快递名称
		logistics.setProductClothesTime(getTime(time));//邮寄时间
		logisticsDAO.attachDirty(logistics);
		
		// 需要记录每次大货发货的信息
		DeliveryRecord deliveryRecord = new DeliveryRecord();
		deliveryRecord.setOrderId(orderId);
		deliveryRecord.setSendType("product");// 发货类型为“大货”
		deliveryRecord.setRecipientName(logistics.getSampleClothesName());// 收件人姓名
		deliveryRecord.setRecipientPhone(logistics.getSampleClothesPhone());// 收件人手机
		deliveryRecord.setRecipientAddr(logistics.getSampleClothesAddress());// 收件人地址
		deliveryRecord.setExpressName(name);// 快递名称
		deliveryRecord.setExpressNumber(number);// 快递单号
		deliveryRecord.setExpressPrice(price + "");// 快递价格
		deliveryRecord.setSendTime(getTime(time));// 邮寄时间
		deliveryRecord.setRemark(remark);// 备注
		deliveryRecordDAO.save(deliveryRecord);
		
		//如果不是最终的样衣发货，不执行completeTask方法，直接返回
		if(isFinal.equals("false")){
			return false;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
			try {
				mainProcessService.completeTask(taskId, ACTOR_LOGISTICS_MANAGER, data);
			/*
			 * whh:设置orderstate字段表示order结束状态
			 */
			Order order = orderDAO.findById(orderId);
			order.setOrderState("Done");
			order.setOrderProcessStateName("已完成");
			orderDAO.merge(order);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
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
		Date outDate = DateUtil.parse(time, DateUtil.haveSecondFormat);
		return new Timestamp(outDate.getTime());
	}

	@Override
	public List<Package> getPackageListByOrderId(int orderId) {
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
	private FMCProcessService mainProcessService;
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
	@Autowired
	private MoneyDAO moneyDAO;

	public final static String ACTOR_LOGISTICS_MANAGER = "logisticsManager";
	
	public final static String TASK_RECEIVE_SAMPLE = "receiveSample";
	public final static String TASK_SEND_SAMPLE = "sendSample";
	public final static String TASK_WAREHOUSE = "warehouse";
	public final static String TASK_WAREHOUSE_HAODUOYI = "warehouse_haoduoyi";
	public final static String TASK_SEND_CLOTHES = "sendClothes";
	
	public final static String RESULT_RECEIVE_SAMPLE = "receiveSample";
	public final static String RESULT_SEND_SAMPLE = "sendSample";
	public final static String RESULT_TAKE_SAMPLE_Money = "takeSampleMoney";
    public final static String RESULT_IS_HAODUOYI = "isHaoDuoYi";
	@Override
	public Logistics findByOrderId(String s_id) {
		return logisticsDAO.findById(Integer.parseInt(s_id));
	}




}
