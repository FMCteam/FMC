package nju.software.process.service;

import java.util.List;
import java.util.Map;

import nju.software.service.impl.MarketServiceImpl;
import nju.software.util.ActivitiAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.activiti.engine.task.Task;


public class BasicProcessService {
	@Autowired
	public ActivitiAPIUtil activitiAPIUtil;
	
	public  String startWorkflow(String processId, Map<String, Object> params){
		return activitiAPIUtil.startWorkflowProcessByKey(processId, params);
	}
	
	public void abortWorkflow(String processId){
		activitiAPIUtil.abortProcess(processId);
	}
	
	public void completeTask(String taskId, String userId, Map<String, Object> params) throws InterruptedException{
		activitiAPIUtil.completeTask(taskId, params, userId);
	}
	
	public List<String> getProcessStateNames(String processId){
		return activitiAPIUtil.getProcessStateNames(processId);
	}
	
	public List<Map<String, Object>> getProcessStates(String processId){
		return activitiAPIUtil.getProcessStates(processId);
	}
	
	public Object getProcessVariable(String processId, String key){
		return activitiAPIUtil.getProcessVariable(processId, key);
	}
	
	public boolean isProcessInstanceActive(String processId){
		return activitiAPIUtil.isProcessInstanceExist(processId);
	}
	
	public Task getTask(String taskId){
		return activitiAPIUtil.getTask(taskId);
	}
	
	/**
	 * 
	 * @param userId
	 * @param taskName
	 * @param key
	 * @param value
	 * @return
	 */
	public Task getTask(String userId, String taskName, String key, Object value){
		return activitiAPIUtil.getTask(userId, taskName, key, value);
	}
	
	public List<Task> getAssignTasksByGroup(String groupId){
		return activitiAPIUtil.getAssignedTasksOfGroup(groupId);
	}

	public List<Task> getTasksOfUser(String userId){
		return activitiAPIUtil.getAssignedTasksOfUser(userId);
	}
	
	public List<Task> getTasksOfUserByTaskName(String userId, String taskName){
		return activitiAPIUtil.getAssignedTasksOfUserByTaskName(userId, taskName);
	}
	
	public void alterAssignee(String processId, String old_userId, String new_userId){
		activitiAPIUtil.updateProcessVariable(processId, MarketServiceImpl.ACTOR_MARKET_STAFF, new_userId);
	}
	
}
