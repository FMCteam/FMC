package nju.software.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Product;
import nju.software.model.ProductModel;
import nju.software.service.BuyService;
import nju.software.util.JbpmAPIUtil;

@Service("buyServiceImpl")
public class BuyServiceImpl implements BuyService {
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

}
