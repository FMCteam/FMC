package nju.software.model;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;

public class SampleProduceTask {
	
	private long taskId;
	private Order order;
	private List<Fabric> fabrics;
	private List<Accessory> accessorys;
	
	
	public SampleProduceTask(long taskId, Order order, List<Fabric> fabrics,
			List<Accessory> accessorys) {
		super();
		this.taskId = taskId;
		this.order = order;
		this.fabrics = fabrics;
		this.accessorys = accessorys;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<Fabric> getFabrics() {
		return fabrics;
	}
	public void setFabrics(List<Fabric> fabrics) {
		this.fabrics = fabrics;
	}
	public List<Accessory> getAccessorys() {
		return accessorys;
	}
	public void setAccessorys(List<Accessory> accessorys) {
		this.accessorys = accessorys;
	}
}
