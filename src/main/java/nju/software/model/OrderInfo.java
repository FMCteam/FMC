package nju.software.model;

import nju.software.dataobject.Order;

public class OrderInfo {
	
	private Order order;
	private long taskId;
	
	
	
	
	public OrderInfo(Order order, long taskId) {
		super();
		this.order = order;
		this.taskId = taskId;
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
	
	
	

}
