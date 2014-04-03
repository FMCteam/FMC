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
	public boolean verify(Account account, int orderId, long taskId,
			long processId, boolean productVal, String comment) {
		// TODO Auto-generated method stub
		// String actorId = account.getUserRole();
		String actorId = "SHENGCHANZHUGUAN";
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
	public List<SampleProduceTaskSummary> getSampleProduceTaskSummaryList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHENGCHANZHUGUAN", "product_sample");
		List<SampleProduceTaskSummary> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) getVariable("orderId", task);
			SampleProduceTaskSummary summary = SampleProduceTaskSummary
					.getInstance(orderDAO.findById(orderId), (Quote) quoteDAO
							.findById(orderId), task
							.getId());
			taskSummarys.add(summary);

		}
		return taskSummarys;
	}

	@Override
	public SampleProduceTask getSampleProduceTask(Integer orderId, long taskId) {
		// TODO Auto-generated method stub
		Order order = orderDAO.findById(orderId);
		List<Fabric> fabrics=fabricDAO.findByOrderId(orderId);
		List<Accessory> accessorys=accessoryDAO.findByOrderId(orderId);
		SampleProduceTask task = new SampleProduceTask(taskId, order, fabrics, accessorys);
		return task;
	}

	@Override
	public void completeSampleProduceTask(long taskId, String result) {
		// TODO Auto-generated method stub
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("producterror", result.equals("0"));
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHENGCHANZHUGUAN");
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
	public boolean costAccounting(Account account, int orderId, long taskId,
			long processId, float cut_cost, float manage_cost, float nali_cost,
			float ironing_cost, float swing_cost, float package_cost,
			float other_cost) {
		// TODO Auto-generated method stub
		
				String actorId = "SHENGCHANZHUGUAN";
				//需要获取task中的数据	
				WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(processId);
				int orderId_process  = (int) process.getVariable("orderId");
				System.out.println("orderId: " + orderId);
				if (orderId == orderId_process) {
					Quote quote = QuoteDAO.findById(orderId);
					
					if(quote==null){
						quote=new Quote();
						quote.setOrderId(orderId);
						quote.setCutCost(cut_cost);
		                quote.setManageCost(manage_cost);
		                quote.setSwingCost(swing_cost);
		                quote.setIroningCost(ironing_cost);
		                quote.setNailCost(nali_cost);
		                quote.setPackageCost(package_cost);
		                quote.setOtherCost(other_cost);
		                QuoteDAO.save(quote);
					}else{
						quote.setCutCost(cut_cost);
		                quote.setManageCost(manage_cost);
		                quote.setSwingCost(swing_cost);
		                quote.setIroningCost(ironing_cost);
		                quote.setNailCost(nali_cost);
		                quote.setPackageCost(package_cost);
		                quote.setOtherCost(other_cost);
		                QuoteDAO.attachDirty(quote);
					}
					//修改QUote内容
		                
		                
		                
					QuoteDAO.attachDirty(quote);

					float producecost=cut_cost+manage_cost+swing_cost+ironing_cost+nali_cost+package_cost+other_cost;
					//修改流程参数
					Map<String, Object> data = new HashMap<>();
//					data.put("designVal", designVal);
					data.put("producecost", producecost);
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
	public List<OrderInfo> getProduceList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHENGCHANZHUGUAN", "volume_production");
		List<OrderInfo> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			
			Integer orderId = (Integer) getVariable("orderId", task);
			OrderInfo summary =new  OrderInfo(orderDAO.findById(orderId), task.getId());
			taskSummarys.add(summary);

		}
		return taskSummarys;
	}

	@Override
	public OrderInfo getProduceInfo(Integer orderId) {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"SHENGCHANZHUGUAN", "volume_production");
		for (TaskSummary task : tasks) {
			if(getVariable("orderId", task).equals(orderId)){
				OrderInfo summary =new  OrderInfo(orderDAO.findById(orderId), task.getId());
				return summary;
			}
		}
		return null;
	}


	
	@Override
	public List<Product> getProductByOrderId(int parseInt) {
		// TODO Auto-generated method stub
		List<Product> productList=productDAO.findByProperty("orderId", parseInt);
		return productList;
	}

	@Override
	public List<nju.software.dataobject.Package> getPackageByOrderId(int parseInt) {
		// TODO Auto-generated method stub
		List<nju.software.dataobject.Package> packageList=packageDAO.findByProperty("orderId",parseInt);
		return packageList;
	}

	@Override
	public List<List<PackageDetail>> getProductDetailByPackage(
			List<nju.software.dataobject.Package> packageList) {
		// TODO Auto-generated method stub
		List<List<PackageDetail>> detail=new ArrayList<List<PackageDetail>>();
		for(nju.software.dataobject.Package p:packageList)
		{
			
			int packageId=p.getPackageId();
			List<PackageDetail> l1=packageDetailDAO.findByProperty("packageId", packageId);
			detail.add(l1);
			
		}
		return detail;

	}
	@Override
	public void pruduceSubmit(String[] pid, String[] askAmount, long taskId) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<pid.length;i++){
			Product product=productDAO.findById(Integer.parseInt(pid[i]));
			product.setAskAmount(Integer.parseInt(askAmount[i]));
			productDAO.attachDirty(product);
		}
		Map<String,Object> data=new HashMap<String,Object>();
		//data.put("producterror", result.equals("0"));
		try {
			jbpmAPIUtil.completeTask(taskId, data, "SHENGCHANZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
