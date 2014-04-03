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
import nju.software.dao.impl.QuoteDAO;
import nju.software.dataobject.Money;
import nju.software.dataobject.Order;
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

	public void addQuoteConfirmTask(Integer eid) {

		Order order = new Order();
		order.setEmployeeId(eid);
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

		Quote q = new Quote();
		q.setOrderId(order.getOrderId());
		q.setInnerPrice((float) 12);
		q.setOuterPrice((float) 12);
		quoteDAO.save(q);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", order.getOrderId());
		params.put("employeeId", order.getEmployeeId());
		params.put("needclothes", order.getIsNeedSampleClothes() == 1);
		params.put("sendclothes", order.getHasPostedSampleClothes() == 1);
		doTMWorkFlowStart(params);

		List<TaskSummary> list = jbpmAPIUtil.getAssignedTasksByTaskname(
				"CAIGOUZHUGUAN", "verification_purchased");

		System.out.println("采购验证:" + list.size());

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("buyVal", true);
				params.put("buyComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params, "CAIGOUZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHEJIZHUGUAN",
				"design_verification ");

		System.out.println("设计验证:" + list.size());

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("designVal", true);
				params.put("designComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params, "SHEJIZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("生产验证:" + list.size());

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHENGCHANZHUGUAN",
				"production_verification");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("productVal", true);
				params.put("productComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params,
						"SHENGCHANZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("CAIGOUZHUGUAN",
				"Purchasing_accounting");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("productVal", true);
				params.put("productComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params, "CAIGOUZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHEJIZHUGUAN",
				"design_accounting");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("designVal", true);
				params.put("designComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params, "SHEJIZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHENGCHANZHUGUAN",
				"business_accounting");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("productVal", true);
				params.put("productComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params,
						"SHENGCHANZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHICHANGZHUANYUAN",
				"quote");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("productVal", true);
				params.put("productComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params,
						"SHICHANGZHUANYUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		list = jbpmAPIUtil.getAssignedTasksByTaskname("SHICHANGZHUGUAN",
				"check_quote");

		for (TaskSummary task : list) {
			params = new HashMap<String, Object>();
			try {
				params.put("productVal", true);
				params.put("productComment", "");
				jbpmAPIUtil.completeTask(task.getId(), params,
						"SHICHANGZHUGUAN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
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
