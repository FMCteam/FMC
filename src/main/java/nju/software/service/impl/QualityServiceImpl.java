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

import nju.software.dao.impl.OrderDAO;
import nju.software.model.OrderModel;
import nju.software.service.QualityService;
import nju.software.util.JbpmAPIUtil;

@Service("qualityServiceImpl")
public class QualityServiceImpl implements QualityService{

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Override
	public List<OrderModel> getCheckList() {
		// TODO Auto-generated method stub
		List<TaskSummary> tasks = jbpmAPIUtil.getAssignedTasksByTaskname(
				"ZHIJIANZHUGUAN", "quality");
		List<OrderModel> taskSummarys = new ArrayList<>();
		for (TaskSummary task : tasks) {
			Integer orderId = (Integer) getVariable("orderId", task);
			OrderModel om = new OrderModel(orderDAO.findById(orderId),task.getId(),task.getProcessInstanceId());
			taskSummarys.add(om);
		}
		return taskSummarys;
	}
	
	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public boolean checkQuality(int id, long taskId, long processId, boolean b) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		//data.put("", b);
		try {
			jbpmAPIUtil.completeTask(taskId, data, "ZHIJIANZHUGUAN");
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
