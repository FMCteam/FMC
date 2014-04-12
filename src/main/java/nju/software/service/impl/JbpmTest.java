package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.util.JbpmAPIUtil;


@Service("test")
public class JbpmTest {

	
	public void completeVerify(String actorId,boolean result) {
		Order order=new Order();
		
		order.setEmployeeId(1);
		order.setCustomerId(1);
		order.setOrderState("Test");
		order.setOrderTime(new Timestamp(new Date().getTime()));
		order.setCustomerName("Test");
		order.setCustomerCompany("Test");
		order.setCustomerCompanyFax("Test");
		order.setCustomerPhone1("Test");
		order.setCustomerPhone2("Test");
		order.setCustomerCompanyAddress("Test");
		order.setStyleName("Test");
		order.setFabricType("Test");
		order.setStyleSex("Test");
		order.setStyleSeason("Test");
		order.setSpecialProcess("Test");
		order.setOtherRequirements("Test");
		order.setAskAmount(1);
		order.setAskProducePeriod("Test");
		order.setAskDeliverDate(new Timestamp(new Date().getTime()));
		order.setAskCodeNumber("Test");
		order.setHasPostedSampleClothes((short) 1);
		order.setIsNeedSampleClothes((short) 1);
		order.setOrderSource("Test");
	
		
		orderDAO.save(order);
		
		Produce produce=new Produce();
	    produce.setType("sampleProduce");
		produce.setColor("lv");
		produce.setL(5);
		produce.setM(1);
		produce.setOid(order.getOrderId());
		produceDAO.save(produce);
		
		
		// 面料数据
		String fabric_names = "fabric1,fabric2,fabric3";
		String fabric_amounts = "1,2,3";
		String fabric_name[] = fabric_names.split(",");
		String fabric_amount[] = fabric_amounts.split(",");
		List<Fabric> fabrics = new ArrayList<Fabric>();
		for (int i = 0; i < fabric_name.length; i++) {
			fabrics.add(new Fabric(0, fabric_name[i], fabric_amount[i]));
		}

		// 辅料数据
		String accessory_names = "accessory1,accessory2,accessory3";
		String accessory_querys = "accessory1,accessory2,accessory3";
		String accessory_name[] = accessory_names.split(",");
		String accessory_query[] = accessory_querys.split(",");
		List<Accessory> accessorys = new ArrayList<Accessory>();
		for (int i = 0; i < fabric_name.length; i++) {
			accessorys.add(new Accessory(0, accessory_name[i],
					accessory_query[i]));
		}
		
		
		
		orderId=order.getOrderId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("marketStaff", actorId);
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
		doTMWorkFlowStart(params);
		
		
		//收取样衣
		long taskId = getTaskId(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER,
				LogisticsServiceImpl.TASK_RECEIVE_SAMPLE, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		data.put("receivedsample", true);
		completeTask(taskId, data, LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
		
		
		//采购验证
		taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_VERIFY_PURCHASE, orderId);
		data=new HashMap <String,Object> ();
		data.put("buyVal", result);
		data.put("buyComment", "Test Buy Verify");
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计验证
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_VERIFY_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		data.put("designVal", result);
		data.put("designComment", "Test Design Verify");
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产验证
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_VERIFY_PRODUCE, orderId);
		data=new HashMap <String,Object> ();
		data.put("productVal", result);
		data.put("productComment", "Test Produce Verify");
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
	}
	
	
	
	public void completeComputeCost(String actorId){
		completeVerify(actorId, true);
		
		//采购成本核算
		long taskId = getTaskId(BuyServiceImpl.ACTOR_PURCHASE_MANAGER,
				BuyServiceImpl.TASK_COMPUTE_PURCHASE_COST, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计成本核算
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_COMPUTE_DESIGN_COST, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产成本核算
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_COMPUTE_PRODUCE_COST, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
	}
	
	
	public void completeConfirmQuote(String actorId){
		completeComputeCost(actorId);
		
		//合并报价
		long taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_MERGE_QUOTE, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		completeTask(taskId, data, actorId);
		
		
		//审核报价
		taskId = getTaskId(MarketServiceImpl.ACTOR_MARKET_MANAGER,
				MarketServiceImpl.TASK_VERIFY_QUOTE, orderId);
		data=new HashMap <String,Object> ();
		completeTask(taskId, data, MarketServiceImpl.ACTOR_MARKET_MANAGER);
		
		
		//确认报价
		taskId = getTaskId(actorId,
				MarketServiceImpl.TASK_CONFIRM_QUOTE, orderId);
		data=new HashMap <String,Object> ();
		data.put("confirmquote", true);
		data.put("eidtquote", false);
		data.put("samplejin", true);
		completeTask(taskId, data, actorId);
	}
	
	
	public void completeTask(long taskId, Map<?, ?> data, String actorId) {
		try {
			jbpmAPIUtil.completeTask(taskId, data, actorId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	public long getTaskId(String actorId, String taskName, Integer orderId) {
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
		for (TaskSummary task : list) {
			if (jbpmAPIUtil.getVariable(task, "orderId").equals(orderId)) {
				return task.getId();
			}
		}
		return 0;
	}
	
	
	

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
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProduceDAO produceDAO;
	private static Integer orderId=0;
}
