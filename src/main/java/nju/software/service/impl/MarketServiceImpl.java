package nju.software.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.DesignCadDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.FabricCostDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dao.impl.VersionDataDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.DesignCad;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderInfo;
import nju.software.service.MarketService;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;

@Service("marketServiceImpl")
public class MarketServiceImpl implements MarketService {

	public final static String ACTOR_MARKET_MANAGER = "marketManager";
	public final static String ACTOR_MARKET_STAFF = "marketStaff";
	public final static String TASK_MODIFY_ORDER = "modifyOrder";
	public final static String TASK_MERGE_QUOTE = "mergeQuote";
	public final static String TASK_VERIFY_QUOTE = "verifyQuote";
	public final static String TASK_CONFIRM_QUOTE = "confirmQuote";
	public final static String TASK_MODIFY_QUOTE = "modifyQuote";
	public final static String TASK_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder";
	public final static String TASK_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String TASK_SIGN_CONTRACT = "signContract";
	public final static String RESULT_REORDER = "reorder";
	public final static String RESULT_MODIFY_ORDER = "modifyOrder";
	public final static String RESULT_QUOTE = "quote";
	public final static String RESULT_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder";
	public final static String RESULT_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String UPLOAD_DIR_SAMPLE = "D:/fmc/sample/";
	public final static String UPLOAD_DIR_REFERENCE = "D:/fmc/reference/";

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private QuoteDAO quoteDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private DesignCadDAO cadDAO;
	@Autowired
	private ProduceDAO produceDAO;
	@Autowired
	private VersionDataDAO versionDataDAO;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private ServiceUtil service;

	@Override
	public List<Customer> getAddOrderList() {
		// TODO Auto-generated method stub
		return customerDAO.findAll();
	}

	@Override
	public Customer getAddOrderDetail(Integer cid) {
		// TODO Auto-generated method stub
		return customerDAO.findById(cid);
	}

	@Override
	public boolean addOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		// 添加订单信息
		orderDAO.save(order);

		Integer orderId = order.getOrderId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		if (!multipartRequest.getFile("sample_clothes_picture").isEmpty()) {
			String filedir = request.getSession().getServletContext()
					.getRealPath("/upload/sampleClothesPicture/" + orderId);
			File file = FileOperateUtil.Upload(request, UPLOAD_DIR_SAMPLE
					+ orderId, "1", "sample_clothes_picture");
			order.setSampleClothesPicture(file.getAbsolutePath());
		}
		if (!multipartRequest.getFile("reference_picture").isEmpty()) {
			String filedir = request.getSession().getServletContext()
					.getRealPath("/upload/reference_picture/" + orderId);
			File file = FileOperateUtil.Upload(request, UPLOAD_DIR_REFERENCE
					+ orderId, "1", "reference_picture");
			order.setReferencePicture(file.getAbsolutePath());
		}

		orderDAO.attachDirty(order);

		// 添加面料信息
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}

		// 添加辅料信息
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}

		// 添加大货加工单信息
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加样衣加工单信息
		for (Produce produce : sample_produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}

		// 添加版型信息
		for (VersionData versionData : versions) {
			versionData.setOrderId(orderId);
			versionDataDAO.save(versionData);
		}

		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.save(logistics);

		// cad
		cad.setOrderId(orderId);
		cadDAO.save(cad);
		// 启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("marketStaff", order.getEmployeeId());
		params.put(LogisticsServiceImpl.RESULT_RECEIVE_SAMPLE,
				(int) order.getHasPostedSampleClothes());
		params.put(LogisticsServiceImpl.RESULT_SEND_SAMPLE,
				(int) order.getIsNeedSampleClothes());
		params.put(RESULT_REORDER, false);
		long processId=doTMWorkFlowStart(params);
		order.setProcessId(processId);
		orderDAO.attachDirty(order);
		return true;
	}

	/**
	 * 启动流程
	 */
	private long doTMWorkFlowStart(Map<String, Object> params) {
		try {
			jbpmAPIUtil.setParams(params);
			ProcessInstance instance = jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程instance"+instance.getId()+"启动成功！");
			return instance.getId();
		} catch (Exception ex) {
			System.out.println("流程启动失败");
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> getModifyOrderList(Integer accountId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(accountId + "",
				TASK_MODIFY_ORDER);
		return temp;
	}

	@Override
	public Map<String, Object> getModifyOrderDetail(int accountId, int id) {
		// TODO Auto-generated method stub
		return service
				.getBasicOrderModel(accountId + "", TASK_MODIFY_ORDER, id);

	}

	@Override
	public void modifyOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, List<Produce> sample_produces,
			List<VersionData> versions, DesignCad cad, boolean editok,
			long taskId, Integer accountId) {
		// TODO Auto-generated method stub
		// 添加订单信息
		orderDAO.merge(order);
		Integer orderId = order.getOrderId();
		// 添加面料信息
		fabricDAO.deleteByProperty("orderId", orderId);
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(orderId);
			fabricDAO.save(fabric);
		}
		// 添加辅料信息
		accessoryDAO.deleteByProperty("orderId", orderId);
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(orderId);
			accessoryDAO.save(accessory);
		}
		// 添加大货加工单信息
		produceDAO.deleteProduceByProperty("oid", orderId);
		for (Produce produce : produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}
		// 添加样衣加工单信息
		produceDAO.deleteSampleProduceByProperty("oid", orderId);
		for (Produce produce : sample_produces) {
			produce.setOid(orderId);
			produceDAO.save(produce);
		}
		// 添加版型数据
		versionDataDAO.deleteByProperty("orderId", orderId);
		for (VersionData version : versions) {
			version.setOrderId(orderId);
			versionDataDAO.save(version);
		}
		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.merge(logistics);

		// cad
		cadDAO.merge(cad);

		// 启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RESULT_MODIFY_ORDER, editok);
		try {
			jbpmAPIUtil.completeTask(taskId, params, accountId + "");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public List<Product> getProductList(int orderId, String productAskAmount,
			String productColor, String productStyle) {
		// TODO Auto-generated method stub

		String[] amountList = productAskAmount.split(",");
		String[] colorList = productColor.split(",");
		String[] styleList = productStyle.split(",");
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < amountList.length; i++) {
			Product product = new Product();
			product.setOrderId(orderId);
			product.setAskAmount(Integer.parseInt(amountList[i]));
			product.setColor(colorList[i]);
			product.setStyle(styleList[i]);
			product.setProduceAmount(0);
			product.setQualifiedAmount(0);
			productList.add(product);
		}

		return productList;
	}

	@Override
	public boolean confirmProduceOrderSubmit(String actorId, int orderId,
			long taskId, long processId, boolean comfirmworksheet,
			List<Produce> productList) {
		// TODO Auto-generated method stub
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");

		if (orderId == orderId_process) {
			// 如果通过，创建合同加工单
			produceDAO.deleteProduceByProperty("oid", orderId);
			if (comfirmworksheet) {
				for (Produce produce : productList) {
					produce.setOid(orderId);
					produceDAO.save(produce);
				}
			}
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_CONFIRM_PRODUCE_ORDER, comfirmworksheet);
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
	public List<OrderInfo> getOrderInfoList(Integer employeeId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "sign_contract");
		List<OrderInfo> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(employeeId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				OrderInfo summary = new OrderInfo(orderDAO.findById(orderId),
						task.getId());
				taskSummarys.add(summary);
			}
		}
		return taskSummarys;
	}

	@Override
	public OrderInfo getOrderInfo(Integer orderId, long taskId) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		OrderInfo orderInfo = new OrderInfo(order, taskId);
		return orderInfo;
	}

	@Override
	public List<Map<String, Object>> getModifyProductList(Integer userId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(userId + "",
				TASK_MODIFY_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public boolean modifyProductSubmit(String userId, int id, long taskId,
			long processId, boolean editworksheetok, List<Produce> productList) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				userId, TASK_MODIFY_PRODUCE_ORDER);
		for (TaskSummary task : tasks) {
			if (task.getId() == taskId
					&& getVariable("orderId", task).equals(id)) {
				produceDAO.deleteProduceByProperty("oid", id);
				if (editworksheetok) {
					for (Produce produce : productList) {
						produce.setOid(id);
						produceDAO.save(produce);
					}
				}
				// 修改流程参数
				Map<String, Object> data = new HashMap<>();
				data.put(RESULT_MODIFY_PRODUCE_ORDER, editworksheetok);
				// 直接进入到下一个流程时
				try {
					jbpmAPIUtil.completeTask(taskId, data, userId);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	// ==========================报价商定=======================
	@Override
	public List<Map<String, Object>> getConfirmQuoteList(String actorId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_CONFIRM_QUOTE);
		return temp;
	}

	@Override
	public OrderInfo getConfirmQuoteDetail(String arctorId, Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(arctorId, TASK_CONFIRM_QUOTE,
				orderId);
		OrderInfo model = new OrderInfo();
		model.setOrder(orderDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public boolean confirmQuoteSubmit(String actorId, long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();

		data.put(RESULT_QUOTE, Integer.parseInt(result));
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> getModifyQuoteList(Integer userId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(userId + "",
				TASK_MODIFY_QUOTE);
		return temp;
	}

	public Map<String, Object> getModifyQuoteDetail(int orderId, int accountId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(accountId + "",
				TASK_MODIFY_QUOTE, orderId);
	}

	@Override
	public Map<String, Object> getModifyProductDetail(int orderId,
			Integer accountId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModel(accountId + "",
				TASK_MODIFY_PRODUCE_ORDER, orderId);
	}

	// ==========================签订合同=======================
	@Override
	public List<Map<String, Object>> getSignContractList(String actorId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_SIGN_CONTRACT);
		return temp;
	}

	@Override
	public Map<String, Object> getSignContractDetail(String actorId,
			Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(actorId, TASK_SIGN_CONTRACT,
				orderId);

	}

	@Override
	public List<Map<String, Object>> getMergeQuoteList(Integer accountId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(accountId + "",
				TASK_MERGE_QUOTE);
		return temp;
	}

	@Override
	public void mergeQuoteSubmit(int accountId, Quote q, int id, long taskId,
			long processId) {
		// TODO Auto-generated method stub
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		if (id == orderId_process) {
			Map<String, Object> data = new HashMap<>();
			quoteDAO.merge(q);
			try {
				jbpmAPIUtil.completeTask(taskId, null, accountId + "");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Map<String, Object>> getVerifyQuoteList(Integer accountId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(
				ACTOR_MARKET_MANAGER, TASK_VERIFY_QUOTE);
		return temp;
	}

	@Override
	public void verifyQuoteSubmit(Quote q, int id, long taskId, long processId) {
		// TODO Auto-generated method stub
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		if (id == orderId_process) {
			quoteDAO.merge(q);
			Map<String, Object> data = new HashMap<>();
			try {
				jbpmAPIUtil.completeTask(taskId, null, ACTOR_MARKET_MANAGER);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean signContractSubmit(String actorId, long taskId, int orderId,
			double discount, double total) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		order.setTotalMoney(total);
		orderDAO.merge(order);
		Map<String, Object> data = new HashMap<>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> getMergeQuoteDetail(Integer userId, int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(userId + "",
				TASK_MERGE_QUOTE, orderId);
	}

	@Override
	public Map<String, Object> getVerifyQuoteDetail(Integer userId, int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_MARKET_MANAGER,
				TASK_VERIFY_QUOTE, orderId);
	}

	@Override
	public Map<String, Object> getConfirmQuoteDetail(Integer userId, int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(userId + "",
				TASK_CONFIRM_QUOTE, orderId);
	}

	@Override
	public void modifyQuoteSubmit(Quote q, int id, long taskId, long processId,
			Integer userId) {
		// TODO Auto-generated method stub
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		if (id == orderId_process) {
			quoteDAO.merge(q);
			Map<String, Object> data = new HashMap<>();
			try {
				jbpmAPIUtil.completeTask(taskId, null, userId + "");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Map<String, Object>> getConfirmProductList(String actorId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(actorId,
				TASK_CONFIRM_PRODUCE_ORDER);
		return temp;
	}

	@Override
	public Map<String, Object> getConfirmProductDetail(Integer userId,
			int orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(userId + "",
				TASK_CONFIRM_PRODUCE_ORDER, orderId);
	}

	@Override
	public List<Map<String, Object>> getOrderList(Integer employeeId) {
		// TODO Auto-generated method stub
		//List<Order> orders = orderDAO.findByEmployeeId(employeeId);
		List<Order> orders = orderDAO.findAll();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			list.add(model);
		}
		return list;
	}

	@Override
	public Map<String, Object> getOrderDetail(Integer orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("orderId", service.getOrderId(order));
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));

		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));

		produce.setType(Produce.TYPE_PRODUCE);
		model.put("produce", produceDAO.findByExample(produce));

		model.put("versions", versionDataDAO.findByOrderId(orderId));

		Quote quote = quoteDAO.findById(orderId);

		model.put("quote", quote);
		List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(orderId);
		model.put("fabricCosts", fabricCosts);
		List<AccessoryCost> accessoryCosts = accessoryCostDAO
				.findByOrderId(orderId);
		model.put("accessoryCosts", accessoryCosts);
		return model;
	}

	@Override
	public List<Map<String, Object>> getAddMoreOrderList(int customerId) {
		// TODO Auto-generated method stub
		Order o = new Order();
		o.setCustomerId(customerId);
		List<Order> orderList = orderDAO.findByExample(o);
		List<Map<String, Object>> list = new ArrayList<>();
		for(Order order: orderList){
			Integer orderId = order.getOrderId();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("taskTime", order.getOrderTime());
			model.put("orderId", orderId);
			list.add(model);
		}
		return list;
	}

	@Override
	public Map<String, Object> getAddMoreOrderDetail(int orderId) {
		// TODO Auto-generated method stub
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = orderDAO.findById(orderId);
		model.put("order", order);
		model.put("employee", employeeDAO.findById(order.getEmployeeId()));
		model.put("logistics", logisticsDAO.findById(orderId));
		model.put("fabrics", fabricDAO.findByOrderId(orderId));
		model.put("accessorys", accessoryDAO.findByOrderId(orderId));
		model.put("designCad", cadDAO.findByOrderId(orderId));
		model.put("orderId", orderId);

		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_SAMPLE_PRODUCE);
		model.put("sample", produceDAO.findByExample(produce));

		produce.setType(Produce.TYPE_PRODUCE);
		model.put("produce", produceDAO.findByExample(produce));

		model.put("versions", versionDataDAO.findByOrderId(orderId));
		return model;
	}

	/*
	 * @Override public List<QuoteConfirmTaskSummary>
	 * getQuoteModifyTaskSummaryList( Integer employeeId) { // TODO
	 * Auto-generated method stub List<TaskSummary> tasks =
	 * jbpmAPIUtil.getAssignedTasksByTaskname( "SHICHANGZHUANYUAN",
	 * "edit_quoteorder"); List<QuoteConfirmTaskSummary> taskSummarys = new
	 * ArrayList<>(); for (TaskSummary task : tasks) { if
	 * (getVariable("employeeId", task).equals(employeeId)) { Integer orderId =
	 * (Integer) getVariable("orderId", task); QuoteConfirmTaskSummary summary =
	 * QuoteConfirmTaskSummary .getInstance(orderDAO.findById(orderId), (Quote)
	 * quoteDAO.findById(orderId), task.getId()); taskSummarys.add(summary); } }
	 * return taskSummarys; }
	 */

	/*
	 * @Override public void completeQuoteConfirmTaskSummary(long taskId, String
	 * result) { // TODO Auto-generated method stub Map<String, Object> data =
	 * new HashMap<String, Object>(); if (result.equals("1")) {
	 * data.put("confirmquote", true); data.put("eidtquote", false);
	 * data.put("samplejin", true); } if (result.equals("2")) {
	 * data.put("confirmquote", false); data.put("eidtquote", true);
	 * data.put("samplejin", true); } if (result.equals("3")) {
	 * data.put("confirmquote", false); data.put("eidtquote", false);
	 * data.put("samplejin", true); } try { jbpmAPIUtil.completeTask(taskId,
	 * data, "SHICHANGZHUANYUAN"); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	/*
	 * @Override public void completeSignContract(Integer orderId, double
	 * discount, long taskId) { // TODO Auto-generated method stub Order order =
	 * orderDAO.findById(orderId); order.setDiscount(discount);
	 * orderDAO.attachDirty(order);
	 * 
	 * Map<String, Object> data = new HashMap<>(); try {
	 * jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN"); } catch
	 * (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
