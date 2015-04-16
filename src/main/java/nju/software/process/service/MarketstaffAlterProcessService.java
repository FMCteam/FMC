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
	
	public void completeVerifyAterTask(String taskId, Map<String, Object> params) throws InterruptedException{
		completeTask(taskId, MarketServiceImpl.ACTOR_MARKET_MANAGER, params);
	}
	
	public List<Task> getVerifyAlterTasksOfMarketManager(){
		return getTasksOfUserByTaskName(MarketServiceImpl.ACTOR_MARKET_MANAGER, MarketServiceImpl.TASK_VERIFY_ALTER);
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
