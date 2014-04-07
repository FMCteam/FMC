package nju.software.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Employee;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.model.OrderModel;
import nju.software.model.QuoteModel;
import nju.software.service.OrderService;
import nju.software.util.JbpmAPIUtil;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 莫其凡
 * 
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
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

	// 新增订单
	public void addOrder(Order order) {
		orderDAO.save(order);
	}

	public List<Order> findAll() {
		List<Order> order = this.orderDAO.findAll();
		return order;
	}

	public Order getOrderById(int orderId) {
		try {
			Order order = orderDAO.findById(orderId);
			return order;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateOrder(Order order) {
		// TODO Auto-generated method stub
		try {
			orderDAO.attachDirty(order);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Object> getOrderWithSample(Map<String, Object> map) {
		try {

			// order that need sample and the hasPostedSampleCloth is
			// false,isNeedSampleCloths is true

			int count = orderDAO.coutSampleOrder();
			int page = (int) map.get("page");
			int number = (int) map.get("number_per_page");
			int total_pages = 0;
			int start = 0;

			System.out.println("count" + count);
			System.out.println("page" + page);
			System.out.println("number" + number);
			if (count > 0)
				total_pages = (int) Math.ceil((double) count / number);
			if (page > total_pages)
				page = total_pages;
			start = number * (page - 1);
			map.put("page_number", total_pages);
			List<Order> orderList = orderDAO.findSampleOrderAndPage(start,
					number);
			map.put("page_number", total_pages);
			List<Object> list = new ArrayList<Object>();
			list.add(orderList);
			list.add(map);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Order findByOrderId(String orderId) {
		// TODO Auto-generated method stub

		return orderDAO.findById(Integer.parseInt(orderId));
	}

	@Override
	public boolean merge(Order o) {
		// TODO Auto-generated method stub
		try {
			Order order = orderDAO.merge(o);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 提交询单，开始流程
	 */
	@Override
	public String addOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics) {
		// TODO Auto-generated method stub

		// 添加订单信息
		orderDAO.save(order);

		File temp;
		File save;
		String sampleClothesPicture = order.getSampleClothesPicture();
		if (sampleClothesPicture != null) {
			temp = new File(sampleClothesPicture);
			save = new File(temp.getParentFile().getParentFile()
					.getParentFile().getAbsolutePath()
					+ "/sampleClothesPicture/"
					+ order.getOrderId()
					+ "/"
					+ temp.getName());
			System.out.println(save.getAbsolutePath());
			save.getParentFile().mkdirs();
			temp.renameTo(save);
			order.setSampleClothesPicture("/upload/sampleClothesPicture/"
					+ order.getOrderId() + "/" + save.getName());
		} else {
			order.setSampleClothesPicture("");
		}

		String referencePicture = order.getReferencePicture();
		if (referencePicture != null) {
			temp = new File(referencePicture);
			save = new File(temp.getParentFile().getParentFile()
					.getParentFile().getAbsolutePath()
					+ "/referencePicture/"
					+ order.getOrderId()
					+ "/"
					+ temp.getName());
			save.getParentFile().mkdirs();
			temp.renameTo(save);
			order.setSampleClothesPicture("/upload/referencePicture/"
					+ order.getOrderId() + "/" + save.getName());
		} else {
			order.setReferencePicture("");
		}
		orderDAO.attachDirty(order);
		

		// 添加面料信息
		for (Fabric fabric : fabrics) {
			fabric.setOrderId(order.getOrderId());
			fabricDAO.save(fabric);
		}

		// 添加辅料信息
		for (Accessory accessory : accessorys) {
			accessory.setOrderId(order.getOrderId());
			accessoryDAO.save(accessory);
		}

		// 添加物流信息
		logistics.setOrderId(order.getOrderId());
		logisticsDAO.save(logistics);

		// 启动流程
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		params.put("marketStaff", order.getEmployeeId());
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
		doTMWorkFlowStart(params);

		return "下单成功";
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

	/**
	 * 通过actorId和taskName来查找order列表，返回List<OrderModel>
	 */
	@Override
	public List<OrderModel> getOrderByActorIdAndTaskname(String actorId,
			String taskName) {
		// TODO Auto-generated method stub
		List<OrderModel> orderList = new ArrayList<OrderModel>();
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
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
			Order order = getOrderById(orderId);
			if (order != null) {
				System.out.println("orderId: " + order.getOrderId());
				OrderModel orderModel = new OrderModel(order, task.getId(),
						processId);
				orderList.add(orderModel);
			}
		}
		return orderList;
	}

	/**
	 * 通过orderId来获取order详细信息，并将taskId和processId封装，返回orderModel
	 */
	@Override
	public OrderModel getOrderDetail(int orderId, long taskId, long processId) {
		// TODO Auto-generated method stub
		OrderModel orderModel = null;
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		if (orderId == orderId_process) {
			Order order = orderDAO.findById(orderId);
			orderModel = new OrderModel(order, taskId, processId);
		}
		return orderModel;
	}

	@Override
	public boolean verify(int orderId, long taskId, long processId,
			boolean editOk, String buyComment, String designComment,
			String productComment) {
		// TODO Auto-generated method stub
		String actorId = "SHICHANGZHUANYUAN";
		// 需要获取task中的数据
		WorkflowProcessInstance process = (WorkflowProcessInstance) jbpmAPIUtil
				.getKsession().getProcessInstance(processId);
		int orderId_process = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {

			// 修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("buyComment", buyComment);
			data.put("designComment", designComment);
			data.put("productCommment", productComment);
			data.put("editok", editOk);
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
	public void modifyOrder(Order order, List<Fabric> fabrics,
			List<Accessory> accessorys, Logistics logistics) {
		// TODO Auto-generated method stub
		// 添加订单信息
		orderDAO.merge(order);
		/*
		 * //删除order相关的面料和辅料和物流的信息
		 * fabricDAO.deleteByProperty(order.getOrderId()); //添加面料信息 for(Fabric
		 * fabric:fabrics){ fabric.setOrderId(order.getOrderId());
		 * fabricDAO.save(fabric); }
		 * 
		 * //添加辅料信息 for(Accessory accessory:accessorys){
		 * accessory.setOrderId(order.getOrderId());
		 * accessoryDAO.save(accessory); }
		 * 
		 * //添加物流信息 logistics.setOrderId(order.getOrderId());
		 * logisticsDAO.save(logistics);
		 */
	}

	@Override
	public List<QuoteModel> getQuoteByActorAndTask(String actor, String taskName) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<QuoteModel> orderList = new ArrayList<QuoteModel>();
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(actor,
				taskName);
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
			List<Quote> quote = quoteDAO.findByProperty("orderId", orderId);
			if (quote != null && quote.size() != 0) {

				System.out.println("quote: " + quote.get(0).getOrderId());
				QuoteModel model = new QuoteModel(quote.get(0), task.getId(),
						process.getId());

				orderList.add(model);
			}
		}
		return orderList;

	}

	@Override
	public QuoteModel getQuoteByOrderAndPro(String actor, String taskName,
			int orderId) {
		// TODO Auto-generated method stub
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(actor,
				taskName);
		if (list.isEmpty()) {
			System.out.println("no task list");
		}
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		WorkflowProcessInstance process = null;
		for (TaskSummary task : list) {
			// 需要获取task中的数据
			long pid = task.getProcessInstanceId();
			process = (WorkflowProcessInstance) session.getProcessInstance(pid);
			int oid = (int) process.getVariable("orderId");
			if (orderId == oid) {
				List<Quote> quote = quoteDAO.findByProperty("orderId", orderId);
				return new QuoteModel(quote.get(0), task.getId(),
						process.getId());
			}
		}
		return null;
	}

}
