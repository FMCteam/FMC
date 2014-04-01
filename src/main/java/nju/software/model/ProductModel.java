package nju.software.model;
import nju.software.dataobject.*;
import java.util.*;
public class ProductModel {

	private List<Product> productList;
	private long taskId;
	private long processId;
	private int orderId;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public List<Accessory> getAccessoryList() {
		return accessoryList;
	}
	public void setAccessoryList(List<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}
	
	public List<Fabric> getFabricList() {
		return fabricList;
	}
	public void setFabricList(List<Fabric> fabricList) {
		this.fabricList = fabricList;
	}

	private List<Accessory> accessoryList;
	private List<Fabric> fabricList;
}
