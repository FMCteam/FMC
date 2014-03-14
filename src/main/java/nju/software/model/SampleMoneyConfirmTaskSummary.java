package nju.software.model;

import nju.software.dataobject.Order;
import nju.software.dataobject.Quote;

public class SampleMoneyConfirmTaskSummary {
	
	private Integer orderId;
	private String customerName;
	private String customerCompany;
	private String customerPhone1;
	private Float outerPrice;

	
	public static SampleMoneyConfirmTaskSummary getInstance(Order order,Quote quote){
		SampleMoneyConfirmTaskSummary task=new SampleMoneyConfirmTaskSummary();
		task.orderId=order.getOrderId();
		task.customerName=order.getCustomerName();
		task.customerCompany=order.getCustomerCompany();
		task.customerPhone1=order.getCustomerPhone1();
		task.outerPrice=quote.getOuterPrice();
		return task;
	}
	
}
