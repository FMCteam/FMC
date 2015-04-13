package nju.software.process.service;

import java.util.List;
import java.util.Map;

import nju.software.util.ActivitiAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.activiti.engine.task.Task;


public class BasicProcessService {
	@Autowired
	public ActivitiAPIUtil activitiAPIUtil;
	
	public  String startWorkflow(String processId, Map<String, Object> params){
		return activitiAPIUtil.startWorkflowProcessById(processId, params);
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
	
	public Task getTask(String userId, String taskName, String key, Object value){
		return activitiAPIUtil.getTask(userId, taskName, key, value);
	}
	
	public List<Task> getAssignTasksByGroup(String groupId){
		return activitiAPIUtil.getAssignedTasksOfGroup(groupId);
	}

	public List<Task> getAssignTasksByUser(String userId){
		return activitiAPIUtil.getAssignedTasksOfUser(userId);
	}
	
	public List<Task> getAssignTasksOfUserByTaskName(String userId, String taskName){
		return activitiAPIUtil.getAssignedTasksOfUserByTaskName(userId, taskName);
	}
	
}
