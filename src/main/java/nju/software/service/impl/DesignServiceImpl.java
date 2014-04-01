package nju.software.service.impl;

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
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Account;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;
import nju.software.service.DesignService;
import nju.software.util.JbpmAPIUtil;

@Service("designServiceImpl")
public class DesignServiceImpl implements DesignService {
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private LogisticsDAO logisticsDAO;
	@Autowired
	private FabricDAO fabricDAO;
	@Autowired
	private QuoteDAO QuoteDAO;
	@Autowired
	private AccessoryDAO accessoryDAO;

	@Override
	public boolean verify(Account account, int orderId, long taskId, 
			long processId, boolean designVal, String comment) {
		// TODO Auto-generated method stub
//		String actorId = account.getUserRole();
		String actorId = "SHEJIZHUGUAN";
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
			data.put("designVal", designVal);
			data.put("designComment", comment);
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
	public boolean costAccounting(Account account, int orderId, long taskId,
			long processId, float design_cost) {
		// TODO Auto-generated method stub
		
		String actorId = "SHEJIZHUGUAN";
		//需要获取task中的数据	
		WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
		int orderId_process  = (int) process.getVariable("orderId");
		System.out.println("orderId: " + orderId);
		if (orderId == orderId_process) {

			
				Quote quote = QuoteDAO.findById(orderId);
			if(quote==null){
				//数据库中无quote对象
				//修改QUote内容
				quote=new Quote();
				quote.setOrderId(orderId);
				 quote.setDesignCost(design_cost);
				 QuoteDAO.save(quote);
			}else{
				//quote已存在于数据库
				//修改QUote内容
	                quote.setDesignCost(design_cost);
				QuoteDAO.attachDirty(quote);
			}
		

			//修改流程参数
			Map<String, Object> data = new HashMap<>();
//			data.put("designVal", designVal);
			data.put("designcost", design_cost);
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

	
}
