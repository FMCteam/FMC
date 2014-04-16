package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryCostDAO;
import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricCostDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.dataobject.Product;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.model.ProductModel;
import nju.software.dataobject.Quote;
import nju.software.service.BuyService;
import nju.software.util.JbpmAPIUtil;

@Service("buyServiceImpl")
public class BuyServiceImpl implements BuyService {

	// ===========================采购验证=================================
	public List<Map<String, Object>> getVerifyPurchaseList() {
		return service.getOrderList(ACTOR_PURCHASE_MANAGER,
				TASK_VERIFY_PURCHASE);
	}

	@Override
	public OrderInfo getVerifyPurchaseDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PURCHASE_MANAGER,
				TASK_VERIFY_PURCHASE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean verifyPurchaseSubmit(long taskId, boolean buyVal,
			String comment) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("buyVal", buyVal);
		data.put("buyComment", comment);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PURCHASE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// ===========================采购成本核算=================================
	@Override
	public List<Map<String, Object>> getComputePurchaseCostList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PURCHASE_MANAGER,
				TASK_COMPUTE_PURCHASE_COST);
	}

	@Override
	public OrderInfo getComputePurchaseCostDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PURCHASE_MANAGER,
				TASK_COMPUTE_PURCHASE_COST, orderId);
		OrderInfo model = new OrderInfo();
		model.setFabrics(fabricDAO.findByOrderId(orderId));
		model.setAccessorys(accessoryDAO.findByOrderId(orderId));
		model.setOrder(orderDAO.findById(orderId));
		model.setLogistics(logisticsDAO.findById(orderId));
		model.setTask(task);
		return model;
	}

	@Override
	public void computePurchaseCostSubmit(int orderId, long taskId,
			String[] fabric_names, String[] tear_per_meters,
			String[] cost_per_meters, String[] fabric_prices,
			String[] accessory_names, String[] tear_per_piece,
			String[] cost_per_piece, String[] accessory_prices) {
		// TODO Auto-generated method stub
		for (int i = 0; i < fabric_names.length; i++) {
			FabricCost fabricCost = new FabricCost();
			fabricCost.setOrderId(orderId);
			// 修改FabricCost内容
			fabricCost.setFabricName(fabric_names[i]);
			fabricCost.setTearPerMeter(Float.parseFloat(tear_per_meters[i]));
			fabricCost.setCostPerMeter(Float.parseFloat(cost_per_meters[i]));
			fabricCost.setPrice(Float.parseFloat(fabric_prices[i]));
			// 提交修改
			FabricCostDAO.save(fabricCost);
		}

		for (int i = 0; i < accessory_names.length; i++) {
			AccessoryCost accessoryCost = new AccessoryCost();
			accessoryCost.setOrderId(orderId);
			// 修改FabricCost内容
			accessoryCost.setAccessoryName(accessory_names[i]);
			accessoryCost.setTearPerPiece(Float.parseFloat(tear_per_piece[i]));
			accessoryCost.setCostPerPiece(Float.parseFloat(cost_per_piece[i]));
			accessoryCost.setPrice(Float.parseFloat(accessory_prices[i]));
			// 提交修改
			AccessoryCostDAO.save(accessoryCost);

		}

		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("ComputePurchaseCost", true);
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PURCHASE_MANAGER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ===========================采购样衣原料=================================
	@Override
	public List<Map<String, Object>> getPurchaseSampleMaterialList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_SAMPLE_MATERIAL);
	}

	@Override
	public OrderInfo getPurchaseSampleMaterialDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_SAMPLE_MATERIAL, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setFabrics(fabricDAO.findByOrderId(orderId));
		orderInfo.setAccessorys(accessoryDAO.findByOrderId(orderId));
		orderInfo.setFabricCosts(FabricCostDAO.findByOrderId(orderId));
		orderInfo.setAccessoryCosts(AccessoryCostDAO.findByOrderId(orderId));

		int SampleAmount = 0;
		// 样衣总数量
		List<Produce> produces = ProduceDAO.findByOrderId(orderId);
		for (Produce produce : produces) {
			if (produce.getType().equals("sampleProduce")) {
				SampleAmount = produce.getL() + produce.getM() + produce.getS()
						+ produce.getXl() + produce.getXs() + produce.getXxl();
			}
		}
		orderInfo.getData().put("SampleAmount", new Integer(SampleAmount));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean purchaseSampleMaterialSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		boolean purchaseerror = result.equals("0");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("purchaseerror", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PURCHASE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// ===========================采购确认=================================
	@Override
	public List<Map<String, Object>> getConfirmPurchaseList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PURCHASE_MANAGER,
				TASK_CONFIRM_PURCHASE);
	}

	@Override
	public OrderInfo getConfirmPurchaseDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PURCHASE_MANAGER,
				TASK_CONFIRM_PURCHASE, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean confirmPurchaseSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		boolean purchaseerror = result.equals("1");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("purchaseerror", purchaseerror);
		data.put("isworksheet", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PURCHASE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// ===========================大货原料采购=================================
	@Override
	public List<Map<String, Object>> getPurchaseMaterialList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_MATERIAL);
	}

	@Override
	public OrderInfo getPurchaseMaterialDetail(Integer orderId) {
		// TODO Auto-generated method stub
		TaskSummary task = jbpmAPIUtil.getTask(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_MATERIAL, orderId);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder(orderDAO.findById(orderId));
		orderInfo.setLogistics(logisticsDAO.findById(orderId));
		orderInfo.setTask(task);
		return orderInfo;
	}

	@Override
	public boolean purchaseMaterialSubmit(long taskId, String result) {
		// TODO Auto-generated method stub
		boolean purchaseerror = result.equals("0");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("procurementerror", purchaseerror);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_PURCHASE_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Autowired
	private ServiceUtil service;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private AccessoryCostDAO AccessoryCostDAO;
	@Autowired
	private FabricCostDAO FabricCostDAO;
	@Autowired
	private ProduceDAO ProduceDAO;

	public final static String ACTOR_PURCHASE_MANAGER = "purchaseManager";
	public final static String TASK_VERIFY_PURCHASE = "verifyPurchase";
	public final static String TASK_COMPUTE_PURCHASE_COST = "computePurchaseCost";
	public final static String TASK_PURCHASE_SAMPLE_MATERIAL = "purchaseSampleMaterial";
	public final static String TASK_CONFIRM_PURCHASE = "confirmPurchase";
	public final static String TASK_PURCHASE_MATERIAL = "purchaseMaterial";
}
