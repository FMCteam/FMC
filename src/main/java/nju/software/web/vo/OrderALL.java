package nju.software.web.vo;

import java.util.List;

import nju.software.dataobject.Accessory;
import nju.software.dataobject.Fabric;
import nju.software.dataobject.Logistics;
import nju.software.dataobject.Order;

public class OrderALL{
	public Order order;
	public List<Fabric> fabricList;
	public List<Accessory> accessoryList;
	public Logistics logistics;
	public long taskId;
	public String reason1;
	public String reason2;
	public String reason3;
	public long processId;
	public OrderALL(){
		
	}
	public OrderALL(Order order,List<Fabric> fabric,List<Accessory> accessory,Logistics logistics,long taskId
			,String reason1,String reason2,String reason3,long processId)
	{
		this.order=order;
		this.fabricList=fabric;
		this.accessoryList=accessory;
		this.logistics=logistics;
		this.taskId=taskId;
		this.reason1=reason1;
		this.reason2=reason2;
		this.reason3=reason3;
		this.processId=processId;
		
	}
	public String getReason1() {
		return reason1;
	}
	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}
	public String getReason2() {
		return reason2;
	}
	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}
	public String getReason3() {
		return reason3;
	}
	public void setReason3(String reason3) {
		this.reason3 = reason3;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
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
	public List<Fabric> getFabricList() {
		return fabricList;
	}
	public void setFabricList(List<Fabric> fabricList) {
		this.fabricList = fabricList;
	}
	public List<Accessory> getAccessoryList() {
		return accessoryList;
	}
	public void setAccessoryList(List<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}
	public Logistics getLogistics() {
		return logistics;
	}
	public void setLogistics(Logistics logistics) {
		this.logistics = logistics;
	}
}