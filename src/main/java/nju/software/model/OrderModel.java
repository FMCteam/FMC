package nju.software.model;

import nju.software.dataobject.Order;

public class OrderModel {
	private Order order;
	private long taskId;
	private long processInstanceId;
	
	public OrderModel(){
		
	}
	
	public OrderModel(Order order, long taskId, long processInstanceId) {
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
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
	

}
