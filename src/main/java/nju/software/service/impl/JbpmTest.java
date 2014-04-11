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

	private static Integer orderId=0;
	
	
	
	//市场专员需要传入自己的account.getUserId,其他角色传入"1"
	public void addOrder(String actorId) {
		orderId = 0;
	}

	
	
	// 莫其凡：完成
	public void receiveSample(String actorId) {
		addOrder(actorId);
		long taskId = getTaskId(LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER,
				LogisticsServiceImpl.TASK_RECEIVE_SAMPLE, orderId);
		Map <String,Object> data=new HashMap <String,Object> ();
		data.put("receivedsample", true);
		completeTask(taskId, data, LogisticsServiceImpl.ACTOR_LOGISTICS_MANAGER);
	}

	
	public void verifyDesign(String actorId) {

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
