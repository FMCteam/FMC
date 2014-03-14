package nju.software.service;

import java.util.List;

import nju.software.dataobject.Money;
import nju.software.model.SampleMoneyConfirmTaskSummary;

public interface FinanceService {
	
	
	public List<SampleMoneyConfirmTaskSummary> getSampleMoneyConfirmTaskSummaryList();
	
	public SampleMoneyConfirmTaskSummary getSampleMoneyConfirmTask(long taskId,Integer orderId);
	
	public void sampleMoneyConfirm(long taskId,Money money);
	
	public void sampleMoneyNoConfirm(long taskId);

}
