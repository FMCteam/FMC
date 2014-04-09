package nju.software.service;

import java.util.List;

import nju.software.dataobject.Account;
import nju.software.dataobject.Money;
import nju.software.model.SampleMoneyConfirmTaskSummary;

public interface FinanceService {
	
	
	public List<SampleMoneyConfirmTaskSummary> getSampleMoneyConfirmTaskSummaryList();
	
	public SampleMoneyConfirmTaskSummary getSampleMoneyConfirmTask(long taskId,Integer orderId);
	
	public void sampleMoneyConfirm(long taskId,Money money);
	
	public void sampleMoneyNoConfirm(long taskId);
	
	public boolean confirmSampleMoneySubmit(Account account, int orderId, long taskId, 
			long processId, boolean receivedsamplejin, Money money);
	
	public boolean confirmDepositSubmit(Account account, int orderId, long taskId, 
			long processId, boolean epositok, Money money);
	
	public boolean confirmFinalPaymentSubmit(Account account, int orderId, long taskId, 
			long processId, boolean paymentok, Money money);

}
