package nju.software.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.AccessoryDAO;
import nju.software.dao.impl.FabricDAO;
import nju.software.dao.impl.LogisticsDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProductDAO;
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
import nju.software.dataobject.Product;
import nju.software.dataobject.Quote;
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


	public void addTask(){
		Order order = new Order();
		order.setEmployeeId(1);
		order.setCustomerId(1);
		order.setOrderState("A");
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
		order.setSampleClothesPicture("Test");
		order.setReferencePicture("Test");
		order.setAskAmount(12);
		order.setAskProducePeriod("Test");
		order.setAskDeliverDate(new Timestamp(new Date().getTime()));
		order.setAskCodeNumber("Test");
		order.setHasPostedSampleClothes((short) 0);
		order.setIsNeedSampleClothes((short) 1);
		order.setOrderSource("Test");
		orderDAO.save(order);
		
		
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		params.put("employeeId", order.getEmployeeId());
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", false);
		doTMWorkFlowStart(params);
		
		Map<String, Object> data=new HashMap<String, Object>();
		long taskId=getTaskId("CAIGOUZHUGUAN", "verification_purchased", order.getOrderId());
		try {
			data.put("buyVal", true);
			data.put("buyComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "CAIGOUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHEJIZHUGUAN",
				"design_verification ", order.getOrderId());
		try {
			data.put("designVal", true);
			data.put("designComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHEJIZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		data=new HashMap<String, Object>();
		taskId=getTaskId("SHENGCHANZHUGUAN",
				"production_verification", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHENGCHANZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		data=new HashMap<String, Object>();
		taskId=getTaskId("CAIGOUZHUGUAN",
				"Purchasing_accounting", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "CAIGOUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		data=new HashMap<String, Object>();
		taskId=getTaskId("SHEJIZHUGUAN",
				"design_accounting", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHEJIZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		data=new HashMap<String, Object>();
		taskId=getTaskId("SHENGCHANZHUGUAN",
				"business_accounting", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHENGCHANZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHICHANGZHUANYUAN",
				"quote", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHICHANGZHUGUAN",
				"check_quote", order.getOrderId());
		try {
			data.put("productVal", true);
			data.put("productComment", "");
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHICHANGZHUANYUAN",
				"confirm_quoteorder", order.getOrderId());
		try {
			data.put("confirmquote", true);
			data.put("eidtquote", false);
			data.put("samplejin", false);
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Product p=new Product();
		p.setOrderId(order.getOrderId());
		p.setColor("yellow");
		p.setAskAmount(22);
		p.setQualifiedAmount(0);
		p.setProduceAmount(0);
		p.setStyle("XXS");
		productDAO.save(p);
		p=new Product();
		p.setOrderId(order.getOrderId());
		p.setColor("Red");
		p.setAskAmount(12);
		p.setProduceAmount(0);
		p.setStyle("XXS");
		p.setQualifiedAmount(0);
		productDAO.save(p);
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHICHANGZHUANYUAN",
				"comfirm_worksheet", order.getOrderId());
		try {
			data.put("comfirmworksheet", true);
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("CAIGOUZHUGUAN",
				"purchase_ok", order.getOrderId());
		try {
			data.put("isworksheet", true);
			jbpmAPIUtil.completeTask(taskId, data, "CAIGOUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHEJIZHUGUAN",
				"design_ok", order.getOrderId());
		try {
			//data.put("isworksheet", true);
			jbpmAPIUtil.completeTask(taskId, data, "SHEJIZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHICHANGZHUANYUAN",
				"sign_contract", order.getOrderId());
		try {
			//data.put("isworksheet", true);
			jbpmAPIUtil.completeTask(taskId, data, "SHICHANGZHUANYUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("CAIWUZHUGUAN",
				"thirtypercent", order.getOrderId());
		try {
			data.put("epositok", true);
			jbpmAPIUtil.completeTask(taskId, data, "CAIWUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("CAIGOUZHUGUAN",
				"purchase_comfirm", order.getOrderId());
		try {
			data.put("procurementerror", false);
			jbpmAPIUtil.completeTask(taskId, data, "CAIGOUZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHEJIZHUGUAN",
				"product_comfirm", order.getOrderId());
		try {
			//data.put("isworksheet", true);
			jbpmAPIUtil.completeTask(taskId, data, "SHEJIZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("SHENGCHANZHUGUAN",
				"volume_production", order.getOrderId());
		try {
			data.put("volumeproduction", true);
			jbpmAPIUtil.completeTask(taskId, data, "SHENGCHANZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data=new HashMap<String, Object>();
		taskId=getTaskId("ZHIJIANZHUGUAN",
				"quality", order.getOrderId());
		try {
			data.put("volumeproduction", true);
			jbpmAPIUtil.completeTask(taskId, data, "ZHIJIANZHUGUAN");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	
	

	public long getTaskId(String actorId, String taskName, Integer orderId) {
		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				actorId, taskName);
		for(TaskSummary task:list){
			if(getVariable("orderId", task).equals(orderId)){
				return task.getId();
			}
		}
		return 0;
	}

	
	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
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

}
