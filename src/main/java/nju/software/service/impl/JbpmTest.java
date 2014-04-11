package nju.software.service.impl;

import java.sql.Timestamp;
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
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Order;
import nju.software.util.JbpmAPIUtil;


@Service("test")
public class JbpmTest {

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

	private static Integer orderId=0;
	
	
	
	
	public void addOrder(String actorId) {
		orderId = 0;
	}

	
	
	// 莫其凡：完成
	public void receiveSample(String actorId) {
		addOrder(actorId);
		
	}

	
	//市场专员需要传入自己的account.getUserId,其他角色传入"1"
	public void modifyOrder(String actorId,boolean modify) {
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
		data.put("buyVal", modify);
		data.put("buyComment", "Test Buy Verify");
		completeTask(taskId, data, BuyServiceImpl.ACTOR_PURCHASE_MANAGER);
		
		
		//设计验证
		taskId = getTaskId(DesignServiceImpl.ACTOR_DESIGN_MANAGER,
				DesignServiceImpl.TASK_VERIFY_DESIGN, orderId);
		data=new HashMap <String,Object> ();
		data.put("designVal", modify);
		data.put("designComment", "Test Design Verify");
		completeTask(taskId, data, DesignServiceImpl.ACTOR_DESIGN_MANAGER);
		
		
		//生产验证
		taskId = getTaskId(ProduceServiceImpl.ACTOR_PRODUCE_MANAGER,
				ProduceServiceImpl.TASK_VERIFY_PRODUCE, orderId);
		data=new HashMap <String,Object> ();
		data.put("productVal", modify);
		data.put("productComment", "Test Produce Verify");
		completeTask(taskId, data, ProduceServiceImpl.ACTOR_PRODUCE_MANAGER);
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
}
