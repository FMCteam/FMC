package nju.software.model;

import nju.software.dataobject.Order;

public class OrderModel {
	private Order order;
	private String taskId;
	private String processInstanceId;
	
	public OrderModel(){
		
	}
	
	public OrderModel(Order order, String taskId, String processInstanceId) {
		this.order = order;
		this.taskId = taskId;
		this.processInstanceId = processInstanceId;
	}

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
	

}
