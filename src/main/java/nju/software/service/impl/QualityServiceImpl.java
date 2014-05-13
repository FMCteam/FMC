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

import nju.software.dao.impl.CustomerDAO;
import nju.software.dao.impl.EmployeeDAO;
import nju.software.dao.impl.OrderDAO;
import nju.software.dao.impl.ProduceDAO;
import nju.software.dataobject.Order;
import nju.software.dataobject.Produce;
import nju.software.model.OrderInfo;
import nju.software.model.OrderModel;
import nju.software.service.QualityService;
import nju.software.util.JbpmAPIUtil;

@Service("qualityServiceImpl")
public class QualityServiceImpl implements QualityService {

	public final static String ACTOR_QUALITY_MANAGER = "qualityManager";
	public final static String TASK_CHECK_QUALITY = "checkQuality";

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	@Autowired
	private ServiceUtil service;
	@Autowired
	private ProduceDAO produceDAO;

	@Override
	public List<Map<String, Object>> getCheckQualityList() {
		// TODO Auto-generated method stub
		return service.getOrderList(ACTOR_QUALITY_MANAGER, TASK_CHECK_QUALITY);
	}

	public Object getVariable(String name, TaskSummary task) {
		StatefulKnowledgeSession session = jbpmAPIUtil.getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	@Override
	public boolean checkQualitySubmit(int id, long taskId, boolean b,
			List<Produce> goodList, List<Produce> badList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < goodList.size(); i++) {
			produceDAO.save(goodList.get(i));
		}
		for (int i = 0; i < badList.size(); i++) {
			produceDAO.save(badList.get(i));
		}
		Map<String, Object> data = new HashMap<>();
		// data.put("", b);
		try {
			jbpmAPIUtil.completeTask(taskId, data, ACTOR_QUALITY_MANAGER);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Map<String,Object> getCheckQualityDetail(int orderId) {
		// TODO Auto-generated method stub
		Map<String,Object> oi=service.getBasicOrderModelWithQuote(
				ACTOR_QUALITY_MANAGER, TASK_CHECK_QUALITY, orderId);
		Produce produce = new Produce();
		produce.setOid(orderId);
		produce.setType(Produce.TYPE_PRODUCED);
		oi.put("produced", produceDAO.findByExample(produce));
		return oi;
	}
}
