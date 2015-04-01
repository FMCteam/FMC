package nju.software.process.service;

import java.util.List;
import java.util.Map;

import nju.software.service.impl.MarketServiceImpl;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

public class MarketstaffAlterProcessService {
	public static final String PROCESS_NAME = "nju.software.marketStaffAlter.bpmn";
	public static final String TASK_VERIFY_ALTER = "verifyAlter";
	@Autowired
	private BasicProcessService service;
	
	public String startWorkflow(Map<String, Object> params){
		return service.startWorkflow(PROCESS_NAME, params);
	}
	
	public void completeVerifyAterTask(String taskId, Map<String, Object> params) throws InterruptedException{
		service.completeTask(taskId, MarketServiceImpl.ACTOR_MARKET_MANAGER, params);
	}
	
	public List<Task> getVerifyAlterTasksOfMarketManager(){
		return service.getAssignTasksOfUserByTaskName(MarketServiceImpl.ACTOR_MARKET_MANAGER, TASK_VERIFY_ALTER);
	}
	
	public void abortWorkflow(String processId){
		service.abortWorkflow(processId);
	}

}
