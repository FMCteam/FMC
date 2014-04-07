package nju.software.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Customer;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.ProductModel;
import nju.software.model.QuoteConfirmTaskSummary;
import nju.software.service.MarketService;
import nju.software.util.FileOperateUtil;
import nju.software.util.JbpmAPIUtil;

@Service("marketServiceImpl")
public class MarketServiceImpl implements MarketService {

	public final static String ACTOR_MARKET_MANAGER = "marketManager";
	public final static String ACTOR_MARKET_STAFF = "marketStaff";
	public final static String TASK_MODIFY_ORDER = "modifyOrder";
	public final static String TASK_MERGE_QUOTE = "mergeQuote";
	public final static String TASK_VERIFY_ORDER = "verifyQuote";
	public final static String TASK_CONFIRM_QUOTE = "confirmQuote";
	public final static String TASK_MODIFY_QUOTE = "modifyQuote";
	public final static String TASK_CONFIRM_PRODUCE_ORDER = "confirmProduceOrder";
	public final static String TASK_MODIFY_PRODUCE_ORDER = "modifyProduceOrder";
	public final static String TASK_SIGN_CONTRACT = "signContract";
	

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CustomerDAO customerDAO;
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
	private LogisticsDAO logisticsDAO;
	

	
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
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		// 添加订单信息
		orderDAO.save(order);
		
		Integer orderId=order.getOrderId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		if(multipartRequest.getFile("sample_clothes_picture")!=null){
			String filedir=request.getSession().getServletContext().getRealPath("/upload/sampleClothesPicture/"+orderId);
			FileOperateUtil.Upload(request, filedir, null, "sample_clothes_picture");
		}
		if(multipartRequest.getFile("reference_picture")!=null){
			String filedir=request.getSession().getServletContext().getRealPath("/upload/reference_picture/"+orderId);
			FileOperateUtil.Upload(request, filedir, null, "reference_picture");
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

		// 添加物流信息
		logistics.setOrderId(orderId);
		logisticsDAO.save(logistics);

		// 启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("marketStaff", order.getEmployeeId());
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
		doTMWorkFlowStart(params);

		return false;
	}

	
	/**
	 * 启动流程
	 */
	private void doTMWorkFlowStart(Map<String, Object> params) {
		try {
			jbpmAPIUtil.setParams(params);
			jbpmAPIUtil.startWorkflowProcess();
			System.out.println("流程启动成功！");
		} catch (Exception ex) {
			System.out.println("流程启动失败");
			ex.printStackTrace();
		}
	}
	
	
	

	@Override
	public List<QuoteConfirmTaskSummary> getQuoteConfirmTaskSummaryList(
			Integer employeeId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "confirm_quoteorder");
		List<QuoteConfirmTaskSummary> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(employeeId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				QuoteConfirmTaskSummary summary = QuoteConfirmTaskSummary
						.getInstance(orderDAO.findById(orderId),
								(Quote) quoteDAO.findById(orderId),
								task.getId());
				taskSummarys.add(summary);
			}
		}
		return taskSummarys;
	}

	@Override
	public List<QuoteConfirmTaskSummary> getQuoteModifyTaskSummaryList(
			Integer employeeId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "edit_quoteorder");
		List<QuoteConfirmTaskSummary> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(employeeId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				QuoteConfirmTaskSummary summary = QuoteConfirmTaskSummary
						.getInstance(orderDAO.findById(orderId),
								(Quote) quoteDAO.findById(orderId),
								task.getId());
				taskSummarys.add(summary);
			}
		}
		return taskSummarys;
	}

	@Override
	public void completeQuoteConfirmTaskSummary(long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		if (result.equals("1")) {
			data.put("confirmquote", true);
			data.put("eidtquote", false);
			data.put("samplejin", true);
		}
		if (result.equals("2")) {
			data.put("confirmquote", false);
			data.put("eidtquote", true);
			data.put("samplejin", true);
		}
		if (result.equals("3")) {
			data.put("confirmquote", false);
			data.put("eidtquote", false);
			data.put("samplejin", true);
		}
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
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
	public List<Product> getProduct(int orderId, String productAskAmount,
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
	public boolean confirmProduct(Account account, int orderId, long taskId,
			long processId, boolean comfirmworksheet, List<Product> productList) {
		// TODO Auto-generated method stub
		// String actorId = account.getUserRole();
		String actorId = "SHICHANGZHUANYUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			// 如果通过，创建合同加工单
			if (comfirmworksheet) {
				for (int i = 0; i < productList.size(); i++) {
					productDAO.save(productList.get(i));
				}
			}
			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("comfirmworksheet", comfirmworksheet);
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
	public void completeSignContract(Integer orderId, double discount,
			long taskId) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		order.setDiscount(discount);
		orderDAO.attachDirty(order);

		Map<String, Object> data = new HashMap<>();
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<OrderModel> getProductModifyList(Integer userId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "edit_worksheet");
		List<OrderModel> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			if (getVariable("employeeId", task).equals(userId)) {
				Integer orderId = (Integer) getVariable("orderId", task);
				OrderModel om = new OrderModel(orderDAO.findById(orderId),
						task.getId(), task.getProcessInstanceId());
				taskSummarys.add(om);
			}
		}
		return taskSummarys;
	}

	@Override
	public boolean modifyProduct(Integer userId, int id, long taskId,
			long processId, boolean editworksheetok, List<Product> productList) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHICHANGZHUANYUAN", "edit_worksheet");
		for (TaskSummary task : tasks) {
			if (task.getId() == taskId
					&& getVariable("employeeId", task).equals(userId)
					&& task.getProcessInstanceId() == processId
					&& getVariable("orderId", task).equals(id)) {
				if (editworksheetok) {
					for (int i = 0; i < productList.size(); i++) {
						productDAO.save(productList.get(i));
					}
				}
				try {
					Map<String, Object> data = new HashMap<>();
					data.put("editworksheetok", editworksheetok);
					jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
					return true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public List<OrderInfo> getConfirmQuoteList(String actorId) {
		// TODO Auto-generated method stub
		// List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
		// actorId, TASK_CONFIRM_QUOTE);

		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				ACTOR_MARKET_STAFF, TASK_CONFIRM_QUOTE);
		List<OrderInfo> models = new ArrayList<OrderInfo>();
		
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) getVariable("orderId", task);
			OrderInfo model = new OrderInfo();
			model.setOrder(orderDAO.findById(orderId));
			model.setQuote(quoteDAO.findById(orderId));
			model.setTask(task);
			models.add(model);
		}
		return models;
	}

	
	@Override
	public OrderInfo getConfirmQuoteDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirmQuoteSubmit(String actorId, long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		if (result.equals("1")) {
			data.put("confirmquote", true);
			data.put("eidtquote", false);
			data.put("samplejin", true);
		}
		if (result.equals("2")) {
			data.put("confirmquote", false);
			data.put("eidtquote", true);
			data.put("samplejin", true);
		}
		if (result.equals("3")) {
			data.put("confirmquote", false);
			data.put("eidtquote", false);
			data.put("samplejin", true);
		}
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_MARKET_STAFF);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


}
