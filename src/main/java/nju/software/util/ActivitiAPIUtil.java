package nju.software.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;


public class ActivitiAPIUtil {
	
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ManagementService managementService;
	
	/**流程实例参数*/
	private Map<String, Object> params;
	
	public void completeTask(String taskId, Map<String, Object> data, String userId) throws InterruptedException{
		taskService.complete(taskId, data);
	}
	/**
	 * 根据候选组的的ID返回其待办事务
	 * @param groupId
	 * 			候选组ID
	 * 
	 * @return 任务列表
	 */
	public List<Task> getAssignedTasksOfGroup(String groupId){
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupId).list();
		return tasks;
	}
	
	/**
	 * 
	 * @param groupId 候选用户组Id
	 * @param taskName 任务名
	 * @return 任务列表
	 */
	public List<Task> getAssignedTasksOfGroupByTaskName(String groupId, String taskName){
		List<Task> resultTasks = new ArrayList<Task>();
		TaskQuery query = taskService.createTaskQuery().taskCandidateGroup(groupId);
		resultTasks = query.taskName(taskName).list();
		return resultTasks;
	}
	
	/**
	 * 根据候选人ID返回其待办任务
	 * @param userId
	 * @return
	 */
	public List<Task> getAssignedTasksOfUser(String userId){
		List<Task> assignedTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		return assignedTasks;
	}
	
	/**
	 * 
	 * @param userId 候选人ID
	 * @param taskName
	 * @return
	 */
	public List<Task> getAssignedTasksOfUserByTaskName(String userId, String taskName){
		List<Task> resultTasks = new ArrayList<Task>();
		TaskQuery query = taskService.createTaskQuery().taskAssignee(userId);
	    resultTasks = query.taskName(taskName).list();
		return resultTasks;
	}
	
	/**
	 * 获取特定流程实例下，某个用户的任务列表
	 * @param processId
	 * @param userId
	 * @return
	 */
	public List<Task> getTasksOfUserWithSpecificProcessInstance(String processId, String userId){
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).taskAssignee(userId).list();
		return tasks;
	}
	
	/**
	 * 获取流程实例的参数
	 * @param task 任务
	 * @param name 参数名
	 * @return
	 */
	public Object getProcessVariable(Task task, String name){
		return runtimeService.getVariable(task.getProcessInstanceId(), name);
	}
	
	/**
	 * 获取流程实例的参数
	 * @param processId 流程Id
	 * @param name 参数名
	 * @return
	 */
	public Object getProcessVariable(String processId, String name){
		return runtimeService.getVariable(processId, name);
	}
	/**
	 * 添加新的用户
	 * @param userId
	 */
	public void addUser(String userId){
		User user = identityService.newUser(userId);
		identityService.saveUser(user);
	}
	
	/**
	 * 根据用户ID和任务ID获得任务
	 * @param groupId 用户ID
	 * @param taskId 任务ID
	 * @return
	 */
	public Task getTask(String groupId, String taskId){
		List<Task> tasks = getAssignedTasksOfUser(groupId);
		for (Task task : tasks) {
			if (task.getId().equals(taskId)) {
				return task;
			}
		}
		return null;
	}
	

	/**
	 * 根据用户ID和任务名，以及特定参数获取任务
	 * @param userId
	 * @param taskName
	 * @param key
	 * @param value
	 * @return
	 */
	public Task getTask(String userId, String taskName, String key, Object value){
		List<Task> tasks = getAssignedTasksOfUser(userId);
		for (Task task : tasks) {
			if (task.getName().equals(taskName) && value.equals(getProcessVariable(task.getProcessInstanceId(), key))) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * 启动流程实例并返回实例ID
	 * @return
	 */
	public String startWorkflowProcessByKey(String processName, Map<String, Object> params) throws RuntimeException{
		this.params = params;
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName, params);
			System.out.println("流程实例启动成功, Id号为： "+ pi.getId());
			return pi.getId();
	}
	
	/**
	 * 根据Id查询该流程实例是否存在
	 * @param processId
	 * @return
	 */
	public boolean isProcessInstanceExist(String processId){
		ProcessInstance pInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
		return pInstance != null;
	}
	

	/**
	 * 根据流程Id获取流程实例中的所有节点状态
	 * @param processId
	 * @return
	 */
	public List<Map<String, Object>> getProcessStates(String processId){
		List<Map<String, Object>> lists = new ArrayList<>();
		if (processId == null || !isProcessInstanceExist(processId)) {
			return lists;
		}
		List<ActivityImpl> activities = getCurrentActivityImpls(processId);
		for (ActivityImpl activityImpl : activities) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("x", activityImpl.getX());
			data.put("y", activityImpl.getY());
			data.put("width", activityImpl.getWidth());
			data.put("height", activityImpl.getHeight());
			data.put("statename", activityImpl.getProperty("name"));
			lists.add(data);
		}
		return lists;
	}
	
	/**
	 *  根据流程Id获取流程实例中的当前执行节点的Name
	 * @param processId
	 * @return
	 */
	public ArrayList<String> getProcessStateNames(String processId){
		ArrayList<String> names = new ArrayList<>();
		List<ActivityImpl> lists = getCurrentActivityImpls(processId);
		for (ActivityImpl activityImpl : lists) {
			names.add((String) activityImpl.getProperty("name"));
		}
		return names;
	}
	
	
	public void updateProcessVariable(String processId, String key, Object value){
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processId).list();
		for (Execution execution : executions) {
			runtimeService.setVariable(execution.getId(), key, value);
		}
	}
	/**
	 * 根据流程ID获取当前活动任务节点
	 * @param processId
	 * @return
	 */
	private List<ActivityImpl> getCurrentActivityImpls(String processId){
		ArrayList<ActivityImpl> lists = new ArrayList<>();
		if (processId == null || !isProcessInstanceExist(processId)) {
			return lists;
		}
		//获取当前执行任务
		List<Task> currentTasks = taskService.createTaskQuery().processInstanceId(processId).list();
		ArrayList<String> currentTaskDefs = new ArrayList<>();
		for (Task task : currentTasks) {
			currentTaskDefs.add(task.getTaskDefinitionKey());
		}
		
		String processDefinitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult().getProcessDefinitionId();
		ProcessDefinitionEntity entity =(ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);
		//获取流程中所有节点实例
		List<ActivityImpl> activities = entity.getActivities();
	
		for (ActivityImpl activityImpl : activities) {
			//如果当前执行任务DefKey和节点ID相同
			if (currentTaskDefs.contains(activityImpl.getId())) {
				lists.add(activityImpl);
			}
		}
		return lists;
	}
	
	
	/**删除流程实例*/
	public void abortProcess(String processId){
		runtimeService.deleteProcessInstance(processId, "No reason!");
	}
	
	public void setParams(Map<String, Object> params){
		this.params = params;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	
	public void setProcessEngine(ProcessEngine processEngine){
		this.processEngine = processEngine;
	}
	
	public ProcessEngine getProcessEngine(){
		return processEngine;
	}
	
	public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration){
		this.processEngineConfiguration = processEngineConfiguration;
	}
	
	public ProcessEngineConfiguration getProcessEngineConfiguration(){
		return processEngineConfiguration;
	}
	
}

