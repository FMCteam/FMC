package nju.software.process.service;

import java.util.List;
import java.util.Map;

import nju.software.service.impl.MarketServiceImpl;

import org.activiti.engine.task.Task;

public class MarketstaffAlterProcessService extends BasicProcessService{
	public static final String PROCESS_NAME = "nju.software.marketStaffAlter.bpmn";
	
	public String startWorkflow(Map<String, Object> params){
		return startWorkflow(PROCESS_NAME, params);
	}
	
	public void completeVerifyAlterTask(String taskId, Map<String, Object> params) throws InterruptedException{
		if (params.containsKey("old_staff") && params.containsKey("new_staff")) {
			//更换专员流程ID
			String processId = (String) params.get(MarketServiceImpl.ALTER_PROCESSID);
			//被申请更换专员order的执行流程ID
			String orderProcessId = (String) getProcessVariable(processId, MarketServiceImpl.ALTER_ORDER_PROCESSID);
			String old_userId = String.valueOf(params.get("old_staff"));
			String new_userId = String.valueOf(params.get("new_staff"));
			alterAssignee(orderProcessId, old_userId, new_userId);
		}
		completeTask(taskId, MarketServiceImpl.ACTOR_MARKET_MANAGER, params);
	}
	
	
	public List<Task> getVerifyAlterTasksOfMarketManager(){
		return getTasksOfUserByTaskName(MarketServiceImpl.ACTOR_MARKET_MANAGER, MarketServiceImpl.TASK_VERIFY_ALTER);
	}
	

	/**
	 * 获取主管同意申请时，当前订单的状态
	 * @param processId 订单流程实例ID
	 * @return
	 */
	public String getCurrentTaskNames(String processId){
		String orderProcessId = (String) getProcessVariable(processId, MarketServiceImpl.ALTER_ORDER_PROCESSID);
		if (!isProcessInstanceActive(orderProcessId)) {
			return "订单已结束";
		}
		List<String> names = getProcessStateNames(orderProcessId);
		StringBuffer result = new StringBuffer();
		for (String one : names) {
			result.append(one);
		}
		return result.toString();
	}
	
	public Object getReason(String processId){
		return getProcessVariable(processId, MarketServiceImpl.ALTER_REASON);
	}
	
	public Object getComment(String processId){
		return getProcessVariable(processId, MarketServiceImpl.ALTER_COMMENT);
	}
	
	public Task getTaskWithSpecificAlterId(int value){
		return getTask(MarketServiceImpl.ACTOR_MARKET_MANAGER,MarketServiceImpl.TASK_VERIFY_ALTER, "alterId", value);
	}
}
