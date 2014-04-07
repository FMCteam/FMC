package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import nju.software.dataobject.Accessory;
import nju.software.dataobject.AccessoryCost;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.FabricCost;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Product;
import nju.software.model.ProductModel;
import nju.software.dataobject.Quote;
import nju.software.service.BuyService;
import nju.software.util.JbpmAPIUtil;

@Service("buyServiceImpl")
public class BuyServiceImpl implements BuyService {
	
	
	public final static String ACTOR_PURCHASE_MANAGER = "purchaseManager";
	public final static String TASK_VERIFY_PURCHASE = "verifyPurchase";
	public final static String TASK_COMPUTE_PURCHASE_COST = "computePurchaseCost";
	public final static String TASK_PURCHASE_SAMPLE_MATERIAL = "purchaseSampleMaterial";
	public final static String TASK_CONFIRM_PURCHASE = "confirmPurchase";
	public final static String TASK_PURCHASE_MATERIAL = "purchaseMaterial";
	
	

	
	
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

	private AccessoryCostDAO AccessoryCostDAO;
	@Autowired
	private FabricCostDAO FabricCostDAO;
	
	
	

	@Override
	public boolean verify(Account account, int orderId, long taskId, 
			long processId, boolean buyVal, String comment) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "CAIGOUZHUGUAN";
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {
			Order order = orderDAO.findById(orderId);
			//修改order内容

			//提交修改
			orderDAO.attachDirty(order);

			//修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("buyVal", buyVal);
			data.put("buyComment", comment);
			//直接进入到下一个流程时
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


	@Override
	public ProductModel getProductDetail(int int_orderId, int int_taskId,
			int int_processId) {
		// TODO Auto-generated method stub
		ProductModel model=new ProductModel();
		List<Product> productList=productDAO.findByProperty("orderId", int_orderId);
		List<Fabric> fabricList=fabricDAO.findByProperty("orderId",int_orderId);
		List<Accessory> accessoryList=accessoryDAO.findByProperty("orderId",int_orderId);
		
		model.setProductList(productList);
		model.setFabricList(fabricList);
		model.setAccessoryList(accessoryList);
		
		model.setProcessId(int_processId);
		model.setTaskId(int_taskId);
		model.setOrderId(int_orderId);
		
		return model;
	}


	
	
	
	
	@Override
	public boolean costAccounting(Account account, int orderId, long taskId,
			long processId, String[] fabric_names, String[] tear_per_meters,
			String[] cost_per_meters, String[] fabric_prices) {
		
	
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "CAIGOUZHUGUAN";
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		
		if (orderId == orderId_process) {
//			Order order = orderDAO.findById(orderId);
			//修改order内容

			
			
			for (int i=0;i<fabric_names.length;i++)      
			  {      
				
		       FabricCost fabricCost =new FabricCost();
				fabricCost.setOrderId(orderId);
				
				//修改FabricCost内容
		       fabricCost.setFabricName(fabric_names[i]);
		       fabricCost.setTearPerMeter(Float.parseFloat(tear_per_meters[i]));
		       fabricCost.setCostPerMeter(Float.parseFloat(cost_per_meters[i]));
		       fabricCost.setPrice(Float.parseFloat(fabric_prices[i]));
		       
		   	//提交修改
		       FabricCostDAO.save(fabricCost);
			  }
			
			
			
			//修改流程参数
			Map<String, Object> data = new HashMap<>();
			data.put("buyVal", orderId);
//			data.put("buyComment", comment);
			//直接进入到下一个流程时
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

	
//
//	@Override
//	public boolean updateAccessoryCost(int orderId, long taskId,
//			long processId, String[] accessory_names, String[] tear_per_piece,
//			String[] cost_per_piece, String[] accessory_prices) {
//		
//		
//		
//		// TODO Auto-generated method stub
////		String actorId = account.getUserRole();
//		String actorId = "CAIGOUZHUGUAN";
//		//需要获取task中的数据	
//		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
//		int orderId_process  = (int) process.getVariable("orderId");
//		System.out.println("orderId: " + orderId);
//	
//		
//		if (orderId == orderId_process) {
////			Order order = orderDAO.findById(orderId);
//			//修改order内容
//
//			
//			
//			for (int i=0;i< accessory_names.length;i++)      
//			  {      
//				
//		       AccessoryCost accessoryCost =new AccessoryCost();
//		       accessoryCost.setOrderId(orderId);
//				
//				//修改FabricCost内容
//		       accessoryCost.setAccessoryName(accessory_names[i]);
//		       accessoryCost.setTearPerPiece(Float.parseFloat(tear_per_piece[i]));
//		       accessoryCost.setCostPerPiece(Float.parseFloat(cost_per_piece[i]));
//		       accessoryCost.setPrice(Float.parseFloat(accessory_prices[i]));
//		       
//		   	//提交修改
//		      AccessoryCostDAO.save(accessoryCost);
//		       return true;
//			  }
//		
//		
//		}
//		
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	
//	
//	
//	
//	
//	
	
	
	
	

}
