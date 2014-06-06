package nju.software.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import nju.software.dataobject.Order;

import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.BlockingGetTaskResponseHandler;
import org.jbpm.process.workitem.wsht.HornetQHTWorkItemHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Status;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.jbpm.workflow.instance.WorkflowProcessInstance;

/**
 * 本类封装jbpm5常用的API
 * 
 * @author william
 */
public class JbpmAPIUtil {

	/**
	 * horentQ Task Server地址
	 */
	private static String ipAddress = "127.0.0.1";

	/**
	 * hornetQ Task Server址址的端口
	 */
	private static int port = 5445;

	/**
	 * hornetQ Task 客户端
	 */
	private static TaskClient client;

	private Map<String, List<String>> groupListMap = new HashMap<String, List<String>>();

	/**
	 * 流程定义
	 */
	private KnowledgeBase kbase;

	/**
	 * 与流程定义绑定的会话，可以生成流程实例
	 */
	private StatefulKnowledgeSession ksession;

	/**
	 * 流程实例名称
	 */
	private String processName;

	/**
	 * 流程实例参数
	 */
	private Map<String, Object> params;

	private HornetQHTWorkItemHandler hornetQHTWorkItemHandler;

	/*
	 * This is similar to 'completeTask' method, but to complete a task that is
	 * in 'Progress' state. In this case client start method is not called
	 */
	/*
	 * public static void completeProgressTask(long taskId, Map data, String
	 * userId) throws InterruptedException { connect();
	 * 
	 * BlockingTaskOperationResponseHandler responseHandler = new
	 * BlockingTaskOperationResponseHandler();
	 * responseHandler.waitTillDone(5000); // Thread.sleep(10000);
	 * responseHandler = new BlockingTaskOperationResponseHandler(); ContentData
	 * contentData = null; if (data != null) { ByteArrayOutputStream bos = new
	 * ByteArrayOutputStream(); ObjectOutputStream out; try { out = new
	 * ObjectOutputStream(bos); out.writeObject(data); out.close(); contentData
	 * = new ContentData(); contentData.setContent(bos.toByteArray());
	 * contentData.setAccessType(AccessType.Inline); } catch (IOException e) {
	 * e.printStackTrace(); } } client.complete(taskId, userId, contentData,
	 * responseHandler); responseHandler.waitTillDone(5000); }
	 */

	// public void assignTask(long taskId, String idRef, String userId) {
	// connect();
	// BlockingTaskOperationResponseHandler responseHandler = new
	// BlockingTaskOperationResponseHandler();
	// if (idRef == null) {
	// client.release(taskId, userId, responseHandler);
	// } else if (idRef.equals(userId)) {
	// List<String> roles = groupListMap.get(userId);
	// if (roles == null) {
	// client.claim(taskId, idRef, responseHandler);
	// } else {
	// client.claim(taskId, idRef, roles, responseHandler);
	// }
	// } else {
	// client.delegate(taskId, userId, idRef, responseHandler);
	// }
	// responseHandler.waitTillDone(5000);
	// }

	/**
	 * 设置任务状态为完成
	 * 
	 * @param taskId
	 *            任务ID
	 * @param data
	 *            任务内容
	 * @param userId
	 *            任务操作者
	 * @throws InterruptedException
	 */
	public void completeTask(long taskId, Map<?, ?> data, String userId)
			throws InterruptedException {
		connect();
		registerWorkItemHandler();
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		client.start(taskId, userId, responseHandler);
		responseHandler.waitTillDone(2000);

		BlockingGetTaskResponseHandler responseHandlerGetTask = new BlockingGetTaskResponseHandler();
		client.getTask(taskId, responseHandlerGetTask);
		responseHandlerGetTask.waitTillDone(3000);
		Task task = responseHandlerGetTask.getTask();
		responseHandler = new BlockingTaskOperationResponseHandler();

		ContentData contentData = null;
		if (data != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(data);
				out.close();
				contentData = new ContentData();
				contentData.setContent(bos.toByteArray());
				contentData.setAccessType(AccessType.Inline);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		client.complete(taskId, userId, contentData, responseHandler);

		// 强制把Human Task提交
		@SuppressWarnings("unchecked")
		Map<String, Object> results = (Map<String, Object>) data;
		ksession.getWorkItemManager().completeWorkItem(
				task.getTaskData().getWorkItemId(), results);

	}

	/**
	 * 根据用户id,返回其待办任务
	 * 
	 * @param idRef
	 *            用户ID
	 * @return 任务列表
	 */
	public List<TaskSummary> getAssignedTasks(String idRef) {
		connect();
		List<TaskSummary> tasks = null;// 保存从数据库拿到的task记录
		List<TaskSummary> doWithTaskList = new ArrayList<TaskSummary>();// 保存非完成状态的task列表
		try {
			BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
			List<Status> statuses = new ArrayList<Status>();
			statuses.add(Status.Reserved);
			client.getTasksOwned(idRef, statuses, "en-UK", responseHandler);
			doWithTaskList = responseHandler.getResults();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		// 最终返回
		return doWithTaskList;
	}

	/**
	 * 根据用户id和taskname,返回对应taskName的任务列表
	 * 
	 * @param actorId
	 * @param taskName
	 * @return
	 */
	public List<TaskSummary> getAssignedTasksByTaskname(String actorId,
			String taskName) {
		List<TaskSummary> tasks = new ArrayList<TaskSummary>();// 保存非完成状态的task列表
		List<TaskSummary> list = getAssignedTasks(actorId);
		if (list.isEmpty()) {
			System.out.println("no assigned task");
		}
		for (TaskSummary task : list) {
			if (task.getName().equals(taskName)) {
				tasks.add(task);
			}
		}
		return tasks;
	}

	public TaskSummary getTask(String actorId, String taskName, Integer orderId) {
		List<TaskSummary> tasks = getAssignedTasks(actorId);

		for (TaskSummary task : tasks) {
			if (task.getName().equals(taskName)
					&& getVariable(task, "orderId").equals(orderId)) {
				return task;
			}
		}
		return null;
	}

	public Object getVariable(TaskSummary task, String name) {
		StatefulKnowledgeSession session = getKsession();
		long processId = task.getProcessInstanceId();
		WorkflowProcessInstance process = (WorkflowProcessInstance) session
				.getProcessInstance(processId);
		return process.getVariable(name);
	}

	/**
	 * hornetQ客户端
	 */
	public void connect() {
		if (client == null) {
			client = new TaskClient(new HornetQTaskClientConnector(
					"org.drools.process.workitem.wsht.WSHumanTaskHandler"
							+ UUID.randomUUID().toString(),
					new HornetQTaskClientHandler(SystemEventListenerFactory
							.getSystemEventListener())));

			boolean connected = client.connect(ipAddress, port);
			if (!connected) {
				throw new IllegalArgumentException("不能连接到任务客户端服务器!");
			}
		}
		/*
		 * try { ClassLoader loader =
		 * Thread.currentThread().getContextClassLoader(); URL url = null;
		 * String propertyName = "roles.properties";
		 * 
		 * if (loader instanceof URLClassLoader) { URLClassLoader ucl =
		 * (URLClassLoader) loader; url = ucl.findResource(propertyName); } if
		 * (url == null) { url = loader.getResource(propertyName); } if (url ==
		 * null) { System.out.println("No properties file: " + propertyName +
		 * " found"); } else { Properties bundle = new Properties(); InputStream
		 * is = url.openStream(); if (is != null) { bundle.load(is); is.close();
		 * } else { throw new IOException("Properties file " + propertyName +
		 * " not available"); } Enumeration<?> propertyNames =
		 * bundle.propertyNames(); while (propertyNames.hasMoreElements()) {
		 * String key = (String) propertyNames.nextElement(); String value =
		 * bundle.getProperty(key); groupListMap.put(key,
		 * Arrays.asList(value.split(","))); System.out.print("Loaded user " +
		 * key + ":"); for (String role: groupListMap.get(key)) {
		 * System.out.print(" " + role); } System.out.println(); } } } catch
		 * (Throwable t) { t.printStackTrace(); }
		 */
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}

	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getProcessName() {
		return processName;
	}

	/**
	 * 设置HorentQ Task Server连接参数
	 * 
	 * @param ipAddress
	 *            IP地址
	 * @param port
	 *            端口号
	 */
	public void setConnection(String ipAddress, int port) {
		JbpmAPIUtil.ipAddress = ipAddress;
		JbpmAPIUtil.port = port;
	}

	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 启动流程实例
	 */
	public ProcessInstance startWorkflowProcess() {
		ProcessInstance pi = null;
		try {

			registerWorkItemHandler();
			// 启动流程
			pi = ksession.startProcess(processName, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	public void registerWorkItemHandler() {
		if (hornetQHTWorkItemHandler == null) {
			hornetQHTWorkItemHandler = new HornetQHTWorkItemHandler(ksession);
			// 注册人工服务
			ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
					hornetQHTWorkItemHandler);
		}
	}
	
	public void abortProcess(long processId){
		ksession.abortProcessInstance(processId);
	}
}
