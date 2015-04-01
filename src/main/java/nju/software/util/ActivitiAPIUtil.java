<<<<<<< HEAD
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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;


public class ActivitiAPIUtil {
	
	/** 流程实例名称*/
	private String processName;
	
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
		List<Task> assignedTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
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
		TaskQuery query = taskService.createTaskQuery().taskCandidateUser(userId);
	    resultTasks = query.taskName(taskName).list();
		return resultTasks;
	}
	
	/**
	 * 获取流程实例的参数
	 * @param task 任务
	 * @param name 参数名
	 * @return
	 */
	public Object getProcessVariable(Task task, String name){
		//TODO  不清楚是什么的东西的参数
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
	 * 根据用户ID和任务ID获得任务
	 * @param userId 用户ID
	 * @param taskName 任务名
	 * @param orderId 订单的ID
	 * @return
	 */
	public Task getTask(String userId, String taskName, String key, Object value){
		List<Task> tasks = getAssignedTasksOfUser(userId);
		for (Task task : tasks) {
			if (task.getName().equals(taskName) && getProcessVariable(task.getProcessInstanceId(), key).equals(value)) {
				
				return task;
			}
		}
		return null;
	}
	
	/**
	 * 启动流程实例并返回实例ID
	 * @return
	 */
	public String startWorkflowProcessByKey(String key, Map<String, Object> params) throws RuntimeException{
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
	
	public void setProcessName(String processName){
		this.processName = processName;
	}
	
	public String getProcessName(){
		return processName;
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
=======
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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;


public class ActivitiAPIUtil {
	
	/** 流程实例名称*/
	private String processName;
	
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
		Map<String, String> nameMap = getTaskNameMap();
		TaskQuery query = taskService.createTaskQuery().taskCandidateGroup(groupId);
		resultTasks = query.taskName(nameMap.get(taskName)).list();
		return resultTasks;
	}
	
	/**
	 * 根据候选人ID返回其待办任务
	 * @param userId
	 * @return
	 */
	public List<Task> getAssignedTasksOfUser(String userId){
		List<Task> assignedTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
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
		TaskQuery query = taskService.createTaskQuery().taskCandidateUser(userId);
	    resultTasks = query.taskName(getTaskNameMap().get(taskName)).list();
		return resultTasks;
	}
	
	/**
	 * 获取流程实例的参数
	 * @param task 任务
	 * @param name 参数名
	 * @return
	 */
	public Object getProcessVariable(Task task, String name){
		//TODO  不清楚是什么的东西的参数
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
	 * 根据用户ID和任务ID获得任务
	 * @param userId 用户ID
	 * @param taskName 任务名
	 * @param orderId 订单的ID
	 * @return
	 */
	public Task getTask(String userId, String taskName, Integer orderId){
		List<Task> tasks = getAssignedTasksOfUser(userId);
		Map<String, String> nameMap = getTaskNameMap();
		for (Task task : tasks) {
			if (task.getName().equals(nameMap.get(taskName)) && getProcessVariable(task, "orderId").equals(orderId)) {
				return task;
			}
		}
		return null;
	}
	
	/**
	 * 启动流程实例并返回实例ID
	 * @return
	 */
	public String startWorkflowProcess(Map<String, Object> params) throws RuntimeException{
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
	
	public void setProcessName(String processName){
		this.processName = processName;
	}
	
	public String getProcessName(){
		return processName;
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
	
	/**
	 * 从流程图中获取到的taskname为中文，而前端需要英文名<span class..>，因此做了个映射
	 * @return
	 */
	private Map<String, String> getTaskNameMap(){
		Map<String, String> nameMap = new HashMap<>();
		//BuyService
		nameMap.put("verifyPurchase","采购验证");
		nameMap.put("computePurchaseCost","采购成本验证并核算");
		nameMap.put( "purchaseSampleMaterial","样衣面料采购");
		nameMap.put( "confirmPurchase","采购确认");
		nameMap.put( "purchaseMaterial","大货面料采购确认");
		nameMap.put( "buySweaterMaterial","购买组织原料");
		//DesignService
		nameMap.put( "verifyDesign","设计验证");
		nameMap.put("computeDesignCost","设计验证");
		nameMap.put("uploadDegisn", "录入版型数据及生产样衣");
		nameMap.put("modifyDesign", "修改设计");
		nameMap.put("confirmDesign", "设计验证");
		nameMap.put("craftSample", "样衣工艺制作");
		nameMap.put("craft", "大货工艺制作");
		nameMap.put("typeSettingSlice", "排版切片");
		nameMap.put("confirmCad", "确认版型");
		//FinanceService
		nameMap.put("confirmSampleMoney", "确认样衣制作金");
		nameMap.put("confirmDeposit", "30%定金确认");
		nameMap.put("returnDeposit", "退还定金");
		nameMap.put("confirmFinalPayment", "尾款金额确认");
		//LogisticsService
		nameMap.put("receiveSample", "收取样衣");
		nameMap.put("sendSample", "样衣发货");
		nameMap.put("warehouse", "入库");
		nameMap.put("warehouse_haoduoyi", "好多衣入库");
		nameMap.put("sendClothes", "发货");
		//MarketService
		nameMap.put("modifyOrder", "修改询单");
		nameMap.put("mergeQuote", "专员合并报价");
		nameMap.put("verifyQuote", "主管审核报价");
		nameMap.put("confirmQuote", "商定报价");
		nameMap.put("modifyQuote", "修改报价");
		nameMap.put("confirmProduceOrder", "确认加工单并签订合同");
		nameMap.put("modifyProduceOrder", "修改加工订单");
		nameMap.put("signContract", "签订合同");
		nameMap.put("pushRest", "催尾款");
		//ProduceService
		nameMap.put("verifyProduce", "生产验证");
		nameMap.put("computeProduceCost", "生产成本验证并核算");
		nameMap.put("produceSample", "生产样衣");
		nameMap.put("produce", "批量生产");
		//QualityService
		nameMap.put("checkQuality", "质量检测");
		//SweaterMakeService
		nameMap.put("confirmSweaterSampleAndCraft", "打小样&制作工艺&制版打样&样衣确认");
		nameMap.put("sendSweater", "外发");
		return nameMap;
	}
	
}
>>>>>>> 16b254200d83c9bd3c1c8c75e60d888794a7eb5e
