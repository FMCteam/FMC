package nju.software.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.drools.command.Context;
import org.drools.command.impl.GenericCommand;
import org.drools.command.impl.KnowledgeCommandContext;
import org.drools.core.util.StringUtils;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.NodeInstance;
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
import nju.software.dao.impl.MoneyDAO;
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
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.dataobject.VersionData;
import nju.software.model.OrderInfo;
import nju.software.service.FinanceService;
import nju.software.service.MarketService;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;
import nju.software.util.mail.MailSenderInfo;
import nju.software.util.mail.SimpleMailSender;

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
	public final static String TASK_PUSH_REST = "pushRest";
	public final static String RESULT_REORDER = "reorder";
	public final static String RESULT_MODIFY_ORDER = "modifyOrder";
	public final static String RESULT_QUOTE = "quote";
	public final static String RESULT_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder"; 
	public final static String RESULT_IS_HAODUOYI = "isHaoDuoYi";
	public final static String RESULT_CONFIRM_PRODUCE_ORDER_CONTRACT="confirmProduceOrderContract";
	public final static String RESULT_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String RESULT_PUSH_RESTMONEY = "pushRestMoney";
	public final static String UPLOAD_DIR_SAMPLE = "C:/fmc/sample/";
	public final static String UPLOAD_DIR_REFERENCE = "C:/fmc/reference/";
	public final static String RESULT_VERIFY_QUOTE = "verifyQuoteSuccess";
	public final static String VERIFY_QUOTE_COMMENT = "verifyQuoteComment";
	

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
	private FinanceService financeService;
	@Autowired
	private VersionDataDAO versionDataDAO;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricCostDAO fabricCostDAO;
	@Autowired
	private AccessoryCostDAO accessoryCostDAO;
	@Autowired
	private MoneyDAO moneyDAO;
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

	@Override
	public void addMoreOrderSubmit(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics,
			List<Produce> produces, 
			List<VersionData> versions, DesignCad cad,
			HttpServletRequest request){
		// 添加订单信息
				orderDAO.save(order);

				Integer orderId = order.getOrderId();

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
//				for (Produce produce : sample_produces) {
//					produce.setOid(orderId);
//					produceDAO.save(produce);
//				}

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
				
				//报价
				String sourceId = request.getParameter("sourceId");
				System.out.println("sourceId:-------->"+sourceId);
				System.out.println("orderId:-------->"+orderId);
				Integer source = Integer.parseInt(sourceId);
				
				Quote quote = quoteDAO.findById(source);
				try {
					Quote newQuote = (Quote)copy(quote);
					newQuote.setOrderId(orderId);
					quoteDAO.save(newQuote);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<FabricCost> fabricCosts = fabricCostDAO.findByOrderId(source);
				for(FabricCost fc : fabricCosts){
					FabricCost newFC = new FabricCost();
					newFC.setCostPerMeter(fc.getCostPerMeter());
					newFC.setFabricName(fc.getFabricName());
					newFC.setOrderId(orderId);
					newFC.setPrice(fc.getPrice());
					newFC.setTearPerMeter(fc.getTearPerMeter());
					fabricCostDAO.save(newFC);
				}
				List<AccessoryCost> accessoryCosts = accessoryCostDAO.findByOrderId(source);
				for(AccessoryCost ac : accessoryCosts){
					AccessoryCost newAC = new AccessoryCost();
					newAC.setAccessoryName(ac.getAccessoryName());
					newAC.setCostPerPiece(ac.getCostPerPiece());
					newAC.setOrderId(orderId);
					newAC.setPrice(ac.getPrice());
					newAC.setTearPerPiece(ac.getTearPerPiece());
					accessoryCostDAO.save(newAC);
				}
				
				//图片
				Order sourceOrder = orderDAO.findById(source);
				File newSamplePic = FileOperateUtil.CopyAndPaste(sourceOrder.getSampleClothesPicture(), UPLOAD_DIR_SAMPLE + orderId);
				File newRefPic = FileOperateUtil.CopyAndPaste(sourceOrder.getReferencePicture(), UPLOAD_DIR_REFERENCE + orderId);
				order.setSampleClothesPicture(newSamplePic.getAbsolutePath());
				order.setReferencePicture(newRefPic.getAbsolutePath());
				
				// 启动流程
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderId);
				params.put("marketStaff", order.getEmployeeId());
				params.put(LogisticsServiceImpl.RESULT_RECEIVE_SAMPLE,
						(int) order.getHasPostedSampleClothes());
				params.put(LogisticsServiceImpl.RESULT_SEND_SAMPLE,
						(int) order.getIsNeedSampleClothes());
				params.put(RESULT_REORDER, true);
				long processId=doTMWorkFlowStart(params);
				order.setProcessId(processId);
				orderDAO.attachDirty(order);
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
	public List<Map<String, Object>> getSearchModifyOrderList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate,String enddate, Integer[] employeeIds) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getSearchOrderList(userId + "",
				 ordernumber,  customername,  stylename,
				 startdate,enddate,  employeeIds,
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
			if(editok==false){//如果editok的的值为false，即为未收取到样衣，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.merge(order);
			}			
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
			int ask_amount = 0;
			produceDAO.deleteProduceByProperty("oid", orderId);
			if (comfirmworksheet) {
				for (Produce produce : productList) {
					produce.setOid(orderId);
					produceDAO.save(produce);
					ask_amount+=produce.getProduceAmount();
				}
				Order order = orderDAO.findById(orderId);
				order.setAskAmount(ask_amount);
				orderDAO.merge(order);
			}
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_CONFIRM_PRODUCE_ORDER_CONTRACT, comfirmworksheet);
			// 直接进入到下一个流程时
			try {
				jbpmAPIUtil.completeTask(taskId, data, actorId);
				if(comfirmworksheet==false){//如果result的的值为false，即为确认加工单并签订合同失败，流程会异常终止，将orderState设置为1
					Order order = orderDAO.findById(orderId);
					order.setOrderState("1");
					orderDAO.merge(order);
 				}
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
	public List<Map<String, Object>> getSearchModifyProductList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(userId + "", ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
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
	public List<Map<String, Object>> getSearchConfirmQuoteList(String string,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(string,
				 ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
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
	public boolean confirmQuoteSubmit(String actorId, long taskId, int orderId,
			String result, String url) {
		//result为0，表示有上传样衣制作金的截图
		//result为1，表示修改报价
		//result为2，表示取消订单
		Order order = orderDAO.findById(orderId);
		if (Integer.parseInt(result) == 0) {
			order.setConfirmSampleMoneyFile(url);
			orderDAO.attachDirty(order);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_QUOTE, Integer.parseInt(result));
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
			if(result.equals("2")){//如果result的的值为1，即为未收取到样衣，流程会异常终止，将orderState设置为1
				order.setOrderState("1");
				orderDAO.attachDirty(order);
			}			
			return true;
		} catch (InterruptedException e) {
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

	@Override
	public List<Map<String, Object>> getSearchModifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(userId + "",
				 ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
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
		return service.getBasicOrderModelWithQuote(accountId + "",
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
	public List<Map<String, Object>> getSearchSignContractList(String actorId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(actorId,
				 ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
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

	// ==========================取得催尾款列表=======================
	@Override
	public List<Map<String, Object>> getPushRestOrderList(String userId) {
		List<Map<String, Object>> model = service.getOrderList(userId,
				TASK_PUSH_REST);
		return model;
	}
	
	@Override
	public List<Map<String, Object>> getSearchPushRestOrderList(String userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(userId  ,
				 ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
				 TASK_PUSH_REST);
		return temp;
	}
	
	// ==========================取得催尾款订单=======================
	@Override
	public Map<String, Object> getPushRestOrderDetail(String userId, int orderId) {
		Map<String, Object> model = service.getBasicOrderModelWithQuote(userId,
				TASK_PUSH_REST, orderId);
		Order order = (Order) model.get("order");
		Quote quote = (Quote) model.get("quote");
		Float price = quote.getOuterPrice();
		model.put("price", price);
		Produce p=new Produce();
		p.setOid(orderId);
		p.setType(Produce.TYPE_QUALIFIED);
		List<Produce>list=produceDAO.findByExample(p);
		Integer amount=0;
		for(Produce produce:list){
			amount+=produce.getProduceAmount();
		}
		model.put("number", amount);
		model.put("total",  order.getTotalMoney() * 0.7);
		model.put("taskName", "催尾款");
		model.put("tabName", "大货尾款");
		model.put("type", "大货尾款");
		model.put("url", "/market/getPushRestOrderSubmit.do");

// 		model.put("moneyName", "70%尾款");
		model.put("moneyName", "大货尾款");
		Money money=new Money();
		money.setOrderId(orderId);
		money.setMoneyType("大货定金");
		model.put("deposit", moneyDAO.findByExample(money).get(0).getMoneyAmount()); 
		Float samplePrice = (float) 0;
		if (order.getStyleSeason().equals("春夏")) {
			samplePrice = (float) 200;
			model.put("samplePrice", samplePrice);
		} else {
			samplePrice = (float) 400;
			model.put("samplePrice", samplePrice);
		}
		return model;
	}
	@Override
	public boolean getPushRestOrderSubmit(String actorId, long taskId,
			boolean result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		data.put(RESULT_PUSH_RESTMONEY, result);
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
	public List<Map<String, Object>> getMergeQuoteList(Integer accountId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getOrderList(accountId + "",
				TASK_MERGE_QUOTE);
		return temp;
	}

	@Override
	public List<Map<String, Object>> getSearchMergeQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> temp = service.getSearchOrderList(userId + "",
				 ordernumber,  customername,  stylename,
				 startdate,  enddate,  employeeIds,
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
		Order order = orderDAO.findById(id);
//		String orderSource = order.getOrderSource();
		//
		Short isHaoduoyi = order.getIsHaoDuoYi();
		short ishaoduoyi = isHaoduoyi.shortValue();
		boolean isHaoDuoYi2 =false;
		if(ishaoduoyi==1)
			isHaoDuoYi2 = true;
		
//		boolean isHaoDuoYi = (orderSource.equals("好多衣"))?true:false;
		if (id == orderId_process) {
			Map<String, Object> data = new HashMap<>();			
		    data.put(RESULT_IS_HAODUOYI, isHaoDuoYi2);
			quoteDAO.merge(q);
			try {
				jbpmAPIUtil.completeTask(taskId, data, accountId + "");
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
	public List<Map<String, Object>> getSearchVerifyQuoteList(Integer userId,
			String ordernumber, String customername, String stylename,
			String startdate, String enddate, Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(
				ACTOR_MARKET_MANAGER,  ordernumber,  customername,  stylename,
				 startdate,  enddate,employeeIds,TASK_VERIFY_QUOTE);
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

	@Override
	public void verifyQuoteSubmit(Quote quote, int id, long taskId,
			long processId, boolean result, String comment) {
		// TODO Auto-generated method stub
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		if (id == orderId_process) {
			quoteDAO.merge(quote);
			Map<String, Object> data = new HashMap<>();
			data.put(RESULT_VERIFY_QUOTE, result);
			data.put(VERIFY_QUOTE_COMMENT, comment);
			try {
				jbpmAPIUtil.completeTask(taskId, data, ACTOR_MARKET_MANAGER);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void signContractSubmit(String actorId, long taskId, int orderId,
			double discount, double total, String url) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		order.setTotalMoney(total);
		order.setContractFile(url);
		orderDAO.merge(order);
//		Map<String, Object> data = new HashMap<>();
//		try {
//			jbpmAPIUtil.completeTask(taskId, data, actorId);
//			return true;
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
	}

	@Override
	public void signContractSubmit(String actorId, long taskId,
			int orderId, double discount, double total, String url,
			String confirmDepositFileUrl) {
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		order.setTotalMoney(total);
		order.setContractFile(url);
		order.setConfirmDepositFile(confirmDepositFileUrl);
		orderDAO.merge(order);
//		Map<String, Object> data = new HashMap<>();
//		try {
//			jbpmAPIUtil.completeTask(taskId, data, actorId);
//			return true;
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
	}
	
	@Override
	public void signConfirmFinalPaymentFileSubmit( 
			int orderId, String confirmFinalPaymentFileUrl) {
		Order order = orderDAO.findById(orderId);
		order.setConfirmFinalPaymentFile(confirmFinalPaymentFileUrl);
		orderDAO.merge(order);
		
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
	public List<Map<String, Object>> getSearchConfirmProductList(
			String actorId, String ordernumber, String customername,
			String stylename, String startdate, String enddate,
			Integer[] employeeIds) {
		List<Map<String, Object>> temp = service.getSearchOrderList(actorId, ordernumber,customername,
				 stylename,  startdate,  enddate, employeeIds,
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
	public List<Map<String, Object>> getOrderList(Integer page) {
		// TODO Auto-generated method stub
		//List<Order> orders = orderDAO.findByEmployeeId(employeeId);
		List<Order> orders = orderDAO.getOrderList(page);
		Integer pages=orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}

	public ArrayList<String> getProcessStateName(final Integer orderId) {
         final ArrayList<String> nodeInstanceNames = new ArrayList<>();

		jbpmAPIUtil.getKsession().execute(new GenericCommand<String>() {
			public String execute(Context context) {
				StatefulKnowledgeSession ksession = ((KnowledgeCommandContext) context).getStatefulKnowledgesession();
				long processId=orderDAO.findById(orderId).getProcessId();
				if((Long)processId!=null){
				org.jbpm.process.instance.ProcessInstance processInstance = (org.jbpm.process.instance.ProcessInstance) ksession.getProcessInstance(processId);
				if(processInstance==null){
					return null;
				}
				/*
				 *get state 
				 *通过当前的node实例方法获得节点名称：nodeInstance.getNodeName()
				 */
				for (NodeInstance nodeInstance : ((org.jbpm.workflow.instance.WorkflowProcessInstance) processInstance)
						.getNodeInstances()) {
					if(!"Gateway".equals(nodeInstance.getNodeName())){
						System.out.println("状态名称："+nodeInstance.getNodeName());
						nodeInstanceNames.add(nodeInstance.getNodeName());
					}
				}
			}
				return null;
			}
		});
		return nodeInstanceNames;
	}
	
	@Override
	public List<Map<String, Object>> getOrders() {
		List<Order> orders = orderDAO.getOrders();
		Integer pages=orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
//			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			ArrayList<String>  orderProcessStateNames = financeService.getProcessStateName(order.getOrderId());
//			System.out.println("取到的订单当前状态为："+orderProcessStateNames.get(0)+"数组大小："+orderProcessStateNames.size());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getOrders(String userRole, Integer userId) {
		List<Order> orders = null;
		
		if("CUSTOMER".equals(userRole)){
			orders = orderDAO.findByCustomerId(userId);
		}else if ("marketStaff".equals(userRole)){
			orders = orderDAO.findByEmployeeId(userId);
		}
		
		Integer pages=orderDAO.getPageNumber();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size() > 0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrdersDoing() {
		List<Order> orders = orderDAO.getOrdersDoing();
 		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
 			list.add(model);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getOrdersDoing(String userRole, Integer userId) {		
		Order orderExample = new Order();
		List<Order> orders = new ArrayList<Order>();
		orderExample.setOrderState("A"); //正在进行中的订单
		
		if("CUSTOMER".equals(userRole)){
			orderExample.setCustomerId(userId);
			orders = orderDAO.findOrdersDoingByCustomer(orderExample);
		}else if ("marketStaff".equals(userRole)){
			orderExample.setEmployeeId(userId);
			orders = orderDAO.findOrdersDoingByEmployee(orderExample);
		}
		
//		List<Order> orders = orderDAO.findByExample(orderExample);
 		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size() > 0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
 			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrdersDone() {
		List<Order> orders = orderDAO.getOrdersDone();
 		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
 			list.add(model);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getOrdersDone(String userRole, Integer userId) {
		Order orderExample = new Order();
		orderExample.setOrderState("Done"); //已经完成的订单
		List<Order> orders = new ArrayList<Order>();

		if("CUSTOMER".equals(userRole)){
			orderExample.setCustomerId(userId);
			orders = orderDAO.findOrdersDoneByCustomer(orderExample);
 
		}else if ("marketStaff".equals(userRole)){
			orderExample.setEmployeeId(userId);
			orders = orderDAO.findOrdersDoneByEmployee(orderExample);

		}
//		List<Order> orders = orderDAO.findByExample(orderExample);
 		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String> orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size() > 0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
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
		
		List<DesignCad> cads = cadDAO.findByOrderId(orderId);
		if (cads != null && cads.size() != 0) {
			model.put("designCad", cads.get(0));
		}
		return model;
	}

	@Override
	public List<Map<String, Object>> getAddMoreOrderList(int customerId) {
		// TODO Auto-generated method stub

		System.out.println("客户的ID是："+customerId);
//		Order o = new Order();
//		o.setCustomerId(customerId);
//		List<Order> orderList = orderDAO.findByExample(o);
		List<Order> orderList = orderDAO.findResultsByCustomerId(customerId);
		List<Map<String, Object>> list = new ArrayList<>();
		System.out.println("翻单数量："+orderList.size());
		for(Order order: orderList){
			System.out.println("翻单数量客户姓名："+order.getCustomerName());
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Integer orderId = order.getOrderId();
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("taskTime", order.getOrderTime());
			model.put("orderId", service.getOrderId(order));
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
		model.put("orderId", service.getOrderId(order));

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
		List<AccessoryCost> accessoryCosts = accessoryCostDAO.findByOrderId(orderId);
		model.put("accessoryCosts", accessoryCosts);
		return model;
	}

	public static Object copy(Object object) throws Exception {
		Class<?> classType = object.getClass();
		Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
		Field fields[] = classType.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			String setMethodName = "set" + firstLetter + fieldName.substring(1);
			Method getMethod = classType.getMethod(getMethodName,new Class[] {});
			Method setMethod = classType.getMethod(setMethodName,new Class[] { field.getType() });
			Object value = getMethod.invoke(object, new Object[] {});
			setMethod.invoke(objectCopy, new Object[] { value });
		}
		return objectCopy;
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

	@Override
	public List<Map<String, Object>> getSearchOrderList(String ordernumber,
			String customername, String stylename, String startdate,String enddate,
			Integer[] employeeIds) {
		List<Order> orders = orderDAO.getSearchOrderList( ordernumber,
				 customername,stylename,startdate,enddate,employeeIds);
 		int orderslength = orders.size();
		Integer pages=(int) Math.ceil((double)orderslength/10);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("pages", pages);
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
 		}
		return list;
 	}

	public String getTaskTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	@Override
	public List<Map<String, Object>> getSearchOrdersDoing(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds) {
		List<Order> orders = orderDAO.getSearchOrderDoingList( ordernumber,
				 customername,stylename,startdate,enddate,employeeIds);
		int orderslength = orders.size();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSearchOrdersDone(String ordernumber,
			String customername, String stylename, String startdate,
			String enddate, Integer[] employeeIds) {
		List<Order> orders = orderDAO.getSearchOrderDoneList( ordernumber,
				 customername,stylename,startdate,enddate,employeeIds);
		int orderslength = orders.size();
		List<Map<String, Object>> list = new ArrayList<>();
		for (Order order : orders) {
			ArrayList<String>  orderProcessStateNames = getProcessStateName(order.getOrderId());
			if(orderProcessStateNames.size()>0){
				order.setOrderProcessStateName(orderProcessStateNames.get(0));
			}else{
				order.setOrderProcessStateName("");
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("order", order);
			model.put("employee", employeeDAO.findById(order.getEmployeeId()));
			model.put("orderId", service.getOrderId(order));
			model.put("taskTime", getTaskTime(order.getOrderTime()));
			list.add(model);
		}
		return list;
	}
	
	@Override
	public void sendOrderInfoViaEmail(Order order, Customer customer){
		if(StringUtils.isEmpty(customer.getEmail()))
			return;
		
		String emailTitle = "智造链 - 下单信息";
        String emailContent = "尊敬的客户，您已成功下单，以下是您具体的订单信息：<br/>" + 
        		              "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>" + 					
        		              	 "<thead>" +
        		              	 	"<tr>" +
        		              	 	   "<th>订单号</th>" +
        		              	 	   "<th>款型</th>" +
        		              	 	   "<th>件数</th>" +
        		              	 	   "<th>预期交付日期</th>" +
        		              	 	 "</tr>" +
        		              	  "</thead>" +
        		              	  "<tbody>" + 
        		              	  	 "<tr>" + 
        		              	  	   "<td>" + service.getOrderId(order) + "</td>" +				
        		              	  	   "<td>" + order.getStyleName() + "</td>" +
        		              	  	   "<td>" + order.getAskAmount() + "</td>" +
        		              	  	   "<td>" + order.getAskDeliverDate() + "</td>" +							
        		              	  	 "</tr>" +													
        		              	  "</tbody>" +
        		               "</table>";
        
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setSubject(emailTitle);
        mailSenderInfo.setContent(emailContent);
        mailSenderInfo.setToAddress(customer.getEmail());
        SimpleMailSender.sendHtmlMail(mailSenderInfo);
	}
	
	@Override
	public void sendOrderInfoViaPhone(Order order, Customer customer){
		
	}















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
