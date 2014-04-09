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
	
	public boolean confirmSampleMoneySubmit();
	
	//===========================定金确认===================================
	public List<OrderInfo>getConfirmDepositList(String actorId);
	
	public OrderInfo getConfirmDepositDetail(String actorId,Integer orderId);
	
	public boolean confirmDepositSubmit();
	
	//===========================尾款确认===================================
	public List<OrderInfo>getConfirmFinalPaymentList(String actorId);
	
	public OrderInfo getConfirmFinalPaymentDetail(String actorId,Integer orderId);
	
	public boolean confirmFinalPaymentSubmit();
	
	
	public boolean confirmSampleMoneySubmit(Account account, int orderId, long taskId, 
			long processId, boolean receivedsamplejin, Money money);
	
	public boolean confirmDepositSubmit(Account account, int orderId, long taskId, 
			long processId, boolean epositok, Money money);
	
	public boolean confirmFinalPaymentSubmit(Account account, int orderId, long taskId, 
			long processId, boolean paymentok, Money money);

}
