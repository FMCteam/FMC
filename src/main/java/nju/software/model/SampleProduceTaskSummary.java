package nju.software.model;

import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;

public class SampleProduceTaskSummary {

	private Integer orderId;
	private String customerName;
	private String customerPhone;
	private String companyName;
	private String companyPhone;
	private Float innerPrice;
	private Float outerPrice;
	private long taskId;

	public static SampleProduceTaskSummary getInstance(Order order,
			Quote quote, long taskId) {
		SampleProduceTaskSummary task = new SampleProduceTaskSummary();
		task.orderId=order.getOrderId();
		task.customerName = order.getCustomerName();
		task.customerPhone = order.getCustomerPhone1();
		task.companyName = order.getCustomerCompany();
		// task.companyPhone=order.get
		task.innerPrice = quote.getInnerPrice();
		task.outerPrice = quote.getOuterPrice();
		task.taskId = taskId;
		return task;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public Float getInnerPrice() {
		return innerPrice;
	}

	public void setInnerPrice(Float innerPrice) {
		this.innerPrice = innerPrice;
	}

	public Float getOuterPrice() {
		return outerPrice;
	}

	public void setOuterPrice(Float outerPrice) {
		this.outerPrice = outerPrice;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
}
