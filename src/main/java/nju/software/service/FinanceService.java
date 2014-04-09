package nju.software.service;

import java.util.List;

import nju.software.dataobject.Account;
import nju.software.dataobject.Money;
import nju.software.model.OrderInfo;
import nju.software.model.SampleMoneyConfirmTaskSummary;

public interface FinanceService {
	
	//===========================样衣金确认=================================
	public List<OrderInfo>getConfirmSampleMoneyList(String actorId);
	
	public OrderInfo getConfirmSampleMoneyDetail(String actorId,Integer orderId);
	
	public boolean confirmSampleMoneySubmit(String actorId,long taskId,boolean receivedsamplejin, Money money);
	
	//===========================定金确认===================================
	public List<OrderInfo>getConfirmDepositList(String actorId);
	
	public OrderInfo getConfirmDepositDetail(String actorId,Integer orderId);
	
	public boolean confirmDepositSubmit(String actorId,long taskId,boolean epositok, Money money);
	
	//===========================尾款确认===================================
	public List<OrderInfo>getConfirmFinalPaymentList(String actorId);
	
	public OrderInfo getConfirmFinalPaymentDetail(String actorId,Integer orderId);
	
	public boolean confirmFinalPaymentSubmit(String actorId,long taskId,boolean paymentok, Money money);
}
