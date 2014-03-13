package nju.software.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.task.query.TaskSummary;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nju.software.dao.impl.OrderDAO;
import nju.software.dataobject.Account;
import nju.software.dataobject.Order;
import nju.software.service.BuyService;
import nju.software.util.JbpmAPIUtil;

@Service("buyServiceImpl")
public class BuyServiceImpl implements BuyService {
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private JbpmAPIUtil jbpmAPIUtil;
	
	@Override
	public boolean verify(Account account, int orderId, String taskName,
			boolean buyVal) {
		// TODO Auto-generated method stub
		String actorId = account.getUserRole();
		List<TaskSummary> list =jbpmAPIUtil.getAssignedTasksByTaskname(actorId, taskName);
		for (TaskSummary task : list) {
			//需要获取task中的数据	
			WorkflowProcessInstance process=(WorkflowProcessInstance) jbpmAPIUtil.getKsession().getProcessInstance(task.getProcessInstanceId());
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
				//直接进入到下一个流程时
				try {
					jbpmAPIUtil.completeTask(task.getId(), data, actorId);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

		}
		return false;
	}

}
