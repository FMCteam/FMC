package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
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
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
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
	public Map<String, Object> getVerifyPurchaseDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModel(ACTOR_PURCHASE_MANAGER,
				TASK_VERIFY_PURCHASE, orderId);
	}

	@Override
	public boolean verifyPurchaseSubmit(long taskId, boolean result,
			String comment) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PURCHASE, result);
		data.put(RESULT_PURCHASE_COMMENT, comment);
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
	public Map<String, Object> getComputePurchaseCostDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModel(ACTOR_PURCHASE_MANAGER,
				TASK_COMPUTE_PURCHASE_COST, orderId);
	}

	@Override
	public void computePurchaseCostSubmit(int orderId, long taskId,
			String[] fabric_names, String[] tear_per_meters,
			String[] cost_per_meters, String[] fabric_prices,
			String[] accessory_names, String[] tear_per_piece,
			String[] cost_per_piece, String[] accessory_prices) {
		// TODO Auto-generated method stub
		if (fabric_names != null) {
			for (int i = 0; i < fabric_names.length; i++) {
				FabricCost fabricCost = new FabricCost();
				fabricCost.setOrderId(orderId);
				fabricCost.setFabricName(fabric_names[i]);
				fabricCost
						.setTearPerMeter(Float.parseFloat(tear_per_meters[i]));
				fabricCost
						.setCostPerMeter(Float.parseFloat(cost_per_meters[i]));
				fabricCost.setPrice(Float.parseFloat(fabric_prices[i]));
				FabricCostDAO.save(fabricCost);
			}
		}
		if (accessory_names != null) {
			for (int i = 0; i < accessory_names.length; i++) {
				AccessoryCost accessoryCost = new AccessoryCost();
				accessoryCost.setOrderId(orderId);
				accessoryCost.setAccessoryName(accessory_names[i]);
				accessoryCost.setTearPerPiece(Float
						.parseFloat(tear_per_piece[i]));
				accessoryCost.setCostPerPiece(Float
						.parseFloat(cost_per_piece[i]));
				accessoryCost.setPrice(Float.parseFloat(accessory_prices[i]));
				AccessoryCostDAO.save(accessoryCost);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
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
	public Map<String,Object> getPurchaseSampleMaterialDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_SAMPLE_MATERIAL, orderId);
	}

	@Override
	public boolean purchaseSampleMaterialSubmit(long taskId, boolean result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PURCHASE, result);
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
	public Map<String, Object> getConfirmPurchaseDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PURCHASE_MANAGER,
				TASK_CONFIRM_PURCHASE, orderId);
	}

	@Override
	public boolean confirmPurchaseSubmit(long taskId, boolean result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PURCHASE, result);
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
	public Map<String, Object> getPurchaseMaterialDetail(Integer orderId) {
		// TODO Auto-generated method stub
		return service.getBasicOrderModelWithQuote(ACTOR_PURCHASE_MANAGER,
				TASK_PURCHASE_MATERIAL, orderId);
	}

	@Override
	public boolean purchaseMaterialSubmit(long taskId, boolean result) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(RESULT_PURCHASE, result);
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
	public final static String RESULT_PURCHASE = "purchase";
	public final static String RESULT_PURCHASE_COMMENT = "purchaseComment";
}
