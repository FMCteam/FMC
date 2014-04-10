package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.PackageDAO;
import nju.software.dao.impl.PackageDetailDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.PackageDetail;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.model.OrderInfo;
import nju.software.model.QuoteConfirmTaskSummary;
import nju.software.model.SampleProduceTask;
import nju.software.model.SampleProduceTaskSummary;
import nju.software.service.ProduceService;
import nju.software.util.JbpmAPIUtil;

@Service("produceServiceImpl")
public class ProduceServiceImpl implements ProduceService {

	public final static String ACTOR_PRODUCE_MANAGER = "produceManager";
	public final static String TASK_VERIFY_PRODUCE = "verifyProduce";
	public final static String TASK_COMPUTE_PRODUCE_COST = "computeProduceCost";
	public final static String TASK_PRODUCE_SAMPLE = "produceSample";
	public final static String TASK_PRODUCE = "produce";

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private QuoteDAO QuoteDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PackageDAO packageDAO;
	@Autowired
	private PackageDetailDAO packageDetailDAO;

	@Override
	public boolean verifyProduceSubmit(Account account, int orderId, long taskId,
			long processId, boolean productVal, String comment) {
		// TODO Auto-generated method stub
		// String actorId = account.getUserRole();
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			Order order = orderDAO.findById(orderId);
			// 修改order内容

			// 提交修改
			orderDAO.attachDirty(order);

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("productVal", productVal);
			data.put("productComment", comment);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
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



	
	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public boolean costAccounting(Account account, int orderId, long taskId,
			long processId, float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost) {
		// TODO Auto-generated method stub

		String actorId = "SHENGCHANZHUGUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			Quote quote = QuoteDAO.findById(orderId);

			if (quote == null) {
				quote = new Quote();
				quote.setOrderId(orderId);
				quote.setCutCost(cut_cost);
				quote.setManageCost(manage_cost);
				quote.setSwingCost(swing_cost);
				quote.setIroningCost(ironing_cost);
				quote.setNailCost(nali_cost);
				quote.setPackageCost(package_cost);
				quote.setOtherCost(other_cost);
				QuoteDAO.save(quote);
			} else {
				quote.setCutCost(cut_cost);
				quote.setManageCost(manage_cost);
				quote.setSwingCost(swing_cost);
				quote.setIroningCost(ironing_cost);
				quote.setNailCost(nali_cost);
				quote.setPackageCost(package_cost);
				quote.setOtherCost(other_cost);
				QuoteDAO.attachDirty(quote);
			}
			// 修改QUote内容

			QuoteDAO.attachDirty(quote);

			float producecost = cut_cost + manage_cost + swing_cost
					+ ironing_cost + nali_cost + package_cost + other_cost;
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			// data.put("designVal", designVal);
			data.put("producecost", producecost);
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
	
	
	//=========================样衣生产========================
	@Override
	public List<OrderInfo> getProduceSampleList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_PRODUCE_MANAGER, TASK_PRODUCE_SAMPLE);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil.getVariable(task,"orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	@Override
	public OrderInfo getProduceSampleDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PRODUCE_MANAGER,
				TASK_PRODUCE_SAMPLE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	
	@Override
	public boolean produceSampleSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("producterror", result.equals("0"));
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	

	//=======================批量生产========================
	@Override
	public List<OrderInfo> getProduceList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_PRODUCE_MANAGER, TASK_PRODUCE);
		List<OrderInfo> list = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) jbpmAPIUtil.getVariable(task,"orderId");
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(orderDAO.findById(orderId));
			orderInfo.setTask(task);
			list.add(orderInfo);
		}
		return list;
	}

	
	@Override
	public OrderInfo getProduceDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PRODUCE_MANAGER,
				TASK_PRODUCE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}
	
	
	@Override
	public boolean pruduceSubmit(String[] pid, String[] askAmount, long taskId) {
		// TODO Auto-generated method stub
		for (int i = 0; i < pid.length; i++) {
			Product product = productDAO.findById(Integer.parseInt(pid[i]));
			product.setAskAmount(Integer.parseInt(askAmount[i]));
			productDAO.attachDirty(product);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("volumeproduction", true);
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public List<Product> getProductByOrderId(int parseInt) {
		// TODO Auto-generated method stub
		List<Product> productList = productDAO.findByProperty("orderId",
				parseInt);
		return productList;
	}

	@Override
	public List<nju.software.dataobject.Package> getPackageByOrderId(
			int parseInt) {
		// TODO Auto-generated method stub
		List<nju.software.dataobject.Package> packageList = packageDAO
				.findByProperty("orderId", parseInt);
		return packageList;
	}

	@Override
	public List<List<PackageDetail>> getProductDetailByPackage(
			List<nju.software.dataobject.Package> packageList) {
		// TODO Auto-generated method stub
		List<List<PackageDetail>> detail = new ArrayList<List<PackageDetail>>();
		for (nju.software.dataobject.Package p : packageList) {

			int packageId = p.getPackageId();
			List<PackageDetail> l1 = packageDetailDAO.findByProperty(
					"packageId", packageId);
			detail.add(l1);

		}
		return detail;

	}
	
	


	@Override
	public void savePackageDetail(int orderId, String[] array_amount,
			String[] array_color, String[] array_name, Timestamp entryTime) {
		// TODO Auto-generated method stub
		// 保存package
		nju.software.dataobject.Package p1 = new nju.software.dataobject.Package();
		p1.setOrderId(orderId);
		p1.setPackageTime(entryTime);
		packageDAO.save(p1);
		// 保持packegedetail
		for (int i = 0; i < array_amount.length; i++) {

			PackageDetail pd1 = new PackageDetail();
			pd1.setClothesAmount(Integer.parseInt(array_amount[i]));
			pd1.setClothesStyleColor(array_color[i]);
			pd1.setClothesStyleName(array_name[i]);
			pd1.setPackageId(p1.getPackageId());
			packageDetailDAO.save(pd1);
		}

	}

	
	@Override
	public List<OrderInfo> getComputeProduceCostList() {
		
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_PRODUCE_MANAGER, TASK_COMPUTE_PRODUCE_COST );
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
	public OrderInfo getComputeProduceCostInfo(Integer orderId) {
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PRODUCE_MANAGER,
				TASK_COMPUTE_PRODUCE_COST, orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setLogistics(logisticsDAO.findById(orderId));
		model.setFabrics(fabricDAO.findByOrderId(orderId));
		model.setAccessorys(accessoryDAO.findByOrderId(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public void ComputeProduceCostSubmit(int orderId,
			long taskId,float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost, float design_cost) {
		
		Quote quote = QuoteDAO.findById(orderId);

		if (quote == null) {
			quote = new Quote();
			quote.setOrderId(orderId);
			quote.setCutCost(cut_cost);
			quote.setManageCost(manage_cost);
			quote.setSwingCost(swing_cost);
			quote.setIroningCost(ironing_cost);
			quote.setNailCost(nali_cost);
			quote.setPackageCost(package_cost);
			quote.setOtherCost(other_cost);
			quote.setDesignCost(design_cost);
			QuoteDAO.save(quote);
		} else {
			quote.setCutCost(cut_cost);
			quote.setManageCost(manage_cost);
			quote.setSwingCost(swing_cost);
			quote.setIroningCost(ironing_cost);
			quote.setNailCost(nali_cost);
			quote.setPackageCost(package_cost);
			quote.setOtherCost(other_cost);
			quote.setDesignCost(design_cost);
			QuoteDAO.attachDirty(quote);
		}
		
		  float producecost = cut_cost + manage_cost + swing_cost
				+ ironing_cost + nali_cost + package_cost + other_cost + design_cost;
		
				Map<String, Object> data = new HashMap<String, Object>();
				try {
					data.put("volumeproduction", true);
					jbpmAPIUtil.completeTask(taskId, data, ACTOR_PRODUCE_MANAGER);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public List<OrderInfo> getVerifyProduceList() {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_PRODUCE_MANAGER, TASK_VERIFY_PRODUCE);
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
	public OrderInfo getVerifyProduceDetail(int orderId, long taskId) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_PRODUCE_MANAGER, TASK_VERIFY_PRODUCE);
		for (TaskSummary task : list) {
			WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
					.getKsession().getProcessInstance(task.getProcessInstanceId());
			int orderId_process = (int) process.getVariable("orderId");
			if (orderId == orderId_process && taskId == task.getId() ) {
				OrderInfo oi = new OrderInfo();
				oi.setOrder(orderDAO.findById(orderId));
				oi.setLogistics(logisticsDAO.findById(orderId));
				oi.setFabrics(fabricDAO.findByOrderId(orderId));
				oi.setAccessorys(accessoryDAO.findByOrderId(orderId));
				oi.setTask(task);
				return oi;
			}
		}
		return null;
	}

}
